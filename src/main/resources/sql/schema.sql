-- User table to store user information
CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    full_name  VARCHAR(100),
    phone_number VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active     BOOLEAN   DEFAULT TRUE
);

-- Email notification table to track emails sent to users
CREATE TABLE email_notifications
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT       NOT NULL,
    email_type VARCHAR(50)  NOT NULL,
    subject    VARCHAR(200) NOT NULL,
    content    CLOB,
    sent_at    TIMESTAMP,
    status     VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Coupon table to store event coupons
CREATE TABLE coupons
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    code                VARCHAR(50) NOT NULL UNIQUE,
    description         VARCHAR(200),
    discount_amount     DECIMAL(10, 2),
    discount_percentage INT,
    valid_from          TIMESTAMP   NOT NULL,
    valid_until         TIMESTAMP   NOT NULL,
    is_active           BOOLEAN     DEFAULT TRUE,
    coupon_type         VARCHAR(50) DEFAULT 'EVENT',
    created_at          TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

-- User coupon table to track which coupons are assigned to users
CREATE TABLE user_coupons
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id   BIGINT NOT NULL,
    coupon_id BIGINT NOT NULL,
    issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    used_at   TIMESTAMP,
    is_used   BOOLEAN   DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (coupon_id) REFERENCES coupons (id),
    UNIQUE (user_id, coupon_id)
);

-- User statistics table to track user growth
CREATE TABLE user_statistics
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    date         DATE NOT NULL UNIQUE,
    total_users  INT  NOT NULL DEFAULT 0,
    new_users    INT  NOT NULL DEFAULT 0,
    active_users INT  NOT NULL DEFAULT 0,
    updated_at   TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);

-- Message queue events table to track events that need to be processed
CREATE TABLE message_queue_events
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_type   VARCHAR(50) NOT NULL,
    payload      CLOB        NOT NULL,
    status       VARCHAR(20) DEFAULT 'PENDING',
    created_at   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP,
    retry_count  INT         DEFAULT 0,
    max_retries  INT         DEFAULT 3
);

CREATE INDEX idx_event_type_status ON message_queue_events (event_type, status);

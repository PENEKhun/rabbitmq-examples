-- Sample users
INSERT INTO users (username, email, password, phone_number, full_name, created_at, active)
VALUES
    ('user1', 'user1@example.com', '...', '010-1234-1234', 'User One', DATEADD('DAY', -30, CURRENT_TIMESTAMP), true),
    ('user2', 'user2@example.com', '...', '010-1234-1235', 'User Two', DATEADD('DAY', -25, CURRENT_TIMESTAMP), true),
    ('user3', 'user3@example.com', '...', '010-1234-1236', 'User Three', DATEADD('DAY', -20, CURRENT_TIMESTAMP), true),
    ('user4', 'user4@example.com', '...', '010-1234-1237', 'User Four', DATEADD('DAY', -15, CURRENT_TIMESTAMP), true),
    ('user5', 'user5@example.com', '...', '010-1234-1238', 'User Five', DATEADD('DAY', -10, CURRENT_TIMESTAMP), true);

-- Sample email notifications
INSERT INTO email_notifications (user_id, email_type, subject, content, sent_at, status)
VALUES
    (1, 'WELCOME', 'Welcome to our service!', 'Thank you for registering with our service.', DATEADD('DAY', -30, CURRENT_TIMESTAMP), 'SENT'),
    (2, 'WELCOME', 'Welcome to our service!', 'Thank you for registering with our service.', DATEADD('DAY', -25, CURRENT_TIMESTAMP), 'SENT'),
    (3, 'WELCOME', 'Welcome to our service!', 'Thank you for registering with our service.', DATEADD('DAY', -20, CURRENT_TIMESTAMP), 'SENT'),
    (4, 'WELCOME', 'Welcome to our service!', 'Thank you for registering with our service.', DATEADD('DAY', -15, CURRENT_TIMESTAMP), 'SENT'),
    (5, 'WELCOME', 'Welcome to our service!', 'Thank you for registering with our service.', DATEADD('DAY', -10, CURRENT_TIMESTAMP), 'SENT');

-- Sample coupons
INSERT INTO coupons (code, description, discount_amount, discount_percentage, valid_from, valid_until, is_active, coupon_type)
VALUES
    ('WELCOME10', 'Welcome discount 10%', NULL, 10, DATEADD('DAY', -60, CURRENT_TIMESTAMP), DATEADD('DAY', 30, CURRENT_TIMESTAMP), true, 'EVENT'),
    ('WELCOME20', 'Welcome discount 20%', NULL, 20, DATEADD('DAY', -60, CURRENT_TIMESTAMP), DATEADD('DAY', 30, CURRENT_TIMESTAMP), true, 'EVENT'),
    ('SPECIAL5', 'Special discount $5', 5.00, NULL, DATEADD('DAY', -60, CURRENT_TIMESTAMP), DATEADD('DAY', 30, CURRENT_TIMESTAMP), true, 'EVENT'),
    ('SPECIAL10', 'Special discount $10', 10.00, NULL, DATEADD('DAY', -60, CURRENT_TIMESTAMP), DATEADD('DAY', 30, CURRENT_TIMESTAMP), true, 'EVENT'),
    ('BIRTHDAY15', 'Birthday discount 15%', NULL, 15, DATEADD('DAY', -60, CURRENT_TIMESTAMP), DATEADD('DAY', 30, CURRENT_TIMESTAMP), true, 'EVENT');

-- Sample user coupons
INSERT INTO user_coupons (user_id, coupon_id, issued_at, used_at, is_used)
VALUES
    (1, 1, DATEADD('DAY', -30, CURRENT_TIMESTAMP), NULL, false),
    (2, 2, DATEADD('DAY', -25, CURRENT_TIMESTAMP), NULL, false),
    (3, 3, DATEADD('DAY', -20, CURRENT_TIMESTAMP), NULL, false),
    (4, 4, DATEADD('DAY', -15, CURRENT_TIMESTAMP), NULL, false),
    (5, 5, DATEADD('DAY', -10, CURRENT_TIMESTAMP), NULL, false);

-- Sample user statistics
INSERT INTO user_statistics (date, total_users, new_users, active_users, updated_at)
VALUES
    (DATEADD('DAY', -30, CURRENT_DATE), 1, 1, 1, DATEADD('DAY', -30, CURRENT_TIMESTAMP)),
    (DATEADD('DAY', -25, CURRENT_DATE), 2, 1, 2, DATEADD('DAY', -25, CURRENT_TIMESTAMP)),
    (DATEADD('DAY', -20, CURRENT_DATE), 3, 1, 3, DATEADD('DAY', -20, CURRENT_TIMESTAMP)),
    (DATEADD('DAY', -15, CURRENT_DATE), 4, 1, 4, DATEADD('DAY', -15, CURRENT_TIMESTAMP)),
    (DATEADD('DAY', -10, CURRENT_DATE), 5, 1, 5, DATEADD('DAY', -10, CURRENT_TIMESTAMP));

-- Sample message queue events
INSERT INTO message_queue_events (event_type, payload, status, created_at, processed_at, retry_count)
VALUES
    ('SEND_WELCOME_EMAIL', '{"userId": 1, "emailType": "WELCOME"}', 'PROCESSED', DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -30, CURRENT_TIMESTAMP), 0),
    ('ISSUE_WELCOME_COUPON', '{"userId": 1, "couponId": 1}', 'PROCESSED', DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -30, CURRENT_TIMESTAMP), 0),
    ('UPDATE_USER_STATISTICS', '{"date": "2025-06-06", "newUsers": 1}', 'PROCESSED', DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -30, CURRENT_TIMESTAMP), 0),
    ('SEND_WELCOME_EMAIL', '{"userId": 2, "emailType": "WELCOME"}', 'PROCESSED', DATEADD('DAY', -25, CURRENT_TIMESTAMP), DATEADD('DAY', -25, CURRENT_TIMESTAMP), 0),
    ('ISSUE_WELCOME_COUPON', '{"userId": 2, "couponId": 2}', 'PROCESSED', DATEADD('DAY', -25, CURRENT_TIMESTAMP), DATEADD('DAY', -25, CURRENT_TIMESTAMP), 0),
    ('UPDATE_USER_STATISTICS', '{"date": "2025-06-11", "newUsers": 1}', 'PROCESSED', DATEADD('DAY', -25, CURRENT_TIMESTAMP), DATEADD('DAY', -25, CURRENT_TIMESTAMP), 0),
    ('SEND_WELCOME_EMAIL', '{"userId": 3, "emailType": "WELCOME"}', 'PROCESSED', DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', -20, CURRENT_TIMESTAMP), 0),
    ('ISSUE_WELCOME_COUPON', '{"userId": 3, "couponId": 3}', 'PROCESSED', DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', -20, CURRENT_TIMESTAMP), 0),
    ('UPDATE_USER_STATISTICS', '{"date": "2025-06-16", "newUsers": 1}', 'PROCESSED', DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', -20, CURRENT_TIMESTAMP), 0),
    ('SEND_WELCOME_EMAIL', '{"userId": 4, "emailType": "WELCOME"}', 'PROCESSED', DATEADD('DAY', -15, CURRENT_TIMESTAMP), DATEADD('DAY', -15, CURRENT_TIMESTAMP), 0),
    ('ISSUE_WELCOME_COUPON', '{"userId": 4, "couponId": 4}', 'PROCESSED', DATEADD('DAY', -15, CURRENT_TIMESTAMP), DATEADD('DAY', -15, CURRENT_TIMESTAMP), 0),
    ('UPDATE_USER_STATISTICS', '{"date": "2025-06-21", "newUsers": 1}', 'PROCESSED', DATEADD('DAY', -15, CURRENT_TIMESTAMP), DATEADD('DAY', -15, CURRENT_TIMESTAMP), 0),
    ('SEND_WELCOME_EMAIL', '{"userId": 5, "emailType": "WELCOME"}', 'PROCESSED', DATEADD('DAY', -10, CURRENT_TIMESTAMP), DATEADD('DAY', -10, CURRENT_TIMESTAMP), 0),
    ('ISSUE_WELCOME_COUPON', '{"userId": 5, "couponId": 5}', 'PROCESSED', DATEADD('DAY', -10, CURRENT_TIMESTAMP), DATEADD('DAY', -10, CURRENT_TIMESTAMP), 0),
    ('UPDATE_USER_STATISTICS', '{"date": "2025-06-26", "newUsers": 1}', 'PROCESSED', DATEADD('DAY', -10, CURRENT_TIMESTAMP), DATEADD('DAY', -10, CURRENT_TIMESTAMP), 0);

-- Sample pending events
INSERT INTO message_queue_events (event_type, payload, status, created_at, retry_count)
VALUES
    ('SEND_WELCOME_EMAIL', '{"userId": 6, "emailType": "WELCOME"}', 'PENDING', CURRENT_TIMESTAMP, 0),
    ('ISSUE_WELCOME_COUPON', '{"userId": 6, "couponId": 1}', 'PENDING', CURRENT_TIMESTAMP, 0),
    ('UPDATE_USER_STATISTICS', '{"date": "2025-07-06", "newUsers": 1}', 'PENDING', CURRENT_TIMESTAMP, 0);

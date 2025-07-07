-- Sample users
INSERT INTO users (username, email, password, phone_number, full_name, created_at, active)
VALUES
    ('user1', 'user1@example.com', '...', '010-1234-1234', 'User One', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 30 DAY), 1),
    ('user2', 'user2@example.com', '...', '010-1234-1235', 'User Two', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 25 DAY), 1),
    ('user3', 'user3@example.com', '...', '010-1234-1236', 'User Three', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 20 DAY), 1),
    ('user4', 'user4@example.com', '...', '010-1234-1237', 'User Four', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 15 DAY), 1),
    ('user5', 'user5@example.com', '...', '010-1234-1238', 'User Five', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 10 DAY), 1);

-- Sample email notifications
INSERT INTO email_notifications (user_id, email_type, subject, content, sent_at, status)
VALUES
    (1, 'WELCOME', 'Welcome to our service!', 'Thank you for registering with our service.', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 30 DAY), 'SENT'),
    (2, 'WELCOME', 'Welcome to our service!', 'Thank you for registering with our service.', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 25 DAY), 'SENT'),
    (3, 'WELCOME', 'Welcome to our service!', 'Thank you for registering with our service.', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 20 DAY), 'SENT'),
    (4, 'WELCOME', 'Welcome to our service!', 'Thank you for registering with our service.', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 15 DAY), 'SENT'),
    (5, 'WELCOME', 'Welcome to our service!', 'Thank you for registering with our service.', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 10 DAY), 'SENT');

-- Sample coupons
INSERT INTO coupons (code, description, discount_amount, discount_percentage, valid_from, valid_until, is_active, coupon_type)
VALUES
    ('WELCOME10', 'Welcome discount 10%', NULL, 10, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 60 DAY), DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 30 DAY), 1, 'EVENT'),
    ('WELCOME20', 'Welcome discount 20%', NULL, 20, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 60 DAY), DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 30 DAY), 1, 'EVENT'),
    ('SPECIAL5', 'Special discount $5', 5.00, NULL, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 60 DAY), DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 30 DAY), 1, 'EVENT'),
    ('SPECIAL10', 'Special discount $10', 10.00, NULL, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 60 DAY), DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 30 DAY), 1, 'EVENT'),
    ('BIRTHDAY15', 'Birthday discount 15%', NULL, 15, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 60 DAY), DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 30 DAY), 1, 'EVENT');

-- Sample user coupons
INSERT INTO user_coupons (user_id, coupon_id, issued_at, used_at, is_used)
VALUES
    (1, 1, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 30 DAY), NULL, 0),
    (2, 2, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 25 DAY), NULL, 0),
    (3, 3, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 20 DAY), NULL, 0),
    (4, 4, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 15 DAY), NULL, 0),
    (5, 5, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 10 DAY), NULL, 0);

-- Sample user statistics
INSERT INTO user_statistics (date, total_users, new_users, active_users, updated_at)
VALUES
    (DATE_SUB(CURDATE(), INTERVAL 30 DAY), 1, 1, 1, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 30 DAY)),
    (DATE_SUB(CURDATE(), INTERVAL 25 DAY), 2, 1, 2, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 25 DAY)),
    (DATE_SUB(CURDATE(), INTERVAL 20 DAY), 3, 1, 3, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 20 DAY)),
    (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 4, 1, 4, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 15 DAY)),
    (DATE_SUB(CURDATE(), INTERVAL 10 DAY), 5, 1, 5, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 10 DAY));

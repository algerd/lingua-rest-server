INSERT INTO authorities (authority) VALUES ('ADMIN'), ('USER');

-- admin:password
INSERT INTO users (username, password, mail, ip, account_non_expired, account_non_locked, credentials_non_expired, enabled)
    VALUES ('admin', '$2a$10$x0k/yA5qN8SP8JD5CEN.6elEBFxVVHeKZTdyv.RPra4jzRR5SlKSC', 'admin@gmail.com', '0.0.0.1', TRUE, TRUE, TRUE, TRUE);
INSERT INTO users_authorities (users_id, authorities_id) VALUES (1, 1), (1, 2);

-- user:green
/*
INSERT INTO users (username, password, mail, ip, account_non_expired, account_non_locked, credentials_non_expired, enabled)
    VALUES ('user', '$2a$10$vacuqbDw9I7rr6RRH8sByuktOzqTheQMfnK3XCT2WlaL7vt/3AMby', 'user@gmail.com', '0.0.0.1', TRUE, TRUE, TRUE, TRUE);
INSERT INTO users_authorities (users_id, authorities_id) VALUES (2, 2);
*/
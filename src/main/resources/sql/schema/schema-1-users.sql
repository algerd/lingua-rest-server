DROP TABLE IF EXISTS users_authorities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    mail VARCHAR(64) NOT NULL,
    ip VARCHAR(32) NOT NULL,
    account_non_expired BOOLEAN NOT NULL,
    account_non_locked BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    enabled BOOLEAN NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
    last_visited TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_users_username(username),
    UNIQUE KEY uk_users_mail(mail)
    /*UNIQUE KEY uk_mail(ip)*/
);

CREATE TABLE IF NOT EXISTS authorities (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    authority VARCHAR(64) NOT NULL,
    UNIQUE KEY uk_authorities_authority(authority)
);

CREATE TABLE IF NOT EXISTS users_authorities (
    users_id BIGINT UNSIGNED NOT NULL,
    authorities_id BIGINT UNSIGNED NOT NULL,
    UNIQUE KEY uk_users_authorities_users_id_authorities_id (users_id, authorities_id),
    CONSTRAINT fk_users_authorities_users_id FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_authorities_authorities_id FOREIGN KEY (authorities_id) REFERENCES authorities(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS verification_token (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(64) NOT NULL,
    users_id BIGINT UNSIGNED NOT NULL,
    expiry_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_verification_token_users_id FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE
);
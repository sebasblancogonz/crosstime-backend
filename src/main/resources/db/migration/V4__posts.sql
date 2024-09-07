CREATE TABLE IF NOT EXISTS posts (
    id varbinary(36) PRIMARY KEY,
    user_id varbinary(36) NOT NULL,
    content varchar(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
    id varbinary(36) PRIMARY KEY,
    post_id varbinary(36) NOT NULL,
    user_id varbinary(36) NOT NULL,
    content varchar(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS likes (
    id varbinary(36) PRIMARY KEY,
    post_id varbinary(36) NOT NULL,
    user_id varbinary(36) NOT NULL,
    comment_id varbinary(36),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
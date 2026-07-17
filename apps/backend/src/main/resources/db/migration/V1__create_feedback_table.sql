CREATE TABLE feedbacks
(
    id         UUID PRIMARY KEY,
    content    TEXT        NOT NULL,
    source     VARCHAR(50) NOT NULL,
    rating     INTEGER,
    created_at TIMESTAMP   NOT NULL
);
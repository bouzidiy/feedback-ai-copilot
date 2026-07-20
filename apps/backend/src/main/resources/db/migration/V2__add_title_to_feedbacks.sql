ALTER TABLE feedbacks
    ADD COLUMN title VARCHAR(255) NOT NULL DEFAULT 'Untitled feedback';

ALTER TABLE feedbacks
    ALTER COLUMN title DROP DEFAULT;

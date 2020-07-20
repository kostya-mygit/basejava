DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS resume;

CREATE TABLE resume
(
    uuid      CHAR(36) PRIMARY KEY,
    full_name TEXT NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL PRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    FOREIGN KEY (resume_uuid) REFERENCES resume (uuid) ON DELETE CASCADE
);
CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);
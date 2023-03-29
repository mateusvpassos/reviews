CREATE TABLE subject (
    id bigint(10) NOT NULL AUTO_INCREMENT,
    title varchar(100) NOT NULL,
    description varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UK_subjectid (id)
);
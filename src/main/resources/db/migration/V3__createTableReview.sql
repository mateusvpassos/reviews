CREATE TABLE review (
    id bigint(10) NOT NULL AUTO_INCREMENT,
    user_id varchar(20) NOT NULL,
    subject_id bigint(10) NOT NULL,
    description varchar(500) NOT NULL,
    rate int(2) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UK_reviewid (id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (subject_id) REFERENCES subject(id)
);
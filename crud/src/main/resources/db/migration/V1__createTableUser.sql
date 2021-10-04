CREATE TABLE user (
    id varchar(20) NOT NULL,
    name varchar(200) NOT NULL,
    email varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UK_userid (id),
    UNIQUE KEY UK_useremail (email)
);
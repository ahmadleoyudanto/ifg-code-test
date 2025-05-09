CREATE TABLE dead_letter
(
    id   INT NOT NULL AUTO_INCREMENT,
    topic TEXT,
    message TEXT,
    primary key (id)
);
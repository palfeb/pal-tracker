CREATE TABLE time_entries(
    id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    project_id BIGINT(20),
    user_id BIGINT(20),
    date DATE,
    hours INT(20)
)
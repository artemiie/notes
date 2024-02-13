CREATE TABLE IF NOT EXISTS USERS
(
    ID BIGSERIAL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    USERNAME VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS USERS_ROLES
(
    USER_ID BIGINT NOT NULL,
    ROLE VARCHAR(255) NOT NULL,
    PRIMARY KEY (USER_ID, ROLE),
    CONSTRAINT FK_USERS_ROLES_USERS FOREIGN KEY (USER_ID) REFERENCES USERS (ID) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE SEQUENCE USER_SEQ
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 1000
    CACHE 10;
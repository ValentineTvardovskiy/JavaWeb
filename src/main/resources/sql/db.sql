CREATE TABLE ROLE (
  NAME VARCHAR(10) PRIMARY KEY
)

CREATE TABLE USER_TO_ROLE (
  FK_USER_ID BIGINT NOT NULL,
  FK_ROLE_ID VARCHAR(10) NOT NULL
  PRIMARY KEY(FK_USER_ID, FK_ROLE_ID)
  CONSTRAINT FK_UTR_USERS FOREIGN KEY (FK_USER_ID)
    REFERENCES USERS(ID),
  CONSTRAINT FK_UTR_ROLES FOREIGN KEY (FK_ROLE_ID)
    REFERENCES ROLE(NAME),
)
DROP TABLE IF EXISTS USER_INFO;
DROP TABLE IF EXISTS AD_INFO;
DROP TABLE IF EXISTS AD_JOIN_HISTORY;

-- 유저 테이블
CREATE TABLE USER_INFO (
                           USER_ID VARCHAR(50) PRIMARY KEY,
                           USER_REMAINING_COUNT INT NOT NULL
);
DROP TABLE IF EXISTS AD_JOIN_HISTORY;
DROP TABLE IF EXISTS AD_INFO;

CREATE TABLE AD_INFO (
                         AD_ID VARCHAR(255) PRIMARY KEY,
                         AD_NAME VARCHAR(255) NOT NULL UNIQUE,
                         AD_DESCRIPTION TEXT NOT NULL,
                         AD_REWARD_AMOUNT INT NOT NULL,
                         AD_REMAINING_COUNT SMALLINT NOT NULL,
                         AD_IMAGE_URL VARCHAR(255) NOT NULL,
                         START_DATE TIMESTAMP NOT NULL,
                         END_DATE TIMESTAMP NOT NULL
);

CREATE TABLE AD_JOIN_HISTORY (
                                 USER_ID VARCHAR(255) NOT NULL,
                                 AD_ID VARCHAR(255) NOT NULL,
                                 JOIN_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                 AD_NAME VARCHAR(255) NOT NULL,
                                 REWARD_AMOUNT INT NOT NULL,
                                 PRIMARY KEY (USER_ID, AD_ID, JOIN_AT)
);


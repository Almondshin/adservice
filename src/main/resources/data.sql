INSERT INTO AD_INFO (AD_ID, AD_NAME, AD_DESCRIPTION, AD_REWARD_AMOUNT, AD_REMAINING_COUNT, AD_IMAGE_URL, START_DATE, END_DATE)
VALUES
    ('ad2', '광고2', '설명 2', 200, 3, 'https://example.com/image2.jpg', '2025-02-22 00:00:00', '2025-03-22 23:59:59'),
    ('ad3', '광고3', '설명 3', 150, 10, 'https://example.com/image3.jpg', '2025-02-23 00:00:00', '2025-03-23 23:59:59'),
    ('ad4', '광고4', '설명 4', 250, 8, 'https://example.com/image4.jpg', '2025-02-24 00:00:00', '2025-03-24 23:59:59'),
    ('ad5', '광고5', '설명 5', 180, 7, 'https://example.com/image5.jpg', '2025-02-25 00:00:00', '2025-03-25 23:59:59'),
    ('ad6', '광고6', '설명 6', 90, 6, 'https://example.com/image6.jpg', '2025-02-26 00:00:00', '2025-03-26 23:59:59'),
    ('ad7', '광고7', '설명 7', 120, 9, 'https://example.com/image7.jpg', '2025-02-27 00:00:00', '2025-03-27 23:59:59'),
    ('ad8', '광고8', '설명 8', 300, 5, 'https://example.com/image8.jpg', '2025-02-28 00:00:00', '2025-03-28 23:59:59'),
    ('ad9', '광고9', '설명 9', 140, 4, 'https://example.com/image9.jpg', '2025-03-01 00:00:00', '2025-03-29 23:59:59'),
    ('ad10', '광고10', '설명 10', 160, 2, 'https://example.com/image10.jpg', '2025-03-02 00:00:00', '2025-03-30 23:59:59');

-- 100개 추가 광고
INSERT INTO AD_INFO (AD_ID, AD_NAME, AD_DESCRIPTION, AD_REWARD_AMOUNT, AD_REMAINING_COUNT, AD_IMAGE_URL, START_DATE, END_DATE)
SELECT
    'ad' || (10 + X),
    '광고' || (10 + X),
    '설명 ' || (10 + X),
    (FLOOR(RAND() * 500) + 50),
    (FLOOR(RAND() * 10) + 1),
    'https://example.com/image' || (10 + X) || '.jpg',
    '2025-03-' ||
    CASE
        WHEN X % 31 = 0 THEN '31' -- 3월은 31일까지
        ELSE LPAD((X % 31) + 1, 2, '0')  -- 1~31일만 생성
        END || ' 00:00:00',
    '2025-04-' ||
    CASE
        WHEN X % 30 = 0 THEN '30' -- 4월은 30일까지
        ELSE LPAD((X % 30) + 1, 2, '0')  -- 1~30일만 생성
        END || ' 23:59:59'
FROM SYSTEM_RANGE(1, 100) AS T(X);




INSERT INTO AD_PARTICIPATION_HISTORY (USER_ID, AD_ID, PARTICIPATION_TIME, AD_NAME, REWARD_AMOUNT)
VALUES
    ('user1', 'ad2', '2025-02-25 12:00:00', '광고2', 200),
    ('user2', 'ad2', '2025-02-26 13:00:00', '광고2', 200),
    ('user1', 'ad2', '2025-02-27 14:00:00', '광고2', 200),
    ('user3', 'ad2', '2025-02-28 15:00:00', '광고2', 200),
    ('user4', 'ad3', '2025-02-28 16:00:00', '광고3', 150),
    ('user5', 'ad3', '2025-03-01 17:00:00', '광고3', 150),
    ('user6', 'ad4', '2025-03-02 18:00:00', '광고4', 250),
    ('user7', 'ad5', '2025-03-03 19:00:00', '광고5', 180),
    ('user8', 'ad6', '2025-03-04 20:00:00', '광고6', 90),
    ('user9', 'ad7', '2025-03-05 21:00:00', '광고7', 120);

-- 100개 추가 참여 이력
INSERT INTO AD_PARTICIPATION_HISTORY (USER_ID, AD_ID, PARTICIPATION_TIME, AD_NAME, REWARD_AMOUNT)
SELECT
    'user' || (10 + X),
    'ad' || CAST(FLOOR(RAND() * 50) + 1 AS INT),
    '2025-02-' ||
    CASE
        WHEN X BETWEEN 1 AND 28 THEN LPAD(X, 2, '0')
        ELSE '28'
        END || ' ' || CAST(FLOOR(RAND() * 24) AS INT) || ':00:00',
    '광고' || CAST(FLOOR(RAND() * 50) + 1 AS INT),
    (FLOOR(RAND() * 500) + 50)
FROM SYSTEM_RANGE(1, 100) AS T(X);


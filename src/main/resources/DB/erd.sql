-- 을지대 교수 정보 플랫폼 ERD (ERDCloud 임포트용 DDL, MySQL 기준)

CREATE TABLE `users` (
    `user_id`    BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username`   VARCHAR(100) NOT NULL,
    `password`   VARCHAR(255) NOT NULL,
    `name`       VARCHAR(50) NOT NULL,
    `nickname`   VARCHAR(50),
    `phone`      VARCHAR(20),
    `point`      INT NOT NULL DEFAULT 0,
    `role`       VARCHAR(20) NOT NULL,
    `created_at` DATETIME NOT NULL,
    UNIQUE KEY `uk_users_username` (`username`)
);

CREATE TABLE `refresh_tokens` (
    `refresh_token_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id`          BIGINT NOT NULL,
    `token`            VARCHAR(512) NOT NULL,
    `created_at`       DATETIME NOT NULL,
    UNIQUE KEY `uk_refresh_tokens_user_id` (`user_id`),
    CONSTRAINT `fk_refresh_tokens_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `department` (
    `department_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`          VARCHAR(100) NOT NULL
);

CREATE TABLE `professor` (
    `professor_id`  BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`          VARCHAR(50) NOT NULL,
    `department_id` BIGINT NOT NULL,
    CONSTRAINT `fk_professor_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
);

CREATE TABLE `subject` (
    `subject_id`   BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`         VARCHAR(100) NOT NULL,
    `professor_id` BIGINT NOT NULL,
    CONSTRAINT `fk_subject_professor` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`professor_id`)
);

CREATE TABLE `enrollment` (
    `enrollment_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id`       BIGINT NOT NULL,
    `subject_id`    BIGINT NOT NULL,
    `semester`      VARCHAR(20) NOT NULL,
    `created_at`    DATETIME NOT NULL,
    UNIQUE KEY `uk_enrollment_user_subject_semester` (`user_id`, `subject_id`, `semester`),
    CONSTRAINT `fk_enrollment_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `fk_enrollment_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`)
);

CREATE TABLE `tag` (
    `tag_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`   VARCHAR(50) NOT NULL,
    UNIQUE KEY `uk_tag_name` (`name`)
);

CREATE TABLE `tag_click` (
    `tag_click_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id`      BIGINT NOT NULL,
    `professor_id` BIGINT NOT NULL,
    `tag_id`       BIGINT NOT NULL,
    `created_at`   DATETIME NOT NULL,
    UNIQUE KEY `uk_tag_click_user_professor_tag` (`user_id`, `professor_id`, `tag_id`),
    CONSTRAINT `fk_tag_click_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `fk_tag_click_professor` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`professor_id`),
    CONSTRAINT `fk_tag_click_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
);

CREATE TABLE `review` (
    `review_id`    BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id`      BIGINT NOT NULL,
    `professor_id` BIGINT NOT NULL,
    `content`      TEXT NOT NULL,
    `created_at`   DATETIME NOT NULL,
    CONSTRAINT `fk_review_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `fk_review_professor` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`professor_id`)
);

CREATE TABLE `review_report` (
    `review_report_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `review_id`         BIGINT NOT NULL,
    `reporter_id`       BIGINT NOT NULL,
    `reason`            TEXT NOT NULL,
    `created_at`        DATETIME NOT NULL,
    UNIQUE KEY `uk_review_report_review_reporter` (`review_id`, `reporter_id`),
    CONSTRAINT `fk_review_report_review` FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`),
    CONSTRAINT `fk_review_report_reporter` FOREIGN KEY (`reporter_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `exam_archive` (
    `exam_archive_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id`         BIGINT NOT NULL,
    `professor_id`    BIGINT NOT NULL,
    `title`           VARCHAR(200) NOT NULL,
    `content`         TEXT NOT NULL,
    `created_at`      DATETIME NOT NULL,
    CONSTRAINT `fk_exam_archive_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `fk_exam_archive_professor` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`professor_id`)
);

CREATE TABLE `exam_archive_view` (
    `exam_archive_view_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id`              BIGINT NOT NULL,
    `exam_archive_id`      BIGINT NOT NULL,
    `viewed_at`            DATETIME NOT NULL,
    UNIQUE KEY `uk_exam_archive_view_user_archive` (`user_id`, `exam_archive_id`),
    CONSTRAINT `fk_exam_archive_view_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `fk_exam_archive_view_archive` FOREIGN KEY (`exam_archive_id`) REFERENCES `exam_archive` (`exam_archive_id`)
);

CREATE TABLE `point_history` (
    `point_history_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id`           BIGINT NOT NULL,
    `amount`            INT NOT NULL,
    `reason`            VARCHAR(20) NOT NULL,
    `created_at`        DATETIME NOT NULL,
    CONSTRAINT `fk_point_history_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

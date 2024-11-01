-- user_service & auth_service
CREATE TABLE `flowright`.`users` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `refresh_token` VARCHAR(255),
);

-- workspace_service
CREATE TABLE `flowright`.`workspaces` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `owner_id` INT NOT NULL,
    FOREIGN KEY (`owner_id`) REFERENCES `users`(`id`)
);

CREATE TABLE `flowright`.`invites` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(100) NOT NULL,
    `otp` VARCHAR(10) NOT NULL,
    `role_id` INT NOT NULL,
    `status` VARCHAR(50) NOT NULL, -- pending, accepted, rejected
    `expires_at` DATETIME NOT NULL,
    `workspace_id` INT NOT NULL,
    FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`),
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`)
);

-- member_service
CREATE TABLE `flowright`.`members` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    `workspace_id` INT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`),
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`)
);

CREATE TABLE `flowright`.`roles` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `workspace_id` INT NOT NULL,
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`),
    `is_default` BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE `flowright`.`permissions` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
);

CREATE TABLE `flowright`.`role_permissions` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `role_id` INT NOT NULL,
    `permission_id` INT NOT NULL,
    FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`),
    FOREIGN KEY (`permission_id`) REFERENCES `permissions`(`id`)
);

CREATE TABLE `flowright`.`specializations` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `workspace_id` INT NOT NULL,
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`)
);

CREATE TABLE `flowright`.`members_specializations` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `member_id` INT NOT NULL,
    `specialization_id` INT NOT NULL,
    `level` VARCHAR(50) NOT NULL, -- junior, middle, senior
    `year_of_experience` INT NOT NULL,
    FOREIGN KEY (`member_id`) REFERENCES `members`(`id`),
    FOREIGN KEY (`specialization_id`) REFERENCES `specializations`(`id`)
);

-- team_service
CREATE TABLE `flowright`.`teams` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `workspace_id` INT NOT NULL,
    `leader_id` INT NOT NULL,
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`),
    FOREIGN KEY (`leader_id`) REFERENCES `members`(`id`)
);

CREATE TABLE `flowright`.`team_members` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `team_id` INT NOT NULL,
    `member_id` INT NOT NULL,
    FOREIGN KEY (`team_id`) REFERENCES `teams`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members`(`id`)
);

-- project_service
CREATE TABLE `flowright`.`projects` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `workspace_id` INT NOT NULL,
    `owner_id` INT NOT NULL,
    `start_date` DATETIME NOT NULL,
    `end_date` DATETIME NOT NULL,
    `status` VARCHAR(50) NOT NULL, -- todo, in_progress, done
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`),
    FOREIGN KEY (`owner_id`) REFERENCES `members`(`id`)
);

CREATE TABLE `flowright`.`project_members` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `project_id` INT NOT NULL,
    `member_id` INT NOT NULL,
    FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members`(`id`)
);

CREATE TABLE `flowright`.`project_logs` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `project_id` INT NOT NULL,
    `log_title` VARCHAR(50) NOT NULL,
    `log_description` VARCHAR(255) NOT NULL,
    `log_date` DATETIME NOT NULL,
    `log_type` VARCHAR(50) NOT NULL, -- start, stop, pause, resume
    FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`)
);

-- task_service
CREATE TABLE `flowright`.`tasks` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `owner_id` INT NOT NULL,
    `priority` VARCHAR(50) NOT NULL, -- low, medium, high
    `start_date` DATETIME NOT NULL,
    `end_date` DATETIME NOT NULL,
    `status` VARCHAR(50) NOT NULL, -- todo, in_progress, done
    `group_task_id` INT NULL,
    FOREIGN KEY (`owner_id`) REFERENCES `members`(`id`),
    FOREIGN KEY (`group_task_id`) REFERENCES `group_tasks`(`id`)
);

CREATE TABLE `flowright`.`task_comments` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `task_id` INT NOT NULL,
    `member_id` INT NOT NULL,
    `comment` TEXT NOT NULL,
    `links` JSON NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`task_id`) REFERENCES `tasks`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members`(`id`)
);

-- one task can have multiple assignees (members)
CREATE TABLE `flowright`.`task_assignments` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `task_id` INT NOT NULL,
    `member_id` INT NOT NULL,
    FOREIGN KEY (`task_id`) REFERENCES `tasks`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members`(`id`)
);

CREATE TABLE `flowright`.`task_logs` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `task_id` INT NOT NULL,
    `log_title` VARCHAR(50) NOT NULL,
    `log_description` VARCHAR(255) NOT NULL,
    `log_date` DATETIME NOT NULL,
    `log_type` VARCHAR(50) NOT NULL, -- start, stop, pause, resume
    FOREIGN KEY (`task_id`) REFERENCES `tasks`(`id`)
);

CREATE TABLE `flowright`.`group_tasks` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `task_id` INT NOT NULL,
    `team_id` INT NOT NULL,
    `project_id` INT NOT NULL,
    FOREIGN KEY (`task_id`) REFERENCES `tasks`(`id`),
    FOREIGN KEY (`team_id`) REFERENCES `teams`(`id`),
    FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`)
);





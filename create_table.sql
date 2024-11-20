-- user_service & auth_service
CREATE TABLE `flowright`.`users` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `refresh_token` VARCHAR(255)
);  

-- workspace_service
CREATE TABLE `flowright`.`workspaces` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `owner_id` INT NOT NULL
);  

CREATE TABLE `flowright`.`workspace_members` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `user_id` BINARY(16) NOT NULL,
    `workspace_id` BINARY(16) NOT NULL
);

CREATE TABLE `flowright`.`invites` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `workspace_id` BINARY(16) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `role_id` BINARY(16) NOT NULL,
    `token` VARCHAR(10) NOT NULL,
    `status` VARCHAR(50) NOT NULL, -- pending, accepted, rejected
    `expires_at` DATETIME NOT NULL
);

-- member_service
CREATE TABLE `flowright`.`permissions` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255)
);

CREATE TABLE `flowright`.`roles` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `workspace_id` BINARY(16) NOT NULL,
    `is_default` BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE `flowright`.`role_permissions` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `role_id` BINARY(16) NOT NULL,
    `permission_id` BINARY(16) NOT NULL
);

CREATE TABLE `flowright`.`members` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `user_id` INT NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `username` VARCHAR(50) NOT NULL,
    `role_id` BINARY(16) NOT NULL,
    `workspace_id` BINARY(16) NOT NULL
);

CREATE TABLE `flowright`.`specializations` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `workspace_id` BINARY(16) NOT NULL
);

CREATE TABLE `flowright`.`members_specializations` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `member_id` BINARY(16) NOT NULL,
    `specialization_id` BINARY(16) NOT NULL,
    `level` VARCHAR(50) NOT NULL, -- junior, middle, senior
    `years_of_experience` INT NOT NULL,
    `is_default` BOOLEAN NOT NULL DEFAULT FALSE
);

-- team_service
CREATE TABLE `flowright`.`teams` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `leader_id` INT NOT NULL,   
    `workspace_id` BINARY(16) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `type` VARCHAR(50) NOT NULL, -- development, design, research, analysis, management
    `status` VARCHAR(50) NOT NULL -- active, inactive
);

CREATE TABLE `flowright`.`team_members` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `team_id` BINARY(16) NOT NULL,
    `member_id` BINARY(16) NOT NULL
);

-- project_service
CREATE TABLE `flowright`.`projects` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `workspace_id` BINARY(16) NOT NULL,
    `owner_id` INT NOT NULL,
    `creator_id` INT NOT NULL,
    `start_date` DATETIME NOT NULL,
    `end_date` DATETIME NULL,
    `status` VARCHAR(50) NOT NULL -- todo, in_progress, done, overdue, overdone
);

-- task_service
CREATE TABLE `flowright`.`task_groups` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `project_id` BINARY(16) NOT NULL
);

CREATE TABLE `flowright`.`tasks` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `creator_id` INT NOT NULL,
    `project_id` BINARY(16) NULL,
    `priority` VARCHAR(50) NOT NULL, -- low, medium, high
    `team_id` BINARY(16) NOT NULL,
    `start_date` DATETIME NOT NULL,
    `end_date` DATETIME NULL,
    `status` VARCHAR(50) NOT NULL, -- todo, in_progress, done, cancel, overdue, overdue_done, hidden
    `task_group_id` BINARY(16) NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `flowright`.`mini_tasks` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `task_id` BINARY(16) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `status` VARCHAR(50) NOT NULL, -- in_progress, done
    `member_id` BINARY(16) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `flowright`.`task_assignments` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `task_id` BINARY(16) NOT NULL,
    `member_id` BINARY(16) NOT NULL,
    `team_id` BINARY(16) NOT NULL
);

CREATE TABLE `flowright`.`task_links` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `task_id` BINARY(16) NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `link` VARCHAR(255) NOT NULL
);

CREATE TABLE `flowright`.`task_comments` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `task_id` BINARY(16) NOT NULL,
    `member_id` BINARY(16) NOT NULL,    
    `comment` TEXT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `flowright`.`task_logs` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `task_id` BINARY(16) NOT NULL,  
    `log_title` VARCHAR(50) NOT NULL,
    `log_description` VARCHAR(255) NOT NULL,
    `log_date` DATETIME NOT NULL
);

-- Other Service
CREATE TABLE `flowright`.`notifications` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `workspace_id` BINARY(16) NOT NULL,
    `member_id` BINARY(16) NOT NULL,
    `uri` VARCHAR(255) NULL,
    `type` VARCHAR(50) NOT NULL, -- notification, message, task_comment, task_log, task_assignment, task_link, task_group
    `title` VARCHAR(255) NOT NULL,
    `detail` VARCHAR(255) NOT NULL,
    `is_read` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);





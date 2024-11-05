-- user_service & auth_service
CREATE TABLE `flowright`.`users` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `refresh_token` VARCHAR(255),   
);

-- user_service-example
-- table: users
-- id: 1, username: user1, email: user1@flowright.com, password: user1
-- id: 2, username: user2, email: user2@flowright.com, password: user2

-- workspace_service
CREATE TABLE `flowright`.`workspaces` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `owner_id` INT NOT NULL,
    FOREIGN KEY (`owner_id`) REFERENCES `users`(`id`)
);

CREATE TABLE `flowright`.`invites` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `workspace_id` INT NOT NULL,
    `user_id` INT NULL,
    `email` VARCHAR(100) NOT NULL,
    `token` VARCHAR(10) NOT NULL,
    `status` VARCHAR(50) NOT NULL, -- pending, accepted, rejected
    `expires_at` DATETIME NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`)
);

CREATE TABLE `flowright`.`workspace_sessions` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `workspace_id` INT NOT NULL,
    `member_id` INT NOT NULL,
    `last_accessed` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members`(`id`)
);

-- workspace_service-example
-- table: workspaces
-- id: 1, name: BlueLight Workspace, owner_id: 1
-- table: invites
-- id: 1, email: user2@flowright.com, otp: 123456, role_id: 1, status: pending, expires_at: 2024-12-31 23:59:59, workspace_id: 1

-- member_service
CREATE TABLE `flowright`.`permissions` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
);

CREATE TABLE `flowright`.`roles` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `workspace_id` INT NOT NULL,
    `is_default` BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`),
);

CREATE TABLE `flowright`.`role_permissions` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `role_id` INT NOT NULL,
    `permission_id` INT NOT NULL,
    FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`),
    FOREIGN KEY (`permission_id`) REFERENCES `permissions`(`id`)
);

CREATE TABLE `flowright`.`members` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `username` VARCHAR(50) NOT NULL,
    `role_id` INT NOT NULL,
    `workspace_id` INT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`),   
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`)
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
    `years_of_experience` INT NOT NULL,
    `is_default` BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (`member_id`) REFERENCES `members`(`id`),
    FOREIGN KEY (`specialization_id`) REFERENCES `specializations`(`id`)
);

-- member_service-example
-- table: permissions
-- id: 1, name: create_project, description: create a new project
-- id: 2, name: update_project, description: update a project
-- id: 3, name: delete_project, description: delete a project
-- id: 4, name: view_project, description: view a project
-- id: 5, name: invite_member, description: invite a new member to the workspace
-- id: 6, name: delete_member, description: delete a member from the workspace
-- id: 7, name: create_team, description: create a new team
-- id: 8, name: update_team, description: update a team
-- id: 9, name: delete_team, description: delete a team
-- id: 10, name: view_team, description: view a team

-- table: roles
-- id: 1, name: admin, workspace_id: 1
-- id: 2, name: manager, workspace_id: 1
-- id: 3, name: developer, workspace_id: 1

-- table: role_permissions
-- admin has all permissions
-- id: 1, role_id: 1, permission_id: 1
-- id: 2, role_id: 1, permission_id: 2
-- id: 3, role_id: 1, permission_id: 3
-- id: 4, role_id: 1, permission_id: 4
-- id: 5, role_id: 1, permission_id: 5
-- id: 6, role_id: 1, permission_id: 6
-- id: 7, role_id: 1, permission_id: 7
-- id: 8, role_id: 1, permission_id: 8
-- id: 9, role_id: 1, permission_id: 9
-- id: 10, role_id: 1, permission_id: 10

-- manager
-- id: 11, role_id: 2, permission_id: 2
-- id: 12, role_id: 2, permission_id: 4
-- id: 13, role_id: 2, permission_id: 5
-- id: 14, role_id: 2, permission_id: 6

-- developer
-- id: 15, role_id: 3, permission_id: 2

-- table: members
-- id: 1, user_id: 1, email: user1@flowright.com, username: user1, role_id: 1, workspace_id: 1
-- id: 2, user_id: 2, email: user2@flowright.com, username: user2, role_id: 3, workspace_id: 1

-- table: specializations
-- id: 1, name: workspace manager, workspace_id: 1
-- id: 2, name: software engineer, workspace_id: 1

-- table: members_specializations
-- id: 1, member_id: 1, specialization_id: 1, level: senior, years_of_experience: 10
-- id: 2, member_id: 2, specialization_id: 2, level: middle, years_of_experience: 5

-- team_service
CREATE TABLE `flowright`.`teams` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `leader_id` INT NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `workspace_id` INT NOT NULL,
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

CREATE TABLE `flowright`.`team_logs` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `team_id` INT NOT NULL,
    `log_title` VARCHAR(50) NOT NULL,
    `log_description` VARCHAR(255) NOT NULL,
    `log_date` DATETIME NOT NULL,
    FOREIGN KEY (`team_id`) REFERENCES `teams`(`id`)
);

-- project_service
CREATE TABLE `flowright`.`projects` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `workspace_id` INT NOT NULL,
    `owner_id` INT NOT NULL,
    `creator_id` INT NOT NULL,
    `start_date` DATETIME NOT NULL,
    `end_date` DATETIME NULL,
    `status` VARCHAR(50) NOT NULL, -- todo, in_progress, done
    FOREIGN KEY (`workspace_id`) REFERENCES `workspaces`(`id`),
    FOREIGN KEY (`owner_id`) REFERENCES `members`(`id`),
    FOREIGN KEY (`creator_id`) REFERENCES `members`(`id`)
);

CREATE TABLE `flowright`.`project_assignment` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `project_id` INT NOT NULL,
    `member_id` INT NOT NULL,
    `team_id` INT NOT NULL,
    FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members`(`id`),
    FOREIGN KEY (`team_id`) REFERENCES `teams`(`id`)
);

CREATE TABLE `flowright`.`project_logs` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `project_id` INT NOT NULL,
    `log_title` VARCHAR(50) NOT NULL,
    `log_description` VARCHAR(255) NOT NULL,
    `log_date` DATETIME NOT NULL,
    FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`)
);

-- task_service
CREATE TABLE `flowright`.`tasks` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `creator_id` INT NOT NULL,
    `project_id` INT NULL,
    `priority` VARCHAR(50) NOT NULL, -- low, medium, high
    `start_date` DATETIME NOT NULL,
    `end_date` DATETIME NULL,
    `status` VARCHAR(50) NOT NULL, -- todo, in_progress, done
    `previous_task_id` INT NULL,
    `next_task_id` INT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`creator_id`) REFERENCES `members`(`id`),
    FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`),
    FOREIGN KEY (`previous_task_id`) REFERENCES `tasks`(`id`),
    FOREIGN KEY (`next_task_id`) REFERENCES `tasks`(`id`)
);

CREATE TABLE `flowright`.`task_assignments` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `task_id` INT NOT NULL,
    `member_id` INT NOT NULL,
    `group_id` INT NOT NULL,
    FOREIGN KEY (`task_id`) REFERENCES `tasks`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members`(`id`),
    FOREIGN KEY (`group_id`) REFERENCES `groups`(`id`)
);

CREATE TABLE `flowright`.`task_links` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `task_id` INT NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `link` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`task_id`) REFERENCES `tasks`(`id`)
);

CREATE TABLE `flowright`.`task_comments` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `task_id` INT NOT NULL,
    `member_id` INT NOT NULL,
    `comment` TEXT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`task_id`) REFERENCES `tasks`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members`(`id`)
);

CREATE TABLE `flowright`.`task_logs` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `task_id` INT NOT NULL,
    `log_title` VARCHAR(50) NOT NULL,
    `log_description` VARCHAR(255) NOT NULL,
    `log_date` DATETIME NOT NULL,
    FOREIGN KEY (`task_id`) REFERENCES `tasks`(`id`)
);





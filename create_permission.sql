CREATE TABLE `flowright`.`permissions` (
    `id` BINARY(16) PRIMARY KEY NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255)
);

-- workspace_management [admin]
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'update_workspace', 'This permission allows the user to update the workspace like update workspace name, etc.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'delete_workspace', 'This permission allows the user to delete the workspace.');

-- role_management [admin]
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'create_role', 'This permission allows the user to create a new role.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'update_role', 'This permission allows the user to update an existing role.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'delete_role', 'This permission allows the user to delete an existing role.');

-- specialization_management [admin]
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'create_specialization', 'This permission allows the user to create a new specialization.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'update_specialization', 'This permission allows the user to update an existing specialization.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'delete_specialization', 'This permission allows the user to delete an existing specialization.');

-- role_permission_management [admin]
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'assign_permission_to_role', 'This permission allows the user to assign a permission to a role.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'remove_permission_from_role', 'This permission allows the user to remove a permission from a role.');

-- member_role_management [admin]
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'assign_role_to_member', 'This permission allows the user to assign a role to a member.');

-- invite_member_to_workspace [admin]
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'invite_member_to_workspace', 'This permission allows the user to invite a member to the workspace.');

-- member_management
-- view_member_details [workspace] [team] [project] [task]
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'view_any_member_details_in_workspace', 'This permission allows the user to view the details of any member in the workspace.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'view_own_member_details_in_workspace', 'This permission allows the user to view the details of their own member in the workspace.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'view_any_member_details_in_team', 'This permission allows the user to view the details of any member in the team.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'view_own_member_details_in_team', 'This permission allows the user to view the details of their own member in the team.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'view_any_member_details_in_project', 'This permission allows the user to view the details of any member in the project.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'view_own_member_details_in_project', 'This permission allows the user to view the details of their own member in the project.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'view_any_member_details_in_task', 'This permission allows the user to view the details of any member in the task.');
INSERT INTO `flowright`.`permissions` (`id`, `name`, `description`) VALUES (UUID_TO_BIN(UUID()), 'view_own_member_details_in_task', 'This permission allows the user to view the details of their own member in the task.');




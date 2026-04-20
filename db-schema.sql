CREATE TABLE `users` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `full_name` varchar(150) NOT NULL,
  `email` varchar(150) UNIQUE NOT NULL,
  `phone` varchar(20) UNIQUE,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) COMMENT 'CITIZEN | SOLVER | ADMIN',
  `profile_image` text,
  `is_active` boolean DEFAULT true,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `citizens` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `user_id` bigint UNIQUE NOT NULL,
  `address` text,
  `city` varchar(100),
  `state` varchar(100),
  `pincode` varchar(10)
);

CREATE TABLE `organizations` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `type` varchar(50) COMMENT 'VMC | PRIVATE | NGO',
  `contact_email` varchar(150),
  `contact_phone` varchar(20),
  `address` text,
  `created_at` timestamp
);

CREATE TABLE `solvers` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `user_id` bigint UNIQUE NOT NULL,
  `solver_type` varchar(50) COMMENT 'VMC | ORG | NOBLE_PERSON',
  `organization_id` bigint,
  `is_verified` boolean DEFAULT false,
  `rating` decimal(3,2) DEFAULT 0
);

CREATE TABLE `problem_categories` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `is_active` boolean DEFAULT true
);

CREATE TABLE `problem_statuses` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL
);

CREATE TABLE `problem_priorities` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL
);

CREATE TABLE `problems` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `image` text,
  `citizen_id` bigint NOT NULL,
  `assigned_solver_id` bigint,
  `category_id` bigint,
  `status_id` bigint,
  `priority_id` bigint,
  `latitude` decimal(10,8),
  `longitude` decimal(11,8),
  `address` text,
  `is_deleted` boolean DEFAULT false,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `problem_images` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `problem_id` bigint NOT NULL,
  `image_data` text COMMENT 'Base64 string or image path',
  `uploaded_by` bigint NOT NULL,
  `created_at` timestamp
);

CREATE TABLE `problem_comments` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `problem_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `comment` text NOT NULL,
  `created_at` timestamp
);

CREATE TABLE `problem_history` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `problem_id` bigint NOT NULL,
  `status_id` bigint NOT NULL,
  `changed_by` bigint NOT NULL,
  `changed_at` timestamp
);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `citizens` (`user_id`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `solvers` (`user_id`);

ALTER TABLE `solvers` ADD FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`);

ALTER TABLE `problems` ADD FOREIGN KEY (`citizen_id`) REFERENCES `citizens` (`id`);

ALTER TABLE `problems` ADD FOREIGN KEY (`assigned_solver_id`) REFERENCES `solvers` (`id`);

ALTER TABLE `problem_images` ADD FOREIGN KEY (`problem_id`) REFERENCES `problems` (`id`);

ALTER TABLE `problem_images` ADD FOREIGN KEY (`uploaded_by`) REFERENCES `users` (`id`);

ALTER TABLE `problems` ADD FOREIGN KEY (`category_id`) REFERENCES `problem_categories` (`id`);

ALTER TABLE `problems` ADD FOREIGN KEY (`status_id`) REFERENCES `problem_statuses` (`id`);

ALTER TABLE `problems` ADD FOREIGN KEY (`priority_id`) REFERENCES `problem_priorities` (`id`);

ALTER TABLE `problem_comments` ADD FOREIGN KEY (`problem_id`) REFERENCES `problems` (`id`);

ALTER TABLE `problem_comments` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `problem_history` ADD FOREIGN KEY (`problem_id`) REFERENCES `problems` (`id`);

ALTER TABLE `problem_history` ADD FOREIGN KEY (`status_id`) REFERENCES `problem_statuses` (`id`);

ALTER TABLE `problem_history` ADD FOREIGN KEY (`changed_by`) REFERENCES `users` (`id`);

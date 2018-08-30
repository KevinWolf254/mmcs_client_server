SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `client_bulksms_db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `client_bulksms_db`;

CREATE TABLE `contact` (
  `id` int(11) NOT NULL,
  `country_code` varchar(255) NOT NULL,
  `phone_number` varchar(9) NOT NULL,
  `tele_com` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `contacts_grouped` (
  `group_fk` int(11) NOT NULL,
  `contact_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `contact_group` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `delivery_report` (
  `id` int(11) NOT NULL,
  `message_id` varchar(255) NOT NULL,
  `received` int(11) NOT NULL,
  `rejected` int(11) NOT NULL,
  `currency` tinyint(4) NOT NULL,
  `cost` double NOT NULL,
  `date` datetime NOT NULL,
  `phone_nos_rejected` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `organisation` (
  `id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `org_contacts` (
  `org_fk` int(11) NOT NULL,
  `client_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `org_contact_groups` (
  `org_fk` int(11) NOT NULL,
  `group_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `org_delivery_report` (
  `org_fk` int(11) NOT NULL,
  `delivery_report_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `org_schedules` (
  `org_fk` int(11) NOT NULL,
  `schedule_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `org_users` (
  `org_fk` int(11) NOT NULL,
  `user_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `schedule` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `date` datetime NOT NULL,
  `day_of_week` tinyint(4) DEFAULT NULL,
  `day_of_month` int(11) NOT NULL,
  `cron_expression` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `schedule_groups` (
  `schedule_fk` int(11) NOT NULL,
  `group_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `text` (
  `id` int(11) NOT NULL,
  `message` varchar(255) NOT NULL,
  `schedule` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `other_names` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_credentials` (
  `id` int(11) NOT NULL,
  `is_active` tinyint(4) NOT NULL,
  `is_signed_in` tinyint(4) NOT NULL,
  `role` varchar(15) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_sign_in_date` datetime DEFAULT NULL,
  `current_sign_in_date` datetime DEFAULT NULL,
  `user_profile` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `contact`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `contacts_grouped`
  ADD PRIMARY KEY (`group_fk`,`contact_fk`);

ALTER TABLE `contact_group`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

ALTER TABLE `delivery_report`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `message_id` (`message_id`);

ALTER TABLE `organisation`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `number` (`number`),
  ADD UNIQUE KEY `name` (`name`);

ALTER TABLE `org_contacts`
  ADD PRIMARY KEY (`org_fk`,`client_fk`);

ALTER TABLE `org_contact_groups`
  ADD PRIMARY KEY (`org_fk`,`group_fk`);

ALTER TABLE `org_delivery_report`
  ADD PRIMARY KEY (`org_fk`,`delivery_report_fk`);

ALTER TABLE `org_schedules`
  ADD PRIMARY KEY (`org_fk`,`schedule_fk`);

ALTER TABLE `org_users`
  ADD PRIMARY KEY (`org_fk`,`user_fk`),
  ADD KEY `user_fk` (`user_fk`);

ALTER TABLE `schedule`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

ALTER TABLE `schedule_groups`
  ADD PRIMARY KEY (`schedule_fk`,`group_fk`),
  ADD KEY `group_fk` (`group_fk`),
  ADD KEY `schedule_fk` (`schedule_fk`);

ALTER TABLE `text`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

ALTER TABLE `user_credentials`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_profile` (`user_profile`);


ALTER TABLE `contact`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;
ALTER TABLE `contact_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
ALTER TABLE `delivery_report`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
ALTER TABLE `organisation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;
ALTER TABLE `schedule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;
ALTER TABLE `text`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=210;
ALTER TABLE `user_credentials`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=310;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

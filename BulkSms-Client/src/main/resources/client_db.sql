-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mmcs_client_db_v3
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mmcs_client_db_v3
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mmcs_client_db_v3` ;
USE `mmcs_client_db_v3` ;

-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`country` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `code` VARCHAR(4) NOT NULL,
  `currency` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `currency_UNIQUE` (`currency` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`organisation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`organisation` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`organisation` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_organisation_country1_idx` (`country_id` ASC) VISIBLE,
  CONSTRAINT `fk_organisation_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `mmcs_client_db_v3`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`delivery_report`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`delivery_report` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`delivery_report` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `messageId` VARCHAR(45) NOT NULL,
  `received` INT(11) NOT NULL,
  `rejected` INT(11) NOT NULL,
  `phone_nos_rejected` VARCHAR(255) NULL DEFAULT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  `organisation_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `messageId_UNIQUE` (`messageId` ASC) VISIBLE,
  INDEX `fk_delivery_report_organisation1_idx` (`organisation_id` ASC) VISIBLE,
  CONSTRAINT `fk_delivery_report_organisation1`
    FOREIGN KEY (`organisation_id`)
    REFERENCES `mmcs_client_db_v3`.`organisation` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`group_`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`group_` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`group_` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `organisation_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_Group_organisation1_idx` (`organisation_id` ASC) VISIBLE,
  CONSTRAINT `fk_Group_organisation1`
    FOREIGN KEY (`organisation_id`)
    REFERENCES `mmcs_client_db_v3`.`organisation` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`schedule` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`schedule` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `type` TINYINT(4) NOT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  `day_of_week` TINYINT(4) NULL DEFAULT NULL,
  `day_of_month` INT(11) NULL DEFAULT NULL,
  `cron_expression` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`group_schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`group_schedule` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`group_schedule` (
  `schedule_id` INT(11) NOT NULL,
  `group_id` INT(11) NOT NULL,
  PRIMARY KEY (`schedule_id`, `group_id`),
  INDEX `fk_schedule_has_group_group1_idx` (`group_id` ASC) VISIBLE,
  INDEX `fk_schedule_has_group_schedule1_idx` (`schedule_id` ASC) VISIBLE,
  CONSTRAINT `fk_schedule_has_group_group1`
    FOREIGN KEY (`group_id`)
    REFERENCES `mmcs_client_db_v3`.`group_` (`id`),
  CONSTRAINT `fk_schedule_has_group_schedule1`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `mmcs_client_db_v3`.`schedule` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`service_provider`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`service_provider` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`service_provider` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_service_provider_country1_idx` (`country_id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  CONSTRAINT `fk_service_provider_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `mmcs_client_db_v3`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`prefix`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`prefix` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`prefix` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`subscriber`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`subscriber` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`subscriber` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(6) NOT NULL,
  `full_phone_no` VARCHAR(13) NOT NULL,
  `service_provider_id` INT NOT NULL,
  `prefix_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_subscriber_service_provider1_idx` (`service_provider_id` ASC) VISIBLE,
  INDEX `fk_subscriber_prefix1_idx` (`prefix_id` ASC) VISIBLE,
  UNIQUE INDEX `full_phone_no_UNIQUE` (`full_phone_no` ASC) VISIBLE,
  CONSTRAINT `fk_subscriber_service_provider1`
    FOREIGN KEY (`service_provider_id`)
    REFERENCES `mmcs_client_db_v3`.`service_provider` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subscriber_prefix1`
    FOREIGN KEY (`prefix_id`)
    REFERENCES `mmcs_client_db_v3`.`prefix` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`group_subscriber`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`group_subscriber` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`group_subscriber` (
  `group_id` INT(11) NOT NULL,
  `subscriber_id` INT(11) NOT NULL,
  PRIMARY KEY (`group_id`, `subscriber_id`),
  INDEX `fk_Group_has_subscriber_subscriber1_idx` (`subscriber_id` ASC) VISIBLE,
  INDEX `fk_Group_has_subscriber_Group1_idx` (`group_id` ASC) VISIBLE,
  CONSTRAINT `fk_Group_has_subscriber_Group1`
    FOREIGN KEY (`group_id`)
    REFERENCES `mmcs_client_db_v3`.`group_` (`id`),
  CONSTRAINT `fk_Group_has_subscriber_subscriber1`
    FOREIGN KEY (`subscriber_id`)
    REFERENCES `mmcs_client_db_v3`.`subscriber` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`text`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`text` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`text` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `message` VARCHAR(360) NULL DEFAULT NULL,
  `schedule_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_text_schedule1_idx` (`schedule_id` ASC) VISIBLE,
  CONSTRAINT `fk_text_schedule1`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `mmcs_client_db_v3`.`schedule` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`user` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `surname` VARCHAR(45) NULL DEFAULT NULL,
  `other_names` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  `organisation_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_organisation_idx` (`organisation_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_organisation`
    FOREIGN KEY (`organisation_id`)
    REFERENCES `mmcs_client_db_v3`.`organisation` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`user_credentials`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`user_credentials` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`user_credentials` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `enabled` TINYINT(4) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `sign_in` DATETIME NULL DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_credentials_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_credentials_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mmcs_client_db_v3`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`user_role` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`user_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `role` TINYINT(4) NOT NULL,
  `credentials_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_role_user_credentials1_idx` (`credentials_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role_user_credentials1`
    FOREIGN KEY (`credentials_id`)
    REFERENCES `mmcs_client_db_v3`.`user_credentials` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mmcs_client_db_v3`.`service_provider_prefix`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_client_db_v3`.`service_provider_prefix` ;

CREATE TABLE IF NOT EXISTS `mmcs_client_db_v3`.`service_provider_prefix` (
  `service_provider_id` INT NOT NULL,
  `prefix_id` INT NOT NULL,
  PRIMARY KEY (`service_provider_id`, `prefix_id`),
  INDEX `fk_service_provider_has_phone_no_prefix_phone_no_prefix1_idx` (`prefix_id` ASC) VISIBLE,
  INDEX `fk_service_provider_has_phone_no_prefix_service_provider1_idx` (`service_provider_id` ASC) VISIBLE,
  CONSTRAINT `fk_service_provider_has_phone_no_prefix_service_provider1`
    FOREIGN KEY (`service_provider_id`)
    REFERENCES `mmcs_client_db_v3`.`service_provider` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_provider_has_phone_no_prefix_phone_no_prefix1`
    FOREIGN KEY (`prefix_id`)
    REFERENCES `mmcs_client_db_v3`.`prefix` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

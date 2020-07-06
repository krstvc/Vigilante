-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ip
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ip` ;

-- -----------------------------------------------------
-- Schema ip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ip` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `ip` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password_hash` VARCHAR(1000) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `surname` VARCHAR(100) NOT NULL,
  `image_uri` VARCHAR(1000) NOT NULL,
  `country_code` VARCHAR(10) NOT NULL,
  `subscribed_app` TINYINT(1) NOT NULL DEFAULT 0,
  `subscribed_mail` TINYINT(1) NOT NULL DEFAULT 0,
  `region` VARCHAR(100) NULL,
  `city` VARCHAR(100) NULL,
  `login_count` INT NOT NULL DEFAULT 0,
  `is_admin` TINYINT(1) NOT NULL DEFAULT 0,
  `is_approved` TINYINT(1) NOT NULL DEFAULT 0,
  `is_blocked` TINYINT(1) NOT NULL DEFAULT 0,
  `is_logged` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `login`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `login` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `login` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `login_time` DATETIME NOT NULL,
  `logout_time` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_LOGIN_USER_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_LOGIN_USER`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `title` VARCHAR(1000) NOT NULL,
  `content` VARCHAR(10000) NOT NULL,
  `link` VARCHAR(1000) NULL,
  `video_uri` VARCHAR(1000) NULL,
  `location` VARCHAR(1000) NULL,
  `is_emergency_alert` TINYINT(1) NOT NULL DEFAULT 0,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_post_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_post_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `post_comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post_comment` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `post_comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `post_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `content` VARCHAR(10000) NOT NULL,
  `image_uri` VARCHAR(1000) NULL,
  `time` DATETIME NOT NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_post_comment_post1_idx` (`post_id` ASC) VISIBLE,
  INDEX `fk_post_comment_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_post_comment_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `image` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `post_id` INT NOT NULL,
  `image_uri` VARCHAR(1000) NOT NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_image_post1_idx` (`post_id` ASC) VISIBLE,
  CONSTRAINT `fk_image_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emergency_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emergency_category` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emergency_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(1000) NOT NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `post_emergency_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post_emergency_category` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `post_emergency_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `post_id` INT NOT NULL,
  `emergency_category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_post_emergency_category_post1_idx` (`post_id` ASC) VISIBLE,
  INDEX `fk_post_emergency_category_emergency_category1_idx` (`emergency_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_post_emergency_category_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_emergency_category_emergency_category1`
    FOREIGN KEY (`emergency_category_id`)
    REFERENCES `emergency_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `call_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `call_category` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `call_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(1000) NOT NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `emergency_call`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `emergency_call` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `emergency_call` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `call_category_id` INT NOT NULL,
  `title` VARCHAR(1000) NOT NULL,
  `description` VARCHAR(10000) NULL,
  `image_uri` VARCHAR(1000) NULL,
  `time` DATETIME NOT NULL,
  `location` VARCHAR(1000) NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_emergency_call_call_category1_idx` (`call_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_emergency_call_call_category1`
    FOREIGN KEY (`call_category_id`)
    REFERENCES `call_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

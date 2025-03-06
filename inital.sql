-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema minuAPI
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema minuAPI
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `minuAPI` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `minuAPI` ;

-- -----------------------------------------------------
-- Table `minuAPI`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `minuAPI`.`User` ;

CREATE TABLE IF NOT EXISTS `minuAPI`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(63) NOT NULL,
  `email` VARCHAR(127) NULL DEFAULT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `providerid` VARCHAR(255) NOT NULL,
  `provider` VARCHAR(10) NOT NULL,
  `iconpath` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `minuAPI`.`Admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `minuAPI`.`Admin` ;

CREATE TABLE IF NOT EXISTS `minuAPI`.`Admin` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Admin_User`
    FOREIGN KEY (`id`)
    REFERENCES `minuAPI`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `minuAPI`.`Board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `minuAPI`.`Board` ;

CREATE TABLE IF NOT EXISTS `minuAPI`.`Board` (
  `id` INT NOT NULL,
  `title` VARCHAR(63) NOT NULL,
  `content` TEXT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `view` INT NOT NULL DEFAULT 0,
  `Userid` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Board_User1_idx` (`Userid` ASC) VISIBLE,
  CONSTRAINT `fk_Board_User1`
    FOREIGN KEY (`Userid`)
    REFERENCES `minuAPI`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `minuAPI`.`Comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `minuAPI`.`Comment` ;

CREATE TABLE IF NOT EXISTS `minuAPI`.`Comment` (
  `id` INT NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Boardid` INT NOT NULL,
  `Userid` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Comment_Board1_idx` (`Boardid` ASC) VISIBLE,
  INDEX `fk_Comment_User1_idx` (`Userid` ASC) VISIBLE,
  CONSTRAINT `fk_Comment_Board1`
    FOREIGN KEY (`Boardid`)
    REFERENCES `minuAPI`.`Board` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comment_User1`
    FOREIGN KEY (`Userid`)
    REFERENCES `minuAPI`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

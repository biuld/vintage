-- Valentina Studio --
-- MySQL dump --
-- ---------------------------------------------------------


-- CREATE TABLE "comment" --------------------------------------
CREATE TABLE `comment` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`post_id` Int( 255 ) NULL,
	`content` LongText CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
	`gmt_create` DateTime NULL,
	`reply_user_id` Int( 255 ) NULL,
	`parent_id` Int( 255 ) NOT NULL DEFAULT 0,
	`request` Text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
	`name` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`user_id` Int( 255 ) NULL,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 40;
-- -------------------------------------------------------------


-- CREATE TABLE "post" -----------------------------------------
CREATE TABLE `post` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`title` VarChar( 100 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
	`gmt_create` DateTime NULL,
	`user_id` Int( 255 ) NULL,
	`view_count` Int( 255 ) NULL DEFAULT 0,
	`abs` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
	`content` LongText CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
	`gmt_modify` DateTime NULL,
	`last_modified_by` Int( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 8;
-- -------------------------------------------------------------


-- CREATE TABLE "post_tag_map" ---------------------------------
CREATE TABLE `post_tag_map` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`post_id` Int( 255 ) NOT NULL,
	`tag_id` Int( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ) )
CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 4;
-- -------------------------------------------------------------


-- CREATE TABLE "role" -----------------------------------------
CREATE TABLE `role` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`role_name` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 4;
-- -------------------------------------------------------------


-- CREATE TABLE "tag" ------------------------------------------
CREATE TABLE `tag` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`tag_desc` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`user_id` Int( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 4;
-- -------------------------------------------------------------


-- CREATE TABLE "user" -----------------------------------------
CREATE TABLE `user` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`username` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
	`password` VarChar( 512 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
	`avatar` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
	`email` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
	`verified` Int( 255 ) NOT NULL DEFAULT 0 COMMENT '1 - true, 0 - fasle',
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 4;
-- -------------------------------------------------------------


-- CREATE TABLE "user_role_map" --------------------------------
CREATE TABLE `user_role_map` ( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`user_id` Int( 255 ) NOT NULL,
	`role_id` Int( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 6;
-- -------------------------------------------------------------




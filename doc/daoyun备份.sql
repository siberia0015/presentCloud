/*
 Navicat MySQL Data Transfer

 Source Server         : 云服务器mysql
 Source Server Type    : MariaDB
 Source Server Version : 50568
 Source Host           : localhost:3306
 Source Schema         : daoyun

 Target Server Type    : MariaDB
 Target Server Version : 50568
 File Encoding         : 65001

 Date: 28/06/2021 16:41:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_buttontype
-- ----------------------------
DROP TABLE IF EXISTS `t_buttontype`;
CREATE TABLE `t_buttontype`  (
  `button_id` int(11) NOT NULL AUTO_INCREMENT,
  `CN_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `EN_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`button_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classes_id` int(11) NOT NULL,
  `classes_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `school` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `teacher_id` bigint(20) NULL DEFAULT NULL,
  `teacher_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `term` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `textbook` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `over` tinyint(1) NULL DEFAULT 0,
  `class_image` blob NULL,
  `joinable` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '是否可加入',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `classes_id`(`classes_id`) USING BTREE,
  INDEX `classes_name`(`classes_name`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `t_class_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 168 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dict_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dict_eng` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `dict_eng`) USING BTREE,
  INDEX `dict_eng`(`dict_eng`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_dict_info
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_info`;
CREATE TABLE `t_dict_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_eng` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `item_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `item_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sequence` int(8) UNSIGNED NULL DEFAULT 1 COMMENT '排序',
  `isdefault` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dict_id`(`dict_eng`) USING BTREE,
  CONSTRAINT `t_dict_info_ibfk_1` FOREIGN KEY (`dict_eng`) REFERENCES `t_dict` (`dict_eng`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_loginlog
-- ----------------------------
DROP TABLE IF EXISTS `t_loginlog`;
CREATE TABLE `t_loginlog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `loginTime` datetime NULL DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `system` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `browser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `menu_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_sort` int(11) NULL DEFAULT NULL,
  `isShow` tinyint(1) NULL DEFAULT NULL,
  `isPage` tinyint(1) NULL DEFAULT NULL,
  `parent_menu_id` int(11) UNSIGNED ZEROFILL NOT NULL DEFAULT 00000000000,
  PRIMARY KEY (`menu_id`) USING BTREE,
  INDEX `menu_name`(`menu_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_pagebutton
-- ----------------------------
DROP TABLE IF EXISTS `t_pagebutton`;
CREATE TABLE `t_pagebutton`  (
  `pageButton_id` int(11) NOT NULL AUTO_INCREMENT,
  `button_id` int(11) NULL DEFAULT NULL,
  `menu_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`pageButton_id`) USING BTREE,
  INDEX `FK_Reference_10`(`menu_id`) USING BTREE,
  INDEX `FK_Reference_9`(`button_id`) USING BTREE,
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`button_id`) REFERENCES `t_buttontype` (`button_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_param
-- ----------------------------
DROP TABLE IF EXISTS `t_param`;
CREATE TABLE `t_param`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `key_eng` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `key_eng`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_passport
-- ----------------------------
DROP TABLE IF EXISTS `t_passport`;
CREATE TABLE `t_passport`  (
  `passport_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `token` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `login_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`passport_id`) USING BTREE,
  INDEX `FK_Reference_7`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CreationDate` datetime NULL DEFAULT NULL,
  `Creator` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Modifier` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ModificationDate` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`permission_id`) USING BTREE,
  INDEX `fk_permission_menu`(`name`) USING BTREE,
  CONSTRAINT `fk_permission_menu` FOREIGN KEY (`name`) REFERENCES `t_menu` (`menu_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CreationDate` datetime NULL DEFAULT NULL,
  `Creator` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Modifier` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ModificationDate` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL,
  `permission_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_Reference_5`(`role_id`) USING BTREE,
  INDEX `FK_Reference_6`(`permission_id`) USING BTREE,
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 412 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_sign
-- ----------------------------
DROP TABLE IF EXISTS `t_sign`;
CREATE TABLE `t_sign`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id（学生）',
  `class_id` int(11) NOT NULL COMMENT '班课id',
  `sign_time` datetime NOT NULL COMMENT '签到时间',
  `score` int(11) UNSIGNED NULL DEFAULT 2 COMMENT '经验值（默认2）',
  `start_sign_id` int(11) NOT NULL COMMENT '签到事件号',
  `longitude` double(10, 0) NULL DEFAULT NULL COMMENT '经度',
  `latitude` double(10, 0) NULL DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_username_sign`(`user_id`) USING BTREE,
  INDEX `fk_class_sign`(`class_id`) USING BTREE,
  INDEX `start_sign_id`(`start_sign_id`) USING BTREE,
  CONSTRAINT `fk_class_sign` FOREIGN KEY (`class_id`) REFERENCES `t_class` (`classes_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_sign_ibfk_1` FOREIGN KEY (`start_sign_id`) REFERENCES `t_start_sign` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_sign_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_start_sign
-- ----------------------------
DROP TABLE IF EXISTS `t_start_sign`;
CREATE TABLE `t_start_sign`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '签到事件号',
  `user_id` bigint(20) NOT NULL COMMENT '用户id（签到发起人）',
  `class_id` int(11) NOT NULL COMMENT '班课id',
  `start_time` datetime NOT NULL COMMENT '签到发起时间',
  `type` int(11) NOT NULL COMMENT '签到类型',
  `time_limit` int(10) UNSIGNED NULL DEFAULT 3 COMMENT '签到时限（该属性是限时签到才有）',
  `end_time` datetime NULL DEFAULT NULL COMMENT '签到结束时间',
  `over` tinyint(1) NULL DEFAULT NULL COMMENT '签到是否结束（bool）',
  `score` int(11) UNSIGNED NULL DEFAULT 2 COMMENT '经验值（默认2）',
  `distance` int(5) UNSIGNED NULL DEFAULT 10 COMMENT '距离',
  `longitude` double(10, 8) NULL DEFAULT NULL COMMENT '经度',
  `latitude` double(10, 8) NULL DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_class_startsign`(`class_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_class_startsign` FOREIGN KEY (`class_id`) REFERENCES `t_class` (`classes_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_start_sign_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 201 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_sub_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sub_menu`;
CREATE TABLE `t_sub_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NOT NULL,
  `sub_menu_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_menu`(`menu_id`) USING BTREE,
  INDEX `fk_submenu`(`sub_menu_id`) USING BTREE,
  CONSTRAINT `fk_menu` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_submenu` FOREIGN KEY (`sub_menu_id`) REFERENCES `t_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `phone` bigint(20) NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值（加密用）',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `school` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在学校',
  `school_number` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号或工号',
  `classes` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Creator` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Modifier` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `college` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在院系',
  `ModificationDate` datetime NULL DEFAULT NULL,
  `CreationDate` datetime NULL DEFAULT NULL,
  `birthday` datetime NULL DEFAULT NULL COMMENT '出生年份',
  `identity` tinyint(1) NULL DEFAULT 1 COMMENT '0管理员1老师2学生',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_user_auths
-- ----------------------------
DROP TABLE IF EXISTS `t_user_auths`;
CREATE TABLE `t_user_auths`  (
  `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `identity_type` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `identifier` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '账号',
  `credential` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `t_user_auths_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_user_class
-- ----------------------------
DROP TABLE IF EXISTS `t_user_class`;
CREATE TABLE `t_user_class`  (
  `class_id` int(11) NULL DEFAULT NULL,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `class_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班级名',
  `identity` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '创建或加入',
  `score` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '学生经验值',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_class_user`(`user_name`) USING BTREE,
  INDEX `FK_user_class`(`class_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `class_name`(`class_name`) USING BTREE,
  CONSTRAINT `FK_class_user` FOREIGN KEY (`user_name`) REFERENCES `t_user` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_user_class` FOREIGN KEY (`class_id`) REFERENCES `t_class` (`classes_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_user_class_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_user_class_ibfk_4` FOREIGN KEY (`class_name`) REFERENCES `t_class` (`classes_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_Reference_3`(`role_id`) USING BTREE,
  INDEX `FK_Reference_4`(`user_id`) USING BTREE,
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

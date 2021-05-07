/*
Navicat MariaDB Data Transfer

Source Server         : daoyun
Source Server Version : 50568
Source Host           : localhost:3306
Source Database       : daoyun

Target Server Type    : MariaDB
Target Server Version : 50568
File Encoding         : 65001

Date: 2021-04-20 11:15:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_ user_auths
-- ----------------------------
DROP TABLE IF EXISTS `t_ user_auths`;
CREATE TABLE `t_ user_auths` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `identity_type` varchar(255) NOT NULL,
  `identifier` varchar(255) NOT NULL,
  `credential` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `t_ user_auths_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for t_buttontype
-- ----------------------------
DROP TABLE IF EXISTS `t_buttontype`;
CREATE TABLE `t_buttontype` (
  `button_id` int(11) NOT NULL AUTO_INCREMENT,
  `CN_name` varchar(10) NOT NULL,
  `EN_name` varchar(10) NOT NULL,
  PRIMARY KEY (`button_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classes_id` int(11) NOT NULL,
  `classes_name` varchar(20) DEFAULT NULL,
  `school` varchar(20) DEFAULT NULL,
  `department` varchar(20) DEFAULT NULL,
  `teacher_id` varchar(20) DEFAULT NULL,
  `teacher_name` varchar(20) DEFAULT NULL,
  `course_name` varchar(20) DEFAULT NULL,
  `term` varchar(20) DEFAULT NULL,
  `textbook` varchar(255) DEFAULT NULL,
  `class_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `classes_id` (`classes_id`),
  KEY `class_image` (`class_image`),
  KEY `classes_name` (`classes_name`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(20) NOT NULL,
  `dict_describe` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_dict_info
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_info`;
CREATE TABLE `t_dict_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_id` int(11) NOT NULL,
  `item_key` varchar(10) NOT NULL,
  `item_value` varchar(10) NOT NULL,
  `sequence` int(11) NOT NULL,
  `isdefault` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dict_id` (`dict_id`),
  CONSTRAINT `fk_dict` FOREIGN KEY (`dict_id`) REFERENCES `t_dict` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_loginlog
-- ----------------------------
DROP TABLE IF EXISTS `t_loginlog`;
CREATE TABLE `t_loginlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `loginTime` datetime DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `system` varchar(255) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(20) NOT NULL,
  `menu_icon` varchar(255) DEFAULT NULL,
  `menu_link` varchar(255) DEFAULT NULL,
  `menu_sort` int(11) DEFAULT NULL,
  `isShow` tinyint(1) DEFAULT NULL,
  `isPage` tinyint(1) DEFAULT NULL,
  `parent_menu_id` int(11) unsigned zerofill NOT NULL DEFAULT '00000000000',
  PRIMARY KEY (`menu_id`),
  KEY `menu_name` (`menu_name`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_pagebutton
-- ----------------------------
DROP TABLE IF EXISTS `t_pagebutton`;
CREATE TABLE `t_pagebutton` (
  `pageButton_id` int(11) NOT NULL AUTO_INCREMENT,
  `button_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`pageButton_id`),
  KEY `FK_Reference_10` (`menu_id`),
  KEY `FK_Reference_9` (`button_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`button_id`) REFERENCES `t_buttontype` (`button_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_param
-- ----------------------------
DROP TABLE IF EXISTS `t_param`;
CREATE TABLE `t_param` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(20) DEFAULT NULL,
  `value` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_passport
-- ----------------------------
DROP TABLE IF EXISTS `t_passport`;
CREATE TABLE `t_passport` (
  `passport_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `token` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `salt` varchar(100) NOT NULL,
  `login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`passport_id`),
  KEY `FK_Reference_7` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `CreationDate` datetime DEFAULT NULL,
  `Creator` varchar(20) DEFAULT NULL,
  `Modifier` varchar(20) DEFAULT NULL,
  `ModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`permission_id`),
  KEY `fk_permission_menu` (`name`),
  CONSTRAINT `fk_permission_menu` FOREIGN KEY (`name`) REFERENCES `t_menu` (`menu_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL,
  `CreationDate` datetime DEFAULT NULL,
  `Creator` varchar(20) DEFAULT NULL,
  `Modifier` varchar(20) DEFAULT NULL,
  `ModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_5` (`role_id`),
  KEY `FK_Reference_6` (`permission_id`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=346 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sign
-- ----------------------------
DROP TABLE IF EXISTS `t_sign`;
CREATE TABLE `t_sign` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `class_id` int(11) NOT NULL,
  `sign_time` datetime NOT NULL,
  `score` int(11) unsigned DEFAULT '2',
  `singn_times` int(11) DEFAULT NULL,
  `start_sign_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_username_sign` (`user_name`),
  KEY `fk_class_sign` (`class_id`),
  CONSTRAINT `fk_class_sign` FOREIGN KEY (`class_id`) REFERENCES `t_class` (`classes_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_username_sign` FOREIGN KEY (`user_name`) REFERENCES `t_user` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_start_sign
-- ----------------------------
DROP TABLE IF EXISTS `t_start_sign`;
CREATE TABLE `t_start_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `class_id` int(11) NOT NULL,
  `singn_num` int(10) NOT NULL,
  `sign_time` datetime NOT NULL,
  `score` int(11) unsigned DEFAULT '2',
  `distance` int(5) unsigned DEFAULT '10',
  `type` int(11) NOT NULL,
  `time` int(10) unsigned DEFAULT '3',
  `latitude` double(10,0) DEFAULT NULL,
  `longitude` double(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_class_startsign` (`class_id`),
  KEY `fk_user_startsign` (`user_name`),
  CONSTRAINT `fk_class_startsign` FOREIGN KEY (`class_id`) REFERENCES `t_class` (`classes_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_startsign` FOREIGN KEY (`user_name`) REFERENCES `t_user` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sub_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sub_menu`;
CREATE TABLE `t_sub_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NOT NULL,
  `sub_menu_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_menu` (`menu_id`),
  KEY `fk_submenu` (`sub_menu_id`),
  CONSTRAINT `fk_menu` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_submenu` FOREIGN KEY (`sub_menu_id`) REFERENCES `t_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `phone` bigint(20) NOT NULL COMMENT '手机号',
  `email` varchar(20) DEFAULT NULL COMMENT '电子邮箱',
  `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(100) DEFAULT NULL COMMENT '盐值（加密用）',
  `image` varchar(255) DEFAULT NULL COMMENT '头像',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `school` varchar(20) DEFAULT NULL COMMENT '所在学校',
  `school_number` varchar(15) DEFAULT NULL COMMENT '学号或工号',
  `classes` varchar(20) DEFAULT NULL,
  `Creator` varchar(20) DEFAULT NULL,
  `Modifier` varchar(20) DEFAULT NULL,
  `college` varchar(20) DEFAULT NULL COMMENT '所在院系',
  `ModificationDate` datetime DEFAULT NULL,
  `CreationDate` datetime DEFAULT NULL,
  `birthday` datetime DEFAULT NULL COMMENT '出生年份',
  `identity` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '老师或学生',
  PRIMARY KEY (`user_id`),
  KEY `name` (`name`),
  KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_class
-- ----------------------------
DROP TABLE IF EXISTS `t_user_class`;
CREATE TABLE `t_user_class` (
  `class_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_image` varchar(255) DEFAULT NULL COMMENT '班级头像',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `user_phone` bigint(20) DEFAULT NULL COMMENT '用户手机',
  `class_name` varchar(20) DEFAULT NULL COMMENT '班级名',
  `identity` tinyint(1) unsigned DEFAULT '1' COMMENT '创建或加入',
  PRIMARY KEY (`id`),
  KEY `FK_class_user` (`user_name`),
  KEY `FK_user_class` (`class_id`),
  KEY `user_id` (`user_id`),
  KEY `class_image` (`class_image`),
  KEY `user_phone` (`user_phone`),
  KEY `class_name` (`class_name`),
  CONSTRAINT `FK_class_user` FOREIGN KEY (`user_name`) REFERENCES `t_user` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_user_class` FOREIGN KEY (`class_id`) REFERENCES `t_class` (`classes_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_user_class_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_user_class_ibfk_2` FOREIGN KEY (`class_image`) REFERENCES `t_class` (`class_image`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_user_class_ibfk_3` FOREIGN KEY (`user_phone`) REFERENCES `t_user` (`phone`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_user_class_ibfk_4` FOREIGN KEY (`class_name`) REFERENCES `t_class` (`classes_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_3` (`role_id`),
  KEY `FK_Reference_4` (`user_id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

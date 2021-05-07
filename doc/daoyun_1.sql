/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : daoyun

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2021-03-25 12:25:23
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of t_buttontype
-- ----------------------------

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
  PRIMARY KEY (`id`),
  KEY `classes_id` (`classes_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_class
-- ----------------------------
INSERT INTO `t_class` VALUES ('8', '19971', '组成原理', 'fzu', 'cs', 'n29037011', '三胖老师');
INSERT INTO `t_class` VALUES ('9', '74922', '工程训练', 'fzu', 'cs', 'n29037011', '三胖老师');
INSERT INTO `t_class` VALUES ('10', '48632', '数据结构', 'fzu', 'cs', 'n29037011', '三胖老师');
INSERT INTO `t_class` VALUES ('12', '78892', 'java', 'fzu', 'cs', 'n29037011', '三胖老师');
INSERT INTO `t_class` VALUES ('13', '58966', 'python', 'fzu', 'cs', 'n29037011', '三胖老师');
INSERT INTO `t_class` VALUES ('19', '38629', '辩证法', '福州大学', '思政学院', 'n29037011', '三胖老师');
INSERT INTO `t_class` VALUES ('27', '88435', '测试111', '福州大学', '数计学院', 'teacher', '教师1');
INSERT INTO `t_class` VALUES ('28', '29846', '修仙第一课', '武当', '修仙学院', 'n19037020', '管理员');
INSERT INTO `t_class` VALUES ('29', '58960', '工程训练', '福州大学', '计算机学院', 'teacher', '教师1');

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `describe` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES ('12', '学籍', null);
INSERT INTO `t_dict` VALUES ('13', '性别', null);
INSERT INTO `t_dict` VALUES ('14', '专业', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dict_info
-- ----------------------------
INSERT INTO `t_dict_info` VALUES ('14', '12', '福建', '200', '1', '1');
INSERT INTO `t_dict_info` VALUES ('15', '12', '上海', '201', '0', '0');
INSERT INTO `t_dict_info` VALUES ('16', '13', '男', '300', '1', '1');
INSERT INTO `t_dict_info` VALUES ('17', '13', '女', '301', '0', '0');

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
-- Records of t_loginlog
-- ----------------------------

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
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('3', '用户管理', 'E:\\daoyun_web\\resources\\image\\2.jpg', '\\daoyun\\user', '0', '1', '1', '00000000000');
INSERT INTO `t_menu` VALUES ('13', '角色管理', 'E:\\daoyun_web\\resources\\image\\5.jpg', '\\daoyun\\role', '0', '1', '1', '00000000000');
INSERT INTO `t_menu` VALUES ('14', '权限管理', 'E:\\daoyun_web\\resources\\image\\6.jpg', '\\daoyun\\permission', '0', '1', '1', '00000000000');
INSERT INTO `t_menu` VALUES ('17', '数据字典', 'E:\\daoyun_web\\resources\\image\\7.jpg', '\\daoyun\\dict', '0', '1', '1', '00000000000');
INSERT INTO `t_menu` VALUES ('18', '参数设置', 'E:\\daoyun_web\\resources\\image\\8.jpg', '\\daoyun\\param', '0', '1', '1', '00000000000');
INSERT INTO `t_menu` VALUES ('19', '班课管理', 'E:\\daoyun_web\\resources\\image\\9.jpg', '\\daoyun\\class', '0', '1', '1', '00000000000');
INSERT INTO `t_menu` VALUES ('20', '签到管理', 'E:\\daoyun_web\\resources\\image\\10.jpg', '\\daoyun\\sign', '0', '1', '1', '00000000000');
INSERT INTO `t_menu` VALUES ('21', '菜单管理', 'E:\\daoyun_web\\resources\\image\\11.jpg', '\\daoyun\\menu', '0', '1', '1', '00000000000');
INSERT INTO `t_menu` VALUES ('22', '新增用户', null, null, '1', '1', '1', '00000000003');
INSERT INTO `t_menu` VALUES ('23', '新增角色', null, null, '1', '1', '0', '00000000013');
INSERT INTO `t_menu` VALUES ('24', '新增权限', null, null, '1', '1', '0', '00000000014');
INSERT INTO `t_menu` VALUES ('25', '新增班课', null, null, '1', '1', '0', '00000000020');
INSERT INTO `t_menu` VALUES ('26', '新增菜单', null, null, '1', '0', '0', '00000000021');
INSERT INTO `t_menu` VALUES ('27', '数据字典录入', null, null, '1', '1', '0', '00000000017');
INSERT INTO `t_menu` VALUES ('28', '数据字典层级展示', null, null, '2', '1', '0', '00000000017');

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
-- Records of t_pagebutton
-- ----------------------------

-- ----------------------------
-- Table structure for t_param
-- ----------------------------
DROP TABLE IF EXISTS `t_param`;
CREATE TABLE `t_param` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(20) DEFAULT NULL,
  `value` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_param
-- ----------------------------
INSERT INTO `t_param` VALUES ('1', '经验', '2');
INSERT INTO `t_param` VALUES ('2', '距离', '10');

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
-- Records of t_passport
-- ----------------------------
INSERT INTO `t_passport` VALUES ('1', '1', '123456', '123456', '434234', '2020-03-31 10:58:45');
INSERT INTO `t_passport` VALUES ('2', '2', 'hgrrfsf', 'srfsgf', '3543534', '2020-04-01 10:22:30');
INSERT INTO `t_passport` VALUES ('3', '3', '54353', 'dfhsdfg', '34234234', '2020-04-01 10:22:46');

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
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', 'role:add', '角色管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('2', 'role:update', '角色管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('3', 'role:delete', '角色管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('4', 'role:select', '角色管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('62', 'user:add', '用户管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('63', 'user:update', '用户管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('64', 'user:delete', '用户管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('65', 'user:select', '用户管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('66', 'permission:add', '权限管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('67', 'permission:update', '权限管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('68', 'permission:delete', '权限管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('69', 'permission:select', '权限管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('70', 'class:add', '班课管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('71', 'class:update', '班课管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('72', 'class:delete', '班课管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('73', 'class:select', '班课管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('74', 'menu:add', '菜单管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('75', 'menu:update', '菜单管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('76', 'menu:delete', '菜单管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('77', 'menu:select ', '菜单管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('78', 'dict:add', '数据字典', null, null, null, null);
INSERT INTO `t_permission` VALUES ('80', 'dict:update', '数据字典', null, null, null, null);
INSERT INTO `t_permission` VALUES ('81', 'dict:delete', '数据字典', null, null, null, null);
INSERT INTO `t_permission` VALUES ('82', 'dict:select', '数据字典', null, null, null, null);
INSERT INTO `t_permission` VALUES ('83', 'sign:update', '签到管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('84', 'sign:add', '签到管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('85', 'sign:delete', '签到管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('86', 'sign:select ', '签到管理', null, null, null, null);
INSERT INTO `t_permission` VALUES ('87', 'param:add', '参数设置', null, null, null, null);
INSERT INTO `t_permission` VALUES ('88', 'param:update', '参数设置', null, null, null, null);
INSERT INTO `t_permission` VALUES ('90', 'param:delete', '参数设置', null, null, null, null);
INSERT INTO `t_permission` VALUES ('91', 'param:select', '参数设置', null, null, null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'student', null, null, null, null);
INSERT INTO `t_role` VALUES ('2', 'teacher', null, null, null, null);
INSERT INTO `t_role` VALUES ('3', 'admin', null, null, null, null);
INSERT INTO `t_role` VALUES ('4', 'guess', null, null, null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=342 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('224', '2', '4');
INSERT INTO `t_role_permission` VALUES ('225', '2', '63');
INSERT INTO `t_role_permission` VALUES ('226', '2', '84');
INSERT INTO `t_role_permission` VALUES ('227', '2', '86');
INSERT INTO `t_role_permission` VALUES ('228', '2', '73');
INSERT INTO `t_role_permission` VALUES ('229', '2', '72');
INSERT INTO `t_role_permission` VALUES ('230', '2', '65');
INSERT INTO `t_role_permission` VALUES ('263', '4', '73');
INSERT INTO `t_role_permission` VALUES ('264', '4', '84');
INSERT INTO `t_role_permission` VALUES ('306', '3', '1');
INSERT INTO `t_role_permission` VALUES ('307', '3', '2');
INSERT INTO `t_role_permission` VALUES ('308', '3', '3');
INSERT INTO `t_role_permission` VALUES ('309', '3', '4');
INSERT INTO `t_role_permission` VALUES ('310', '3', '62');
INSERT INTO `t_role_permission` VALUES ('311', '3', '63');
INSERT INTO `t_role_permission` VALUES ('312', '3', '64');
INSERT INTO `t_role_permission` VALUES ('313', '3', '65');
INSERT INTO `t_role_permission` VALUES ('314', '3', '66');
INSERT INTO `t_role_permission` VALUES ('315', '3', '67');
INSERT INTO `t_role_permission` VALUES ('316', '3', '68');
INSERT INTO `t_role_permission` VALUES ('317', '3', '69');
INSERT INTO `t_role_permission` VALUES ('318', '3', '70');
INSERT INTO `t_role_permission` VALUES ('319', '3', '71');
INSERT INTO `t_role_permission` VALUES ('320', '3', '72');
INSERT INTO `t_role_permission` VALUES ('321', '3', '73');
INSERT INTO `t_role_permission` VALUES ('322', '3', '74');
INSERT INTO `t_role_permission` VALUES ('323', '3', '75');
INSERT INTO `t_role_permission` VALUES ('324', '3', '76');
INSERT INTO `t_role_permission` VALUES ('325', '3', '77');
INSERT INTO `t_role_permission` VALUES ('326', '3', '78');
INSERT INTO `t_role_permission` VALUES ('327', '3', '80');
INSERT INTO `t_role_permission` VALUES ('328', '3', '81');
INSERT INTO `t_role_permission` VALUES ('329', '3', '82');
INSERT INTO `t_role_permission` VALUES ('330', '3', '83');
INSERT INTO `t_role_permission` VALUES ('331', '3', '84');
INSERT INTO `t_role_permission` VALUES ('332', '3', '85');
INSERT INTO `t_role_permission` VALUES ('333', '3', '86');
INSERT INTO `t_role_permission` VALUES ('334', '3', '87');
INSERT INTO `t_role_permission` VALUES ('335', '3', '88');
INSERT INTO `t_role_permission` VALUES ('336', '3', '90');
INSERT INTO `t_role_permission` VALUES ('337', '3', '91');
INSERT INTO `t_role_permission` VALUES ('338', '1', '73');
INSERT INTO `t_role_permission` VALUES ('339', '1', '70');
INSERT INTO `t_role_permission` VALUES ('340', '1', '86');
INSERT INTO `t_role_permission` VALUES ('341', '1', '84');

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
-- Records of t_sign
-- ----------------------------
INSERT INTO `t_sign` VALUES ('28', 'student1', '88435', '2020-07-01 19:15:22', '2', '1', '63');
INSERT INTO `t_sign` VALUES ('29', 'admin', '88435', '2020-07-02 18:52:23', '2', '1', '70');
INSERT INTO `t_sign` VALUES ('30', 'admin', '88435', '2020-07-02 18:54:31', '4', '2', '71');
INSERT INTO `t_sign` VALUES ('31', 'admin', '88435', '2020-07-02 19:08:06', '6', '3', '72');

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
-- Records of t_start_sign
-- ----------------------------
INSERT INTO `t_start_sign` VALUES ('64', 'teacher', '88435', '1234567', '2020-07-01 19:26:23', '2', '100', '0', '1', '27', '117');
INSERT INTO `t_start_sign` VALUES ('69', 'teacher', '58960', '1234567', '2020-07-02 18:34:43', '2', '100', '0', null, null, null);
INSERT INTO `t_start_sign` VALUES ('72', 'teacher', '88435', '1234567', '2020-07-02 19:07:40', '2', '100', '0', '2', '27', '117');

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
-- Records of t_sub_menu
-- ----------------------------
INSERT INTO `t_sub_menu` VALUES ('9', '20', '25');
INSERT INTO `t_sub_menu` VALUES ('10', '21', '26');
INSERT INTO `t_sub_menu` VALUES ('28', '17', '27');
INSERT INTO `t_sub_menu` VALUES ('29', '17', '28');
INSERT INTO `t_sub_menu` VALUES ('30', '3', '22');
INSERT INTO `t_sub_menu` VALUES ('31', '13', '23');
INSERT INTO `t_sub_menu` VALUES ('32', '14', '24');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  `sex` char(1) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `tel` bigint(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `school` varchar(20) DEFAULT NULL,
  `classes` varchar(20) DEFAULT NULL,
  `school_number` varchar(15) DEFAULT NULL,
  `CreationDate` datetime DEFAULT NULL,
  `Creator` varchar(20) DEFAULT NULL,
  `Modifier` varchar(20) DEFAULT NULL,
  `ModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `tel` (`tel`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('37', '管理员', 'n19037020', '女', null, '15567678989', '0ae595863c837cb1786a95f6f981fd41', 'a92c1d863504fc10652dee93f5b7331b', null, null, null, '2020-05-12 15:38:21', null, null, '2020-05-12 15:38:21');
INSERT INTO `t_user` VALUES ('43', '学生1', 'student1', '男', null, '15900000003', '4615c847a1078ada3f42602e1a850da2', 'dde58a7a462f96c78334e80ec7bb771b', null, null, null, '2020-06-29 12:03:37', null, null, '2020-06-29 12:03:37');
INSERT INTO `t_user` VALUES ('44', '学生2', 'student2', '男', null, '15900000004', '0c04d0874d83ded43043e60477fef966', 'ec7ff5e59b6f87ed1dd3d3d184ceaaf2', null, null, null, '2020-06-29 12:04:54', null, null, '2020-06-29 12:04:54');
INSERT INTO `t_user` VALUES ('49', '管理员', 'admin', '男', null, '15900000001', '856aea89ad509f163284abb75579dcfc', '50a228355820519844e2b24b7c56dc71', null, null, null, '2020-06-29 12:13:50', null, null, '2020-06-29 12:13:50');
INSERT INTO `t_user` VALUES ('50', '教师1', 'teacher', '男', null, '15900000002', '7267b3920ad6cef1e82d958b11180b45', 'cabedb3d52f6f63e91f71ac056245143', null, null, null, '2020-06-29 12:14:29', null, null, '2020-06-29 12:14:29');
INSERT INTO `t_user` VALUES ('51', null, 'student5', '男', null, '15900000006', 'e741e317d6190e79a03de63a2e780ede', '61de456b33acb7ba733650b60faba1ab', null, null, null, '2020-07-02 17:37:35', null, null, '2020-07-02 17:37:35');
INSERT INTO `t_user` VALUES ('52', null, '测试员', '男', null, '13859105646', 'b1ae2456ca724c4fc408a91c141852a8', 'cc3f063d82411903f4c42dd5aca8a99c', null, null, null, '2020-07-03 09:12:12', null, null, '2020-07-03 09:12:12');

-- ----------------------------
-- Table structure for t_user_class
-- ----------------------------
DROP TABLE IF EXISTS `t_user_class`;
CREATE TABLE `t_user_class` (
  `class_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `FK_class_user` (`user_name`),
  KEY `FK_user_class` (`class_id`),
  CONSTRAINT `FK_class_user` FOREIGN KEY (`user_name`) REFERENCES `t_user` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_user_class` FOREIGN KEY (`class_id`) REFERENCES `t_class` (`classes_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_class
-- ----------------------------
INSERT INTO `t_user_class` VALUES ('74922', 'student1', '20');
INSERT INTO `t_user_class` VALUES ('19971', 'student1', '21');
INSERT INTO `t_user_class` VALUES ('38629', 'admin', '22');
INSERT INTO `t_user_class` VALUES ('88435', 'student1', '23');
INSERT INTO `t_user_class` VALUES ('78892', 'admin', '24');
INSERT INTO `t_user_class` VALUES ('19971', 'student2', '25');
INSERT INTO `t_user_class` VALUES ('74922', 'student2', '27');
INSERT INTO `t_user_class` VALUES ('48632', 'student2', '28');
INSERT INTO `t_user_class` VALUES ('58960', 'student1', '29');
INSERT INTO `t_user_class` VALUES ('58960', 'student2', '30');
INSERT INTO `t_user_class` VALUES ('88435', 'student2', '31');
INSERT INTO `t_user_class` VALUES ('88435', 'admin', '32');

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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('14', '1', '43');
INSERT INTO `t_user_role` VALUES ('15', '3', '49');
INSERT INTO `t_user_role` VALUES ('16', '2', '50');
INSERT INTO `t_user_role` VALUES ('17', '1', '44');
INSERT INTO `t_user_role` VALUES ('18', '3', '37');
INSERT INTO `t_user_role` VALUES ('19', '2', '37');

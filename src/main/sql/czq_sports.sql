/*
Navicat MySQL Data Transfer

Source Server         : 47.107.79.61
Source Server Version : 50642
Source Host           : 47.107.79.61:3306
Source Database       : czq

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-09-18 17:44:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号';

-- ----------------------------
-- Records of account
-- ----------------------------

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `ct` datetime DEFAULT NULL,
  `coach` varchar(255) DEFAULT NULL COMMENT '教练',
  `leader` varchar(255) DEFAULT NULL COMMENT '领队',
  `gid` int(11) DEFAULT NULL COMMENT 'group id',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系人号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES ('1', '19技师模具', '2019-09-17 21:18:42', '教练哥', '联系人是我', '1', '1.3859930921E10');

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '分组名称',
  `ct` datetime DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '0启用 1禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='分组表：如需按照年级分组';

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES ('1', '高职', '2019-09-09 16:14:26', '0');
INSERT INTO `group` VALUES ('3', '中职', '2019-09-10 11:16:33', '0');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '比赛项目名称',
  `limit` int(11) NOT NULL COMMENT '名额限制',
  `ct` datetime DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL COMMENT '比赛所需时长',
  `status` tinyint(4) DEFAULT '0' COMMENT '0启用 1禁用',
  `type` tinyint(4) DEFAULT NULL COMMENT '0径赛 1田赛',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='比赛项目表：名称、名额限制、比赛时间等属性';

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', '男子100米', '2', '2019-09-10 11:27:30', null, '0', '0');
INSERT INTO `project` VALUES ('2', '男子200米', '2', '2019-09-10 11:27:41', null, '0', '0');
INSERT INTO `project` VALUES ('3', '男子400米', '2', '2019-09-10 11:27:53', null, '0', '0');
INSERT INTO `project` VALUES ('4', '男子800米', '2', '2019-09-10 11:28:04', null, '0', '0');
INSERT INTO `project` VALUES ('5', '男子1500米', '2', '2019-09-10 11:28:16', null, '0', '0');
INSERT INTO `project` VALUES ('6', '男子5000米', '2', '2019-09-10 11:28:32', null, '0', '0');
INSERT INTO `project` VALUES ('7', '男子110米栏', '2', '2019-09-10 11:30:28', null, '0', '0');
INSERT INTO `project` VALUES ('8', '男子跳高', '2', '2019-09-10 11:30:40', null, '0', '1');
INSERT INTO `project` VALUES ('9', '男子跳远', '2', '2019-09-10 11:30:51', null, '0', '1');
INSERT INTO `project` VALUES ('10', '男子三级跳远', '2', '2019-09-10 11:31:00', null, '0', '1');
INSERT INTO `project` VALUES ('11', '男子铅球', '2', '2019-09-10 11:31:10', null, '0', '1');
INSERT INTO `project` VALUES ('12', '男子4×100接力', '1', '2019-09-10 11:31:50', null, '0', '0');
INSERT INTO `project` VALUES ('13', '男子4×400接力', '1', '2019-09-10 11:31:52', null, '0', '0');
INSERT INTO `project` VALUES ('14', '女子100米', '2', '2019-09-10 11:27:30', null, '0', '0');
INSERT INTO `project` VALUES ('15', '女子200米', '2', '2019-09-10 11:27:41', null, '0', '0');
INSERT INTO `project` VALUES ('16', '女子400米', '2', '2019-09-10 11:27:53', null, '0', '0');
INSERT INTO `project` VALUES ('17', '女子800米', '2', '2019-09-10 11:28:04', null, '0', '0');
INSERT INTO `project` VALUES ('18', '女子1500米', '2', '2019-09-10 11:28:16', null, '0', '0');
INSERT INTO `project` VALUES ('19', '女子5000米', '2', '2019-09-10 11:28:32', null, '0', '0');
INSERT INTO `project` VALUES ('20', '女子110米栏', '2', '2019-09-10 11:30:28', null, '0', '0');
INSERT INTO `project` VALUES ('21', '女子跳高', '2', '2019-09-10 11:30:40', null, '0', '1');
INSERT INTO `project` VALUES ('22', '女子跳远', '2', '2019-09-10 11:30:51', null, '0', '1');
INSERT INTO `project` VALUES ('23', '女子三级跳远', '2', '2019-09-10 11:31:00', null, '0', '1');
INSERT INTO `project` VALUES ('24', '女子铅球', '2', '2019-09-10 11:31:10', null, '0', '1');
INSERT INTO `project` VALUES ('25', '女子4×100接力', '1', '2019-09-10 11:31:50', null, '0', '0');
INSERT INTO `project` VALUES ('26', '女子4×400接力', '1', '2019-09-10 11:31:52', null, '0', '0');

-- ----------------------------
-- Table structure for sport
-- ----------------------------
DROP TABLE IF EXISTS `sport`;
CREATE TABLE `sport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `ct` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运动会表';

-- ----------------------------
-- Records of sport
-- ----------------------------

-- ----------------------------
-- Table structure for sport_group
-- ----------------------------
DROP TABLE IF EXISTS `sport_group`;
CREATE TABLE `sport_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '运动会id',
  `gid` int(11) NOT NULL COMMENT '分组id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运动会-分组关联表';

-- ----------------------------
-- Records of sport_group
-- ----------------------------

-- ----------------------------
-- Table structure for sport_project
-- ----------------------------
DROP TABLE IF EXISTS `sport_project`;
CREATE TABLE `sport_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '运动会id',
  `pid` int(11) NOT NULL COMMENT '比赛项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运动会-比赛项目关联表';

-- ----------------------------
-- Records of sport_project
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(255) DEFAULT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `ct` datetime DEFAULT NULL COMMENT '创建时间',
  `cid` int(11) DEFAULT NULL COMMENT 'class id',
  `gid` int(11) DEFAULT NULL COMMENT 'group id',
  `sex` tinyint(4) DEFAULT '0' COMMENT '性别 0男生 1女生',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=357 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('309', null, '陈一', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('310', null, '王二', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('311', null, '张三', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('312', null, '李四', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('313', null, '六六', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('314', null, '赵强', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('315', null, '王刚', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('316', null, '李毅', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('317', null, '郭艾伦', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('318', null, '易建联', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('319', null, '周琦', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('320', null, '赵硕', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('321', null, '方锐', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('322', null, '崔建', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('323', null, '岢岚', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('324', null, '阿布都', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('325', null, '詹姆斯', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('326', null, '勒布朗', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('327', null, '戴维斯', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('328', null, '韦德', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('329', null, '霍华德', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('330', null, '奥尼尔', '2019-09-17 21:17:44', '1', '1', '0');
INSERT INTO `student` VALUES ('331', null, '张靓颖', '2019-09-17 21:17:44', '1', '1', '1');
INSERT INTO `student` VALUES ('332', null, '范冰冰', '2019-09-17 21:17:44', '1', '1', '1');
INSERT INTO `student` VALUES ('333', null, '陈一', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('334', null, '王二', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('335', null, '张三', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('336', null, '李四', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('337', null, '六六', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('338', null, '赵强', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('339', null, '王刚', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('340', null, '李毅', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('341', null, '郭艾伦', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('342', null, '易建联', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('343', null, '周琦', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('344', null, '赵硕', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('345', null, '方锐', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('346', null, '崔建', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('347', null, '岢岚', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('348', null, '阿布都', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('349', null, '詹姆斯', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('350', null, '勒布朗', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('351', null, '戴维斯', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('352', null, '韦德', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('353', null, '霍华德', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('354', null, '奥尼尔', '2019-09-17 21:18:42', '1', '1', '0');
INSERT INTO `student` VALUES ('355', null, '张靓颖', '2019-09-17 21:18:42', '1', '1', '1');
INSERT INTO `student` VALUES ('356', null, '范冰冰', '2019-09-17 21:18:42', '1', '1', '1');

-- ----------------------------
-- Table structure for student_project
-- ----------------------------
DROP TABLE IF EXISTS `student_project`;
CREATE TABLE `student_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sid` int(11) DEFAULT NULL COMMENT 'student id',
  `pid` int(11) DEFAULT NULL COMMENT 'project id',
  `ct` datetime DEFAULT NULL,
  `cid` int(11) DEFAULT NULL COMMENT 'class id',
  `sname` varchar(50) DEFAULT NULL COMMENT '学生姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='学生-项目 报名表';

-- ----------------------------
-- Records of student_project
-- ----------------------------
INSERT INTO `student_project` VALUES ('1', null, '1', '2019-09-17 21:17:44', '1', '陈一');
INSERT INTO `student_project` VALUES ('2', null, '1', '2019-09-17 21:17:44', '1', '王二');
INSERT INTO `student_project` VALUES ('3', null, '2', '2019-09-17 21:17:44', '1', '张三');
INSERT INTO `student_project` VALUES ('4', null, '2', '2019-09-17 21:17:44', '1', '李四');
INSERT INTO `student_project` VALUES ('5', null, '3', '2019-09-17 21:17:44', '1', '六六');
INSERT INTO `student_project` VALUES ('6', null, '3', '2019-09-17 21:17:44', '1', '赵强');
INSERT INTO `student_project` VALUES ('7', null, '4', '2019-09-17 21:17:44', '1', '王刚');
INSERT INTO `student_project` VALUES ('8', null, '4', '2019-09-17 21:17:44', '1', '李毅');
INSERT INTO `student_project` VALUES ('9', null, '5', '2019-09-17 21:17:44', '1', '郭艾伦');
INSERT INTO `student_project` VALUES ('10', null, '5', '2019-09-17 21:17:44', '1', '易建联');
INSERT INTO `student_project` VALUES ('11', null, '6', '2019-09-17 21:17:44', '1', '周琦');
INSERT INTO `student_project` VALUES ('12', null, '6', '2019-09-17 21:17:44', '1', '赵硕');
INSERT INTO `student_project` VALUES ('13', null, '7', '2019-09-17 21:17:44', '1', '方锐');
INSERT INTO `student_project` VALUES ('14', null, '7', '2019-09-17 21:17:44', '1', '崔建');
INSERT INTO `student_project` VALUES ('15', null, '8', '2019-09-17 21:17:44', '1', '岢岚');
INSERT INTO `student_project` VALUES ('16', null, '8', '2019-09-17 21:17:44', '1', '阿布都');
INSERT INTO `student_project` VALUES ('17', null, '9', '2019-09-17 21:17:44', '1', '詹姆斯');
INSERT INTO `student_project` VALUES ('18', null, '9', '2019-09-17 21:17:44', '1', '勒布朗');
INSERT INTO `student_project` VALUES ('19', null, '10', '2019-09-17 21:17:44', '1', '戴维斯');
INSERT INTO `student_project` VALUES ('20', null, '10', '2019-09-17 21:17:44', '1', '韦德');
INSERT INTO `student_project` VALUES ('21', null, '11', '2019-09-17 21:17:44', '1', '霍华德');
INSERT INTO `student_project` VALUES ('22', null, '11', '2019-09-17 21:17:44', '1', '奥尼尔');
INSERT INTO `student_project` VALUES ('23', null, '14', '2019-09-17 21:17:44', '1', '张靓颖');
INSERT INTO `student_project` VALUES ('24', null, '14', '2019-09-17 21:17:44', '1', '范冰冰');
INSERT INTO `student_project` VALUES ('25', null, '1', '2019-09-17 21:18:42', '1', '陈一');
INSERT INTO `student_project` VALUES ('26', null, '1', '2019-09-17 21:18:42', '1', '王二');
INSERT INTO `student_project` VALUES ('27', null, '2', '2019-09-17 21:18:42', '1', '张三');
INSERT INTO `student_project` VALUES ('28', null, '2', '2019-09-17 21:18:42', '1', '李四');
INSERT INTO `student_project` VALUES ('29', null, '3', '2019-09-17 21:18:42', '1', '六六');
INSERT INTO `student_project` VALUES ('30', null, '3', '2019-09-17 21:18:42', '1', '赵强');
INSERT INTO `student_project` VALUES ('31', null, '4', '2019-09-17 21:18:42', '1', '王刚');
INSERT INTO `student_project` VALUES ('32', null, '4', '2019-09-17 21:18:42', '1', '李毅');
INSERT INTO `student_project` VALUES ('33', null, '5', '2019-09-17 21:18:42', '1', '郭艾伦');
INSERT INTO `student_project` VALUES ('34', null, '5', '2019-09-17 21:18:42', '1', '易建联');
INSERT INTO `student_project` VALUES ('35', null, '6', '2019-09-17 21:18:42', '1', '周琦');
INSERT INTO `student_project` VALUES ('36', null, '6', '2019-09-17 21:18:42', '1', '赵硕');
INSERT INTO `student_project` VALUES ('37', null, '7', '2019-09-17 21:18:42', '1', '方锐');
INSERT INTO `student_project` VALUES ('38', null, '7', '2019-09-17 21:18:42', '1', '崔建');
INSERT INTO `student_project` VALUES ('39', null, '8', '2019-09-17 21:18:42', '1', '岢岚');
INSERT INTO `student_project` VALUES ('40', null, '8', '2019-09-17 21:18:42', '1', '阿布都');
INSERT INTO `student_project` VALUES ('41', null, '9', '2019-09-17 21:18:42', '1', '詹姆斯');
INSERT INTO `student_project` VALUES ('42', null, '9', '2019-09-17 21:18:42', '1', '勒布朗');
INSERT INTO `student_project` VALUES ('43', null, '10', '2019-09-17 21:18:42', '1', '戴维斯');
INSERT INTO `student_project` VALUES ('44', null, '10', '2019-09-17 21:18:42', '1', '韦德');
INSERT INTO `student_project` VALUES ('45', null, '11', '2019-09-17 21:18:42', '1', '霍华德');
INSERT INTO `student_project` VALUES ('46', null, '11', '2019-09-17 21:18:42', '1', '奥尼尔');
INSERT INTO `student_project` VALUES ('47', null, '14', '2019-09-17 21:18:42', '1', '张靓颖');
INSERT INTO `student_project` VALUES ('48', null, '14', '2019-09-17 21:18:42', '1', '范冰冰');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '管理员', '123456');
INSERT INTO `user` VALUES ('2', 'account2', '李四', null);
INSERT INTO `user` VALUES ('3', 'account2', '王五', null);

-- ----------------------------
-- Procedure structure for p_update_no
-- ----------------------------
DROP PROCEDURE IF EXISTS `p_update_no`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `p_update_no`()
BEGIN
	#Routine body goes here...
set @num:=1000;
update student set no=(@num:= @num+1) ORDER BY cid , sex ;
END
;;
DELIMITER ;

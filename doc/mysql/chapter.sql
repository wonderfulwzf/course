/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50648
Source Host           : localhost:3306
Source Database       : course

Target Server Type    : MYSQL
Target Server Version : 50648
File Encoding         : 65001

Date: 2021-03-12 19:27:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `chapter`
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `id` char(8) NOT NULL DEFAULT '' COMMENT 'ID',
  `course_id` char(8) NOT NULL DEFAULT '' COMMENT '课程ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '大章',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of chapter
-- ----------------------------
INSERT INTO `chapter` VALUES ('00000001', '00000001', '王');
INSERT INTO `chapter` VALUES ('00000002', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000003', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000004', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000005', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000006', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000007', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000008', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000009', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000010', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000011', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000012', '00000001', '智');
INSERT INTO `chapter` VALUES ('00000013', '00000001', '智');

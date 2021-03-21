/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : course

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2021-03-21 14:16:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` char(8) NOT NULL DEFAULT '' COMMENT 'id',
  `parent` char(8) NOT NULL DEFAULT '' COMMENT '父id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('00000100', '00000000', '前端', '100');
INSERT INTO `category` VALUES ('00000101', '00000100', 'html/css', '101');
INSERT INTO `category` VALUES ('00000102', '00000100', 'js', '102');
INSERT INTO `category` VALUES ('00000103', '00000100', 'vue', '103');
INSERT INTO `category` VALUES ('hwlbBvLa', 'xbf80BZp', 'spring', '202');
INSERT INTO `category` VALUES ('wWiEGeoW', 'xbf80BZp', 'java', '201');
INSERT INTO `category` VALUES ('xbf80BZp', '00000000', '后端', '200');

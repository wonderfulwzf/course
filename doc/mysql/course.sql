/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : course

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2021-03-21 14:16:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` char(8) NOT NULL DEFAULT '' COMMENT 'id',
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `sunmary` varchar(200) DEFAULT '' COMMENT '概述',
  `time` int(11) DEFAULT '0' COMMENT '时长|单位秒',
  `price` decimal(10,2) DEFAULT '9.99' COMMENT '价格(元)',
  `image` varchar(100) DEFAULT NULL COMMENT '封面',
  `level` char(1) NOT NULL DEFAULT '1' COMMENT '级别',
  `charge` char(1) DEFAULT 'C' COMMENT '收费',
  `status` char(1) DEFAULT 'D' COMMENT '状态',
  `enroll` int(11) DEFAULT '0' COMMENT '报名数',
  `sort` int(11) DEFAULT NULL COMMENT '顺序',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('00000001', '倚天屠龙记', '倚天屠龙', '20', '19.10', '/static/image/课程封面4.jpg', '1', 'C', 'D', '100', '0', '2021-03-25 09:49:55', '2021-03-21 10:06:04');
INSERT INTO `course` VALUES ('kpkX6xNw', '斗罗大陆', '魂师', '21', '1.10', '/static/image/课程封面5.jpg', '2', 'F', 'P', '11', null, '2021-03-20 14:03:12', '2021-03-21 10:06:20');

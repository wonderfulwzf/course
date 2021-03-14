/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : course

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2021-03-14 13:51:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `section`
-- ----------------------------
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section` (
  `id` char(8) NOT NULL DEFAULT '' COMMENT 'ID',
  `title` varchar(50) DEFAULT '' COMMENT '标题',
  `course_id` char(8) DEFAULT NULL COMMENT '课程|course.id',
  `chapter_id` char(8) DEFAULT NULL COMMENT '大章|chapter.id',
  `video` varchar(200) DEFAULT NULL COMMENT '视频',
  `time` int(11) DEFAULT NULL COMMENT '时长',
  `charge` char(1) DEFAULT NULL COMMENT '收费:C收费F免费',
  `sort` int(11) DEFAULT NULL COMMENT '顺序',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of section
-- ----------------------------
INSERT INTO `section` VALUES ('00000001', '测试小节01', '00000001', '00000000', '', '500', 'F', '1', '2021-03-14 13:48:01', '2021-03-14 13:48:01');

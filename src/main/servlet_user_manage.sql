/*
 Navicat Premium Data Transfer

 Source Server         : Mysql57_3357
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3357
 Source Schema         : servlet_user_manage

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 02/05/2018 23:45:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade` int(11) NULL DEFAULT 1,
  `passwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `unknow` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'aaaaa1', 'aaaa1@sohu.com', 1, '123', '', '');
INSERT INTO `users` VALUES (2, 'aaaaa2', 'aaaa2@sohu.com', 1, '123', '', '');
INSERT INTO `users` VALUES (3, 'aaaaa3', 'aaaa3@sohu.com', 1, '123', '', '');
INSERT INTO `users` VALUES (4, 'aaaaa4', 'aaaa4@sohu.com', 1, '123', '', '');
INSERT INTO `users` VALUES (5, 'aaaaa5', 'aaaa5@sohu.com', 5, '123', '', '');

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : 106.12.153.115
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 106.12.153.115:3306
 Source Schema         : skrivet

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/09/2020 09:53:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for SKRIVET_EXCEPTION
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_EXCEPTION`;
CREATE TABLE `SKRIVET_EXCEPTION`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `INTERFACE_TAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口',
  `EXCEPTION_MSG` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常信息',
  `EXCEPTION_DETAIL` blob NOT NULL COMMENT '异常详情',
  `HAPPEN_TIME` datetime(0) NOT NULL COMMENT '报错时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

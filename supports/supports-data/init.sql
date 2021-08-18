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

 Date: 10/09/2020 09:55:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for SKRIVET_DICT
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_DICT`;
CREATE TABLE `SKRIVET_DICT`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键编号',
  `TEXT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文本内容',
  `VALUE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '值',
  `REMARK` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `ORDER_NUM` bigint(0) NOT NULL DEFAULT 0 COMMENT '排序号',
  `GROUP_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组编号',
  `GROUP_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组名',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SKRIVET_DICT
-- ----------------------------
INSERT INTO `SKRIVET_DICT` VALUES ('2ae7bfd00b7d4e61b13972e36509bba7', '否', '0', NULL, 2, 'test', '测试字典');
INSERT INTO `SKRIVET_DICT` VALUES ('f6bc1571232b49a4b6aa5c46d83a2b3b', '是', '1', NULL, 1, 'test', '测试字典');

-- ----------------------------
-- Table structure for SKRIVET_SYSTEM
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_SYSTEM`;
CREATE TABLE `SKRIVET_SYSTEM`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键编号',
  `TEXT` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文本内容',
  `SYS_VALUE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '值',
  `REMARK` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `ORDER_NUM` bigint(0) NOT NULL DEFAULT 0 COMMENT '排序号',
  `GROUP_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组编号',
  `GROUP_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组名',
  `SYS_KEY` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数key',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for SKRIVET_TREE
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_TREE`;
CREATE TABLE `SKRIVET_TREE`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键编号',
  `TEXT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文本内容',
  `VALUE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '值',
  `REMARK` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `ORDER_NUM` bigint(0) NOT NULL DEFAULT 0 COMMENT '排序号',
  `GROUP_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组编号',
  `GROUP_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组名',
  `PARENT_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级编号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '树结构表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SKRIVET_TREE
-- ----------------------------
INSERT INTO `SKRIVET_TREE` VALUES ('239fbefa8b1b4de5bb9684dd68e4ea89', '测试1', '2', '2', 2, 'test', '测试组', 'd9f41e1f7da745088b59a1e65f8b0b73');
INSERT INTO `SKRIVET_TREE` VALUES ('d9f41e1f7da745088b59a1e65f8b0b73', '根目录', '1', '1', 1, 'test', '测试组', NULL);

SET FOREIGN_KEY_CHECKS = 1;

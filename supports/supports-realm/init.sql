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

 Date: 10/09/2020 09:56:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for SKRIVET_DEPT
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_DEPT`;
CREATE TABLE `SKRIVET_DEPT`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据编号',
  `PARENT_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级编号',
  `TEXT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `CONTACT_PEOPLE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `CONTACT_PHONE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `CONTACT_EMAIL` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  `DEPT_DESCRIBE` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `ORDER_NUM` int(0) NOT NULL COMMENT '排序号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SKRIVET_DEPT
-- ----------------------------
INSERT INTO `SKRIVET_DEPT` VALUES ('18f473f16c934949b84879311b6b7ec2', NULL, 'test', NULL, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for SKRIVET_USER
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_USER`;
CREATE TABLE `SKRIVET_USER`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `USER_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `SALT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码盐值',
  `NICK_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `HEAD_PORTRAIT` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `PHONE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `EMAIL` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `ORG_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构编号',
  `CREATE_DATE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建日期',
  `LOGIN_COUNT` bigint(0) NOT NULL DEFAULT 0 COMMENT '登陆次数',
  `LAST_LOGIN_IP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后一次登陆的IP',
  `LAST_LOGIN_DATE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后一次登陆日期',
  `STATE` int(0) NULL DEFAULT 1 COMMENT '状态(0-禁用，1-正常)',
  `DISABLE_REASON` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户禁用原因',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of SKRIVET_USER
-- ----------------------------
INSERT INTO `SKRIVET_USER` VALUES ('da2c3e38052548a49049c9ded123d768', 'admin', '95c273355c1674538130c8e894c5122f40b05ffd', 'BEFC758D54FF42FC8E3E2C5748985CD9', '恩哥', 'http://106.12.153.115:1110/file/downLoad?id=430afef86c5f4dc58747a8a9adbfbfbf', '18615625210', '1107061838@qq.com', NULL, '2020-07-08 16:42:41', 275, '124.133.255.66', '2020-09-09 15:15:19', 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;

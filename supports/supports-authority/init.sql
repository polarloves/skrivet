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

 Date: 25/09/2020 12:08:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for SKRIVET_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_PERMISSION`;
CREATE TABLE `SKRIVET_PERMISSION`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据编号',
  `PARENT_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级编号',
  `VALUE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限标识',
  `TEXT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称',
  `REMARK` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `ORDER_NUM` int(0) NOT NULL COMMENT '排序号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SKRIVET_PERMISSION
-- ----------------------------
INSERT INTO `SKRIVET_PERMISSION` VALUES ('005a6427880642c98e35622e62bf215f', '5966d56b8dad45bb91e9868f14733a9e', 'skrivet:template:update', '修改模板', NULL, 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('058afcd62d3a4914898e6b5ebeece29c', '2ec089c2520b40e599d86934c3b582df', 'skrivet:dict:add', '添加字典', NULL, 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('05df074e5e704106a47cfa248696e7f1', '0f843dd989ea4d6f9779f5d0d1126d99', 'skrivet:user:disable', '禁用用户', NULL, 8);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('09bc1ee17cba4ccea83d4da5a8748460', NULL, 'skrivet:tree:basic', '树数据管理', NULL, 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('0b71b49aca454c25a2f0cdc5ec97c2f8', NULL, 'skrivet:resource:basic', '资源管理', '资源管理', 9);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('0d1fa499f3f146c08de4439acf29cddd', '10811fa8d49d462a9c5da78b3f45eede', 'skrivet:system:detail', '查询系统配置详情', NULL, 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('0d2140581dc94be8a7a5f7ea2d97c795', '0f843dd989ea4d6f9779f5d0d1126d99', 'skrivet:user:enable', '启用用户', NULL, 7);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('0f843dd989ea4d6f9779f5d0d1126d99', NULL, 'skrivet:user:basic', '用户管理', '用户管理', 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('104d04e5e3ce47d38486c34bbd070409', '09bc1ee17cba4ccea83d4da5a8748460', 'skrivet:tree:delete', '删除树数据', NULL, 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('10811fa8d49d462a9c5da78b3f45eede', NULL, 'skrivet:system:basic', '系统配置管理', NULL, 4);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('187907016e3d40a29c8deac52e507722', '46e0ccebedba4a30ac324eab6b0d83cd', 'skrivet:role:user:list', '查询用户角色列表', '查询用户角色列表', 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('18bb6312f14c413ebb449d89efb24063', NULL, 'skrivet:exception:basic', '异常日志', NULL, 12);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('1aa3d6213127426d86a041a39e635ef4', '2ec089c2520b40e599d86934c3b582df', 'skrivet:dict:groups', '字典分组', NULL, 6);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('1e32cefb03cf4f279c7d068200e83b2e', '46e0ccebedba4a30ac324eab6b0d83cd', 'skrivet:role:user:update', '修改用户角色', NULL, 7);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('21674f8eee294ffc85fbcd40bbc8fd5a', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:role:update', '修改角色权限', '修改角色权限', 7);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('2ec089c2520b40e599d86934c3b582df', NULL, 'skrivet:dict:basic', '字典管理', NULL, 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('31948484e002473d8eae2e202987e46f', NULL, 'skrivet:backstage', '后台管理', '后台管理的基础权限，只有有了此权限才能管理后台', 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('33481fe515d5482fa221d5fdbf67cadf', '0b71b49aca454c25a2f0cdc5ec97c2f8', 'skrivet:resource:update', '修改资源', '修改资源', 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('334c0b07e5e04ebbaa421ff4277d5643', '5966d56b8dad45bb91e9868f14733a9e', 'skrivet:template:delete', '删除模板', NULL, 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('41c4e01228ca4f86a3d49b5649f95632', '0b71b49aca454c25a2f0cdc5ec97c2f8', 'skrivet:resource:add', '添加资源', '添加资源', 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('4408555c98e0425b8bed2164aea86a68', '0f843dd989ea4d6f9779f5d0d1126d99', 'skrivet:activeUser:shotOff', '强制下线', NULL, 10);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('450ad926ba37401387a2393ae2fb0e07', '0f843dd989ea4d6f9779f5d0d1126d99', 'skrivet:user:add', '添加用户', '添加用户', 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('454df71934a948bfbdfe3df8036867f4', '2ec089c2520b40e599d86934c3b582df', 'skrivet:dict:update', '修改字典', NULL, 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('46e0ccebedba4a30ac324eab6b0d83cd', NULL, 'skrivet:role:basic', '角色管理', '角色管理', 7);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('4be319df033d48d29f99d9309140fb84', '09bc1ee17cba4ccea83d4da5a8748460', 'skrivet:tree:add', '添加树数据', NULL, 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('51cc1c9fb5d54c398cfba70420d9b795', '10811fa8d49d462a9c5da78b3f45eede', 'skrivet:system:update', '修改系统配置', NULL, 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('5966d56b8dad45bb91e9868f14733a9e', NULL, 'skrivet:template:basic', '代码生成-模板管理', NULL, 11);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('5b231310cbcf43448381663942b7e09a', 'b55a2b16e16c46189ebf3e56f64799d0', 'skrivet:form:list', '表单列表', NULL, 4);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('648c3873213641b8a873f45f6e820653', 'a8f9f98973d142d085f336b2b0973d67', 'skrivet:dept:detail', '查看部门详情', NULL, 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('66b0ad668bea40f3833dada114a3df5c', 'a8f9f98973d142d085f336b2b0973d67', 'skrivet:dept:delete', '删除部门', NULL, 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('6af4fde0f7844e1b95df88ba02edd547', '2ec089c2520b40e599d86934c3b582df', 'skrivet:dict:list', '字典列表', NULL, 4);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('6bc783701eca43248e88207146d3e387', '0f843dd989ea4d6f9779f5d0d1126d99', 'skrivet:user:update', '修改用户', '修改用户', 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('6c5e55b07436400c8a50c6052a19154c', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:detail', '查询权限详情', '查询权限详情', 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('710c217b695c434ba5af617cddab09a5', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:resource:update', '修改资源权限', '修改资源权限', 9);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('741f24be30754c3ea33fd40d23d735b7', 'b55a2b16e16c46189ebf3e56f64799d0', 'skrivet:form:add', '添加表单', NULL, 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('761c572027d34b1d88d00c89240159fd', 'b55a2b16e16c46189ebf3e56f64799d0', 'skrivet:form:detail', '表单详情', NULL, 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('76ea0df1cf0b43cf99f54cfc53ce28f0', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:role:list', '查询角色权限', '查询角色权限', 6);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('76f63e138240475296263a3d524c5d4b', '0b71b49aca454c25a2f0cdc5ec97c2f8', 'skrivet:resource:detail', '查询资源详情', '查询资源详情', 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('784ec342d226434f95ce4a375cf5544c', '0f843dd989ea4d6f9779f5d0d1126d99', 'skrivet:user:detail', '查看用户详情', '查看用户详情', 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('7ac87b9ca9214b078f0241ece7c699f9', 'a8f9f98973d142d085f336b2b0973d67', 'skrivet:dept:update', '修改部门', NULL, 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('7c3b46c15471494e83daae82eeab838f', '18bb6312f14c413ebb449d89efb24063', 'skrivet:exception:list', '查看异常日志列表', NULL, 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('84f808605b5646b99423e9fefbc0c7c2', '10811fa8d49d462a9c5da78b3f45eede', 'skrivet:system:groups', '系统配置分组信息', NULL, 6);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('894fcc46829f4335adb50d2fdd390317', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:list', '查询权限列表', '查询权限列表', 4);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('8bb9ddc3fe1e4acc893a5b6331c2a34c', '2ec089c2520b40e599d86934c3b582df', 'skrivet:dict:delete', '删除字典', NULL, 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('8dac2719ca3548fb9cbb4f24f1829fca', NULL, 'skrivet:permission:basic', '权限管理', '权限管理', 8);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('8de03eb99fe1473b87efa5563352a6b8', '0b71b49aca454c25a2f0cdc5ec97c2f8', 'skrivet:resource:delete', '删除资源', '删除资源', 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('8f9234a9295a4153975d8b095bfed608', '2ec089c2520b40e599d86934c3b582df', 'skrivet:dict:detail', '字典详情', NULL, 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('8ff6d6de615c458faf9381219d860a9c', '0f843dd989ea4d6f9779f5d0d1126d99', 'skrivet:activeUser:list', '在线用户列表', NULL, 9);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('937ccdadaa0a4e4793159254deec7be9', 'a8f9f98973d142d085f336b2b0973d67', 'skrivet:dept:add', '添加部门', NULL, 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('939e435356564ca7baf6d9e84f9613d4', 'b55a2b16e16c46189ebf3e56f64799d0', 'skrivet:form:delete', '删除表单', NULL, 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('9412517309bc44c2bf5ecacde67b05f7', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:resource:reload', '重新加载资源权限', '重新加载资源权限', 10);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('991dd0428f924d8db1525fd5f5ca1db0', '09bc1ee17cba4ccea83d4da5a8748460', 'skrivet:tree:update', '修改树数据', NULL, 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('9c671b96aae84ceeade723f449a8f41d', '5966d56b8dad45bb91e9868f14733a9e', 'skrivet:template:detail', '模板详情', NULL, 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('a58d7d4bc4024430ac6b28707022f50d', '09bc1ee17cba4ccea83d4da5a8748460', 'skrivet:tree:groups', '树分组信息', NULL, 6);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('a8b97317bd3546348de2d62e98c58def', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:update', '修改权限', '修改权限', 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('a8f9f98973d142d085f336b2b0973d67', NULL, 'skrivet:dept:basic', '部门管理', NULL, 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('ac01399cf7604e0b9a77f43b3908f880', '10811fa8d49d462a9c5da78b3f45eede', 'skrivet:system:add', '添加系统配置', NULL, 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('ad4aef63d8e743be826bea2d2220d0c9', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:add', '添加权限', '添加权限', 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('b55a2b16e16c46189ebf3e56f64799d0', NULL, 'skrivet:form:basic', '代码生成-表单管理', NULL, 10);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('b919969701be4a4eb7718dc1060f7ece', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:delete', '删除权限', '删除权限', 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('bb7ce28f073749acb2066fbbbad9f8cd', '5966d56b8dad45bb91e9868f14733a9e', 'skrivet:template:add', '添加模板', NULL, 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('bf9aa93036e647f0a164c355cd602b47', '18bb6312f14c413ebb449d89efb24063', 'skrivet:exception:delete', '删除异常日志', NULL, 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('c16fec57b7e242039d916bc6fab038ce', '0f843dd989ea4d6f9779f5d0d1126d99', 'skrivet:user:password:update', '修改密码', '修改密码', 6);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('cb5b796f46234e7786a287accb09fdd7', '09bc1ee17cba4ccea83d4da5a8748460', 'skrivet:tree:detail', '树详情', NULL, 5);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('cec2504c84d841169a51cdfb08f107ff', '09bc1ee17cba4ccea83d4da5a8748460', 'skrivet:tree:list', '树数据列表', NULL, 4);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('d3cf3560f73e4c189c5ae93f060beb8c', '46e0ccebedba4a30ac324eab6b0d83cd', 'skrivet:role:update', '修改角色', '修改角色', 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('d460d9dea666481db834cc2b20452d05', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:user:reload', '重新加载用户权限', '重新加载用户权限', 11);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('d4fd3e5ef6d64e6bb827c856542d99d3', '0f843dd989ea4d6f9779f5d0d1126d99', 'skrivet:user:delete', '删除用户', '删除用户', 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('d5bcb82222f04c45b8defd7f8f283ab7', '46e0ccebedba4a30ac324eab6b0d83cd', 'skrivet:role:delete', '删除角色', '删除角色', 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('d804c438596c4bee8fe66b065b8b0fad', '0f843dd989ea4d6f9779f5d0d1126d99', 'skrivet:user:list', '查看用户列表', '查看用户列表', 4);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('da1b2292a7c243af952c1c0fb95adf90', '46e0ccebedba4a30ac324eab6b0d83cd', 'skrivet:role:add', '添加角色', '添加角色', 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('de6b37bfa53d41aba565b8d624df20dc', '8dac2719ca3548fb9cbb4f24f1829fca', 'skrivet:permission:resource:list', '查询资源权限', '查询资源权限', 8);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('e87be89233834c43b1042ce8c9b4a7a8', '0b71b49aca454c25a2f0cdc5ec97c2f8', 'skrivet:resource:list', '查询资源列表', '查询资源列表', 4);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('ea9658c2232849f088d46f9c61c9a5b3', '10811fa8d49d462a9c5da78b3f45eede', 'skrivet:system:list', '查询系统配置列表', NULL, 4);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('eb2cb23c2a1e4e07bef8e56d7b877782', '5966d56b8dad45bb91e9868f14733a9e', 'skrivet:template:list', '模板列表', NULL, 4);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('ef7fc5c76f924b7a8b43505129401c31', 'a8f9f98973d142d085f336b2b0973d67', 'skrivet:dept:list', '查看部门列表', NULL, 4);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('f2054f24c5df4a1d8a1fdc3cd5503c29', '10811fa8d49d462a9c5da78b3f45eede', 'skrivet:system:delete', '删除系统配置', NULL, 2);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('f4137b7f5a524bd6bc7d9d080db5ed85', 'b55a2b16e16c46189ebf3e56f64799d0', 'skrivet:form:update', '修改表单', NULL, 3);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('f4fc569fd1524535ace09d93ebc72cd4', '46e0ccebedba4a30ac324eab6b0d83cd', 'skrivet:role:detail', '查询角色详情', '查询角色详情', 6);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('f6176c0e229a4b05ac6ea6f0e59247c4', '18bb6312f14c413ebb449d89efb24063', 'skrivet:exception:detail', '查看异常日志详情', NULL, 1);
INSERT INTO `SKRIVET_PERMISSION` VALUES ('fd3ecc92e793444a872482d553311740', '46e0ccebedba4a30ac324eab6b0d83cd', 'skrivet:role:list', '查询角色列表', '查询角色列表', 4);

-- ----------------------------
-- Table structure for SKRIVET_RESOURCE
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_RESOURCE`;
CREATE TABLE `SKRIVET_RESOURCE`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源编号',
  `VALUE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访问路径',
  `TEXT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `REMARK` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `ORDER_NUM` int(0) NOT NULL COMMENT '排序号',
  `TYPE` int(0) NOT NULL COMMENT '类型:1-接口,2-页面',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SKRIVET_RESOURCE
-- ----------------------------
INSERT INTO `SKRIVET_RESOURCE` VALUES ('6c2c1a579444428baaba2f6c3f86ce50', '^/authority/.{0,}$', 't', '1', 1, 2);

-- ----------------------------
-- Table structure for SKRIVET_RESOURCE_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_RESOURCE_PERMISSION`;
CREATE TABLE `SKRIVET_RESOURCE_PERMISSION`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据编号',
  `RESOURCE_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源编号',
  `PERMISSION_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限编号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源-权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SKRIVET_RESOURCE_PERMISSION
-- ----------------------------
INSERT INTO `SKRIVET_RESOURCE_PERMISSION` VALUES ('316d319678e447b1a821e1f685b1c90d', '6c2c1a579444428baaba2f6c3f86ce50', '454df71934a948bfbdfe3df8036867f4');
INSERT INTO `SKRIVET_RESOURCE_PERMISSION` VALUES ('77385869f6ab4823ad4133ca99362884', '6c2c1a579444428baaba2f6c3f86ce50', '8f9234a9295a4153975d8b095bfed608');
INSERT INTO `SKRIVET_RESOURCE_PERMISSION` VALUES ('7dc5c24298c54b2a9dbce1cc489fba34', '6c2c1a579444428baaba2f6c3f86ce50', '058afcd62d3a4914898e6b5ebeece29c');
INSERT INTO `SKRIVET_RESOURCE_PERMISSION` VALUES ('815acdb8c4fd4bd3b2a18254aa86e41d', '6c2c1a579444428baaba2f6c3f86ce50', '8bb9ddc3fe1e4acc893a5b6331c2a34c');
INSERT INTO `SKRIVET_RESOURCE_PERMISSION` VALUES ('832af987236c44d49057637620300e56', '6c2c1a579444428baaba2f6c3f86ce50', '6af4fde0f7844e1b95df88ba02edd547');
INSERT INTO `SKRIVET_RESOURCE_PERMISSION` VALUES ('c351a4cfa5674920ba08d348d45d39fc', '6c2c1a579444428baaba2f6c3f86ce50', '1aa3d6213127426d86a041a39e635ef4');
INSERT INTO `SKRIVET_RESOURCE_PERMISSION` VALUES ('c7dab22259f447caac93dd02dc8caaac', '6c2c1a579444428baaba2f6c3f86ce50', '31948484e002473d8eae2e202987e46f');
INSERT INTO `SKRIVET_RESOURCE_PERMISSION` VALUES ('d130804a8cac44b5a5ddc5e7115e5071', '6c2c1a579444428baaba2f6c3f86ce50', '2ec089c2520b40e599d86934c3b582df');

-- ----------------------------
-- Table structure for SKRIVET_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_ROLE`;
CREATE TABLE `SKRIVET_ROLE`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编号',
  `VALUE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色标识',
  `TEXT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `REMARK` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `ORDER_NUM` int(0) NOT NULL COMMENT '排序号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SKRIVET_ROLE
-- ----------------------------
INSERT INTO `SKRIVET_ROLE` VALUES ('4f6abb00c27145f89bfb397d83b168d8', 'system', '系统管理员', '系统管理员', 1);

-- ----------------------------
-- Table structure for SKRIVET_ROLE_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_ROLE_PERMISSION`;
CREATE TABLE `SKRIVET_ROLE_PERMISSION`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据编号',
  `ROLE_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编号',
  `PERMISSION_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限编号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SKRIVET_ROLE_PERMISSION
-- ----------------------------
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('0395b97b2743420b9affb3d55bd2f344', '4f6abb00c27145f89bfb397d83b168d8', '8ff6d6de615c458faf9381219d860a9c');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('09d765da7e5a4d299a46304f389dbba6', '4f6abb00c27145f89bfb397d83b168d8', '76f63e138240475296263a3d524c5d4b');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('09e2f675ed9b4ae49609eb49710364fb', '4f6abb00c27145f89bfb397d83b168d8', 'f4fc569fd1524535ace09d93ebc72cd4');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('0a6df10bba4d48e58d92f06645ae25db', '4f6abb00c27145f89bfb397d83b168d8', 'c16fec57b7e242039d916bc6fab038ce');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('0af47a57c50443c89ef305435886c586', '4f6abb00c27145f89bfb397d83b168d8', '937ccdadaa0a4e4793159254deec7be9');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('0b225ade6a6f4f41a058737a35752c1e', '4f6abb00c27145f89bfb397d83b168d8', '6af4fde0f7844e1b95df88ba02edd547');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('0c76aa7f2c77493cba1d68f1c38c589c', '4f6abb00c27145f89bfb397d83b168d8', '8f9234a9295a4153975d8b095bfed608');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('0dae4ae160384f9bab9860d61afd515e', '4f6abb00c27145f89bfb397d83b168d8', '9412517309bc44c2bf5ecacde67b05f7');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('14c7adbe7ecd4922b28aed49a302880d', '4f6abb00c27145f89bfb397d83b168d8', 'd3cf3560f73e4c189c5ae93f060beb8c');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('187bc0a8bda84c9abdb3a3d5f85ee1e7', '4f6abb00c27145f89bfb397d83b168d8', 'bf9aa93036e647f0a164c355cd602b47');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('1dd310c8f5d84d6aaee65e7613b43ae3', '4f6abb00c27145f89bfb397d83b168d8', '1aa3d6213127426d86a041a39e635ef4');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('1edfd74ee4dc4d7a8456f38b5822b957', '4f6abb00c27145f89bfb397d83b168d8', '05df074e5e704106a47cfa248696e7f1');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('20da1f6347814487828919883994dd5c', '4f6abb00c27145f89bfb397d83b168d8', '10811fa8d49d462a9c5da78b3f45eede');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('21707b9b9e954c9bad5864539ef5c786', '4f6abb00c27145f89bfb397d83b168d8', '84f808605b5646b99423e9fefbc0c7c2');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('247b3c2d5c264cd7894e197dd27dbd7d', '4f6abb00c27145f89bfb397d83b168d8', 'bb7ce28f073749acb2066fbbbad9f8cd');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('2ea30fd82dac4e1181db7df3ba909e68', '4f6abb00c27145f89bfb397d83b168d8', '51cc1c9fb5d54c398cfba70420d9b795');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('312f420b2a824a6db7f4ae75cdc095bc', '4f6abb00c27145f89bfb397d83b168d8', 'd4fd3e5ef6d64e6bb827c856542d99d3');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('322a84dfaf78409b9963b69846e43337', '4f6abb00c27145f89bfb397d83b168d8', '450ad926ba37401387a2393ae2fb0e07');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('35c5787c710e4be8887f96f5ac7e6f05', '4f6abb00c27145f89bfb397d83b168d8', '8de03eb99fe1473b87efa5563352a6b8');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('36345a542b80431e8a4cbdcb6d6d7223', '4f6abb00c27145f89bfb397d83b168d8', 'ad4aef63d8e743be826bea2d2220d0c9');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('3c21d845db154e9fabdc5065158e55c5', '4f6abb00c27145f89bfb397d83b168d8', '2ec089c2520b40e599d86934c3b582df');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('3f47c5be542b434aac2a368bc6f5ebcf', '4f6abb00c27145f89bfb397d83b168d8', '761c572027d34b1d88d00c89240159fd');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('403fca87df7744aaafaaa640cac428df', '4f6abb00c27145f89bfb397d83b168d8', '005a6427880642c98e35622e62bf215f');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('436f0c64076546b3b990aab8f2d61c11', '4f6abb00c27145f89bfb397d83b168d8', '741f24be30754c3ea33fd40d23d735b7');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('4755fda79c7b4a58a8a6c53174125d30', '4f6abb00c27145f89bfb397d83b168d8', 'e87be89233834c43b1042ce8c9b4a7a8');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('4b48e23829e948feb0b5455cea65d083', '4f6abb00c27145f89bfb397d83b168d8', '187907016e3d40a29c8deac52e507722');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('4e96ace5931942f181d55cf1b4cea804', '4f6abb00c27145f89bfb397d83b168d8', '7ac87b9ca9214b078f0241ece7c699f9');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('510cd25adca9448bb6d908b00e34f446', '4f6abb00c27145f89bfb397d83b168d8', '0d1fa499f3f146c08de4439acf29cddd');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('51e7a27b83a3431d80c2c688b07b083d', '4f6abb00c27145f89bfb397d83b168d8', 'de6b37bfa53d41aba565b8d624df20dc');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('554d4abf4ff64592ad50a867d82e829c', '4f6abb00c27145f89bfb397d83b168d8', '058afcd62d3a4914898e6b5ebeece29c');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('5659e6a5ffd6439e958b72fac519fc67', '4f6abb00c27145f89bfb397d83b168d8', 'f2054f24c5df4a1d8a1fdc3cd5503c29');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('5b33e729549d447eb215bccac9e13ee1', '4f6abb00c27145f89bfb397d83b168d8', '0b71b49aca454c25a2f0cdc5ec97c2f8');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('5e0a2c69662e4b838ad654e2e4c01565', '4f6abb00c27145f89bfb397d83b168d8', '6bc783701eca43248e88207146d3e387');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('62cae827799a4fc5adcbbb267138bff2', '4f6abb00c27145f89bfb397d83b168d8', '09bc1ee17cba4ccea83d4da5a8748460');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('66c28d2f83db443ab56428ae5544a192', '4f6abb00c27145f89bfb397d83b168d8', '0f843dd989ea4d6f9779f5d0d1126d99');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('6bdb1d0ebc0f4b0b8cf61879c73b8b9e', '4f6abb00c27145f89bfb397d83b168d8', 'a8b97317bd3546348de2d62e98c58def');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('6c5992d407004d80880b6750c2589dd1', '4f6abb00c27145f89bfb397d83b168d8', 'ef7fc5c76f924b7a8b43505129401c31');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('704d0df7e1e4417a8b4aa0e24d112528', '4f6abb00c27145f89bfb397d83b168d8', 'a58d7d4bc4024430ac6b28707022f50d');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('7104d9047bdc41f39b3e094974c3dce9', '4f6abb00c27145f89bfb397d83b168d8', '939e435356564ca7baf6d9e84f9613d4');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('79c17fc5c34c43bbbdcf4f6ced7503a2', '4f6abb00c27145f89bfb397d83b168d8', '104d04e5e3ce47d38486c34bbd070409');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('7ab872cda6324c57af7b7bf4369f9000', '4f6abb00c27145f89bfb397d83b168d8', 'eb2cb23c2a1e4e07bef8e56d7b877782');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('7ba8b00b1d074301a791fb34ffb11d2a', '4f6abb00c27145f89bfb397d83b168d8', 'da1b2292a7c243af952c1c0fb95adf90');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('85633b3a574648e393d73c2f7a147e44', '4f6abb00c27145f89bfb397d83b168d8', 'b55a2b16e16c46189ebf3e56f64799d0');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('86fcf4193901479283fa18d7f05d40e8', '4f6abb00c27145f89bfb397d83b168d8', '334c0b07e5e04ebbaa421ff4277d5643');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('8b036b3d15bd45848e5ca5a970734e06', '4f6abb00c27145f89bfb397d83b168d8', '21674f8eee294ffc85fbcd40bbc8fd5a');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('8c91d7e477424c429759761f6d4a2ea0', '4f6abb00c27145f89bfb397d83b168d8', '18bb6312f14c413ebb449d89efb24063');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('9668c7bb987d4ee6b8aa99e1559dd7ce', '4f6abb00c27145f89bfb397d83b168d8', '76ea0df1cf0b43cf99f54cfc53ce28f0');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('9799444ae31347158d18086eb46c2d7b', '4f6abb00c27145f89bfb397d83b168d8', '4408555c98e0425b8bed2164aea86a68');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('99093493aef847d698e0f8c69d99012e', '4f6abb00c27145f89bfb397d83b168d8', 'b919969701be4a4eb7718dc1060f7ece');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('9d697b28d26b4fe0a6b08f5df57effb6', '4f6abb00c27145f89bfb397d83b168d8', '46e0ccebedba4a30ac324eab6b0d83cd');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('9debb4e6eb67478a87d7bbc3fcb766df', '4f6abb00c27145f89bfb397d83b168d8', 'cec2504c84d841169a51cdfb08f107ff');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('a2c7160347324b45b66c06bd807a6259', '4f6abb00c27145f89bfb397d83b168d8', '894fcc46829f4335adb50d2fdd390317');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('a7e18ef7caaf43068397b9eef3b7b41e', '4f6abb00c27145f89bfb397d83b168d8', '1e32cefb03cf4f279c7d068200e83b2e');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('aaf0e584749e4c6997b8f64e6490aea8', '4f6abb00c27145f89bfb397d83b168d8', '8bb9ddc3fe1e4acc893a5b6331c2a34c');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('ac452b9dc0b14b38a689d7db6986d3ca', '4f6abb00c27145f89bfb397d83b168d8', '5966d56b8dad45bb91e9868f14733a9e');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('af0769f4d277460988feed6c546d6520', '4f6abb00c27145f89bfb397d83b168d8', '991dd0428f924d8db1525fd5f5ca1db0');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('af3e29c621544e4598cd6f7734a8c563', '4f6abb00c27145f89bfb397d83b168d8', 'f6176c0e229a4b05ac6ea6f0e59247c4');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('b225e0c426114e4eafe45d0e81296a7d', '4f6abb00c27145f89bfb397d83b168d8', 'ea9658c2232849f088d46f9c61c9a5b3');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('b29ecf1c7a384a2d8d04375d8dd7ea8c', '4f6abb00c27145f89bfb397d83b168d8', '0d2140581dc94be8a7a5f7ea2d97c795');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('b3e62f1d9c704b82953c8704a27cdcdb', '4f6abb00c27145f89bfb397d83b168d8', '784ec342d226434f95ce4a375cf5544c');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('b5a2d9e1531a4f738e601b8905902edc', '4f6abb00c27145f89bfb397d83b168d8', 'fd3ecc92e793444a872482d553311740');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('b6ae7a26b5cb410c8f7c7f0e9acc81b0', '4f6abb00c27145f89bfb397d83b168d8', '4be319df033d48d29f99d9309140fb84');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('c2f00560b3e04b1090d338c4906a09c6', '4f6abb00c27145f89bfb397d83b168d8', '6c5e55b07436400c8a50c6052a19154c');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('c4fc8dfedd9245d2b30a923367ae79d8', '4f6abb00c27145f89bfb397d83b168d8', 'ac01399cf7604e0b9a77f43b3908f880');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('cadc89a819ac4b68a7475c338350ac1f', '4f6abb00c27145f89bfb397d83b168d8', 'f4137b7f5a524bd6bc7d9d080db5ed85');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('caddeb55adff483a926c944c08e19dab', '4f6abb00c27145f89bfb397d83b168d8', '648c3873213641b8a873f45f6e820653');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('cfdec68f62f1413d8547be18009583d3', '4f6abb00c27145f89bfb397d83b168d8', '8dac2719ca3548fb9cbb4f24f1829fca');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('d0c3867d626148d8ba59e12a0fa0cc17', '4f6abb00c27145f89bfb397d83b168d8', '9c671b96aae84ceeade723f449a8f41d');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('d744a425c6ba467e95b35f6e6c5678c8', '4f6abb00c27145f89bfb397d83b168d8', 'a8f9f98973d142d085f336b2b0973d67');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('dacd03c9e2cc4163a28ad1d08af48c0a', '4f6abb00c27145f89bfb397d83b168d8', '66b0ad668bea40f3833dada114a3df5c');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('e144af4ad2b9419bbd635abeb85bc046', '4f6abb00c27145f89bfb397d83b168d8', '454df71934a948bfbdfe3df8036867f4');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('e156bd6437954fb0b223bb370dea54e5', '4f6abb00c27145f89bfb397d83b168d8', 'd804c438596c4bee8fe66b065b8b0fad');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('e68b04a7039349f68a07c1ead7a5e4b3', '4f6abb00c27145f89bfb397d83b168d8', '5b231310cbcf43448381663942b7e09a');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('e944d55854994533b2686d7733d61118', '4f6abb00c27145f89bfb397d83b168d8', 'd460d9dea666481db834cc2b20452d05');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('eb61d20eac3d45e68fec894e3e440e9f', '4f6abb00c27145f89bfb397d83b168d8', '33481fe515d5482fa221d5fdbf67cadf');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('ed96c393e91848c68676b9de413e125f', '4f6abb00c27145f89bfb397d83b168d8', '7c3b46c15471494e83daae82eeab838f');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('eeb39fb82eec4dfe96c317d76d820de5', '4f6abb00c27145f89bfb397d83b168d8', 'cb5b796f46234e7786a287accb09fdd7');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('ef48b14f596345fb81d9b66c8120887c', '4f6abb00c27145f89bfb397d83b168d8', 'd5bcb82222f04c45b8defd7f8f283ab7');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('f9dadf35137d41e18f36fef9c78e1160', '4f6abb00c27145f89bfb397d83b168d8', '41c4e01228ca4f86a3d49b5649f95632');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('fb600c76dac74233b70535756e7ec5f7', '4f6abb00c27145f89bfb397d83b168d8', '31948484e002473d8eae2e202987e46f');
INSERT INTO `SKRIVET_ROLE_PERMISSION` VALUES ('fc04a1ee068b45bca82076a21ea9e017', '4f6abb00c27145f89bfb397d83b168d8', '710c217b695c434ba5af617cddab09a5');

-- ----------------------------
-- Table structure for SKRIVET_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SKRIVET_USER_ROLE`;
CREATE TABLE `SKRIVET_USER_ROLE`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据编号',
  `ROLE_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编号',
  `USER_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SKRIVET_USER_ROLE
-- ----------------------------
INSERT INTO `SKRIVET_USER_ROLE` VALUES ('1c63f5d35bb0403aa51dace862bf1081', '4f6abb00c27145f89bfb397d83b168d8', 'da2c3e38052548a49049c9ded123d768');

SET FOREIGN_KEY_CHECKS = 1;
/*
 Navicat Premium Data Transfer

 Source Server         : MyConnection
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : invest_track_pro

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 23/05/2024 16:39:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for asset
-- ----------------------------
DROP TABLE IF EXISTS `asset`;
CREATE TABLE `asset`  (
  `asset_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'asset_id',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `investment_id` int(0) NULL DEFAULT NULL COMMENT '投资项目的id',
  `amount` double NULL DEFAULT NULL COMMENT '持有数量',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  PRIMARY KEY (`asset_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `investment_id`(`investment_id`) USING BTREE,
  CONSTRAINT `asset_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `asset_ibfk_2` FOREIGN KEY (`investment_id`) REFERENCES `investment` (`investment_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of asset
-- ----------------------------
INSERT INTO `asset` VALUES (1, 2, 116, 0, '2024-05-23 16:37:50', '2024-05-23 16:37:51');
INSERT INTO `asset` VALUES (2, 2, 123, 2.5, '2024-05-23 16:37:50', '2024-05-23 16:37:51');
INSERT INTO `asset` VALUES (3, 2, 114, 2, '2024-05-23 16:37:50', '2024-05-23 16:37:51');
INSERT INTO `asset` VALUES (4, 2, 112, 1246, '2024-05-23 16:37:50', '2024-05-23 16:37:51');
INSERT INTO `asset` VALUES (5, 2, 113, 26.6, '2024-05-23 16:37:50', '2024-05-23 16:37:51');
INSERT INTO `asset` VALUES (6, 2, 117, 22, '2024-05-23 16:37:50', '2024-05-23 16:37:51');

-- ----------------------------
-- Table structure for investment
-- ----------------------------
DROP TABLE IF EXISTS `investment`;
CREATE TABLE `investment`  (
  `investment_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '投资项目id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '投资项目名',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '投资项目描述',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '投资项目分类',
  `initial_value` double NULL DEFAULT NULL COMMENT '初始市值',
  `current_value` double NULL DEFAULT NULL COMMENT '当前市值',
  `expected_return` double NULL DEFAULT NULL COMMENT '预期回报',
  `risk_level` int(0) NULL DEFAULT 0 COMMENT '风险等级',
  `created_at` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '创建时间戳',
  `update_at` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '上次更新时间戳',
  PRIMARY KEY (`investment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 141 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of investment
-- ----------------------------
INSERT INTO `investment` VALUES (111, '纳斯达克指数基金', '跟踪纳斯达克综合指数的表现', '基金', 100000, 120000, 20000, 4, '2024-01-01 10:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (112, '上证50ETF', '反映上证50指数的ETF', '基金', 50000, 55000, 5000, 3, '2023-12-01 09:30:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (113, '美国国债', '美国政府发行的低风险债券', '债券', 75000, 75200, 200, 1, '2023-08-15 14:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (114, '标普500指数基金', '复制标准普尔500指数的表现', '基金', 120000, 130000, 10000, 2, '2024-02-01 11:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (115, '阿里巴巴股票', '中国最大的电子商务公司之一', '股票', 200000, 250000, 50000, 4, '2023-11-20 13:45:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (116, '房地产信托基金', '投资于多个房地产项目的REIT', '房地产', 150000, 180000, 30000, 3, '2023-07-10 10:15:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (117, '黄金现货', '投资于黄金实物或黄金合约', '大宗商品', 30000, 31000, 1000, 2, '2024-03-01 16:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (118, '纽约证券交易所', '美国最大的股票交易市场之一', '股票', 180000, 185000, 5000, 3, '2023-09-01 14:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (119, '铜期货', '投资于铜的期货合约', '大宗商品', 100000, 102000, 2000, 3, '2023-10-10 09:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (120, '东京房地产市场', '投资于东京市区的商业和居住物业', '房地产', 250000, 270000, 20000, 2, '2024-01-20 15:30:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (121, '纽约时报股票', '美国著名的新闻媒体公司股票', '股票', 90000, 95000, 5000, 3, '2024-02-01 12:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (122, '欧洲央行债券', '欧洲中央银行发行的稳定收益债券', '债券', 50000, 50200, 200, 1, '2023-10-25 11:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (123, '阿姆斯特丹REIT', '投资于荷兰阿姆斯特丹的房地产', '房地产', 110000, 115000, 5000, 2, '2023-12-15 15:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (124, '中国银行股票', '中国的大型国有银行股票', '股票', 200000, 220000, 20000, 2, '2023-10-01 09:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (125, '硅谷科技基金', '投资于美国硅谷地区科技公司的基金', '基金', 150000, 165000, 15000, 4, '2024-03-21 14:30:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (126, '沪深300指数基金', '跟踪沪深300指数的表现', '基金', 80000, 84000, 4000, 3, '2023-11-15 16:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (127, '南非黄金矿业', '南非的主要黄金矿业投资项目', '大宗商品', 95000, 98000, 3000, 3, '2023-09-20 17:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (128, '布拉德利大厦', '位于芝加哥的标志性办公楼投资', '房地产', 250000, 275000, 25000, 2, '2023-08-10 10:15:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (129, '石油期货合约', '投资于国际市场的石油期货', '大宗商品', 60000, 63000, 3000, 4, '2024-01-05 13:45:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (130, 'Facebook股票', '全球最大的社交媒体网络公司之一', '股票', 210000, 250000, 40000, 3, '2024-04-25 11:30:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (131, '德国政府债券', '德国政府发行的低风险债券', '债券', 100000, 100200, 200, 1, '2023-10-20 09:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (132, '新加坡房地产基金', '专注于新加坡市场的房地产投资基金', '房地产', 130000, 135000, 5000, 2, '2024-03-15 10:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (133, '银行间债券市场基金', '投资于亚洲银行间债券市场', '债券', 85000, 86000, 1000, 2, '2024-02-18 14:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (134, '国际大豆市场', '投资于国际大豆贸易市场', '大宗商品', 40000, 42000, 2000, 3, '2024-05-01 12:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (135, '东京证券交易所', '日本最大的证券交易所股票', '股票', 190000, 195000, 5000, 3, '2023-08-30 15:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (136, '香港商业地产', '投资于香港的商业房地产', '房地产', 180000, 200000, 20000, 2, '2023-07-22 10:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (137, '钯金投资项目', '涉及钯金的采矿和贸易投资', '大宗商品', 75000, 77000, 2000, 4, '2023-09-11 16:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (138, '亚马逊股票', '全球最大的电子商务和云计算公司之一', '股票', 240000, 300000, 60000, 3, '2024-04-10 12:30:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (139, '北欧房地产投资', '涵盖瑞典、挪威和丹麦的房地产投资', '房地产', 160000, 168000, 8000, 2, '2023-10-05 14:00:00.000000', '2024-05-20 10:00:00.000000');
INSERT INTO `investment` VALUES (140, '铜期货投资', '专注于国际铜市场的期货合约', '大宗商品', 50000, 53000, 3000, 3, '2024-01-15 11:00:00.000000', '2024-05-20 10:00:00.000000');

-- ----------------------------
-- Table structure for investment_record
-- ----------------------------
DROP TABLE IF EXISTS `investment_record`;
CREATE TABLE `investment_record`  (
  `investment_record_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '投资记录id',
  `investment_id` int(0) NULL DEFAULT NULL COMMENT '投资选项id',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `amount` double NULL DEFAULT NULL COMMENT '数量',
  `current_prize` double NULL DEFAULT NULL COMMENT '交易发生时的价格',
  `operation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '\"买入\"或\"卖出\"',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '状态',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时的时间戳',
  `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时的时间戳',
  `asset_id` int(0) NULL DEFAULT NULL COMMENT '用户资产id',
  PRIMARY KEY (`investment_record_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `investment_id`(`investment_id`) USING BTREE,
  INDEX `asset_id`(`asset_id`) USING BTREE,
  CONSTRAINT `investment_record_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `investment_record_ibfk_2` FOREIGN KEY (`investment_id`) REFERENCES `investment` (`investment_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `investment_record_ibfk_3` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`asset_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of investment_record
-- ----------------------------
INSERT INTO `investment_record` VALUES (3, 111, 2, 12, 120000, NULL, '进行中', '2024-05-22 16:53:06', '2024-05-22 16:53:06', NULL);
INSERT INTO `investment_record` VALUES (4, 111, 2, 0.1, 120000, NULL, '进行中', '2024-05-22 16:55:41', '2024-05-22 16:55:41', NULL);
INSERT INTO `investment_record` VALUES (5, 112, 2, 12, 55000, NULL, '进行中', '2024-05-23 14:30:57', '2024-05-23 14:30:57', NULL);
INSERT INTO `investment_record` VALUES (6, 113, 2, 22, 75200, NULL, '进行中', '2024-05-23 14:33:14', '2024-05-23 14:33:14', NULL);
INSERT INTO `investment_record` VALUES (7, 112, 2, 123, 55000, NULL, '进行中', '2024-05-23 14:44:17', '2024-05-23 14:44:17', NULL);
INSERT INTO `investment_record` VALUES (8, 112, 2, 123, 55000, NULL, '进行中', '2024-05-23 14:44:22', '2024-05-23 14:44:22', NULL);
INSERT INTO `investment_record` VALUES (9, 111, 2, 1, 120000, NULL, '进行中', '2024-05-23 14:48:04', '2024-05-23 14:48:04', NULL);
INSERT INTO `investment_record` VALUES (10, 111, 2, 1, 120000, NULL, '进行中', '2024-05-23 14:48:29', '2024-05-23 14:48:29', NULL);
INSERT INTO `investment_record` VALUES (11, 116, 2, 2, 180000, NULL, '进行中', '2024-05-23 15:32:52', '2024-05-23 15:32:52', NULL);
INSERT INTO `investment_record` VALUES (12, 123, 2, 2.5, 115000, '买入', '进行中', '2024-05-23 15:35:55', '2024-05-23 15:35:55', NULL);
INSERT INTO `investment_record` VALUES (13, 114, 2, 2, 130000, '买入', '进行中', '2024-05-23 15:49:54', '2024-05-23 15:49:54', 3);
INSERT INTO `investment_record` VALUES (14, 112, 2, 1234, 55000, '买入', '进行中', '2024-05-23 15:55:14', '2024-05-23 15:55:14', 4);
INSERT INTO `investment_record` VALUES (15, 113, 2, 12, 75200, '买入', '进行中', '2024-05-23 15:56:50', '2024-05-23 15:56:50', 5);
INSERT INTO `investment_record` VALUES (16, 113, 2, 12.6, 75200, '买入', '进行中', '2024-05-23 15:59:52', '2024-05-23 15:59:52', 5);
INSERT INTO `investment_record` VALUES (17, 113, 2, 2, 75200, '买入', '进行中', '2024-05-23 16:14:34', '2024-05-23 16:14:34', 5);
INSERT INTO `investment_record` VALUES (18, 117, 2, 22, 31000, '买入', '进行中', '2024-05-23 16:29:54', '2024-05-23 16:29:54', 6);
INSERT INTO `investment_record` VALUES (19, 112, 2, 12, 55000, '买入', '进行中', '2024-05-23 16:37:34', '2024-05-23 16:37:34', 4);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名',
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时的时间戳',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'super', '123456', '22222@qq.com', '15888589665', 'yy', 's', '地球', NULL);
INSERT INTO `user` VALUES (2, '230551049110007', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '2058666094@qq.com', NULL, NULL, NULL, NULL, '2024-05-19 13:12:00');
INSERT INTO `user` VALUES (3, 'super', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'fuazying@gmail.com', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (4, 'super', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'cpy@qq.com', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (5, 'super', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'cp2y@qq.com', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (6, 'super', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '3333@rw.s', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (7, 'super', '6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918', '3333@rw.s2', NULL, NULL, NULL, NULL, '2024-05-19 12:29:10');

SET FOREIGN_KEY_CHECKS = 1;

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

 Date: 20/05/2024 12:44:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for investment
-- ----------------------------
DROP TABLE IF EXISTS `investment`;
CREATE TABLE `investment`  (
  `investment_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '投资项目id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '投资项目名',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '投资项目描述',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '投资项目分类',
  `initial_value` decimal(65, 0) NULL DEFAULT NULL COMMENT '初始市值',
  `current_value` decimal(65, 0) NULL DEFAULT NULL COMMENT '当前市值',
  `expected_return` decimal(65, 0) NULL DEFAULT NULL COMMENT '预期回报',
  `risk_level` decimal(65, 0) NULL DEFAULT NULL COMMENT '风险等级',
  `created_at` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '创建时间戳',
  `update_at` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '上次更新时间戳',
  PRIMARY KEY (`investment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of investment
-- ----------------------------
INSERT INTO `investment` VALUES (1, 'A股精选', '精选A股市场优质股票', '股票', 800000, 920000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (2, '全球基金', '投资于全球多元化基金', '基金', 1500000, 1650000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (3, '黄金投资', '投资于黄金及其他贵金属', '黄金', 1000000, 1050000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (4, '新兴科技股', '投资于新兴科技公司的股票', '股票', 500000, 575000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (5, '医疗保健基金', '投资于医疗保健行业的基金', '基金', 1200000, 1300000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (6, '房地产信托', '投资于商业和住宅房地产', '房地产', 2000000, 2200000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (7, '新能源基金', '投资于太阳能和风能项目', '基金', 900000, 990000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (8, '消费品股票', '投资于消费品行业的股票', '股票', 400000, 420000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (9, '政府债券', '投资于政府发行的债券', '债券', 1000000, 1050000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (10, '国际商品基金', '投资于国际商品市场', '基金', 500000, 550000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (11, '加密货币投资', '投资于各种加密货币', '加密货币', 200000, 300000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (12, '私募股权投资', '投资于未上市的公司股权', '私募股权', 1500000, 1700000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (13, '指数追踪基金', '追踪主要股票市场指数', '基金', 700000, 735000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (14, '综合投资基金', '多元化的股票和债券组合', '基金', 1000000, 1100000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (15, '基础设施建设基金', '投资于基础设施项目', '基金', 1200000, 1300000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (16, '生物技术股', '投资于生物技术公司的股票', '股票', 800000, 900000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (17, '高股息股票', '投资于高股息收益的股票', '股票', 600000, 660000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (18, '可再生能源股票', '投资于可再生能源公司的股票', '股票', 900000, 990000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (19, '消费品基金', '投资于消费品公司的基金', '基金', 400000, 420000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');
INSERT INTO `investment` VALUES (20, '科技成长基金', '投资于高成长科技公司的基金', '基金', 1100000, 1500000, 0, 0, '2024-05-19 16:32:27.920547', '2024-05-19 16:32:27.920547');

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

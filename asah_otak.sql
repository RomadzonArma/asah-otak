/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : asah_otak

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 30/09/2024 13:36:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for master_kata
-- ----------------------------
DROP TABLE IF EXISTS `master_kata`;
CREATE TABLE `master_kata`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `kata` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `clue` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of master_kata
-- ----------------------------
INSERT INTO `master_kata` VALUES (1, 'LEMARI', 'Aku tempat menyimpan pakaian?');
INSERT INTO `master_kata` VALUES (2, 'KASUR', 'Aku tempat tidur?');
INSERT INTO `master_kata` VALUES (3, 'Mobil', 'Kendaraan yang memiliki empat roda dan dapat menampung penumpang');
INSERT INTO `master_kata` VALUES (4, 'Sepeda', 'Kendaraan yang digerakkan dengan mengayuh pedal');
INSERT INTO `master_kata` VALUES (5, 'Truk', 'Kendaraan besar yang digunakan untuk mengangkut barang');
INSERT INTO `master_kata` VALUES (6, 'Bus', 'Kendaraan besar yang digunakan untuk mengangkut banyak penumpang');

-- ----------------------------
-- Table structure for point_game
-- ----------------------------
DROP TABLE IF EXISTS `point_game`;
CREATE TABLE `point_game`  (
  `id_point` int NOT NULL AUTO_INCREMENT,
  `nama_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `total_point` int NOT NULL,
  PRIMARY KEY (`id_point`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of point_game
-- ----------------------------
INSERT INTO `point_game` VALUES (1, 'SUMINI', 50);
INSERT INTO `point_game` VALUES (2, 'bobon', 30);
INSERT INTO `point_game` VALUES (3, 'bobon', 8);
INSERT INTO `point_game` VALUES (4, 'bobon', 30);
INSERT INTO `point_game` VALUES (5, 'bobon', 30);
INSERT INTO `point_game` VALUES (6, 'bobon', 16);
INSERT INTO `point_game` VALUES (7, 'BOBON', 50);
INSERT INTO `point_game` VALUES (8, 'bobon', 40);
INSERT INTO `point_game` VALUES (9, 'andy', 50);
INSERT INTO `point_game` VALUES (10, 'budi', 50);
INSERT INTO `point_game` VALUES (11, 'AMOI', 50);

SET FOREIGN_KEY_CHECKS = 1;

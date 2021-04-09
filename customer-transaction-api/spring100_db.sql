/*
Navicat MySQL Data Transfer

Source Server         : LOCAL
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : spring100_db

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2021-03-26 06:12:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mdogs_tbl`
-- ----------------------------
DROP TABLE IF EXISTS `mdogs_tbl`;
CREATE TABLE `mdogs_tbl` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `breed` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `doe` datetime DEFAULT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of mdogs_tbl
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin@email.com', '$2a$12$IBdo4KkaAeIFkNIE3f0n2.PPdaFEpfmT1eq6/.93hrz2GkH/Dpouu', 'admin');
INSERT INTO `user` VALUES ('2', 'client@email.com', '$2a$12$HVgx4ULjObE0jw8KoKWYV.emXxItIQ8qmojIbGKVwYSN7LmbmOsrm', 'client');

-- ----------------------------
-- Table structure for `user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `roles` int(11) DEFAULT NULL,
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('1', '0');
INSERT INTO `user_roles` VALUES ('2', '1');

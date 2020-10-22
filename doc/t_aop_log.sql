-- ----------------------------
-- Table structure for `t_aop_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_aop_log`;
CREATE TABLE `t_aop_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullMethod` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `requestParameters` varchar(2000) CHARACTER SET utf8 DEFAULT NULL,
  `requestTime` int(11) DEFAULT NULL,
  `responseParameters` varchar(2000) CHARACTER SET utf8 DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Records of t_aop_log
-- ----------------------------

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hotel
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `hotel_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '酒店名称',
                         `room_num` int DEFAULT NULL COMMENT '房间数量',
                         `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='酒店';

SET FOREIGN_KEY_CHECKS = 1;
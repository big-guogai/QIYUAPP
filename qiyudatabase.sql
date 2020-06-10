/*
Navicat MySQL Data Transfer

Source Server         : 123.56.248.183
Source Server Version : 50562
Source Host           : 123.56.248.183:3306
Source Database       : qiyudatabase

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2020-05-16 23:25:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bosscourseinfo
-- ----------------------------
DROP TABLE IF EXISTS `bosscourseinfo`;
CREATE TABLE `bosscourseinfo` (
  `courseid` int(11) NOT NULL AUTO_INCREMENT COMMENT '八位（从18000001递增）',
  `bossid` int(11) NOT NULL,
  `coursename` varchar(255) NOT NULL,
  `courseprice` double NOT NULL,
  `coursepricetype` varchar(255) NOT NULL,
  `courseinformation` varchar(255) NOT NULL,
  `coursetypeid1` int(11) NOT NULL,
  `coursetypeid2` int(11) DEFAULT NULL,
  `coursetypeid3` int(11) DEFAULT NULL,
  PRIMARY KEY (`courseid`),
  KEY `coursetypeid` (`coursetypeid1`),
  KEY `bosscourseinfo_ibfk_1` (`bossid`),
  CONSTRAINT `bosscourseinfo_ibfk_1` FOREIGN KEY (`bossid`) REFERENCES `bossinfo` (`bossid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18000038 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bosscourseinfo
-- ----------------------------
INSERT INTO `bosscourseinfo` VALUES ('18000001', '1300000001', '高中语文', '188', '节', '本课程由高校名师教学，30人一班，按节计算课程费用，每节课40分钟，教学质量保证优秀，请各位家长或学生放心购买。（测试论文截图文本）', '203', '208', '209');
INSERT INTO `bosscourseinfo` VALUES ('18000002', '1300000001', '高中数学', '120', '节', '测试截图测试截图', '208', '209', '208');
INSERT INTO `bosscourseinfo` VALUES ('18000003', '1300000002', 'C语言', '188', '节', '核对文化和你就打我湖大', '205', '0', '0');
INSERT INTO `bosscourseinfo` VALUES ('18000004', '1300000002', 'java语言', '188', '节', '横渡外汇就打我爱的骄傲', '205', null, null);
INSERT INTO `bosscourseinfo` VALUES ('18000005', '1300000003', '高中数学家教', '288', '节', '厚度外电话hi胡', '201', '208', null);
INSERT INTO `bosscourseinfo` VALUES ('18000006', '1300000003', '高中英语家教', '288', '节', '大话王ID哈UI几点我激动爱', '201', '208', null);
INSERT INTO `bosscourseinfo` VALUES ('18000007', '1300000004', '学前教育', '388', '节', '带坏我的hi案件打我一等奖', '202', '208', null);
INSERT INTO `bosscourseinfo` VALUES ('18000008', '1300000004', '幼儿兴趣培养', '388', '节', '大话王IDhiu安徽地阿文化', '202', null, null);
INSERT INTO `bosscourseinfo` VALUES ('18000010', '1300000001', '高中英语', '188', '节', 'adajiduhaduahdauidhhadhiu', '203', '208', null);
INSERT INTO `bosscourseinfo` VALUES ('18000012', '1300000001', '高中物理', '188', '节', 'jaoiudhaiudhadahidia', '208', null, null);
INSERT INTO `bosscourseinfo` VALUES ('18000013', '1300000001', '高中化学', '188', '节', '就丢啊还都爱黄金岛ui的号到点好', '208', null, null);
INSERT INTO `bosscourseinfo` VALUES ('18000014', '1300000002', 'python基础', '1888', '季度/系列', '就丢奥好短哦i好歹i的还u', '205', null, null);
INSERT INTO `bosscourseinfo` VALUES ('18000015', '1300000002', 'Android入门', '1888', '季度/系列', '带回家奥尼的豪华坏的还', '205', null, null);
INSERT INTO `bosscourseinfo` VALUES ('18000017', '1300000003', '初中语文家教', '288', '节', '大号杜鳌ID号维护打我蝴蝶花', '201', '208', null);
INSERT INTO `bosscourseinfo` VALUES ('18000018', '1300000003', '钢琴家教', '388', '节', '和DAU我一的哈文的海带花UI湖大道hi啊', '207', '204', '201');
INSERT INTO `bosscourseinfo` VALUES ('18000019', '1300000003', '吉他家教', '388', '节', '哈我等哈无换大号hiu阿文化大牛的海', '207', '201', '204');
INSERT INTO `bosscourseinfo` VALUES ('18000020', '1300000002', 'ps平面设计', '1888', '季度/系列', '很大UI还低啊hiadhi段', '205', '206', null);
INSERT INTO `bosscourseinfo` VALUES ('18000023', '1300000001', '大学英语四级', '188', '节', '户籍地我就欧文奇偶外地脊瓦', '203', '208', null);
INSERT INTO `bosscourseinfo` VALUES ('18000024', '1300000001', '大学英语六级', '188', '节', '很对海带舞啊Howard建瓯盘玩脊瓦脊瓦', '203', '208', null);
INSERT INTO `bosscourseinfo` VALUES ('18000028', '1300000001', '大学马克思主义', '188', '节', '家违法化为风刹那即使啊呜那边框架AV阿卡丽西安发建瓯盘玩', '208', '0', '0');
INSERT INTO `bosscourseinfo` VALUES ('18000029', '1300000012', '古娜拉黑魔法', '5888', '套/季度', '古娜拉黑魔法是古老的魔仙一族衍生的一种黑暗系魔法，此魔法威力强大，使用者心灵将会受到黑暗侵蚀，永无安宁，但是学会黑魔法就是能力与地位的象征，如果没有能力很强的几位魔仙同时对抗，黑魔法则可以为所欲为', '204', '209', '0');
INSERT INTO `bosscourseinfo` VALUES ('18000032', '1300000022', '不锈钢大锅盖制作培训', '2888', '套/系列', '本套课程主要内容为不锈钢大锅盖的制作教学，2888元一个套餐，包教包会包分配，学不会报销往返路费', '204', '209', '0');

-- ----------------------------
-- Table structure for bossinfo
-- ----------------------------
DROP TABLE IF EXISTS `bossinfo`;
CREATE TABLE `bossinfo` (
  `bossid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` bigint(11) NOT NULL,
  `bossname` varchar(255) NOT NULL,
  `bossdocuments` varchar(255) NOT NULL,
  `bosstelephone` decimal(11,0) NOT NULL,
  `bossphoto_one` varchar(255) NOT NULL,
  `bossphoto_two` varchar(255) DEFAULT NULL,
  `bossphoto_three` varchar(255) DEFAULT NULL,
  `bossphoto_four` varchar(255) DEFAULT NULL,
  `bossphoto_five` varchar(255) DEFAULT NULL,
  `bossinformation` varchar(255) NOT NULL,
  `bossposition` varchar(255) NOT NULL,
  `bossreceipttype` enum('支付宝','银行卡') NOT NULL,
  `bossreceiptid` varchar(255) NOT NULL,
  PRIMARY KEY (`bossid`),
  KEY `userid` (`userid`),
  CONSTRAINT `bossinfo_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userinfo` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1300000024 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bossinfo
-- ----------------------------
INSERT INTO `bossinfo` VALUES ('1300000001', '17738750029', '奇育教育培训学校', 'bossphoto/1300000001/document.jpg', '17738750029', 'bossphoto/1300000001/main1.jpg', 'bossphoto/1300000001/main2.jpg', 'bossphoto/1300000001/main3.jpg', null, null, '奇育教育培训学校是一所旨在为广大各年级学生提供各类课程辅导与学习的培训学校。本校拥有各种专业教导技能，丰富的知识储备，保证您把孩子交到我们手中能放心舒心。', '四川省成都市温江区海科路东段99号', '支付宝', '17738750029');
INSERT INTO `bossinfo` VALUES ('1300000002', '15680663820', '奇育IT培训学校', 'bossphoto/1300000002/document.jpg', '15680663820', 'bossphoto/1300000002/main1.jpg', 'bossphoto/1300000002/main2.jpg', 'bossphoto/1300000002/main3.jpg', null, null, '的痕迹啊UI文化带还好我大号啦啦舞', '四川省南充市', '支付宝', '17738750029');
INSERT INTO `bossinfo` VALUES ('1300000003', '13333333333', '奇育家教公司', 'bossphoto/1300000003/document.jpg', '17738750029', 'bossphoto/1300000003/main1.jpg', 'bossphoto/1300000003/main2.jpg', 'bossphoto/1300000003/main3.jpg', null, null, '氮化物我很大UI我等哈我hi芵', '四川省成都市', '支付宝', '17738750029');
INSERT INTO `bossinfo` VALUES ('1300000004', '13444444444', '奇育幼儿园', 'bossphoto/1300000004/document.jpg', '15680663820', 'bossphoto/1300000004/main1.jpg', 'bossphoto/1300000004/main2.jpg', 'bossphoto/1300000004/main3.jpg', null, null, '和端午我还带货物变化嗲', '四川省南充市', '支付宝', '17738750029');
INSERT INTO `bossinfo` VALUES ('1300000005', '13222222222', '霍格沃兹学院', 'bossphoto/1300000005/document.jpg', '13222222222', 'bossphoto/1300000005/main1.jpg', 'bossphoto/1300000005/main2.jpg', 'bossphoto/1300000005/main3.jpg', null, null, '发发嘎嘎三个个问题阿汤哥干啥', '四川省绵阳市', '支付宝', '17738750029');
INSERT INTO `bossinfo` VALUES ('1300000012', '13111111111', '黑暗学院', 'bossphoto/1300000012/document.jpg', '13111111111', 'bossphoto/1300000012/main1.jpg', 'bossphoto/1300000012/main2.jpg', 'bossphoto/1300000012/main3.jpg', null, null, '电话薇虎娃会胡迪华为的和机对外界脊瓦偶家', '四川省绵阳市', '支付宝', '17738750029');
INSERT INTO `bossinfo` VALUES ('1300000022', '18382959123', '大锅盖制作培训公司', 'bossphoto/1300000022/document.jpg', '18382959123', 'bossphoto/1300000022/main1.jpg', 'bossphoto/1300000022/main2.jpg', 'bossphoto/1300000022/main3.jpg', null, null, '大锅盖制作培训公司拥有几乎全部材质的大锅盖制作方法与过程，生铁、熟铁、不锈钢、铝合金、振金、曼德拉合金、纯金、纯银、托马斯、詹姆斯、尼古拉斯等各种大锅盖制作培训课程应有尽有。', '四川省绵阳市嘎子县英子镇翻译官村1组', '支付宝', '17738750029');

-- ----------------------------
-- Table structure for checkbossinfo
-- ----------------------------
DROP TABLE IF EXISTS `checkbossinfo`;
CREATE TABLE `checkbossinfo` (
  `userid` bigint(20) NOT NULL,
  `bossname` varchar(255) NOT NULL,
  `bossdocuments` varchar(255) NOT NULL,
  `bosstelephone` bigint(20) NOT NULL,
  `bossphoto_one` varchar(255) NOT NULL,
  `bossphoto_two` varchar(255) DEFAULT NULL,
  `bossphoto_three` varchar(255) DEFAULT NULL,
  `bossphoto_four` varchar(255) DEFAULT NULL,
  `bossphoto_five` varchar(255) DEFAULT NULL,
  `bossinformation` varchar(255) NOT NULL,
  `bossposition` varchar(255) NOT NULL,
  `bossreceipttype` enum('支付宝','银行卡') NOT NULL,
  `bossreceiptid` varchar(255) NOT NULL,
  PRIMARY KEY (`userid`),
  CONSTRAINT `checkbossinfo_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userinfo` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of checkbossinfo
-- ----------------------------

-- ----------------------------
-- Table structure for coursetypeinfo
-- ----------------------------
DROP TABLE IF EXISTS `coursetypeinfo`;
CREATE TABLE `coursetypeinfo` (
  `coursetypeid` int(11) NOT NULL,
  `coursetype` varchar(255) NOT NULL,
  PRIMARY KEY (`coursetypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coursetypeinfo
-- ----------------------------
INSERT INTO `coursetypeinfo` VALUES ('201', '家教');
INSERT INTO `coursetypeinfo` VALUES ('202', '幼儿');
INSERT INTO `coursetypeinfo` VALUES ('203', '语言');
INSERT INTO `coursetypeinfo` VALUES ('204', '技能');
INSERT INTO `coursetypeinfo` VALUES ('205', 'IT');
INSERT INTO `coursetypeinfo` VALUES ('206', '设计');
INSERT INTO `coursetypeinfo` VALUES ('207', '艺体');
INSERT INTO `coursetypeinfo` VALUES ('208', '学历');
INSERT INTO `coursetypeinfo` VALUES ('209', '其他');

-- ----------------------------
-- Table structure for indentinfo
-- ----------------------------
DROP TABLE IF EXISTS `indentinfo`;
CREATE TABLE `indentinfo` (
  `indentid` int(11) NOT NULL AUTO_INCREMENT,
  `buyuserid` bigint(20) NOT NULL,
  `bossid` int(11) NOT NULL,
  `courseid` int(11) NOT NULL,
  `courseunitprice` double NOT NULL,
  `coursequantity` int(11) NOT NULL,
  `indentprice` double NOT NULL,
  `indenttime` datetime NOT NULL,
  `indenttype` varchar(255) NOT NULL,
  PRIMARY KEY (`indentid`),
  KEY `indentinfo_ibfk_1` (`buyuserid`),
  KEY `indentinfo_ibfk_2` (`bossid`),
  KEY `indentinfo_ibfk_3` (`courseid`),
  CONSTRAINT `indentinfo_ibfk_1` FOREIGN KEY (`buyuserid`) REFERENCES `userinfo` (`userid`) ON DELETE CASCADE,
  CONSTRAINT `indentinfo_ibfk_2` FOREIGN KEY (`bossid`) REFERENCES `bossinfo` (`bossid`) ON DELETE CASCADE,
  CONSTRAINT `indentinfo_ibfk_3` FOREIGN KEY (`courseid`) REFERENCES `bosscourseinfo` (`courseid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100000096 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of indentinfo
-- ----------------------------
INSERT INTO `indentinfo` VALUES ('100000001', '16111111111', '1300000001', '18000001', '188', '3', '564', '2020-01-02 13:33:38', '购买完成');
INSERT INTO `indentinfo` VALUES ('100000003', '16111111111', '1300000001', '18000002', '188', '3', '564', '2020-01-02 13:34:43', '待处理');
INSERT INTO `indentinfo` VALUES ('100000004', '16111111111', '1300000002', '18000003', '188', '3', '564', '2020-01-02 13:35:24', '待处理');
INSERT INTO `indentinfo` VALUES ('100000005', '16111111111', '1300000001', '18000001', '188', '1', '188', '2020-01-02 13:33:38', '购买完成');
INSERT INTO `indentinfo` VALUES ('100000037', '16111111111', '1300000003', '18000019', '388', '4', '1552', '2020-01-07 17:20:04', '待处理');
INSERT INTO `indentinfo` VALUES ('100000038', '16111111111', '1300000003', '18000017', '288', '4', '1152', '2020-01-07 17:21:14', '待处理');
INSERT INTO `indentinfo` VALUES ('100000039', '16111111111', '1300000001', '18000023', '188', '10', '1880', '2020-01-07 17:46:18', '待处理');
INSERT INTO `indentinfo` VALUES ('100000040', '16111111111', '1300000001', '18000001', '188', '2', '376', '2020-01-07 18:06:04', '待处理');
INSERT INTO `indentinfo` VALUES ('100000041', '16111111111', '1300000001', '18000023', '188', '3', '564', '2020-01-07 18:10:14', '购买完成');
INSERT INTO `indentinfo` VALUES ('100000043', '16111111111', '1300000001', '18000001', '188', '1', '188', '2020-01-08 12:58:14', '购买完成');
INSERT INTO `indentinfo` VALUES ('100000045', '17111111111', '1300000003', '18000017', '288', '1', '288', '2020-01-08 14:25:44', '待支付');
INSERT INTO `indentinfo` VALUES ('100000046', '17111111111', '1300000001', '18000001', '188', '1', '188', '2020-01-08 14:25:57', '待支付');
INSERT INTO `indentinfo` VALUES ('100000047', '16111111111', '1300000001', '18000001', '188', '6', '1128', '2020-01-13 07:58:21', '购买完成');
INSERT INTO `indentinfo` VALUES ('100000048', '16111111111', '1300000002', '18000003', '188', '5', '940', '2020-01-13 09:48:17', '待处理');
INSERT INTO `indentinfo` VALUES ('100000049', '16111111111', '1300000001', '18000001', '188', '1', '188', '2020-01-14 17:53:17', '购买完成');
INSERT INTO `indentinfo` VALUES ('100000050', '16111111111', '1300000002', '18000020', '1888', '1', '1888', '2020-03-02 15:28:21', '待处理');
INSERT INTO `indentinfo` VALUES ('100000052', '16111111111', '1300000012', '18000029', '5888', '1', '5888', '2020-03-17 16:26:10', '待处理');
INSERT INTO `indentinfo` VALUES ('100000053', '16111111111', '1300000012', '18000029', '5888', '1', '5888', '2020-03-17 17:01:57', '待处理');
INSERT INTO `indentinfo` VALUES ('100000054', '16111111111', '1300000012', '18000029', '5888', '1', '5888', '2020-03-17 17:02:23', '待处理');
INSERT INTO `indentinfo` VALUES ('100000091', '16111111111', '1300000001', '18000001', '188', '1', '188', '2020-03-18 15:55:13', '待支付');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `userid` bigint(20) NOT NULL COMMENT '用户手机号码',
  `userpassword` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `usersex` enum('男','女') NOT NULL,
  `userage` int(11) NOT NULL,
  `userhead` varchar(255) NOT NULL,
  `usertypeid` int(11) NOT NULL,
  PRIMARY KEY (`userid`),
  KEY `usertypeid` (`usertypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('13111111111', '123456789', '尼古拉斯小锅盖', '男', '28', 'head4_null.png', '102');
INSERT INTO `userinfo` VALUES ('13222222222', '123456789', '托马斯大锅盖', '男', '21', 'head8_null.png', '102');
INSERT INTO `userinfo` VALUES ('13333333333', '123456789', '詹姆斯大锅盖', '女', '41', 'head2_null.png', '102');
INSERT INTO `userinfo` VALUES ('13444444444', '123456789', '罗纳尔多大锅盖', '男', '30', 'head2_null.png', '102');
INSERT INTO `userinfo` VALUES ('15111111111', '123456789', '黑曜石大锅盖', '男', '23', 'head6_null.png', '101');
INSERT INTO `userinfo` VALUES ('15555555555', '123456789', '回收二手锅盖', '女', '9', 'head2_null.png', '1012');
INSERT INTO `userinfo` VALUES ('15680663820', '123456789', '徐浩', '男', '17', 'head2_null.png', '102');
INSERT INTO `userinfo` VALUES ('15881732168', '123456789', '管理员', '男', '22', 'head5_null.png', '103');
INSERT INTO `userinfo` VALUES ('16111111111', '123456789', '美国队长大锅盖', '男', '3', 'head3_null.png', '1012');
INSERT INTO `userinfo` VALUES ('17111111111', '123456789', '罗伯特大锅盖', '女', '13', 'head2_null.png', '101');
INSERT INTO `userinfo` VALUES ('17738750029', '123456789', '许浩', '男', '20', 'head3_null.png', '102');
INSERT INTO `userinfo` VALUES ('17777777777', '123456789', '啊啊啊', '男', '23', 'head2_null.png', '1012');
INSERT INTO `userinfo` VALUES ('18382959123', '123456789', '詹姆斯大锅盖', '女', '22', 'head6_null.png', '102');

-- MySQL dump 10.11
--
-- Host: localhost    Database: myserver
-- ------------------------------------------------------
-- Server version	5.0.51b-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `meaord_dishes`
--

DROP TABLE IF EXISTS `meaord_dishes`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `meaord_dishes` (
  `id_` int(11) NOT NULL auto_increment,
  `name_` varchar(255) default NULL,
  `description_` varchar(255) default NULL,
  `price_` float default NULL,
  `sort_` int(11) default NULL,
  `type_` varchar(255) default NULL,
  `restaurant_id_` int(11) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `meaord_dishes`
--

LOCK TABLES `meaord_dishes` WRITE;
/*!40000 ALTER TABLE `meaord_dishes` DISABLE KEYS */;
INSERT INTO `meaord_dishes` VALUES (19,'菜品22','',22,22,'凉菜',4),(17,'菜品1','',1,1,'默认',4),(18,'菜品2','',3,3,'默认',4),(28,'凉拌海蜇丝','',12,12,'凉菜',4),(26,'菜品5','',1,3,'凉菜',4),(27,'东北大拉皮','',10,1,'凉菜',4);
/*!40000 ALTER TABLE `meaord_dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meaord_member`
--

DROP TABLE IF EXISTS `meaord_member`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `meaord_member` (
  `id_` int(11) NOT NULL auto_increment,
  `user_id_` int(11) default NULL,
  `name_` varchar(255) default NULL,
  `phone1_` varchar(255) default NULL,
  `phone2_` varchar(255) default NULL,
  `balance` float default NULL,
  `sort_` int(11) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `meaord_member`
--

LOCK TABLES `meaord_member` WRITE;
/*!40000 ALTER TABLE `meaord_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `meaord_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meaord_restaurant`
--

DROP TABLE IF EXISTS `meaord_restaurant`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `meaord_restaurant` (
  `id_` int(11) NOT NULL auto_increment,
  `name_` varchar(255) default NULL,
  `description_` varchar(255) default NULL,
  `linkman_` varchar(255) default NULL,
  `phone1_` varchar(255) default NULL,
  `phone2_` varchar(255) default NULL,
  `sort_` int(11) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `meaord_restaurant`
--

LOCK TABLES `meaord_restaurant` WRITE;
/*!40000 ALTER TABLE `meaord_restaurant` DISABLE KEYS */;
INSERT INTO `meaord_restaurant` VALUES (4,'裕晨幸福里','','赵冬梅','15699560885','',1);
/*!40000 ALTER TABLE `meaord_restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_attachment`
--

DROP TABLE IF EXISTS `sys_attachment`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_attachment` (
  `id_` int(11) NOT NULL auto_increment,
  `entity_name_` varchar(255) default NULL,
  `entity_id_` varchar(255) default NULL,
  `content_type_` varchar(255) default NULL,
  `original_filename_` varchar(255) default NULL,
  `random_filename_` varchar(255) default NULL,
  `file_size_` int(11) default NULL,
  `extension_name_` varchar(255) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_attachment`
--

LOCK TABLES `sys_attachment` WRITE;
/*!40000 ALTER TABLE `sys_attachment` DISABLE KEYS */;
INSERT INTO `sys_attachment` VALUES (1,'org.kyll.myserver.business.meaord.entity.MeaordDishes','10','image/jpeg','286323.jpg','286323.jpg_a3db4fa07a25417b80e1875bdea42051',182972,'jpg'),(2,'org.kyll.myserver.business.meaord.entity.MeaordDishes','11','image/jpeg','286323.jpg','286323.jpg_abffd48dcb8b4337bbe1aa5094bb9e27',182972,'jpg'),(3,'org.kyll.myserver.business.meaord.entity.MeaordDishes','12','image/jpeg','280089.jpg','280089.jpg_9a00a0f2bf494b308dc8e3eacd30958a',772018,'jpg'),(4,'org.kyll.myserver.business.meaord.entity.MeaordDishes','13','image/jpeg','280089.jpg','280089_dae6ba1250f7464787514ed28a35026f',772018,'jpg'),(5,'org.kyll.myserver.business.meaord.entity.MeaordDishes','14','image/jpeg','286323.jpg','286323_491ed1973bd54231a4d4a67f168aa722',182972,'jpg'),(6,'org.kyll.myserver.business.meaord.entity.MeaordDishes','15','image/jpeg','286323.jpg','286323_3673393848d04e6eb7f7b0dedef8f801',182972,'jpg'),(20,'org.kyll.myserver.business.meaord.entity.MeaordDishes','20','image/jpeg','53f98e7b69401b0c697f6c97.jpg','53f98e7b69401b0c697f6c97_bebdcb21f3544cdaa66eab047c77aefd',284388,'jpg'),(19,'org.kyll.myserver.business.meaord.entity.MeaordDishes','18','image/jpeg','54a3bd9569401b20920c802a.jpg','54a3bd9569401b20920c802a_f50a6f15f1b948ec903a54f213cb12cf',288988,'jpg'),(17,'org.kyll.myserver.business.meaord.entity.MeaordDishes','17','image/jpeg','53f6f30369401b0c697f6729.jpg','53f6f30369401b0c697f6729_882a33bdfe3849109d312a67a539a88a',499605,'jpg'),(18,'org.kyll.myserver.business.meaord.entity.MeaordDishes','19','image/jpeg','53ec62c1cb7b254103fbf95f.jpg','53ec62c1cb7b254103fbf95f_fc6a949d6cb54445b615673b25cedddd',208011,'jpg'),(22,'org.kyll.myserver.business.meaord.entity.MeaordDishes','23','image/jpeg','54a746af69401b50292971bb.jpg','54a746af69401b50292971bb_a2a660a6f48e466ea2370d11e42ee525',506188,'jpg'),(23,'org.kyll.myserver.business.meaord.entity.MeaordDishes','25','image/jpeg','5305b74c48d5b9432257235e.jpg','5305b74c48d5b9432257235e_b3f80c7427014c5ab3fa8a032db8f089',475362,'jpg'),(24,'org.kyll.myserver.business.meaord.entity.MeaordDishes','26','image/jpeg','53217f8f48d5b91c93bef343.jpg','53217f8f48d5b91c93bef343_d0f59883fe164f3f83c9d362677537e4',556815,'jpg'),(27,'org.kyll.myserver.business.meaord.entity.MeaordDishes','28','image/jpeg','5417f83569401b74e6739276.jpg','5417f83569401b74e6739276_0a907787e67845a38d7d8831dd027585',760333,'jpg'),(26,'org.kyll.myserver.business.meaord.entity.MeaordDishes','27','image/jpeg','5343a5d248d5b93cb1fd9999.jpg','5343a5d248d5b93cb1fd9999_33888ea498c94691b7005dd8356a69aa',716912,'jpg');
/*!40000 ALTER TABLE `sys_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_config` (
  `id_` int(11) NOT NULL auto_increment,
  `key_` varchar(255) default NULL,
  `value_` varchar(255) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'ATTACHMENT_PATH','e:\\temp\\myserver');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_department`
--

DROP TABLE IF EXISTS `sys_department`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_department` (
  `id_` int(11) NOT NULL auto_increment,
  `parent_id_` int(11) default NULL,
  `name_` varchar(255) default NULL,
  `description_` varchar(255) default NULL,
  `sort_` int(11) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_department`
--

LOCK TABLES `sys_department` WRITE;
/*!40000 ALTER TABLE `sys_department` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_dict` (
  `id_` int(11) NOT NULL auto_increment,
  `invoke_code_` varchar(255) default NULL,
  `name_` varchar(255) default NULL,
  `parent_id_` int(11) default NULL,
  `sort_` int(11) default NULL,
  PRIMARY KEY  (`id_`),
  UNIQUE KEY `unique_invoke_code_` (`invoke_code_`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` VALUES (1,'sysmanager_module_type','系统管理-模块-类型',NULL,1),(2,'sysmanager_module_funcType','系统管理-模块-功能类型',NULL,2),(7,'r','r',NULL,3);
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_item`
--

DROP TABLE IF EXISTS `sys_dict_item`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_dict_item` (
  `id_` int(11) NOT NULL auto_increment,
  `key_` varchar(255) default NULL,
  `value_` varchar(255) default NULL,
  `dict_id_` int(11) default NULL,
  `sort_` int(11) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_dict_item`
--

LOCK TABLES `sys_dict_item` WRITE;
/*!40000 ALTER TABLE `sys_dict_item` DISABLE KEYS */;
INSERT INTO `sys_dict_item` VALUES (1,'sysmanager_module_type','系统管理-模块-类型',NULL,1),(2,'1','模块',1,1),(3,'2','功能',1,2),(4,'sysmanager_module_funcType','系统管理-模块-功能类型',NULL,2),(5,'1','JavaScript',2,1),(6,'2','URL',2,2);
/*!40000 ALTER TABLE `sys_dict_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_employee`
--

DROP TABLE IF EXISTS `sys_employee`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_employee` (
  `id_` int(11) NOT NULL auto_increment,
  `name_` varchar(255) NOT NULL,
  `user_id_` int(11) default NULL,
  `sort_` int(11) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_employee`
--

LOCK TABLES `sys_employee` WRITE;
/*!40000 ALTER TABLE `sys_employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_job` (
  `id_` int(11) NOT NULL auto_increment,
  `department_id_` int(11) NOT NULL,
  `name_` varchar(255) NOT NULL,
  `description_` varchar(255) NOT NULL,
  `sort_` int(11) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_menu` (
  `id_` int(11) NOT NULL auto_increment,
  `parent_id_` int(11) default NULL,
  `name_` varchar(255) default NULL,
  `description_` varchar(255) default NULL,
  `sort_` int(11) default NULL,
  `function_id_` int(11) NOT NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_module`
--

DROP TABLE IF EXISTS `sys_module`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_module` (
  `id_` int(11) NOT NULL auto_increment,
  `parent_id_` int(11) default NULL,
  `name_` varchar(255) NOT NULL,
  `description_` varchar(255) NOT NULL,
  `sort_` int(11) default NULL,
  `type_` int(11) default NULL,
  `func_type_` int(11) default NULL,
  `func_code_` varchar(20000) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_module`
--

LOCK TABLES `sys_module` WRITE;
/*!40000 ALTER TABLE `sys_module` DISABLE KEYS */;
INSERT INTO `sys_module` VALUES (8,1,'qq','',1,1,NULL,NULL),(9,8,'qqq','',1,1,NULL,NULL),(10,9,'qqqq','',1,1,NULL,NULL),(11,8,'www','',1,1,NULL,NULL),(13,1,'ee','',1,1,NULL,NULL),(14,1,'rr','',1,1,NULL,NULL),(15,1,'tt','',1,1,NULL,NULL),(16,1,'yy','',1,1,NULL,NULL),(17,1,'uu','',1,1,NULL,NULL),(18,8,'ii','',1,1,NULL,NULL),(19,1,'ii','',1,1,NULL,NULL),(20,12,'1','',1,1,NULL,NULL),(26,9,'wwww','',1,1,NULL,NULL),(27,NULL,'系统管理','',99,1,NULL,NULL),(28,27,'用户管理','',1,2,1,'{className: \'SysManager.user.UserContainer\', config: {}}'),(29,27,'角色管理','',2,2,1,'{className: \'SysManager.role.RoleContainer\', config: {}}'),(30,27,'模块管理','',3,2,1,'{className: \'SysManager.module.ModuleContainer\', config: {}}'),(33,27,'组织机构','',4,1,NULL,NULL),(34,33,'员工管理','',1,2,1,'{\'\', config: {}}'),(35,33,'组织管理','',2,2,1,'{\'\', config:[]}'),(36,27,'数据字典','',5,2,1,'{className: \'SysManager.dict.DictContainer\', config: {}}'),(37,NULL,'餐饮订单','',1,1,NULL,NULL),(38,37,'餐厅菜品维护','',1,2,1,'{className: \'Meaord.restaurant.RestaurantGridPanel\', config: {}}');
/*!40000 ALTER TABLE `sys_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_role` (
  `id_` int(11) NOT NULL auto_increment,
  `name_` varchar(255) default NULL,
  `description_` varchar(255) default NULL,
  `sort_` int(11) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (3,'系统管理员','x',1),(4,'角色名称','',4),(5,'餐饮订单管理员','',2),(6,'角色名称3','',3);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_module`
--

DROP TABLE IF EXISTS `sys_role_module`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_role_module` (
  `role_id_` int(11) NOT NULL,
  `module_id_` int(11) NOT NULL default '0',
  PRIMARY KEY  (`role_id_`,`module_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_role_module`
--

LOCK TABLES `sys_role_module` WRITE;
/*!40000 ALTER TABLE `sys_role_module` DISABLE KEYS */;
INSERT INTO `sys_role_module` VALUES (3,27),(3,28),(3,29),(3,30),(3,33),(3,34),(3,35),(3,36),(3,37),(3,38),(4,28),(5,29),(5,30),(5,31),(5,37),(5,38),(6,32);
/*!40000 ALTER TABLE `sys_role_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_user` (
  `id_` int(11) NOT NULL auto_increment,
  `username_` varchar(255) default NULL,
  `password_` varchar(255) default NULL,
  `sort_` int(11) default NULL,
  `email_` varchar(255) default NULL,
  `freeze_` int(11) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'kyll','123',1,'kyllyang@gmail.com',0),(6,'admin','123',0,'a@b.com',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sys_user_role` (
  `user_id_` int(11) NOT NULL default '0',
  `role_id_` int(11) NOT NULL default '0',
  PRIMARY KEY  (`user_id_`,`role_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,3),(1,5),(6,3);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `test` (
  `id_` int(11) NOT NULL auto_increment,
  `value_` varchar(255) default NULL,
  PRIMARY KEY  (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'GLGL'),(2,'GLGL'),(3,'GLGL'),(4,'GLGL'),(6,'GLGL'),(15,'GLGL'),(16,'GLGL'),(19,'LLLL');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-13  3:31:35

CREATE DATABASE  IF NOT EXISTS `atl2` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `atl2`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: atl2
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `street` varchar(255) DEFAULT NULL,
  `city_id` bigint(20) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `zipCode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpwa35mv5w9mb3syngd4m8fprw` (`city_id`),
  KEY `FKav5kjy34no960hq7hytlil9xy` (`country_id`),
  KEY `FKagra7kpejdqumfohau83xli4t` (`zipCode_id`),
  CONSTRAINT `FKagra7kpejdqumfohau83xli4t` FOREIGN KEY (`zipCode_id`) REFERENCES `zip_codes` (`id`),
  CONSTRAINT `FKav5kjy34no960hq7hytlil9xy` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`),
  CONSTRAINT `FKpwa35mv5w9mb3syngd4m8fprw` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k2eaywpi1ggxr6khq3unqqv3h` (`image_url`),
  UNIQUE KEY `UK_b14lvebnt5suo43obgxmgwaim` (`name`),
  KEY `FKg33lofex8aqelglprldtvxwla` (`description_id`),
  CONSTRAINT `FKg33lofex8aqelglprldtvxwla` FOREIGN KEY (`description_id`) REFERENCES `i18n` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,NULL,'Asos',NULL),(2,NULL,'Bershka',NULL),(3,NULL,'Bellfield',NULL),(4,NULL,'Blend',NULL),(5,NULL,'Cheap Monday',NULL),(6,NULL,'Cropp',NULL),(7,NULL,'Jack & Jones',NULL),(8,NULL,'House',NULL),(9,NULL,'New Look',NULL),(10,NULL,'Loft',NULL),(11,NULL,'Pull & Bear',NULL),(12,NULL,'Reserved',NULL),(13,NULL,'River Island',NULL),(14,NULL,'Smog',NULL),(15,NULL,'Selected',NULL);
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcd5g10ho6s1r416iiijeda5wk` (`name_id`),
  CONSTRAINT `FKcd5g10ho6s1r416iiijeda5wk` FOREIGN KEY (`name_id`) REFERENCES `i18n` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clothesitem`
--

DROP TABLE IF EXISTS `clothesitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clothesitem` (
  `gender` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `brand_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK14wn8d4fpniw1ta3s34ghe4md` (`brand_id`),
  CONSTRAINT `FK14wn8d4fpniw1ta3s34ghe4md` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK8epnrlid7ni1lc7ipbx69b6fr` FOREIGN KEY (`id`) REFERENCES `items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clothesitem`
--

LOCK TABLES `clothesitem` WRITE;
/*!40000 ALTER TABLE `clothesitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `clothesitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clothesitem_size`
--

DROP TABLE IF EXISTS `clothesitem_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clothesitem_size` (
  `ClothesItem_id` bigint(20) NOT NULL,
  `size_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_sam8xnhsm1egag7cfopevi8ml` (`size_id`),
  KEY `FKkdphakmfpj8n6k6xm1i0wqfir` (`ClothesItem_id`),
  CONSTRAINT `FKkdphakmfpj8n6k6xm1i0wqfir` FOREIGN KEY (`ClothesItem_id`) REFERENCES `clothesitem` (`id`),
  CONSTRAINT `FKoyb0w0jgi3jogdlqsbwu4ug1w` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clothesitem_size`
--

LOCK TABLES `clothesitem_size` WRITE;
/*!40000 ALTER TABLE `clothesitem_size` DISABLE KEYS */;
/*!40000 ALTER TABLE `clothesitem_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colors`
--

DROP TABLE IF EXISTS `colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `colors` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hex` varchar(8) DEFAULT NULL,
  `print` bit(1) NOT NULL,
  `color_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8d5vvonrcshfyqsp7qgom1gex` (`color_id`),
  CONSTRAINT `FK8d5vvonrcshfyqsp7qgom1gex` FOREIGN KEY (`color_id`) REFERENCES `i18n` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colors`
--

LOCK TABLES `colors` WRITE;
/*!40000 ALTER TABLE `colors` DISABLE KEYS */;
INSERT INTO `colors` VALUES (1,'#654321','\0',1),(2,'#000000','\0',2),(3,'#0000ff','\0',3),(4,'#800020','\0',4),(5,'#00008b','\0',5),(6,'#838383','\0',6),(7,'#000034','\0',7),(8,'#a9a9a9','\0',8),(9,'#008000','\0',9),(10,'#c3bd91','\0',10),(11,'#72bcd4','\0',11),(12,'#d3d3d3','\0',12),(13,'#ffffad','\0',13),(14,'#8d6745','\0',14),(15,'#000080','\0',15),(16,'#800080','\0',16),(17,'#ffff00','\0',17),(18,'#ff0000','\0',18),(19,'#f9f5d0','\0',19),(20,'#ffae1a','\0',20),(21,'#ffffff','\0',21),(22,'#822C42','\0',22);
/*!40000 ALTER TABLE `colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `countries` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7mvrj489fksdnssk6exny4qpw` (`name_id`),
  CONSTRAINT `FK7mvrj489fksdnssk6exny4qpw` FOREIGN KEY (`name_id`) REFERENCES `i18n` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries`
--

LOCK TABLES `countries` WRITE;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `EUR` double NOT NULL,
  `GBP` double NOT NULL,
  `UAH` double NOT NULL,
  `USD` double NOT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency`
--

LOCK TABLES `currency` WRITE;
/*!40000 ALTER TABLE `currency` DISABLE KEYS */;
/*!40000 ALTER TABLE `currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eu_size`
--

DROP TABLE IF EXISTS `eu_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eu_size` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cat` int(11) DEFAULT NULL,
  `name_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgl54dkblm7je4yar821uv3d6e` (`name_id`),
  CONSTRAINT `FKgl54dkblm7je4yar821uv3d6e` FOREIGN KEY (`name_id`) REFERENCES `i18n` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eu_size`
--

LOCK TABLES `eu_size` WRITE;
/*!40000 ALTER TABLE `eu_size` DISABLE KEYS */;
INSERT INTO `eu_size` VALUES (1,4,23),(2,4,24),(3,4,25),(4,4,26),(5,4,27),(6,4,28),(7,4,29),(8,3,30),(9,2,31),(10,3,32),(11,2,33),(12,3,34),(13,2,35),(14,2,36),(15,2,37),(16,2,38),(17,2,39),(18,3,40),(19,2,41),(20,2,42),(21,2,43),(22,3,44),(23,2,45),(24,3,46),(25,2,47),(26,2,48),(27,3,49),(28,2,50),(29,5,51),(30,5,52),(31,5,53),(32,5,54),(33,5,55),(34,5,56),(35,5,57);
/*!40000 ALTER TABLE `eu_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fields`
--

DROP TABLE IF EXISTS `fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fields` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fields`
--

LOCK TABLES `fields` WRITE;
/*!40000 ALTER TABLE `fields` DISABLE KEYS */;
/*!40000 ALTER TABLE `fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `i18n`
--

DROP TABLE IF EXISTS `i18n`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `i18n` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `locale_en` varchar(255) DEFAULT NULL,
  `locale_ru` varchar(255) DEFAULT NULL,
  `locale_ua` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `i18n`
--

LOCK TABLES `i18n` WRITE;
/*!40000 ALTER TABLE `i18n` DISABLE KEYS */;
INSERT INTO `i18n` VALUES (1,'Brown','Коричневый','Коричневий'),(2,'Black','Черный','Чорний'),(3,'Blue','Голубой','Голубий'),(4,'Burgundy','Бургунди','Бургунді'),(5,'Dark blue','Темно-синий','Темно-синій'),(6,'Dark grey','Темно-серый','Темно-сірий'),(7,'Dark navy','Темный нави','Темний наві'),(8,'Grey','Серый','Сірий'),(9,'Green','Зеленый','Зелений'),(10,'Khaki','Хаки','Хакі'),(11,'Light blue','Светло-голубой','Світло-голубий'),(12,'Light grey','Светло-серый','Світло-сірий'),(13,'Light yellow','Светло-желтый','Світло-жовтий'),(14,'Light brown','Светло-коричневый','Світло-коричневий'),(15,'Navy','Нави','Наві'),(16,'Purple','Фиолетовый','Фіолетовий'),(17,'Yellow','Желтый','Жовтий'),(18,'Red','Красный','Червоний'),(19,'Stone','Песочный','Піщаний'),(20,'Tobacco','Табачный','Табачний'),(21,'White','Белый','Білий'),(22,'Wine','Цвет вина','Колір вина'),(23,'XS','ХС','ХС'),(24,'S','С','С'),(25,'M','М','М'),(26,'L','Л','Л'),(27,'XL','ХЛ','ХЛ'),(28,'XXL','ХХЛ','ХХЛ'),(29,'XXXL','ХХХЛ','ХХХЛ'),(30,'W28','W28','W28'),(31,'W28 L32','W28 L32','W28 L32'),(32,'W29','W29','W29'),(33,'W29 L32','W29 L32','W29 L32'),(34,'W30','W30','W30'),(35,'W30 L30','W30 L30','W30 L30'),(36,'W30 L32','W30 L32','W30 L32'),(37,'W30 L34','W30 L34','W30 L34'),(38,'W31 L30','W31 L30','W31 L30'),(39,'W31 L32','W31 L32','W31 L32'),(40,'W32','W32','W32'),(41,'W32 L30','W32 L30','W32 L30'),(42,'W32 L32','W32 L32','W32 L32'),(43,'W32 L34','W32 L34','W32 L34'),(44,'W33','W33','W33'),(45,'W33 L32','W33 L32','W33 L32'),(46,'W34','W34','W34'),(47,'W34 L32','W34 L32','W34 L32'),(48,'W34 L34','W34 L34','W34 L34'),(49,'W36','W36','W36'),(50,'W36 L32','W36 L32','W36 L32'),(51,'UK 7','41','41'),(52,'UK 7.5','41.5','41.5'),(53,'UK 8','42','42'),(54,'UK 8.5','42.5','42.5'),(55,'UK 9','43','43'),(56,'UK 10','44','44'),(57,'UK 11','45','45'),(58,'Items','Товар','Товар'),(59,'Clothes','Одежда','Одяг'),(60,'Men','Мужское','Чоловіче'),(61,'Top','Верх','Верх'),(62,'T-Shirts','Футболки','Футболки'),(63,'Shirts','Рубашки','Сорочки'),(64,'Jackets','Пиджаки/куртки','Піджаки/куртки'),(65,'Vests','Майки','Майки'),(66,'Long Sleeves','Лонгсливы','Лонгсліви'),(67,'Jumpers','Джемпера','Джемпери'),(68,'Bottom','Низ','Низ'),(69,'Jeans','Джинсы','Джинси'),(70,'Chinos','Брюки','Брюки'),(71,'Shorts','Шорты','Шорти'),(72,'Shoes, Boots & Trainers','Обувь','Взуття'),(73,'Boots','Ботинки','Черевики'),(74,'Shoes','Туфли','Туфлі'),(75,'Trainers','Кросовки','Кросівки'),(76,'Other','Другое','Інше'),(77,'Accessories','Аксессуары','Аксесуари'),(78,'Beanies','Головные уборы','Шапки'),(79,'Women','Женское','Жіноче');
/*!40000 ALTER TABLE `i18n` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `currency` int(11) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `extraNotes` varchar(2048) DEFAULT NULL,
  `importDate` datetime NOT NULL,
  `isActive` bit(1) NOT NULL,
  `isUsed` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `lastChangeDate` datetime NOT NULL,
  `metaInfo` varchar(1024) DEFAULT NULL,
  `metaKeys` varchar(255) DEFAULT NULL,
  `originPrice` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `salePrice` int(11) NOT NULL,
  `vendorCode` varchar(255) DEFAULT NULL,
  `addedBy_id` bigint(20) DEFAULT NULL,
  `color_id` bigint(20) DEFAULT NULL,
  `itemGroup_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3wud7qq6smq235rud1v4qu5qr` (`addedBy_id`),
  KEY `FKtmc0l7ne4xplaq1xk0akv50hd` (`color_id`),
  KEY `FK3hry4aik6nvpogn7s03ym95oi` (`itemGroup_id`),
  CONSTRAINT `FK3hry4aik6nvpogn7s03ym95oi` FOREIGN KEY (`itemGroup_id`) REFERENCES `itemsgroup` (`id`),
  CONSTRAINT `FK3wud7qq6smq235rud1v4qu5qr` FOREIGN KEY (`addedBy_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKtmc0l7ne4xplaq1xk0akv50hd` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemsgroup`
--

DROP TABLE IF EXISTS `itemsgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemsgroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cat` int(11) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `level` int(11) NOT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `groupName_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9hj2vkq1jsps7xoc7auhbeqwg` (`createdBy_id`),
  KEY `FKro556jckqctv4bxxilwr5ihbk` (`groupName_id`),
  KEY `FKkwmvgl05a8btq8yxncjyht8oo` (`parent_id`),
  CONSTRAINT `FK9hj2vkq1jsps7xoc7auhbeqwg` FOREIGN KEY (`createdBy_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKkwmvgl05a8btq8yxncjyht8oo` FOREIGN KEY (`parent_id`) REFERENCES `itemsgroup` (`id`),
  CONSTRAINT `FKro556jckqctv4bxxilwr5ihbk` FOREIGN KEY (`groupName_id`) REFERENCES `i18n` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemsgroup`
--

LOCK TABLES `itemsgroup` WRITE;
/*!40000 ALTER TABLE `itemsgroup` DISABLE KEYS */;
INSERT INTO `itemsgroup` VALUES (1,0,NULL,0,NULL,58,NULL),(2,1,NULL,1,NULL,59,1),(3,1,NULL,2,NULL,60,2),(4,1,NULL,3,NULL,61,3),(5,4,NULL,4,NULL,62,4),(6,4,NULL,4,NULL,63,4),(7,4,NULL,4,NULL,64,4),(8,4,NULL,4,NULL,65,4),(9,4,NULL,4,NULL,66,4),(10,4,NULL,4,NULL,67,4),(11,1,NULL,3,NULL,68,3),(12,2,NULL,4,NULL,69,11),(13,2,NULL,4,NULL,70,11),(14,3,NULL,4,NULL,71,11),(15,1,NULL,3,NULL,72,3),(16,5,NULL,4,NULL,73,15),(17,5,NULL,4,NULL,74,15),(18,5,NULL,4,NULL,75,15),(19,1,NULL,3,NULL,76,3),(20,6,NULL,4,NULL,77,19),(21,6,NULL,4,NULL,78,19),(22,1,NULL,2,NULL,79,2);
/*!40000 ALTER TABLE `itemsgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `op_code`
--

DROP TABLE IF EXISTS `op_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `op_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `op_code`
--

LOCK TABLES `op_code` WRITE;
/*!40000 ALTER TABLE `op_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `op_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitem`
--

DROP TABLE IF EXISTS `orderitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `currency` int(11) DEFAULT NULL,
  `discount` int(11) NOT NULL,
  `originPrice` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `salePrice` int(11) NOT NULL,
  `shortName` varchar(255) NOT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKennhlr3y1p9kf8odsnav7er4x` (`item_id`),
  CONSTRAINT `FKennhlr3y1p9kf8odsnav7er4x` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitem`
--

LOCK TABLES `orderitem` WRITE;
/*!40000 ALTER TABLE `orderitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `currency` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `earn` int(11) NOT NULL,
  `finalPrice` int(11) NOT NULL,
  `orderState` int(11) DEFAULT NULL,
  `originalPrice` int(11) NOT NULL,
  `salePrice` int(11) NOT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `promoCode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdwccmb4hwgjklc3flyw39hr82` (`createdBy_id`),
  KEY `FKjy3qhffqdivqv8rqmo16nwyk3` (`promoCode_id`),
  CONSTRAINT `FKdwccmb4hwgjklc3flyw39hr82` FOREIGN KEY (`createdBy_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjy3qhffqdivqv8rqmo16nwyk3` FOREIGN KEY (`promoCode_id`) REFERENCES `promo_code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_orderitem`
--

DROP TABLE IF EXISTS `orders_orderitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders_orderitem` (
  `Order_id` bigint(20) NOT NULL,
  `items_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_ja8g39k7p5tbtoyw22841cry4` (`items_id`),
  KEY `FKtjre1uig6aldnssqhwh0jbsgm` (`Order_id`),
  CONSTRAINT `FK99ja0x8d1yxapwd7b2pqdwt87` FOREIGN KEY (`items_id`) REFERENCES `orderitem` (`id`),
  CONSTRAINT `FKtjre1uig6aldnssqhwh0jbsgm` FOREIGN KEY (`Order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_orderitem`
--

LOCK TABLES `orders_orderitem` WRITE;
/*!40000 ALTER TABLE `orders_orderitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders_orderitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `p_messages`
--

DROP TABLE IF EXISTS `p_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `p_messages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `unread` bit(1) NOT NULL,
  `from_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq3y4w6m32axub518pls42r0p6` (`from_id`),
  CONSTRAINT `FKq3y4w6m32axub518pls42r0p6` FOREIGN KEY (`from_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `p_messages`
--

LOCK TABLES `p_messages` WRITE;
/*!40000 ALTER TABLE `p_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `p_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `p_messages_users`
--

DROP TABLE IF EXISTS `p_messages_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `p_messages_users` (
  `PrivateMessage_id` bigint(20) NOT NULL,
  `to_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_4fquicraglr21ehdskpr0co07` (`to_id`),
  KEY `FKs42b315rr9hayqjqixm7kmbg9` (`PrivateMessage_id`),
  CONSTRAINT `FKfp5omvj8yspj696c9en25tm8t` FOREIGN KEY (`to_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKs42b315rr9hayqjqixm7kmbg9` FOREIGN KEY (`PrivateMessage_id`) REFERENCES `p_messages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `p_messages_users`
--

LOCK TABLES `p_messages_users` WRITE;
/*!40000 ALTER TABLE `p_messages_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `p_messages_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phones`
--

DROP TABLE IF EXISTS `phones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phones` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` int(11) NOT NULL,
  `code_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4twcr5u51jikoj02bo2ilp8l1` (`code_id`),
  CONSTRAINT `FK4twcr5u51jikoj02bo2ilp8l1` FOREIGN KEY (`code_id`) REFERENCES `op_code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phones`
--

LOCK TABLES `phones` WRITE;
/*!40000 ALTER TABLE `phones` DISABLE KEYS */;
/*!40000 ALTER TABLE `phones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promo_code`
--

DROP TABLE IF EXISTS `promo_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promo_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `validOnSale` bit(1) NOT NULL,
  `validTo` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promo_code`
--

LOCK TABLES `promo_code` WRITE;
/*!40000 ALTER TABLE `promo_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `promo_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promo_code_itemsgroup`
--

DROP TABLE IF EXISTS `promo_code_itemsgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promo_code_itemsgroup` (
  `PromoCode_id` bigint(20) NOT NULL,
  `validOnGroup_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_eqk8adw7y92g2qb2gc485vmfn` (`validOnGroup_id`),
  KEY `FKgjfy961u83mcd9gmslka7sbvm` (`PromoCode_id`),
  CONSTRAINT `FKfytdpuld6y6duabdhspmn3kko` FOREIGN KEY (`validOnGroup_id`) REFERENCES `itemsgroup` (`id`),
  CONSTRAINT `FKgjfy961u83mcd9gmslka7sbvm` FOREIGN KEY (`PromoCode_id`) REFERENCES `promo_code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promo_code_itemsgroup`
--

LOCK TABLES `promo_code_itemsgroup` WRITE;
/*!40000 ALTER TABLE `promo_code_itemsgroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `promo_code_itemsgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promogroup`
--

DROP TABLE IF EXISTS `promogroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promogroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `discount` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promogroup`
--

LOCK TABLES `promogroup` WRITE;
/*!40000 ALTER TABLE `promogroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `promogroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promogroup_items`
--

DROP TABLE IF EXISTS `promogroup_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promogroup_items` (
  `PromoGroup_id` bigint(20) NOT NULL,
  `items_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_cxhefxjhifyeci4kgob9xws6y` (`items_id`),
  KEY `FKnuf9jolu3oa5iwuy3ijinivn3` (`PromoGroup_id`),
  CONSTRAINT `FKbd23av0wp9k8vhbtrc7h80qa4` FOREIGN KEY (`items_id`) REFERENCES `items` (`id`),
  CONSTRAINT `FKnuf9jolu3oa5iwuy3ijinivn3` FOREIGN KEY (`PromoGroup_id`) REFERENCES `promogroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promogroup_items`
--

LOCK TABLES `promogroup_items` WRITE;
/*!40000 ALTER TABLE `promogroup_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `promogroup_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `size` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) NOT NULL,
  `eu_size_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2e26ai65qkddouseo4hwiocj` (`eu_size_id`),
  CONSTRAINT `FK2e26ai65qkddouseo4hwiocj` FOREIGN KEY (`eu_size_id`) REFERENCES `eu_size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size_fields`
--

DROP TABLE IF EXISTS `size_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `size_fields` (
  `Size_id` bigint(20) NOT NULL,
  `measurements_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_q665h27tmp7u3e6tbcuxeskly` (`measurements_id`),
  KEY `FK4ko7ofdy7ob697rg1aujbxiky` (`Size_id`),
  CONSTRAINT `FK4ko7ofdy7ob697rg1aujbxiky` FOREIGN KEY (`Size_id`) REFERENCES `size` (`id`),
  CONSTRAINT `FK5hj5bawb6723aq14g2ofn8hn9` FOREIGN KEY (`measurements_id`) REFERENCES `fields` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size_fields`
--

LOCK TABLES `size_fields` WRITE;
/*!40000 ALTER TABLE `size_fields` DISABLE KEYS */;
/*!40000 ALTER TABLE `size_fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4c9esomdgjbdbthienu1cxw7p` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (1,0),(2,1),(3,2),(4,3);
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_u_profile`
--

DROP TABLE IF EXISTS `user_u_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_u_profile` (
  `USER_ID` bigint(20) NOT NULL,
  `USER_PROFILE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`,`USER_PROFILE_ID`),
  KEY `FK6vmm5gybxuheevhxk3w5a1qoo` (`USER_PROFILE_ID`),
  CONSTRAINT `FK6vmm5gybxuheevhxk3w5a1qoo` FOREIGN KEY (`USER_PROFILE_ID`) REFERENCES `user_profile` (`id`),
  CONSTRAINT `FKopxm82gsrc8owg25eyj8c3t43` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_u_profile`
--

LOCK TABLES `user_u_profile` WRITE;
/*!40000 ALTER TABLE `user_u_profile` DISABLE KEYS */;
INSERT INTO `user_u_profile` VALUES (1,1);
/*!40000 ALTER TABLE `user_u_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `createDt` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `skype` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `userName` varchar(255) NOT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `phone_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4xg9hm367869b2524a6wx7m11` (`address_id`),
  KEY `FKi1oh2csiwe7axqnc5t42j5c4q` (`createdBy_id`),
  KEY `FKo4a11778i8peh6w0lo1ufdojs` (`phone_id`),
  CONSTRAINT `FK4xg9hm367869b2524a6wx7m11` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FKi1oh2csiwe7axqnc5t42j5c4q` FOREIGN KEY (`createdBy_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKo4a11778i8peh6w0lo1ufdojs` FOREIGN KEY (`phone_id`) REFERENCES `phones` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,NULL,NULL,'4else@i.ua','Misha',NULL,'$2a$10$8vpronUxRIRO3oRUQ6w89eiA/.gqT6jHheFw695dtv4gZUi8OU4P6',NULL,0,'ffw_ATL',NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zip_codes`
--

DROP TABLE IF EXISTS `zip_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zip_codes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zip_codes`
--

LOCK TABLES `zip_codes` WRITE;
/*!40000 ALTER TABLE `zip_codes` DISABLE KEYS */;
/*!40000 ALTER TABLE `zip_codes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-16 14:52:01

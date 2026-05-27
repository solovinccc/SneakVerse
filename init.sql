-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: sneakverse
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `sneakverse`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `sneakverse` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `sneakverse`;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `brandId` int NOT NULL AUTO_INCREMENT,
  `brandName` varchar(255) NOT NULL,
  PRIMARY KEY (`brandId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'Nike'),(2,'Adidas'),(3,'New Balance');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `cartItemId` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `shoeId` int NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`cartItemId`),
  UNIQUE KEY `UKsr4nwdqncp3u3al44p5e6raa8` (`userId`,`shoeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courier`
--

DROP TABLE IF EXISTS `courier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier` (
  `courierId` int NOT NULL AUTO_INCREMENT,
  `phoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`courierId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier`
--

LOCK TABLES `courier` WRITE;
/*!40000 ALTER TABLE `courier` DISABLE KEYS */;
INSERT INTO `courier` VALUES (1,'123456789'),(2,'33413412'),(3,'1231'),(4,'463575264'),(5,'2562424'),(6,'657225425'),(7,'64752642'),(8,'542446'),(9,'424523546'),(10,'54624524'),(13,'143513'),(14,'432153314532 '),(15,'35123143'),(16,'5351235134'),(17,'543156451'),(18,'544514'),(19,'53416432513'),(20,'435351315321');
/*!40000 ALTER TABLE `courier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `orderId` int DEFAULT NULL,
  `orderItemId` int NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `shoeId` int DEFAULT NULL,
  PRIMARY KEY (`orderItemId`),
  KEY `FK8gn227d1vyk4swl9t3sv40hcr` (`orderId`),
  KEY `FKjrfs6mh3tn3bd2tj9kivcreny` (`shoeId`),
  CONSTRAINT `FK8gn227d1vyk4swl9t3sv40hcr` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`),
  CONSTRAINT `FKjrfs6mh3tn3bd2tj9kivcreny` FOREIGN KEY (`shoeId`) REFERENCES `shoes` (`shoeId`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,2,3),(2,2,26,3),(3,3,1,3),(3,4,1,4),(4,5,1,4),(4,6,1,5),(5,7,1,3),(5,8,1,4),(5,9,7,5),(7,11,3,4),(7,12,1,22),(7,13,1,23),(9,16,1,16),(9,17,1,17),(15,38,1,16),(15,39,1,17),(15,40,1,4),(15,41,1,5),(18,49,1,4),(18,50,1,5),(18,51,1,17),(18,52,1,16),(18,53,1,15),(18,54,1,3),(19,55,9,4),(19,56,5,16),(19,57,6,20),(19,58,1,21),(19,60,1,24),(21,62,1,3),(21,63,1,4),(21,64,1,5),(22,65,1,3),(22,66,6,16),(22,67,1,17),(26,73,1,4),(26,74,1,5),(27,75,1,3),(27,76,1,4),(28,77,1,3),(28,78,1,4),(29,79,2,4),(29,80,2,5),(30,81,1,3),(30,82,1,4),(31,83,1,3),(32,84,2,4),(32,85,1,3),(33,86,1,15),(33,87,1,5),(34,88,1,4),(34,89,1,5),(35,90,1,3),(35,91,1,4),(35,92,5,5),(35,93,1,15),(36,94,1,3),(36,95,1,4),(36,96,1,5),(37,97,1,4),(37,98,1,5),(39,101,1,3),(39,102,1,4),(39,103,1,5),(40,104,1,3),(40,105,1,4),(43,111,1,3),(43,112,1,4),(44,113,1,3),(44,114,1,4),(45,115,1,5),(45,116,1,15),(45,117,1,4),(45,118,1,3),(46,119,1,17),(46,120,1,18),(46,121,1,20),(47,122,1,3),(47,123,1,4),(48,124,1,4),(48,125,1,5),(49,126,1,3),(49,127,1,4),(49,128,1,5),(50,129,1,3),(50,130,1,4),(51,131,1,3),(51,132,1,4),(52,133,1,3);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `orderDate` datetime(6) DEFAULT NULL,
  `paymentMethod` enum('CARD','CASH_ON_DELIVERY','PAYPAL') NOT NULL,
  PRIMARY KEY (`orderId`),
  KEY `FK6co8q7ko456baksb6tdjq2dfv` (`userId`),
  CONSTRAINT `FK6co8q7ko456baksb6tdjq2dfv` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'2026-02-04 12:22:43.344034','CARD'),(2,8,'2026-02-05 10:18:00.231570','CARD'),(3,9,'2026-02-07 19:39:53.106969','PAYPAL'),(4,9,'2026-02-07 19:40:39.823147','PAYPAL'),(5,9,'2026-02-07 19:50:55.203478','CASH_ON_DELIVERY'),(7,9,'2026-02-07 20:01:52.502996','CASH_ON_DELIVERY'),(9,9,'2026-02-09 09:20:25.584490','PAYPAL'),(15,9,'2026-02-09 09:21:20.126458','CASH_ON_DELIVERY'),(18,9,'2026-02-09 10:20:42.108756','PAYPAL'),(19,12,'2026-02-10 09:22:45.988693','PAYPAL'),(20,12,'2026-02-10 09:23:01.774556','PAYPAL'),(21,9,'2026-02-10 09:50:10.822845','PAYPAL'),(22,15,'2026-02-10 10:43:40.350951','PAYPAL'),(23,9,'2026-02-17 12:05:04.278066','CASH_ON_DELIVERY'),(26,1,'2026-03-18 10:36:08.011746','PAYPAL'),(27,1,'2026-03-18 10:36:36.144665','PAYPAL'),(28,1,'2026-03-18 10:41:00.292880','CASH_ON_DELIVERY'),(29,1,'2026-03-18 13:41:14.350831','CASH_ON_DELIVERY'),(30,1,'2026-03-19 09:48:24.423854','CASH_ON_DELIVERY'),(31,1,'2026-03-19 09:49:02.302231','CASH_ON_DELIVERY'),(32,1,'2026-03-20 10:14:22.106344','CASH_ON_DELIVERY'),(33,1,'2026-03-23 12:45:56.293810','CASH_ON_DELIVERY'),(34,1,'2026-03-24 14:55:06.705722','CASH_ON_DELIVERY'),(35,1,'2026-03-24 16:09:46.440399','PAYPAL'),(36,1,'2026-03-26 10:43:42.347254','PAYPAL'),(37,1,'2026-03-26 11:41:06.192402','CASH_ON_DELIVERY'),(39,1,'2026-03-31 12:19:38.502886','CASH_ON_DELIVERY'),(40,1,'2026-03-31 12:59:17.651246','CASH_ON_DELIVERY'),(43,1,'2026-04-01 11:04:00.955422','CASH_ON_DELIVERY'),(44,1,'2026-04-01 14:02:34.268627','CASH_ON_DELIVERY'),(45,1,'2026-04-01 14:03:17.065382','CASH_ON_DELIVERY'),(46,1,'2026-04-01 14:04:14.941274','CASH_ON_DELIVERY'),(47,1,'2026-04-01 14:13:45.195622','CASH_ON_DELIVERY'),(48,1,'2026-04-01 14:17:47.898353','CASH_ON_DELIVERY'),(49,1,'2026-04-01 15:32:18.670110','CASH_ON_DELIVERY'),(50,1,'2026-04-08 09:38:18.741472','CASH_ON_DELIVERY'),(51,1,'2026-04-08 09:58:15.441923','CASH_ON_DELIVERY'),(52,1,'2026-04-08 12:42:11.516397','CASH_ON_DELIVERY');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment` (
  `courierId` int NOT NULL,
  `orderId` int NOT NULL,
  `shipmentId` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`shipmentId`),
  UNIQUE KEY `UK6codhlcpmjo8txlrtusvkll5h` (`orderId`),
  KEY `idx_shipment_courierId` (`courierId`),
  CONSTRAINT `FK5fjujp6uooiatcgsn618u8pfb` FOREIGN KEY (`courierId`) REFERENCES `courier` (`courierId`),
  CONSTRAINT `FK824anesuoqy54mw4cotmqrsn4` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
INSERT INTO `shipment` VALUES (1,1,1),(5,2,2),(6,3,3),(7,4,4),(10,5,5),(4,7,7),(3,9,9),(9,15,15),(18,18,18),(2,19,19),(8,20,20),(14,21,21),(14,22,22),(1,23,23),(5,26,26),(5,27,27),(15,28,28),(15,29,29),(1,30,30),(8,31,31),(10,32,32),(4,33,33),(13,34,34),(16,35,35),(4,36,36),(16,37,37),(14,39,39),(20,40,40),(4,43,43),(8,44,44),(18,45,45),(14,46,46),(14,47,47),(20,48,48),(14,49,49),(4,50,50),(19,51,51),(19,52,52);
/*!40000 ALTER TABLE `shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoes`
--

DROP TABLE IF EXISTS `shoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shoes` (
  `brandId` int DEFAULT NULL,
  `shoeId` int NOT NULL AUTO_INCREMENT,
  `shoePrice` decimal(38,2) NOT NULL,
  `shoeSize` float NOT NULL,
  `shoeName` varchar(255) NOT NULL,
  `imageUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shoeId`),
  KEY `FKox6dloxt2ac6sd41m3xucd71y` (`brandId`),
  CONSTRAINT `FKox6dloxt2ac6sd41m3xucd71y` FOREIGN KEY (`brandId`) REFERENCES `brands` (`brandId`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoes`
--

LOCK TABLES `shoes` WRITE;
/*!40000 ALTER TABLE `shoes` DISABLE KEYS */;
INSERT INTO `shoes` VALUES (1,3,120.00,43,'Air Force One','AIR+FORCE+1+\'07.avif'),(1,4,85.00,41,'Dunk Low','dunkdonna.jpg'),(1,5,189.00,44,'Air Force One Valentine Edition','AIR+FORCE+1+RETRO.avif'),(1,15,180.00,43,'Air Max TL 2.5','AIR+MAX+TL+2.5.avif'),(1,16,169.00,41,'Shox TL','NIKE+SHOX+TL.avif'),(1,17,179.00,45,'Vomero Plus SE','NIKE+VOMERO+PLUS+SE.avif'),(1,18,179.00,43,'Vomero 18 GORE-TEX','NIKE+VOMERO+18+GTX.avif'),(1,20,119.00,44,'P-600','NIKE+P-6000.avif'),(1,21,89.00,45,'Air Monarch SE','AIR+MONARCH+IV+SE.avif'),(1,22,89.00,43,'Terrascout','NIKE+TERRASCOUT+(GS).avif'),(1,23,149.00,42,'Air Max 90 Drift','AIR+MAX+90+DRIFT.avif'),(1,24,209.00,43,'Pegasus Premium','NIKE+PEGASUS+PREMIUM.avif');
/*!40000 ALTER TABLE `shoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `homeAddress` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `verificationToken` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Via Giuseppe Garibaldi 1','$2a$10$PDMGpLkrXjgEn/Tdmis8s.05otJAKEFEtfk5UxpZGGYMz1LGdj0Ei','USER','vincenzolisi12@gmail.com',_binary '\0',NULL,'Vincenzo','Lisi');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `sneakverse_orders`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `sneakverse_orders` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `sneakverse_orders`;

--
-- Table structure for table `courier`
--

DROP TABLE IF EXISTS `courier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier` (
  `courier_id` int NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`courier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier`
--

LOCK TABLES `courier` WRITE;
/*!40000 ALTER TABLE `courier` DISABLE KEYS */;
INSERT INTO `courier` VALUES (1,'1123456789'),(2,'233413412'),(3,'31231'),(4,'4463575264'),(5,'52562424'),(6,'6657225425'),(7,'764752642'),(8,'8542446'),(9,'9424523546'),(10,'1054624524'),(11,'13143513'),(12,'14432153314532'),(13,'1535123143'),(14,'165351235134'),(15,'17543156451'),(16,'18544514'),(17,'1953416432513'),(18,'20435351315321');
/*!40000 ALTER TABLE `courier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `shoe_id` int NOT NULL,
  `order_id` int DEFAULT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,4,1),(2,1,5,1),(3,1,3,2),(4,1,4,2),(5,1,3,3),(6,1,4,3),(7,1,3,4),(8,1,4,4),(9,1,3,9),(10,1,4,9),(11,1,3,10),(12,1,4,10),(13,1,3,11),(14,1,4,11),(15,1,3,12),(16,1,4,12),(17,1,4,13),(18,1,5,13),(19,1,3,14),(20,1,4,14),(21,1,3,15),(22,1,4,15),(23,1,4,16),(24,1,5,16),(25,1,3,17),(26,1,4,17),(27,1,3,18),(28,1,4,18),(29,1,3,19),(30,1,4,19),(31,1,3,20),(32,1,4,20),(33,1,3,21),(34,1,4,21),(35,1,3,22),(36,1,4,22),(37,1,3,23),(38,1,4,23);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_date` datetime(6) DEFAULT NULL,
  `payment_method` varchar(255) NOT NULL,
  `user_id` int NOT NULL,
  `delivery_date` datetime(6) DEFAULT NULL,
  `status` enum('DELIVERED','PROCESSING','SHIPPED') NOT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2026-04-10 12:16:54.366653','CASH_ON_DELIVERY',1,NULL,'DELIVERED',NULL),(2,'2026-04-14 10:13:51.476437','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(3,'2026-04-14 10:17:51.221141','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(4,'2026-04-14 10:38:00.456084','CASH_ON_DELIVERY',1,'2026-04-14 10:41:13.032177','DELIVERED','vincenzolisi12@gmail.com'),(9,'2026-04-14 11:29:58.750978','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(10,'2026-04-14 11:31:11.701124','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(11,'2026-04-14 11:33:46.099545','CASH_ON_DELIVERY',1,'2026-04-14 11:35:00.150718','DELIVERED','vincenzolisi12@gmail.com'),(12,'2026-04-14 11:40:49.981225','CASH_ON_DELIVERY',1,'2026-04-14 11:41:18.836867','DELIVERED','vincenzolisi12@gmail.com'),(13,'2026-04-14 12:32:37.112308','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(14,'2026-04-14 12:41:09.274344','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(15,'2026-04-14 14:31:34.041820','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(16,'2026-04-14 14:46:48.056211','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(17,'2026-04-14 14:57:57.382771','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(18,'2026-04-14 15:49:08.893197','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(19,'2026-04-14 16:15:03.740463','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(20,'2026-04-14 16:16:13.381490','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(21,'2026-04-15 10:05:43.360877','CASH_ON_DELIVERY',1,NULL,'PROCESSING','vincenzolisi12@gmail.com'),(22,'2026-04-15 10:08:37.349817','PAYPAL',1,'2026-04-15 10:10:01.929737','DELIVERED','vincenzolisi12@gmail.com'),(23,'2026-04-15 10:13:34.428446','CASH_ON_DELIVERY',1,'2026-04-15 10:16:15.262962','DELIVERED','vincenzolisi12@gmail.com');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment` (
  `shipment_id` int NOT NULL AUTO_INCREMENT,
  `courier_id` int NOT NULL,
  `order_id` int NOT NULL,
  PRIMARY KEY (`shipment_id`),
  UNIQUE KEY `UKp06cong2injx54ipykoegys3w` (`order_id`),
  KEY `FKhn0r0u6nafu8w4a6e09ctp91q` (`courier_id`),
  CONSTRAINT `FK8amw90d62x67honrwucvjado7` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FKhn0r0u6nafu8w4a6e09ctp91q` FOREIGN KEY (`courier_id`) REFERENCES `courier` (`courier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
INSERT INTO `shipment` VALUES (1,15,1),(2,7,2),(3,14,3),(4,13,4),(5,6,9),(6,11,10),(7,17,11),(8,11,12),(9,5,13),(10,6,14),(11,3,15),(12,17,16),(13,12,17),(14,9,18),(15,16,19),(16,7,20),(17,14,21),(18,1,22),(19,18,23);
/*!40000 ALTER TABLE `shipment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-16 10:10:55

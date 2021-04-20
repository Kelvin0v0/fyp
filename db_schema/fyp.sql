-- MySQL dump 10.13  Distrib 5.7.33, for Linux (x86_64)
--
-- Host: localhost    Database: fyp
-- ------------------------------------------------------
-- Server version	5.7.33-0ubuntu0.18.04.1

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `uuid` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `balance` int(11) NOT NULL DEFAULT '0',
  `last_login_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,0,'test',0,NULL,'2021-01-29 18:15:23','2021-01-29 18:15:23'),(2,3,'c3d20b12-063f-42c8-b74e-2acb34c2a8de',10,NULL,'2021-01-29 18:45:54','2021-03-27 14:54:49'),(3,3,'a4582008-df21-47ad-905b-071babbad1ae',20,NULL,'2021-01-29 23:54:03','2021-01-29 23:56:28'),(4,0,'92a664d2-0f1e-41bb-8382-f39d0d971ba8',0,NULL,'2021-01-30 01:06:17','2021-01-30 01:06:17'),(5,3,'11b2c12a-a59a-4221-8805-81954f89f8a4',0,NULL,'2021-01-30 16:15:06','2021-01-30 16:18:59'),(6,3,'4488c6ec-409f-4608-a003-7c98a3aa2d09',25,'2021-04-05 11:45:28','2021-01-30 18:08:48','2021-04-07 16:35:11'),(7,3,'faa2635b-7f14-42d8-ac69-9b4262a90ee5',25,'2021-04-05 08:25:25','2021-01-31 22:14:10','2021-04-05 08:25:25'),(8,3,'83ba279f-d0a0-456e-a82f-80091b63f5bf',10,NULL,'2021-02-18 10:37:59','2021-03-24 20:57:23'),(9,4,'11a3dd84-37f0-469e-97ff-8b183287a6cb',50,'2021-04-20 07:15:40','2021-03-25 11:07:18','2021-04-20 07:28:42'),(10,6,'11a3dd84-37f0-469e-97ff-8b183287a6cb',50,'2021-04-03 10:03:33','2021-04-02 18:20:17','2021-04-03 10:03:33'),(14,7,'11a3dd84-37f0-469e-97ff-8b183287a6cb',0,'2021-04-03 08:37:34','2021-04-03 08:37:34','2021-04-03 08:37:34'),(15,4,'faa2635b-7f14-42d8-ac69-9b4262a90ee5',0,'2021-04-03 09:46:55','2021-04-03 08:39:22','2021-04-03 09:46:55'),(16,13,'11a3dd84-37f0-469e-97ff-8b183287a6cb',0,'2021-04-04 16:14:41','2021-04-04 16:14:41','2021-04-04 16:14:41'),(17,8,'11a3dd84-37f0-469e-97ff-8b183287a6cb',10,'2021-04-05 11:25:25','2021-04-04 16:38:48','2021-04-05 11:33:25'),(18,6,'4488c6ec-409f-4608-a003-7c98a3aa2d09',25,'2021-04-19 13:01:18','2021-04-10 01:05:40','2021-04-19 13:05:00'),(19,4,'c3d20b12-063f-42c8-b74e-2acb34c2a8de',0,'2021-04-20 06:14:33','2021-04-20 06:14:33','2021-04-20 06:14:33'),(20,6,'c3d20b12-063f-42c8-b74e-2acb34c2a8de',15,'2021-04-20 07:28:17','2021-04-20 07:16:24','2021-04-20 07:28:42');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `failed_jobs`
--

DROP TABLE IF EXISTS `failed_jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `failed_jobs` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `connection` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `failed_jobs`
--

LOCK TABLES `failed_jobs` WRITE;
/*!40000 ALTER TABLE `failed_jobs` DISABLE KEYS */;
/*!40000 ALTER TABLE `failed_jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `migrations`
--

DROP TABLE IF EXISTS `migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `migrations` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `migrations`
--

LOCK TABLES `migrations` WRITE;
/*!40000 ALTER TABLE `migrations` DISABLE KEYS */;
INSERT INTO `migrations` VALUES (1,'2014_10_12_000000_create_users_table',1),(21,'2014_10_12_100000_create_password_resets_table',2),(22,'2019_08_19_000000_create_failed_jobs_table',2),(23,'2020_12_30_085119_transaction',2),(24,'2020_12_30_090816_account',2),(25,'2020_12_30_090859_otp',2),(26,'2020_12_30_092608_create_users_table',2);
/*!40000 ALTER TABLE `migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otp` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `uuid` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expire_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp`
--

LOCK TABLES `otp` WRITE;
/*!40000 ALTER TABLE `otp` DISABLE KEYS */;
/*!40000 ALTER TABLE `otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_resets`
--

DROP TABLE IF EXISTS `password_resets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `password_resets` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  KEY `password_resets_email_index` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_resets`
--

LOCK TABLES `password_resets` WRITE;
/*!40000 ALTER TABLE `password_resets` DISABLE KEYS */;
/*!40000 ALTER TABLE `password_resets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `status` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `phone_num` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_admin` tinyint(1) DEFAULT NULL,
  `is_merchant` tinyint(1) DEFAULT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Admin','admin@itsolutionstuff.com',NULL,NULL,'$2y$10$ddkOBxzVrJZ.IZINrqtYXO5CA.oE0HqEN/v3n9iY2j.IMSkljyL.C',1,NULL,NULL,'2021-01-21 01:07:17','2021-01-21 01:07:17'),(2,'User','user@itsolutionstuff.com',NULL,NULL,'$2y$10$/lO1YMzXHc1WHTZ7i3iNZ.yp8f23exjqkLZLclMtLCkUM3Hcn/Vmu',0,NULL,NULL,'2021-01-21 01:07:17','2021-01-21 01:07:17'),(3,'hello','hello@hello.com',NULL,NULL,'$2y$10$.5impCNtbriO9MAH59w2T.8KIc4n.SOvLj4SJ.qhbcx/FV56Mir2q',1,NULL,'JIZ49tHSJLAvUgZcc24BHe1JORVH5wAftvjq7YTVbxislV2KaD9ex3gkAqJE','2021-01-21 01:09:02','2021-01-21 01:09:02'),(4,'Alice','Alice@123.com',NULL,'12345678','$2y$10$QuaHJh4q7kKrfuC4ak9MTOTnTj7H4S0TUq6d..FVfurVPrdBG2yaa',NULL,1,NULL,'2021-01-28 19:31:08','2021-01-28 19:31:08'),(5,'test','test@test.com',NULL,'123456','$2y$10$By/WXptvBuzbjrzQYgdaFuz4ekbd3CATuOd65X3ayw.Jgk.Yg96I6',NULL,1,NULL,'2021-02-03 21:52:47','2021-02-03 21:52:47'),(6,'Bob','bob@123.com',NULL,'12345678','$2y$10$glz8ab85qpKoF40gFi9Ci.kuDNvbPE9I2GRHXvPW2NhEwm8H1HA06',NULL,0,NULL,'2021-03-25 12:24:56','2021-03-25 12:24:56'),(7,'tester','test@hh.com',NULL,'1234569','$2y$10$iMuSTFv308WI4C2fPxT6hOGYzqyqO8/rUROQeMJ7aiuSn4nF52Cca',NULL,1,NULL,'2021-04-02 18:44:35','2021-04-02 18:44:35'),(8,'Tom','tom@123.com',NULL,'22356989','$2y$10$8KpP0Ktu2ro9hH9X2kEWLO6.DS/TNLK6QzPB7MWJbm69/.0CB0oIW',NULL,0,NULL,'2021-04-04 16:35:46','2021-04-04 16:35:46');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-20 20:10:33

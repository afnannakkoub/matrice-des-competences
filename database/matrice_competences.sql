-- MySQL dump 10.13  Distrib 9.1.0, for Win64 (x86_64)
--
-- Host: localhost    Database: matrice_competences
-- ------------------------------------------------------
-- Server version	9.1.0

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Cloud','Cloud Computing1'),(2,'DevOps','Docker Kubernetes CI/CD'),(3,'Programming','Programming languages'),(5,'Finance','contrôle financier');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competences`
--

DROP TABLE IF EXISTS `competences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competences` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(150) NOT NULL,
  `description` text,
  `categorie_id` bigint NOT NULL,
  `archive` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_categorie` (`categorie_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competences`
--

LOCK TABLES `competences` WRITE;
/*!40000 ALTER TABLE `competences` DISABLE KEYS */;
INSERT INTO `competences` VALUES (1,'Java','Java Programming Language_',3,0),(5,'aws','qwer',1,0),(4,'angular','prg',3,0),(6,'Java','Java Programming Language',3,0),(7,'qwe','qwertyuiop',1,0),(8,'qwer','aDevOps',2,0),(9,'af','adfgh',5,1);
/*!40000 ALTER TABLE `competences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluations`
--

DROP TABLE IF EXISTS `evaluations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `utilisateur_id` bigint NOT NULL,
  `competence_id` bigint NOT NULL,
  `niveau` int NOT NULL,
  `commentaire` text,
  `date_evaluation` date DEFAULT (curdate()),
  `evalue_par` bigint DEFAULT NULL,
  `auto_evaluation` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_user` (`utilisateur_id`),
  KEY `fk_competence` (`competence_id`),
  KEY `fk_evaluateur` (`evalue_par`),
  CONSTRAINT `evaluations_chk_1` CHECK ((`niveau` between 0 and 5))
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluations`
--

LOCK TABLES `evaluations` WRITE;
/*!40000 ALTER TABLE `evaluations` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formation`
--

DROP TABLE IF EXISTS `formation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `formation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `titre` varchar(255) NOT NULL,
  `description` text,
  `duree` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `competence_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_formation_competence` (`competence_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formation`
--

LOCK TABLES `formation` WRITE;
/*!40000 ALTER TABLE `formation` DISABLE KEYS */;
INSERT INTO `formation` VALUES (1,'Java Advanced','Formation avancée en développement Java.','20 heures','Online',1);
/*!40000 ALTER TABLE `formation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poste_competence`
--

DROP TABLE IF EXISTS `poste_competence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poste_competence` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `poste` varchar(100) NOT NULL,
  `competence_id` bigint NOT NULL,
  `niveau_requis` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_poste_competence` (`competence_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poste_competence`
--

LOCK TABLES `poste_competence` WRITE;
/*!40000 ALTER TABLE `poste_competence` DISABLE KEYS */;
INSERT INTO `poste_competence` VALUES (1,'Backend Developer',1,4),(2,'Marketing',9,3),(3,'Chef de produit',7,3),(4,'Développeur',1,4),(9,'aaa',5,3),(8,'dev',4,4);
/*!40000 ALTER TABLE `poste_competence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propositions_competence`
--

DROP TABLE IF EXISTS `propositions_competence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `propositions_competence` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(150) NOT NULL,
  `description` text,
  `utilisateur_id` bigint NOT NULL,
  `statut` enum('EN_ATTENTE','APPROUVEE','REFUSEE') DEFAULT 'EN_ATTENTE',
  `date_proposition` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_user_prop` (`utilisateur_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propositions_competence`
--

LOCK TABLES `propositions_competence` WRITE;
/*!40000 ALTER TABLE `propositions_competence` DISABLE KEYS */;
/*!40000 ALTER TABLE `propositions_competence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rapports`
--

DROP TABLE IF EXISTS `rapports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rapports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `titre` varchar(200) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `date_generation` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `genere_par` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_manager_report` (`genere_par`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rapports`
--

LOCK TABLES `rapports` WRITE;
/*!40000 ALTER TABLE `rapports` DISABLE KEYS */;
/*!40000 ALTER TABLE `rapports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nom` (`nom`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'ADMIN'),(3,'EMPLOYE'),(4,'MANAGER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilisateur_competence`
--

DROP TABLE IF EXISTS `utilisateur_competence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateur_competence` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `utilisateur_id` bigint NOT NULL,
  `competence_id` bigint NOT NULL,
  `niveau_employe` int NOT NULL,
  `niveau_valide` tinyint DEFAULT NULL,
  `statut` enum('EN_ATTENTE','VALIDE') DEFAULT 'EN_ATTENTE',
  `date_creation` datetime DEFAULT CURRENT_TIMESTAMP,
  `date_validation` datetime DEFAULT NULL,
  `manager_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `utilisateur_id` (`utilisateur_id`,`competence_id`),
  KEY `competence_id` (`competence_id`),
  KEY `manager_id` (`manager_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateur_competence`
--

LOCK TABLES `utilisateur_competence` WRITE;
/*!40000 ALTER TABLE `utilisateur_competence` DISABLE KEYS */;
INSERT INTO `utilisateur_competence` VALUES (2,5,4,3,2,'VALIDE','2026-08-12 12:31:12','2026-08-26 14:34:36',4),(3,5,1,4,3,'VALIDE','2026-08-26 21:34:05','2026-08-26 22:29:40',4),(4,5,7,2,2,'VALIDE','2026-08-27 10:14:33','2026-08-27 11:03:42',4),(5,8,4,2,2,'VALIDE','2026-08-27 14:38:49','2026-08-27 14:39:40',4),(6,10,1,3,3,'VALIDE','2026-08-27 15:08:36','2026-08-27 15:09:11',4),(7,10,4,2,2,'VALIDE','2026-08-27 15:09:18','2026-08-27 15:09:18',4),(8,8,5,3,3,'VALIDE','2026-08-29 11:43:18','2026-08-29 11:43:18',4),(9,5,9,1,NULL,'EN_ATTENTE','2026-08-30 14:14:47',NULL,NULL),(10,6,5,3,NULL,'EN_ATTENTE','2026-08-30 19:09:44',NULL,NULL);
/*!40000 ALTER TABLE `utilisateur_competence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilisateurs`
--

DROP TABLE IF EXISTS `utilisateurs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateurs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `role_id` bigint NOT NULL,
  `manager_id` bigint DEFAULT NULL,
  `actif` tinyint(1) DEFAULT '1',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `poste` varchar(100) NOT NULL,
  `departement` varchar(70) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_role` (`role_id`),
  KEY `fk_manager` (`manager_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateurs`
--

LOCK TABLES `utilisateurs` WRITE;
/*!40000 ALTER TABLE `utilisateurs` DISABLE KEYS */;
INSERT INTO `utilisateurs` VALUES (12,'benali','ahmad','ahmad@gmail.com','123456',2,NULL,1,'2026-08-30 18:11:18','chef','informatique'),(4,'All','Am','am@test.com','123456',4,NULL,1,NULL,'Développeur','Informatique'),(5,'Sara','Aminaa','sara@test.com','123456',3,4,1,NULL,'Développeur','IT'),(6,'ben','douae','douae@gmail.com','123',3,NULL,1,NULL,'Gestionnaire RH','Ressources Humaines'),(7,'qq','ll','ll@gmail.com','123',3,NULL,1,NULL,'aaa','iii'),(8,'qq','qwe','qwe@gmail.com','123',3,4,1,NULL,'aaa','iii'),(9,'ben','alami','alami@gmail.com','123',3,4,1,NULL,'dev','it'),(10,'ben','alami','ben@gmail.com','123',3,4,1,NULL,'dev','it'),(11,'ali','abd','ali@gmail.com','123',4,NULL,1,NULL,'Chef de produit','Marketing');
/*!40000 ALTER TABLE `utilisateurs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-31  0:19:30

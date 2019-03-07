start transaction;

create database `Acme-Madruga`;

use `Acme-Madruga`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete on `Acme-Madruga`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables, lock tables, create view, create routine, alter routine, execute, trigger, show view on `Acme-Madruga`.* to 'acme-manager'@'%';


-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Madruga
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `spammer` bit(1) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_i7xei45auwq1f6vu25985riuh` (`user_account`),
  CONSTRAINT `FK_i7xei45auwq1f6vu25985riuh` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_from`
--

DROP TABLE IF EXISTS `actor_from`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_from` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `terms_and_condicions` bit(1) DEFAULT NULL,
  `user_accountpassword` varchar(255) DEFAULT NULL,
  `user_accountuser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_from`
--

LOCK TABLES `actor_from` WRITE;
/*!40000 ALTER TABLE `actor_from` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor_from` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `spammer` bit(1) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1469,0,'Reina Mercedes','conwdasto@jmsx.es','jmsx12','Jose Manuel','+34647607406','http://tinyurl.com/picture.png',0.5,'\0','Gonzalez',1458),(1470,0,'Reina Mercedes','lusi@gamil.es','jluis','Jose Luis','+34647307406','http://tinyurl.com/picture.png',0.5,'\0','Gonzalez',1459);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1500,0,'Capilla San Jorge'),(1501,0,'Capilla La Estrella'),(1502,0,'Capilla Mexico'),(1503,0,'Capilla San Enrique');
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `area_pictures`
--

DROP TABLE IF EXISTS `area_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area_pictures` (
  `area` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_s2y5bun5v8b608aoptnxfuelm` (`area`),
  CONSTRAINT `FK_s2y5bun5v8b608aoptnxfuelm` FOREIGN KEY (`area`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area_pictures`
--

LOCK TABLES `area_pictures` WRITE;
/*!40000 ALTER TABLE `area_pictures` DISABLE KEYS */;
INSERT INTO `area_pictures` VALUES (1500,'https://upload.wikimedia.org/wikipedia/commons/1/15/Cantabria_BarcenaCicero_retablo_capilla_palacioRugama_lou.JPG\n				'),(1500,'http://www.casabeneficencia.es/wp-content/uploads/2017/03/capilla-rec.jpg\n				'),(1500,'http://images.adsttc.com/media/images/5570/fc61/e58e/ce23/c800/008e/large_jpg/capilla-3910.jpg?1433467987\n				'),(1501,'http://1.bp.blogspot.com/--XhoeQjNsb8/T9IDuLD1dLI/AAAAAAAAX9A/2OFZ8YQHkNk/s1600/Vista+general.jpg\n				'),(1502,'https://3.bp.blogspot.com/-1Ru7y6uKptQ/WMX3Xg9KbnI/AAAAAAAAADc/NiD2xdEeC-4qN-9bsAFL3wlUuQiO_xzyACLcB/s1600/Texcoco-Capilla%2Bde%2Bla%2BTercera%2Borden.jpg\n				'),(1503,'https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Henry7Chapel_09.jpg/1200px-Henry7Chapel_09.jpg\n				');
/*!40000 ALTER TABLE `area_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brotherhood`
--

DROP TABLE IF EXISTS `brotherhood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brotherhood` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `spammer` bit(1) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `area` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j7wkl7fdmmjo3c5wa21wo8nl` (`user_account`),
  KEY `FK_oku65kpdi3ro8ta0bmmxdkidt` (`area`),
  CONSTRAINT `FK_j7wkl7fdmmjo3c5wa21wo8nl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_oku65kpdi3ro8ta0bmmxdkidt` FOREIGN KEY (`area`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brotherhood`
--

LOCK TABLES `brotherhood` WRITE;
/*!40000 ALTER TABLE `brotherhood` DISABLE KEYS */;
INSERT INTO `brotherhood` VALUES (1512,0,'C/ Esperanza','esperanza@hotmail.es','Triana','Esperanza de triana','+34655398741','http://tinyurl.com/serfdq.png',0.7,'\0','surnameBrother',1463,'2018-02-16','La Esperanza de Triana',1500),(1513,0,'C/ Grna Poder','granpoder@hotmail.es','Gran Poder','Gran poder','+34655398741','http://tinyurl.com/serfdq.png',0.7,'\0','surnameBrother',1464,'2018-02-16','El gran poder',1501);
/*!40000 ALTER TABLE `brotherhood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brotherhood_area_form`
--

DROP TABLE IF EXISTS `brotherhood_area_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brotherhood_area_form` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `area` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_99wour6atk038xvsqfevcssak` (`area`),
  CONSTRAINT `FK_99wour6atk038xvsqfevcssak` FOREIGN KEY (`area`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brotherhood_area_form`
--

LOCK TABLES `brotherhood_area_form` WRITE;
/*!40000 ALTER TABLE `brotherhood_area_form` DISABLE KEYS */;
/*!40000 ALTER TABLE `brotherhood_area_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brotherhood_form`
--

DROP TABLE IF EXISTS `brotherhood_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brotherhood_form` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `terms_and_condicions` bit(1) DEFAULT NULL,
  `user_accountpassword` varchar(255) DEFAULT NULL,
  `user_accountuser` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brotherhood_form`
--

LOCK TABLES `brotherhood_form` WRITE;
/*!40000 ALTER TABLE `brotherhood_form` DISABLE KEYS */;
/*!40000 ALTER TABLE `brotherhood_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brotherhood_form_pictures`
--

DROP TABLE IF EXISTS `brotherhood_form_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brotherhood_form_pictures` (
  `brotherhood_form` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_o6b2oqa2oaxyqwul3ubl318we` (`brotherhood_form`),
  CONSTRAINT `FK_o6b2oqa2oaxyqwul3ubl318we` FOREIGN KEY (`brotherhood_form`) REFERENCES `brotherhood_form` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brotherhood_form_pictures`
--

LOCK TABLES `brotherhood_form_pictures` WRITE;
/*!40000 ALTER TABLE `brotherhood_form_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `brotherhood_form_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brotherhood_pictures`
--

DROP TABLE IF EXISTS `brotherhood_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brotherhood_pictures` (
  `brotherhood` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_8d0m2wigmw0c32w3yqpgqlpyl` (`brotherhood`),
  CONSTRAINT `FK_8d0m2wigmw0c32w3yqpgqlpyl` FOREIGN KEY (`brotherhood`) REFERENCES `brotherhood` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brotherhood_pictures`
--

LOCK TABLES `brotherhood_pictures` WRITE;
/*!40000 ALTER TABLE `brotherhood_pictures` DISABLE KEYS */;
INSERT INTO `brotherhood_pictures` VALUES (1512,'http://tyniurl.com/dsfresesd.png'),(1512,'http://tyniurl.com/dsfes3r32w45d.png'),(1512,'http://tyniurl.com/dsfe2435rwsd.png'),(1513,'http://tyniurl.com/dsfresesd.png'),(1513,'http://tyniurl.com/dsfes3r32w45d.png'),(1513,'http://tyniurl.com/dsfe2435rwsd.png');
/*!40000 ALTER TABLE `brotherhood_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_parameters`
--

DROP TABLE IF EXISTS `configuration_parameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_parameters` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `country_phone_code` varchar(255) DEFAULT NULL,
  `finder_time` int(11) NOT NULL,
  `max_finder_results` int(11) NOT NULL,
  `sys_name` varchar(255) DEFAULT NULL,
  `welcome_message_en` varchar(255) DEFAULT NULL,
  `welcome_message_esp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_parameters`
--

LOCK TABLES `configuration_parameters` WRITE;
/*!40000 ALTER TABLE `configuration_parameters` DISABLE KEYS */;
INSERT INTO `configuration_parameters` VALUES (1517,0,'https://tinyurl.com/acme-madruga','+34',1,10,'Acme Madrugá','Welcome to Acme Madrugá!  The site to organise your processions','¡Bienvenidos a Acme Madrugá!  Tu sitio para organizar procesiones');
/*!40000 ALTER TABLE `configuration_parameters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_parameters_negative_words`
--

DROP TABLE IF EXISTS `configuration_parameters_negative_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_parameters_negative_words` (
  `configuration_parameters` int(11) NOT NULL,
  `negative_words` varchar(255) DEFAULT NULL,
  KEY `FK_4auqbhvtjhot2strl1dqnumwd` (`configuration_parameters`),
  CONSTRAINT `FK_4auqbhvtjhot2strl1dqnumwd` FOREIGN KEY (`configuration_parameters`) REFERENCES `configuration_parameters` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_parameters_negative_words`
--

LOCK TABLES `configuration_parameters_negative_words` WRITE;
/*!40000 ALTER TABLE `configuration_parameters_negative_words` DISABLE KEYS */;
INSERT INTO `configuration_parameters_negative_words` VALUES (1517,'not'),(1517,'bad'),(1517,'horrible'),(1517,'average'),(1517,'disaster'),(1517,'malo'),(1517,'media'),(1517,'desastre');
/*!40000 ALTER TABLE `configuration_parameters_negative_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_parameters_positive_words`
--

DROP TABLE IF EXISTS `configuration_parameters_positive_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_parameters_positive_words` (
  `configuration_parameters` int(11) NOT NULL,
  `positive_words` varchar(255) DEFAULT NULL,
  KEY `FK_br643v4oj38jqfgcq5f11q8cx` (`configuration_parameters`),
  CONSTRAINT `FK_br643v4oj38jqfgcq5f11q8cx` FOREIGN KEY (`configuration_parameters`) REFERENCES `configuration_parameters` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_parameters_positive_words`
--

LOCK TABLES `configuration_parameters_positive_words` WRITE;
/*!40000 ALTER TABLE `configuration_parameters_positive_words` DISABLE KEYS */;
INSERT INTO `configuration_parameters_positive_words` VALUES (1517,'good'),(1517,'factastic'),(1517,'excellent'),(1517,'great'),(1517,'amazing'),(1517,'terrific'),(1517,'beautiful'),(1517,'bueno'),(1517,'buena'),(1517,'fantástico'),(1517,'fantástica'),(1517,'excelente'),(1517,'genial'),(1517,'terrorífico'),(1517,'bonito'),(1517,'bonita');
/*!40000 ALTER TABLE `configuration_parameters_positive_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_parameters_spam_words`
--

DROP TABLE IF EXISTS `configuration_parameters_spam_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_parameters_spam_words` (
  `configuration_parameters` int(11) NOT NULL,
  `spam_words` varchar(255) DEFAULT NULL,
  KEY `FK_r9o9dd0kww7hr04phroji3ig7` (`configuration_parameters`),
  CONSTRAINT `FK_r9o9dd0kww7hr04phroji3ig7` FOREIGN KEY (`configuration_parameters`) REFERENCES `configuration_parameters` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_parameters_spam_words`
--

LOCK TABLES `configuration_parameters_spam_words` WRITE;
/*!40000 ALTER TABLE `configuration_parameters_spam_words` DISABLE KEYS */;
INSERT INTO `configuration_parameters_spam_words` VALUES (1517,'sex'),(1517,'viagra'),(1517,'cialis'),(1517,'one million'),(1517,'you\'ve been selected'),(1517,'nigeria'),(1517,'sexo'),(1517,'un millón'),(1517,'ha sido seleccionado');
/*!40000 ALTER TABLE `configuration_parameters_spam_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrolment`
--

DROP TABLE IF EXISTS `enrolment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrolment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `drop_out` datetime DEFAULT NULL,
  `enrolled` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `brotherhood` int(11) NOT NULL,
  `member` int(11) NOT NULL,
  `position` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kacft8i7jufll7t0nyk68cptn` (`brotherhood`),
  KEY `FK_o5re2u23cjomuht1q0fjmu09u` (`member`),
  KEY `FK_aopae51comq4kt7iadag2ygye` (`position`),
  CONSTRAINT `FK_aopae51comq4kt7iadag2ygye` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_kacft8i7jufll7t0nyk68cptn` FOREIGN KEY (`brotherhood`) REFERENCES `brotherhood` (`id`),
  CONSTRAINT `FK_o5re2u23cjomuht1q0fjmu09u` FOREIGN KEY (`member`) REFERENCES `member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrolment`
--

LOCK TABLES `enrolment` WRITE;
/*!40000 ALTER TABLE `enrolment` DISABLE KEYS */;
INSERT INTO `enrolment` VALUES (1514,0,NULL,'\0','2018-03-16 15:20:00',1512,1466,1504),(1515,0,NULL,'\0','2018-03-14 17:30:00',1513,1467,1505),(1516,0,NULL,'\0','2018-03-14 17:30:00',1513,1466,1506);
/*!40000 ALTER TABLE `enrolment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrolment_form`
--

DROP TABLE IF EXISTS `enrolment_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrolment_form` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ojy7iy9dmstlx7h3c2mtrdkcg` (`position`),
  CONSTRAINT `FK_ojy7iy9dmstlx7h3c2mtrdkcg` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrolment_form`
--

LOCK TABLES `enrolment_form` WRITE;
/*!40000 ALTER TABLE `enrolment_form` DISABLE KEYS */;
/*!40000 ALTER TABLE `enrolment_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `area_name` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `max_date` datetime DEFAULT NULL,
  `min_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
INSERT INTO `finder` VALUES (1511,0,'','2019-03-06 15:20:00','poder','2030-04-16 15:20:00','2018-04-16 15:20:00');
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_processions`
--

DROP TABLE IF EXISTS `finder_processions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_processions` (
  `finder` int(11) NOT NULL,
  `processions` int(11) NOT NULL,
  KEY `FK_6p1eb8rm7luhusax3fd8uksw1` (`processions`),
  KEY `FK_1cueuewr7dicti6yua7dv4b6c` (`finder`),
  CONSTRAINT `FK_1cueuewr7dicti6yua7dv4b6c` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_6p1eb8rm7luhusax3fd8uksw1` FOREIGN KEY (`processions`) REFERENCES `procession` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_processions`
--

LOCK TABLES `finder_processions` WRITE;
/*!40000 ALTER TABLE `finder_processions` DISABLE KEYS */;
INSERT INTO `finder_processions` VALUES (1511,1533),(1511,1534);
/*!40000 ALTER TABLE `finder_processions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `float`
--

DROP TABLE IF EXISTS `float`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `float` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `brotherhood` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bmjnirgvwerdv604sfiusq45v` (`brotherhood`),
  CONSTRAINT `FK_bmjnirgvwerdv604sfiusq45v` FOREIGN KEY (`brotherhood`) REFERENCES `brotherhood` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `float`
--

LOCK TABLES `float` WRITE;
/*!40000 ALTER TABLE `float` DISABLE KEYS */;
INSERT INTO `float` VALUES (1546,0,'Cristo de José  Pires Azcárraga (1947)','Cristo de las Almas',1512),(1547,0,'Virgen de Fernández Andés (1936) restaurada por Manuel Ramos Corona en 1992.','Virgen de Gracia y Amparo',1512),(1548,0,'El Nazareno es de Felipe de Ribas (1641) restaurado por Ortega Bru.','Divina Misericordia',1512),(1549,0,'El Crucificado de Felipe Martínez, 1682). La Virgen de los Remedios y el Misterio (Manuel Gutiérrez, 1865) excepto San Juan (José Sánchez, 1859).','Siete Palabras y Virgen de los Remedios',1512),(1550,0,'La Virgen de la Cabeza procede de la talla de un ángel, realizado por Pizarro (1901) que fue adaptado por Manuel Escamilla en 1956.','Virgen de la Cabeza',1512);
/*!40000 ALTER TABLE `float` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `float_pictures`
--

DROP TABLE IF EXISTS `float_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `float_pictures` (
  `float` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_dp4g3ry840d4yqsjkifnm8q3t` (`float`),
  CONSTRAINT `FK_dp4g3ry840d4yqsjkifnm8q3t` FOREIGN KEY (`float`) REFERENCES `float` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `float_pictures`
--

LOCK TABLES `float_pictures` WRITE;
/*!40000 ALTER TABLE `float_pictures` DISABLE KEYS */;
INSERT INTO `float_pictures` VALUES (1546,'https://www.semana-santa.org/wp-content/uploads/2017/02/cristodelasalmas.jpg\n				'),(1547,'https://lh5.ggpht.com/-ZDiNKFYfaFo/Ts1j0aYu1HI/AAAAAAAABY8/aSjcCksNUNM/Virgen%252520de%252520Gracia%252520y%252520Amparo%252520-%252520Los%252520Javieres%252520-%252520Sevillanvbre2011%252520%2525281%252529.jpg?imgmax=640\n				'),(1548,'https://www.semana-santa.org/wp-content/uploads/2017/03/senor-divina-misericordia.jpg\n				'),(1549,'https://archivos.wikanda.es/sevillapedia/thumb/Paso_cristo_siete_palabras.jpg/700px-Paso_cristo_siete_palabras.jpg\n				'),(1550,'https://sevilla.abc.es/Media/201202/23/virgen-cabeza-siete-palabras--644x362.jpg\n				'),(1550,'https://cinturondeesparto.com/0.1/wp-content/uploads/2017/12/las-siete-palabras-celebro-besamanos-a-la-virgen-de-la-cabeza-3.jpg\n				');
/*!40000 ALTER TABLE `float_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_system_folder` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `actor` int(11) NOT NULL,
  `father` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_13reojwl2ypyecuvvsopd48lq` (`father`),
  CONSTRAINT `FK_13reojwl2ypyecuvvsopd48lq` FOREIGN KEY (`father`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (1473,0,'','Out box',1469,NULL),(1474,0,'','In box',1469,NULL),(1475,0,'','Trash box',1469,NULL),(1476,0,'','Notification box',1469,NULL),(1477,0,'','Spam box',1469,NULL),(1478,0,'','Out box',1470,NULL),(1479,0,'','In box',1470,NULL),(1480,0,'','Trash box',1470,NULL),(1481,0,'','Notification box',1470,NULL),(1482,0,'','Spam box',1470,NULL),(1483,0,'','Out box',1466,NULL),(1484,0,'','In box',1466,NULL),(1485,0,'','Trash box',1466,NULL),(1486,0,'','Notification box',1466,NULL),(1487,0,'','Spam box',1466,NULL),(1488,0,'','Out box',1467,NULL),(1489,0,'','In box',1467,NULL),(1490,0,'','Trash box',1467,NULL),(1491,0,'','Notification box',1467,NULL),(1492,0,'','Spam box',1467,NULL),(1493,0,'','Out box',1468,NULL),(1494,0,'','In box',1468,NULL),(1495,0,'','Trash box',1468,NULL),(1496,0,'','Notification box',1468,NULL),(1497,0,'','Spam box',1468,NULL),(1520,0,'','Out box',1512,NULL),(1521,0,'','In box',1512,NULL),(1522,0,'','Trash box',1512,NULL),(1523,0,'','Notification box',1512,NULL),(1524,0,'','Spam box',1512,NULL),(1525,0,'','Out box',1513,NULL),(1526,0,'','In box',1513,NULL),(1527,0,'','Trash box',1513,NULL),(1528,0,'','Notification box',1513,NULL),(1529,0,'','Spam box',1513,NULL);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder_messages`
--

DROP TABLE IF EXISTS `folder_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder_messages` (
  `folder` int(11) NOT NULL,
  `messages` int(11) NOT NULL,
  KEY `FK_pd7js9rp0nie7ft4b2ltq7jx0` (`messages`),
  KEY `FK_p4c0hkadh5uwpdsjbyqfkauak` (`folder`),
  CONSTRAINT `FK_p4c0hkadh5uwpdsjbyqfkauak` FOREIGN KEY (`folder`) REFERENCES `folder` (`id`),
  CONSTRAINT `FK_pd7js9rp0nie7ft4b2ltq7jx0` FOREIGN KEY (`messages`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder_messages`
--

LOCK TABLES `folder_messages` WRITE;
/*!40000 ALTER TABLE `folder_messages` DISABLE KEYS */;
INSERT INTO `folder_messages` VALUES (1483,1498),(1483,1499),(1485,1532),(1489,1498),(1489,1531),(1494,1498),(1494,1531),(1520,1530),(1520,1531),(1521,1499),(1526,1530);
/*!40000 ALTER TABLE `folder_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `spammer` bit(1) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `finder` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_porqrqrfw70onhs6pelgepxhu` (`user_account`),
  KEY `FK_mjoa5yw12sbvknx53x7fu5a1g` (`finder`),
  CONSTRAINT `FK_porqrqrfw70onhs6pelgepxhu` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_mjoa5yw12sbvknx53x7fu5a1g` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1466,1,'Reina Mercedes','contacto@jmsx.es','jmsx','Jose Manuel','+34647607406','http://tinyurl.com/picture.png',0.5,'\0','Gonzalez',1460,1511),(1467,0,'Reina Mercedes','ant@opensource.es','a80801','Antonio','+34647675239','http://tinyurl.com/picture.png',0.5,'\0','Ruiz',1461,NULL),(1468,0,'Reina Mercedes','HACKER@gmail.es','jmsx','Manuel MALO','+34647607406','http://tinyurl.com/picture.png',0.5,'','SPAMMER',1462,NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `sender` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1498,0,'body 1','2017-10-01 15:20:00','HIGH','subject 1',1466),(1499,0,'body 1','2018-09-01 15:20:00','HIGH','subject 3',1466),(1530,0,'body 2','2017-11-02 15:20:00','LOW','subject 2',1512),(1531,0,'body 4','2018-04-16 15:20:00','HIGH','subject 4',1512),(1532,0,'body 5','2018-04-16 12:20:00','HIGH','subject 5',1512);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_recipients`
--

DROP TABLE IF EXISTS `message_recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_recipients` (
  `message` int(11) NOT NULL,
  `recipients` int(11) NOT NULL,
  KEY `FK_1odmg2n3n487tvhuxx5oyyya2` (`message`),
  CONSTRAINT `FK_1odmg2n3n487tvhuxx5oyyya2` FOREIGN KEY (`message`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_recipients`
--

LOCK TABLES `message_recipients` WRITE;
/*!40000 ALTER TABLE `message_recipients` DISABLE KEYS */;
INSERT INTO `message_recipients` VALUES (1498,1467),(1499,1512),(1530,1513),(1531,1467),(1532,1466);
/*!40000 ALTER TABLE `message_recipients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_tags`
--

DROP TABLE IF EXISTS `message_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_tags` (
  `message` int(11) NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  KEY `FK_suckduhsrrtot7go5ejeev57w` (`message`),
  CONSTRAINT `FK_suckduhsrrtot7go5ejeev57w` FOREIGN KEY (`message`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_tags`
--

LOCK TABLES `message_tags` WRITE;
/*!40000 ALTER TABLE `message_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name_english` varchar(255) DEFAULT NULL,
  `name_spanish` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (1504,0,'President','Presidente'),(1505,0,'Vice President','Vicepresidente'),(1506,0,'Secretary','Secretario'),(1507,0,'Treasurer','Tesorero'),(1508,0,'Historian','Historiador'),(1509,0,'Fundraiser','Promotor'),(1510,0,'Officer','Vocal');
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `procession`
--

DROP TABLE IF EXISTS `procession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `procession` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `max_columns` int(11) DEFAULT NULL,
  `max_rows` int(11) DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `brotherhood` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sc8hr69nciikog00mms5616f8` (`ticker`),
  KEY `FK_k1aiqpf52p76km7ua07nlt1go` (`brotherhood`),
  CONSTRAINT `FK_k1aiqpf52p76km7ua07nlt1go` FOREIGN KEY (`brotherhood`) REFERENCES `brotherhood` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `procession`
--

LOCK TABLES `procession` WRITE;
/*!40000 ALTER TABLE `procession` DISABLE KEYS */;
INSERT INTO `procession` VALUES (1533,0,'This is a description of a procession1',200,200,'FINAL','2020-04-24 00:00:00','200424-TUEBG','El gran poder',1512),(1534,0,'This is a description of a procession2',300,120,'FINAL','2020-04-26 00:00:00','200426-DOUBG','Esperanza de triana',1513),(1535,0,'This is a description of a procession3',300,120,'FINAL','2025-04-26 00:00:00','200427-DOUBG','Esperanza de jesus',1512),(1536,0,'This is a description of a procession4',300,120,'FINAL','2019-04-26 00:00:00','200428-DOUBG','Nazareno',1512),(1537,0,'This is a description of a procession5',300,120,'FINAL','2020-04-26 00:00:00','200429-DOUBG','Señor de la sangre',1512),(1538,0,'This is a description of a procession6',300,120,'FINAL','2021-04-26 00:00:00','200436-DOUBG','Virgen de la Macarena',1512),(1539,0,'This is a description of a procession7',300,120,'FINAL','2021-04-26 00:00:00','200446-DOUBG','El cachorro',1512),(1540,0,'This is a description of a procession8',300,120,'FINAL','2021-04-26 00:00:00','200456-DOUBG','Los gitanos',1512),(1541,0,'This is a description of a procession9s',300,120,'FINAL','2021-04-26 00:00:00','200466-DOUBG','Santa genoveva',1512),(1542,0,'This is a description of a procession10',300,120,'FINAL','2022-04-26 00:00:00','200476-DOUBG','San gonzalo',1512),(1543,0,'This is a description of a procession11',300,120,'FINAL','2022-04-26 00:00:00','200486-DOUBG','La amargura',1512);
/*!40000 ALTER TABLE `procession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `procession_floats`
--

DROP TABLE IF EXISTS `procession_floats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `procession_floats` (
  `procession` int(11) NOT NULL,
  `floats` int(11) NOT NULL,
  KEY `FK_h2i386rvrj0r7gc5bicglm0ab` (`floats`),
  KEY `FK_3kw2qfesi92aqi13ubidr6ffv` (`procession`),
  CONSTRAINT `FK_3kw2qfesi92aqi13ubidr6ffv` FOREIGN KEY (`procession`) REFERENCES `procession` (`id`),
  CONSTRAINT `FK_h2i386rvrj0r7gc5bicglm0ab` FOREIGN KEY (`floats`) REFERENCES `float` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `procession_floats`
--

LOCK TABLES `procession_floats` WRITE;
/*!40000 ALTER TABLE `procession_floats` DISABLE KEYS */;
INSERT INTO `procession_floats` VALUES (1533,1546),(1533,1546),(1534,1548),(1535,1548),(1536,1548),(1537,1548),(1538,1548),(1539,1548),(1540,1548),(1541,1548),(1542,1548),(1543,1548);
/*!40000 ALTER TABLE `procession_floats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `procession_form`
--

DROP TABLE IF EXISTS `procession_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `procession_form` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `max_columns` int(11) DEFAULT NULL,
  `max_rows` int(11) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `procession_form`
--

LOCK TABLES `procession_form` WRITE;
/*!40000 ALTER TABLE `procession_form` DISABLE KEYS */;
/*!40000 ALTER TABLE `procession_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `procession_form_floats`
--

DROP TABLE IF EXISTS `procession_form_floats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `procession_form_floats` (
  `procession_form` int(11) NOT NULL,
  `floats` int(11) NOT NULL,
  KEY `FK_72d5hmr7kpjy2qnn5a61mx6b5` (`floats`),
  KEY `FK_k275pcyvp1veyocxwggprb0fh` (`procession_form`),
  CONSTRAINT `FK_k275pcyvp1veyocxwggprb0fh` FOREIGN KEY (`procession_form`) REFERENCES `procession_form` (`id`),
  CONSTRAINT `FK_72d5hmr7kpjy2qnn5a61mx6b5` FOREIGN KEY (`floats`) REFERENCES `float` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `procession_form_floats`
--

LOCK TABLES `procession_form_floats` WRITE;
/*!40000 ALTER TABLE `procession_form_floats` DISABLE KEYS */;
/*!40000 ALTER TABLE `procession_form_floats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `column_position` int(11) DEFAULT NULL,
  `explanation` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `row_position` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `member` int(11) NOT NULL,
  `procession` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hgv8wexlup4hjaqo4ki13th8v` (`member`),
  KEY `FK_pihoapjtqc0dtknukqggpxmq6` (`procession`),
  CONSTRAINT `FK_pihoapjtqc0dtknukqggpxmq6` FOREIGN KEY (`procession`) REFERENCES `procession` (`id`),
  CONSTRAINT `FK_hgv8wexlup4hjaqo4ki13th8v` FOREIGN KEY (`member`) REFERENCES `member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` VALUES (1544,0,NULL,NULL,'2018-04-16 15:20:00',NULL,'PENDING',1466,1533),(1545,0,2,NULL,'2018-02-16 15:20:00',1,'APPROVED',1467,1534);
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_profile`
--

DROP TABLE IF EXISTS `social_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_profile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `profile_link` varchar(255) DEFAULT NULL,
  `social_network` varchar(255) DEFAULT NULL,
  `actor` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_profile`
--

LOCK TABLES `social_profile` WRITE;
/*!40000 ALTER TABLE `social_profile` DISABLE KEYS */;
INSERT INTO `social_profile` VALUES (1471,0,'nickmember1','http://www.linkmember1.com','socialnetworkmember1',1466),(1472,0,'nickmember2','http://www.limkmember2.com','socialnetworkmember2',1467),(1518,0,'nickbrotherhood1','http://www.linkbrotherhood1.com','socialnetworkbrotherhood1',1512),(1519,0,'nickbrotherhood2','http://www.linkbrotherhood2.com','socialnetworkbrotherhood2',1513);
/*!40000 ALTER TABLE `social_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (1458,0,'e00cf25ad42683b3df678c61f42c6bda','admin1'),(1459,0,'c84258e9c39059a89ab77d846ddab909','admin2'),(1460,0,'c7764cfed23c5ca3bb393308a0da2306','member1'),(1461,0,'88ed421f060aadcacbd63f28d889797f','member2'),(1462,0,'3ef4802d8a37022fd187fbd829d1c4d6','member3'),(1463,0,'479e3180a45b21ea8e88beb0c45aa8ed','brotherhood1'),(1464,0,'88f1b810c40cd63107fb758fef4d77db','brotherhood2'),(1465,0,'1b3231655cebb7a1f783eddf27d254ca','super');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (1458,'ADMIN'),(1459,'ADMIN'),(1460,'MEMBER'),(1461,'MEMBER'),(1462,'MEMBER'),(1462,'BANNED'),(1463,'BROTHERHOOD'),(1464,'BROTHERHOOD'),(1465,'ADMIN'),(1465,'MEMBER'),(1465,'BROTHERHOOD');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-07 23:16:54

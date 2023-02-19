-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: isel_share
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrador` (
  `nomeUtilizador` varchar(45) NOT NULL,
  PRIMARY KEY (`nomeUtilizador`),
  CONSTRAINT `fk_admin_nomeutilizador` FOREIGN KEY (`nomeUtilizador`) REFERENCES `utilizador` (`nomeUtilizador`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `artista`
--

DROP TABLE IF EXISTS `artista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artista` (
  `nomeArtista` varchar(45) NOT NULL,
  `foto` blob NOT NULL,
  PRIMARY KEY (`nomeArtista`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `artista_recurso`
--

DROP TABLE IF EXISTS `artista_recurso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artista_recurso` (
  `nomeArtista` varchar(45) NOT NULL,
  `profissao` varchar(45) NOT NULL,
  `tituloRecurso` varchar(45) NOT NULL,
  `utilizadorRecurso` varchar(45) NOT NULL,
  PRIMARY KEY (`nomeArtista`,`profissao`,`tituloRecurso`,`utilizadorRecurso`),
  KEY `fk_artista_recurso_carregadopor_idx` (`utilizadorRecurso`,`tituloRecurso`),
  CONSTRAINT `fk_artista_recurso` FOREIGN KEY (`utilizadorRecurso`, `tituloRecurso`) REFERENCES `recurso` (`carregadoPor`, `titulo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_artista_recurso_nomeartista` FOREIGN KEY (`nomeArtista`) REFERENCES `artista` (`nomeArtista`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comentario`
--

DROP TABLE IF EXISTS `comentario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentario` (
  `utilizadorComentario` varchar(45) NOT NULL,
  `tituloRecurso` varchar(45) NOT NULL,
  `utilizadorRecurso` varchar(45) NOT NULL,
  `classificacao` enum('1','2','3','4','5') NOT NULL,
  `conteudo` varchar(600) DEFAULT NULL,
  `dataComentario` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`utilizadorComentario`,`tituloRecurso`,`utilizadorRecurso`),
  KEY `fk_comentario_carregadopor_idx` (`utilizadorRecurso`,`tituloRecurso`),
  CONSTRAINT `fk_comentario_nomeutilizador` FOREIGN KEY (`utilizadorComentario`) REFERENCES `utilizador` (`nomeUtilizador`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_comentario_recurso` FOREIGN KEY (`utilizadorRecurso`, `tituloRecurso`) REFERENCES `recurso` (`carregadoPor`, `titulo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `convidado`
--

DROP TABLE IF EXISTS `convidado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `convidado` (
  `nomeUtilizador` varchar(45) NOT NULL,
  `bloqueado` tinyint DEFAULT '0',
  PRIMARY KEY (`nomeUtilizador`),
  CONSTRAINT `fk_convidado_nomeutilizador` FOREIGN KEY (`nomeUtilizador`) REFERENCES `utilizador` (`nomeUtilizador`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `criador`
--

DROP TABLE IF EXISTS `criador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `criador` (
  `nomeUtilizador` varchar(45) NOT NULL,
  `palavraPasse` varchar(64) NOT NULL,
  `bloqueado` tinyint DEFAULT '0',
  `pontosReputacao` decimal(10,1) DEFAULT '0.0',
  PRIMARY KEY (`nomeUtilizador`),
  CONSTRAINT `fk_criador_nomeutilizador` FOREIGN KEY (`nomeUtilizador`) REFERENCES `utilizador` (`nomeUtilizador`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `filme`
--

DROP TABLE IF EXISTS `filme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filme` (
  `titulo` varchar(45) NOT NULL,
  `carregadoPor` varchar(45) NOT NULL,
  `anoLancamento` date NOT NULL,
  PRIMARY KEY (`titulo`,`carregadoPor`),
  CONSTRAINT `fk_filme_recurso` FOREIGN KEY (`titulo`, `carregadoPor`) REFERENCES `recurso` (`titulo`, `carregadoPor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fotografia`
--

DROP TABLE IF EXISTS `fotografia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fotografia` (
  `titulo` varchar(45) NOT NULL,
  `carregadoPor` varchar(45) NOT NULL,
  PRIMARY KEY (`titulo`,`carregadoPor`),
  CONSTRAINT `fk_fotografia_recurso` FOREIGN KEY (`titulo`, `carregadoPor`) REFERENCES `recurso` (`titulo`, `carregadoPor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `grupo_recurso`
--

DROP TABLE IF EXISTS `grupo_recurso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupo_recurso` (
  `idGrupo` int NOT NULL,
  `tituloRecurso` varchar(45) NOT NULL,
  `carregadoPor` varchar(45) NOT NULL,
  PRIMARY KEY (`idGrupo`,`tituloRecurso`,`carregadoPor`),
  KEY `fk_grupo_recurso_idx` (`tituloRecurso`,`carregadoPor`),
  CONSTRAINT `fk_grupo_recurso` FOREIGN KEY (`tituloRecurso`, `carregadoPor`) REFERENCES `recurso` (`titulo`, `carregadoPor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `musica`
--

DROP TABLE IF EXISTS `musica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `musica` (
  `titulo` varchar(45) NOT NULL,
  `carregadoPor` varchar(45) NOT NULL,
  PRIMARY KEY (`titulo`,`carregadoPor`),
  CONSTRAINT `fk_musica_recurso` FOREIGN KEY (`titulo`, `carregadoPor`) REFERENCES `recurso` (`titulo`, `carregadoPor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nacionalidade`
--

DROP TABLE IF EXISTS `nacionalidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nacionalidade` (
  `codigo` int NOT NULL,
  `nacionalidade` varchar(45) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `poema`
--

DROP TABLE IF EXISTS `poema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poema` (
  `titulo` varchar(45) NOT NULL,
  `carregadoPor` varchar(45) NOT NULL,
  PRIMARY KEY (`titulo`,`carregadoPor`),
  CONSTRAINT `fk_poema_recurso` FOREIGN KEY (`titulo`, `carregadoPor`) REFERENCES `recurso` (`titulo`, `carregadoPor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recurso`
--

DROP TABLE IF EXISTS `recurso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recurso` (
  `titulo` varchar(45) NOT NULL,
  `carregadoPor` varchar(45) NOT NULL,
  `resumo` varchar(200) DEFAULT 'Sem descrição',
  `ficheiro` longblob NOT NULL,
  `ilustracao` mediumblob NOT NULL,
  `faixaEtaria` int DEFAULT '6',
  `bloqueado` tinyint DEFAULT '1',
  `dataPublicacao` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`titulo`,`carregadoPor`),
  KEY `fk_recurso_nomeutilizador_idx` (`carregadoPor`),
  CONSTRAINT `fk_recurso_nomeutilizador` FOREIGN KEY (`carregadoPor`) REFERENCES `utilizador` (`nomeUtilizador`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_recurso`
--

DROP TABLE IF EXISTS `tipo_recurso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_recurso` (
  `titulo` varchar(45) NOT NULL,
  `carregadoPor` varchar(45) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  PRIMARY KEY (`titulo`,`carregadoPor`),
  CONSTRAINT `fk_tipo_recurso` FOREIGN KEY (`titulo`, `carregadoPor`) REFERENCES `recurso` (`titulo`, `carregadoPor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_utilizador`
--

DROP TABLE IF EXISTS `tipo_utilizador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_utilizador` (
  `nomeUtilizador` varchar(45) NOT NULL,
  `tipo` int NOT NULL,
  PRIMARY KEY (`nomeUtilizador`),
  CONSTRAINT `fk_tipo_utilizador_nomeUtilizador` FOREIGN KEY (`nomeUtilizador`) REFERENCES `utilizador` (`nomeUtilizador`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utilizador`
--

DROP TABLE IF EXISTS `utilizador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilizador` (
  `nomeUtilizador` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `dataNascimento` date NOT NULL,
  `nacionalidade` int NOT NULL,
  PRIMARY KEY (`nomeUtilizador`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_nacionalidade_idx` (`nacionalidade`),
  CONSTRAINT `fk_nacionalidade` FOREIGN KEY (`nacionalidade`) REFERENCES `nacionalidade` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-01  0:11:13

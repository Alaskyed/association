-- MySQL dump 10.13  Distrib 8.0.18, for linux-glibc2.12 (x86_64)
--
-- Host: localhost    Database: association
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `association_info`
--

DROP TABLE IF EXISTS `association_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `association_info` (
  `ass_uuid` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '社团uuid',
  `university_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '学校名称',
  `association_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '社团名称',
  `department_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '部门名称',
  PRIMARY KEY (`ass_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `association_info`
--

LOCK TABLES `association_info` WRITE;
/*!40000 ALTER TABLE `association_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `association_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major_info`
--

DROP TABLE IF EXISTS `major_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `major_info` (
  `major_uuid` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '专业uuid,主键',
  `university_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '学校名称',
  `academy_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '学院名称',
  `major_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '专业名称',
  PRIMARY KEY (`major_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major_info`
--

LOCK TABLES `major_info` WRITE;
/*!40000 ALTER TABLE `major_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `major_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stu_ass`
--

DROP TABLE IF EXISTS `stu_ass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stu_ass` (
  `stu_uuid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '对应学生信息表的uuid',
  `ass_uuid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '对应社团信息表的uuid',
  `position` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '职位',
  `status` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu_ass`
--

LOCK TABLES `stu_ass` WRITE;
/*!40000 ALTER TABLE `stu_ass` DISABLE KEYS */;
/*!40000 ALTER TABLE `stu_ass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_info`
--

DROP TABLE IF EXISTS `student_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_info` (
  `stu_uuid` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '学生信息表uuid',
  `stu_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '学生姓名',
  `stu_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '学号',
  `stu_grade` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '年级',
  `pro_uuid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '对应专业信息表中的uuid',
  PRIMARY KEY (`stu_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_info`
--

LOCK TABLES `student_info` WRITE;
/*!40000 ALTER TABLE `student_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `university`
--

DROP TABLE IF EXISTS `university`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `university` (
  `id` int(11) DEFAULT NULL COMMENT '序号',
  `university_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学校名称',
  `id_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '学校标识码',
  `competent_department` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主管部门',
  `local` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所在地',
  `level` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '办学层次',
  `remark` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学校表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `university`
--

LOCK TABLES `university` WRITE;
/*!40000 ALTER TABLE `university` DISABLE KEYS */;
/*!40000 ALTER TABLE `university` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `phone_number` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '手机号,主键',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `stu_uuid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '对应学生信息表的uuid',
  PRIMARY KEY (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('test','e10adc3949ba59abbe56e057f20f883e','11122223333','111@qq.com',NULL),('test2','96e79218965eb72c92a549dd5a330112','22233334444','',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'association'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-06 19:48:26

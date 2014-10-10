-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 10, 2014 at 12:45 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `corpseslasher`
--
CREATE DATABASE `corpseslasher` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `corpseslasher`;

-- --------------------------------------------------------

--
-- Table structure for table `oauth_user`
--

CREATE TABLE IF NOT EXISTS `oauth_user` (
  `tableId` int(10) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `zombieKills` int(11) NOT NULL,
  `experiencePoints` int(11) NOT NULL,
  `id` text NOT NULL,
  PRIMARY KEY (`tableId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `oauth_user`
--

INSERT INTO `oauth_user` (`tableId`, `name`, `zombieKills`, `experiencePoints`, `id`) VALUES
(1, 'Martin Schoeman', 0, 0, '100000068202419'),
(3, 'Martin Schoeman', 0, 0, '111790131659551738379');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` text NOT NULL,
  `password` varbinary(200) NOT NULL,
  `name` text NOT NULL,
  `surname` text NOT NULL,
  `email` text NOT NULL,
  `zombieKills` int(11) NOT NULL,
  `experiencePoints` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `name`, `surname`, `email`, `zombieKills`, `experiencePoints`) VALUES
(13, 'u10651994', 'ùÆ°—©¶Ê^∑∞aj8ôuóª)˚¡a¸Çºæ,…Úz”éÏ 6*Q‹/¿r\0‡…`', 'Martin', 'Schoeman', 'firstnamemartins@yahoo.com', 12, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

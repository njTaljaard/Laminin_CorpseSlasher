-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 02, 2014 at 05:20 PM
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
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `screenName` text NOT NULL,
  `name` text NOT NULL,
  `surname` text NOT NULL,
  `dateOfBirth` date NOT NULL,
  `gender` tinyint(1) NOT NULL,
  `email` text NOT NULL,
  `zombieKills` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `screenName`, `name`, `surname`, `dateOfBirth`, `gender`, `email`, `zombieKills`) VALUES
(2, '10651994', '1232', 'MartinS', 'Martin', 'Schoeman', '1990-06-19', 1, 'm@s.com', 0),
(3, 'usern', 'pass', 'sn', 'n', 's', '2000-05-20', 1, 'm2g.com', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

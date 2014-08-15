-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 15, 2014 at 01:14 PM
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `zombieKills` int(11) NOT NULL,
  `experiencePoints` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `oauth_user`
--

INSERT INTO `oauth_user` (`id`, `name`, `zombieKills`, `experiencePoints`) VALUES
(1, 'facebook user1', 150, 1500),
(4, 'google user', 23, 230);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `name`, `surname`, `email`, `zombieKills`, `experiencePoints`) VALUES
(6, 'u10651994', 'g&ﬁq)aZY\0ôø˜Å¯', 'Martin', 'Schoeman', 'firstnamemartins@yahoo.com', 0, 0),
(7, 'test1', 'g&ﬁq)aZY\0ôø˜Å¯', 'one', 'one', 'm@s.com', 12, 120),
(8, 'two', 'g&ﬁq)aZY\0ôø˜Å¯', 'two', 'two', 'test@non.valid', 111, 1110);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

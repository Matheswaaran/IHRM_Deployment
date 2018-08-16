-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Jul 22, 2018 at 12:28 PM
-- Server version: 5.6.38
-- PHP Version: 7.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `HRM_Database`
--

-- --------------------------------------------------------

--
-- Table structure for table `allocated_employee_table`
--

CREATE TABLE `allocated_employee_table` (
  `aid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `rid` int(11) NOT NULL,
  `eid` int(11) NOT NULL,
  `date` int(15) NOT NULL,
  `atten` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `contract_users`
--

CREATE TABLE `contract_users` (
  `cid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `sid` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `aadhar_uid` bigint(20) NOT NULL,
  `aadhar_string` varchar(1000) NOT NULL,
  `uid` int(11) NOT NULL,
  `GCMtoken` varchar(250) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `employee_attendance_table`
--

CREATE TABLE `employee_attendance_table` (
  `atid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `eid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `date` int(15) NOT NULL,
  `enter_time` varchar(20) NOT NULL,
  `exit_time` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `employee_table`
--

CREATE TABLE `employee_table` (
  `eid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `cid` int(11) NOT NULL,
  `auth` int(2) NOT NULL DEFAULT '0',
  `aadhar_uid` bigint(20) NOT NULL,
  `aadhar_string` varchar(1000) NOT NULL,
  `skill` int(3) NOT NULL,
  `emp_type` int(3) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `goverment_users`
--

CREATE TABLE `goverment_users` (
  `gid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `aadhar_uid` bigint(20) NOT NULL,
  `aadhar_string` varchar(1000) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `goverment_users`
--

INSERT INTO `goverment_users` (`gid`, `name`, `email`, `password`, `aadhar_uid`, `aadhar_string`, `created`) VALUES
(1, 'Matheswaaran', 'mat@mat.com', '1234', 123, 'laskdjflaksjl', '2018-07-19 18:26:15');

-- --------------------------------------------------------

--
-- Table structure for table `package_contractors`
--

CREATE TABLE `package_contractors` (
  `pkg_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `sid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `aadhar_uid` bigint(20) NOT NULL,
  `aadhar_string` varchar(1000) NOT NULL,
  `uid` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `site_table`
--

CREATE TABLE `site_table` (
  `sid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `address` varchar(20) NOT NULL,
  `district` varchar(1000) NOT NULL,
  `state` varchar(20) NOT NULL,
  `pincode` varchar(10) NOT NULL,
  `type` varchar(50) NOT NULL,
  `gid` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `supervisor_users`
--

CREATE TABLE `supervisor_users` (
  `su_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `sid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `aadhar_uid` bigint(20) NOT NULL,
  `aadhar_string` varchar(1000) NOT NULL,
  `gid` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `super_req_table`
--

CREATE TABLE `super_req_table` (
  `rid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `task_name` varchar(100) NOT NULL,
  `su_id` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  `date` varchar(30) NOT NULL,
  `skill_1` int(5) NOT NULL,
  `skill_2` int(5) NOT NULL,
  `skill_3` int(5) NOT NULL,
  `skill_4` int(5) NOT NULL,
  `alloc_skill_1` int(5) NOT NULL DEFAULT '0',
  `alloc_skill_2` int(5) NOT NULL DEFAULT '0',
  `alloc_skill_3` int(5) NOT NULL DEFAULT '0',
  `alloc_skill_4` int(5) NOT NULL DEFAULT '0',
  `alloc_time` varchar(20) DEFAULT NULL,
  `req_date` varchar(15) NOT NULL,
  `c_response` int(2) NOT NULL DEFAULT '0',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users_table`
--

CREATE TABLE `users_table` (
  `uid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email_id` varchar(100) NOT NULL,
  `aadhar_uid` int(20) NOT NULL DEFAULT '0',
  `aadhar_string` varchar(3000) NOT NULL DEFAULT '0',
  `address_1` varchar(100) NOT NULL,
  `address_2` varchar(100) NOT NULL,
  `district` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `pincode` varchar(10) NOT NULL,
  `contact_no` varchar(15) NOT NULL,
  `password` varchar(1000) NOT NULL,
  `gid` int(11) NOT NULL,
  `blocked` int(11) NOT NULL DEFAULT '0',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

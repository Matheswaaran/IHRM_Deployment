SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Creating a database
--

CREATE DATABASE IF NOT EXISTS `HRM_Database`;

-- --------------------------------------------------------


--
-- Table structure for table `goverment_users`
--

CREATE TABLE `HRM_Database`.`goverment_users` (
  `gid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `aadhar_uid` int(20) NOT NULL,
  `aadhar_string` varchar(1000) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------


-- --------------------------------------------------------

--
-- Table structure for table `contract_users`
--

CREATE TABLE `HRM_Database`.`contract_users` (
  `cid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `sid` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `aadhar_uid` int(20) NOT NULL,
  `aadhar_string` varchar(1000) NOT NULL,
  `gid` int(11) NOT NULL,
  `GCMtoken` varchar(250),
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- --------------------------------------------------------

--
-- Table structure for table `supervisour_users`
--

CREATE TABLE `HRM_Database`.`supervisor_users` (
  `su_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `sid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `aadhar_uid` int(20) NOT NULL,
  `aadhar_string` varchar(1000) NOT NULL,
  `gid` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------


-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `HRM_Database`.`employee_table` (
  `eid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `cid` int(11) NOT NULL, -- only contractors
  `auth` int(2) NOT NULL DEFAULT 0, -- 0 - not authenticated 1 - authenticated
  `aadhar_uid` int(20) NOT NULL,
  `aadhar_string` varchar(1000) NOT NULL,
  `skill` int(3) NOT NULL, -- 1-skilled 2-semi-skilled 3-unskilled 4-helper
  `emp_type` int(3) NOT NULL, -- 1-permanent 2-contract 3-casual
  `assigned` int(2) NOT NULL, -- 0-not assigned 1 - assigned
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `site_table`
--

CREATE TABLE `HRM_Database`.`site_table` (
  `sid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `address` varchar(20) NOT NULL,
  `district` varchar(1000) NOT NULL,
  `state` varchar(20) NOT NULL,
  `type` int(3) NOT NULL,  -- 0-mines 1-industry
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- --------------------------------------------------------


--
-- Table structure for table `superviser_requirements`
--

CREATE TABLE `HRM_Database`.`super_req_table` (
  `rid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `task_name` varchar(100) NOT NULL,
  `su_id` int(11) NOT NULL,
  `sid` int(11) NOT NULL, -- site id
  `cid` int(11) NOT NULL, -- contracor from whome the supervisor gets his men
  `date` varchar(30) NOT NULL,
  `skill_1` int(5) NOT NULL, -- contractor requirement
  `skill_2` int(5) NOT NULL,
  `skill_3` int(5) NOT NULL,
  `skill_4` int(5) NOT NULL,
  `alloc_skill_1` int(5) NOT NULL DEFAULT 0, -- allocated no
  `alloc_skill_2` int(5) NOT NULL DEFAULT 0,
  `alloc_skill_3` int(5) NOT NULL DEFAULT 0,
  `alloc_skill_4` int(5) NOT NULL DEFAULT 0,
  `alloc_time` varchar(20) NULL DEFAULT NULL,
  `req_date` varchar(15) NOT NULL, -- requirements date
  `c_response` int(2) NOT NULL DEFAULT 0, -- 0-no response 1-success 2-partial
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- --------------------------------------------------------

--
-- Table structure for table `attendance table`
--
CREATE TABLE `HRM_Database`.`employee_attendance_table`(
  `atid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `eid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `date` varchar(15) NOT NULL,
  `enter_time` varchar(20) NOT NULL, -- enter time
  `exit_time` varchar(20) NOT NULL -- exit time
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `allocated employees for a request`
--

CREATE TABLE `HRM_Database`.`allocated_employee_table`(
  `aid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `rid` int(11) NOT NULL,
  `eid` int(11) NOT NULL,
  `date` int(15) NOT NULL,
  `atten` int(2) NOT NULL -- attendance fields
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

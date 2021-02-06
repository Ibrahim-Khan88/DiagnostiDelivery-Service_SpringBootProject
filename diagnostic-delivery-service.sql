-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 06, 2021 at 03:36 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `diagnostic-delivery-service`
--

-- --------------------------------------------------------

--
-- Table structure for table `accessories`
--

CREATE TABLE `accessories` (
  `id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accessories`
--

INSERT INTO `accessories` (`id`, `amount`, `description`, `name`) VALUES
(1, 300, 'Tube', 'Tube'),
(2, 300, 'Paper', 'Paper'),
(3, 300, 'Paper', 'Accessories 4'),
(4, 300, 'Paper', 'Accessories 5'),
(5, 300, 'Paper', 'Accessories 3'),
(6, 300, 'Paper', 'Accessories 6');

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`id`, `name`, `type`) VALUES
(1, 'BIOCHEMISTRY', 'List'),
(2, 'X-RAY', 'Individual'),
(3, 'MOLECULAR', 'Individual'),
(4, 'Dept', 'List'),
(5, 'ParameterTypeDepartment', 'Parameter'),
(6, 'ParameterTypeDepartment 2', 'Parameter');

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE `doctor` (
  `id` int(11) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `provider_id` int(11) NOT NULL,
  `selection_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`id`, `degree`, `description`, `name`, `provider_id`, `selection_code`) VALUES
(1, 'MBBS, FCPS', 'SENIOR CONSULTANT', 'S K Ballav', 1, '009'),
(2, 'MBBS, FCPS', 'SENIOR CONSULTANT', 'Satunu kumar mondal', 1, '003'),
(3, 'MBBS, FCPS', 'SENIOR CONSULTANT', 'Aman Biswas', 1, '001');

-- --------------------------------------------------------

--
-- Table structure for table `drop_down_input`
--

CREATE TABLE `drop_down_input` (
  `id` int(11) NOT NULL,
  `value` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `drop_down_input`
--

INSERT INTO `drop_down_input` (`id`, `value`) VALUES
(1, 'Staw'),
(2, 'red'),
(3, 'green'),
(4, 'positive'),
(5, 'Nagative'),
(6, 'Drop Down Input 6'),
(7, 'Drop Down Input 7'),
(8, 'Drop Down Input 8'),
(9, 'Drop Down Input 9');

-- --------------------------------------------------------

--
-- Table structure for table `drop_down_input_parameters`
--

CREATE TABLE `drop_down_input_parameters` (
  `drop_down_input_id` int(11) NOT NULL,
  `parameters_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `drop_down_input_parameters`
--

INSERT INTO `drop_down_input_parameters` (`drop_down_input_id`, `parameters_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 4),
(5, 4),
(6, 9),
(7, 9),
(8, 9),
(9, 9);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(537),
(537),
(537),
(537),
(537),
(537),
(537);

-- --------------------------------------------------------

--
-- Table structure for table `investigation`
--

CREATE TABLE `investigation` (
  `id` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount_amount` int(11) NOT NULL,
  `discount_per` int(11) NOT NULL,
  `format` text DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `rate` int(11) NOT NULL,
  `ref_value` varchar(255) DEFAULT NULL,
  `result_type` varchar(255) DEFAULT NULL,
  `selection_code` varchar(255) DEFAULT NULL,
  `sorting_id` int(11) NOT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `vat_apply` bit(1) NOT NULL,
  `vat_apply_amount` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `specimen_id` int(11) NOT NULL,
  `sub_group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `investigation`
--

INSERT INTO `investigation` (`id`, `created_date`, `update_date`, `code`, `description`, `discount_amount`, `discount_per`, `format`, `name`, `note`, `rate`, `ref_value`, `result_type`, `selection_code`, `sorting_id`, `unit`, `vat_apply`, `vat_apply_amount`, `department_id`, `provider_id`, `specimen_id`, `sub_group_id`) VALUES
(1, '2021-01-13 15:27:54', '2021-01-13 15:27:54', NULL, 'description', 100, 5, 'format', 'CBC', 'note', 200, '7-11', NULL, '001', 3, 'ml/gg', b'0', 0, 1, 1, 1, 1),
(3, '2021-01-14 15:06:05', '2021-01-14 15:06:05', NULL, 'description', 100, 0, 'format', 'RBC', 'note', 200, '7-11', NULL, '002', 2, 'ml/gg', b'1', 150, 1, 1, 1, 1),
(4, '2021-01-13 15:27:54', '2021-01-15 14:06:32', NULL, 'description', 100, 10, '', 'Creamming', 'note', 200, '7-11', 'Drop Down', '003', 4, 'ml/gg', b'0', 0, 1, 1, 1, 1),
(5, '2021-01-13 15:27:54', '2021-01-14 15:14:02', NULL, 'description', 100, 0, '<div style=\" font-size: 10px;\"> <b >FINDINGS :</b> <br>         No fracture and dislocation is noted <br>        No bony lession is seen <br>        Bone density is within limit <br>  This is for hips<br>  <br>        <b>COMMENT :</b> <br>      No significant abnormality is detected       </div>', 'Hips', 'note', 200, '7-11', NULL, '004', 2, NULL, b'0', 0, 2, 1, 1, 1),
(6, '2021-01-26 06:01:59', '2021-01-26 06:02:42', NULL, 'description description', 50, 50, '<h1><strong>format</strong></h1>', 'Investigation', 'note', 260, '7-11', 'DropDown', '005', 6, NULL, b'1', 0, 5, 1, 3, 3),
(7, '2021-01-13 15:27:54', '2021-01-14 15:14:02', NULL, 'description', 100, 0, 'format', 'investigation 2', 'note', 200, '7-11', NULL, '006', 7, NULL, b'0', 0, 5, 1, 1, 1),
(8, '2021-01-13 15:27:54', '2021-01-14 15:14:02', NULL, 'description', 100, 0, '<div style=\" font-size: 10px;\"> <b >FINDINGS :</b> <br>         No fracture and dislocation is noted <br>        No bony lession is seen <br>        Bone density is within limit <br>  <br>        <b>COMMENT :</b> <br>      No significant abnormality is detected       </div>', 'HLA', 'note', 200, '7-11', NULL, '004', 1, NULL, b'0', 0, 2, 1, 1, 1),
(45, '2021-01-26 16:57:24', '2021-01-26 16:57:24', '2345', 'Description', 100, 10, '<h1><strong>this is positive</strong></h1>', 'Test Investigation', 'Short Note', 500, '4 -10', 'DropDown', '0009', 3, 'ml/g', b'1', 100, 5, 1, 3, 3),
(46, '2021-01-26 09:33:43', '2021-01-26 09:33:43', NULL, NULL, 150, 10, NULL, 'Investigation Name 2', NULL, 500, NULL, 'DropDown', NULL, 4, NULL, b'0', 100, 1, 1, 3, 3),
(47, '2021-01-26 17:27:11', '2021-01-26 17:27:11', '34567', 'Description', 111, 11, '<h1><strong>This is posaitive</strong></h1>', 'Test Investigation 2', NULL, 500, '7 - 10', 'DropDown', '00088', 6, 'ml/g', b'1', 100, 5, 1, 3, 3),
(48, '2021-01-30 09:05:42', '2021-01-30 09:05:42', '2345', 'Description', 100, 10, '<p>No format</p>', 'Parameter investigation 1', 'Description\nShort Note', 600, NULL, 'DropDown', '00099', 3, NULL, b'1', 150, 5, 1, 3, 2),
(49, '2021-01-30 09:07:49', '2021-01-30 09:10:12', '123456', NULL, 200, 20, NULL, 'Parameter investigation 2', NULL, 800, NULL, 'Input', '0888', 4, NULL, b'1', 0, 6, 1, 2, 3),
(63, '2021-02-06 13:11:11', '2021-02-06 13:11:11', '1000', 'Description', 10, 10, NULL, 'Parameter investigation 3', 'Short Note', 3000, NULL, 'DropDown', '0077', 4, NULL, b'1', 10, 5, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `investigation_accessories`
--

CREATE TABLE `investigation_accessories` (
  `investigation_id` int(11) NOT NULL,
  `accessories_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `investigation_accessories`
--

INSERT INTO `investigation_accessories` (`investigation_id`, `accessories_id`) VALUES
(63, 2),
(63, 3);

-- --------------------------------------------------------

--
-- Table structure for table `investigation_parameters`
--

CREATE TABLE `investigation_parameters` (
  `investigation_id` int(11) NOT NULL,
  `parameters_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `investigation_parameters`
--

INSERT INTO `investigation_parameters` (`investigation_id`, `parameters_id`) VALUES
(47, 1),
(47, 2),
(47, 4),
(47, 5),
(47, 6),
(45, 1),
(45, 2),
(45, 3),
(45, 4),
(45, 5),
(45, 6),
(45, 7),
(45, 8),
(45, 9),
(63, 1),
(63, 2),
(63, 9),
(63, 10),
(63, 11),
(63, 8),
(63, 7);

-- --------------------------------------------------------

--
-- Table structure for table `investigation_param_group`
--

CREATE TABLE `investigation_param_group` (
  `investigation_id` int(11) NOT NULL,
  `param_group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `investigation_param_group`
--

INSERT INTO `investigation_param_group` (`investigation_id`, `param_group_id`) VALUES
(47, 1),
(47, 2),
(47, 3),
(45, 1),
(45, 2),
(45, 3),
(45, 4),
(63, 1),
(63, 2),
(63, 4);

-- --------------------------------------------------------

--
-- Table structure for table `investigation_request`
--

CREATE TABLE `investigation_request` (
  `id` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `image_file_name` varchar(255) DEFAULT NULL,
  `download_status` varchar(255) DEFAULT NULL,
  `external_id` int(11) NOT NULL,
  `reference_id` int(11) NOT NULL,
  `report_image` tinyblob DEFAULT NULL,
  `report_text` text DEFAULT NULL,
  `report_url` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `investigation_id` int(11) NOT NULL,
  `lab_doctor_id` int(11) DEFAULT NULL,
  `patient_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `investigation_request`
--

INSERT INTO `investigation_request` (`id`, `created_date`, `update_date`, `image_file_name`, `download_status`, `external_id`, `reference_id`, `report_image`, `report_text`, `report_url`, `status`, `investigation_id`, `lab_doctor_id`, `patient_id`, `provider_id`) VALUES
(1, NULL, '2021-02-02 19:25:43', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 6, 1, 32, 1),
(2, NULL, '2021-02-02 19:27:16', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 7, 3, 32, 1),
(3, NULL, '2021-02-02 19:20:14', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 5, 3, 32, 1),
(4, NULL, '2021-02-02 19:17:29', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 4, 1, 32, 1),
(5, NULL, '2021-02-02 19:17:29', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 3, 1, 32, 1),
(6, NULL, '2021-02-02 19:17:29', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 1, 1, 32, 1),
(7, NULL, '2021-01-25 17:11:59', NULL, NULL, 0, 0, NULL, '<div style=\" font-size: 10px;\"> <b >FINDINGS :</b> <br>         No fracture and dislocation is noted <br>        No bony lession is seen <br>        Bone density is within limit <br>  <br>        <b>COMMENT :</b> <br>      No significant abnormality is detected       </div>', NULL, NULL, 8, 3, 32, 1),
(22, '2021-01-26 17:05:48', '2021-01-26 17:08:24', NULL, NULL, 0, 0, NULL, '8', NULL, NULL, 3, 2, 56, 1),
(23, '2021-01-26 17:05:48', '2021-01-26 17:08:24', NULL, NULL, 0, 0, NULL, '8', NULL, NULL, 1, 2, 56, 1),
(24, '2021-01-26 17:05:48', '2021-01-26 17:09:56', NULL, NULL, 0, 0, NULL, 'set', NULL, NULL, 45, 1, 56, 1),
(25, '2021-01-26 17:15:28', '2021-01-26 17:15:48', NULL, NULL, 0, 0, NULL, '5', NULL, NULL, 1, 1, 57, 1),
(26, '2021-01-26 17:15:28', '2021-01-26 17:15:28', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 6, NULL, 57, 1),
(27, '2021-01-26 17:15:28', '2021-01-26 17:16:34', NULL, NULL, 0, 0, NULL, 'set', NULL, NULL, 45, 2, 57, 1),
(28, '2021-01-26 17:31:05', '2021-01-26 17:32:04', NULL, NULL, 0, 0, NULL, '5', NULL, NULL, 1, 1, 58, 1),
(29, '2021-01-26 17:31:05', '2021-01-30 07:12:01', NULL, NULL, 0, 0, NULL, 'set', NULL, NULL, 47, 1, 58, 1),
(30, '2021-01-27 14:36:08', '2021-01-27 14:36:08', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 1, NULL, 59, 1),
(31, '2021-01-27 15:13:10', '2021-01-27 15:13:10', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 1, NULL, 60, 1),
(32, '2021-01-27 15:13:10', '2021-01-27 15:13:10', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 4, NULL, 60, 1),
(33, '2021-01-27 15:13:10', '2021-01-27 15:13:10', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 3, NULL, 60, 1),
(34, '2021-01-27 15:15:55', '2021-01-27 15:15:55', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 1, NULL, 61, 1),
(35, '2021-01-27 15:15:55', '2021-01-27 15:15:55', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 3, NULL, 61, 1),
(36, '2021-01-27 15:15:55', '2021-01-27 15:15:55', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 47, NULL, 61, 1),
(37, '2021-01-27 16:06:19', '2021-01-27 16:06:19', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 1, NULL, 62, 1),
(38, '2021-01-27 16:06:20', '2021-01-27 16:06:20', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 47, NULL, 62, 1),
(39, '2021-01-26 17:31:05', '2021-01-30 07:09:12', NULL, NULL, 0, 0, NULL, 'set', NULL, NULL, 45, 3, 58, 1),
(123, '2021-01-30 09:50:02', '2021-01-30 09:59:35', NULL, NULL, 0, 0, NULL, '7', NULL, NULL, 1, 1, 67, 1),
(124, '2021-01-30 09:50:02', '2021-01-30 09:59:35', NULL, NULL, 0, 0, NULL, '9', NULL, NULL, 3, 1, 67, 1),
(125, '2021-01-30 09:50:02', '2021-01-30 10:02:28', NULL, NULL, 0, 0, NULL, 'set', NULL, NULL, 45, 3, 67, 1),
(126, '2021-01-30 09:50:02', '2021-01-30 10:00:53', NULL, NULL, 0, 0, NULL, 'set', NULL, NULL, 49, 3, 67, 1),
(127, '2021-01-30 09:50:02', '2021-01-30 10:02:06', NULL, NULL, 0, 0, NULL, 'set', NULL, NULL, 48, 1, 67, 1),
(128, '2021-01-30 09:50:02', '2021-01-30 10:06:26', NULL, NULL, 0, 0, NULL, '<p><strong>FINDINGS :</strong> </p><p><strong><em><u>Two found </u></em></strong><em>and dislocation is noted </em></p><p><em> No bony lession is seen </em></p><p><em> Bone density is within limit </em></p><p><em> This is for hips</em></p><p> </p><p> <strong>COMMENT :</strong> </p><p> No significant abnormality is detected</p>', NULL, NULL, 5, 2, 67, 1),
(129, '2021-01-30 09:50:02', '2021-01-30 10:07:11', NULL, NULL, 0, 0, NULL, '<p><strong>FINDINGS :</strong> </p><p> No fracture and dislocation is noted </p><p> No bony lession is seen </p><p> Bone density is within limit </p><p> </p><h1> <strong>COMMENT :</strong> </h1><h1> No significant abnormality is detected</h1>', NULL, NULL, 8, 3, 67, 1),
(130, '2021-02-03 17:57:12', '2021-02-03 17:57:12', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 45, NULL, 68, 1),
(131, '2021-02-03 17:57:12', '2021-02-03 17:57:12', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 3, NULL, 68, 1),
(132, '2021-02-03 17:57:12', '2021-02-03 17:57:12', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 1, NULL, 68, 1),
(133, '2021-01-26 17:31:05', '2021-01-26 17:32:04', NULL, NULL, 0, 0, NULL, '5', NULL, NULL, 3, 1, 58, 1),
(134, '2021-01-26 17:31:05', '2021-01-26 17:32:04', NULL, NULL, 0, 0, NULL, '5', NULL, NULL, 4, 1, 58, 1),
(135, '2021-01-26 17:31:05', '2021-01-26 17:32:04', NULL, NULL, 0, 0, NULL, '5', NULL, NULL, 5, 1, 58, 1),
(136, '2021-01-26 17:31:05', '2021-01-26 17:32:04', NULL, NULL, 0, 0, NULL, '5', NULL, NULL, 8, 1, 58, 1),
(137, '2021-02-06 13:19:48', '2021-02-06 13:19:48', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 45, NULL, 69, 1),
(138, '2021-02-06 13:19:48', '2021-02-06 14:12:01', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 63, 3, 69, 1),
(139, '2021-02-06 13:19:48', '2021-02-06 14:09:19', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 3, 1, 69, 1),
(140, '2021-02-06 13:19:48', '2021-02-06 14:09:19', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 1, 1, 69, 1),
(141, '2021-02-06 13:19:48', '2021-02-06 14:07:24', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 5, 3, 69, 1),
(142, '2021-02-06 13:19:48', '2021-02-06 14:13:12', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 8, 3, 69, 1);

-- --------------------------------------------------------

--
-- Table structure for table `investivation_parameter_result`
--

CREATE TABLE `investivation_parameter_result` (
  `id` int(11) NOT NULL,
  `result` varchar(255) DEFAULT NULL,
  `investigation_request_id` int(11) NOT NULL,
  `parameter_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `investivation_parameter_result`
--

INSERT INTO `investivation_parameter_result` (`id`, `result`, `investigation_request_id`, `parameter_id`) VALUES
(31, 'parameter-input-2-1', 39, 6),
(32, 'Staw', 39, 1),
(33, '666', 39, 3),
(34, 'red', 39, 4),
(35, '222', 39, 2),
(36, 'green', 39, 9),
(37, '5555', 39, 5),
(40, 'positive', 29, 4);

-- --------------------------------------------------------

--
-- Table structure for table `lab_doctor`
--

CREATE TABLE `lab_doctor` (
  `id` int(11) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `provider_id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `selection_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lab_doctor`
--

INSERT INTO `lab_doctor` (`id`, `degree`, `title`, `provider_id`, `description`, `selection_code`) VALUES
(1, 'MBBS, FCPS', 'S K Ballav', 1, 'SENIOR CONSULTANT', '006'),
(2, 'MBBS, FCPS', 'Satunu kumar mondal', 1, 'SENIOR CONSULTANT', '004'),
(3, 'MBBS, FCPS', 'DR Aman Biswas', 1, 'SENIOR CONSULTANT', '001');

-- --------------------------------------------------------

--
-- Table structure for table `parameter`
--

CREATE TABLE `parameter` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ref_value` varchar(255) DEFAULT NULL,
  `result_type` varchar(255) DEFAULT NULL,
  `param_group_id` int(11) NOT NULL,
  `shorting_id` int(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `parameter`
--

INSERT INTO `parameter` (`id`, `name`, `ref_value`, `result_type`, `param_group_id`, `shorting_id`, `unit`) VALUES
(1, 'Color', NULL, 'DropDown', 1, 10, NULL),
(2, 'Appearance', '6 - 9', 'text', 1, 4, 'mg/ml'),
(3, 'Reaction', '6 - 12', 'text', 2, 3, 'mg/ml'),
(4, 'Excess of phosphate', NULL, 'DropDown', 2, 9, NULL),
(5, 'R.C.B', '2- 9', 'text', 3, 16, 'mg/ml'),
(6, 'Puss cells', '12 - 19', 'text', 3, 11, 'mg/ml'),
(7, 'Parameter 7', '7-9', 'text', 4, 2, 'mg/ml'),
(8, 'Parameter 8', '5 - 10', 'text', 4, 9, 'mg/ml'),
(9, 'Chemical sub parameter 2', NULL, 'DropDown', 2, 7, NULL),
(10, 'Parameter 10', NULL, 'DropDown', 2, 7, NULL),
(11, 'Parameter 11', NULL, 'DropDown', 2, 7, NULL),
(12, 'Parameter 12', NULL, 'DropDown', 2, 7, NULL),
(13, 'Parameter 13', NULL, 'DropDown', 2, 7, NULL),
(14, 'Parameter 14', NULL, 'DropDown', 2, 7, NULL),
(15, 'Parameter 15', NULL, 'DropDown', 2, 7, NULL),
(16, 'Parameter 16', NULL, 'DropDown', 2, 7, NULL),
(17, 'Parameter 17', NULL, 'DropDown', 2, 7, NULL),
(18, 'Parameter 18', NULL, 'DropDown', 2, 7, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `paramgroup_department`
--

CREATE TABLE `paramgroup_department` (
  `param_groups_id` int(11) NOT NULL,
  `departments_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `paramgroup_department`
--

INSERT INTO `paramgroup_department` (`param_groups_id`, `departments_id`) VALUES
(1, 1),
(1, 2),
(1, 5),
(2, 5),
(2, 1),
(3, 5),
(4, 5),
(5, 5),
(6, 5),
(7, 5),
(8, 5),
(9, 5),
(10, 5),
(11, 5);

-- --------------------------------------------------------

--
-- Table structure for table `paramgroup_sub_group`
--

CREATE TABLE `paramgroup_sub_group` (
  `param_groups_id` int(11) NOT NULL,
  `sub_groups_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `paramgroup_sub_group`
--

INSERT INTO `paramgroup_sub_group` (`param_groups_id`, `sub_groups_id`) VALUES
(1, 1),
(2, 1),
(4, 1),
(3, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(9, 1),
(11, 1);

-- --------------------------------------------------------

--
-- Table structure for table `param_group`
--

CREATE TABLE `param_group` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `heading_show` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `param_group`
--

INSERT INTO `param_group` (`id`, `name`, `heading_show`) VALUES
(1, 'Physical Examination', b'1'),
(2, 'Chemical  Examination', b'0'),
(3, 'Microscopic Examination', b'1'),
(4, 'Param Group 4', b'1'),
(5, 'Param Group 5', b'1'),
(6, 'Param Group 6', b'1'),
(7, 'Param Group 7', b'1'),
(8, 'Param Group 8', b'1'),
(9, 'Param Group 9', b'1'),
(10, 'Param Group 10', b'1'),
(11, 'Param Group 11', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `id` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `age` varchar(255) DEFAULT NULL,
  `bill_number` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `payment_amount` varchar(255) DEFAULT NULL,
  `payment_mode` varchar(255) DEFAULT NULL,
  `reference_doctor` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`id`, `created_date`, `update_date`, `age`, `bill_number`, `gender`, `mobile`, `name`, `payment_amount`, `payment_mode`, `reference_doctor`) VALUES
(32, '2020-12-22 14:01:04', '2021-01-01 06:19:41', '22', 'b000008', 'male', '01521453788', ' test', '1111', 'null', 'null'),
(33, '2020-12-22 14:01:04', '2021-01-01 06:25:58', '22', 'b000009', 'male', '01521453700', ' test', '345678', 'null', 'Hasiv Ullah'),
(56, '2021-01-26 17:05:48', '2021-01-26 17:05:48', '22', 'b0000099997', 'Male', '2345678', 'Usuf', '3000', 'Bkash', 'Aman Biswas'),
(57, '2021-01-26 17:15:28', '2021-01-26 17:15:28', '1122233', 'b0000', 'Female', '1122233', 'md ibrahim', '5000', 'Bkash', 'Aman Biswas'),
(58, '2021-01-26 17:31:05', '2021-01-26 17:31:05', '32', 'b99999', 'Male', '1122233', 'md ibrahim', '5000', 'Bkash', 'Satunu kumar mondal'),
(59, '2021-01-27 14:36:08', '2021-01-27 14:36:08', NULL, 'b000059', NULL, NULL, '1122233', '2222', NULL, NULL),
(60, '2021-01-27 15:13:09', '2021-01-27 15:13:09', NULL, 'b34567', 'Female', '1122233', '1122233', '5000', 'SureCash', 'Satunu kumar mondal'),
(61, '2021-01-27 15:15:55', '2021-01-27 15:15:55', '1122233', 'b000061', 'Female', '1122233', '1122233', '5000', 'SureCash', 'S K Ballav'),
(62, '2021-01-27 16:06:19', '2021-01-27 16:06:19', '22', 'b23456789', 'Male', '1122233', 'MD Ibrahim Khan', '5000', 'SureCash', 'S K Ballav'),
(67, '2021-01-30 09:50:02', '2021-01-30 09:50:02', '33', 'b55555', 'Male', '091221234', 'MD Ibrahim Khan', '9000', NULL, 'Aman Biswas'),
(68, '2021-02-03 17:57:12', '2021-02-03 17:57:12', '22', 'b12345678', 'Male', '1234567890', 'rifat', '1500', 'Payment Cash', 'Satunu kumar mondal'),
(69, '2021-02-06 13:19:48', '2021-02-06 13:19:48', '22', 'b088888', 'Male', '1234567', 'MD Zahid Khan', '5000', 'Payment Cash', 'Satunu kumar mondal');

-- --------------------------------------------------------

--
-- Table structure for table `provider`
--

CREATE TABLE `provider` (
  `id` int(11) NOT NULL,
  `medical_name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `provider`
--

INSERT INTO `provider` (`id`, `medical_name`, `note`) VALUES
(1, 'Khulna Medical Hospital', ''),
(2, 'Shaheed Sheikh Abu Naser Specialised Hospital', ''),
(3, 'Khulna City Hospital', NULL),
(4, 'Islami Bank Hospital Khulna', NULL),
(5, 'Khulna Shishu Hospital', NULL),
(6, 'Gazi Medical College and Hospital', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `specimen`
--

CREATE TABLE `specimen` (
  `id` int(11) NOT NULL,
  `amout` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `specimen`
--

INSERT INTO `specimen` (`id`, `amout`, `description`, `name`) VALUES
(1, 100, 'Speciman 1', 'Speciman 1'),
(2, 200, 'Speciman 2', 'Speciman 2'),
(3, 200, 'Speciman 3', 'Speciman 3');

-- --------------------------------------------------------

--
-- Table structure for table `sub_group`
--

CREATE TABLE `sub_group` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `heading` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sub_group`
--

INSERT INTO `sub_group` (`id`, `description`, `heading`, `name`) VALUES
(1, 'description', 'Sub group 1', 'Sub group 1'),
(2, 'Sub group 2', 'Sub group 2', 'Sub group 2'),
(3, 'Sub group 3', 'Sub group 3', 'Sub group 3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accessories`
--
ALTER TABLE `accessories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3ojhvkpmhbu5q9g2y7e4yesmf` (`provider_id`);

--
-- Indexes for table `drop_down_input`
--
ALTER TABLE `drop_down_input`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `drop_down_input_parameters`
--
ALTER TABLE `drop_down_input_parameters`
  ADD KEY `FKg5uwdtk3xymaeoq5cq96awqpl` (`parameters_id`),
  ADD KEY `FK6d4pssgbcfjs99b2smc1sdt2p` (`drop_down_input_id`);

--
-- Indexes for table `investigation`
--
ALTER TABLE `investigation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfhhuy2cs3qehqtfju0kqyatww` (`department_id`),
  ADD KEY `FKnei7wr701dkk3c7kxgh6k90by` (`provider_id`),
  ADD KEY `FK8i8slsu3pkgqa8slx9fkx0a03` (`specimen_id`),
  ADD KEY `FK89015t0jrgpkl8wcw94opabpy` (`sub_group_id`);

--
-- Indexes for table `investigation_accessories`
--
ALTER TABLE `investigation_accessories`
  ADD KEY `FK11jg1e4i0hy1rt5un0g81hvho` (`accessories_id`),
  ADD KEY `FKedf99wktbrlclqhnk4an1xeg2` (`investigation_id`);

--
-- Indexes for table `investigation_parameters`
--
ALTER TABLE `investigation_parameters`
  ADD KEY `FK88ibxyv7asapb16f16l99t5mc` (`parameters_id`),
  ADD KEY `FK7g2pd62n9m79q8cofecs8pi0` (`investigation_id`);

--
-- Indexes for table `investigation_param_group`
--
ALTER TABLE `investigation_param_group`
  ADD KEY `FKhk2ghhpd6nomwyrkknxcvkfe4` (`param_group_id`),
  ADD KEY `FKi8x42sp9ulrv808so7ob37svh` (`investigation_id`);

--
-- Indexes for table `investigation_request`
--
ALTER TABLE `investigation_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmsqw6uait07pxj12ayn8pnu4a` (`investigation_id`),
  ADD KEY `FKdnpqx53ouf35n8lfps49i6l6n` (`lab_doctor_id`),
  ADD KEY `FKeg5op2hjp9111qv3ank70huqh` (`patient_id`),
  ADD KEY `FKjd35lh3tf6664vvv7vl4pnv8r` (`provider_id`);

--
-- Indexes for table `investivation_parameter_result`
--
ALTER TABLE `investivation_parameter_result`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKo3n2qyljivchjxnlrfulpd57m` (`investigation_request_id`),
  ADD KEY `FKm49vr535onu2vpbbp55y1uiol` (`parameter_id`);

--
-- Indexes for table `lab_doctor`
--
ALTER TABLE `lab_doctor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjuifg2gkic1kptkige05gghix` (`provider_id`);

--
-- Indexes for table `parameter`
--
ALTER TABLE `parameter`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtj7w1ypuii3rv15tvsi3a6tx4` (`param_group_id`);

--
-- Indexes for table `paramgroup_department`
--
ALTER TABLE `paramgroup_department`
  ADD KEY `FKmh6th510qp4hgi9n38g1n6sbp` (`departments_id`),
  ADD KEY `FKtniuggh0t9dspr9gks1h9ub93` (`param_groups_id`);

--
-- Indexes for table `paramgroup_sub_group`
--
ALTER TABLE `paramgroup_sub_group`
  ADD KEY `FK83oofxl0fjnixrt6wa0nqgthj` (`sub_groups_id`),
  ADD KEY `FK7u45fqel3y9t5kc40pqlvd5k9` (`param_groups_id`);

--
-- Indexes for table `param_group`
--
ALTER TABLE `param_group`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `provider`
--
ALTER TABLE `provider`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `specimen`
--
ALTER TABLE `specimen`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sub_group`
--
ALTER TABLE `sub_group`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accessories`
--
ALTER TABLE `accessories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `doctor`
--
ALTER TABLE `doctor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `investigation`
--
ALTER TABLE `investigation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT for table `investigation_request`
--
ALTER TABLE `investigation_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=143;

--
-- AUTO_INCREMENT for table `investivation_parameter_result`
--
ALTER TABLE `investivation_parameter_result`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT for table `lab_doctor`
--
ALTER TABLE `lab_doctor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `parameter`
--
ALTER TABLE `parameter`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `param_group`
--
ALTER TABLE `param_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT for table `specimen`
--
ALTER TABLE `specimen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `sub_group`
--
ALTER TABLE `sub_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `doctor`
--
ALTER TABLE `doctor`
  ADD CONSTRAINT `FK3ojhvkpmhbu5q9g2y7e4yesmf` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`);

--
-- Constraints for table `drop_down_input_parameters`
--
ALTER TABLE `drop_down_input_parameters`
  ADD CONSTRAINT `FK6d4pssgbcfjs99b2smc1sdt2p` FOREIGN KEY (`drop_down_input_id`) REFERENCES `drop_down_input` (`id`),
  ADD CONSTRAINT `FKg5uwdtk3xymaeoq5cq96awqpl` FOREIGN KEY (`parameters_id`) REFERENCES `parameter` (`id`);

--
-- Constraints for table `investigation`
--
ALTER TABLE `investigation`
  ADD CONSTRAINT `FK89015t0jrgpkl8wcw94opabpy` FOREIGN KEY (`sub_group_id`) REFERENCES `sub_group` (`id`),
  ADD CONSTRAINT `FK8i8slsu3pkgqa8slx9fkx0a03` FOREIGN KEY (`specimen_id`) REFERENCES `specimen` (`id`),
  ADD CONSTRAINT `FKfhhuy2cs3qehqtfju0kqyatww` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  ADD CONSTRAINT `FKnei7wr701dkk3c7kxgh6k90by` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`);

--
-- Constraints for table `investigation_accessories`
--
ALTER TABLE `investigation_accessories`
  ADD CONSTRAINT `FK11jg1e4i0hy1rt5un0g81hvho` FOREIGN KEY (`accessories_id`) REFERENCES `accessories` (`id`),
  ADD CONSTRAINT `FKedf99wktbrlclqhnk4an1xeg2` FOREIGN KEY (`investigation_id`) REFERENCES `investigation` (`id`);

--
-- Constraints for table `investigation_parameters`
--
ALTER TABLE `investigation_parameters`
  ADD CONSTRAINT `FK7g2pd62n9m79q8cofecs8pi0` FOREIGN KEY (`investigation_id`) REFERENCES `investigation` (`id`),
  ADD CONSTRAINT `FK88ibxyv7asapb16f16l99t5mc` FOREIGN KEY (`parameters_id`) REFERENCES `parameter` (`id`);

--
-- Constraints for table `investigation_param_group`
--
ALTER TABLE `investigation_param_group`
  ADD CONSTRAINT `FKhk2ghhpd6nomwyrkknxcvkfe4` FOREIGN KEY (`param_group_id`) REFERENCES `param_group` (`id`),
  ADD CONSTRAINT `FKi8x42sp9ulrv808so7ob37svh` FOREIGN KEY (`investigation_id`) REFERENCES `investigation` (`id`);

--
-- Constraints for table `investigation_request`
--
ALTER TABLE `investigation_request`
  ADD CONSTRAINT `FKdnpqx53ouf35n8lfps49i6l6n` FOREIGN KEY (`lab_doctor_id`) REFERENCES `lab_doctor` (`id`),
  ADD CONSTRAINT `FKeg5op2hjp9111qv3ank70huqh` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  ADD CONSTRAINT `FKjd35lh3tf6664vvv7vl4pnv8r` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`),
  ADD CONSTRAINT `FKmsqw6uait07pxj12ayn8pnu4a` FOREIGN KEY (`investigation_id`) REFERENCES `investigation` (`id`);

--
-- Constraints for table `investivation_parameter_result`
--
ALTER TABLE `investivation_parameter_result`
  ADD CONSTRAINT `FKm49vr535onu2vpbbp55y1uiol` FOREIGN KEY (`parameter_id`) REFERENCES `parameter` (`id`),
  ADD CONSTRAINT `FKo3n2qyljivchjxnlrfulpd57m` FOREIGN KEY (`investigation_request_id`) REFERENCES `investigation_request` (`id`);

--
-- Constraints for table `lab_doctor`
--
ALTER TABLE `lab_doctor`
  ADD CONSTRAINT `FKjuifg2gkic1kptkige05gghix` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`);

--
-- Constraints for table `parameter`
--
ALTER TABLE `parameter`
  ADD CONSTRAINT `FKtj7w1ypuii3rv15tvsi3a6tx4` FOREIGN KEY (`param_group_id`) REFERENCES `param_group` (`id`);

--
-- Constraints for table `paramgroup_department`
--
ALTER TABLE `paramgroup_department`
  ADD CONSTRAINT `FKmh6th510qp4hgi9n38g1n6sbp` FOREIGN KEY (`departments_id`) REFERENCES `department` (`id`),
  ADD CONSTRAINT `FKtniuggh0t9dspr9gks1h9ub93` FOREIGN KEY (`param_groups_id`) REFERENCES `param_group` (`id`);

--
-- Constraints for table `paramgroup_sub_group`
--
ALTER TABLE `paramgroup_sub_group`
  ADD CONSTRAINT `FK7u45fqel3y9t5kc40pqlvd5k9` FOREIGN KEY (`param_groups_id`) REFERENCES `param_group` (`id`),
  ADD CONSTRAINT `FK83oofxl0fjnixrt6wa0nqgthj` FOREIGN KEY (`sub_groups_id`) REFERENCES `sub_group` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

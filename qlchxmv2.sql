-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 12, 2025 at 06:52 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `qlchxmv2`
--

-- --------------------------------------------------------

--
-- Table structure for table `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `MADH` varchar(10) NOT NULL,
  `MAXE` varchar(10) NOT NULL,
  `SOLUONG` int(11) DEFAULT NULL,
  `DONGIA` int(11) DEFAULT NULL,
  `THANHTIEN` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `MAHD` varchar(10) NOT NULL,
  `MAXE` varchar(10) NOT NULL,
  `SOLUONG` int(11) DEFAULT NULL,
  `DONGIA` int(11) DEFAULT NULL,
  `THANHTIEN` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `chitietphieunhap`
--

CREATE TABLE `chitietphieunhap` (
  `MAPN` bigint(20) NOT NULL,
  `MAXE` varchar(10) NOT NULL,
  `SOLUONG` int(11) DEFAULT NULL,
  `DONGIA` int(11) DEFAULT NULL,
  `THANHTIEN` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `chitietphieunhap`
--

INSERT INTO `chitietphieunhap` (`MAPN`, `MAXE`, `SOLUONG`, `DONGIA`, `THANHTIEN`) VALUES
(1, 'XM001', 3, 18000000, 54000000),
(2, 'XM002', 3, 21000000, 63000000),
(3, 'XM003', 1, 35000000, 35000000);

-- --------------------------------------------------------

--
-- Table structure for table `donhang`
--

CREATE TABLE `donhang` (
  `MADH` varchar(10) NOT NULL,
  `NGAYLAP` date DEFAULT NULL,
  `MAKH` varchar(10) NOT NULL,
  `DIACHI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TONGTIEN` int(11) DEFAULT NULL,
  `TRANGTHAI` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `donhang`
--

INSERT INTO `donhang` (`MADH`, `NGAYLAP`, `MAKH`, `DIACHI`, `TONGTIEN`, `TRANGTHAI`) VALUES
('1', '2024-01-15', '1', '123 Đường ABC, Quận XYZ, Thành phố Hồ Chí Minh', 15000000, 'Đang giao hàng'),
('2', '2024-01-20', '2', '456 Đường DEF, Quận UVW, Hà Nội', 25000000, 'Đang giao hàng');

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
--

CREATE TABLE `hoadon` (
  `MAHD` varchar(10) NOT NULL,
  `NGAYLAP` date DEFAULT NULL,
  `MAKH` varchar(10) NOT NULL,
  `MANV` varchar(10) NOT NULL,
  `TONGTIEN` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

CREATE TABLE `khachhang` (
  `MAKH` varchar(10) NOT NULL,
  `HOTEN` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `SDT` varchar(10) DEFAULT NULL,
  `DIACHI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TENDANGNHAP` varchar(50) DEFAULT NULL,
  `MATKHAU` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`MAKH`, `HOTEN`, `SDT`, `DIACHI`, `TENDANGNHAP`, `MATKHAU`) VALUES
('1', 'Nguyễn Văn A', '0912345678', 'Hà Nội', 'nguyenvana', '123456'),
('2', 'Trần Thị B', '0987654321', 'Hồ Chí Minh', 'tranthib', 'abcdef'),
('3', 'Lê Văn C', '0909123456', 'Đà Nẵng', 'levanc', '112233');

-- --------------------------------------------------------

--
-- Table structure for table `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `MANCC` varchar(10) NOT NULL,
  `TENNCC` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DIACHI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SODIENTHOAI` varchar(10) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `nhacungcap`
--

INSERT INTO `nhacungcap` (`MANCC`, `TENNCC`, `DIACHI`, `SODIENTHOAI`, `isActive`) VALUES
('NCC001', 'Công ty Honda Việt Nam', 'Hà Nội', '0123456789', 0),
('NCC002', 'Công ty Yamaha Motor', 'Đà Nẵng', '9876543210', 1),
('NCC003', 'Công ty SYM Việt Nam', 'Bình Dương', '0969764271', 1),
('NCC02', 'abc company', 'adss', '0987654321', 1);

-- --------------------------------------------------------

--
-- Table structure for table `nhanvien`
--

CREATE TABLE `nhanvien` (
  `MANV` varchar(10) NOT NULL,
  `HOTEN` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `NGAYSINH` date DEFAULT NULL,
  `GIOITINH` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SODIENTHOAI` varchar(10) DEFAULT NULL,
  `DIACHI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `CHUCVU` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TENDANGNHAP` varchar(50) DEFAULT NULL,
  `MATKHAU` varchar(100) DEFAULT NULL,
  `QUYEN` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isActive` smallint(6) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `nhanvien`
--

INSERT INTO `nhanvien` (`MANV`, `HOTEN`, `NGAYSINH`, `GIOITINH`, `SODIENTHOAI`, `DIACHI`, `CHUCVU`, `TENDANGNHAP`, `MATKHAU`, `QUYEN`, `isActive`) VALUES
('NV001', 'Huỳnh Chí Văn', '2005-05-15', 'Nam', '0911222333', 'Lâm Đồng', 'Quản lý', 'huynhchivan', '123', 'ADMIN', 1),
('NV002', 'Nguyễn Thanh Hiệu', '2005-09-20', 'Nam', '0988111222', 'Hải Phòng', 'Nhân viên bán hàng', 'nguyenthanhieu', '456', 'NHANVIENBANHANG', 1),
('NV003', 'Nguyễn Thanh Văn', '2005-12-01', 'Nam', '0909111222', 'Nam Định', 'Nhân viên kho', 'nguyenthanhvan', '789', 'NHANVIENKHO', 1),
('nv11', 'khang huy', '2005-08-29', 'Nam', '0123456798', 'abc', 'Nhân viên bán hàng', 'huy', 'huy', 'NHANVENBANHANG', 1);

-- --------------------------------------------------------

--
-- Table structure for table `phieunhap`
--

CREATE TABLE `phieunhap` (
  `MAPN` bigint(20) NOT NULL,
  `NGAYNHAP` date DEFAULT NULL,
  `MANV` varchar(10) NOT NULL,
  `MANCC` varchar(10) NOT NULL,
  `TONGTIEN` int(11) DEFAULT NULL,
  `status` enum('Đang_Chờ','Hoàn_Thành','Đã_Hủy') NOT NULL DEFAULT 'Hoàn_Thành'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `phieunhap`
--

INSERT INTO `phieunhap` (`MAPN`, `NGAYNHAP`, `MANV`, `MANCC`, `TONGTIEN`, `status`) VALUES
(1, '2025-03-15', 'NV001', 'NCC001', 54000000, 'Đang_Chờ'),
(2, '2025-03-18', 'NV001', 'NCC002', 63000000, 'Đang_Chờ'),
(3, '2025-03-20', 'NV003', 'NCC003', 35000000, 'Đang_Chờ');

-- --------------------------------------------------------

--
-- Table structure for table `xemay`
--

CREATE TABLE `xemay` (
  `MAXE` varchar(10) NOT NULL,
  `TENXE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `HANGXE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `GIABAN` int(11) DEFAULT NULL,
  `SOLUONG` int(11) DEFAULT NULL,
  `ANH` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `xemay`
--

INSERT INTO `xemay` (`MAXE`, `TENXE`, `HANGXE`, `GIABAN`, `SOLUONG`, `ANH`) VALUES
('XM001', 'Wave Alpha 110cc', 'Honda', 18000000, 10, NULL),
('XM002', 'Sirius Fi', 'Yamaha', 21000000, 5, NULL),
('XM003', 'Vision 2023', 'Honda', 35000000, 8, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`MADH`,`MAXE`),
  ADD KEY `MAXE` (`MAXE`);

--
-- Indexes for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD PRIMARY KEY (`MAHD`,`MAXE`),
  ADD KEY `MAXE` (`MAXE`);

--
-- Indexes for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD PRIMARY KEY (`MAPN`,`MAXE`),
  ADD KEY `MAXE` (`MAXE`);

--
-- Indexes for table `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`MADH`),
  ADD KEY `MAKH` (`MAKH`);

--
-- Indexes for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`MAHD`),
  ADD KEY `MAKH` (`MAKH`),
  ADD KEY `MANV` (`MANV`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MAKH`),
  ADD UNIQUE KEY `SDT` (`SDT`),
  ADD UNIQUE KEY `TENDANGNHAP` (`TENDANGNHAP`);

--
-- Indexes for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`MANCC`),
  ADD UNIQUE KEY `SODIENTHOAI` (`SODIENTHOAI`);

--
-- Indexes for table `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`MANV`),
  ADD UNIQUE KEY `SODIENTHOAI` (`SODIENTHOAI`),
  ADD UNIQUE KEY `TENDANGNHAP` (`TENDANGNHAP`);

--
-- Indexes for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`MAPN`),
  ADD KEY `MANV` (`MANV`),
  ADD KEY `MANCC` (`MANCC`);

--
-- Indexes for table `xemay`
--
ALTER TABLE `xemay`
  ADD PRIMARY KEY (`MAXE`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `MAPN` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD CONSTRAINT `chitietdonhang_ibfk_1` FOREIGN KEY (`MADH`) REFERENCES `donhang` (`MADH`),
  ADD CONSTRAINT `chitietdonhang_ibfk_2` FOREIGN KEY (`MAXE`) REFERENCES `xemay` (`MAXE`);

--
-- Constraints for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`MAHD`) REFERENCES `hoadon` (`MAHD`),
  ADD CONSTRAINT `chitiethoadon_ibfk_2` FOREIGN KEY (`MAXE`) REFERENCES `xemay` (`MAXE`);

--
-- Constraints for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD CONSTRAINT `chitietphieunhap_ibfk_1` FOREIGN KEY (`MAPN`) REFERENCES `phieunhap` (`MAPN`),
  ADD CONSTRAINT `chitietphieunhap_ibfk_2` FOREIGN KEY (`MAXE`) REFERENCES `xemay` (`MAXE`);

--
-- Constraints for table `donhang`
--
ALTER TABLE `donhang`
  ADD CONSTRAINT `donhang_ibfk_1` FOREIGN KEY (`MAKH`) REFERENCES `khachhang` (`MAKH`);

--
-- Constraints for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`MAKH`) REFERENCES `khachhang` (`MAKH`),
  ADD CONSTRAINT `hoadon_ibfk_2` FOREIGN KEY (`MANV`) REFERENCES `nhanvien` (`MANV`);

--
-- Constraints for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD CONSTRAINT `phieunhap_ibfk_1` FOREIGN KEY (`MANV`) REFERENCES `nhanvien` (`MANV`),
  ADD CONSTRAINT `phieunhap_ibfk_2` FOREIGN KEY (`MANCC`) REFERENCES `nhacungcap` (`MANCC`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

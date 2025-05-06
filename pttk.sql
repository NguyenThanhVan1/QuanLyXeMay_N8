-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               10.4.32-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for qlchxm
CREATE DATABASE IF NOT EXISTS `qlchxm` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `qlchxm`;

-- Dumping structure for table qlchxm.chitietdonhang
CREATE TABLE IF NOT EXISTS `chitietdonhang` (
  `MAXM` int(11) NOT NULL,
  `MADH` int(11) NOT NULL,
  `SOLUONG` int(11) NOT NULL,
  `GIATRI` decimal(18,0) NOT NULL,
  `THANHTIEN` decimal(18,0) NOT NULL,
  PRIMARY KEY (`MADH`,`MAXM`),
  KEY `FK_chitietdonhang_xemay` (`MAXM`),
  CONSTRAINT `FK_chitietdonhang_donhang` FOREIGN KEY (`MADH`) REFERENCES `donhang` (`MADH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_chitietdonhang_xemay` FOREIGN KEY (`MAXM`) REFERENCES `xemay` (`MAXE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table qlchxm.chitietdonhang: ~7 rows (approximately)
INSERT INTO `chitietdonhang` (`MAXM`, `MADH`, `SOLUONG`, `GIATRI`, `THANHTIEN`) VALUES
	(1, 1, 2, 31000000, 62000000),
	(2, 2, 1, 39000000, 39000000),
	(6, 3, 1, 28000000, 28000000),
	(4, 4, 1, 63000000, 63000000),
	(3, 5, 2, 50000000, 100000000),
	(6, 5, 1, 4000000, 4000000),
	(2, 6, 1, 39000000, 39000000);

-- Dumping structure for table qlchxm.chitiethoadon
CREATE TABLE IF NOT EXISTS `chitiethoadon` (
  `MAHD` int(11) NOT NULL,
  `MAXE` int(11) NOT NULL,
  `SOLUONG` int(11) DEFAULT NULL,
  `DONGGIA` decimal(18,0) DEFAULT NULL,
  `THANHTIEN` decimal(18,0) DEFAULT NULL,
  PRIMARY KEY (`MAHD`,`MAXE`),
  KEY `FK_chitiethoadon_xemay` (`MAXE`),
  CONSTRAINT `FK_chitiethoadon_hoadon` FOREIGN KEY (`MAHD`) REFERENCES `hoadon` (`MAHD`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_chitiethoadon_xemay` FOREIGN KEY (`MAXE`) REFERENCES `xemay` (`MAXE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table qlchxm.chitiethoadon: ~0 rows (approximately)

-- Dumping structure for table qlchxm.chitietphieunhap
CREATE TABLE IF NOT EXISTS `chitietphieunhap` (
  `MAPN` int(11) NOT NULL DEFAULT 0,
  `MAXE` int(11) NOT NULL DEFAULT 0,
  `SOLUONG` int(11) DEFAULT NULL,
  `DONGIA` decimal(18,0) DEFAULT NULL,
  `THANHTIEN` decimal(18,0) DEFAULT NULL,
  PRIMARY KEY (`MAPN`,`MAXE`),
  KEY `FK_chitietphieunhap_xemay` (`MAXE`),
  CONSTRAINT `FK_chitietphieunhap_phieunhap` FOREIGN KEY (`MAPN`) REFERENCES `phieunhap` (`MAPN`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_chitietphieunhap_xemay` FOREIGN KEY (`MAXE`) REFERENCES `xemay` (`MAXE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table qlchxm.chitietphieunhap: ~0 rows (approximately)

-- Dumping structure for table qlchxm.donhang
CREATE TABLE IF NOT EXISTS `donhang` (
  `MADH` int(11) NOT NULL AUTO_INCREMENT,
  `NGAYLAP` date DEFAULT NULL,
  `MAKH` int(11) DEFAULT NULL,
  `DIACHI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TONGTIEN` decimal(18,0) DEFAULT NULL,
  `TRANGTHAI` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MADH`),
  KEY `FK_donhang_khachhang` (`MAKH`),
  CONSTRAINT `FK_donhang_khachhang` FOREIGN KEY (`MAKH`) REFERENCES `khachhang` (`MAKH`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table qlchxm.donhang: ~6 rows (approximately)
INSERT INTO `donhang` (`MADH`, `NGAYLAP`, `MAKH`, `DIACHI`, `TONGTIEN`, `TRANGTHAI`) VALUES
	(1, '2023-05-03', 1, '123 Lý Thường Kiệt, Q10', 62000000, 'Đã hoàn thành'),
	(2, '2023-05-07', 2, '45 Lê Văn Sỹ, Q3', 39000000, 'Đang giao hàng'),
	(3, '2023-05-10', 3, '78 Nguyễn Trãi, Q5', 28000000, 'Chờ xử lý'),
	(4, '2023-05-14', 4, '12 Cách Mạng Tháng 8, Q1', 63000000, 'Chờ xử lý'),
	(5, '2023-05-18', 5, '88 Trần Hưng Đạo, Q1', 104000000, 'Chờ xử lý'),
	(6, '2023-05-21', 1, '123 Lý Thường Kiệt, Q10', 39000000, 'Chờ xử lý');

-- Dumping structure for table qlchxm.hoadon
CREATE TABLE IF NOT EXISTS `hoadon` (
  `MAHD` int(11) NOT NULL AUTO_INCREMENT,
  `NGAYLAP` date DEFAULT NULL,
  `MAKH` int(11) DEFAULT NULL,
  `MANV` int(11) DEFAULT NULL,
  `TONGTIEN` decimal(18,0) DEFAULT NULL,
  `MADH` int(11) DEFAULT NULL,
  PRIMARY KEY (`MAHD`),
  KEY `FK_hoadon_khachhang` (`MAKH`),
  KEY `FK_hoadon_nhanvien` (`MANV`),
  KEY `FK_hoadon_donhang` (`MADH`),
  CONSTRAINT `FK_hoadon_donhang` FOREIGN KEY (`MADH`) REFERENCES `donhang` (`MADH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_hoadon_khachhang` FOREIGN KEY (`MAKH`) REFERENCES `khachhang` (`MAKH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_hoadon_nhanvien` FOREIGN KEY (`MANV`) REFERENCES `nhanvien` (`MANV`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table qlchxm.hoadon: ~0 rows (approximately)

-- Dumping structure for table qlchxm.khachhang
CREATE TABLE IF NOT EXISTS `khachhang` (
  `MAKH` int(11) NOT NULL AUTO_INCREMENT,
  `HOTEN` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SDT` varchar(15) DEFAULT NULL,
  `DIACHI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TENDANGNHAP` varchar(50) DEFAULT NULL,
  `MATKHAU` varchar(100) DEFAULT NULL,
  `QUYEN` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MAKH`),
  UNIQUE KEY `TENDANGNHAP` (`TENDANGNHAP`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table qlchxm.khachhang: ~6 rows (approximately)
INSERT INTO `khachhang` (`MAKH`, `HOTEN`, `SDT`, `DIACHI`, `TENDANGNHAP`, `MATKHAU`, `QUYEN`) VALUES
	(1, 'Nguyễn Thị Hồng Gấm', '0938123456', 'Quận 3', 'nguyengam', '123456', 'user'),
	(2, 'Trần Hồng Ngọc', '0987987987', 'Quận 1', 'hongngoc', '123456', 'user'),
	(3, 'Nguyễn Quốc Việt', '0345672345', 'Quận 5', 'quocviet', '123456', 'user'),
	(4, 'Phạm Thị Minh Tâm', '0567890987', 'Quận 10', 'minhtam', '123456', 'user'),
	(5, 'Nguyễn Ngọc Yến Nhi', '0876543212', 'Quận 7', 'nhi123', '123456', 'user'),
	(6, 'Nguyễn Văn An', '0978675412', 'Quận Bình Thạnh', 'an97', '123456', 'user');

-- Dumping structure for table qlchxm.nhacungcap
CREATE TABLE IF NOT EXISTS `nhacungcap` (
  `MANCC` int(11) NOT NULL AUTO_INCREMENT,
  `TENNCC` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DIACHI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SODIENTHOAI` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`MANCC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table qlchxm.nhacungcap: ~0 rows (approximately)

-- Dumping structure for table qlchxm.nhanvien
CREATE TABLE IF NOT EXISTS `nhanvien` (
  `MANV` int(11) NOT NULL AUTO_INCREMENT,
  `HOTEN` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `NGAYSINH` date DEFAULT NULL,
  `GIOITINH` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SODIENTHOAI` varchar(15) DEFAULT NULL,
  `DIACHI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `CHUCVU` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TENDANGNHAP` varchar(50) DEFAULT NULL,
  `MATKHAU` varchar(100) DEFAULT NULL,
  `QUYEN` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`MANV`),
  UNIQUE KEY `TENDANGNHAP` (`TENDANGNHAP`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table qlchxm.nhanvien: ~5 rows (approximately)
INSERT INTO `nhanvien` (`MANV`, `HOTEN`, `NGAYSINH`, `GIOITINH`, `SODIENTHOAI`, `DIACHI`, `CHUCVU`, `TENDANGNHAP`, `MATKHAU`, `QUYEN`) VALUES
	(1, 'Nguyễn Văn A', '1990-05-10', 'Nam', '0909123456', '123 Lê Lợi, Q1', 'Quản lý', 'nguyenvana', 'matkhau1', 'admin'),
	(2, 'Trần Thị B', '1992-08-20', 'Nữ', '0909876543', '456 Trần Hưng Đạo, Q5', 'Nhân viên', 'tranthib', 'matkhau2', 'user'),
	(3, 'Lê Văn C', '1985-01-15', 'Nam', '0911222333', '789 Nguyễn Trãi, Q3', 'Kế toán', 'levanc', 'matkhau3', 'user'),
	(4, 'Phạm Thị D', '1995-12-01', 'Nữ', '0988333444', '321 Cách Mạng Tháng 8, Q10', 'Nhân viên', 'phamthid', 'matkhau4', 'user'),
	(5, 'Hoàng Văn E', '1988-06-30', 'Nam', '0977666555', '654 Võ Thị Sáu, Q3', 'Kỹ thuật', 'hoangvane', 'matkhau5', 'user');

-- Dumping structure for table qlchxm.phieunhap
CREATE TABLE IF NOT EXISTS `phieunhap` (
  `MAPN` int(11) NOT NULL AUTO_INCREMENT,
  `NGAYNHAP` date DEFAULT NULL,
  `MANV` int(11) DEFAULT NULL,
  `MANCC` int(11) DEFAULT NULL,
  `TONGTIEN` decimal(18,0) DEFAULT NULL,
  PRIMARY KEY (`MAPN`),
  KEY `FK_phieunhap_nhanvien` (`MANV`),
  KEY `FK_phieunhap_nhacungcap` (`MANCC`),
  CONSTRAINT `FK_phieunhap_nhacungcap` FOREIGN KEY (`MANCC`) REFERENCES `nhacungcap` (`MANCC`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_phieunhap_nhanvien` FOREIGN KEY (`MANV`) REFERENCES `nhanvien` (`MANV`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table qlchxm.phieunhap: ~0 rows (approximately)

-- Dumping structure for table qlchxm.xemay
CREATE TABLE IF NOT EXISTS `xemay` (
  `MAXE` int(11) NOT NULL AUTO_INCREMENT,
  `TENXE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `HANGXE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `GIABAN` decimal(18,0) DEFAULT NULL,
  `SOLUONG` int(11) DEFAULT NULL,
  PRIMARY KEY (`MAXE`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table qlchxm.xemay: ~8 rows (approximately)
INSERT INTO `xemay` (`MAXE`, `TENXE`, `HANGXE`, `GIABAN`, `SOLUONG`) VALUES
	(1, 'Vision 2023', 'Honda', 31000000, 10),
	(2, 'Lead 125cc', 'Honda', 39000000, 8),
	(3, 'AirBlade 160cc', 'Honda', 50000000, 6),
	(4, 'SH Mode 2023', 'Honda', 63000000, 4),
	(5, 'Grande Blue Core', 'Yamaha', 45000000, 7),
	(6, 'Janus 2022', 'Yamaha', 28000000, 12),
	(7, 'Freego S ABS', 'Yamaha', 33000000, 5),
	(8, 'NVX 155 VVA', 'Yamaha', 52000000, 3);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

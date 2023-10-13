CREATE
DATABASE DuAnTotNghiep;
USE
DuAnTotNghiep;

CREATE TABLE `ChatLieuDeGiay`
(
    `id`  Integer AUTO_INCREMENT PRIMARY KEY,
    `ten` nvarchar(50)
);

CREATE TABLE `KichCo`
(
    `id`   Integer AUTO_INCREMENT PRIMARY KEY,
    `size` int
);

CREATE TABLE `MauSac`
(
    `id`  Integer AUTO_INCREMENT PRIMARY KEY,
    `ten` nvarchar(50)
);

CREATE TABLE `XuatXu`
(
    `id`  Integer AUTO_INCREMENT PRIMARY KEY,
    `ten` nvarchar(50)
);

CREATE TABLE `ThuongHieu`
(
    `id`  Integer AUTO_INCREMENT PRIMARY KEY,
    `ten` nvarchar(50)
);

CREATE TABLE `ChatLieuGiay`
(
    `id`  Integer AUTO_INCREMENT PRIMARY KEY,
    `ten` nvarchar(50)
);

CREATE TABLE `AnhSanPham`
(
    `id`            Integer AUTO_INCREMENT PRIMARY KEY,
    `idGiaySneaker` Integer,
    `ten`           varchar(100)
);

CREATE TABLE `GiaySneaker`
(
    `id`           Integer AUTO_INCREMENT PRIMARY KEY,
    `ma`           varchar(25),
    `ten`          nvarchar(100),
    `anhChinh`     varchar(250),
    `moTa`         nvarchar(500),
    `trangThai`    int,
    `idThuongHieu` Integer,
    `idXuatXu`     Integer
);

CREATE TABLE `ChiTietSanPham`
(
    `id`               Integer AUTO_INCREMENT PRIMARY KEY,
    `soLuong`          int,
    `giaBan`           decimal(18, 2),
    `ngayTao`          date,
    `trangThai`        int,
    `idMauSac`         Integer,
    `idKichCo`         Integer,
    `idChatLieuGiay`   Integer,
    `idChatLieuDeGiay` Integer,
    `idGiaySneaker`    Integer
);

CREATE TABLE `HoaDon`
(
    `id`                  Integer AUTO_INCREMENT PRIMARY KEY,
    `maHoaDon`            varchar(36),
    `tenKhachHang`        nvarchar(40),
    `soDienThoai`         varchar(10),
    `diaChi`              nvarchar(250),
    `phuongXa`            nvarchar(100),
    `quanHuyen`           nvarchar(100),
    `tinhThanh`           nvarchar(100),
    `ngayTao`             date,
    `tongTien`            Decimal(18, 2),
    `phiVanChuyen`        Decimal(18, 2),
    `phuongThucThanhToan` nvarchar(50),
    `trangThai`           int,
    `idPhieuGiamGia`      Integer,
    `idKhachHang`         Integer,
    `idNhanVien`          Integer
);

CREATE TABLE `HoaDonChiTiet`
(
    `id`            Integer AUTO_INCREMENT PRIMARY KEY,
    `idHoaDon`      Integer,
    `idChiTietGiay` Integer,
    `donGia`        Decimal(18, 2),
    `soLuong`       int
);

CREATE TABLE `GioHang`
(
    `id`          Integer AUTO_INCREMENT PRIMARY KEY,
    `idKhachHang` Integer
);

CREATE TABLE `GioHangChiTiet`
(
    `id`               Integer AUTO_INCREMENT PRIMARY KEY,
    `idGioHang`        Integer,
    `idChiTietSanPham` Integer,
    `soLuong`          Integer,
    `giaBan`           Decimal(18, 2)
);

CREATE TABLE `KhachHang`
(
    `id`           Integer AUTO_INCREMENT PRIMARY KEY,
    `tenKhachHang` nvarchar(40),
    `email`        varchar(50),
    `soDienThoai`  varchar(15),
    `password`     varchar(25),
    `ngaySinh`     varchar(8),
    `diaChi`       nvarchar(250),
    `trangThai`    int
);

CREATE TABLE `ChucVu`
(
    `id`        Integer AUTO_INCREMENT PRIMARY KEY,
    `tenChucVu` nvarchar(50),
    `trangThai` boolean
);

CREATE TABLE `NhanVien`
(
    `id`          Integer AUTO_INCREMENT PRIMARY KEY,
    `hoTen`       nvarchar(50),
    `anh`         varchar(100),
    `email`       varchar(50),
    `matKhau`     varchar(25),
    `soDienThoai` varchar(10),
    `gioiTinh`    boolean,
    `ngaySinh`    varchar(8),
    `trangThai`   int,
    `idChucVu`    Integer
);

CREATE TABLE `PhieuGiamGia`
(
    `id`              Integer AUTO_INCREMENT PRIMARY KEY,
    `ma`              varchar(10),
    `ten`             nvarchar(300),
    `chietKhau`       Decimal(18, 2),
    `hinhThucGiamGia` boolean,
    `thoiGianBatDau`  Date,
    `thoiGianKetThuc` Date,
    `moTa`            nvarchar(100),
    `trangThai`       int,
    `idKhachHang`     Integer
);

CREATE TABLE `NhanVienChucVu`
(
    `idNhanVien` Integer,
    `idChucVu`   Integer
);

ALTER TABLE `AnhSanPham`
    ADD FOREIGN KEY (`idGiaySneaker`) REFERENCES `GiaySneaker` (`id`);

ALTER TABLE `GiaySneaker`
    ADD FOREIGN KEY (`idThuongHieu`) REFERENCES `ThuongHieu` (`id`);

ALTER TABLE `GiaySneaker`
    ADD FOREIGN KEY (`idXuatXu`) REFERENCES `XuatXu` (`id`);

ALTER TABLE `ChiTietSanPham`
    ADD FOREIGN KEY (`idMauSac`) REFERENCES `MauSac` (`id`);

ALTER TABLE `ChiTietSanPham`
    ADD FOREIGN KEY (`idKichCo`) REFERENCES `KichCo` (`id`);

ALTER TABLE `ChiTietSanPham`
    ADD FOREIGN KEY (`idChatLieuGiay`) REFERENCES `ChatLieuGiay` (`id`);

ALTER TABLE `ChiTietSanPham`
    ADD FOREIGN KEY (`idChatLieuDeGiay`) REFERENCES `ChatLieuDeGiay` (`id`);

ALTER TABLE `ChiTietSanPham`
    ADD FOREIGN KEY (`idGiaySneaker`) REFERENCES `GiaySneaker` (`id`);

ALTER TABLE `HoaDon`
    ADD FOREIGN KEY (`idPhieuGiamGia`) REFERENCES `PhieuGiamGia` (`id`);

ALTER TABLE `HoaDon`
    ADD FOREIGN KEY (`idKhachHang`) REFERENCES `KhachHang` (`id`);

ALTER TABLE `HoaDon`
    ADD FOREIGN KEY (`idNhanVien`) REFERENCES `NhanVien` (`id`);

ALTER TABLE `HoaDonChiTiet`
    ADD FOREIGN KEY (`idHoaDon`) REFERENCES `HoaDon` (`id`);

ALTER TABLE `HoaDonChiTiet`
    ADD FOREIGN KEY (`idChiTietGiay`) REFERENCES `ChiTietSanPham` (`id`);

ALTER TABLE `GioHang`
    ADD FOREIGN KEY (`idKhachHang`) REFERENCES `KhachHang` (`id`);

ALTER TABLE `GioHangChiTiet`
    ADD FOREIGN KEY (`idGioHang`) REFERENCES `GioHang` (`id`);

ALTER TABLE `GioHangChiTiet`
    ADD FOREIGN KEY (`idChiTietSanPham`) REFERENCES `ChiTietSanPham` (`id`);

ALTER TABLE `NhanVien`
    ADD FOREIGN KEY (`idChucVu`) REFERENCES `ChucVu` (`id`);

ALTER TABLE `PhieuGiamGia`
    ADD FOREIGN KEY (`idKhachHang`) REFERENCES `KhachHang` (`id`);

ALTER TABLE `NhanVienChucVu`
    ADD FOREIGN KEY (`idNhanVien`) REFERENCES `NhanVien` (`id`);

ALTER TABLE `NhanVienChucVu`
    ADD FOREIGN KEY (`idChucVu`) REFERENCES `ChucVu` (`id`);

-- Insert values
-- Insert KichCo
Insert into KichCo value (null, "37"),
	(null, "38"),
    (null, "39"),
    (null, "40"),
    (null, "41"),
    (null, "42");

-- Insert ThuongHieu
Insert into ThuongHieu value (null, "Adidas"),
    (null, "Nike"),
    (null, "Converse"),
    (null, "Puma"),
    (null, "Vans"),
    (null, "New Balance"),
    (null, "Balenciaga"),
    (null, "Dior"),
    (null, "Supreme"),
    (null, "Gucci");

-- Insert XuatXu
Insert into XuatXu value (null, "Đức"),
    (null, "Mỹ"),
    (null, "Anh"),
    (null, "Pháp"),
	(null, "Ý"),
    (null, "Trung Quốc"),
    (null, "Việt Nam"),
    (null, "Nhật Bản"),
    (null, "Hàn Quốc"),
    (null, "Hà Lan");

-- Insert MauSac
Insert into MauSac value (null, "Blue"),
	(null, "Black"),
	(null, "Pink"),
	(null, "Yellow"),
	(null, "purple");

-- Insert ChatLieuDeGiay
Insert into ChatLieuDeGiay value (null, "EVA"),
    (null, "PVC"),
    (null, "PU");

-- Insert ChatLieuGiay
Insert into ChatLieuGiay value (null, "Da Lộn"),
    (null, "Da Nubuck"),
    (null, "Da tổng hợp"),
    (null, "Da Shellac"),
    (null, "Vải canvas"),
    (null, "Vải Jeans"),
    (null, "Vải lưới");

-- Insert GiaySneaker
Insert into GiaySneaker value (null,"G1","Air Jordan 1 Zoom CMFT 2",null,"Da lộn cao cấp và bọt Công thức 23 đặc trưng của Jordan Brand kết hợp với nhau để mang đến cho bạn chiếc AJ1 sang trọng hơn (và cực kỳ ấm cúng). Bạn không cần phải chơi trò hoặc khi chọn phong cách hoặc sự thoải mái với kiểu này—điều này thật tuyệt, vì bạn xứng đáng có được cả hai.",0,2,8),
    (null,"G2","GIÀY LG2 SPZL",null,"LG2 lần đầu ra mắt với dòng sản phẩm SS22 SPZL và là phiên bản tiếp nối của Spezial LG nguyên bản từ mùa FW19. Đây là kiểu dáng kết hợp giữa giày bóng quần cổ điển và giày trong nhà.Mẫu Giày LG2 SPZL này có thân giày bằng vải nylon, 3 Sọc in nhung, các chi tiết phủ ngoài bằng da lộn cùng phần lỗ xỏ dây giày và viền gót giày bằng da. Mặt đến bao gồm đế giữa cắt theo khuôn bằng chất liệu EVA, phần bọc mũi giày bất đối xứng và đế ngoài bằng cao su. ",0,1,7),
    (null,"G3","Chuck 70s Low Cream White",null,"Converse 1970s là 1 trong những dòng sản phẩm bán chạy nhất của Converse.Sunflower là một trong những phối màu hot nhất của dòng Converse 1970s, rất đẹp và dễ phối đồ, đồng thời có 2 bản là cao cổ và thấp cổ",0,3,2),
    (null,"G4","Giày Puma Cell Speed Reflective In 371868-01",null,"Giày Puma Cell Speed Reflective In 371868-01 có thiết kế hiện đại và trẻ trung, phù hợp với nhiều đối tượng khác nhau, từ các vận động viên chuyên nghiệp đến những người đam mê thể thao và phong cách đường phố.",0,4,1),
    (null,"G5","VANS AUTHENTIC CLASSIC BLACK/WHITE",null,"Là phiên bản được ưa chuộng nhất trong bộ sưu tập Authentic của VANS với sự kết hợp 2 màu đen trắng dễ phối đồ và custom, đặc biệt là phiên bản cổ nhất có tuổi đời hơn 50 năm, dù vậy vẫn được fan hâm mộ săn đón và được sử dụng khá nhiều với những vận động viên trượt ván chuyên nghiệp. VANS CLASSIC AUTHENTIC BLACK/WHITE được đánh giá là một siêu phẩm cần có khi bạn quyết định sẽ trở thành một tín đồ của nhà VANS đấy!",0,5,2),
    (null,"G6","Unisex New Balance chuyển 90/60 giày",null,"Lưới phía trên với lớp phủ da lộn da lợn.Đế giữa mật độ kép có đệm ABZORB và SBS.Logo lưỡi lấy cảm hứng từ viên ngọc ren 991 nguyên bản.Thiết bị CR mờ ở gót chân.Mẫu đế ngoài kim cương lấy cảm hứng từ thiết kế 860 cổ điển.410 gram (14,5 oz)",0,6,8),
    (null,"G7","Balenciaga Triple S Trainer Black Red",null,"Đây là thương hiệu thời trang lâu đời nổi tiếng, cao cấp nhất nhì thế giới được thành lập từ năm 1919. Tiếp nối sự thành công, thương hiệu tiếp tục tung ra Triple S – dòng giày đa sắc màu, phá bỏ mọi giới hạn, đủ đẹp, đủ chất để khiến giới mộ điệu lại một lần nữa phải điên đảo Đây là mẫu giày cũng được nhiều người nghệ sĩ thế giới lựa chọn. Tại Việt Nam, giày Triple S được rất nhiều nghệ sĩ nổi tiếng yêu thích.",0,7,10),
    (null,"G8","WALK'N'DIOR PLATFORM SNEAKER",null,"Giày thể thao nền tảng Walk'n'Dior nâng tầm dòng sản phẩm với sự sáng tạo hiện đại. Phần trên thêu bông Dior Oblique màu xanh đậm để lộ phần đế dày thoải mái, lưỡi và dây buộc Christian Dior Paris. Giày thể thao sẽ thêm một nét hiện đại cho bất kỳ vẻ ngoài thoải mái nào.",0,8,4),
    (null,"G9","Giày Sneaker Nam Dolce & Gabbana D&G NS1 CS1810 AD505",null,"Giày Sneaker Nam Dolce & Gabbana D&G NS1 CS1810 AD505 được làm từ chất liệu 70% polyamide, 20% calf leather, 10% spandex cao cap, bền đẹp trong suốt qua trình sử dụng. Form giày chuẩn từng chi tiết, các đường nét vô cùng tinh tế và sắc xảo",0,9,2),
    (null,"G10","Giày Sneaker Nam Gucci Screener GG Leather Canvas 546551-9Y920-9666",null,"Đôi giày Sneaker Gucci Screener GG Leather Canvas 546551-9Y920-9666 là sự kết hợp của những ảnh hưởng khác nhau trải qua nhiều thập kỷ. Chất liệu được sử dụng là da, 2 bên sườn giày nổi bật với sọc web và logo cổ điển của Gucci tạo nên phong cách thể thao khỏe khoắn, năng động nhưng không kém phân sành điệu",0,1,5);

-- Insert ChiTietSanPham
Insert into ChiTietSanPham(id, soLuong, giaBan, ngayTao, trangThai, idMauSac, idKichCo, idChatLieuGiay,
                           idChatLieuDeGiay, idGiaySneaker) value (null,15,4259000,null,0,1,1,1,1,1),--
    (null,15,4259000,null,0,2,2,2,2,1),
    (null,15,4259000,null,0,3,3,3,3,1),
    (null,15,4259000,null,0,4,4,4,1,1),
    (null,15,4259000,null,0,5,5,5,2,1),--
    
    (null,15,3300000,null,0,1,1,1,1,2),--
    (null,15,3300000,null,0,2,2,2,2,2),
    (null,15,3300000,null,0,3,3,3,3,2),
    (null,15,3300000,null,0,4,4,4,1,2),
    (null,15,3300000,null,0,5,5,5,2,2),--
    
    (null,15,850000,null,0,1,1,1,1,3),--
    (null,15,850000,null,0,2,2,2,2,3),
    (null,15,850000,null,0,3,3,3,3,3),
    (null,15,850000,null,0,4,4,4,1,3),
    (null,15,850000,null,0,5,5,5,2,3),--
    
    (null,15,1100000,null,0,1,1,1,1,4),--
    (null,15,1100000,null,0,2,2,2,2,4),
    (null,15,1100000,null,0,3,3,3,3,4),
    (null,15,1100000,null,0,4,4,4,1,4),
    (null,15,1100000,null,0,5,5,5,2,4),--
    
    (null,15,5500000,null,0,1,1,1,1,5),--
    (null,15,5500000,null,0,2,2,2,2,5),
    (null,15,5500000,null,0,3,3,3,3,5),
    (null,15,5500000,null,0,4,4,4,1,5),
    (null,15,5500000,null,0,5,5,5,2,5),--
    
    (null,15,3500000,null,0,1,1,1,1,6),--
    (null,15,3500000,null,0,2,2,2,2,6),
    (null,15,3500000,null,0,3,3,3,3,6),
    (null,15,3500000,null,0,4,4,4,1,6),
    (null,15,3500000,null,0,5,5,5,2,6),--
    
    (null,15,3700000,null,0,1,1,1,1,7),--
    (null,15,3700000,null,0,2,2,2,2,7),
    (null,15,3700000,null,0,3,3,3,3,7),
    (null,15,3700000,null,0,4,4,4,1,7),
    (null,15,3700000,null,0,5,5,5,2,7),--
    
    (null,15,8000000,null,0,1,1,1,1,8),--
    (null,15,8000000,null,0,2,2,2,2,8),
    (null,15,8000000,null,0,3,3,3,3,8),
    (null,15,8000000,null,0,4,4,4,1,8),
    (null,15,8000000,null,0,5,5,5,2,8),--
    
    (null,15,8500000,null,0,1,1,1,1,9),--
    (null,15,8500000,null,0,2,2,2,2,9),
    (null,15,8500000,null,0,3,3,3,3,9),
    (null,15,8500000,null,0,4,4,4,1,9),
    (null,15,8500000,null,0,5,5,5,2,9),--
    
    (null,15,8800000,null,0,1,1,1,1,10),--
    (null,15,8800000,null,0,2,2,2,2,10),
    (null,15,8800000,null,0,3,3,3,3,10),
    (null,15,8800000,null,0,4,4,4,1,10),
    (null,15,8800000,null,0,5,5,5,2,10);
--

-- Insert ChucVu
Insert into ChucVu value (null,"Quản Lý",0),
    (null,"Nhân Viên",0)
    


drop
database duantotnghiep;

CREATE
DATABASE DuAnTotNghiep;
USE
DuAnTotNghiep;

CREATE TABLE `ChatLieuDeGiay`
(
    `id`        Integer AUTO_INCREMENT PRIMARY KEY,
    `ten`       nvarchar(50),
    `trangThai` Integer
);

CREATE TABLE `KichCo`
(
    `id`        Integer AUTO_INCREMENT PRIMARY KEY,
    `size`      int,
    `trangThai` Integer
);

CREATE TABLE `MauSac`
(
    `id`        Integer AUTO_INCREMENT PRIMARY KEY,
    `ten`       nvarchar(50),
    `trangThai` Integer
);


CREATE TABLE `ChatLieuGiay`
(
    `id`        Integer AUTO_INCREMENT PRIMARY KEY,
    `ten`       nvarchar(50),
    `trangThai` Integer
);

CREATE TABLE `AnhSanPham`
(
    `id`                Integer AUTO_INCREMENT PRIMARY KEY,
    `id_ChiTietSanPham` Integer,
    `ten`               varchar(255)
);

CREATE TABLE `XuatXu`
(
    `id`        Integer AUTO_INCREMENT PRIMARY KEY,
    `ten`       nvarchar(50),
    `trangThai` Integer
);

CREATE TABLE `ThuongHieu`
(
    `id`        Integer AUTO_INCREMENT PRIMARY KEY,
    `ten`       nvarchar(50),
    `trangThai` Integer
);

CREATE TABLE `DanhMuc`
(
    `id`        Integer AUTO_INCREMENT PRIMARY KEY,
    `ten`       nvarchar(50),
    `trangThai` Integer
);

CREATE TABLE `SanPham`
(
    `id`            Integer AUTO_INCREMENT PRIMARY KEY,
    `ma`            nvarchar(100),
    `ten`           nvarchar(100),
    `anhChinh`      varchar(250),
    `moTa`          nvarchar(500),
    `ngayTao`       date,
    `ngayCapNhat`   date,
    `trangThai`     int,
    `id_ThuongHieu` Integer,
    `id_XuatXu`     Integer,
    `id_DanhMuc`    Integer
);

CREATE TABLE `ChiTietSanPham`
(
    `id`                Integer AUTO_INCREMENT PRIMARY KEY,
    `ma`                varchar(50),
    `soLuong`           int,
    `giaBan`            Decimal(20, 0),
    `ngayTao`           date,
    `ngayCapNhat`       date,
    `trangThai`         int,
    `id_MauSac`         Integer,
    `id_KichCo`         Integer,
    `id_ChatLieuGiay`   Integer,
    `id_ChatLieuDeGiay` Integer,
    `id_SanPham`        Integer
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
    `ngayCapNhat`         date,
    `tongTien`            Decimal(20, 0),
    `tienGiam`            Decimal(20, 0),
    `tongTienSauGiam`     Decimal(20, 0),
    `phiVanChuyen`        Decimal(20, 0),
    `phuongThucThanhToan` int,
    `trangThai`           int,
    `id_PhieuGiamGia`     Integer,
    `id_nhanVien`         Integer,
    `id_khachHang`        Integer
);

CREATE TABLE `HoaDonChiTiet`
(
    `id`                Integer AUTO_INCREMENT PRIMARY KEY,
    `id_HoaDon`         Integer,
    `id_ChiTietSanPham` Integer,
    `donGia`            Decimal(20, 0),
    `soLuong`           int
);

CREATE TABLE `TraHang`
(
    `id`				Integer AUTO_INCREMENT PRIMARY KEY,
    `id_HoaDon`			Integer,
    `lyDo`				Varchar(200),
    `ngayTao`           date,
    `ngayCapNhat`       date,
    `tienTraKhach`		Decimal(20, 0),
    `trangThai`			Integer
);

CREATE TABLE `TraHangChiTiet`
(
    `id`				Integer AUTO_INCREMENT PRIMARY KEY,
    `id_TraHang`		Integer,
    `id_HoaDonChiTiet`  Integer,
    `donGia`            Decimal(20, 0),
    `soLuong`           Integer
);


CREATE TABLE `GioHang`
(
    `id`           Integer AUTO_INCREMENT PRIMARY KEY,
    `id_khachHang` Integer
);

CREATE TABLE `GioHangChiTiet`
(
    `id`                Integer AUTO_INCREMENT PRIMARY KEY,
    `id_GioHang`        Integer,
    `id_ChiTietSanPham` Integer,
    `soLuong`           Integer,
    `giaBan`            Decimal(20, 0)
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
    `anhDaiDien`  varchar(500),
    `email`       varchar(50),
    `matKhau`     varchar(255),
    `soDienThoai` varchar(10),
    `gioiTinh`    boolean,
    `ngaySinh`    varchar(10),
    `diaChi`      nvarchar(250),
    `trangThai`   int
);

CREATE TABLE `NhanVienChucVu`
(
    `id_nhanVien` Integer,
    `id_ChucVu`   Integer
);

CREATE TABLE `KhachHang`
(
    `id`          Integer AUTO_INCREMENT PRIMARY KEY,
    `hoTen`       nvarchar(50),
    `email`       varchar(50),
    `matKhau`     varchar(255),
    `soDienThoai` varchar(10),
    `ngaySinh`    varchar(10),
    `trangThai`   int
);

CREATE TABLE `DiaChi`
(
    `id`           Integer AUTO_INCREMENT PRIMARY KEY,
    `diaChi`       nvarchar(255),
    `phuongXa`     nvarchar(255),
    `quanHuyen`    nvarchar(255),
    `tinhThanh`    nvarchar(255),
    `trangThai`    int,
    `id_khachHang` integer
);

CREATE TABLE `PhieuGiamGia`
(
    `id`              Integer AUTO_INCREMENT PRIMARY KEY,
    `ma`              varchar(10),
    `ten`             nvarchar(300),
    `chietKhau`       Decimal(20, 0),
    `hinhThucGiamGia` boolean,
    `thoiGianBatDau`  datetime,
    `thoiGianKetThuc` datetime,
    `moTa`            nvarchar(100),
    `trangThai`       int
);

CREATE TABLE `GioiThieu`
(
    `id`           Integer AUTO_INCREMENT PRIMARY KEY,
    `tenGioiThieu` nvarchar(255),
    `noiDung`      nvarchar(255),
    `logo`         nvarchar(255),
    `banner`       nvarchar(255),
    `moTa`         nvarchar(255),
    `ngayTao`      date,
    `ngayXoa`      date,
    `trangThai`    Integer,
    `id_nhanVien`  Integer
);


ALTER TABLE `AnhSanPham`
    ADD FOREIGN KEY (`id_ChiTietSanPham`) REFERENCES `ChiTietSanPham` (`id`);

ALTER TABLE `SanPham`
    ADD FOREIGN KEY (`id_ThuongHieu`) REFERENCES `ThuongHieu` (`id`);

ALTER TABLE `SanPham`
    ADD FOREIGN KEY (`id_XuatXu`) REFERENCES `XuatXu` (`id`);

ALTER TABLE `SanPham`
    ADD FOREIGN KEY (`id_DanhMuc`) REFERENCES `DanhMuc` (`id`);

ALTER TABLE `ChiTietSanPham`
    ADD FOREIGN KEY (`id_MauSac`) REFERENCES `MauSac` (`id`);

ALTER TABLE `ChiTietSanPham`
    ADD FOREIGN KEY (`id_KichCo`) REFERENCES `KichCo` (`id`);

ALTER TABLE `ChiTietSanPham`
    ADD FOREIGN KEY (`id_ChatLieuGiay`) REFERENCES `ChatLieuGiay` (`id`);

ALTER TABLE `ChiTietSanPham`
    ADD FOREIGN KEY (`id_ChatLieuDeGiay`) REFERENCES `ChatLieuDeGiay` (`id`);

ALTER TABLE `ChiTietSanPham`
    ADD FOREIGN KEY (`id_SanPham`) REFERENCES `SanPham` (`id`);

ALTER TABLE `HoaDon`
    ADD FOREIGN KEY (`id_PhieuGiamGia`) REFERENCES `PhieuGiamGia` (`id`);

ALTER TABLE `HoaDon`
    ADD FOREIGN KEY (`id_khachHang`) REFERENCES `KhachHang` (`id`);

ALTER TABLE `HoaDon`
    ADD FOREIGN KEY (`id_nhanVien`) REFERENCES `NhanVien` (`id`);

ALTER TABLE `HoaDonChiTiet`
    ADD FOREIGN KEY (`id_HoaDon`) REFERENCES `HoaDon` (`id`);

ALTER TABLE `HoaDonChiTiet`
    ADD FOREIGN KEY (`id_ChiTietSanPham`) REFERENCES `ChiTietSanPham` (`id`);

ALTER TABLE `TraHang`
    ADD FOREIGN KEY (`id_HoaDon`) REFERENCES `HoaDon` (`id`);

ALTER TABLE `TraHangChiTiet`
    ADD FOREIGN KEY (`id_TraHang`) REFERENCES `TraHang` (`id`);

ALTER TABLE `TraHangChiTiet`
    ADD FOREIGN KEY (`id_HoaDonChiTiet`) REFERENCES `HoaDonChiTiet` (`id`);

ALTER TABLE `GioHang`
    ADD FOREIGN KEY (`id_khachHang`) REFERENCES `KhachHang` (`id`);

ALTER TABLE `GioHangChiTiet`
    ADD FOREIGN KEY (`id_GioHang`) REFERENCES `GioHang` (`id`);

ALTER TABLE `GioHangChiTiet`
    ADD FOREIGN KEY (`id_ChiTietSanPham`) REFERENCES `ChiTietSanPham` (`id`);


ALTER TABLE `NhanVienChucVu`
    ADD FOREIGN KEY (`id_nhanVien`) REFERENCES `NhanVien` (`id`);

ALTER TABLE `NhanVienChucVu`
    ADD FOREIGN KEY (`id_chucVu`) REFERENCES `ChucVu` (`id`);

ALTER TABLE `DiaChi`
    ADD FOREIGN KEY (`id_khachHang`) REFERENCES `KhachHang` (`id`);

ALTER TABLE `GioiThieu`
    ADD FOREIGN KEY (`id_nhanvien`) REFERENCES `NhanVien` (`id`);

-- Insert values
-- Insert KichCo
Insert into KichCo value (null, "37",0),
	(null, "38",0),
    (null, "39",0),
    (null, "40",0),
    (null, "41",0),
    (null, "42",0);

-- Insert ThuongHieu
Insert into ThuongHieu value (null, "Adidas",0),
    (null, "Nike",0),
    (null, "Converse",0),
    (null, "Puma",0),
    (null, "Vans",0),
    (null, "New Balance",0),
    (null, "Balenciaga",0),
    (null, "Dior",0),
    (null, "Supreme",0),
    (null, "Gucci",0);

-- Insert XuatXu
Insert into XuatXu value (null, "Đức",0),
    (null, "Mỹ",0),
    (null, "Anh",0),
    (null, "Pháp",0),
	(null, "Ý",0),
    (null, "Trung Quốc",0),
    (null, "Việt Nam",0),
    (null, "Nhật Bản",0),
    (null, "Hàn Quốc",0),
    (null, "Hà Lan",0);

-- Insert Danh Mục
Insert into DanhMuc value (null, "Giày Nam",0),
    (null, "Giày Nữ",0);

-- Insert MauSac
Insert into MauSac value (null, "Blue",0),
	(null, "Black",0),
	(null, "Pink",0),
	(null, "Yellow",0),
	(null, "purple",0);

-- Insert ChatLieuDeGiay
Insert into ChatLieuDeGiay value (null, "EVA",0),
    (null, "PVC",0),
    (null, "PU",0);

-- Insert ChatLieuGiay
Insert into ChatLieuGiay value (null, "Da Lộn",0),
    (null, "Da Nubuck",0),
    (null, "Da tổng hợp",0),
    (null, "Da Shellac",0),
    (null, "Vải canvas",0),
    (null, "Vải Jeans",0),
    (null, "Vải lưới",0);

-- Insert SanPham
Insert into SanPham (id, ma, ten, anhChinh, moTa, ngayTao, ngayCapNhat, trangThai, id_ThuongHieu, id_XuatXu) value (null,"SP00001","Air Jordan 1 Zoom CMFT 2","https://res.cloudinary.com/dxmuvfnsp/image/upload/v1698385135/huqgjvxjzlkn6nywlz0z.jpg","Da lộn cao cấp và bọt Công thức 23 đặc trưng của Jordan Brand kết hợp với nhau để mang đến cho bạn chiếc AJ1 sang trọng hơn (và cực kỳ ấm cúng). Bạn không cần phải chơi trò hoặc khi chọn phong cách hoặc sự thoải mái với kiểu này—điều này thật tuyệt, vì bạn xứng đáng có được cả hai.","2023-10-27","2023-10-27",0,2,8),
    (null,"SP00002","GIÀY LG2 SPZL","https://res.cloudinary.com/dxmuvfnsp/image/upload/v1698385135/iakvaepuk9oftfoq8qgr.jpg","LG2 lần đầu ra mắt với dòng sản phẩm SS22 SPZL và là phiên bản tiếp nối của Spezial LG nguyên bản từ mùa FW19. Đây là kiểu dáng kết hợp giữa giày bóng quần cổ điển và giày trong nhà.Mẫu Giày LG2 SPZL này có thân giày bằng vải nylon, 3 Sọc in nhung, các chi tiết phủ ngoài bằng da lộn cùng phần lỗ xỏ dây giày và viền gót giày bằng da. Mặt đến bao gồm đế giữa cắt theo khuôn bằng chất liệu EVA, phần bọc mũi giày bất đối xứng và đế ngoài bằng cao su. ","2023-10-27","2023-10-27",0,1,7),
    (null,"SP00003","Chuck 70s Low Cream White","https://res.cloudinary.com/dxmuvfnsp/image/upload/v1698385135/jjmtsdklvfurp1o8fxah.jpg","Converse 1970s là 1 trong những dòng sản phẩm bán chạy nhất của Converse.Sunflower là một trong những phối màu hot nhất của dòng Converse 1970s, rất đẹp và dễ phối đồ, đồng thời có 2 bản là cao cổ và thấp cổ","2023-10-27","2023-10-27",0,3,2),
    (null,"SP00004","Giày Puma Cell Speed Reflective In 371868-01","https://res.cloudinary.com/dxmuvfnsp/image/upload/v1698385135/jjmtsdklvfurp1o8fxah.jpg","Giày Puma Cell Speed Reflective In 371868-01 có thiết kế hiện đại và trẻ trung, phù hợp với nhiều đối tượng khác nhau, từ các vận động viên chuyên nghiệp đến những người đam mê thể thao và phong cách đường phố.","2023-10-27","2023-10-27",0,4,1),
    (null,"SP00005","VANS AUTHENTIC CLASSIC BLACK/WHITE","https://res.cloudinary.com/dxmuvfnsp/image/upload/v1698385135/ppjedfjrz4mi55sw4wmu.jpg","Là phiên bản được ưa chuộng nhất trong bộ sưu tập Authentic của VANS với sự kết hợp 2 màu đen trắng dễ phối đồ và custom, đặc biệt là phiên bản cổ nhất có tuổi đời hơn 50 năm, dù vậy vẫn được fan hâm mộ săn đón và được sử dụng khá nhiều với những vận động viên trượt ván chuyên nghiệp. VANS CLASSIC AUTHENTIC BLACK/WHITE được đánh giá là một siêu phẩm cần có khi bạn quyết định sẽ trở thành một tín đồ của nhà VANS đấy!","2023-10-27","2023-10-27",0,5,2),
    (null,"SP00006","Unisex New Balance chuyển 90/60 giày","https://res.cloudinary.com/dxmuvfnsp/image/upload/v1698385135/ndcbphfeezechiap0djn.jpg","Lưới phía trên với lớp phủ da lộn da lợn.Đế giữa mật độ kép có đệm ABZORB và SBS.Logo lưỡi lấy cảm hứng từ viên ngọc ren 991 nguyên bản.Thiết bị CR mờ ở gót chân.Mẫu đế ngoài kim cương lấy cảm hứng từ thiết kế 860 cổ điển.410 gram (14,5 oz)","2023-10-27","2023-10-27",0,6,8),
    (null,"SP00007","Balenciaga Triple S Trainer Black Red","https://res.cloudinary.com/dxmuvfnsp/image/upload/v1698385135/vylqdfc5ddkskl4cugfj.jpg","Đây là thương hiệu thời trang lâu đời nổi tiếng, cao cấp nhất nhì thế giới được thành lập từ năm 1919. Tiếp nối sự thành công, thương hiệu tiếp tục tung ra Triple S – dòng giày đa sắc màu, phá bỏ mọi giới hạn, đủ đẹp, đủ chất để khiến giới mộ điệu lại một lần nữa phải điên đảo Đây là mẫu giày cũng được nhiều người nghệ sĩ thế giới lựa chọn. Tại Việt Nam, giày Triple S được rất nhiều nghệ sĩ nổi tiếng yêu thích.","2023-10-27","2023-10-27",0,7,10),
    (null,"SP00008","WALK'N'DIOR PLATFORM SNEAKER","https://res.cloudinary.com/dxmuvfnsp/image/upload/v1698385134/smc36ptnecvlfkeimjpn.jpg","Giày thể thao nền tảng Walk'n'Dior nâng tầm dòng sản phẩm với sự sáng tạo hiện đại. Phần trên thêu bông Dior Oblique màu xanh đậm để lộ phần đế dày thoải mái, lưỡi và dây buộc Christian Dior Paris. Giày thể thao sẽ thêm một nét hiện đại cho bất kỳ vẻ ngoài thoải mái nào.","2023-10-27","2023-10-27",0,8,4),
    (null,"SP00009","Giày Sneaker Nam Dolce & Gabbana D&G NS1 CS1810 AD505","https://res.cloudinary.com/dxmuvfnsp/image/upload/v1698385134/ad2knzjb4gq7zjv47jkr.jpg","Giày Sneaker Nam Dolce & Gabbana D&G NS1 CS1810 AD505 được làm từ chất liệu 70% polyamide, 20% calf leather, 10% spandex cao cap, bền đẹp trong suốt qua trình sử dụng. Form giày chuẩn từng chi tiết, các đường nét vô cùng tinh tế và sắc xảo","2023-10-27","2023-10-27",0,9,2),
    (null,"SP00010","Giày Sneaker Nam Gucci Screener GG Leather Canvas 546551-9Y920-9666","https://res.cloudinary.com/dxmuvfnsp/image/upload/v1698385134/nouojqjpzpeexh3jdivw.jpg","Đôi giày Sneaker Gucci Screener GG Leather Canvas 546551-9Y920-9666 là sự kết hợp của những ảnh hưởng khác nhau trải qua nhiều thập kỷ. Chất liệu được sử dụng là da, 2 bên sườn giày nổi bật với sọc web và logo cổ điển của Gucci tạo nên phong cách thể thao khỏe khoắn, năng động nhưng không kém phân sành điệu","2023-10-27","2023-10-27",0,1,5);

-- Insert ChiTietSanPham
Insert into ChiTietSanPham(id, ma, soLuong, giaBan, ngayTao, ngayCapNhat, trangThai, id_MauSac, id_KichCo,
                           id_ChatLieuGiay,
                           id_ChatLieuDeGiay, id_SanPham) value (null,"G1",15,4259000,"2023-10-27","2023-10-27",0,1,1,1,1,1),--
    (null,"G2",15,4259000,"2023-10-27","2023-10-27",0,2,2,2,2,1),
    (null,"G3",15,4259000,"2023-10-27","2023-10-27",0,3,3,3,3,1),
    (null,"G4",15,4259000,"2023-10-27","2023-10-27",0,4,4,4,1,1),
    (null,"G5",15,4259000,"2023-10-27","2023-10-27",0,5,5,5,2,1),--

    (null,"G6",15,3300000,"2023-10-27","2023-10-27",0,1,1,1,1,2),--
    (null,"G7",15,3300000,"2023-10-27","2023-10-27",0,2,2,2,2,2),
    (null,"G8",15,3300000,"2023-10-27","2023-10-27",0,3,3,3,3,2),
    (null,"G9",15,3300000,"2023-10-27","2023-10-27",0,4,4,4,1,2),
    (null,"G10",15,3300000,"2023-10-27","2023-10-27",0,5,5,5,2,2),--

    (null,"G11",15,850000,"2023-10-27","2023-10-27",0,1,1,1,1,3),--
    (null,"G12",15,850000,"2023-10-27","2023-10-27",0,2,2,2,2,3),
    (null,"G13",15,850000,"2023-10-27","2023-10-27",0,3,3,3,3,3),
    (null,"G14",15,850000,"2023-10-27","2023-10-27",0,4,4,4,1,3),
    (null,"G15",15,850000,"2023-10-27","2023-10-27",0,5,5,5,2,3),--

    (null,"G16",15,1100000,"2023-10-27","2023-10-27",0,1,1,1,1,4),--
    (null,"G17",15,1100000,"2023-10-27","2023-10-27",0,2,2,2,2,4),
    (null,"G18",15,1100000,"2023-10-27","2023-10-27",0,3,3,3,3,4),
    (null,"G19",15,1100000,"2023-10-27","2023-10-27",0,4,4,4,1,4),
    (null,"G20",15,1100000,"2023-10-27","2023-10-27",0,5,5,5,2,4),--

    (null,"G21",15,5500000,"2023-10-27","2023-10-27",0,1,1,1,1,5),--
    (null,"G22",15,5500000,"2023-10-27","2023-10-27",0,2,2,2,2,5),
    (null,"G23",15,5500000,"2023-10-27","2023-10-27",0,3,3,3,3,5),
    (null,"G24",15,5500000,"2023-10-27","2023-10-27",0,4,4,4,1,5),
    (null,"G25",15,5500000,"2023-10-27","2023-10-27",0,5,5,5,2,5),--

    (null,"G26",15,3500000,"2023-10-27","2023-10-27",0,1,1,1,1,6),--
    (null,"G27",15,3500000,"2023-10-27","2023-10-27",0,2,2,2,2,6),
    (null,"G28",15,3500000,"2023-10-27","2023-10-27",0,3,3,3,3,6),
    (null,"G29",15,3500000,"2023-10-27","2023-10-27",0,4,4,4,1,6),
    (null,"G30",15,3500000,"2023-10-27","2023-10-27",0,5,5,5,2,6),--

    (null,"G31",15,3700000,"2023-10-27","2023-10-27",0,1,1,1,1,7),--
    (null,"G32",15,3700000,"2023-10-27","2023-10-27",0,2,2,2,2,7),
    (null,"G33",15,3700000,"2023-10-27","2023-10-27",0,3,3,3,3,7),
    (null,"G34",15,3700000,"2023-10-27","2023-10-27",0,4,4,4,1,7),
    (null,"G35",15,3700000,"2023-10-27","2023-10-27",0,5,5,5,2,7),--

    (null,"G36",15,8000000,"2023-10-27","2023-10-27",0,1,1,1,1,8),--
    (null,"G37",15,8000000,"2023-10-27","2023-10-27",0,2,2,2,2,8),
    (null,"G38",15,8000000,"2023-10-27","2023-10-27",0,3,3,3,3,8),
    (null,"G39",15,8000000,"2023-10-27","2023-10-27",0,4,4,4,1,8),
    (null,"G40",15,8000000,"2023-10-27","2023-10-27",0,5,5,5,2,8),--

    (null,"G41",15,8500000,"2023-10-27","2023-10-27",0,1,1,1,1,9),--
    (null,"G42",15,8500000,"2023-10-27","2023-10-27",0,2,2,2,2,9),
    (null,"G43",15,8500000,"2023-10-27","2023-10-27",0,3,3,3,3,9),
    (null,"G44",15,8500000,"2023-10-27","2023-10-27",0,4,4,4,1,9),
    (null,"G45",15,8500000,"2023-10-27","2023-10-27",0,5,5,5,2,9),--

    (null,"G46",15,8800000,"2023-10-27","2023-10-27",0,1,1,1,1,10),--
    (null,"G47",15,8800000,"2023-10-27","2023-10-27",0,2,2,2,2,10),
    (null,"G48",15,8800000,"2023-10-27","2023-10-27",0,3,3,3,3,10),
    (null,"G49",15,8800000,"2023-10-27","2023-10-27",0,4,4,4,1,10),
    (null,"G50",15,8800000,"2023-10-27","2023-10-27",0,5,5,5,2,10);
--

-- Insert ChucVu
Insert into ChucVu value (null,"Quản Lý",0),
    (null,"Nhân Viên",0);

-- Insert NhanVien
Insert into NhanVien value (null,"admin",null,"admin@gmail.com","$2a$10$d3XgEQxbzK/sIG4HbDIk0.lIQaDeLWPqv3bhuf7qt1YNgGAkw2Ln2","0987654321",0,"2023/10/08","ha noi",0),
    (null,"nhanVien",null,"nhanVien@gmail.com","$2a$10$d3XgEQxbzK/sIG4HbDIk0.lIQaDeLWPqv3bhuf7qt1YNgGAkw2Ln2","0987654321",0,"2023/10/08","ha noi",1);

-- Insert NhanVienChucVu
Insert into NhanVienChucVu value (1,1),
    (2,2);

-- Insert KhachHang
Insert into KhachHang value (null,"Trịnh Trọng Vũ","vuttph25379@fpt.edu.vn","$2a$10$A8M44tVtA.P0TUjUczlgMeskUu2WviP3BDS7G6X2tVx43WFScRFq6","0375111058","2023/10/17",0),
	(null,"Trần Quyết Chiến","chientqph25370@fpt.edu.vn","$2a$10$TKWMK7CjCas4JGyAc4LZceOQmoIwt8Ss4jiPQAwbtGfKi7BSpo2e6","0346544561","2023/10/17",0),
	(null,"Trương Duy Hưng","hungtdph254100@fpt.edu.vn","$2a$10$ySt2CyVKWYeSD/eOb4ps5u3ilsPk1gIBZO8NnS4ocYj1XAFZ/Xu1S","0348079278","2023/10/17",0),
	(null,"Lê Mạnh Tuấn","tuanlmph25427@fpt.edu.vn","$2a$10$VTLSJMj1TBfk25Ko/w4et.2G69bN2E6W0aOB1UjzyPGoIR0YIpOqW","0967340351","2023/10/17",0),
	(null,"Nguyễn Trọng Đức","ducntph25394@fpt.edu.vn","$2a$10$d3XgEQxbzK/sIG4HbDIk0.lIQaDeLWPqv3bhuf7qt1YNgGAkw2Ln2","0375670501","2023/10/17",0);

-- Insert DiaChi
Insert into DiaChi value (null,"hanoi",null,null,null,0,1),
	(null,"tuyenquang",null,null,null,0,2),
	(null,"namdinh",null,null,null,0,3),
	(null,"ninhbinh",null,null,null,0,4),
	(null,"thaibinh",null,null,null,0,5);

-- Insert GioiThieu
Insert into GioiThieu value (null,"gioithieu1","noidung1",null,null,"mota1",null,null,0,null),
	(null,"gioithieu2","noidung2",null,null,"mota2",null,null,0,null),
	(null,"gioithieu3","noidung3",null,null,"mota3",null,null,0,null),
	(null,"gioithieu4","noidung4",null,null,"mota4",null,null,0,null),
	(null,"gioithieu5","noidung5",null,null,"mota5",null,null,0,null)




DROP DATABASE IF EXISTS phongkhamnhakhoa;

-- Tạo cơ sở dữ liệu
CREATE DATABASE phongkhamnhakhoa;
USE phongkhamnhakhoa;

-- Tạo bảng Nhân viên
CREATE TABLE Employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birthDate DATE,
    address VARCHAR(255),
    gender INT,
    phoneNumber VARCHAR(15) UNIQUE,
    idCard VARCHAR(12) UNIQUE,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    salary DOUBLE,
    experienceYears INT, -- Số năm kinh nghiệm
    role ENUM('Lễ tân', 'Bác sĩ', 'Nhân viên quầy thuốc')
);

-- Tạo bảng Bác sĩ
CREATE TABLE Doctor (
    id INT PRIMARY KEY,
    specialty VARCHAR(255),
    FOREIGN KEY (id) REFERENCES Employee(id) ON DELETE CASCADE
);

-- Tạo bảng Bệnh nhân
CREATE TABLE Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birthDate DATE,
    address VARCHAR(255),
    gender INT,
    phoneNumber VARCHAR(15) UNIQUE,
    idCard VARCHAR(12) UNIQUE
);

-- Tạo bảng Thuốc
CREATE TABLE Drug (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE,
    stockQuantity INT
);

-- Tạo bảng Dịch vụ
CREATE TABLE Service (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE
);

-- Tạo bảng Đơn thuốc
CREATE TABLE Prescription (
    id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT,
    patient_id INT,
    diagnosis TEXT,
    treatment TEXT,
    FOREIGN KEY (doctor_id) REFERENCES Doctor(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE
);

-- Tạo bảng Chi tiết đơn thuốc
CREATE TABLE PrescriptionDetail (
    prescription_id INT,
    drug_id INT,
    quantity INT,
    PRIMARY KEY (prescription_id, drug_id),
    FOREIGN KEY (prescription_id) REFERENCES Prescription(id) ON DELETE CASCADE,
    FOREIGN KEY (drug_id) REFERENCES Drug(id) ON DELETE CASCADE
);

-- Nhập dữ liệu nhân viên (bao gồm bác sĩ, lễ tân, nhân viên quầy thuốc)
INSERT INTO Employee (name, birthDate, address, gender, phoneNumber, idCard, username, password, salary, experienceYears, role) VALUES
('Nguyễn Văn A', '1980-05-12', 'Hà Nội', 1, '0912345678', '123456789012', 'bacsia', 'password', 30000000, 15, 'Bác sĩ'),
('Trần Thị B', '1985-08-25', 'Hồ Chí Minh', 0, '0912345679', '123456789013', 'bacsib', 'password', 32000000, 12, 'Bác sĩ'),
('Lê Văn C', '1975-12-05', 'Đà Nẵng', 1, '0912345680', '123456789014', 'bacsic', 'password', 31000000, 20, 'Bác sĩ'),
('Phạm Hồng D', '1990-07-15', 'Hải Phòng', 1, '0912345681', '123456789015', 'bacsid', 'password', 29000000, 10, 'Bác sĩ'),
('Hoàng Minh E', '1982-04-10', 'Cần Thơ', 1, '0912345682', '123456789016', 'bacsie', 'password', 33000000, 18, 'Bác sĩ'),
('Nguyễn Thị X', '1995-09-20', 'Huế', 0, '0912345683', '123456789017', 'letan1', 'password', 12000000, 5, 'Lễ tân'),
('Đặng Thùy Y', '1998-03-11', 'Nha Trang', 0, '0912345684', '123456789018', 'letan2', 'password', 12500000, 3, 'Lễ tân'),
('Bùi Văn Z', '1990-11-30', 'Vũng Tàu', 1, '0912345685', '123456789019', 'nvqt1', 'password', 15000000, 8, 'Nhân viên quầy thuốc'),
('Võ Thị H', '1992-06-18', 'Buôn Ma Thuột', 0, '0912345686', '123456789020', 'nvqt2', 'password', 15500000, 7, 'Nhân viên quầy thuốc'),
('Lương Minh K', '1997-01-25', 'Đồng Nai', 1, '0912345687', '123456789021', 'nvqt3', 'password', 14000000, 6, 'Nhân viên quầy thuốc');

-- Nhập dữ liệu bác sĩ
INSERT INTO Doctor (id, specialty) VALUES
(1, 'Răng hàm mặt'),
(2, 'Chỉnh nha'),
(3, 'Nha chu'),
(4, 'Nội nha'),
(5, 'Phẫu thuật miệng');
-- Nhập thêm thuốc
INSERT INTO Drug (name, description, price, stockQuantity) VALUES
('Paracetamol', 'Giảm đau, hạ sốt', 5000, 100),
('Amoxicillin', 'Kháng sinh', 20000, 50),
('Erythromycin', 'Kháng sinh nhóm macrolid', 18000, 90),
('Lidocaine', 'Thuốc gây tê', 22000, 50),
('Chlorhexidine', 'Dung dịch sát khuẩn miệng', 12000, 100),
('Dexamethasone', 'Chống viêm, giảm sưng', 25000, 75);

-- Nhập thêm dịch vụ
INSERT INTO Service (name, price) VALUES
('Khám răng tổng quát', 200000),
('Trám răng sâu', 500000),
('Niềng răng', 15000000),
('Tẩy trắng răng', 3000000),
('Chữa viêm tủy', 800000),
('Nhổ răng', 1000000);

-- Nhập dữ liệu bệnh nhân
INSERT INTO Patient (name, birthDate, address, gender, phoneNumber, idCard) VALUES
('Nguyễn Văn B', '1990-05-12', 'Hà Nội', 1, '0912345679', '123456789013'),
('Trần Thị C', '1985-08-25', 'Hồ Chí Minh', 0, '0912345680', '123456789014'),
('Lê Văn D', '1980-01-10', 'Đà Nẵng', 1, '0912345681', '123456789015'),
('Cao Thị C', '1982-08-25', 'Bình Dương', 0, '0912345580', '123456729014');

-- Nhập thêm đơn thuốc cho bệnh nhân
INSERT INTO Prescription (doctor_id, patient_id, diagnosis, treatment) VALUES
(1, 1, 'Sâu răng', 'Trám răng'),
(2, 1, 'Viêm nướu', 'Dùng thuốc kháng sinh'),
(3, 2, 'Viêm nha chu', 'Điều trị viêm'),
(4, 3, 'Đau răng', 'Nhổ răng'),
(5, 3, 'Nhiễm trùng chân răng', 'Dùng kháng sinh và súc miệng'),
(1, 1, 'Khám răng tổng quát', 'Dùng dịch vụ khám răng tổng quát'),
(2, 2, 'Chữa viêm nướu', 'Dùng thuốc kháng sinh'),
(3, 3, 'Nhiễm trùng nướu', 'Điều trị viêm và dùng thuốc sát khuẩn');

-- Nhập chi tiết đơn thuốc cho các đơn thuốc đã nhập trên
INSERT INTO PrescriptionDetail (prescription_id, drug_id, quantity) VALUES
-- Đơn thuốc của bệnh nhân 1, bác sĩ 1
(1, 1, 2),  -- Paracetamol (Sâu răng)
(1, 2, 1),  -- Amoxicillin (Kháng sinh)
-- Đơn thuốc của bệnh nhân 1, bác sĩ 2
(2, 3, 1),  -- Erythromycin (Viêm nướu)
(2, 4, 2),  -- Lidocaine (Kháng sinh)
-- Đơn thuốc của bệnh nhân 2, bác sĩ 3
(3, 2, 3),  -- Erythromycin (Viêm nha chu)
(3, 5, 2),  -- Chlorhexidine (Sát khuẩn miệng)
-- Đơn thuốc của bệnh nhân 3, bác sĩ 4
(4, 1, 1),  -- Paracetamol (Đau răng)
(4, 4, 1),  -- Lidocaine (Thuốc gây tê)
-- Đơn thuốc của bệnh nhân 3, bác sĩ 5
(5, 4, 1),  -- Lidocaine (Nhiễm trùng chân răng)
(5, 2, 2),  -- Amoxicillin (Kháng sinh)
-- Đơn thuốc của bệnh nhân 1, bác sĩ 1 (khám tổng quát)
(6, 5, 1),  -- Chlorhexidine (Dịch vụ khám)
(6, 1, 1),  -- Paracetamol (Khám tổng quát)
-- Đơn thuốc của bệnh nhân 2, bác sĩ 2
(7, 3, 1),  -- Erythromycin (Chữa viêm nướu)
(7, 5, 2),  -- Chlorhexidine (Chữa viêm nướu)
-- Đơn thuốc của bệnh nhân 3, bác sĩ 3
(8, 2, 3),  -- Erythromycin (Nhiễm trùng nướu)
(8, 5, 2);  -- Chlorhexidine (Điều trị viêm)


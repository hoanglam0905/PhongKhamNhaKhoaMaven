CREATE DATABASE phongkhamnhakhoa;
use phongkhamnhakhoa;

DROP DATABASE phongkhamnhakhoa;

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
    dosage varchar(50),
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
    symptom text,-- triệu chứng
    advice text, -- lời khuyên của bs
    FOREIGN KEY (doctor_id) REFERENCES Doctor(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE
);

-- Tạo bảng Chi tiết đơn thuốc
CREATE TABLE PrescriptionDetail (
    prescription_id INT,
    drug_id INT,
    quantity INT,
    preDate datetime,-- thời gian khám
    service_id int,-- thêm để có nhiều dịch vụ
    PRIMARY KEY (prescription_id, drug_id,service_id),
    FOREIGN KEY (prescription_id) REFERENCES Prescription(id) ON DELETE CASCADE,
    FOREIGN KEY (drug_id) REFERENCES Drug(id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES Service(id) ON DELETE CASCADE
);

-- Dữ liệu mẫu cho bảng Employee
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

-- Dữ liệu bảng Doctor
INSERT INTO Doctor (id, specialty) VALUES
(1, 'Răng hàm mặt'),
(2, 'Chỉnh nha'),
(3, 'Nha chu'),
(4, 'Nội nha'),
(5, 'Phẫu thuật miệng');

-- Dữ liệu bảng Patient
INSERT INTO Patient (name, birthDate, address, gender, phoneNumber, idCard) VALUES
('Nguyễn Văn B', '1990-05-12', 'Hà Nội', 1, '0987654321', '234567890123'),
('Trần Thị C', '1985-08-25', 'Hồ Chí Minh', 0, '0987654322', '234567890124'),
('Lê Văn D', '1980-01-10', 'Đà Nẵng', 1, '0987654323', '234567890125'),
('Cao Thị C', '1982-08-25', 'Bình Dương', 0, '0987655580', '234567890126');

-- Dữ liệu bảng Drug
INSERT INTO Drug (name, description, price, dosage, stockQuantity) VALUES
('Paracetamol', 'Giảm đau, hạ sốt', 5000, '2 viên/ngày', 100),
('Amoxicillin', 'Kháng sinh', 20000, '1 viên/ngày', 50),
('Erythromycin', 'Kháng sinh nhóm macrolid', 18000, '1 viên/ngày', 90),
('Lidocaine', 'Thuốc gây tê', 22000, '1 viên/ngày', 50),
('Chlorhexidine', 'Dung dịch sát khuẩn miệng', 12000, '2 viên/ngày', 100),
('Dexamethasone', 'Chống viêm, giảm sưng', 25000, '1 viên/ngày', 75);

-- Dữ liệu bảng Service
INSERT INTO Service (name, price) VALUES
('Khám răng tổng quát', 200000),
('Trám răng sâu', 500000),
('Niềng răng', 15000000),
('Tẩy trắng răng', 3000000),
('Chữa viêm tủy', 800000),
('Nhổ răng', 1000000);

-- Dữ liệu bảng Prescription
INSERT INTO Prescription (doctor_id, patient_id, diagnosis, treatment, symptom, advice) VALUES
(1, 1, 'Sâu răng', 'Trám răng', 'Đau nhức khi ăn', 'Điều trị sớm'),
(2, 1, 'Viêm nướu', 'Dùng thuốc kháng sinh', 'Chảy máu lợi', 'Vệ sinh răng miệng thường xuyên'),
(3, 2, 'Viêm nha chu', 'Điều trị viêm', 'Đau, sưng lợi', 'Súc miệng kỹ'),
(4, 3, 'Đau răng', 'Nhổ răng', 'Đau dữ dội', 'Thăm khám định kỳ'),
(5, 3, 'Nhiễm trùng chân răng', 'Dùng kháng sinh và súc miệng', 'Sưng, sốt nhẹ', 'Uống thuốc đúng liều'),
(1, 1, 'Khám răng tổng quát', 'Dịch vụ khám tổng quát', 'Không có triệu chứng', 'Bảo dưỡng định kỳ');

-- Dữ liệu bảng PrescriptionDetail
INSERT INTO PrescriptionDetail (prescription_id, drug_id, quantity, preDate, service_id) VALUES
(1, 1, 2, '2023-04-01 08:30:00', 2),
(1, 2, 1, '2023-04-01 08:30:00', 2),
(2, 3, 1, '2023-04-02 09:00:00', 1),
(2, 4, 2, '2023-04-02 09:00:00', 1),
(3, 2, 3, '2023-04-03 10:15:00', 1),
(3, 5, 2, '2023-04-03 10:15:00', 1),
(4, 1, 1, '2023-04-04 11:00:00', 6),
(4, 4, 1, '2023-04-04 11:00:00', 6),
(5, 4, 1, '2023-04-05 08:45:00', 1),
(5, 2, 2, '2023-04-05 08:45:00', 1),
(6, 5, 1, '2023-04-06 09:30:00', 1),
(6, 1, 1, '2023-04-06 09:30:00', 1);

-- pdf querry1
SELECT 
    p.name AS 'Tên bệnh nhân',
    p.address AS 'Địa chỉ bệnh nhân',
    p.idCard AS 'ID Card',
    pr.diagnosis AS 'Diagnosis',
    e.name AS 'Tên bác sĩ khám',
    e.birthDate AS 'Ngày sinh',
    e.gender AS 'Gender',
    d.name AS 'Tên thuốc',
    pd.quantity AS 'Số lượng',
    pr.advice AS 'Lời khuyên',
    pr.symptom AS 'Triệu chứng'
FROM 
    Prescription pr
JOIN Patient p ON pr.patient_id = p.id
JOIN Doctor doc ON pr.doctor_id = doc.id
JOIN Employee e ON doc.id = e.id
JOIN PrescriptionDetail pd ON pr.id = pd.prescription_id
JOIN Drug d ON pd.drug_id = d.id
where p.id=1;
-- querry2
SELECT 
    p.name AS 'Tên bệnh nhân',
    p.address AS 'Địa chỉ bệnh nhân',
    pr.id AS 'Prescription ID',
    s.name AS 'Tên dịch vụ',
    s.price AS 'Giá dịch vụ',
    d.name AS 'Tên thuốc',
    pd.quantity AS 'Số lượng',
    d.price AS 'Giá tiền'
FROM 
    Prescription pr
JOIN Patient p ON pr.patient_id = p.id
JOIN PrescriptionDetail pd ON pr.id = pd.prescription_id
JOIN Drug d ON pd.drug_id = d.id
JOIN Service s ON pd.service_id = s.id
where p.id=1;



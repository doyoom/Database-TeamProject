CREATE DATABASE DB2024Team06;
USE DB2024Team06;

CREATE TABLE DB2024_Centers(
	centerid VARCHAR(20) PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	address VARCHAR(255),
	phone VARCHAR(20),
	business_hour VARCHAR(20),
	description VARCHAR(255)
);

CREATE TABLE DB2024_Members(
	memberid VARCHAR(20) PRIMARY KEY,
	centerid VARCHAR(20),
	name VARCHAR(255) NOT NULL,
	birthday DATE,
	phone VARCHAR(20) NOT NULL,
	gender CHAR,
	join_date DATE,
	quit_date DATE,
	address VARCHAR(255),
	password VARCHAR(255) NOT NULL,
	
	FOREIGN KEY (centerid) REFERENCES DB2024_Centers(centerid) ON DELETE NO ACTION
);

CREATE TABLE DB2024_Instructors(
	instructorid VARCHAR(20) PRIMARY KEY,
	centerid VARCHAR(20),
	name VARCHAR(255) NOT NULL,
	birthday DATE,
	phone VARCHAR(20) NOT NULL,
	gender CHAR,
	join_date DATE,
	quit_date DATE,
	address VARCHAR(255),
	
	FOREIGN KEY (centerid) REFERENCES DB2024_Centers(centerid) ON DELETE NO ACTION
);

CREATE TABLE DB2024_Employees(
	employeeid VARCHAR(20) PRIMARY KEY,
	centerid VARCHAR(20),
	name VARCHAR(255) NOT NULL,
	birthday DATE,
	phone VARCHAR(20) NOT NULL,
	gender CHAR,
	join_date DATE,
	quit_date DATE,
	address VARCHAR(255),
	password VARCHAR(255) NOT NULL,
	title VARCHAR(20),
	
	FOREIGN KEY (centerid) REFERENCES DB2024_Centers(centerid) ON DELETE NO ACTION
);

CREATE TABLE DB2024_Class_Types(
	ctypeid VARCHAR(20) PRIMARY KEY,
	sport VARCHAR(255) NOT NULL,
	level VARCHAR(20) NOT NULL
);

CREATE TABLE DB2024_Places(
	placeid VARCHAR(20) PRIMARY KEY,
	centerid VARCHAR(20),
	place_name VARCHAR(20) NOT NULL,
	max_people INT,
	
	FOREIGN KEY (centerid) REFERENCES DB2024_Centers(centerid) ON DELETE NO ACTION
);

CREATE TABLE DB2024_Class_Times(
	ctimeid VARCHAR(20) PRIMARY KEY,
	day VARCHAR(20) NOT NULL,
	hours VARCHAR(20)
);

CREATE TABLE DB2024_Classes(
	classid VARCHAR(20) PRIMARY KEY,
	centerid VARCHAR(20),
	instructorid VARCHAR(20),
	ctypeid VARCHAR(20),
	placeid VARCHAR(20),
	ctimeid VARCHAR(20),
	class_name VARCHAR(255) NOT NULL,
	class_fee INT,
	description TEXT,
	max_people INT,
	current_people INT,
	
	FOREIGN KEY (centerid) REFERENCES DB2024_Centers(centerid) ON DELETE NO ACTION,
	FOREIGN KEY (instructorid) REFERENCES DB2024_Instructors(instructorid) ON DELETE NO ACTION,
	FOREIGN KEY (ctypeid) REFERENCES DB2024_Class_Types(ctypeid) ON DELETE NO ACTION,
	FOREIGN KEY (placeid) REFERENCES DB2024_Places(placeid) ON DELETE NO ACTION,
	FOREIGN KEY (ctimeid) REFERENCES DB2024_Class_Times(ctimeid) ON DELETE NO ACTION
);

CREATE TABLE DB2024_Payments(
	payid VARCHAR(20) PRIMARY KEY,
	pay_type VARCHAR(20),
	pay_fee INT NOT NULL,
	date DATE,
	status VARCHAR(20) NOT NULL
);

CREATE TABLE DB2024_Attendings(
	attendid VARCHAR(20) PRIMARY KEY,
	memberid VARCHAR(20),
	payid VARCHAR(20),
	classid VARCHAR(20),
	date DATE,
	status VARCHAR(20),
	
	FOREIGN KEY (memberid) REFERENCES DB2024_Members(memberid) ON DELETE NO ACTION,
	FOREIGN KEY (payid) REFERENCES DB2024_Payments(payid) ON DELETE NO ACTION,
	FOREIGN KEY (classid) REFERENCES DB2024_Classes(classid) ON DELETE NO ACTION
);


INSERT INTO DB2024_Centers (centerid, name, address, phone, business_hour, description)
VALUES 
    ('center01', '파워피트니스', '서울특별시 서대문구 이화로18', '02-4343-5656', '6:00~24:00', '저희 헬스장은 최신 시설과 장비를 갖추고 있어 고객들이 건강 증진과 체력 향상에 집중할 수 있는 환경을 제공합니다. 전문 트레이너들이 맞춤형 피트니스 계획을 제공하며, 다양한 그룹 운동 프로그램도 제공합니다.'),
    ('center02', '파워피트니스 강남점', '서울특별시 강남구 배꽃로86', '02-6565-7979', '6:00~24:00', '파워피트니스 2호점 입니다. 최신 시설과 청결한 환경에서 다양한 운동을 경험해보실 수 있습니다. 전문 트레이너들이 직접 정확하고 안전한 운동 방법을 지도해 줍니다.');


INSERT INTO DB2024_Members (memberid, centerid, name, birthday, phone, gender, join_date, quit_date, address, password)
VALUES 
    ('member01001', 'center01', '신영민', STR_TO_DATE('2001-04-03', '%Y-%m-%d'), '010-2222-3333', 'M', STR_TO_DATE('2023-12-20', '%Y-%m-%d'), NULL, '서울특별시 서대문구', 'SYM12345!@'),
    ('member01002', 'center01', '박준영', STR_TO_DATE('1999-01-01', '%Y-%m-%d'), '010-5511-2216', 'M', STR_TO_DATE('2023-12-13', '%Y-%m-%d'), STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '서울특별시 강남구', 'abc1234!'),
    ('member01003', 'center01', '김하원', STR_TO_DATE('1990-05-15', '%Y-%m-%d'), '010-1234-5678', 'M', STR_TO_DATE('2022-01-10', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'abc12345'),
    ('member01004', 'center01', '이영희', STR_TO_DATE('1985-08-25', '%Y-%m-%d'), '010-9879-5432', 'F', STR_TO_DATE('2022-11-20', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'xyz98765'),
    ('member01005', 'center01', '이지원', STR_TO_DATE('1997-09-29', '%Y-%m-%d'), '010-9292-9898', 'F', STR_TO_DATE('2023-12-11', '%Y-%m-%d'), STR_TO_DATE('2024-05-01', '%Y-%m-%d'), '서울특별시 서대문구-', 'gT0#Ydl'),
    ('member01006', 'center01', '이다원', STR_TO_DATE('1998-09-19', '%Y-%m-%d'), '010-7272-9898', 'F', STR_TO_DATE('2023-12-10', '%Y-%m-%d'), NULL, '서울특별시 서대문구', 'mF0yJW'),
    ('member01007', 'center01', '김민희', STR_TO_DATE('1996-03-04', '%Y-%m-%d'), '010-4564-4785', 'F', STR_TO_DATE('2024-02-04', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'asd12345'),
    ('member01008', 'center01', '박상민', STR_TO_DATE('1990-05-10', '%Y-%m-%d'), '010-3849-4358', 'M', STR_TO_DATE('2024-02-04', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'sdf12345'),
    ('member01009', 'center01', '윤현수', STR_TO_DATE('1998-06-20', '%Y-%m-%d'), '010-8888-9999', 'M', STR_TO_DATE('2023-12-31', '%Y-%m-%d'), NULL, '서울특별시 강서구', 'pass1234'),
    ('member01010', 'center01', '최윤진', STR_TO_DATE('2001-12-05', '%Y-%m-%d'), '010-8743-8440', 'F', STR_TO_DATE('2022-12-25', '%Y-%m-%d'), NULL, '서울특별시 관악구', 'asdf1234'),
    ('member01011', 'center01', '김지연', STR_TO_DATE('2002-03-24', '%Y-%m-%d'), '010-3345-6654', 'F', STR_TO_DATE('2023-06-24', '%Y-%m-%d'), NULL, '서울특별시 마포구', 'mM5+e.TYh6.'),
    ('member01012', 'center01', '이성민', STR_TO_DATE('1990-02-13', '%Y-%m-%d'), '010-9954-5436', 'M', STR_TO_DATE('2022-02-15', '%Y-%m-%d'), STR_TO_DATE('2023-04-15', '%Y-%m-%d'), '서울특별시 서대문구', 'oB45\sP?rH'),
    ('member01013', 'center01', '박지우', STR_TO_DATE('1995-06-05', '%Y-%m-%d'), '010-0729-5580', 'F', STR_TO_DATE('2024-01-01', '%Y-%m-%d'), NULL, '서울특별시 서대문구', 'iA95G/qJ<T'),
    ('member01014', 'center01', '최현우', STR_TO_DATE('1988-09-14', '%Y-%m-%d'), '010-3816-2027', 'M', STR_TO_DATE('2020-12-13', '%Y-%m-%d'), NULL, '서울특별시 관악구', 'oI5MwX_'),
    ('member01015', 'center01', '정서연', STR_TO_DATE('2003-08-17', '%Y-%m-%d'), '010-9615-3249', 'F', STR_TO_DATE('2023-02-01', '%Y-%m-%d'), NULL, '서울특별시 서대문구', 'rW9FAh=$'),
    ('member01016', 'center01', '한민준', STR_TO_DATE('2002-04-25', '%Y-%m-%d'), '010-8408-5820', 'M', STR_TO_DATE('2024-03-25', '%Y-%m-%d'), NULL, '서울특별시 마포구', 'fZ4C%lZ>B'),
    ('member01017', 'center01', '장지현', STR_TO_DATE('2000-04-19', '%Y-%m-%d'), '010-2637-5280', 'F', STR_TO_DATE('2022-06-06', '%Y-%m-%d'), NULL, '서울특별시 강서구', 'zA7(b%J'),
    ('member01018', 'center01', '정민지', STR_TO_DATE('1992-09-20', '%Y-%m-%d'), '010-4146-8663', 'F', STR_TO_DATE('2022-10-14', '%Y-%m-%d'), STR_TO_DATE('2024-01-10', '%Y-%m-%d'), '서울특별시 서대문구', 'wE8oY+_hm@`J'),
    ('member01019', 'center01', '김기현', STR_TO_DATE('1993-10-24', '%Y-%m-%d'), '010-0432-0437', 'M', STR_TO_DATE('2023-10-15', '%Y-%m-%d'), NULL, '서울특별시 마포구', 'xS8vHWfe0'),
    ('member01020', 'center01', '이한섭', STR_TO_DATE('1988-08-18', '%Y-%m-%d'), '010-8407-9304', 'M', STR_TO_DATE('2024-03-03', '%Y-%m-%d'), NULL, '서울특별시 강서구', 'fI5PE+#'),
    ('member02001', 'center02', '정태환', STR_TO_DATE('1990-02-02', '%Y-%m-%d'), '010-5996-4491', 'M', STR_TO_DATE('2023-01-01', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'oI2SOo>C('),
    ('member02002', 'center02', '이수진', STR_TO_DATE('1982-10-18', '%Y-%m-%d'), '010-7249-9098', 'F', STR_TO_DATE('2023-02-03', '%Y-%m-%d'), NULL, '서울특별시 서초구', 'lL3vsV'),
    ('member02003', 'center02', '이가영', STR_TO_DATE('1991-03-03', '%Y-%m-%d'), '010-2919-4616', 'F', STR_TO_DATE('2023-03-01', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'xZ7@bU3N0N'),
    ('member02004', 'center02', '이수현', STR_TO_DATE('1990-03-25', '%Y-%m-%d'), '010-1631-8947', 'F', STR_TO_DATE('2022-01-15', '%Y-%m-%d'), STR_TO_DATE('2023-10-22', '%Y-%m-%d'), '서울특별시 강남구', 'bZ8ml&ID'),
    ('member02005', 'center02', '정준형', STR_TO_DATE('1985-07-12', '%Y-%m-%d'), '010-2976-4786', 'M', STR_TO_DATE('2021-12-05', '%Y-%m-%d'), NULL, '서울특별시 마포구', 'iB1cyuI6,f'),
    ('member02006', 'center02', '김성진', STR_TO_DATE('1992-09-18', '%Y-%m-%d'), '010-2224-2758', 'M', STR_TO_DATE('2022-02-01', '%Y-%m-%d'), NULL, '서울특별시 송파구', 'mV1Sli~XVJ0'),
    ('member02007', 'center02', '유선욱', STR_TO_DATE('1995-11-08', '%Y-%m-%d'), '010-7935-8385', 'M', STR_TO_DATE('2022-02-10', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'vZ9NB7'),
    ('member02008', 'center02', '김홍기', STR_TO_DATE('1988-04-30', '%Y-%m-%d'), '010-5848-8279', 'M', STR_TO_DATE('2021-11-20', '%Y-%m-%d'), STR_TO_DATE('2022-11-30', '%Y-%m-%d'), '서울특별시 서대문구-', 'vQ5jeV\\'),
    ('member02009', 'center02', '김진아', STR_TO_DATE('1997-01-19', '%Y-%m-%d'), '010-3863-7302', 'F', STR_TO_DATE('2022-01-23', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'dA5_E?'),
    ('member02010', 'center02', '최나원', STR_TO_DATE('1983-04-24', '%Y-%m-%d'), '010-1763-6065', 'F', STR_TO_DATE('2022-03-18', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'fV1+K*dFh'),
    ('member02011', 'center02', '진상기', STR_TO_DATE('1965-02-09', '%Y-%m-%d'), '010-7305-2237', 'M', STR_TO_DATE('2022-04-29', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'mC0VdByi%d'),
    ('member02012', 'center02', '김광수', STR_TO_DATE('1969-04-11', '%Y-%m-%d'), '010-3249-4279', 'M', STR_TO_DATE('2022-08-10', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'pR8zbyG#<'),
    ('member02013', 'center02', '박민아', STR_TO_DATE('1999-05-07', '%Y-%m-%d'), '010-2796-1394', 'F', STR_TO_DATE('2023-04-18', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'uD5xMc&l=/Fu'),
    ('member02014', 'center02', '김윤아', STR_TO_DATE('2001-03-04', '%Y-%m-%d'), '010-0906-6814', 'F', STR_TO_DATE('2023-05-19', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'xM3`kn@|,'),
    ('member02015', 'center02', '김철수', STR_TO_DATE('1982-09-15', '%Y-%m-%d'), '010-3815-6322', 'M', STR_TO_DATE('2021-10-05', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'sQ73!b7/gM'),
    ('member02016', 'center02', '박영희', STR_TO_DATE('1990-04-27', '%Y-%m-%d'), '010-8645-9825', 'F', STR_TO_DATE('2022-08-22', '%Y-%m-%d'), NULL, '서울특별시 송파구', 'aD8I2rb\kP5'),
    ('member02017', 'center02', '정수빈', STR_TO_DATE('1999-11-10', '%Y-%m-%d'), '010-1629-9140', 'F', STR_TO_DATE('2024-01-09', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'jB54e{hpMMtZ'),
    ('member02018', 'center02', '이지은', STR_TO_DATE('1985-03-22', '%Y-%m-%d'), '010-2637-0436', 'F', STR_TO_DATE('2023-11-30', '%Y-%m-%d'), NULL, '서울특별시 서초구', 'lL1$hN'),
    ('member02019', 'center02', '최성호', STR_TO_DATE('1996-08-09', '%Y-%m-%d'), '010-1870-7194', 'M', STR_TO_DATE('2021-06-18', '%Y-%m-%d'), NULL, '서울특별시 동작구', 'pX6+A589'),
    ('member02020', 'center02', '장미영', STR_TO_DATE('2003-05-18', '%Y-%m-%d'), '010-5805-2217', 'F', STR_TO_DATE('2022-03-07', '%Y-%m-%d'), STR_TO_DATE('2022-06-07', '%Y-%m-%d'), '서울특별시 강남구', 'iP0tpJgb*nAO');


INSERT INTO DB2024_Instructors (instructorid, centerid, name, birthday, phone, gender, join_date, quit_date, address)
VALUES 
    ('instructor01001', 'center01', '이준호', STR_TO_DATE('1986-01-01', '%Y-%m-%d'), '010-8222-3674', 'M', STR_TO_DATE('2020-08-18', '%Y-%m-%d'), NULL, '서울특별시 마포구'),
    ('instructor01002', 'center01', '김태우', STR_TO_DATE('1990-04-16', '%Y-%m-%d'), '010-3333-9999', 'M', STR_TO_DATE('2020-08-18', '%Y-%m-%d'), NULL, '서울특별시 용산구'),
    ('instructor01003', 'center01', '박민수', STR_TO_DATE('1994-04-08', '%Y-%m-%d'), '010-2345-6789', 'M', STR_TO_DATE('2021-03-15', '%Y-%m-%d'), NULL, '서울특별시 종로구'),
    ('instructor01004', 'center01', '최영진', STR_TO_DATE('1998-07-19', '%Y-%m-%d'), '010-3456-7890', 'M', STR_TO_DATE('2021-08-12', '%Y-%m-%d'), NULL, '서울특별시 중구'),
    ('instructor01005', 'center01', '김하은', STR_TO_DATE('1989-03-11', '%Y-%m-%d'), '010-4567-8901', 'F', STR_TO_DATE('2020-08-18', '%Y-%m-%d'), NULL, '서울특별시 은평구'),
    ('instructor01006', 'center01', '박지영', STR_TO_DATE('1991-05-28', '%Y-%m-%d'), '010-5678-9012', 'F', STR_TO_DATE('2021-03-15', '%Y-%m-%d'), NULL, '서울특별시 강서구'),
    ('instructor01007', 'center01', '이소연', STR_TO_DATE('1996-04-29', '%Y-%m-%d'), '010-6789-0123', 'F', STR_TO_DATE('2021-08-12', '%Y-%m-%d'), NULL, '서울특별시 동대문구'),
    ('instructor02001', 'center02', '정대윤', STR_TO_DATE('1995-02-11', '%Y-%m-%d'), '010-8901-2345', 'M', STR_TO_DATE('2022-01-23', '%Y-%m-%d'), NULL, '서울특별시 서초구'),
    ('instructor02002', 'center02', '송지훈', STR_TO_DATE('1998-07-12', '%Y-%m-%d'), '010-9012-3456', 'M', STR_TO_DATE('2022-01-23', '%Y-%m-%d'), NULL, '서울특별시 송파구'),
    ('instructor02003', 'center02', '한석준', STR_TO_DATE('1997-02-09', '%Y-%m-%d'), '010-0123-4567', 'M', STR_TO_DATE('2022-01-23', '%Y-%m-%d'), NULL, '서울특별시 강동구'),
    ('instructor02004', 'center02', '윤서준', STR_TO_DATE('1996-09-23', '%Y-%m-%d'), '010-4321-8765', 'M', STR_TO_DATE('2022-08-21', '%Y-%m-%d'), NULL, '서울특별시 관악구'),
    ('instructor02005', 'center02', '정다미', STR_TO_DATE('1999-11-24', '%Y-%m-%d'), '010-5432-9876', 'F', STR_TO_DATE('2022-01-23', '%Y-%m-%d'), NULL, '서울특별시 동작구'),
    ('instructor02006', 'center02', '한서연', STR_TO_DATE('1997-05-18', '%Y-%m-%d'), '010-4837-2734', 'F', STR_TO_DATE('2022-01-23', '%Y-%m-%d'), NULL, '서울특별시 금천구'),
    ('instructor02007', 'center02', '윤아리', STR_TO_DATE('2000-03-30', '%Y-%m-%d'), '010-3475-3478', 'F', STR_TO_DATE('2022-01-23', '%Y-%m-%d'), NULL, '서울특별시 구로구');
 

INSERT INTO DB2024_Employees (employeeid, centerid, name, birthday, phone, gender, join_date, quit_date, address, password, title)
VALUES 
    ('employee01001', 'center01', '최윤서', STR_TO_DATE('1998-03-24', '%Y-%m-%d'), '010-8888-7777', 'F', STR_TO_DATE('2022-09-30', '%Y-%m-%d'), NULL, '서울특별시 동대문구', 'dfgt1842', '총괄팀장'),
    ('employee01002', 'center01', '유선아', STR_TO_DATE('1994-02-25', '%Y-%m-%d'), '010-3456-2356', 'F', STR_TO_DATE('2024-02-08', '%Y-%m-%d'), NULL, '서울특별시 마포구', 'aj8ka234!', '데스크직원'),
    ('employee02001', 'center02', '김명섭', STR_TO_DATE('1990-02-18', '%Y-%m-%d'), '010-5685-9016', 'M', STR_TO_DATE('2022-01-24', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'lobe!989', '총괄팀장'),
    ('employee02002', 'center02', '이지연', STR_TO_DATE('1993-05-07', '%Y-%m-%d'), '010-4019-2106', 'F', STR_TO_DATE('2023-10-23', '%Y-%m-%d'), NULL, '서울특별시 강남구', 'victk12@1', '데스크직원');

INSERT INTO DB2024_Class_Types (ctypeid, sport, level)
VALUES 
    ('ctype00001', '헬스', '통합'),
    ('ctype00002', '필라테스', '초급'),
    ('ctype00003', '필라테스', '중급'),
    ('ctype00004', '필라테스', '고급'),
    ('ctype00005', '요가', '초급'),
    ('ctype00006', '요가', '중급'),
    ('ctype00007', '요가', '고급'),
    ('ctype01001', '호신술', '초급'),
    ('ctype01002', '호신술', '중급'),
    ('ctype01003', '호신술', '고급'),
    ('ctype02001', '수영', '초급'),
    ('ctype02002', '수영', '중급'),
    ('ctype02003', '수영', '고급');

INSERT INTO DB2024_Places (placeid, centerid, place_name, max_people)
VALUES 
    ('place01101', 'center01', '헬스장 1층', 100),
    ('place01201', 'center01', '필라테스 2층 1번방', 10),
    ('place01202', 'center01', '필라테스 2층 2번방', 10),
    ('place01203', 'center01', '요가 2층 3번방', 15),
    ('place01204', 'center01', '요가 2층 4번방', 15),
    ('place01301', 'center01', '호신술 3층', 10),
    ('place02101', 'center02', '헬스장 1층', 100),
    ('place02201', 'center02', '필라테스 2층 1번방', 8),
    ('place02202', 'center02', '필라테스 2층 2번방', 8),
    ('place02203', 'center02', '요가 2층 3번방', 15),
    ('place02204', 'center02', '요가 2층 4번방', 15),
    ('place022001', 'center02', '수영 지하 1층', 15);


INSERT INTO DB2024_Class_Times (ctimeid, day, hours)
VALUES 
    ('ctime00001', '월수금', '9시-10시'),
    ('ctime00002', '월수금', '2시-3시'),
    ('ctime00003', '월수금', '7시-8시'),
    ('ctime00004', '화목', '9시-10시'),
    ('ctime00005', '화목', '2시-3시'),
    ('ctime00006', '화목', '7시-8시'),
    ('ctime01001', '주말반', '1시-2시'),
    ('ctime02001', '주말반', '11시-12시'),
    ('ctime10000', '매일', '24시간');


INSERT INTO DB2024_Classes (classid, centerid, instructorid, ctypeid, placeid, ctimeid, class_name, class_fee, description, max_people, current_people)
VALUES 
    ('class01001', 'center01', 'instructor01001', 'ctype00001', 'place01101', 'ctime10000', '헬스', 70000, '헬스', 100, NULL),
    ('class01002', 'center01', 'instructor01002', 'ctype00002', 'place01202', 'ctime00001', '초보자를 위한 필라테스', 100000, '처음부터 차근차근 배우는 필라테스', 10, NULL),
    ('class01003', 'center01', 'instructor01002', 'ctype00003', 'place01202', 'ctime01001', '집중력 향상 필라테스', 200000, '필라테스로 느끼는 이너피스', 6, NULL),
    ('class01004', 'center01', 'instructor01003', 'ctype00004', 'place01201', 'ctime00003', '레벨업 필라테스', 200000, '한 단계 업그레이드 된 필라테스', 6, NULL),
    ('class01005', 'center01', 'instructor01004', 'ctype00005', 'place01203', 'ctime00005', '처음 시작하는 요가', 80000, '남녀노소 누구나 시작할 수 있도록 쉬운 동작들을 알려드려요.', 15, NULL),
    ('class01006', 'center01', 'instructor01004', 'ctype00006', 'place01203', 'ctime00006', '하루의 고됨을 풀어주는 요가', 80000, '고된 일상을 보내고 요가로 내 몸과 마음을 풀어보아요.', 10, NULL),
    ('class01007', 'center01', 'instructor01005', 'ctype00007', 'place01204', 'ctime02001', '내면을 관찰하는 요가', 100000, '마음의 고요가 필요할때, 조금 더 어려운 동작들을 알려드려요.', 10, NULL),
    ('class01008', 'center01', 'instructor01006', 'ctype01001', 'place01301', 'ctime00002', '몸과 체력을 지키는 호신술', 150000, '체력을 늘리고 호신술을 배우려는 분들을 위한 호신술', 10, NULL),
    ('class01009', 'center01', 'instructor01006', 'ctype01002', 'place01301', 'ctime00005', '내 몸 지키기의 시작, 중급 호신술', 150000, '본격적으로 배워보는 호신술', 10, NULL),
    ('class01010', 'center01', 'instructor01007', 'ctype01003', 'place01301', 'ctime00004', '마지막 호신술', 150000, '나를 지키기 위해, 호신술 고급 기술을 배워보아요.', 10, NULL),
    ('class02001', 'center02', 'instructor02001', 'ctype00001', 'place02201', 'ctime10000', '헬스', 60000, '헬스', 100, NULL),
    ('class02002', 'center02', 'instructor02002', 'ctype00002', 'place02201', 'ctime00004', '코어 강화 필라테스', 200000, '복부, 등, 엉덩이 근육을 강화하여 몸의 균형과 자세를 향상시킬 수 있어요.', 6, NULL),
    ('class02003', 'center02', 'instructor02002', 'ctype00003', 'place02201', 'ctime00005', '플로우 필라테스', 200000, '부드럽고 연속적인 동작을 통해 몸의 유연성과 조화를 개선하며 전체적인 웰빙을 증진시킬 수 있어요.', 6, NULL),
    ('class02004', 'center02', 'instructor02003', 'ctype00004', 'place02202', 'ctime00006', '파워 필라테스', 100000, '강도 높은 운동으로 전신의 힘과 스태미너를 증진시켜, 더욱 강하고 탄력적인 몸을 만드는 데 도움이 돼요.', 8, NULL),
    ('class02005', 'center02', 'instructor02004', 'ctype00005', 'place02203', 'ctime00001', '이너피스 요가', 100000, '명상과 요가 포즈를 조화롭게 결합하여 심신의 평화와 균형을 찾아가는 치유의 시간을 가질 수 있어요.', 10, NULL),
    ('class02006', 'center02', 'instructor02004', 'ctype00006', 'place02204', 'ctime00003', '조화로운 숨결 요가', 100000, '호흡과 움직임을 일치시켜 내면의 조화를 추구하고 전체적인 건강을 향상시켜요.', 15, NULL),
    ('class02007', 'center02', 'instructor02005', 'ctype00007', 'place02204', 'ctime01001', '밸런스 요가', 80000, '몸의 균형과 안정성을 향상시키는 다양한 포즈를 통해 육체적, 정신적 균형을 개선하는 데 중점을 둡니다.', 15, NULL),
    ('class02008', 'center02', 'instructor02006', 'ctype02001', 'place022001', 'ctime00001', '딥다이브 수영', 80000, '깊은 물에서의 다이빙 기술과 수중 자신감을 향상시키는 심화 훈련을 제공하여 수영 능력을 한 단계 업그레이드 할 수 있어요.', 15, NULL),
    ('class02009', 'center02', 'instructor02006', 'ctype02002', 'place022001', 'ctime00002', '아쿠아 파워 수영', 80000, '물속에서의 강력한 운동을 통해 전신의 힘과 지구력을 강화하며, 물의 저항을 이용한 효과적인 피트니스를 경험할 수 있어요.', 15, NULL),
    ('class02010', 'center02', 'instructor02007', 'ctype02003', 'place022001', 'ctime00003', '레벨업 수영', 80000, '기초 수영 기술을 체계적으로 배우고 익혀 다음 단계로 나아가기 위한 튼튼한 기반을 마련할 수 있어요.', 15, NULL);


INSERT INTO DB2024_Payments (payid, pay_type, pay_fee, date, status)
VALUES 
    ('pay01001', '현금', 200000, STR_TO_DATE('2023-12-20', '%Y-%m-%d'), '결제완료'),
    ('pay01002', '카드', 100000, STR_TO_DATE('2023-12-30', '%Y-%m-%d'), '결제완료'),
    ('pay01003', '카드', 200000, STR_TO_DATE('2022-01-10', '%Y-%m-%d'), '결제완료'),
    ('pay01004', NULL, 80000, NULL, '결제미완료'),
    ('pay01005', '현금', 100000, STR_TO_DATE('2023-03-20', '%Y-%m-%d'), '결제완료'),
    ('pay01006', '카드', 100000, STR_TO_DATE('2023-12-10', '%Y-%m-%d'), '결제완료'),
    ('pay01007', '현금', 150000, STR_TO_DATE('2024-02-08', '%Y-%m-%d'), '결제완료'),
    ('pay01008', '카드', 150000, STR_TO_DATE('2022-02-09', '%Y-%m-%d'), '결제완료'),
    ('pay01009', '카드', 150000, STR_TO_DATE('2024-02-09', '%Y-%m-%d'), '결제완료'),
    ('pay01010', '카드', 70000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay01011', '카드', 200000, STR_TO_DATE('2024-03-25', '%Y-%m-%d'), '결제완료'),
    ('pay01012', '카드', 200000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay01013', '카드', 80000, STR_TO_DATE('2024-03-27', '%Y-%m-%d'), '결제완료'),
    ('pay01014', '카드', 80000, STR_TO_DATE('2024-03-21', '%Y-%m-%d'), '결제완료'),
    ('pay01015', '현금', 100000, STR_TO_DATE('2024-03-21', '%Y-%m-%d'), '결제완료'),
    ('pay01016', '현금', 150000, STR_TO_DATE('2024-03-22', '%Y-%m-%d'), '결제완료'),
    ('pay01017', '카드', 150000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay01018', '현금', 150000, STR_TO_DATE('2024-03-28', '%Y-%m-%d'), '결제완료'),
    ('pay01019', '현금', 70000, STR_TO_DATE('2024-03-28', '%Y-%m-%d'), '결제완료'),
    ('pay01020', '카드', 100000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay01021', '현금', 200000, STR_TO_DATE('2024-03-25', '%Y-%m-%d'), '결제취소'),
    ('pay01022', '카드', 150000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay01023', NULL, 80000, NULL, '결제미완료'),
    ('pay01024', '카드', 150000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay01025', '현금', 150000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02001', '카드', 60000, STR_TO_DATE('2023-02-18', '%Y-%m-%d'), '결제완료'),
    ('pay02002', '현금', 60000, STR_TO_DATE('2023-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02003', '현금', 200000, STR_TO_DATE('2024-03-16', '%Y-%m-%d'), '결제완료'),
    ('pay02004', '카드', 100000, STR_TO_DATE('2024-03-24', '%Y-%m-%d'), '결제완료'),
    ('pay02005', '카드', 100000, STR_TO_DATE('2024-03-22', '%Y-%m-%d'), '결제취소'),
    ('pay02006', '현금', 80000, STR_TO_DATE('2024-03-11', '%Y-%m-%d'), '결제취소'),
    ('pay02007', '카드', 80000, STR_TO_DATE('2024-03-24', '%Y-%m-%d'), '결제완료'),
    ('pay02008', '카드', 80000, STR_TO_DATE('2024-03-28', '%Y-%m-%d'), '결제완료'),
    ('pay02009', '카드', 80000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02010', '현금', 60000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02011', '카드', 200000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02012', '현금', 200000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02013', '카드', 100000, STR_TO_DATE('2024-03-25', '%Y-%m-%d'), '결제완료'),
    ('pay02014', '현금', 100000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02015', '카드', 100000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02016', '카드', 80000, STR_TO_DATE('2024-03-17', '%Y-%m-%d'), '결제완료'),
    ('pay02017', '현금', 80000, STR_TO_DATE('2024-03-29', '%Y-%m-%d'), '결제완료'),
    ('pay02018', '카드', 80000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02019', '현금', 80000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02020', '카드', 60000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02021', '현금', 200000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료'),
    ('pay02022', '카드', 200000, STR_TO_DATE('2024-03-28', '%Y-%m-%d'), '결제완료'),
    ('pay02023', NULL, 100000, NULL, '결제미완료'),
    ('pay02024', '카드', 100000, STR_TO_DATE('2024-03-27', '%Y-%m-%d'), '결제완료'),
    ('pay02025', '카드', 100000, STR_TO_DATE('2024-03-30', '%Y-%m-%d'), '결제완료');


INSERT INTO DB2024_Attendings (attendid, memberid, payid, classid, date, status)
VALUES 
    ('attend01001', 'member01001', 'pay01001', 'class01003', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강가능'),
    ('attend01002', 'member01005', 'pay01002', 'class01002', STR_TO_DATE('2024-01-01', '%Y-%m-%d'), '수강완료'),
    ('attend01003', 'member01003', 'pay01003', 'class01003', STR_TO_DATE('2022-01-01', '%Y-%m-%d'), '수강완료'),
    ('attend01004', 'member01003', 'pay01004', 'class01005', STR_TO_DATE('2022-02-01', '%Y-%m-%d'), '수강불가능'),
    ('attend01005', 'member01015', 'pay01005', 'class01007', STR_TO_DATE('2023-04-01', '%Y-%m-%d'), '수강완료'),
    ('attend01006', 'member01006', 'pay01006', 'class01007', STR_TO_DATE('2024-03-01', '%Y-%m-%d'), '수강완료'),
    ('attend01007', 'member01006', 'pay01007', 'class01009', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01008', 'member01012', 'pay01008', 'class01008', STR_TO_DATE('2022-03-01', '%Y-%m-%d'), '수강완료'),
    ('attend01009', 'member01008', 'pay01009', 'class01010', STR_TO_DATE('2024-03-01', '%Y-%m-%d'), '수강완료'),
    ('attend01010', 'member01010', 'pay01010', 'class01001', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01011', 'member01011', 'pay01011', 'class01003', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01012', 'member01012', 'pay01012', 'class01004', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01013', 'member01013', 'pay01013', 'class01005', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01014', 'member01014', 'pay01014', 'class01006', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01015', 'member01015', 'pay01015', 'class01007', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01016', 'member01016', 'pay01016', 'class01008', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01017', 'member01017', 'pay01017', 'class01009', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01018', 'member01018', 'pay01018', 'class01010', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01019', 'member01019', 'pay01019', 'class01001', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01020', 'member01020', 'pay01020', 'class01002', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강불가능'),
    ('attend01021', 'member01009', 'pay01021', 'class01003', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강불가능'),
    ('attend01022', 'member01012', 'pay01022', 'class01009', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01023', 'member01013', 'pay01023', 'class01006', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강불가능'),
    ('attend01024', 'member01014', 'pay01024', 'class01008', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend01025', 'member01004', 'pay01025', 'class01010', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02001', 'member02001', 'pay02001', 'class02001', STR_TO_DATE('2024-03-01', '%Y-%m-%d'), '수강완료'),
    ('attend02002', 'member02002', 'pay02002', 'class02001', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강완료'),
    ('attend02003', 'member02003', 'pay02003', 'class02003', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02004', 'member02006', 'pay02004', 'class02004', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02005', 'member02008', 'pay02005', 'class02005', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강불가능'),
    ('attend02006', 'member02012', 'pay02006', 'class02007', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강불가능'),
    ('attend02007', 'member02014', 'pay02007', 'class02008', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02008', 'member02015', 'pay02008', 'class02010', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02009', 'member02019', 'pay02009', 'class02010', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02010', 'member02001', 'pay02010', 'class02001', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02011', 'member02004', 'pay02011', 'class02002', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02012', 'member02005', 'pay02012', 'class02003', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02013', 'member02007', 'pay02013', 'class02004', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02014', 'member02009', 'pay02014', 'class02005', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02015', 'member02010', 'pay02015', 'class02006', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02016', 'member02011', 'pay02016', 'class02007', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02017', 'member02013', 'pay02017', 'class02008', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02018', 'member02016', 'pay02018', 'class02009', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02019', 'member02017', 'pay02019', 'class02010', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02020', 'member02018', 'pay02020', 'class02001', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02021', 'member02020', 'pay02021', 'class02002', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02022', 'member02011', 'pay02022', 'class02003', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02023', 'member02004', 'pay02023', 'class02004', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강불가능'),
    ('attend02024', 'member02005', 'pay02024', 'class02005', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중'),
    ('attend02025', 'member02012', 'pay02025', 'class02006', STR_TO_DATE('2024-04-01', '%Y-%m-%d'), '수강중');

-- For 결제미완료 and 결제취소 (both result in 수강 불가능)
UPDATE DB2024_Attendings
SET status = '수강불가능'
WHERE payid IN (
    SELECT payid FROM DB2024_Payments WHERE status IN ('결제미완료', '결제취소')
);

-- For 결제 완료 + 아직 수강신청한 "달"이 안됨 (result in 수강가능)
UPDATE DB2024_Attendings
SET status = '수강가능'
WHERE payid IN (
    SELECT payid FROM DB2024_Payments WHERE status = '결제완료'
) AND DATE_FORMAT(date, '%Y-%m') > '2024-04';

-- For 결제 완료 + 수강신청한 "달"임 (result in 수강중)
UPDATE DB2024_Attendings
SET status = '수강중'
WHERE payid IN (
    SELECT payid FROM DB2024_Payments WHERE status = '결제완료'
) AND DATE_FORMAT(date, '%Y-%m') = '2024-04';

-- For 결제 완료 + 수강신청한 "달"을 넘었음 (result in 수강완료)
UPDATE DB2024_Attendings
SET status = '수강완료'
WHERE payid IN (
    SELECT payid FROM DB2024_Payments WHERE status = '결제완료'
) AND DATE_FORMAT(date, '%Y-%m') < '2024-04';



-- #1 인덱스 

-- 수강 목록 조회 시 회원 ID에 기반한 검색 속도 향상
-- 결제 내역을 회원 ID로 빠르게 찾기 위한 인덱스. 
-- 실제 DB2024_Payments 테이블에 memberid가 없으므로, DB2024_Attendings에서 이를 활용
CREATE INDEX idx_memberid ON DB2024_Attendings(memberid);

-- 수강 목록 조회 시 수업 ID에 기반한 검색 속도 향상
CREATE INDEX idx_classid ON DB2024_Attendings(classid);

-- 결제 상태별 조회 시 결제 상태에 기반한 검색 속도 향상
CREATE INDEX idx_status ON DB2024_Payments(status);

-- 결제 ID를 기반으로 하는 조회가 빈번
CREATE INDEX idx_payid ON DB2024_Attendings(payid);

-- 강사 이름 검색 시 검색 속도 향상
CREATE INDEX idx_instructor_name ON DB2024_Instructors(name);

-- #2 뷰

-- 결제 내역 조회 뷰
CREATE VIEW DB2024_V_Payment AS
SELECT 
    m.memberid,
    m.name AS member_name,
    p.payid,
    p.pay_type,
    p.pay_fee,
    p.date,
    p.status
FROM DB2024_Payments p
JOIN DB2024_Attendings a ON p.payid = a.payid
JOIN DB2024_Members m ON a.memberid = m.memberid
ORDER BY p.status;

-- 회원 개인정보 조회 뷰
CREATE VIEW DB2024_V_MembersInfo AS
SELECT memberid, centerid, phone, name, gender, join_date, quit_date
FROM DB2024_members;

-- 수강 목록 조회 뷰 
CREATE VIEW DB2024_V_AttendingDetails AS
SELECT 
    a.attendid,
    a.memberid,
    m.name AS member_name,
    a.classid,
    c.class_name,
    a.date,
    a.status AS attending_status,
    p.payid,  -- 결제 ID 추가
    p.status AS payment_status  -- 결제 상태 추가
FROM DB2024_Attendings a
JOIN DB2024_Members m ON a.memberid = m.memberid
JOIN DB2024_Classes c ON a.classid = c.classid
JOIN DB2024_Payments p ON a.payid = p.payid;


-- 수업 정보 조회 뷰
CREATE VIEW DB2024_ClassDetails AS
SELECT 
    c.classid,
    c.centerid,
    ce.name AS center_name,
    c.instructorid,
    i.name AS instructor_name,
    c.ctypeid,
    ct.sport,
    c.placeid,
    c.ctimeid,
    t.day,
    c.class_name,
    c.class_fee,
    c.description,
    c.max_people,
    c.current_people
FROM 
    DB2024_Classes c
LEFT JOIN DB2024_Instructors i USE INDEX (idx_instructor_name) ON c.instructorid = i.instructorid
LEFT JOIN DB2024_Class_Types ct ON c.ctypeid = ct.ctypeid
LEFT JOIN DB2024_Class_Times t ON c.ctimeid = t.ctimeid
LEFT JOIN DB2024_Centers ce ON c.centerid = ce.centerid;

-- DROP DATABASE DB2024Team06;
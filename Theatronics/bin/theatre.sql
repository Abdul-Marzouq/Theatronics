CREATE DATABASE `Theatronics` 
USE `Theatronics`;

DROP TABLE IF EXISTS `Tickets`;
DROP TABLE IF EXISTS `Employees`;
DROP TABLE IF EXISTS `Shows`;
DROP TABLE IF EXISTS `Movies`;
DROP TABLE IF EXISTS `Screens`;


CREATE TABLE `Movies` (
  `Movie_ID` int(11) NOT NULL,
  `Movie_Name` varchar(40) NOT NULL,
  `No_of_Runs` int(11) DEFAULT '0',
  `Max_runs` int(11) DEFAULT NULL,
  `Collection` bigint(20) DEFAULT NULL,
  `Movie_Type` varchar(40) DEFAULT NULL,
  `Start_Date` date DEFAULT NULL,
  `End_Date` date DEFAULT NULL,
  PRIMARY KEY (`Movie_ID`)
);

CREATE TABLE `Screens` (
  `Screen_ID` int(11) NOT NULL,
  `Screen_Name` varchar(40) DEFAULT NULL,
  `Screen_Type` varchar(40) DEFAULT NULL,
  `No_of_Seats` int(11) DEFAULT NULL,
  PRIMARY KEY (`Screen_ID`)
);

CREATE TABLE `Shows` (
  `Show_ID` int(11) NOT NULL,
  `Date` date DEFAULT NULL,
  `Time` varchar(10) DEFAULT NULL,
  `Seats_Left` int(11) DEFAULT NULL,
  `Screen_ID` int(11) DEFAULT NULL,
  `Movie_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`Show_ID`),
  UNIQUE KEY `Unique Screen Movie` (`Date`,`Time`,`Screen_ID`),
  KEY `Hosted on` (`Screen_ID`),
  KEY `Premiered at` (`Movie_ID`),
  CONSTRAINT `Hosted on` FOREIGN KEY (`Screen_ID`) REFERENCES `Screens` (`Screen_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Premiered at` FOREIGN KEY (`Movie_ID`) REFERENCES `Movies` (`Movie_ID`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `Employees` (
  `Emp_ID` int(11) NOT NULL,
  `Emp_Name` varchar(40) NOT NULL,
  `Emp_Category` varchar(40) DEFAULT NULL,
  `Emp_Passwd` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`Emp_ID`)
);

CREATE TABLE `Tickets` (
  `Show_ID` int(11) NOT NULL,
  `Emp_ID` int(11) NOT NULL,
  `Ticket_ID` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `Seat_Num` int(11) NOT NULL,
  KEY `Booked by` (`Emp_ID`),
  CONSTRAINT `Booked by` FOREIGN KEY (`Emp_ID`) REFERENCES `Employees` (`Emp_ID`),
  CONSTRAINT `Tickets` FOREIGN KEY (`Show_ID`) REFERENCES `Shows` (`Show_ID`) ON DELETE CASCADE ON UPDATE CASCADE
);

insert  into `Movies`(`Movie_ID`,`Movie_Name`,`No_of_Runs`,`Max_runs`,`Collection`,`Movie_Type`,`Start_Date`,`End_Date`) values 
(1,'Bigil',0,0,0,'2D','2019-10-25','2019-11-05'),
(2,'The Lion King',0,0,0,'3D','2019-10-30','2019-11-10');

insert  into `Screens`(`Screen_ID`,`Screen_Name`,`Screen_Type`,`No_of_Seats`) values 
(1,'ScreenOne','3D',100),
(2,'ScreenTwo','2D',100);

insert  into `Shows`(`Show_ID`,`Date`,`Time`,`Seats_Left`,`Screen_ID`,`Movie_ID`) values 
(1,'2019-10-30','09:00',50,1,1),
(2,'2019-10-30','09:00',60,2,2);

insert  into `Employees`(`Emp_ID`,`Emp_Name`,`Emp_Category`,`Emp_Passwd`) values 
(1,'admin','Manager','root'),
(2,'vicky','Cashier','123'),
(3,'flake','Cashier','234');

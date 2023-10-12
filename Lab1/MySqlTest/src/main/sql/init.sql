drop database if exists exam;
create database exam;
use exam;

drop table if exists room;
create table room (
  kdno      int(4),
  kcno      int(1),
  ccno      int(2),
  kdname    varchar(10),
  exptime   varchar(20),
  papername varchar(10),
  primary key (kdno, kcno, ccno)
);

drop table if exists student;
create table student (
  registno    varchar(7),
  name        varchar(20),
  kdno        int(4),
  kcno        int(1),
  ccno        int(2),
  seat        int(2),
  primary key (registno),
  foreign key (kdno, kcno, ccno) references room (kdno, kcno, ccno)
);
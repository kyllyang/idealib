drop database if exists myserver;
create database myserver;
alter database myserver default character set utf8 collate utf8_general_ci;
use myserver;
source E:\workspace\myserver\standard\doc\myserver.sql

alter table sys_user_role convert to character set utf8 collate utf8_general_ci;
source d:\myserver.sql
mysqldump -uroot -proot myserver > e:\myserver.sql

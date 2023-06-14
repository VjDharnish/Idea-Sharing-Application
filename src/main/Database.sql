use tedboss;

create table credentials (emailid varchar(30),
password varchar(20)
);

create table userinfo(emailid varchar(30)primary key,
name varchar(20),
location varchar(20),
bio TEXT);

alter table userinfo add column posts int  default 0;
alter table userinfo drop column location;

create table userposts(emailid varchar(20), postid int );
drop table userposts;
drop table postinfo;
create table postinfo (postid int auto_increment primary key, emailid varchar(20),
title varchar(80), 
content text,
image text,
likes int default 0
);
create table topic(topic_name varchar(20));
select * from postinfo;
desc userposts;
select * from userposts;
alter table topic rename   topics;
create table topics (name varchar(20), postid int );
create table likes(emailid varchar(20),postid int, primary key(emailid,postid));
create table bookmarks(emailid varchar(20),postid int, primary key(emailid,postid));

show tables;
select * from userinfo;
select * from credentials;
select * from topics;
alter table userinfo add column profile_pic text after bio;
truncate table credentials;
select * from user_topics;
select * from userposts;
select * from post_topic;
create table user_topics(emailid varchar(20),topic varchar(20));
truncate table userinfo;
insert into topics values('Health'),('Money & Investment'),('Love & Relationship'),('Career'),('Marketing'),('Time Management'),
('Mindfulness'),('Music'),('Philosophy'),('Travel');

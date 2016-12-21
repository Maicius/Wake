/*****New database*************/
create database clock;
use clock;
create table appuser(
username varchar(255) primary key,
password varchar(255),
nickname varchar(255),
brief_intro varchar(255)
);

create table getuptime(
	time_id int primary key,
	username varchar(255) references appuser(username),
	up_time Timestamp
);
insert into appuser(username, password, nickname) values("18030848367","110110","麦子");

select up_time from getuptime where username = "麦子";
insert into getuptime(time_id, username) values(1, 18996720676);

create table friend_list(
frineds_id int not null AUTO_INCREMENT,
user_id varchar(255),
friends varchar(255),
primary key(frineds_id)
);
alter table friend_list add constraint FK_b_c foreign key(friends) references appuser(username);
alter table friend_list add constraint FK_a_b foreign key(friends) references appuser(username);

/*example:*/
/*为用户18996720676增加好友*/
insert into friend_list(user_id, friends) values(18996720676, 18030848367);
/*查询18996720676的好友*/
select friends from friend_list where user_id = 18996720676;
/*删除好友18030848367*/
delete from friend_list where user_id = 18996720676 and frineds = 18030848367;

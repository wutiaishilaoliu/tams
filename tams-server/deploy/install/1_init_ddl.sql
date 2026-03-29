set character set utf8mb4;
create table t_classroom
(
	id bigint auto_increment
		primary key,
	name varchar(50) default '' not null comment '名称',
	type tinyint default 1 not null comment '类型: 1普通教室 2多媒体教室 3实验室 4操场',
	capacity int default 0 not null comment '容量(人数)',
	available_time varchar(200) default '' not null comment '可用时间段(格式: 08:00-12:00,14:00-18:00)',
	enable_state int unsigned default 1 not null comment '停启用状态, 1启用 2停用'
)
comment '教室';

create table t_color
(
	id bigint auto_increment comment 'id'
		primary key,
	name varchar(30) default '' not null comment '名称',
	value char(7) default '' not null comment '值'
)
comment '颜色';

create table t_course
(
	id bigint auto_increment comment 'id'
		primary key,
	name varchar(30) default '' not null comment '名称',
	enable_state int unsigned default 1 not null comment '停启用状态，1启用 2停用',
	duration int unsigned null comment '课程时长，单位分钟',
	background_color char(7) default '' not null comment '背景颜色'
)
comment '课程';

create table t_course_scheduling
(
	id bigint auto_increment comment 'id'
		primary key,
	classroom_id bigint default 0 not null comment '教室id',
	course_id bigint default 0 not null comment '课程id',
	teacher_id bigint default 0 not null comment '教师id',
	date date null comment '日期',
	attend_time time null comment '上课时间',
	finish_time time null comment '下课时间'
)
comment '排课';

create table t_school
(
	id bigint auto_increment comment 'id'
		primary key,
	名称 varchar(50) default '' not null comment '名称'
)
comment '学校';

create table t_teacher
(
	id bigint auto_increment comment 'id'
		primary key,
	name varchar(10) default '' not null comment '姓名',
	username varchar(50) default '' not null comment '登录账号',
	password varchar(100) default '' not null comment '登录密码',
	enable_state int unsigned default 1 not null comment '停启用状态，1启用 2停用'
)
comment '教师';

create table t_class
(
	id bigint auto_increment comment 'id'
		primary key,
	name varchar(80) default '' not null comment '班级名称(如:大一计算机与科学技术一班)',
	grade varchar(20) default '' not null comment '年级: 大一/大二/大三/大四',
	major varchar(50) default '' not null comment '专业',
	class_number varchar(20) default '' not null comment '班号: 一班/二班/三班',
	enable_state int unsigned default 1 not null comment '停启用状态，1启用 2停用'
)
comment '班级';

create table t_student
(
	id bigint auto_increment comment 'id'
		primary key,
	name varchar(10) default '' not null comment '姓名',
	class_id bigint default 0 not null comment '班级id',
	username varchar(50) default '' not null comment '登录账号',
	password varchar(100) default '' not null comment '登录密码',
	enable_state int unsigned default 1 not null comment '停启用状态，1启用 2停用'
)
comment '学生';

create table t_course_scheduling_class
(
	id bigint auto_increment comment 'id'
		primary key,
	course_scheduling_id bigint default 0 not null comment '排课id',
	class_id bigint default 0 not null comment '班级id'
)
comment '排课班级关联';


-- "tutoring system" database
drop database ts;
create database ts;
use ts;

create table `student` (
	`id` int auto_increment,
	`fname` varchar(50),
	`lname` varchar(50),
	`username` varchar(20),
	`password` varchar(20),

	primary key(`id`)
);

create table `class` (
	`id` int auto_increment,
	`name` varchar(50),

	primary key(`id`)
);

create table `enrolled in class` (
	`student id` int,
	`class id` int,

	primary key(`student id`, `class id`),

	foreign key(`student id`) references `student`(`id`) on delete cascade,
	foreign key(`class id`) references `class`(`id`) on delete cascade
);

create table `lesson` (
	`id` int auto_increment,
	-- name of lesson, for example: counting, comparisons, addition
	`name` varchar(50),
	`class id` int,
	-- if false, a new problem will be randomly generated after an incorrect answer is submitted
	`allow multiple attempts` boolean,
	-- identify the rules for how to generate random problems, number of choices for answer, how to display problem description, etc. For now, we can just have all the problems in a lesson be of the same type.
	`problem type` varchar(10),
	-- number of problems that need to be solved to complete the lesson
	`number of problems` tinyint,

	primary key(`id`),

	foreign key(`class id`) references `class`(`id`) on delete cascade
);

create table `prereq lesson` (
	`prereq lesson id` int,
	`lesson id` int,

	primary key(`prereq lesson id`, `lesson id`),

	foreign key(`prereq lesson id`) references `lesson`(`id`) on delete cascade,
	foreign key(`lesson id`) references `lesson`(`id`) on delete cascade
);

create table `lesson progress` (
	`student id` int,
	`lesson id` int,
	-- number of problems currently solved
	`progress` tinyint,

	primary key(`student id`, `lesson id`),

	foreign key(`student id`) references `student`(`id`) on delete cascade,
	foreign key(`lesson id`) references `lesson`(`id`) on delete cascade
	
);

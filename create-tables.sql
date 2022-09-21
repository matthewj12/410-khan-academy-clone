-- "tutoring system" database
drop database ts;
create database ts;
use ts;

create table `student` (
	`id` int auto_increment NOT NULL,
	`fname` varchar(50) NOT NULL,
	`lname` varchar(50) NOT NULL,
	`username` varchar(20) NOT NULL,
	`password` varchar(20) NOT NULL,

	primary key(`id`)
);

create table `class` (
	`id` int auto_increment NOT NULL,
	`name` varchar(50) NOT NULL,

	primary key(`id`)
);

create table `enrolled in class` (
	`student id` int NOT NULL,
	`class id` int NOT NULL,

	primary key(`student id`, `class id`),

	foreign key(`student id`) references `student`(`id`) on delete cascade,
	foreign key(`class id`) references `class`(`id`) on delete cascade
);

create table `lesson` (
	`id` int auto_increment NOT NULL,
	-- name of lesson, for example: counting, comparisons, addition
	`name` varchar(50) NOT NULL,
	`class id` int NOT NULL,
	-- if false, a new problem will be randomly generated after an incorrect answer is submitted
	`allow multiple attempts` boolean NOT NULL,
	-- identify the rules for how to generate random problems, number of choices for answer, how to display problem description, etc. For now, we can just have all the problems in a lesson be of the same type.
	`problem type` varchar(10) NOT NULL,
	-- number of problems that need to be solved to complete the lesson
	`number of problems` tinyint NOT NULL,

	primary key(`id`),

	foreign key(`class id`) references `class`(`id`) on delete cascade
);

create table `prereq lesson` (
	`prereq lesson id` int NOT NULL,
	`lesson id` int NOT NULL,

	primary key(`prereq lesson id`, `lesson id`),

	foreign key(`prereq lesson id`) references `lesson`(`id`) on delete cascade,
	foreign key(`lesson id`) references `lesson`(`id`) on delete cascade
);

create table `lesson progress` (
	`student id` int NOT NULL,
	`lesson id` int NOT NULL,
	-- number of problems currently solved
	`progress` tinyint NOT NULL,

	primary key(`student id`, `lesson id`),

	foreign key(`student id`) references `student`(`id`) on delete cascade,
	foreign key(`lesson id`) references `lesson`(`id`) on delete cascade
	
);

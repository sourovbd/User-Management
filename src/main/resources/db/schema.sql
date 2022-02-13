create table if not exists user_credential (
     id bigint NOT NULL auto_increment,
     employee_id varchar(255) not null,
     password varchar(255),
     active bit,
     roles varchar(255) not null,
     primary key (id)
) engine=InnoDB;
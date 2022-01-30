INSERT INTO user_credential(id,employee_id,password,role)
VALUES(1,'012518','$2a$12$XMG3cM8IzAtwe3NsWn/HRuWmvUJ3YNzDMOCEysSoonoDihx6Dsysi','admin');

create table user (
      id bigint not null,
      business_unit varchar(255),
      department varchar(255),
      designation varchar(255),
      email_address varchar(255),
      employee_id bigint not null,
      role varchar(255),
      primary key (id)
) engine=InnoDB
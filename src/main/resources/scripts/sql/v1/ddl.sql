INSERT INTO user_credential(id, employee_id, password, active, roles)
VALUES(1,'012518','$2a$12$XMG3cM8IzAtwe3NsWn/HRuWmvUJ3YNzDMOCEysSoonoDihx6Dsysi', true, 'SYS_ADMIN');

INSERT INTO user_credential(id, employee_id, password, active, roles)
VALUES(2,'012519','$2a$12$tZLPGLFWrBMUg.k6u5.BlOvpCTm9YT9FOnoEwVIOchLigMVc2PR2G',true,'EMPLOYEE');

INSERT INTO user_credential(id, employee_id, password, active, roles)
VALUES(3,'012517','$2a$12$tZLPGLFWrBMUg.k6u5.BlOvpCTm9YT9FOnoEwVIOchLigMVc2PR2G',true,'ORG_SETUP');

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
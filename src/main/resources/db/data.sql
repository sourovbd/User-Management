
INSERT INTO user_credential (id, employee_id, password, active, roles)
VALUES (1,'012518','$2a$12$XMG3cM8IzAtwe3NsWn/HRuWmvUJ3YNzDMOCEysSoonoDihx6Dsysi', true, 'SYS_ADMIN');

INSERT INTO user_credential(id, employee_id, password, active, roles)
VALUES(2,'012519','$2a$12$tZLPGLFWrBMUg.k6u5.BlOvpCTm9YT9FOnoEwVIOchLigMVc2PR2G',true,'EMPLOYEE');

INSERT INTO user_credential(id, employee_id, password, active, roles)
VALUES(3,'012517','$2a$12$tZLPGLFWrBMUg.k6u5.BlOvpCTm9YT9FOnoEwVIOchLigMVc2PR2G',true,'ORG_SETUP');

INSERT INTO user (id, business_unit, department, designation, email_address, employee_id, roles, user_credential_id)
VALUES (1,"A1Polymar","Accounts","AGM","test@gmail.com","012518","SYS_ADMIN",1);

INSERT INTO user (id, business_unit, department, designation, email_address, employee_id, roles, user_credential_id)
VALUES (2,"AES","Development","CTO","test18@gmail.com","012519","EMPLOYEE",2);

INSERT INTO user (id, business_unit, department, designation, email_address, employee_id, roles, user_credential_id)
VALUES (3,"IT","Maintenance","IT","test2@gmail.com","012517","ORG_SETUP",3);
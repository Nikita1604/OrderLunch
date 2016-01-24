use `order_lunch_db`;

insert into user_role(type)
values ('USER');

insert into user_role(type)
values ('ADMIN');

insert into user(login, password, user_name, e_mail, state, deposit_id, company)
values ('nikita1604', '1111', 'Nikita Pischik', 'pnvik96@gmail.com', 'Active', '1', 'CompCorp');

insert into deposit(user_id, invoice, tomorrow_cost, residue)
values(1, 0, 0, 0);


insert into user_user_role (user_id, user_role_id)
	select user.id, role.id from user user, user_role role
    where user.login = 'Nikita1604' and role.type = 'USER';

insert into user_user_role (user_id, user_role_id)
	select user.id, role.id from user user, user_role role
    where user.login = 'Nikita1604' and role.type = 'ADMIN';
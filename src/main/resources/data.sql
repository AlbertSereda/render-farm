insert into Clients (login, password)
values ('root', 'root');

insert into Tasks (name, id_Client, status)
values ('first Task', 1, 'COMPLETE');

insert into status_history (id_client, id_Task, status, date)
values (1, 1, 1, now());

insert into status_history (id_client, id_Task, status, date)
values (1, 1, 0, now());
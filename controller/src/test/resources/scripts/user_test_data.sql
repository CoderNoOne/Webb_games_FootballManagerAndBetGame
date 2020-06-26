insert into users (username, email, enabled, gender, password, photo_url)
values ('Alaba',
        'alaba@gmail.com',
        1,
        'MALE',
        '{noop}Alaba123',
        'www.photoUrl.com');

insert into authorities
values ('Alaba', 'ROLE_USER');

insert into users (username, email, enabled, gender, password, photo_url)
values ('Admin',
        'admin@gmail.com',
        1,
        'MALE',
        '{noop}Admin123',
        'www.photoUrl.com');

insert into authorities
values ('Admin', 'ROLE_ADMIN');

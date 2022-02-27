use car_rental_test;
insert into car (id, brand, model, available, deleted) values
            (1, 'BMW', 'X3', 1, 0),
            (2, 'Ford', 'galaxy', 1, 0),
            (3, 'Opel', 'Insignia', 1, 0),
            (4, 'BMW', 'F11', 1, 0),
            (5, 'Mercedes', 'S500', 1, 0),
            (6, 'Ford', 'Fiesta', 0, 1),
            (7, 'Toyota', 'Avensis', 0, 0),
            (8, 'BMW', 'E92', 1, 0);

insert into car_details (id, vin, color, registration_year, price, segment,
    doors, seats, registration_number, mileage, inspection, insurance, fuel,
    average_consumption, transmission) values
    (1,'12345678912345678', 'blue', 2018, 110.0, 'SUV', 5, 5, 'PSL 1234', 120000,
    '2022-09-19', '2022-09-19', 'diesel', 10.5, 'automatic'),
    (2,'ABC123ABC123ABC12', 'green', 2015, 95.0, 'VAN', 5, 5, 'PO 1234', 135000,
    '2022-09-19', '2022-09-19', 'petrol', 8.5, 'manual'),
    (3,'5NMSH13E89H310439', 'brown', 2017, 95.0, 'sedan', 5, 5, 'SZ 1234', 166000,
    '2022-09-19', '2022-09-19', 'diesel', 8.2, 'manual'),
    (4,'1FTNF21L53EA67192', 'black', 2013, 115.0, 'combi', 5, 5, 'GB 12345', 155000,
    '2022-09-19', '2022-09-19', 'petrol', 9.8, 'automatic'),
    (5,'1G2ZH35N074288387', 'black', 2020, 150.0, 'limousine', 5, 5, 'GD 12345', 133000,
    '2022-09-19', '2022-09-19', 'diesel', 12.5, 'automatic'),
    (6,'5FNRL18943B012178', 'red', 2016, 80.0, 'hatchback', 5, 5, 'PSL 9692', 111000,
    '2022-09-19', '2022-09-19', 'petrol', 7.2, 'manual'),
    (7,'NM0GE9G71E1134666', 'grey', 2018, 100.0, 'sedan', 5, 5, 'PSL 9692', 100000,
    '2022-09-19', '2022-09-19', 'diesel', 8.3, 'manual'),
    (8,'2C4RDGCGXDR882416', 'blue', 2011, 100.0, 'coupe', 5, 5, 'PSL 9692', 950000,
    '2022-09-19', '2022-09-19', 'petrol', 9.2, 'manual');

update car set car_details_id = 1 where id=1;
update car set car_details_id = 2 where id=2;
update car set car_details_id = 3 where id=3;
update car set car_details_id = 4 where id=4;
update car set car_details_id = 5 where id=5;
update car set car_details_id = 6 where id=6;
update car set car_details_id = 7 where id=7;
update car set car_details_id = 8 where id=8;

insert into rental (id, city, country, phone, post_code, street, deleted) values
        (1, 'Poznan', 'Poland', '997 997 997', '61-810', 'Poznanska 11', 0),
        (2, 'Warszawa', 'Poland', '000 666 111', '01-122', 'Warszawska 11', 0),
        (3, 'Gdynia', 'Poland', '112 112 112', '32-500', 'Gdynska 11', 0);

insert into rental_cars (rental_id, cars_id) values
        (1, 1), (1, 2), (1, 3), (2, 4), (2, 5), (2, 6), (2, 8);

insert into employee (id, employment_position, first_name, last_name, salary_per_hour, deleted) values
        (1, 'bigboss', 'Patryk', 'Chojnacki', 120, 0),
        (2, 'manager', 'Julka', 'Pee', 45, 0),
        (3, 'dealer', 'Kaska', 'Chmiel', 13, 0),
        (4, 'dealer', 'Slawek', 'Petarda', 15, 0),
        (5, 'dealer', 'Igor', 'Cykor', 14, 0),
        (6, 'dealer', 'Emily', 'Gilmore', 15, 1),
        (7, 'dealer', 'Patryk', 'Man', 17, 0),
        (8, 'dealer', 'Spider', 'Man', 14, 0);

insert into rental_employees (rental_id, employees_id) values
        (1,1), (1,2), (1,3), (1,4), (2,5), (2,6), (3,7), (3,8);

insert into users(id, birth, email, first_name, last_name, password, activated, deleted) values
        (1, '1996-06-12', 'penelopa@bigczopa.pl', 'Penelopa', 'Czopa', 'kutasnica123', 1, 0),
        (2, '1990-10-13', 'kolcz@gmail.pl', 'Wojtek', 'Kolcz', 'kutaadca123', 1, 0),
        (3, '1992-06-14', 'petrigro@gmail.pl', 'Petter', 'Sweter', 'kudsadnica123', 0, 0),
        (4, '1999-03-15', 'email@email.pl', 'Diho', 'Orangutan', 'kutaasdsad123', 1, 0);

insert into rents(id, comment, confirmed, start, end, final_price, returned, car_id, user_id, deleted) values
        (1, 'Everything ok', 1, '2021-05-15T12:30:00', '2021-05-25T12:30:00', 1100, 1, 1, 1, 0),
        (2, 'my comment', 0, '2022-02-05T12:30:00', '2022-02-15T12:30:00', 780, 0, 5, 1, 0),
        (3, 'my comment 3', 1, '2021-02-20T12:30:00', '2021-02-28T12:30:00', 800, 1, 5, 2, 0),
        (4, 'my comment 4', 1, '2021-02-15T12:30:00', '2021-02-24T12:30:00', 1200, 1, 8, 3, 0),
        (5, 'my comment 5', 0, '2022-06-15T12:30:00', '2022-06-22T12:30:00', 1000, 0, 2, 1, 0),
        (6, 'my comment 6', 1, '2021-07-15T12:30:00', '2021-07-27T12:30:00', 1300, 1, 3, 2, 0),
        (7, 'my comment 7', 1, '2021-09-01T12:30:00', '2021-09-22T12:30:00', 970, 1, 4, 3, 0),
        (8, 'my comment 8', 0, '2022-05-10T12:30:00', '2022-05-18T12:30:00', 999, 0, 1, 4, 0),
        (9, 'my comment 9', 1, '2021-10-15T12:30:00', '2021-10-27T12:30:00', 888, 1, 2, 4, 0),
        (10, 'my comment 10', 1, '2021-11-09T12:30:00', '2021-11-23T12:30:00', 777, 1, 3, 1, 0),
        (11, 'my comment 11', 0, '2022-06-06T12:30:00', '2022-06-19T12:30:00', 666, 0, 8, 2, 0),
        (12, 'my comment 12', 1, '2021-06-06T12:30:00', '2021-02-19T12:30:00', 555, 1, 1, 3, 0),
        (13, 'my comment 13', 1, '2021-09-01T12:30:00', '2021-09-22T12:30:00', 970, 1, 4, 3, 1);

insert into return_report(id, damaged, note, car_id, employee_id) values
        (1, 0, 'note 1', 1, 5),
        (2, 1, 'bumper cracked 2', 5, 3),
        (3, 0, 'note 3', 5, 3),
        (4, 0, 'note 4', 8, 3),
        (5, 0, 'note 5', 2, 7),
        (6, 0, 'note 6', 3, 8),
        (7, 0, 'note 7', 4, 8),
        (8, 0, 'note 8', 1, 4),
        (9, 1, 'paint scratch on left doors 9', 2, 6),
        (10, 0, 'note 10', 3, 7),
        (11, 0, 'note 11', 8, 2),
        (12, 0, 'note 12', 1, 1),
        (13, 0, 'note 12', 1, 1);

update rents set report_id = 1 where id=1;
update rents set report_id = 2 where id=2;
update rents set report_id = 3 where id=3;
update rents set report_id = 4 where id=4;
update rents set report_id = 5 where id=5;
update rents set report_id = 6 where id=6;
update rents set report_id = 7 where id=7;
update rents set report_id = 8 where id=8;
update rents set report_id = 9 where id=9;
update rents set report_id = 10 where id=10;
update rents set report_id = 11 where id=11;
update rents set report_id = 12 where id=12;
update rents set report_id = 13 where id=13;

insert into users_rents(user_id, rents_id) values
        (1,1), (1,2), (2,3), (3,4), (1,5), (2,6), (3,7), (4,8), (4,9), (1,10), (2,11), (3, 12);

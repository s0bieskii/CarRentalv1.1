insert into car (id, brand, model, available, deleted) values
            (1, 'BMW', 'X3', true, false),
            (2, 'Ford', 'Galaxy', true, false),
            (3, 'Opel', 'Insignia', true, false),
            (4, 'BMW', 'F11', true, false),
            (5, 'Mercedes', 'S500', true, false),
            (6, 'BMW', 'E92', true, false),
            (7, 'Ford', 'Fiesta', false, true),
            (8, 'Toyota', 'Avensis', false, false);

insert into car_details (id, segment, transmission, fuel, average_consumption, color, vin, registration_year,
                        doors, seats, price, registration_number, mileage, inspection, insurance) values
                        (1, 'SUV', 'automatic', 'diesel', 11.5, 'blue', 'JNKCV66E09M752516', 2018,
                        5, 7, 50.00, 'PO 12', 123000, '2022-09-19', '2022-09-19'),
                        (2, 'minivan', 'manual', 'diesel', 9.5, 'green', '9BWGD21J514001375', 2014,
                        5, 5, 35.00, 'PO 123', 130000, '2022-08-19', '2022-08-19'),
                        (3, 'sedan', 'manual', 'petrol', 7.5, 'brown', '1G8JW52R1YY672314', 2016,
                        5, 5, 35.00, 'PO 1234', 140000, '2022-07-19', '2022-07-19'),
                        (4, 'limousine', 'automatic', 'petrol', 11, 'black', '3C4PDCBGXCT339418', 2018,
                        5, 5, 75.00, 'WA 12', 123000, '2023-01-19', '2023-01-19'),
                        (5, 'limousine', 'automatic', 'diesel', 12, 'black', '1FTYR11U38PA47851', 2020,
                        5, 5, 80.00, 'WA 123', 90000, '2022-11-19', '2022-11-19'),
                        (6, 'coupe', 'manual', 'diesel', 9.5, 'silver', ' KNALD124345094422', 2011,
                        2, 4, 65.00, 'WWA 1234', 110000, '2022-12-19', '2022-12-19'),
                        (7, 'hatchback', 'manual', 'petrol', 7.4, 'red', '1ZVHT82H765106644', 2017,
                        2, 5, 35.00, 'GD 12', 100000, '2022-10-19', '2022-10-19'),
                        (8, 'sedan', 'manual', 'petrol', 7.2, 'silver', '5NPE24AF4FH100852', 2019,
                        5, 5, 45.00, 'GD 123', 110000, '2023-01-12', '2023-01-12');

update car set car_details_id = 1 where id=1;
update car set car_details_id = 2 where id=2;
update car set car_details_id = 3 where id=3;
update car set car_details_id = 4 where id=4;
update car set car_details_id = 5 where id=5;
update car set car_details_id = 6 where id=6;
update car set car_details_id = 7 where id=7;
update car set car_details_id = 8 where id=8;

insert into rental (id, city, country, phone, post_code, street, deleted) values
        (1, 'Poznan', 'Poland', '997 997 997', '61-810', 'Poznanska 11', false),
        (2, 'Warszawa', 'Poland', '000 666 111', '01-122', 'Warszawska 11', false),
        (3, 'Gdynia', 'Poland', '112 112 112', '32-500', 'Gdynska 11', false);

insert into rental_cars (rental_id, cars_id) values
        (1, 1), (1, 2), (1, 3), (2, 4), (2, 5), (2, 6), (3, 8);

insert into role (id, name) values
        (1, 'ROLE_USER'), (2, 'ROLE_EMPLOYEE'), (3, 'ROLE_ADMIN');

insert into users (dtype, id, first_name, last_name, email, birth, password, activated, deleted,
            employment_position, salary_per_hour) values
            ('Employee', 1, 'Patryk', 'Chojnacki', 'admin@admin.pl', '1996-06-12', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, false, 'boss', 120),
            ('Employee', 2, 'John', 'Snow', 'employee@employee.pl', '1970-06-12', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, false, 'dealer', 12),
            ('Employee', 3, 'Polly', 'Shelby', 'polly@shelby.pl', '1990-06-12', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, false, 'dealer', 14),
            ('Employee', 4, 'Thomas', 'Shelby', 'thomas@shelby.pl', '1992-06-12', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, false, 'dealer', 17),
            ('Employee', 5, 'Arthur', 'Shelby', 'arthur@shelby.pl', '1999-06-12', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, false, 'dealer', 16),
            ('Employee', 6, 'Finn', 'Shelby', 'finn@shelby.pl', '1982-06-12', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, false, 'dealer', 14),
            ('Employee', 7, 'Michael', 'Shelby', 'michael@shelby.pl', '1957-06-12', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, true, 'dealer', 18);

insert into rental_employees (rental_id, employees_id) values
        (1,1), (1,2), (1,3), (2,4), (2,5), (3,6), (3,7);

insert into users (dtype, id, first_name, last_name, email, birth, password, activated, deleted) values
            ('User', 8, 'user1', 'user1', 'user@user.pl', '1996-06-10', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, false),
            ('User', 9, 'user2', 'user2', 'user2@user.pl', '1999-04-11', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            false, false),
            ('User', 10, 'user3', 'user3', 'user3@user.pl', '1980-02-12', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, false),
            ('User', 11, 'user4', 'user4', 'user4@user.pl', '1992-08-13', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, false),
            ('User', 12, 'user5', 'user5', 'user5@user.pl', '1996-10-14', '$2a$12$rtZtnVoK9h7OGlvUtd7wU.dMGf6amRFjiP7X5OKjuhqGBeGyWzRVe',
            true, true);

            insert into users_roles(user_id, roles_id) values
            (1, 3), (1, 2), (1, 1), (2, 2), (2, 1), (3, 2), (4, 2), (5, 2),
            (6, 2), (7, 2), (8, 1), (9, 1), (10, 1), (11, 1);

insert into rents(id, user_id, car_id, comment, start_date, end_date, final_price, confirmed, returned, deleted) values
        (1, 8, 1, 'my comment 1', '2022-01-10T12:30:00', '2022-01-20T12:30:00', 1100, true, false, false),
        (2, 8, 6, 'my comment 2', '2022-03-05T12:30:00', '2022-03-15T12:30:00', 780, true, false, false),
        (3, 8, 2, 'my comment 3', '2022-03-10T12:30:00', '2022-03-10T12:30:00', 800, true, false, false),
        (4, 9, 3, 'my comment 4', '2021-02-15T12:30:00', '2021-02-24T12:30:00', 1200, true, true, false),
        (5, 9, 1, 'my comment 5', '2021-11-15T12:30:00', '2021-12-22T12:30:00', 1000, true, true, false),
        (6, 9, 2, 'my comment 6', '2021-10-15T12:30:00', '2021-10-27T12:30:00', 1300, true, true, false),
        (7, 10, 4, 'my comment 7', '2021-09-01T12:30:00', '2021-09-22T12:30:00', 970, true, true, false),
        (8, 8, 5, 'my comment 8', '2022-08-10T12:30:00', '2022-08-18T12:30:00', 999, true, true, false),
        (9, 12, 3, 'my comment 9', '2021-07-15T12:30:00', '2021-07-27T12:30:00', 888, true, true, true),
        (10, 10, 3, 'my comment 10', '2021-06-09T12:30:00', '2021-06-23T12:30:00', 777, false, false, true),
        (11, 11, 8, 'my comment 11', '2022-05-06T12:30:00', '2022-05-19T12:30:00', 666, false, false, false),
        (12, 8, 1, 'my comment 12', '2021-05-06T12:30:00', '2021-05-19T12:30:00', 555, false, false, false),
        (13, 9, 1, 'my comment 13', '2021-04-01T12:30:00', '2021-04-22T12:30:00', 970, false, false, false);


insert into return_report(id, damaged, note, car_id, employee_id, deleted) values
        (1, false, 'note 1', 1, 2, false),
        (2, true, 'bumper cracked 2', 6, 4, false),
        (3, false, 'note 3', 2, 2, false),
        (4, false, 'note 4', 3, 2, false),
        (5, false, 'note 5', 1, 3, false),
        (6, false, 'note 6', 2, 3, false),
        (7, false, 'note 7', 4, 4, false),
        (8, false, 'note 8', 5, 5, false),
        (9, true, 'paint scratch on left doors 9', 3, 6, true),
        (10, false, 'note 10', 3, null, true),
        (11, false, 'note 11', 8, null, false),
        (12, false, 'note 12', 1, null, false),
        (13, false, 'note 12', 1, null, false);

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
            (8, 1), (8, 2), (8, 3), (9, 4), (9, 5), (9, 6), (10, 7), (8, 8), (12, 9), (10, 10), (11, 11), (8, 12), (9, 13);

DELETE FROM material WHERE true;
DELETE FROM activity WHERE true;
DELETE FROM building WHERE true;
DELETE FROM report WHERE true;
DELETE FROM users WHERE true;

INSERT INTO users(user_name, last_name, email, email_backup, TN, TN_backup) VALUES ('David', 'Black', 'david@text.com', 'd@text.com', '111-11-11', '1');
INSERT INTO users(user_name, last_name, email, TN) VALUES ('Anna', 'Brown', 'anna@text.com', '222-22-22');

INSERT INTO report(report_name, price, order_date, user_id) VALUES ('Sunday report', 20, '2020-11-01', 1);
INSERT INTO report(report_name, price, order_date, user_id) VALUES ('Monday report', 32.5, '2020-11-02', 1);
INSERT INTO report(report_name, price, order_date, user_id) VALUES ('Tuesday report', 25, '2020-11-03', 1);
INSERT INTO report(report_name, price, order_date, user_id) VALUES ('Annas report 11/01', 15, '2020-11-01', 2);
INSERT INTO report(report_name, price, order_date, user_id) VALUES ('Annas report 11/02', 28.75, '2020-11-02', 2);

INSERT INTO building(building_name, is_active, report_id) VALUES ('School gym', true, 1);
INSERT INTO building(building_name, is_active, report_id) VALUES ('Evergreen street, 10/2', false, 2);
INSERT INTO building(building_name, is_active, report_id) VALUES ('Evergreen street, 10/2 (garage)', false, 2);
INSERT INTO building(building_name, is_active, report_id) VALUES ('Recycling plant (main office)', true, 3);
INSERT INTO building(building_name, is_active, report_id) VALUES ('Recycling plant (waiting room)', true, 3);
INSERT INTO building(building_name, is_active, report_id) VALUES ('54 Park avenue', false, 4);
INSERT INTO building(building_name, is_active, report_id) VALUES ('12 Park avenue', true, 5);

INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Walls painting', 'm2', 5, 210, 1);
INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Ceiling painting', 'm2', 12, 300, 1);
INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Floor covering (rubber tiles)', 'm2', 15, 300, 1);
INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Plastic windows installing', 'piece', 120, 10, 2);
INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Plastic windows installing', 'piece', 120, 4, 3);
INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Carpet covering', 'm2', 24, 50, 4);
INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Door installation', 'piece', 80, 1, 4);
INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Carpet covering', 'm2', 24, 24, 5);
INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Plastic windows installing', 'piece', 80, 2, 6);
INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Wallpapering', 'm2', 5, 60, 7);
INSERT INTO activity(work_name, measurement, price, amount, building_id) VALUES ('Ceiling painting', 'm2', 10, 20, 7);

INSERT INTO material(material, price, supplier, measurement, balance) VALUES ('Red brick', 24, 'Brick Inc', 'kg', 250);
INSERT INTO material(material, price, supplier, measurement, balance) VALUES ('White brick', 32, 'Brick Inc', 'kg', 100);
INSERT INTO material(material, price, supplier, measurement, balance) VALUES ('Simple door', 30, 'Doors', 'piece', 40);

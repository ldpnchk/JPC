DELETE FROM material WHERE true;
DELETE FROM activity WHERE true;
DELETE FROM building WHERE true;
DELETE FROM report WHERE true;
DELETE FROM users WHERE true;

INSERT INTO users(inst_id, user_name, last_name, email, email_backup, TN, TN_backup) VALUES (1, 'David', 'Black', 'david@text.com', 'd@text.com', '111-11-11', '1');
INSERT INTO users(inst_id, user_name, last_name, email, TN) VALUES (2, 'Anna', 'Brown', 'anna@text.com', '222-22-22');

INSERT INTO report(inst_id, report_name, price, order_date, user_id) VALUES (1, 'Sunday report', 20, '2020-11-01', 1);
INSERT INTO report(inst_id, report_name, price, order_date, user_id) VALUES (2, 'Monday report', 32.5, '2020-11-02', 1);
INSERT INTO report(inst_id, report_name, price, order_date, user_id) VALUES (3, 'Tuesday report', 25, '2020-11-03', 1);
INSERT INTO report(inst_id, report_name, price, order_date, user_id) VALUES (4, 'Annas report 11/01', 15, '2020-11-01', 2);
INSERT INTO report(inst_id, report_name, price, order_date, user_id) VALUES (5, 'Annas report 11/02', 28.75, '2020-11-02', 2);

INSERT INTO building(inst_id, building_name, is_active, report_id) VALUES (1, 'School gym', true, 1);
INSERT INTO building(inst_id, building_name, is_active, report_id) VALUES (2, 'Evergreen street, 10/2', false, 2);
INSERT INTO building(inst_id, building_name, is_active, report_id) VALUES (3, 'Evergreen street, 10/2 (garage)', false, 2);
INSERT INTO building(inst_id, building_name, is_active, report_id) VALUES (4, 'Recycling plant (main office)', true, 3);
INSERT INTO building(inst_id, building_name, is_active, report_id) VALUES (5, 'Recycling plant (waiting room)', true, 3);
INSERT INTO building(inst_id, building_name, is_active, report_id) VALUES (6, '54 Park avenue', false, 4);
INSERT INTO building(inst_id, building_name, is_active, report_id) VALUES (7, '12 Park avenue', true, 5);

INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (1, 'Walls painting', 'm2', 5, 210, 1);
INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (2, 'Ceiling painting', 'm2', 12, 300, 1);
INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (3, 'Floor covering (rubber tiles)', 'm2', 15, 300, 1);
INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (4, 'Plastic windows installing', 'piece', 120, 10, 2);
INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (5, 'Plastic windows installing', 'piece', 120, 4, 3);
INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (6, 'Carpet covering', 'm2', 24, 50, 4);
INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (7, 'Door installation', 'piece', 80, 1, 4);
INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (8, 'Carpet covering', 'm2', 24, 24, 5);
INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (9, 'Plastic windows installing', 'piece', 80, 2, 6);
INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (10, 'Wallpapering', 'm2', 5, 60, 7);
INSERT INTO activity(inst_id, work_name, measurement, price, amount, building_id) VALUES (11, 'Ceiling painting', 'm2', 10, 20, 7);

INSERT INTO material(inst_id, material, price, supplier, measurement, balance) VALUES (1, 'Red brick', 24, 'Brick Inc', 'kg', 250);
INSERT INTO material(inst_id, material, price, supplier, measurement, balance) VALUES (2, 'White brick', 32, 'Brick Inc', 'kg', 100);
INSERT INTO material(inst_id, material, price, supplier, measurement, balance) VALUES (3, 'Simple door', 30, 'Doors', 'piece', 40);

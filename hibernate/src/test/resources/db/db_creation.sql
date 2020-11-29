DROP TABLE material;
DROP TABLE activity;
DROP TABLE building;
DROP TABLE report;
DROP TABLE users;

CREATE TABLE users
(
    inst_id         SERIAL8,
    user_name       VARCHAR(50),
    last_name       VARCHAR(50),
    email           VARCHAR(50)     NOT NULL,
    email_backup    VARCHAR(50),
    TN              VARCHAR(20)     NOT NULL,
    TN_backup       VARCHAR(20),

    CONSTRAINT users_PK PRIMARY KEY (inst_id)
);

CREATE TABLE report
(
    inst_id         SERIAL8,
    report_name     VARCHAR(50),
    price           DECIMAL(19, 2),
    order_date      DATE            NOT NULL,
    user_id         BIGINT          NOT NULL,

    CONSTRAINT report_PK PRIMARY KEY (inst_id),
    CONSTRAINT report_users_FK FOREIGN KEY (user_id) REFERENCES users (inst_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE building
(
    inst_id         SERIAL8,
    building_name   VARCHAR(50),
    is_active       BOOLEAN,
    report_id       BIGINT          NOT NULL,

    CONSTRAINT building_PK PRIMARY KEY (inst_id),
    CONSTRAINT building_report_FK FOREIGN KEY (report_id) REFERENCES report (inst_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE activity
(
    inst_id         SERIAL8,
    work_name       VARCHAR(100),
    measurement     VARCHAR(20),
    price           DECIMAL(19, 2),
    amount          DECIMAL,
    building_id     BIGINT          NOT NULL,

    CONSTRAINT activity_PK PRIMARY KEY (inst_id),
    CONSTRAINT activity_building_FK FOREIGN KEY (building_id) REFERENCES building (inst_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE material
(
    inst_id         SERIAL8,
    material        VARCHAR(150),
    price           DECIMAL(19, 2),
    supplier        VARCHAR(50),
    measurement     VARCHAR(50),
    balance         VARCHAR(50),

    CONSTRAINT material_PK PRIMARY KEY (inst_id)
);

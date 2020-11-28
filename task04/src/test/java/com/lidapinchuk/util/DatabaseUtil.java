package com.lidapinchuk.util;

import lombok.SneakyThrows;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.hibernate.internal.SessionImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class DatabaseUtil {

    @SneakyThrows
    public static void initializeDatabase() {

        ScriptRunner scriptRunner = new ScriptRunner(((SessionImpl) HibernateFactory.getSessionFactory().openSession()).connection());
        Reader reader = new BufferedReader(new FileReader("src/test/resources/db/db_creation.sql"));
        scriptRunner.runScript(reader);
    }

    @SneakyThrows
    public static void fillDatabase() {

        ScriptRunner scriptRunner = new ScriptRunner(((SessionImpl) HibernateFactory.getSessionFactory().openSession()).connection());
        Reader reader = new BufferedReader(new FileReader("src/test/resources/db/db_filling.sql"));
        scriptRunner.runScript(reader);
    }

}

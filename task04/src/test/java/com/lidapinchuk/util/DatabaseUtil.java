package com.lidapinchuk.util;

import lombok.SneakyThrows;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class DatabaseUtil {

    @SneakyThrows
    public static void initializeDatabase() {

        Session session = HibernateFactory.getSessionFactory().openSession();

        ScriptRunner scriptRunner = new ScriptRunner(((SessionImpl) session).connection());
        scriptRunner.setSendFullScript(true);
        Reader reader = new BufferedReader(new FileReader("src/test/resources/db/db_creation.sql"));
        scriptRunner.runScript(reader);

        session.close();
    }

    @SneakyThrows
    public static void fillDatabase() {

        Session session = HibernateFactory.getSessionFactory().openSession();

        ScriptRunner scriptRunner = new ScriptRunner(((SessionImpl) session).connection());
        scriptRunner.setSendFullScript(true);
        Reader reader = new BufferedReader(new FileReader("src/test/resources/db/db_filling.sql"));
        scriptRunner.runScript(reader);

        session.close();
    }

}

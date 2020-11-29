package com.lidapinchuk.util;

import lombok.SneakyThrows;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class DatabaseUtil {

    @SneakyThrows
    public static void initializeDatabase(DataSource dataSource) {

        ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
        scriptRunner.setSendFullScript(true);
        Reader reader = new BufferedReader(new FileReader("src/test/resources/db/db_creation.sql"));
        scriptRunner.runScript(reader);
    }

    @SneakyThrows
    public static void fillDatabase(DataSource dataSource) {

        ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
        scriptRunner.setSendFullScript(true);
        Reader reader = new BufferedReader(new FileReader("src/test/resources/db/db_filling.sql"));
        scriptRunner.runScript(reader);
    }

}

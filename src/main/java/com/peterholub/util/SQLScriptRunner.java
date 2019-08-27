package com.peterholub.util;

import java.io.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;

/**
 * Class util for Sql script run
 */
public final class SQLScriptRunner {
    private final static String DELIMITER = ";";

    private SQLScriptRunner() {
    }

    /**
     * method for run Sql script from resources
     *
     * @param fileName name of file which have sql script
     */
    public static void runScript(String fileName) {
        String statementFromFile;
        StringBuilder stringBuilder = new StringBuilder();
        try (Connection connection = DatabaseConnection.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(SQLScriptRunner.class.getClassLoader().getResourceAsStream(fileName))))) {

            while ((statementFromFile = bufferedReader.readLine()) != null) {
                stringBuilder.append(statementFromFile);
            }

            String[] queries = stringBuilder.toString().split(DELIMITER);

            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    statement.executeUpdate(query);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

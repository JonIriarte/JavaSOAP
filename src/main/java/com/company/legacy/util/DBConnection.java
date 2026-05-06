package com.company.legacy.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mariadb://172.31.0.1:3306/employee_directory";

    private static final String USER = "springstudent";
    private static final String PASSWORD = "springstudent";

    public static Connection getConnection() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}

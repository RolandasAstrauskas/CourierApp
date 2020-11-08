package com.example.courierapp;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection extends AppCompatActivity {

    private static Connection conn;

    private static final String url = "";
    private static final String user = "";
    private static final String pass = "";

    public static Connection getConnection() {

        try {
            if(conn == null) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
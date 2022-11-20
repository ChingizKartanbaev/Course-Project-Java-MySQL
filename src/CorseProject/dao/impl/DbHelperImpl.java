package CorseProject.dao.impl;

import CorseProject.dao.DbHelper;

import java.sql.*;

public class DbHelperImpl implements DbHelper {

    Connection connection;

    @Override
    public Connection dbGetConnection(){
        String dbHost = "localhost";
        String dbPort = "3306";
        String dbName = "kfc";
        String dbUser = "root";
        String dbPass = "@$5{~7~?*{58~6$";
        String connectioString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        try {
            connection = DriverManager.getConnection(connectioString, dbUser, dbPass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }





}

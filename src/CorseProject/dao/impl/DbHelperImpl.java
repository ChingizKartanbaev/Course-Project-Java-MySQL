package CorseProject.dao.impl;

import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.models.Employee;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class DbHelperImpl implements DbHelper {

    Connection connection;
    private final String dbHost = "localhost";
    private final String dbPort = "3306";
    private final String dbUser = "root";
    private final String dbPass = "@$5{~7~?*{58~6$";
    private final String dbName = "kfc";

    @Override
    public Connection dbGetConnection(){
        String connectioString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        try {
            connection = DriverManager.getConnection(connectioString, dbUser, dbPass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }





}

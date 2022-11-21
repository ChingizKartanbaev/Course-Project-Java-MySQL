package CorseProject.dao;

import CorseProject.dao.impl.DbHelperImpl;

import java.sql.Connection;

public interface DbHelper {
    DbHelper INSTANCE = new DbHelperImpl();

    Connection dbGetConnection();

}

package CorseProject.dao;

import CorseProject.dao.impl.DbHelperImpl;
import CorseProject.models.Employee;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

public interface DbHelper {
    DbHelper INSTANCE = new DbHelperImpl();

    Connection dbGetConnection();

}

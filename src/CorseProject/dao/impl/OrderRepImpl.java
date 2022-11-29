package CorseProject.dao.impl;

import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.dao.OrderRep;
import CorseProject.models.Basket;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderRepImpl implements OrderRep {

    DbHelper dbHelper = DbHelper.INSTANCE;

    @Override
    public void createOrder(ArrayList<Basket> baskets) {

        String insert = "INSERT INTO " + Const.ORDER_TABLE + "(" + Const.ORDER_IDCLIENT + "," + Const.ORDER_ORDER + "," +
                Const.ORDER_COST + "," + Const.ORDER_PROGRESS + ")" + "VALUES(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(insert);
            for (Basket basket : baskets) {
                preparedStatement.setLong(1, basket.getIdClient());
                preparedStatement.setString(2, basket.getOrder());
                preparedStatement.setDouble(3, basket.getCost());
                preparedStatement.setString(4, String.valueOf(basket.getBdProcess()));
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package CorseProject.dao.impl;

import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.dao.OrderRep;
import CorseProject.models.Basket;
import CorseProject.models.enums.BdProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderRepImpl implements OrderRep {

    DbHelper dbHelper = DbHelper.INSTANCE;

    @Override
    public void createOrder(ArrayList<Basket> baskets) {

        String insert = "INSERT INTO " + Const.ORDER_TABLE + "(" + Const.ORDER_IDCLIENT + "," + Const.ORDER_ORDER + "," +
                Const.ORDER_AMOUNT + "," + Const.ORDER_COST + "," + Const.ORDER_PROGRESS + ")" + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(insert);
            for (Basket basket : baskets) {
                preparedStatement.setLong(1, basket.getIdClient());
                preparedStatement.setString(2, basket.getOrder());
                preparedStatement.setInt(3, basket.getAmount());
                preparedStatement.setDouble(4, basket.getCost());
                preparedStatement.setString(5, String.valueOf(basket.getBdProcess()));
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Basket> getAllOrders() {

        ArrayList<Basket> basketList = new ArrayList<>();
        String select = "SELECT * FROM " + Const.ORDER_TABLE;

        try (PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Basket basket = new Basket();
                basket.setIdOrders(resultSet.getLong("idOrders"));
                basket.setIdClient(resultSet.getInt("idClient"));
                basket.setOrder(resultSet.getString("orders"));
                basket.setAmount(resultSet.getInt("amount"));
                basket.setCost(resultSet.getDouble("cost"));
                basket.setBdProcess(BdProcess.valueOf(resultSet.getString("progress")));
                basketList.add(basket);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return basketList;
    }

    @Override
    public void updateOrder(int id, String process){

        String update = "UPDATE " + Const.ORDER_TABLE + " set " + Const.ORDER_PROGRESS + "=?" + " WHERE "
                + Const.ORDER_IDCLIENT + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(update);
            preparedStatement.setString(1, process);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

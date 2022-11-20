package CorseProject.dao.impl;

import CorseProject.dao.ClientRep;
import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.models.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientRepImpl implements ClientRep {
    DbHelper dbHelper = DbHelper.INSTANCE;

    @Override
    public void createClient(Client client) {

        String insert = "INSERT INTO " + Const.CLIENT_TABLE + "(" + Const.CLIENT_FULLNAME + "," + Const.CLIENT_LOGIN + "," +
                Const.CLIENT_PASSWORD + ")" + "VALUES(?,?,?)";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(insert);
            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setString(2, client.getLogin());
            preparedStatement.setString(3, client.getPassword());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Client> getAllClient() {

        List<Client> clientList = new ArrayList<>();
        String select = "SELECT * FROM " + Const.CLIENT_TABLE;

        try (PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Client client = new Client();
                client.setId(resultSet.getLong("idClient"));
                client.setFullName(resultSet.getNString("fullName"));
                client.setLogin(resultSet.getString("login"));
                client.setPassword(resultSet.getString("passwords"));
                clientList.add(client);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientList;
    }

    @Override
    public Client getClientById(long id) {

        String select = "SELECT * FROM " + Const.CLIENT_TABLE + " WHERE " + Const.CLINET_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select);
            preparedStatement.setInt(1, Math.toIntExact(id));

            ResultSet resultSet = preparedStatement.executeQuery();
            Client client = new Client();

            while (resultSet.next()){
                client.setId(resultSet.getLong("idClient"));
                client.setFullName(resultSet.getNString("fullName"));
                client.setLogin(resultSet.getString("login"));
                client.setPassword(resultSet.getString("passwords"));
            }
            return client;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateClient(int id) {

        Scanner scanner = new Scanner(System.in);
        String update = "UPDATE " + Const.CLIENT_TABLE + " set " + Const.CLIENT_LOGIN + "=?" + "," + Const.CLIENT_PASSWORD
                + "=? WHERE " + Const.CLINET_ID + "=?";

        System.out.print("Введите новый логин: ");
        String changeLogin = scanner.nextLine();
        System.out.println("Введите новый пароль: ");
        String changePassword = scanner.nextLine();

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(update);
            preparedStatement.setString(1, changeLogin);
            preparedStatement.setString(2, changePassword);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteClient(long id) {

        String delete = "delete from " + Const.CLIENT_TABLE + " where " + Const.CLINET_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(delete);
            preparedStatement.setInt(1, Math.toIntExact(id));
            int result = preparedStatement.executeUpdate();

            if(result == 1){
                System.out.println("Клиент удален");
            }else if(result == 0){
                System.out.println("Данный клиент не был найден");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

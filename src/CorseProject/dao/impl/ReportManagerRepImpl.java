package CorseProject.dao.impl;

import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.dao.ReportManagerRep;
import CorseProject.models.Employee;
import CorseProject.models.ReportManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportManagerRepImpl implements ReportManagerRep {

    DbHelper dbHelper = DbHelper.INSTANCE;

    @Override
    public void createReport(ReportManager reportManager) {

        String insert = "INSERT INTO " + Const.REPORRMANAGER_TABLE + "(" +
                Const.REPORRMANAGER_CITYNAME + "," + Const.REPORRMANAGER_CUSTOMERCOVERAGEAREA + ")" + "VALUES(?,?)";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(insert);
            preparedStatement.setString(1, reportManager.getCityName());
            preparedStatement.setString(2, reportManager.getCustomerCoverageArea());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReportManager> getAllReports() {

        List<ReportManager> reportManagerList = new ArrayList<>();
        String select = "SELECT * FROM " + Const.REPORRMANAGER_TABLE;

        try (PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                ReportManager reportManager = new ReportManager();
                reportManager.setId(resultSet.getLong("idReportManager"));
                reportManager.setCityName(resultSet.getNString("cityName"));
                reportManager.setCustomerCoverageArea(resultSet.getString("customerCoverageArea"));
                reportManagerList.add(reportManager);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reportManagerList;
    }

    @Override
    public ReportManager getByCityName(String cityName) {

        String select = "SELECT * FROM " + Const.REPORRMANAGER_TABLE + " WHERE " + Const.REPORRMANAGER_CITYNAME + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select);
            preparedStatement.setString(1, cityName);

            ResultSet resultSet = preparedStatement.executeQuery();
            ReportManager reportManager = new ReportManager();

            while (resultSet.next()){
                reportManager.setId(resultSet.getLong("idReportManager"));
                reportManager.setCityName(resultSet.getNString("cityName"));
                reportManager.setCustomerCoverageArea(resultSet.getString("customerCoverageArea"));
            }
            return reportManager;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCustomerCoverageArea(int id, String followers) {

        String update = "UPDATE " + Const.REPORRMANAGER_TABLE + " set " + Const.REPORRMANAGER_CUSTOMERCOVERAGEAREA
                + "=? WHERE " + Const.REPORRMANAGER_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(update);
            preparedStatement.setString(1, followers);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteReport(long id) {
        String delete = "delete from " + Const.REPORRMANAGER_TABLE + " where " + Const.REPORRMANAGER_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(delete);
            preparedStatement.setInt(1, Math.toIntExact(id));
            int result = preparedStatement.executeUpdate();

            if(result == 1){
                System.out.println("Данные были удалены");
            }else if(result == 0){
                System.out.println("Данные не были найдены");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

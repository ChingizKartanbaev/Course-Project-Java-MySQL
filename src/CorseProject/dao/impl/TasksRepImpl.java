package CorseProject.dao.impl;

import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.dao.TasksRep;
import CorseProject.models.Client;
import CorseProject.models.Reviews;
import CorseProject.models.Tasks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TasksRepImpl implements TasksRep {

    DbHelper dbHelper = DbHelper.INSTANCE;

    @Override
    public void createTasks(Tasks tasks) {
        String insert = "INSERT INTO " + Const.TASKS_TABLE + "(" +
                Const.TASKS_TASK + "," + Const.TASKS_IDEMPLOYEE + ")" + "VALUES(?,?)";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(insert);
            preparedStatement.setString(1, tasks.getTask());
            preparedStatement.setLong(2, tasks.getIdEmployee());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tasks> getAllTasks () {

        List<Tasks> tasksList = new ArrayList<>();
        String select = "SELECT * FROM " + Const.TASKS_TABLE;

        try (PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select)){
            ResultSet resultSet = preparedStatement.executeQuery();
            Tasks tasks = new Tasks();

            while (resultSet.next()){
                tasks.setId(resultSet.getLong("idTasks"));
                tasks.setTask(resultSet.getNString("Task"));
                tasks.setIdEmployee(resultSet.getInt("idEmployee"));
                tasksList.add(tasks);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasksList;
    }

    @Override
    public Tasks getTaskById(long id) {

        String select = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE " + Const.TASKS_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select);
            preparedStatement.setInt(1, Math.toIntExact(id));

            ResultSet resultSet = preparedStatement.executeQuery();
            Tasks tasks = new Tasks();

            while (resultSet.next()){
                tasks.setId(resultSet.getLong("idTasks"));
                tasks.setTask(resultSet.getNString("Task"));
                tasks.setIdEmployee(resultSet.getInt("idEmployee"));
            }
            return tasks;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

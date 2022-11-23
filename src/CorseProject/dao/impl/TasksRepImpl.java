package CorseProject.dao.impl;

import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.dao.TasksRep;
import CorseProject.models.Tasks;
import CorseProject.models.enums.BdProcess;

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
                Const.TASKS_TASK + "," + Const.TASKS_IDEMPLOYEE + "," + Const.TASKS_PROCESS + ")" + "VALUES(?,?,?)";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(insert);
            preparedStatement.setString(1, tasks.getTask());
            preparedStatement.setLong(2, tasks.getIdEmployee());
            preparedStatement.setString(3, tasks.getProcess());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tasks> getAllTasks() {

        List<Tasks> tasksList = new ArrayList<>();
        String select = "SELECT * FROM " + Const.TASKS_TABLE;

        try (PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Tasks tasks = new Tasks();
                tasks.setId(resultSet.getLong("idTasks"));
                tasks.setTask(resultSet.getNString("task"));
                tasks.setIdEmployee(resultSet.getInt("idEmployee"));
                tasks.setProcess(BdProcess.valueOf(resultSet.getString("process")));
                tasksList.add(tasks);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasksList;
    }

    @Override
    public Tasks getTaskByProcess(String process) {

        String select = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE " + Const.TASKS_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select);
            preparedStatement.setString(1, process);

            ResultSet resultSet = preparedStatement.executeQuery();
            Tasks tasks = new Tasks();

            while (resultSet.next()){
                tasks.setId(resultSet.getLong("idTasks"));
                tasks.setTask(resultSet.getNString("task"));
                tasks.setIdEmployee(resultSet.getInt("idEmployee"));
            }
            return tasks;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTask(long id) {

        String delete = "delete from " + Const.TASKS_TABLE + " where " + Const.TASKS_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(delete);
            preparedStatement.setInt(1, Math.toIntExact(id));
            int result = preparedStatement.executeUpdate();

            if(result == 1){
                System.out.println("Задание удалено");
            }else if(result == 0){
                System.out.println("Данный клиент не был найден");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTask (int id, String process) {

        String update = "UPDATE " + Const.TASKS_TABLE + " set " + Const.TASKS_PROCESS + "=?" + " WHERE "
                + Const.TASKS_ID + "=?";

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

package CorseProject.dao.impl;

import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.dao.TasksRep;
import CorseProject.models.Reviews;
import CorseProject.models.Tasks;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    // Закончить
    @Override
    public List<Tasks> getAllReviews() {
        return null;
    }

    // Закончить
    @Override
    public Reviews getTaskById(long id) {
        return null;
    }
}

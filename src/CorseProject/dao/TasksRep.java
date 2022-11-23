package CorseProject.dao;

import CorseProject.models.Tasks;

import java.util.List;

public interface TasksRep {

    void createTasks (Tasks tasks);
    List<Tasks> getAllTasks();
    Tasks getTaskByProcess (String process);
    void updateTask (int id, String process);
    void deleteTask (long id);
}

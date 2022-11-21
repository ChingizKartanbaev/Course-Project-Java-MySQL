package CorseProject.dao;

import CorseProject.models.Reviews;
import CorseProject.models.Tasks;

import java.util.List;

public interface TasksRep {

    void createTasks (Tasks tasks);

    List<Tasks> getAllReviews();

    Reviews getTaskById (long id);
}

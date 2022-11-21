package CorseProject.models;

public class Tasks {

    private long id;
    private String task;
    private long idEmployee;

    public Tasks() {
    }

    public Tasks(String task, long idEmployee) {
        this.task = task;
        this.idEmployee = idEmployee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(long idEmployee) {
        this.idEmployee = idEmployee;
    }

    @Override
    public String toString() {
        return id + " " + task + " " + idEmployee;
    }
}

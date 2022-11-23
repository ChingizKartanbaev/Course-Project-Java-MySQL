package CorseProject.models;

import CorseProject.models.enums.BdProcess;

public class Tasks {

    private long id;
    private String task;
    private long idEmployee;
    private BdProcess process;

    public Tasks() {
    }

    public Tasks(String task, long idEmployee, BdProcess process) {
        this.task = task;
        this.idEmployee = idEmployee;
        this.process = process;
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

    public String getProcess() {
        return process.toString();
    }

    public void setProcess(BdProcess process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return id + " " + task + " " + idEmployee + " " + process;
    }
}

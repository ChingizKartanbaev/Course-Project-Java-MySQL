package CorseProject.models;

public class Employee {
    private long id;
    private String FullName;
    private String login;
    private String password;
    private String typeOfAccount;
    private double salary;

    public Employee() {}

    public Employee(String fullName, String login, String password, String typeOfAccount, double salary) {
        FullName = fullName;
        this.login = login;
        this.password = password;
        this.typeOfAccount = typeOfAccount;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(String typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id + " " + FullName + " " + login + " " + password + " " + typeOfAccount + " " + salary;
    }
}

package CorseProject.models;

import CorseProject.models.enums.BdProcess;

public class Basket {

    private long idOrders;
    private int idClient;
    private String order;
    private int amount;
    private double cost;
    private double totalCost;
    private BdProcess bdProcess;

    public Basket(int idClient, String order, int amount, double cost, double totalCost, BdProcess bdProcess) {
        this.idClient = idClient;
        this.order = order;
        this.amount = amount;
        this.cost = cost;
        this.totalCost = totalCost;
        this.bdProcess = bdProcess;
    }

    public long getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(long idOrders) {
        this.idOrders = idOrders;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public BdProcess getBdProcess() {
        return bdProcess;
    }

    public void setBdProcess(BdProcess bdProcess) {
        this.bdProcess = bdProcess;
    }

    public int getAmount() {return amount;}

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return idClient + " " + order + " " + amount + " " + cost + " " + bdProcess;
    }
}

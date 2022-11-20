package CorseProject.models;

public class ReportManager {
    private long id;
    private String cityName;
    private String customerCoverageArea;

    public ReportManager() {
    }

    public ReportManager(String cityName, String customerCoverageArea) {
        this.cityName = cityName;
        this.customerCoverageArea = customerCoverageArea;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCustomerCoverageArea() {
        return customerCoverageArea;
    }

    public void setCustomerCoverageArea(String customerCoverageArea) {
        this.customerCoverageArea = customerCoverageArea;
    }

    @Override
    public String toString() {
        return id + " " + cityName + " " + customerCoverageArea;
    }
}

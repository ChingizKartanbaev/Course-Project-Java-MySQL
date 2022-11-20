package CorseProject.dao;

import CorseProject.models.ReportManager;

import java.util.List;

public interface ReportManagerRep {

    void createReport (ReportManager reportManager);
    List<ReportManager> getAllReports();

    ReportManager getByCityName (String cityName);

    void updateCustomerCoverageArea (int id, String followers);

    void deleteReport (long id);

}

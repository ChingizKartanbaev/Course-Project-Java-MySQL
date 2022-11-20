import CorseProject.dao.ReportManagerRep;
import CorseProject.dao.impl.ReportManagerRepImpl;
import CorseProject.models.ReportManager;
import CorseProject.service.Authorization;

public class Main {
    public static void main(String[] args) {

        Authorization authorization = new Authorization();
        authorization.authotization();

    }
}
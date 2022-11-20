import CorseProject.dao.BudgetRep;
import CorseProject.dao.impl.BudgetRepImpl;
import CorseProject.models.Budget;
import CorseProject.service.Authorization;

public class Main {
    public static void main(String[] args) {

        Authorization authorization = new Authorization();
        authorization.authotization();

    }
}
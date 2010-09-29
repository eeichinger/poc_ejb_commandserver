import java.util.ArrayList;
import java.util.List;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public class HandleCallContactChoreographyStub implements HandleCallContactChoreography {
    public void updateProcessState(String selectedCountry, String partialAccountId) {
        /* noop in this stub */
    }

    public List<Account> findMatchingAccounts(String selectedCountry, String partialAccountId) {
        List<Account> accounts = new ArrayList<Account>();
        accounts.add(new Account(1, "Account 1"));
        accounts.add(new Account(2, "Account 2"));
        return accounts;
    }

    public List<String> getCountries() {
        List<String> countries = new ArrayList<String>();
        countries.add("Portugal");
        countries.add("France");
        return countries;
    }
}

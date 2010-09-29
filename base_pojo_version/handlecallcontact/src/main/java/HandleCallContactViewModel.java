import java.io.Serializable;
import java.util.List;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public class HandleCallContactViewModel implements Serializable {
    private String viewName;
    private HandleCallContactForm form;
    private List<String> countryList;
    private List<Account> matchingAccounts;

    public HandleCallContactViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public HandleCallContactForm getForm() {
        return form;
    }

    public void setForm(HandleCallContactForm form) {
        this.form = form;
    }

    public List<String> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<String> countryList) {
        this.countryList = countryList;
    }

    public List<Account> getMatchingAccounts() {
        return matchingAccounts;
    }

    public void setMatchingAccounts(List<Account> matchingAccounts) {
        this.matchingAccounts = matchingAccounts;
    }
}

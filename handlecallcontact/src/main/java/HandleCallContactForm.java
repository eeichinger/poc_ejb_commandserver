import java.io.Serializable;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public class HandleCallContactForm implements Serializable {
    private String accountId;
    private String selectedCountry;

    /* etc ... */

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }
}

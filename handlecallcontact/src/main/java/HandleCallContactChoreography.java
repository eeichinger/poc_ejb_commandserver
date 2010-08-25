import java.util.List;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public interface HandleCallContactChoreography {
    public void updateProcessState( String selectedCountry, String partialAccountId /* etc */ );
    public List<Account> findMatchingAccounts(String selectedCountry, String partialAccountId);
    public List<String> getCountries();
}

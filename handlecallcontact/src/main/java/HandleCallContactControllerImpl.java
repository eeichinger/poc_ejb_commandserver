import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public class HandleCallContactControllerImpl implements HandleCallContactController {

    private HandleCallContactChoreography handleCallContactChoreography;

    public HandleCallContactControllerImpl(HandleCallContactChoreography handleCallContactChoreography) {
        Assert.notNull(handleCallContactChoreography);
        this.handleCallContactChoreography = handleCallContactChoreography;
    }

    public HandleCallContactViewModel update(HandleCallContactForm form) {
        /* perform lookups based on form data */
        HandleCallContactViewModel modelAndView = new HandleCallContactViewModel("defaultView");
        modelAndView.setForm(form);
        modelAndView.setCountryList(fetchCountryNames(form));
        modelAndView.setMatchingAccounts(fetchMatchingAccounts(form));
        return modelAndView;
    }

    private List<Account> fetchMatchingAccounts(HandleCallContactForm form) {
        return handleCallContactChoreography.findMatchingAccounts(form.getSelectedCountry(), form.getAccountId());
    }

    private List<String> fetchCountryNames(HandleCallContactForm form) {
        return handleCallContactChoreography.getCountries();
    }
}

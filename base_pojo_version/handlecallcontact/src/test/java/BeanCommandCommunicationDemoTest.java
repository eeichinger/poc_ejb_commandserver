import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BeanCommandCommunicationDemoTest {

    @Resource
    HandleCallContactController handleCallContactController;

    @Test
    public void allTogetherInProcess() {
        HandleCallContactForm form = new HandleCallContactForm();
        form.setAccountId("233");
        form.setSelectedCountry("Portugal");

        HandleCallContactViewModel modelAndView = handleCallContactController.update(form);
        Assert.assertEquals("defaultView", modelAndView.getViewName());
        Assert.assertEquals(2, modelAndView.getMatchingAccounts().size());
    }
}

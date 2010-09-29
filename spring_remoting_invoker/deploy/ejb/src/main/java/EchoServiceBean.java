import javax.ejb.Stateless;

@Stateless
public class EchoServiceBean implements EchoServiceRemote {
    public String echo(String message) {
        return "FROM EJB";
    }
}

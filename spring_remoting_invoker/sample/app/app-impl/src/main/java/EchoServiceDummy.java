import org.springframework.security.core.context.SecurityContextHolder;

public class EchoServiceDummy implements EchoService {
    public String echo(String message) {
        return message + "[" + SecurityContextHolder.getContext().getAuthentication() + "]";
    }
}

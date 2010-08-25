/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public interface BeanCommandServer {
    Object invoke(BeanCommand command);
}

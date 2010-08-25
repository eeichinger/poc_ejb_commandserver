import java.io.Serializable;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public class Account implements Serializable {
    private int accountId;
    private String name;

    public Account(int accountId, String name) {
        this.accountId = accountId;
        this.name = name;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode
@ToString
public class Account implements Comparable<Account> {

    @Getter
    @Setter
    private long money;

    @Getter
    @Setter
    private String accNumber;

    private static long id;

    public Account(long startMoney) {
        this.money = startMoney;
        id++;
        this.accNumber = Long.toString(id);
    }

    @Override
    public int compareTo(@NotNull Account o) {
        return this.getAccNumber().compareTo(o.getAccNumber());
    }
}

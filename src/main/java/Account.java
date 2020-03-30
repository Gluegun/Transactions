import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Account {

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

}

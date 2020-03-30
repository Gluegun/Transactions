import junit.framework.TestCase;

public class TransferTest extends TestCase {

    Bank bank;
    Account account1;
    Account account2;
    long ac1Money = 100000;
    long ac2Money = 30000;

    @Override
    protected void setUp() throws Exception {

        bank = new Bank();
        account1 = new Account(ac1Money);
        account2 = new Account(ac2Money);
        bank.addAccountsToMap(account1.getAccNumber(), account1);
        bank.addAccountsToMap(account2.getAccNumber(), account2);

    }

    public void testDeposit() {

        long amount = 20000;

        bank.transfer(account1.getAccNumber(), account2.getAccNumber(), amount);

        long expectedFrom1Account = ac1Money - amount;
        long actualFrom1Account = account1.getMoney();

        long expectedFrom2Account = amount + ac2Money;
        long actualFrom2Account = account2.getMoney();

        long totalBalanceExpected = (ac1Money - amount) + (ac2Money + amount);
        long totalBalanceActual = account2.getMoney() + account1.getMoney();

        assertEquals(expectedFrom1Account, actualFrom1Account);
        assertEquals(expectedFrom2Account, actualFrom2Account);
        assertEquals(totalBalanceExpected, totalBalanceActual);

    }

    public void testWithDrawLessThan50k() {

        long amount = 40000;
        bank.transfer(account2.getAccNumber(), account1.getAccNumber(), amount);

        long expected = ac1Money;
        long actual = account1.getMoney();

        assertEquals(expected, actual);
    }

    public void testNegativeAmount() {

        long amount = -500;
        bank.transfer(account1.getAccNumber(), account1.getAccNumber(), amount);

        long expected = ac2Money;
        long actual = account2.getMoney();

        assertEquals(expected, actual);

    }

    public void testNotEnoughMoney() {

        long amount = 45000;

        bank.transfer(account2.getAccNumber(), account1.getAccNumber(), amount);

        long expected = ac2Money;
        long actual = account2.getMoney();

        String status = "Недостаточно средств";

        assertEquals(expected, actual);
        assertEquals(status, "Недостаточно средств");

    }

    @Override
    protected void tearDown() throws Exception {
        bank = null;
        account1 = null;
        account2 = null;
    }
}

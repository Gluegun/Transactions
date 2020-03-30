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

        long expected = 50000;
        long actual = account2.getMoney();

        assertEquals(expected, actual);
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

    public void testDepositMoreThan50k() throws InterruptedException {

        long amount = 55000;
        long expected;
        long actual;

        if (bank.isFraud(account1.getAccNumber(), account2.getAccNumber(), amount)) {
            expected = ac2Money;
            actual = account2.getMoney();
        } else {
            expected = ac2Money + amount;
            actual = account2.getMoney();
        }

        assertEquals(expected, actual);


    }


    @Override
    protected void tearDown() throws Exception {
        bank = null;
        account1 = null;
        account2 = null;
    }
}

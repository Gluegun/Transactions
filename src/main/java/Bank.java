import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank extends Thread {

    @Getter
    @Setter
    private HashMap<String, Account> accounts;

    private static Logger logger = LogManager.getRootLogger();
    private static final Marker INPUT_OK = MarkerManager.getMarker("INPUT_OK");
    private static final Marker INPUT_ERROR = MarkerManager.getMarker("INPUT_ERROR");
    private final Random random = new Random();

    public synchronized boolean isFraud()
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public Bank() {
        accounts = new HashMap<>();
        logger.info(INPUT_OK, "Создан новый экземпляр класса " + this.getClass().getSimpleName());
    }

    public void transfer(String accountNumFrom, String accountNumTo, long amount) {


        if (amount < 0) {

            logger.info(INPUT_ERROR, "Введена неверная сумма (" + amount + "€). Операция перевода денежных средств со счета " +
                    accountNumFrom + " на счет " + accountNumTo + " отменена.");
            return;
        }

        Account from = accounts.get(accountNumFrom);
        Account to = accounts.get(accountNumTo);

        synchronized (from.compareTo(to) > 0 ? from : to) {
            synchronized (to.compareTo(from) > 0 ? to : from) {

                try {
                    if (amount > 50000 && isFraud()) {
                        logger.info(INPUT_ERROR, "Операция перевода " + amount + "€ со счета " + from.getAccNumber() + " на счет " +
                                to.getAccNumber() + " прервана службой безопасности");

                    } else {
                        deposit(from, to, amount);
                        logger.info(INPUT_OK, "Операция успешно выполнена. Со счета " + from.getAccNumber() + " на счет " +
                                to.getAccNumber() + " поступили денеждные средства в размере " + amount + " €");

                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public long getBalance(String accountNum) {
        if (accounts.containsKey(accountNum)) {
            return accounts.get(accountNum).getMoney();
        }
        return 0;
    }

    private void deposit(Account from, Account to, long amount) {

        long balance = from.getMoney() - amount;
        if (balance < 0) {
            logger.error(INPUT_ERROR, "Недостаточно средств на счету. Операцию перевода " + amount + " с счета " +
                    from.getAccNumber() + " на счет " + to.getAccNumber() + " невозможно выполнить");
        } else {
            from.setMoney(balance);
            to.setMoney(to.getMoney() + amount);
        }
    }


    public void addAccountsToMap(String accountNum, Account account) {
        accounts.put(accountNum, account);
    }

    public void printAccountsData() {
        accounts.forEach((key, value) -> System.out.println("Account " + key + ": " + value));
    }

    public void printBankMoneyAmount() {

        Account account;
        int sum = 0;

        for (Map.Entry<String, Account> map : accounts.entrySet()) {
            account = map.getValue();
            sum += account.getMoney();
        }

        System.out.println(sum);

    }
}

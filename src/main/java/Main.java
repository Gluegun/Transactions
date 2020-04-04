import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static Logger logger = LogManager.getRootLogger();
    private static final Marker INPUT_OK = MarkerManager.getMarker("INPUT_OK");
    private static Bank bank;
    private static List<Account> accounts;

    public static void main(String[] args) {

        bank = new Bank();

        int amountOfAccounts = 30;
        logger.info(INPUT_OK, "Создано " + amountOfAccounts + " счетов");

        accounts = createAccountList(amountOfAccounts); // создаем список счетов со случайным количеством ДС на счету

        addAccountsToBank(bank, accounts); // добавляем счета в банк

        System.out.println("Before operations");
        bank.printBankMoneyAmount(); // сумма всех ДС на всех счетах в банке

        long start = System.currentTimeMillis();

        List<Thread> threads = createThreadsList(15, 10000, 49000);

        threads.forEach(Thread::start);

        for(Thread thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        System.out.printf("Время работы программы: %d мс\n", (System.currentTimeMillis() - start));

        System.out.println("After operations:");
        bank.printBankMoneyAmount(); // проверка, что после всех операций сумма не изменилась


    }

    private static List<Thread> createThreadsList(int amountOfThreads, int amountOfTransactions, int rangeOfTransactionAmount) {

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < amountOfThreads; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < amountOfTransactions; j++) {
                    String from = accounts.get((int) (Math.random() * accounts.size())).getAccNumber();
                    String to = accounts.get((int) (Math.random() * accounts.size())).getAccNumber();
                    int sum = (int) (Math.random() * rangeOfTransactionAmount);
                    bank.transfer(from, to, sum);
                }
            }));
        }
        return threads;
    }

    private static List<Account> createAccountList(int amount) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            accounts.add(new Account((long) (1 + Math.random() * 1000000)));
        }
        return accounts;
    }

    private static void addAccountsToBank(Bank bank, List<Account> accounts) {
        String accNumber;

        for (Account account : accounts) {
            accNumber = account.getAccNumber();
            bank.addAccountsToMap(accNumber, account);
        }
        logger.info("Счета добавлены в банк в количестве: " + accounts.size() + " шт.");
    }
}

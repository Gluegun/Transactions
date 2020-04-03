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

    public static void main(String[] args) {

        Bank bank = new Bank();

        int amountOfAccounts = 30;
        logger.info(INPUT_OK, "Создано " + amountOfAccounts + " счетов");

        List<Account> accounts = createAccountList(amountOfAccounts); // создаем список счетов со случайным количеством ДС на счету

        addAccountsToBank(bank, accounts); // добавляем счета в банк

        System.out.println("Before operations");
        bank.printBankMoneyAmount(); // сумма всех ДС на всех счетах в банке

        startThreadsWithTransferMethod(bank, accounts, 10, 3000); // метод, запускающий потоки, выполняющие метод трансфер класса Банк

        System.out.println("After operations:");
        bank.printBankMoneyAmount(); // проверка, что после всех операций сумма не изменилась


    }

    private static void startThreadsWithTransferMethod(Bank bank, List<Account> accounts, int amountOfThreads, int amountOfTransactions) {

        for (int i = 0; i < amountOfThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < amountOfTransactions; j++) {
                    String from = accounts.get((int) (Math.random() * accounts.size())).getAccNumber();
                    String to = accounts.get((int) (Math.random() * accounts.size())).getAccNumber();
                    int sum = (int) (Math.random() * 52000);
                    bank.transfer(from, to, sum);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static List<Account> createAccountList(int amount) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            accounts.add(new Account((long) (1 + Math.random() * 1000000)));
        }
        return accounts;
    }

    public static void addAccountsToBank(Bank bank, List<Account> accounts) {
        String accNumber;

        for (Account account : accounts) {
            accNumber = account.getAccNumber();
            bank.addAccountsToMap(accNumber, account);
        }
        logger.info("Счета добавлены в банк в количестве: " + accounts.size() + " шт.");
    }
}

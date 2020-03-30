import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Bank bank = new Bank();

        int amountOfAccounts = 3;

        List<Account> accounts = createAccountList(amountOfAccounts);

        addAccountsToBank(bank, accounts);

        int sumOfTransaction = 5000;

        System.out.println(sumOfTransaction);

        Account account1 = accounts.get(0);
        Account account2 = accounts.get(1);

        System.out.println("total balance: " + (account1.getMoney() + account2.getMoney()));

        System.out.println(account1.getAccNumber() + " " + bank.getBalance(account1.getAccNumber()));
        System.out.println(account2.getAccNumber() + " " + bank.getBalance(account2.getAccNumber()));

        Thread thread1;
        Thread thread;
        Random random = new Random();

        for (int i = 0; i < 10; i++) {

            int nextInt = random.nextInt(60000);
            System.out.println(nextInt);
            thread1 = new Thread((() -> bank.transfer(account1.getAccNumber(), account2.getAccNumber(), nextInt)));
            thread = new Thread((() -> bank.transfer(account2.getAccNumber(), account1.getAccNumber(), nextInt)));

            thread1.start();
            thread.start();

            try {
                thread1.join();
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(account1.getAccNumber() + ": " + account1.getMoney());
        System.out.println(account2.getAccNumber() + ": " + account2.getMoney());

        System.out.println("total balance: " + (account1.getMoney() + account2.getMoney()));


//        System.out.println(bank.getBalance("1"));
//        for (Account account : accounts) {
//            if (account.getAccNumber().equals("1")) {
//                System.out.println(account.getMoney());
//            }
//        }
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
    }
}

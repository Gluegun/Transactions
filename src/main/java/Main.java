import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Bank bank = new Bank();

        int amountOfAccounts = 3;

        List<Account> accounts = createAccountList(amountOfAccounts);

        addAccountsToBank(bank, accounts);

        int sumOfTransaction = 500_000;

        System.out.println(sumOfTransaction);
        Account account1 = accounts.get(0);
        Account account2 = accounts.get(1);

        bank.printAccountsData();

        bank.transfer(account1.getAccNumber(), account2.getAccNumber(), sumOfTransaction);

        System.out.println(account1.getAccNumber() + ": " + account1.getMoney());
        System.out.println(account2.getAccNumber() + ": " + account2.getMoney());


        System.out.println(bank.getBalance("1"));
        for (Account account : accounts) {
            if (account.getAccNumber().equals("1")) {
                System.out.println(account.getMoney());
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
    }
}

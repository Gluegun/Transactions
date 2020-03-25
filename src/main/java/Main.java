import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Bank bank = new Bank();
        List<Account> accounts = createAccountList(100);

        addAccountsToBank(bank, accounts);

        Account account1 = bank.getAccounts().get("1");
        Account account2 = bank.getAccounts().get("2");

        long sumOfTransaction = 50001;
        System.out.println("Счет: " + account1.getAccNumber() + "\nБаланс: " + account1.getMoney() + "€");
        System.out.println("Счет: " + account2.getAccNumber() + "\nБаланс: " + account2.getMoney() + "€");

        System.out.println("Перевод " + sumOfTransaction + "€ со счета " + account1.getAccNumber() + " на счет " + account2.getAccNumber());
        bank.transfer(account1.getAccNumber(), account2.getAccNumber(), sumOfTransaction);

        System.out.println("Баланс после транзакции:");
        System.out.println("Счет: " + account1.getAccNumber() + "\nБаланс: " + account1.getMoney() + "€");
        System.out.println("Счет: " + account2.getAccNumber() + "\nБаланс: " + account2.getMoney() + "€");

        System.out.println(bank.getBalance("1"));




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

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Random;

public class Bank extends Thread {

    @Getter
    @Setter
    private HashMap<String, Account> accounts;

    private final Random random = new Random();

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public Bank() {
        accounts = new HashMap<>();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount) {

        Account fromAccount;
        Account toAccount;
        long sum;

        if (accounts.containsKey(fromAccountNum) && accounts.containsKey(toAccountNum)) {

            fromAccount = accounts.get(fromAccountNum);
            toAccount = accounts.get(toAccountNum);
            sum = fromAccount.getMoney() - amount;

            if (amount > 50000) {
                try {
                    if (isFraud(fromAccountNum, toAccountNum, amount)) {

                        System.out.println(true);
                        return;

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (sum < 0) {
                System.out.println("Недостаточно средств на счету, операция невозможна");

            } else {
                fromAccount.setMoney(sum);
                toAccount.setMoney(toAccount.getMoney() + amount);
            }
        } else {
            System.out.println("Неверно введен номер счета");
        }
    }


    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        if (accounts.containsKey(accountNum)) {
            return accounts.get(accountNum).getMoney();
        }
        return 0;
    }


    public void addAccountsToMap(String accountNum, Account account) {
        accounts.put(accountNum, account);
    }

    public void printAccountsData() {
        accounts.forEach((key, value) -> System.out.println(key + ": " + value));
    }

}

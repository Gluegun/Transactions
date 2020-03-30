import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Random;

public class Bank extends Thread {

    @Getter
    @Setter
    private HashMap<String, Account> accounts;
    private final Object lock = new Object();
    private final Object lock2 = new Object();

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

//    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
//
//        if (amount < 0) {
//            System.out.println("Неверная сумма транзакции");
//            return;
//        }
//
//        Account fromAccount;
//        Account toAccount;
//        long balance;
//
//        if (accounts.containsKey(fromAccountNum) && accounts.containsKey(toAccountNum)) {
//
//            fromAccount = accounts.get(fromAccountNum);
//            toAccount = accounts.get(toAccountNum);
//            balance = fromAccount.getMoney() - amount;
//
//            if (balance < 0) {
//                System.out.println("Недостаточно средств на счету, операция невозможна");
//                return;
//            }
//
//            if (amount > 50000) {
//                try {
//                    if (isFraud(fromAccountNum, toAccountNum, amount)) {
//                        return;
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                fromAccount.setMoney(balance);
//                toAccount.setMoney(toAccount.getMoney() + amount);
//            }
//        } else {
//            System.out.println("Неверно введен номер счета");
//        }
//    }
    public void transfer(String accountNumFrom, String accountNumTo, long amount) {
        if (amount < 0) {
            System.out.println("Неверная сумма транзакции");
            return;
        }

        Account accountFrom;
        Account accountTo;

        if (accounts.containsKey(accountNumFrom) && accounts.containsKey(accountNumTo)) {
            accountFrom = accounts.get(accountNumFrom);
            accountTo = accounts.get(accountNumTo);


            try {
                if (amount > 50000 && isFraud(accountNumFrom, accountNumTo, amount)) {

                    System.out.println("Операция заблокирована службой безопасности");
                } else {
                    deposit(accountFrom, accountTo, amount);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    private void deposit(Account from, Account to, long amount) {

        long balance = from.getMoney() - amount;
        if (balance < 0) {
            System.out.println("Недостаточно средств");
            return;
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
}

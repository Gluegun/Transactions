import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank extends Thread {

    @Getter
    @Setter
    private HashMap<String, Account> accounts;
    private final Lock lock = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();


    private final Random random = new Random();

    public synchronized boolean isFraud()
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public Bank() {
        accounts = new HashMap<>();
    }

    public void transfer(String accountNumFrom, String accountNumTo, long amount) {
        lock.lock();
        if (amount < 0) {
            System.out.println("Неверная сумма транзакции");
            lock.unlock();
            return;
        }

        Account accountFrom;
        Account accountTo;


        if (accounts.containsKey(accountNumFrom) && accounts.containsKey(accountNumTo)) {
            accountFrom = accounts.get(accountNumFrom);
            accountTo = accounts.get(accountNumTo);

            try {
                long balance = accountFrom.getMoney() - amount;
                if (balance < 0) {
                    System.out.println("Недостаточно средств");
                    return;
                }

                if (amount > 50000 && isFraud()) {
                    System.out.println("Операция заблокирована службой безопасности");
                    lock.lock();
                    lock2.lock();

                } else {
                    deposit(accountFrom, accountTo, amount);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();

            }


        } else {
            System.out.println("Неверно введен номер счета");
            lock.unlock();
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
            System.out.println("Недостаточно средств");
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

package org.example;

import java.util.concurrent.atomic.AtomicInteger;

class atomicity {
    private AtomicInteger balance;

    public atomicity(int initialBalance) {
        this.balance = new AtomicInteger(initialBalance);
    }

    public int getBalance() {
        return balance.get();
    }

    public void deposit(int amount) {
        balance.getAndAdd(amount);
    }

    public void withdraw(int amount) {
        balance.getAndAdd(-amount);
    }
}

public class AtomicityExample {
    public static void main(String[] args) {
        atomicity account1 = new atomicity(1000);
        atomicity account2 = new atomicity(2000);

        // Transaction 1: Transfer $500 from account1 to account2
        boolean successful1 = performTransaction(account1, account2, 100);
        if (successful1) {
            System.out.println("Transfer 1 successful!");
        } else {
            System.out.println("Transfer 1 failed!");
        }

        // Transaction 2: Transfer $700 from account2 to account1
        boolean successful2 = performTransaction(account2, account1, 200);
        if (successful2) {
            System.out.println("Transfer 2 successful!");
        } else {
            System.out.println("Transfer 2 failed!");
        }

        System.out.println("Account 1 balance: " + account1.getBalance());
        System.out.println("Account 2 balance: " + account2.getBalance());
    }

    private static boolean performTransaction(atomicity fromAccount, atomicity toAccount, int amount) {
        if (fromAccount.getBalance() >= amount) {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            return true;
        } else {
            System.out.println("Insufficient funds for the transaction! Rolling back...");
            return false;
        }
    }
}

public class Rollback {
    public static void main(String[] args) {
        // Set up two bank accounts with different balances.
        BankAccount account1 = new BankAccount(100);
        BankAccount account2 = new BankAccount(200);

        // Start a transaction.
        try {
            // Transfer money from account1 to account2.
            account1.withdraw(50);
            account2.deposit(50);

            // Check that the balances have been updated correctly.
            assert account1.getBalance() == 50;
            assert account2.getBalance() == 250;

            // If an error occurs, the transaction will be rolled back.
            throw new Exception("Simulating an error");
        } catch (Exception e) {
            // Rollback the transaction.
            account1.rollback();
            account2.rollback();

            // Check that the balances have been restored to their original values.
            assert account1.getBalance() == 100;
            assert account2.getBalance() == 200;
        }
    }
}

class BankAccount {
    private int balance;
    private int previousBalance;

    public BankAccount(int balance) {
        this.balance = balance;
        this.previousBalance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        // Update the previous balance before making the withdrawal.
        previousBalance = balance;
        balance -= amount;
    }

    public void deposit(int amount) {
        // Update the previous balance before making the deposit.
        previousBalance = balance;
        balance += amount;
    }

    public void rollback() {
        // Rollback the balance to the previous value.
        balance = previousBalance;
    }
}


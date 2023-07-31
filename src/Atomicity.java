import java.util.concurrent.atomic.AtomicInteger;

public class Atomicity {
    private static AtomicInteger account1Balance = new AtomicInteger(1000);
    private static AtomicInteger account2Balance = new AtomicInteger(2000);

    public static void main(String[] args) {
        // Create multiple threads to perform transactions simultaneously
        Thread[] threads = new Thread[1];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                // Simulating a transfer of $100 from account1 to account2
                int amount = 1;
                if (account1Balance.get() >= amount) {
                    // Perform the transfer atomically
                    account1Balance.getAndAdd(-amount);
                    account2Balance.getAndAdd(amount);
                    System.out.println("Transfer successful! Account 1 balance: " + account1Balance.get() +
                            ", Account 2 balance: " + account2Balance.get());
                } else {
                    System.out.println("Insufficient funds in Account 1! Rolling back...");
                    // Rollback the changes made during the failed transfer
                    account1Balance.getAndAdd(amount);
                    account2Balance.getAndAdd(-amount);
                    System.out.println("Rollback complete. Account 1 balance: " + account1Balance.get() +
                            ", Account 2 balance: " + account2Balance.get());
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the final balances of both accounts
        System.out.println("Final balance - Account 1: " + account1Balance.get() +
                ", Account 2: " + account2Balance.get());
    }
}

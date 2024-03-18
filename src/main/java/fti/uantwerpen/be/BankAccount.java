package fti.uantwerpen.be;

import java.util.ArrayList;
import java.util.HashMap;

public class BankAccount {
    private final String bankAccountNumber;
    private double balance;
    private final HashMap<Integer, User> linkedUsers = new HashMap<>();
    public BankAccount(String bankAccountNumber, User user) {
        this.bankAccountNumber = bankAccountNumber;
        this.linkedUsers.put(user.getUserId(), user);
        this.balance = 0;
    }

    public void addUser(User user) {
        linkedUsers.put(user.getUserId(), user);
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public HashMap<Integer, User> getLinkedUsers() {
        return linkedUsers;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "bankAccountNumber='" + bankAccountNumber + '\'' +
                ", balance=" + balance +
                ", linkedUsers=" + linkedUsers +
                '}';
    }
}

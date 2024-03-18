package fti.uantwerpen.be;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@RestController
public class Controller {
    HashMap<String, BankAccount> bankAccounts = new HashMap<>();
    HashMap<Integer, User> users = new HashMap<>();
    @PostMapping("/{userid}")
    public String registerUser(@PathVariable int userid) {
        if (users.containsKey(userid)) {
            return "This user has already been registered.";
        }
        users.put(userid, new User(userid));
        return "User with id = " + userid + " has been registered.";
    }
    @PostMapping("/bankaccount/{userid}/")
    public String createBankAccount(@PathVariable int userid) {
        if (!users.containsKey(userid)) {
            return "This user has not yet registered.";
        }
        // Create unique bankaccount number
        Random random = new Random(userid);
        String bankAccountNumber = "BE" + random.nextInt();
        BankAccount bankAccount = new BankAccount(bankAccountNumber, new User(userid));
        bankAccounts.put(bankAccountNumber, bankAccount);
        users.put(userid, new User(userid));
        return "Bankaccount for user with id = " + userid + " has been created with bankaccount number = " + bankAccountNumber;
    }
    @GetMapping("/{userid}/{bankaccountnumber}/balance")
    public String getBalance(@PathVariable int userid, @PathVariable String bankaccountnumber) {
        if (!users.containsKey(userid)) {
            return "This user has not yet registered.";
        }
        if (!bankAccounts.containsKey(bankaccountnumber)) {
            return "This bank account does not exist.";
        }
        if (!bankAccounts.get(bankaccountnumber).getLinkedUsers().containsKey(userid)) {
            return "This user is not linked to this bankaccount.";
        }
        return "The current balance is: " + bankAccounts.get(bankaccountnumber).getBalance();
    }
    @PostMapping("/{userid1}/{bankaccountnumber}/{userid2}")
    public String addUserToBankAccount(@PathVariable int userid1, @PathVariable String bankaccountnumber, @PathVariable int userid2) {
        if (!users.containsKey(userid1)) {
            return "User 1 has not yet registered.";
        }
        if (!users.containsKey(userid2)) {
            return "User 2 has not yet registered.";
        }
        if (!bankAccounts.containsKey(bankaccountnumber)) {
            return "This bankaccount does not exist.";
        }
        BankAccount bankAccount = bankAccounts.get(bankaccountnumber);
        if (bankAccount.getLinkedUsers().containsKey(userid1)) {
            return "This bankaccount is not linked to the first user.";
        }
        if (bankAccount.getLinkedUsers().containsKey(userid2)) {
            return "The second user has already been linked to this bank account.";
        }
        bankAccount.addUser(users.get(userid2));
        return "User has succesfully been added.";
    }
    @PutMapping("/{userid}/{bankaccountnumber}/balance/{amount}")
    public String addToBalance(@PathVariable int userid, @PathVariable String bankaccountnumber, @PathVariable int amount) {
        if (!users.containsKey(userid)) {
            return "This user has not yet registered.";
        }
        if (!bankAccounts.containsKey(bankaccountnumber)) {
            return "This bank account does not exist.";
        }
        if (!bankAccounts.get(bankaccountnumber).getLinkedUsers().containsKey(userid)) {
            return "This user is not linked to this bankaccount.";
        }
        if (amount <= 0) {
            return "Amount has to be positive.";
        }
        bankAccounts.get(bankaccountnumber).updateBalance(amount);
        return "Balance has succesfully been updated.";
    }
    @DeleteMapping("/{userid}/{bankaccountnumber}/balance/{amount}")
    public String withdrawFromBalance(@PathVariable int userid, @PathVariable String bankaccountnumber, @PathVariable int amount) {
        if (!users.containsKey(userid)) {
            return "This user has not yet registered.";
        }
        if (!bankAccounts.containsKey(bankaccountnumber)) {
            return "This bank account does not exist.";
        }
        if (!bankAccounts.get(bankaccountnumber).getLinkedUsers().containsKey(userid)) {
            return "This user is not linked to this bankaccount.";
        }
        if (amount >= 0) {
            return "Amount has to be negative.";
        }
        bankAccounts.get(bankaccountnumber).updateBalance(amount);
        return "Balance has succesfully been updated.";
    }
}

package lk.bank.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String username;
    private String password;
    private List<Account> accounts;
    private Boolean isLogged = false;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public void setAccount(int index,Account account){
        accounts.set(index,account);
    }

    public Boolean getLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }
}

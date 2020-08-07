package lk.bank.process;

import lk.bank.model.Account;
import lk.bank.model.User;
import lk.bank.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FundTransaction {

    //changed variable types to private
    private String from;
    private String cvv;
    private String to;
    private DataOutputStream dos;
    private DataInputStream dis;
    private User user;
    private double price;

    public FundTransaction(String from, String cvv, String to, DataOutputStream dos, DataInputStream dis, User user, Double price) {
        this.from = from;
        this.cvv = cvv;
        this.to = to;
        this.dos = dos;
        this.dis = dis;
        this.user = user;
        this.price = price;
    }

    public void doTransaction() throws IOException {

        Account account = null;
        Account tranferAccount = null;
        User tranferUser = null;

        for (Account acc : user.getAccounts()) {
            if (from.equals(acc.getAccno())) {
                account = acc;
                break;
            }
        }

        for (User user : Server.getUsers()) {
            for (Account acc : user.getAccounts()) {
                if (to.equals(acc.getAccno())) {
                    tranferAccount = acc;
                    tranferUser = user;
                    break;
                }
            }
        }

        //Used if cases to check whether the variable values are null prevents from getting null point exceptions
        if (account != null && tranferAccount != null) {

            if (account.getPrice() < price) {

                dos.writeUTF("The amout your trying to transfer is not available in your account\n" +
                        "Press enter to continue");

            } else {

                int index = user.getAccounts().indexOf(account);
                int transferIndex;

                account.setPrice(account.getPrice() - price);
                tranferAccount.setPrice(tranferAccount.getPrice() + price);

                user.setAccount(index, account);
                tranferUser.setAccount(index, tranferAccount);

                transferIndex = Server.getUsers().indexOf(tranferUser);
                index = Server.getUsers().indexOf(user);

                Server.getUsers().set(index, user);
                Server.getUsers().set(transferIndex, tranferUser);

                dos.writeUTF("Transaction Success\n" +
                        "Press enter to continue");
            }

        } else if (account == null) {

            dos.writeUTF("Your acc no is not registered under your acc\n" +
                    "Press enter to continue");

        } else if (tranferAccount == null) {

            dos.writeUTF("The account your trying to enter cash is not available\n" +
                    "Press enter to continue");

        }
    }
}

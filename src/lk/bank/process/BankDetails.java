package lk.bank.process;

import lk.bank.model.Account;
import lk.bank.model.User;

import java.io.DataOutputStream;
import java.io.IOException;

public class BankDetails {

    //changed variable types to private
    private User user;
    private DataOutputStream dos;

    public BankDetails(User user, DataOutputStream dos) {
        this.user = user;
        this.dos = dos;
    }

    public void getDetails() throws IOException {

        StringBuffer accountNos = new StringBuffer(); //Used String buffer

        accountNos.append("Account Details\n")
                .append("Account holder's name : ")
                .append(user.getName()).append("\nAccounts count :")
                .append(user.getAccounts().size())
                .append("\n---------------:\n");

        for (Account acc : user.getAccounts()) {

            accountNos.append(acc.getAccno())
                    .append(" Expire Date:")
                    .append(acc.getExpirationDate())
                    .append(" Accound balance: ")
                    .append(acc.getPrice() + "\n");
        }

        accountNos.append("\nHIT ENTER");

        dos.writeUTF(accountNos.toString());

    }
}

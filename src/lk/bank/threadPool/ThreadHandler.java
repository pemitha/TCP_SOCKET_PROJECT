package lk.bank.threadPool;

import lk.bank.model.User;
import lk.bank.process.BankDetails;
import lk.bank.process.FundTransaction;
import lk.bank.process.LoginProcess;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadHandler implements Runnable {

    //changed variable types to private
    private final Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private User user;
    private final String loginConst = "LOGIN";
    private String statusLogged = loginConst;
    private final String logoutConst = "LOGOUT";


    public ThreadHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {

        while (true) {
            try {
                if (user != null) {
                    if (!user.getLogged()) {
                        //as mentioned used constant values to set some values
                        statusLogged = loginConst;
                    } else {
                        statusLogged = logoutConst;
                    }
                } else {
                    statusLogged = loginConst;
                }

                StringBuffer welcomeMsg = new StringBuffer();  //Used String buffer
                welcomeMsg.append("Welcome to BANK SYSTEM (your connected on port :" + socket.getPort() + ")")
                        .append("\n 01. " + statusLogged)
                        .append("\n 02. BANK DETAILS")
                        .append("\n 03. FUND TRANSACTION")
                        .append("\n 04. EXIT")
                        .append("\n Please input a number for the following action");
                dataOutputStream.writeUTF(welcomeMsg.toString());

                String inputText = dataInputStream.readUTF();

                switch (inputText) {

                    case "01":
                        // replaced the codes inside with a method to make the code look more cleaner
                        loginProcess(statusLogged);
                        break;
                    case "02":
                        // replaced the codes inside with a method to make the code look more cleaner
                        bankDetails();
                        break;
                    case "03":
                        // replaced the codes inside with a method to make the code look more cleaner
                        fundTransaction();
                        break;
                    case "04":
                        dataOutputStream.writeUTF("Leaving");
                        dataInputStream.readUTF();
                        break;
                    default:
                        dataOutputStream.writeUTF("Invalid Input\n" +
                                "Press enter to continue");
                        dataInputStream.readUTF();

                }

                if (inputText.equals("04")) {
                    System.out.println("THREAD CLOSED..." + socket.getPort());
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Created three methods to make the changes
    private void fundTransaction() throws IOException {

        dataOutputStream.writeUTF("Please enter your acc no ,cvv , the acc your wishing to transfer cash and the price devided by a space");
        String transData = dataInputStream.readUTF();
        String from = transData.split(" ")[0];
        String cvv = transData.split(" ")[1];
        String to = transData.split(" ")[2];
        Double price = Double.valueOf(transData.split(" ")[3]);

        FundTransaction transaction = new FundTransaction(from, cvv, to, dataOutputStream, dataInputStream, user, price);
        transaction.doTransaction();
        dataInputStream.readUTF();

    }

    private void bankDetails() throws IOException {

        dataOutputStream.writeUTF("Requesting bank details\n" +
                "Press enter to continue");
        dataInputStream.readUTF();

        if (user != null) {
            BankDetails bank = new BankDetails(user, dataOutputStream);
            bank.getDetails();
            dataInputStream.readUTF();
        } else {
            dataOutputStream.writeUTF("Please Login First to use this funcation");
            dataInputStream.readUTF();
        }
    }

    private void loginProcess(String statusLogged) throws IOException {

        if (statusLogged.equals("LOGIN")) {

            dataOutputStream.writeUTF("Please enter your username and password devided by a space");
            String username = null;
            String password = null;
            try {

                String loginData = dataInputStream.readUTF();
                username = loginData.split(" ")[0];
                password = loginData.split(" ")[1];

            } catch (ArrayIndexOutOfBoundsException e) {

                dataOutputStream.writeUTF("Please enter the credentials according to the right format");
                String loginData = dataInputStream.readUTF();
                username = loginData.split(" ")[0];
                password = loginData.split(" ")[1];

            }

            LoginProcess process = new LoginProcess(username, password, dataOutputStream, dataInputStream);
            user = process.Login();

        } else if (statusLogged.equals("LOGOUT")) {

            this.user = null;
            dataOutputStream.writeUTF("Logout success\n" +
                    "Press enter to continue");
            dataInputStream.readUTF();

        }
    }
}

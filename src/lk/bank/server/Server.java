package lk.bank.server;

import lk.bank.model.Account;
import lk.bank.model.User;
import lk.bank.threadPool.ThreadHandler;
import lk.bank.threadPool.ThreadPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

    static ArrayList<User> users;  //created two methods to load and get the array list

    public static void main(String[] args) throws IOException {

        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;

        try (ServerSocket serverSocket = new ServerSocket(5000)) { //used a try finally bloack

            ThreadPool threadPool = new ThreadPool(2);
            Scanner scn = new Scanner(System.in);
            System.out.println("SERVER STARTED ON PORT 5000");
            loadUsers();  //called the newly created user loading method

            while (true) {   //After all the threads finish their work server shutdowns itself or we can call shutdownImmediately() method in the ThreadPool class

                socket = serverSocket.accept();
                System.out.println("Client Connected on port :" + socket.getPort());

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                ThreadHandler threadHandler = new ThreadHandler(socket, dataInputStream, dataOutputStream);
                threadPool.execute(threadHandler);

                String InputText = scn.nextLine();
                if (InputText.equals("Shutdown")) {

                    threadPool.shutdownImmediately();
                    if(scn!=null){scn.close();}//closing the scanner
                    break; //not a endless while shutting down the server after receiving the input shutdown

                }

            }

        } finally {
            if (socket != null) {
                socket.close();
            } else if (dataInputStream != null) {
                dataInputStream.close();
            } else if (dataOutputStream != null) {
                dataOutputStream.close();
            }
        }
    }

//    Tried using try finally block but ended up getting a 'socket closed' error even after several tries, there for used a try catch and closed the socket and both the streams in the finally scope

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void loadUsers() {

        users = new ArrayList<>();

        User user = new User("Gayan", "gayan", "123");
        user.addAccount(new Account("1111222233", "111", "11/21", 850000.0));
        user.addAccount(new Account("2222333311", "123", "12/21", 350000.0));

        User user1 = new User("Pasindu", "pasindu", "123");
        user1.addAccount(new Account("0123456789", "222", "11/21", 650000.0));

        users.add(user);
        users.add(user1);

    }
}

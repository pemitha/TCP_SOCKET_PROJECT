package lk.bank.process;

import lk.bank.model.User;
import lk.bank.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LoginProcess {

    //changed variable types to private
    private String username;
    private String password;
    private DataOutputStream dos;
    private DataInputStream dis;

    public LoginProcess(String username, String password, DataOutputStream dos, DataInputStream dis) {
        this.username = username;
        this.password = password;
        this.dos = dos;
        this.dis = dis;
    }

    public User Login() throws IOException {

        ArrayList<User> users = Server.getUsers();//used the method created for getting static user accounts
        boolean b = true;
        User loggedUser = null;

        for (User user : users) {

            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {

                dos.writeUTF("Successfully logged in\n" +
                        "Press enter to continue");
                user.setLogged(true);
                loggedUser = user;
                dis.readUTF();
                b = false;

            }
        }

        if (b) {

            dos.writeUTF("Invalid credentials or user does't exists\n" +
                    "Press enter to continue");
            dis.readUTF();
        }

        return loggedUser;
    }
}

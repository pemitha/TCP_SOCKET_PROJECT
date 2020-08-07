package lk.bank.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        //Used try finally block to reformat the codes
        try (Socket socket = new Socket("127.0.0.1", 5000)) {
            try (Scanner scn = new Scanner(System.in)) {
                try (DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
                    try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
                        while (true) {
                            String read = dataInputStream.readUTF();
                            System.out.println(read);

                            String InputText = scn.nextLine();
                            dataOutputStream.writeUTF(InputText);

                            if (read.equals("Leaving")) {
                                System.out.println("DISCONNECTED FROM THE SERVER SUCCESSFULLY");
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Con4_Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author Tolga
 */
// dinleme threadi
class ServerThread extends Thread { // server , clientten gelenlere göre karar verip hamle yapacak ve geri dönüs yapacak

    public void run() {

        // while (true) {
        try {
            Server.Display("Client Bekleniyor...");

            Socket clientSocket = Server.serverSocket.accept();

            Server.Display("Client Geldi...");

            ObjectOutputStream sOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream sInput = new ObjectInputStream(clientSocket.getInputStream());

            sOutput.writeObject("Hosgeldin");
            Server.Display(sInput.readObject().toString());

        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        // }
    }
}

public class Server {

    //server soketi eklemeliyiz
    public static ServerSocket serverSocket;
    // Serverın dileyeceği port
    public static int port = 0;
    //Serverı sürekli dinlemede tutacak thread nesnesi
    public static ServerThread runThread;

    public static void Start(int openport) {
        try {
            Server.port = openport;
            // serversoket nesnesi
            Server.serverSocket = new ServerSocket(Server.port);
            
            Server.Display("Client Bekleniyor...");

            Socket clientSocket = Server.serverSocket.accept();

            Server.Display("Client Geldi...");

            ObjectOutputStream sOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream sInput = new ObjectInputStream(clientSocket.getInputStream());
            
            sOutput.writeObject("Server:Hosgeldin");
            
            
            Server.Display(sInput.readObject().toString());
            
//            Server.runThread = new ServerThread();
//            Server.runThread.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public static void Display(String msg) {

        System.out.println("Connected 4 is ready");

    }

}
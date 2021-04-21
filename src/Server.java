

import java.io.*;
import java.util.*;
import java.net.*;


public class Server
{

    // Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();


    public static void main(String[] args) throws IOException
    {
        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(1234);

        Socket s;

        System.out.println("SERVER ONLINE");



        while (true)
        {
            // Accept the incoming request
            s = ss.accept();

            System.out.println("New client request received : " + s);

            // obtain input and output streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());


            // Create a new handler object for handling this request.
            ClientHandler mtch = new ClientHandler(s,  dis, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(mtch);

            System.out.println("Adding client to active client list");

            // add this client to active clients list
            ar.add(mtch);

            // start the thread.
            t.start();


            System.out.println("RECIPANTS: "+ar);

        }
    }
}

// ClientHandler class
class ClientHandler implements Runnable
{

    final static String QUITMESSAGE = "!--QUIT-->";

    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;

    // constructor
    public ClientHandler(Socket s,
                         DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.s = s;
        this.isloggedin=true;
    }

    @Override
    public void run() {

        String received;
        while (true)
        {

            try
            {
                // receive the string
                received = dis.readUTF();
                System.out.println(received);
                String[] client_msg = received.split("#", 3);

                System.out.println("RECIPANT Client: "+Server.ar);

                // search for the recipient in the connected devices list.
                // ar is the vector storing client of active users
                for (ClientHandler mc : Server.ar)
                {

                    if (mc != this && mc.isloggedin)
                    {
                        try{
                            mc.dos.writeUTF(received);} catch (Exception e){System.out.println(e);}
                    }
                }

                if(client_msg[2].equals(QUITMESSAGE)){
                    System.out.println("EXITING");
                    this.isloggedin=false;
                    this.s.close();
                    break;
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        try
        {
            this.dis.close();
            this.dos.close();
            Server.ar.remove(this);


        }catch(IOException e){
            e.printStackTrace();
            System.out.println("ERROR:");
        }
    }
}


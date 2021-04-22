import java.io.*;
import java.util.*;
import java.net.*;


public class Server
{

    // Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();


    public static void main(String[] args) throws IOException
    {

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


            ClientHandler mtch = new ClientHandler(s,  dis, dos);
            Thread t = new Thread(mtch);
            ar.add(mtch);

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


    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
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

                received = dis.readUTF();
                System.out.println(received);
                String[] client_msg = received.split("#", 3);


                for (ClientHandler mc : Server.ar)
                {
                    if (mc != this && mc.isloggedin) //send to only other clients
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


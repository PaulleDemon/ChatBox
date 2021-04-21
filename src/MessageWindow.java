import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;


public class MessageWindow extends JFrame implements ActionListener{

    private static JPanel panel = new JPanel(new GridBagLayout());

    private static JTextArea text_area = new JTextArea();
    private static JScrollPane scrolltext = new JScrollPane(text_area);

    private static JButton send_btn = new JButton("Send");

    private static ScrollArea scroll_area = new ScrollArea();

    private static GridBagConstraints g_constraint = new GridBagConstraints();
    private Insets inset = new Insets(5, 5, 5, 5);

//    private static String user_name="";

    MessageWindow(String name) throws IOException {

//        user_name = name;

        g_constraint.fill = GridBagConstraints.BOTH;
        g_constraint.insets = inset;

        g_constraint.weightx = 1;
        g_constraint.weighty = 1;

        g_constraint.gridx = 0;
        g_constraint.gridy = 0;
        g_constraint.gridwidth = 2;

        panel.add(scroll_area, g_constraint);

        g_constraint.gridwidth = 1;
        g_constraint.weightx = 0.7;
        g_constraint.weighty = 0.5;
        g_constraint.gridx = 0;
        g_constraint.gridy = 1;

        text_area.setWrapStyleWord(true);
        panel.add(scrolltext, g_constraint);

        g_constraint.weightx = 0.2;
        g_constraint.weighty = 0;
        g_constraint.gridx = 1;
        g_constraint.gridy = 1;

        send_btn.addActionListener(this);
        panel.add(send_btn, g_constraint);

        add(panel);
        setSize(300, 300);

        new Client(name);


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit the program?", "Exit Program Message Box",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    Client.running = false;
                    Client.sendMessage(Client.QUITMESSAGE);
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                }

                else
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            }
        });

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        String text = text_area.getText().trim();

        if (text.length() > 0) {
//            text = breakWords(text, 10);
//            System.out.println(text);

            text_area.setText("");
            Client.sendMessage(text);
        }
    }



//    private String breakWords(String str, int width){
//
//        String new_string = "";
//
//        for (int i=0; i<str.length(); i++){
//            if (i%width == 0){
//                new_string += "\n";
//            }
//            new_string += str.charAt(i);
//        }
////        new_string += "</html>";
//        return new_string;
//    }

    // todo: Label taking all the space
    private String breakWords(String str, int width){
        final String html = "<html><body style='width: %1spx;'>%1s";
        return String.format(html, width, str);
    }

    // handle incoming and outgoing messages
    public static class Client {


        final static int ServerPort = 1234;
        final static String QUITMESSAGE = "!--QUIT-->";

        private static InetAddress ip;
        private static Socket s;

        private static DataInputStream dis;
        private static DataOutputStream dos;

        private static boolean running = true;

        private static String client_name;

        Client(String name) throws IOException {
            client_name = name;
            start();
        }

        public static void start() throws IOException {
            Scanner scn = new Scanner(System.in);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection
            Socket s = new Socket(ip, ServerPort);

            // obtaining input and out streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            receiveMessage();

        }


        /*private static void _sendMessage(String message){

            try {
                // write on the output stream
                System.out.println("ClientName: "+ client_name);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa");
                String formattedDate = dateFormat.format(new Date()).toString();

                dos.writeUTF(client_name+"#"+ formattedDate +"#"+message);
//              ScrollArea.send_message(message);

                System.out.println(message);

            } catch (IOException e){e.printStackTrace(); System.out.println("SEND ERROR");}

        }*/


        public static void sendMessage(String message){

            new Thread(() -> {
               try{

                   SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
                   String formattedDate = dateFormat.format(new Date());

                   String[] date_msg = {formattedDate, message};

                   SwingUtilities.invokeLater(() -> ScrollArea.send_message(date_msg));

                   dos.writeUTF(client_name+"#"+ formattedDate +"#"+message);

               } catch (Exception e){System.out.println(e);}
            }).start();


        }

        public static void receiveMessage() {


            new Thread(() -> { // lambda function
                while (running) {
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();

                        String[] client_msg = msg.split("#", 3);
                        System.out.println("Received Msg: "+String.join(": ", client_msg) + ";" +client_msg[0]);

                        SwingUtilities.invokeLater(() -> ScrollArea.receive_message(client_msg));

                    } catch (IOException e) { System.out.println("Receive ERROR"+e);}

                }
            }


            ).start();
        }


    }


}

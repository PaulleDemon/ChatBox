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
import java.text.SimpleDateFormat;

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

    MessageWindow(String name) throws IOException {

        text_area.setBackground(new Color(59, 56, 56));
        text_area.setForeground(new Color(227, 225, 225));
        text_area.setFont(new Font(text_area.getFont().getFontName(), Font.PLAIN, 20));
        text_area.setCaretColor(new Color(227, 225, 225));

        panel.setBackground(new Color(59, 56, 56));

        send_btn.setBackground(new Color(55, 138, 211));

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
            text_area.setText("");
            Client.sendMessage(text);
        }
    }


    // handle incoming and outgoing messages
    public static class Client {


        final static int ServerPort = 1234;
        final static String QUITMESSAGE = "!--QUIT-->";
        final static String JOINEDMESSAGE = "!--Joined-->";

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


            InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip, ServerPort);

            // obtaining input and out streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            dos.writeUTF(client_name+"# #"+ JOINEDMESSAGE);

            receiveMessage();

        }



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

                        String msg = dis.readUTF();

                        String[] client_msg = msg.split("#", 3);  // client-name, date, message

                        if (!client_msg[2].equals(QUITMESSAGE) && !client_msg[2].equals(JOINEDMESSAGE))
                            SwingUtilities.invokeLater(() -> ScrollArea.receive_message(client_msg));

                        if(client_msg[2].equals(JOINEDMESSAGE)){
                            SwingUtilities.invokeLater(()-> ScrollArea.add_remove_user(client_msg[0]));
                        }

                        if(client_msg[2].equals(QUITMESSAGE))
                            SwingUtilities.invokeLater(() -> ScrollArea.add_remove_user(client_msg[0], true));

                    } catch (IOException e) { System.out.println("Receive ERROR"+e);}

                }
            }


            ).start();
        }


    }


}

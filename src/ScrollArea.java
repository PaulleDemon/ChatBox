import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class ScrollArea extends JPanel {

    static JScrollPane scroll_area = new JScrollPane();
    static JPanel panel = new JPanel();
    private static Color lbl_color = new Color(171, 171, 171);
    private static EmptyBorder lbl_padding = new EmptyBorder(10, 10,10, 10);

    ScrollArea(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBackground(Color.BLACK);

        scroll_area.getViewport().add(panel);
        scroll_area.createVerticalScrollBar();
        scroll_area.createHorizontalScrollBar();

//        scroll_area.getViewport().setBackground(Color.green);

        add(scroll_area);
        setSize(300, 30);

    }

    public static void send_message(String[] message){
//        JLabel mess_lbl = new JLabel(message, JLabel.RIGHT);
        System.out.println("Message: "+message[0]+message[1]);
        CustomLabel mess_lbl = new CustomLabel("", message[1], message[0]);

        mess_lbl.setBorder(lbl_padding);
        mess_lbl.setOpaque(true);
        mess_lbl.setBackground(lbl_color);
//        mess_lbl.setHorizontalAlignment();

        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(mess_lbl);
        panel.updateUI();

        adjustScroll();
    }

    public static void receive_message(String[] message){

        String msg = String.join(": ", message);

        JLabel mess_lbl = new JLabel(msg);

        mess_lbl.setBorder(lbl_padding);
        mess_lbl.setOpaque(true);
        mess_lbl.setBackground(lbl_color);
        mess_lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(mess_lbl);
        panel.updateUI();

        adjustScroll();

    }

    private static void add_remove_user_label(String message){

        JLabel mess_lbl = new JLabel(message);

        mess_lbl.setBorder(lbl_padding);
        mess_lbl.setOpaque(true);
        mess_lbl.setBackground(lbl_color);
        mess_lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(mess_lbl);
        panel.updateUI();

    }

    public static void add_remove_user(String user){
        add_remove_user_label("'" + user + "' Joined the room.");
    }

    public static void add_remove_user(String user, boolean remove){


        if (remove){
            add_remove_user_label("'" + user + "' Joined the room.");
        }

        else{
            add_remove_user_label("'" + user + "' Left the room.");
        }

    }

    private static void adjustScroll(){

        SwingUtilities.invokeLater(() -> {
            JScrollBar sb = scroll_area.getVerticalScrollBar();
            sb.setValue(sb.getMaximum());
        });



    }


}

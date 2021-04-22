import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.text.MessageFormat;


public class ScrollArea extends JPanel {

    static JScrollPane scroll_area = new JScrollPane();
    static JPanel panel = new JPanel();

    private static final Color send_lbl_color = new Color(87, 151, 107);
    private static final Color receive_lbl_color = new Color(101, 114, 117);
    private static final Color join_recv_lbl_color = new Color(113, 40, 181);
    private static final Color scroll_bar_color = new Color(59, 56, 56);
    private static final Color scroll_bar_thumb_color = new Color(130, 135, 141, 150);

    private static final EmptyBorder lbl_padding = new EmptyBorder(10, 10,10, 10);
    private static final GridBagConstraints gbc = new GridBagConstraints();
    private static final int wordWrap = 50;

    ScrollArea(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(59, 56, 56));

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);


        scroll_area.getVerticalScrollBar().setBackground(scroll_bar_color);
        scroll_area.getHorizontalScrollBar().setBackground(scroll_bar_color);

        scroll_area.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = scroll_bar_thumb_color;
            }
        });

        scroll_area.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = scroll_bar_thumb_color;
            }
        });

        scroll_area.getViewport().add(panel);
        scroll_area.createVerticalScrollBar();
        scroll_area.createHorizontalScrollBar();



        add(scroll_area);
        setSize(300, 30);

    }

    private static String breakWords(String str, int width){

        String new_string = "";

        for (int i=0; i<str.length(); i++){

            if (i%width == 0 && i!=0){
                new_string += "<br>";
            }
            new_string += str.charAt(i);
        }

        new_string = new_string.replaceAll("\n", "<br>");

        return new_string;
    }

    public static void send_message(String[] message){

        gbc.anchor = GridBagConstraints.EAST;

        String date_time = message[0];
        String msg = breakWords(message[1], wordWrap);

        String text = MessageFormat.format("<html><font color='#F9F6F6' font size='5'>{0}<br></font> " +
                "<font color='#E1DDDC' font size='2'>{1}</font><font size='7'></font></html>",  msg, date_time);

        JLabel mess_lbl = new JLabel(text, JLabel.RIGHT);

        mess_lbl.setBorder(lbl_padding);
        mess_lbl.setOpaque(true);
        mess_lbl.setBackground(send_lbl_color);

        panel.add(mess_lbl, gbc);

        panel.revalidate();
        panel.repaint();

        adjustScroll();
    }

    public static void receive_message(String[] message){

        String client_name, date_time, msg;
        client_name = message[0];
        date_time = message[1];
        msg = breakWords(message[2], wordWrap);

        String text = MessageFormat.format("<html><font color='#a1a1a1' font size='3'>~{0}<br></font>" +
                        "<font color='#F9F6F6' font size='5'>{1}<br></font> <font color='#E1DDDC' font size='2'>{2}</font>" +
                        "<font size='7'></font></html>", client_name, msg, date_time);

        gbc.anchor = GridBagConstraints.WEST;

        JLabel mess_lbl = new JLabel(text);

        mess_lbl.setBorder(lbl_padding);
        mess_lbl.setOpaque(true);
        mess_lbl.setBackground(receive_lbl_color);
        mess_lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(mess_lbl, gbc);
        panel.revalidate();
        panel.repaint();

        adjustScroll();

    }

    private static void add_remove_user_label(String message){

        JLabel mess_lbl = new JLabel(message);

        gbc.anchor = GridBagConstraints.CENTER;

        mess_lbl.setBorder(lbl_padding);
        mess_lbl.setOpaque(true);
        mess_lbl.setBackground(join_recv_lbl_color);
        mess_lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(mess_lbl, gbc);

        panel.revalidate();
        panel.repaint();

        adjustScroll();

    }

    public static void add_remove_user(String user){
        add_remove_user_label("<html><font color='#ffffff'>'" + user + "' Joined the room.</font></html>");
    }

    public static void add_remove_user(String user, boolean remove){


        if (!remove){
            add_remove_user_label("<html><font color='#ffffff'>'" + user + "' Joined the room.</font></html>");
        }

        else{
            add_remove_user_label("<html><font color='#ffffff'>'" + user + "' Left the room.</font></html>");
        }

    }

    private static void adjustScroll(){

        SwingUtilities.invokeLater(() -> {
            JScrollBar sb = scroll_area.getVerticalScrollBar();
            sb.setValue(sb.getMaximum());
        });

    }


}

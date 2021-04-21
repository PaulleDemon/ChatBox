import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JPanel {

    public String sender="";
    public String date_time ="";
    public String message="";

    private JLabel date_time_lbl;
    private JLabel message_lbl;
    private JLabel sender_lbl;

    private GridBagConstraints constraints = new GridBagConstraints();
    private Insets insets = new Insets(5, 5, 5, 5);


    CustomLabel(String sender, String message, String date_time){
        this.date_time = this.date_time;

        this.message = message;
        this.sender = sender;

        this.date_time_lbl = new JLabel(this.date_time);
        this.date_time_lbl.setAlignmentX(Component.RIGHT_ALIGNMENT);

        this.message_lbl = new JLabel(this.message);
        this.message_lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.sender_lbl = new JLabel(this.sender);
        this.sender_lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.setLayout(new GridBagLayout());

        this.constraints.fill = GridBagConstraints.BOTH;
        this.constraints.weightx = 1;
        this.constraints.weighty = 1;

        this.constraints.gridwidth=1;
        this.constraints.gridx=0;
        this.constraints.gridy=0;
        this.add(this.sender_lbl, constraints);

        this.constraints.gridx=0;
        this.constraints.gridy=1;
        this.constraints.gridwidth=2;
        this.add(this.message_lbl, constraints);


        this.constraints.gridx=0;
        this.constraints.gridy=2;
        this.constraints.gridwidth=1;
        this.add(this.date_time_lbl, constraints);

//        resize();
        System.out.println("CUSTOM LABEL");
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        resize();
//    }

    private void resize(){
        int width = this.getWidth();
        int height = this.getHeight();

        this.setSize(width, height);
        System.out.println("RESIZE: "+width+";"+height);
    }


    public void setDate_time(String date_time){
        this.date_time = date_time;
        this.date_time_lbl.setText(date_time);
        resize();
    }

    public void setMessage(String message){
        this.date_time = message;
        this.date_time_lbl.setText(message);
        resize();
    }

    public void setSender(String sender){
        this.date_time = sender;
        this.date_time_lbl.setText(sender);
        resize();
    }

    public void setForegroundColor(Color message_foreground, Color sender_foreground, Color date_timeForeground){

    }


}


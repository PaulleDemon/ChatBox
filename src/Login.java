import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JPanel{

    private JLabel user_name;
    private static JTextField userName_text;

    private GridBagConstraints g_constraint = new GridBagConstraints();
    private Insets inset = new Insets(5, 5, 5, 5);

    Login(){

        setLayout(new GridLayout());
        user_name = new JLabel("Enter Your name: ");
        userName_text = new JTextField();

        g_constraint.fill = GridBagConstraints.HORIZONTAL;
        g_constraint.gridx = 0;
        g_constraint.gridy = 0;
        g_constraint.insets = inset;

        add(user_name, g_constraint);

        g_constraint.fill = GridBagConstraints.HORIZONTAL;
        g_constraint.gridx = 1;
        g_constraint.gridy = 0;
        g_constraint.ipadx = 250;

        add(userName_text, g_constraint);


    }

    public static String get_user_name(){
        return userName_text.getText();
    }

}

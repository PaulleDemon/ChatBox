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
        user_name.setForeground(new Color(227, 225, 225));

        userName_text = new JTextField();
        userName_text.setCaretColor(new Color(227, 225, 225));
        userName_text.setBackground(new Color(94, 93, 93));
        userName_text.setForeground(new Color(227, 225, 225));

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
        setBackground(new Color(59, 56, 56));
    }

    public static String get_user_name(){
        return userName_text.getText();
    }

}

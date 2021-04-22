import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.IOException;

public class Controller {

    public static String user_name;

    Controller() throws IOException {
        Login login = new Login();

        UIManager.put("OptionPane.background",new ColorUIResource(59, 56, 56));
        UIManager.put("Panel.background",new ColorUIResource(59, 56, 56));

        UIManager.put("OptionPane.foreground", new ColorUIResource(227, 225, 225));
        UIManager.put("Button.background", new Color(55, 138, 211));
        UIManager.put("Button.foreground", new Color(227, 225, 225));

        int result = JOptionPane.showConfirmDialog(null, login,"Join", JOptionPane.DEFAULT_OPTION,
                                                                                        JOptionPane.PLAIN_MESSAGE);

        if (result==JOptionPane.OK_OPTION)

            if (Login.get_user_name() != null){
                user_name = Login.get_user_name();
                System.out.println("Name: "+ user_name);

                MessageWindow msg_window = new MessageWindow(user_name);
                msg_window.setVisible(true);

            }
    }

}

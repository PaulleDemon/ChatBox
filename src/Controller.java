import javax.swing.*;
import java.io.IOException;

public class Controller {

    public static String user_name;

    Controller() throws IOException {
        Login login = new Login();

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

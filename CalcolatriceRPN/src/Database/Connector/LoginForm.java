package Database.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Calcolatrice.RPN.*;
public class LoginForm {
    public JPanel panel1;
    private JButton btnLogin;
    private JButton btnSignIn;
    private JTextField txtUsername;
    private JPasswordField pwfPassword;
    //private JTextField txtPassword;

    public LoginForm() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(pwfPassword.getPassword());
                Database db = new Database();   //creo un nuovo database
                if(db.login(username,password) == 1){   //se il login è avvenuto con successo
                    JFrame frame = new JFrame("Calcolatrice");
                    frame.setContentPane(new Calcolatrice().pnlCalcolatrice);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "login non riuscito");
                    txtUsername.setText("");
                    pwfPassword.setText("");
                }
            }
        });
        btnSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    //apro form registrazione
                JFrame frame = new JFrame("LoginForm");
                frame.setContentPane(new RegisterForm().Registrazione);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginForm");
        frame.setContentPane(new LoginForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

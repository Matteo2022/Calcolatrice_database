package Database.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterForm {
    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegistra;
    public JPanel Registrazione;


    public RegisterForm() {
        btnRegistra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database db = new Database();   //creo un nuovo database
                String nome = txtNome.getText();
                String cognome = txtCognome.getText();
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                try{
                    if(db.registrazione(nome,cognome,username,password) == 1){  //se il metodo registrazione ritorna 1 (quindi è avvenuto con sucesso)
                        JOptionPane.showMessageDialog(null, "registrazione completata con successo!");
                        JFrame frame = new JFrame("LoginForm");
                        frame.setContentPane(new LoginForm().panel1);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                    }
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RegisterForm");
        frame.setContentPane(new RegisterForm().Registrazione);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

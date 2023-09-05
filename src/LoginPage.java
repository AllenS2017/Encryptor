import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginPage extends JFrame implements ActionListener {
    HashMap<String, String> loginData;
    public LoginPage(){

        loginData = new LoginInformation().getLoginData();

        this.setTitle("Login");
        this.setBounds(10, 10, 400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(10, 10, 80, 25);
        this.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(10, 40, 80, 25);
        this.add(passwordLabel);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(100, 10, 160, 25);
        this.add(usernameText);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        this.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 80, 80, 25);
        this.add(loginButton);

        this.setVisible(true);

        loginButton.addActionListener(e->{
            String username = usernameText.getText();
            char[] password = passwordText.getPassword();

            if(loginData.containsKey(username)){
                if(loginData.get(username).equals(new String(password))){
                    new Frame();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong password!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Wrong username!");
            }
        });


    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

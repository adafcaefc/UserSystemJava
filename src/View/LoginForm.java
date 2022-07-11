package View;

import Controller.DatabaseManager;
import Model.User;

import javax.swing.*;
import java.sql.SQLException;

public class LoginForm extends BaseActionForm
{
    private JTextField emailField = new JTextField();
    private JTextField passwordField = new JPasswordField();
    private JLabel imageIcon = new JLabel(new ImageIcon("logo.png"));

    public LoginForm()
    {
        super();

        this.setTitle("Login");
        this.continueButton.setText("Login");

        this.addMenu(this.imageIcon);
        this.addMenu("Email", this.emailField);
        this.addMenu("Password", this.passwordField);

        this.continueButton.addActionListener(e ->
        {
            try
            {
                var users = DatabaseManager.getInstance().getUsers("user", "id");
                boolean login = false;
                for (var user : users)
                {
                    if (user.getEmail().equals(this.emailField.getText()) && user.getPassword().equals(this.passwordField.getText()))
                    {
                        login = true;
                    }
                }
                String temp = login ? "success!" : "failed!";
                JOptionPane.showMessageDialog(null, "Login " + temp, "Login", JOptionPane.INFORMATION_MESSAGE);
                if (login)
                {
                    new UserDataForm().setVisible(true);
                }
            }
            catch (SQLException exception)
            {
                JOptionPane.showMessageDialog(
                        null,
                        "Unexpected error:  " + exception.getMessage(),
                        "Could not connect to SQL server",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        this.buildForm();
    }
}

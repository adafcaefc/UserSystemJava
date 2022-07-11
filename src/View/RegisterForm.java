package View;

import Controller.DatabaseManager;
import Model.User;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.SQLException;

public class RegisterForm extends BaseActionForm
{
    private JTextField nameField = new JTextField();
    private JTextField emailField = new JTextField();
    private JTextField passwordField = new JPasswordField();
    private JFileChooser fileChooser = new JFileChooser();
    private JComboBox<String> categoryChooser = new JComboBox<>();

    public RegisterForm()
    {
        super();

        this.setTitle("Register");
        this.continueButton.setText("Register");

        this.addMenu("Email", this.emailField);
        this.addMenu("Name", this.nameField);
        this.addMenu("Password", this.passwordField);
        this.addMenu(this.fileChooser);
        this.addMenu("Category", this.categoryChooser);

        try
        {
            var categories = DatabaseManager.getInstance().getUserCategories("category", "id");
            for (var category : categories)
            {
                this.categoryChooser.addItem(category.getName());
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

        this.continueButton.addActionListener(e ->
        {
            var user = new User(0,
                    this.categoryChooser.getSelectedIndex(),
                    this.nameField.getText(),
                    this.emailField.getText(),
                    this.passwordField.getText(),
                    this.fileChooser.getCurrentDirectory().getPath());

            try
            {
                DatabaseManager.getInstance().insert("user", user.getData());
                JOptionPane.showMessageDialog(null, "Register success!", "Register", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
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

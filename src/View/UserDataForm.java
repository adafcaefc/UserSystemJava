package View;

import Controller.DatabaseManager;
import Model.CategoryUser;
import Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDataForm extends BaseActionForm
{
    private JComboBox<String> categoryChooser = new JComboBox<>();
    private JTextField searchField = new JTextField();
    private JButton searchButton = new JButton("Search");

    private JTable userTable = new JTable();
    private JScrollPane userTablePanel = new JScrollPane(this.userTable);

    public ArrayList<User> userList = new ArrayList<>();
    public ArrayList<CategoryUser> categoryUserList = new ArrayList<>();

    public void setTableData(String[] header, ArrayList<ArrayList<Object>> data)
    {
        DefaultTableModel tableModel = new DefaultTableModel(header, 0);
        for (var row : data) { tableModel.addRow(row.toArray()); }

        this.userTable.setModel(tableModel);
        this.userTable.setDefaultEditor(Object.class, null);
        this.userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void applySearch()
    {
        try
        {
            var categories = DatabaseManager.getInstance().getUserCategories("category", "id");
            var users = DatabaseManager.getInstance().getUsers("user", "id");

            this.categoryChooser.removeAllItems();
            for (var category : categories)
            {
                this.categoryChooser.addItem(category.getName());
            }

            var matrix = new ArrayList<ArrayList<Object>>();
            for (var user : users)
            {
                ArrayList<Object> inner = new ArrayList<>();
                inner.add(user.getName());
                inner.add(user.getEmail());
                inner.add(categories.get(user.getIdCategory()).getName());
                if (user.getIdCategory() == this.categoryChooser.getSelectedIndex() && user.getName().equals(this.searchField.getText()))
                {
                    matrix.add(inner);
                }
            }

            String[] header = new String[] { "name", "email", "category" };

            this.setTableData(header, matrix);
        }
        catch (SQLException exception)
        {
            JOptionPane.showMessageDialog(
                    null,
                    "Unexpected error:  " + exception.getMessage(),
                    "Could not connect to SQL server",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public UserDataForm()
    {
        super();

        this.setTitle("User Data");
        this.continueButton.setVisible(false);

        this.addMenu("Category", this.categoryChooser);
        this.addMenu("Name", this.searchField);
        this.addMenu(this.searchButton);
        this.addMenu(this.userTablePanel);

        try
        {
            String[] header = new String[] { "name", "email", "category" };

            var categories = DatabaseManager.getInstance().getUserCategories("category", "id");
            var users = DatabaseManager.getInstance().getUsers("user", "id");

            this.categoryChooser.removeAllItems();
            for (var category : categories)
            {
                this.categoryChooser.addItem(category.getName());
            }

            var matrix = new ArrayList<ArrayList<Object>>();
            for (var user : users)
            {
                ArrayList<Object> inner = new ArrayList<>();
                inner.add(user.getName());
                inner.add(user.getEmail());
                inner.add(categories.get(user.getIdCategory()).getName());
                matrix.add(inner);
            }

            this.setTableData(header, matrix);

            this.searchButton.addActionListener(e ->
            {
                this.applySearch();
            });
        }
        catch (SQLException exception)
        {
            JOptionPane.showMessageDialog(
                    null,
                    "Unexpected error:  " + exception.getMessage(),
                    "Could not connect to SQL server",
                    JOptionPane.ERROR_MESSAGE);
        }

        this.buildForm();
    }
}

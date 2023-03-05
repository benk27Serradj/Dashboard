package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Main extends JFrame implements ActionListener {
    public static JTextField amountField;
    public JTextField descriptionField;

    public JTextField totalField;//

    public JComboBox<String> categoryBox;
    public JButton addButton;

    public JButton removeButton;
    public JButton resetButton;

    public JButton saveButton;

    public JLabel totalLabel;
    public ArrayList<Expense> expenseList = new ArrayList<>();

    double amount ;

    double total = 0; // use in the save method
    String description;
    String category;
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Main main = new Main();
        main.init();
    }

    Connection conn = null;
    Statement stmt = null;
    public void init() throws ClassNotFoundException, SQLException {


        // Connect to the database
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        conn = DriverManager.getConnection("jdbc:hsqldb:file:mydatabase.;hsqldb.lock_file=false", "SA", "");
        stmt = conn.createStatement();
        System.out.println("Connected to database.");


        String sql = new String();

        // Set up the main window
        setTitle("Expense Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
        ImageIcon image = new ImageIcon("https://th.bing.com/th/id/R.cd6abe1e8c28bc45272fb6490da61072?rik=59SMaCNZe6J0wQ&pid=ImgRaw&r=0");
        setIconImage(image.getImage());
        setResizable(false);

        // Set up the input panel
//        JLabel amountLabel = new JLabel("Amount:");
//        amountField = new JTextField(10);
//
//
//
//        JLabel descriptionLabel = new JLabel("Description:");
//        descriptionField = new JTextField(10);
//        JLabel categoryLabel = new JLabel("Category:");
//
//        //total field
//        totalField = new JTextField(10);
//        categoryBox = new JComboBox<>(new String[]{"Food", "Entertainment", "Travel", "Other"});
//
//        addButton = new JButton("Add");
//
//        addButton.addActionListener(this);
//
//        resetButton = new JButton("Reset");
//        resetButton.addActionListener(this);
//
//        saveButton = new JButton("Save");
//        saveButton.addActionListener(this);


        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        amountLabel.setFont(new Font("Pacifico", Font.PLAIN, 18));


        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(10);
        descriptionLabel.setFont(new Font("Pacifico", Font.PLAIN, 18));


        JLabel categoryLabel = new JLabel("Category:");
        categoryBox = new JComboBox<>(new String[]{"Food", "Entertainment", "Travel", "Other"});
        categoryLabel.setFont(new Font("Pacifico", Font.PLAIN, 18));
        categoryBox.setFont(new Font("Pacifico", Font.PLAIN, 18));


        addButton = new JButton("Add");
        /*sql = "INSERT INTO expenses (date, amount, category, description) VALUES ('2023-02-19',  *{amount} , *{category},*{description})";
        String finalSql = sql;*/
        addButton.setFont(new Font("Pacifico", Font.PLAIN, 18));
        addButton.addActionListener(this);
        removeButton= new JButton("Remove");
        removeButton.setFont(new Font("Pacifico", Font.PLAIN, 18));

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Pacifico", Font.PLAIN, 18));

        resetButton.addActionListener(this);

        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Pacifico", Font.PLAIN, 18));

        saveButton.addActionListener(this);

        totalLabel = new JLabel("Total:");
        totalLabel.setFont(new Font("Pacifico", Font.PLAIN, 18));

        totalField = new JTextField(10);


//        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
//        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        inputPanel.add(amountLabel);
//        inputPanel.add(amountField);
//        inputPanel.add(descriptionLabel);
//        inputPanel.add(descriptionField);
//        inputPanel.add(categoryLabel);
//        inputPanel.add(categoryBox);
//        inputPanel.add(addButton);
//        inputPanel.add(resetButton);
//        inputPanel.add(totalField);
//        inputPanel.add(saveButton);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryBox);
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel());
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(resetButton);
        inputPanel.add(saveButton);
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel());
        // inputPanel.add(new JLabel()); // add an empty label for spacing
        inputPanel.add(totalLabel);

        // Set up the output panel
       // totalLabel = new JLabel("Total");
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //outputPanel.add(totalLabel, BorderLayout.CENTER);

        // Add the panels to the main window
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.CENTER);
        getContentPane().add(outputPanel, BorderLayout.SOUTH);
        inputPanel.add(totalField);


        // Show the window
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {

            total += Double.parseDouble(amountField.getText());
            totalField.setText(total+"DZD");
            addExpense();



        } else if (e.getSource() == resetButton) {
            resetForm();}
        else if(e.getSource() == saveButton){
            save();
        }
//                } else if (e.getSource() == saveButton) {
//                    saveExpenses();
//                }
            }

            public void addExpense() {
                try {

                    Class.forName("org.hsqldb.jdbc.JDBCDriver");
                    conn = DriverManager.getConnection("jdbc:hsqldb:file:mydatabase;hsqldb.lock_file=false", "SA", "");
                    System.out.println("Connected to database.");

                    System.out.println("testing1");
                    amount = Double.parseDouble(amountField.getText());
                    description = descriptionField.getText();
                    category = categoryBox.getSelectedItem().toString();
                    expenseList.add(new Expense(amount, description, category));
                    amountField.setText("");
                    descriptionField.setText("");
                    totalLabel.setText("Total: DZD" + getTotal());
                    totalField.setText("");
                    System.out.println(getTotal());
                    String sql="insert into expense values(null, 3231234, 'test', 'test')";

//                   // PreparedStatement pstmt = conn.prepareStatement(sql);
//                    pstmt.setDouble(1, amount);
//                    pstmt.setString(2, category);
//                    pstmt.setString(3, description);
//                    pstmt.executeUpdate();
//                stmt=conn.createStatement();
//                    String sql="insert into expense values(null,*{amount},*{category},*{description})";
                   stmt.executeQuery(sql);
                    System.out.println("testing2");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            public void resetForm() {
                amountField.setText("");
                descriptionField.setText("");
                save();

                categoryBox.setSelectedIndex(0);
            }

            public void saveExpenses() {
                JOptionPane.showMessageDialog(this, "Expenses saved", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            public void save(){
                total += Double.parseDouble(amountField.getText());

                expenseList.add(new Expense(amount, description, category));

                for (Expense expense : expenseList) {
                    total += expense.getAmount();
                }

                totalField.setText(total+"DZD");///
                 categoryBox.setSelectedIndex(0);

            }

            public double getTotal() {
                double total = 0;

                for (Expense expense : expenseList) {
                    total += expense.getAmount();
                }

                return total;
            }



    class Expense {
        double amount;
        String description;
        String category;

        public Expense(double amount, String description, String category) {
            this.amount = amount;
            this.description = description;
            this.category = category;
        }

        public double getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public String getCategory() {
            return category;
        }
    }


    }



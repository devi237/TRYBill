import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinanceManagementSystem extends JFrame {

    // =============================
    // BILLING COMPONENTS
    // =============================
    JTextField txtItem, txtPrice, txtQuantity;
    JLabel lblBillTotal;
    JTextArea billArea;
    JTable billingTable;
    DefaultTableModel billingModel;

    double totalSales = 0;

    // =============================
    // EXPENSE TRACKER COMPONENTS
    // =============================
    JTextField txtExpenseName, txtExpenseAmount;
    JComboBox<String> comboCategory;
    JLabel lblExpenseTotal;

    JTable expenseTable;
    DefaultTableModel expenseModel;

    double totalExpenses = 0;

    // =============================
    // PROFIT LABEL
    // =============================
    JLabel lblProfit;

    public FinanceManagementSystem() {

        setTitle("Finance Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // =============================
        // BILLING PANEL
        // =============================
        JPanel billingPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Billing System"));

        txtItem = new JTextField();
        txtPrice = new JTextField();
        txtQuantity = new JTextField();

        topPanel.add(new JLabel("Item Name"));
        topPanel.add(new JLabel("Price"));
        topPanel.add(new JLabel("Quantity"));
        topPanel.add(new JLabel(""));

        topPanel.add(txtItem);
        topPanel.add(txtPrice);
        topPanel.add(txtQuantity);

        JButton btnAddItem = new JButton("Add Item");
        topPanel.add(btnAddItem);

        billingPanel.add(topPanel, BorderLayout.NORTH);

        // =============================
        // BILLING TABLE
        // =============================
        String[] billingColumns = {
                "Item Name",
                "Price",
                "Quantity",
                "Total"
        };

        billingModel = new DefaultTableModel(billingColumns, 0);
        billingTable = new JTable(billingModel);

        JScrollPane billingScroll = new JScrollPane(billingTable);
        billingPanel.add(billingScroll, BorderLayout.CENTER);

        // =============================
        // BILL AREA
        // =============================
        billArea = new JTextArea();
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane billAreaScroll = new JScrollPane(billArea);
        billAreaScroll.setPreferredSize(new Dimension(350, 0));

        billingPanel.add(billAreaScroll, BorderLayout.EAST);

        // =============================
        // BILL BOTTOM PANEL
        // =============================
        JPanel bottomPanel = new JPanel();

        JButton btnGenerateBill = new JButton("Generate Bill");
        JButton btnClearBill = new JButton("Clear Bill");

        lblBillTotal = new JLabel("Total Sales: ₹0.0");

        bottomPanel.add(btnGenerateBill);
        bottomPanel.add(btnClearBill);
        bottomPanel.add(lblBillTotal);

        billingPanel.add(bottomPanel, BorderLayout.SOUTH);

        // =============================
        // EXPENSE TRACKER PANEL
        // =============================
        JPanel expensePanel = new JPanel(new BorderLayout());

        JPanel expenseTopPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        expenseTopPanel.setBorder(BorderFactory.createTitledBorder("Expense Tracker"));

        txtExpenseName = new JTextField();
        txtExpenseAmount = new JTextField();

        String[] categories = {
                "Food",
                "Travel",
                "Shopping",
                "Electricity",
                "Internet",
                "Miscellaneous"
        };

        comboCategory = new JComboBox<>(categories);

        expenseTopPanel.add(new JLabel("Expense Name"));
        expenseTopPanel.add(new JLabel("Category"));
        expenseTopPanel.add(new JLabel("Amount"));
        expenseTopPanel.add(new JLabel(""));

        expenseTopPanel.add(txtExpenseName);
        expenseTopPanel.add(comboCategory);
        expenseTopPanel.add(txtExpenseAmount);

        JButton btnAddExpense = new JButton("Add Expense");
        expenseTopPanel.add(btnAddExpense);

        expensePanel.add(expenseTopPanel, BorderLayout.NORTH);

        // =============================
        // EXPENSE TABLE
        // =============================
        String[] expenseColumns = {
                "Expense Name",
                "Category",
                "Amount",
                "Date"
        };

        expenseModel = new DefaultTableModel(expenseColumns, 0);
        expenseTable = new JTable(expenseModel);

        JScrollPane expenseScroll = new JScrollPane(expenseTable);
        expensePanel.add(expenseScroll, BorderLayout.CENTER);

        // =============================
        // EXPENSE BOTTOM PANEL
        // =============================
        JPanel expenseBottomPanel = new JPanel();

        JButton btnDeleteExpense = new JButton("Delete Expense");
        JButton btnClearExpenses = new JButton("Clear Expenses");

        lblExpenseTotal = new JLabel("Total Expenses: ₹0.0");

        expenseBottomPanel.add(btnDeleteExpense);
        expenseBottomPanel.add(btnClearExpenses);
        expenseBottomPanel.add(lblExpenseTotal);

        expensePanel.add(expenseBottomPanel, BorderLayout.SOUTH);

        // =============================
        // DASHBOARD PANEL
        // =============================
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridLayout(3, 1, 20, 20));

        JLabel lblTitle = new JLabel("Finance Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));

        lblProfit = new JLabel("Net Profit: ₹0.0", SwingConstants.CENTER);
        lblProfit.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblInfo = new JLabel(
                "Profit = Total Sales - Total Expenses",
                SwingConstants.CENTER
        );

        dashboardPanel.add(lblTitle);
        dashboardPanel.add(lblProfit);
        dashboardPanel.add(lblInfo);

        // =============================
        // ADD TABS
        // =============================
        tabbedPane.addTab("Billing System", billingPanel);
        tabbedPane.addTab("Expense Tracker", expensePanel);
        tabbedPane.addTab("Dashboard", dashboardPanel);

        add(tabbedPane);

        // =============================
        // ADD ITEM BUTTON ACTION
        // =============================
        btnAddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String item = txtItem.getText();

                    double price = Double.parseDouble(txtPrice.getText());
                    int quantity = Integer.parseInt(txtQuantity.getText());

                    double total = price * quantity;

                    totalSales += total;

                    billingModel.addRow(new Object[]{
                            item,
                            price,
                            quantity,
                            total
                    });

                    lblBillTotal.setText("Total Sales: ₹" + totalSales);

                    updateProfit();

                    txtItem.setText("");
                    txtPrice.setText("");
                    txtQuantity.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter valid product details");
                }
            }
        });

        // =============================
        // GENERATE BILL ACTION
        // =============================
        btnGenerateBill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                billArea.setText("");

                billArea.append("\tXYZ SHOP\n");
                billArea.append("\tKochi, Kerala\n");
                billArea.append("=====================================\n");

                SimpleDateFormat formatter =
                        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                Date date = new Date();

                billArea.append("Date: " + formatter.format(date) + "\n");
                billArea.append("=====================================\n");

                billArea.append(String.format(
                        "%-12s %-10s %-10s %-10s\n",
                        "Item",
                        "Price",
                        "Qty",
                        "Total"
                ));

                billArea.append("=====================================\n");

                for (int i = 0; i < billingModel.getRowCount(); i++) {

                    String item = billingModel.getValueAt(i, 0).toString();
                    String price = billingModel.getValueAt(i, 1).toString();
                    String qty = billingModel.getValueAt(i, 2).toString();
                    String total = billingModel.getValueAt(i, 3).toString();

                    billArea.append(String.format(
                            "%-12s %-10s %-10s %-10s\n",
                            item,
                            price,
                            qty,
                            total
                    ));
                }

                billArea.append("=====================================\n");
                billArea.append("Total Amount: ₹" + totalSales + "\n");
                billArea.append("=====================================\n");
                billArea.append("Thank You Visit Again!\n");
            }
        });

        // =============================
        // CLEAR BILL ACTION
        // =============================
        btnClearBill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                billingModel.setRowCount(0);
                billArea.setText("");

                totalSales = 0;

                lblBillTotal.setText("Total Sales: ₹0.0");

                updateProfit();
            }
        });

        // =============================
        // ADD EXPENSE ACTION
        // =============================
        btnAddExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String expenseName = txtExpenseName.getText();

                    String category =
                            comboCategory.getSelectedItem().toString();

                    double amount =
                            Double.parseDouble(txtExpenseAmount.getText());

                    SimpleDateFormat formatter =
                            new SimpleDateFormat("dd/MM/yyyy");

                    Date date = new Date();

                    expenseModel.addRow(new Object[]{
                            expenseName,
                            category,
                            amount,
                            formatter.format(date)
                    });

                    totalExpenses += amount;

                    lblExpenseTotal.setText(
                            "Total Expenses: ₹" + totalExpenses
                    );

                    updateProfit();

                    txtExpenseName.setText("");
                    txtExpenseAmount.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter valid expense details");
                }
            }
        });

        // =============================
        // DELETE EXPENSE ACTION
        // =============================
        btnDeleteExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = expenseTable.getSelectedRow();

                if (selectedRow >= 0) {

                    double amount = Double.parseDouble(
                            expenseModel.getValueAt(selectedRow, 2).toString()
                    );

                    totalExpenses -= amount;

                    expenseModel.removeRow(selectedRow);

                    lblExpenseTotal.setText(
                            "Total Expenses: ₹" + totalExpenses
                    );

                    updateProfit();

                } else {
                    JOptionPane.showMessageDialog(null,
                            "Please select an expense to delete");
                }
            }
        });

        // =============================
        // CLEAR EXPENSES ACTION
        // =============================
        btnClearExpenses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                expenseModel.setRowCount(0);

                totalExpenses = 0;

                lblExpenseTotal.setText("Total Expenses: ₹0.0");

                updateProfit();
            }
        });
    }

    // =============================
    // UPDATE PROFIT METHOD
    // =============================
    public void updateProfit() {

        double profit = totalSales - totalExpenses;

        lblProfit.setText("Net Profit: ₹" + profit);
    }

    // =============================
    // MAIN METHOD
    // =============================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FinanceManagementSystem().setVisible(true);
            }
        });
    }
}

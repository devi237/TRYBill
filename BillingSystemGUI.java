// ==============================
// File: BillingSystemGUI.java
// Complete Java Billing System with GUI
// Technologies: Java Swing
// ==============================

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillingSystemGUI extends JFrame {

    // Components
    JTextField txtItem, txtPrice, txtQuantity;
    JLabel lblTotal;
    JTextArea billArea;
    JTable table;
    DefaultTableModel model;

    double grandTotal = 0;

    public BillingSystemGUI() {

        // Frame Settings
        setTitle("Billing System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =========================
        // TOP PANEL
        // =========================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 4, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));

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

        JButton btnAdd = new JButton("Add Item");
        topPanel.add(btnAdd);

        add(topPanel, BorderLayout.NORTH);

        // =========================
        // TABLE
        // =========================
        String[] columns = {
                "Item Name",
                "Price",
                "Quantity",
                "Total"
        };

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane tableScroll = new JScrollPane(table);
        add(tableScroll, BorderLayout.CENTER);

        // =========================
        // RIGHT PANEL - BILL AREA
        // =========================
        JPanel rightPanel = new JPanel(new BorderLayout());

        billArea = new JTextArea();
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane billScroll = new JScrollPane(billArea);

        rightPanel.add(billScroll, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.EAST);

        // =========================
        // BOTTOM PANEL
        // =========================
        JPanel bottomPanel = new JPanel();

        JButton btnGenerate = new JButton("Generate Bill");
        JButton btnClear = new JButton("Clear");
        JButton btnExit = new JButton("Exit");

        lblTotal = new JLabel("Grand Total: 0.0");

        bottomPanel.add(btnGenerate);
        bottomPanel.add(btnClear);
        bottomPanel.add(btnExit);
        bottomPanel.add(lblTotal);

        add(bottomPanel, BorderLayout.SOUTH);

        // =========================
        // ADD BUTTON ACTION
        // =========================
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String item = txtItem.getText();

                    double price = Double.parseDouble(txtPrice.getText());
                    int quantity = Integer.parseInt(txtQuantity.getText());

                    double total = price * quantity;

                    grandTotal += total;

                    Object[] row = {
                            item,
                            price,
                            quantity,
                            total
                    };

                    model.addRow(row);

                    lblTotal.setText("Grand Total: " + grandTotal);

                    txtItem.setText("");
                    txtPrice.setText("");
                    txtQuantity.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please Enter Valid Data");
                }
            }
        });

        // =========================
        // GENERATE BILL
        // =========================
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                billArea.setText("");

                billArea.append("\tXYZ SHOP\n");
                billArea.append("\tKochi, Kerala\n");
                billArea.append("====================================\n");

                SimpleDateFormat formatter =
                        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                Date date = new Date();

                billArea.append("Date: " + formatter.format(date) + "\n");
                billArea.append("====================================\n");

                billArea.append(String.format(
                        "%-10s %-10s %-10s %-10s\n",
                        "Item",
                        "Price",
                        "Qty",
                        "Total"));

                billArea.append("====================================\n");

                for (int i = 0; i < model.getRowCount(); i++) {

                    String item = model.getValueAt(i, 0).toString();
                    String price = model.getValueAt(i, 1).toString();
                    String qty = model.getValueAt(i, 2).toString();
                    String total = model.getValueAt(i, 3).toString();

                    billArea.append(String.format(
                            "%-10s %-10s %-10s %-10s\n",
                            item,
                            price,
                            qty,
                            total));
                }

                billArea.append("====================================\n");

                double gst = grandTotal * 0.18;
                double finalAmount = grandTotal + gst;

                billArea.append("Subtotal : " + grandTotal + "\n");
                billArea.append("GST 18%  : " + gst + "\n");
                billArea.append("Final Amt: " + finalAmount + "\n");

                billArea.append("====================================\n");
                billArea.append("Thank You Visit Again!\n");
            }
        });

        // =========================
        // CLEAR BUTTON
        // =========================
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                model.setRowCount(0);

                billArea.setText("");

                grandTotal = 0;

                lblTotal.setText("Grand Total: 0.0");
            }
        });

        // =========================
        // EXIT BUTTON
        // =========================
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BillingSystemGUI().setVisible(true);
            }
        });
    }
}
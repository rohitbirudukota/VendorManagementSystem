import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MainDashboard extends JFrame {

    JTextField nameField, contactField, gstField;
    JTextField vendorIdField, productField, qtyField, originalField, sellingField, paymentField;
    JTable table;
    DefaultTableModel model;

    public MainDashboard() {

        setTitle("Vendor Inventory & Profit System");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= HEADER =================
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 102, 204));
        JLabel title = new JLabel("Vendor Inventory Management System");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title);
        add(header, BorderLayout.NORTH);

        // ================= LEFT PANEL (TABLE) =================
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Vendor Summary"));

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID", "Name", "Total Stock", "Profit", "Balance"
        });

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        leftPanel.add(scrollPane);

        leftPanel.setPreferredSize(new Dimension(500, 0));
        add(leftPanel, BorderLayout.WEST);

        // ================= CENTER PANEL =================
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // ---------- VENDOR PANEL ----------
        JPanel vendorPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        vendorPanel.setBorder(BorderFactory.createTitledBorder("Add / Delete Vendor"));

        nameField = new JTextField();
        contactField = new JTextField();
        gstField = new JTextField();

        vendorPanel.add(new JLabel("Name"));
        vendorPanel.add(nameField);

        vendorPanel.add(new JLabel("Contact"));
        vendorPanel.add(contactField);

        vendorPanel.add(new JLabel("GST"));
        vendorPanel.add(gstField);

        JButton addVendorBtn = new JButton("Add Vendor");
        addVendorBtn.setBackground(new Color(0, 153, 76));
        addVendorBtn.setForeground(Color.WHITE);
        vendorPanel.add(addVendorBtn);

        JButton deleteVendorBtn = new JButton("Delete Vendor");
        deleteVendorBtn.setBackground(new Color(204, 0, 0));
        deleteVendorBtn.setForeground(Color.WHITE);
        vendorPanel.add(deleteVendorBtn);

        addVendorBtn.addActionListener(e -> addVendor());
        deleteVendorBtn.addActionListener(e -> deleteVendor());

        centerPanel.add(vendorPanel);

        // ---------- STOCK PANEL ----------
        JPanel stockPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        stockPanel.setBorder(BorderFactory.createTitledBorder("Add Stock"));

        vendorIdField = new JTextField();
        productField = new JTextField();
        qtyField = new JTextField();
        originalField = new JTextField();
        sellingField = new JTextField();
        paymentField = new JTextField();

        stockPanel.add(new JLabel("Vendor ID"));
        stockPanel.add(vendorIdField);

        stockPanel.add(new JLabel("Product Name"));
        stockPanel.add(productField);

        stockPanel.add(new JLabel("Quantity"));
        stockPanel.add(qtyField);

        stockPanel.add(new JLabel("Original Cost (Total)"));
        stockPanel.add(originalField);

        stockPanel.add(new JLabel("Selling Cost (Total)"));
        stockPanel.add(sellingField);

        stockPanel.add(new JLabel("Payment Received"));
        stockPanel.add(paymentField);

        JButton addStockBtn = new JButton("Add Stock");
        addStockBtn.setBackground(new Color(0, 102, 204));
        addStockBtn.setForeground(Color.WHITE);
        stockPanel.add(addStockBtn);

        addStockBtn.addActionListener(e -> addStock());

        centerPanel.add(stockPanel);

        add(centerPanel, BorderLayout.CENTER);

        loadVendors();

        setVisible(true);
    }

    // ================= ADD VENDOR =================
    private void addVendor() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO vendors(name, contact, gst) VALUES (?, ?, ?)");
            ps.setString(1, nameField.getText());
            ps.setString(2, contactField.getText());
            ps.setString(3, gstField.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Vendor Added Successfully!");
            loadVendors();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETE VENDOR =================
    private void deleteVendor() {

        if (vendorIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Vendor ID to delete");
            return;
        }

        try {
            int id = Integer.parseInt(vendorIdField.getText());
            Connection con = DBConnection.getConnection();

            PreparedStatement ps1 = con.prepareStatement(
                    "DELETE FROM vendor_stock WHERE vendor_id = ?");
            ps1.setInt(1, id);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                    "DELETE FROM vendors WHERE vendor_id = ?");
            ps2.setInt(1, id);
            int rows = ps2.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Vendor Deleted Successfully!");
                loadVendors();
            } else {
                JOptionPane.showMessageDialog(this, "Vendor Not Found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= ADD STOCK =================
    private void addStock() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO vendor_stock(vendor_id, product_name, quantity, original_cost, selling_cost, payment_received) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setInt(1, Integer.parseInt(vendorIdField.getText()));
            ps.setString(2, productField.getText());
            ps.setInt(3, Integer.parseInt(qtyField.getText()));
            ps.setDouble(4, Double.parseDouble(originalField.getText()));
            ps.setDouble(5, Double.parseDouble(sellingField.getText()));
            ps.setDouble(6, Double.parseDouble(paymentField.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Stock Added Successfully!");
            loadVendors();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= LOAD VENDOR SUMMARY =================
    private void loadVendors() {
        try {
            model.setRowCount(0);
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                    "SELECT v.vendor_id, v.name, " +
                            "IFNULL(SUM(s.quantity),0) AS total_stock, " +
                            "IFNULL(SUM(s.original_cost),0) AS total_purchase, " +
                            "IFNULL(SUM(s.selling_cost),0) AS total_sale, " +
                            "IFNULL(SUM(s.payment_received),0) AS total_paid " +
                            "FROM vendors v " +
                            "LEFT JOIN vendor_stock s ON v.vendor_id = s.vendor_id " +
                            "GROUP BY v.vendor_id, v.name"
            );

            while (rs.next()) {

                double purchase = rs.getDouble("total_purchase");
                double sale = rs.getDouble("total_sale");
                double paid = rs.getDouble("total_paid");

                double profit = sale - purchase;
                double balance = sale - paid;

                model.addRow(new Object[]{
                        rs.getInt("vendor_id"),
                        rs.getString("name"),
                        rs.getInt("total_stock"),
                        profit,
                        balance
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainDashboard();
    }
}
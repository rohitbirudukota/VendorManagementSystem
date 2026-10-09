import javax.swing.*;
import java.sql.*;

public class VendorForm extends JFrame {

    JTextField nameField, contactField, gstField;

    public VendorForm() {

        setTitle("Add Vendor");
        setSize(350, 250);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 20, 150, 25);
        add(nameField);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(20, 60, 100, 25);
        add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(120, 60, 150, 25);
        add(contactField);

        JLabel gstLabel = new JLabel("GST:");
        gstLabel.setBounds(20, 100, 100, 25);
        add(gstLabel);

        gstField = new JTextField();
        gstField.setBounds(120, 100, 150, 25);
        add(gstField);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(120, 150, 100, 30);
        add(saveBtn);

        saveBtn.addActionListener(e -> saveVendor());

        setVisible(true);
    }

    private void saveVendor() {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO vendors(name, contact, gst) VALUES (?, ?, ?)"
            );

            ps.setString(1, nameField.getText());
            ps.setString(2, contactField.getText());
            ps.setString(3, gstField.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Vendor Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
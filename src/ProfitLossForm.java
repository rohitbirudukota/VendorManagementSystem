import javax.swing.*;
import java.sql.*;

public class ProfitLossForm extends JFrame {

    JTextField vendorIdField;

    public ProfitLossForm() {

        setTitle("Profit & Loss");
        setSize(300, 200);
        setLayout(null);

        JLabel label = new JLabel("Vendor ID:");
        label.setBounds(20, 30, 100, 25);
        add(label);

        vendorIdField = new JTextField();
        vendorIdField.setBounds(120, 30, 100, 25);
        add(vendorIdField);

        JButton calculateBtn = new JButton("Calculate");
        calculateBtn.setBounds(80, 80, 120, 30);
        add(calculateBtn);

        calculateBtn.addActionListener(e -> calculate());

        setVisible(true);
    }

    private void calculate() {
        try {
            Connection con = DBConnection.getConnection();
            int id = Integer.parseInt(vendorIdField.getText());

            Statement st = con.createStatement();

            ResultSet rs1 = st.executeQuery(
                    "SELECT SUM(amount) FROM purchases WHERE vendor_id=" + id);
            rs1.next();
            double purchase = rs1.getDouble(1);

            ResultSet rs2 = st.executeQuery(
                    "SELECT SUM(amount) FROM sales WHERE vendor_id=" + id);
            rs2.next();
            double sales = rs2.getDouble(1);

            double profit = sales - purchase;

            JOptionPane.showMessageDialog(this,
                    "Total Purchase: " + purchase +
                            "\nTotal Sales: " + sales +
                            "\nProfit/Loss: " + profit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package practical3.q5;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FundTransfer extends JFrame {
    static FundTransfer objFundTransfer;

    NumberFormat formatter = new DecimalFormat("#0.0");

    private JTextField txtFromTeller = new JTextField(10);
    private JTextField txtToTeller   = new JTextField(10);

    private JTextField txtQtyDm100 = new JTextField(10);
    private JTextField txtQtyDm50  = new JTextField(10);
    private JTextField txtQtyDm10  = new JTextField(10);
    private JTextField txtQtyDm5   = new JTextField(10);
    private JTextField txtQtyDm1   = new JTextField(10);

    private JTextField txtValueDm100 = new JTextField(10);
    private JTextField txtValueDm50  = new JTextField(10);
    private JTextField txtValueDm10  = new JTextField(10);
    private JTextField txtValueDm5   = new JTextField(10);
    private JTextField txtValueDm1   = new JTextField(10);

    private JTextField txtTotalValue = new JTextField(10);

    public FundTransfer() {
        setLayout(new BorderLayout());

        add(getHeaderPanel(), BorderLayout.NORTH);
        add(getInputPanel(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel tellerPanel = new JPanel(new GridLayout(2, 2));

        headerPanel.add(getCenterLabel("Transfer Funds"), BorderLayout.NORTH);

        tellerPanel.add(getRightAlignedLabel("From teller"));
        tellerPanel.add(txtFromTeller);
        tellerPanel.add(getRightAlignedLabel("To teller"));
        tellerPanel.add(txtToTeller);

        headerPanel.add(tellerPanel);

        return headerPanel;
    }

    private JPanel getInputPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 3));

        setupSynchronizeField(txtQtyDm100, txtValueDm100, 100);
        setupSynchronizeField(txtQtyDm50, txtValueDm50, 50);
        setupSynchronizeField(txtQtyDm10, txtValueDm10, 10);
        setupSynchronizeField(txtQtyDm5, txtValueDm5, 5);
        setupSynchronizeField(txtQtyDm1, txtValueDm1, 1);

        setRightAlign(new ArrayList<JTextField>() {{
            add(txtQtyDm100);
            add(txtValueDm100);
            add(txtQtyDm50);
            add(txtValueDm50);
            add(txtQtyDm10);
            add(txtValueDm10);
            add(txtQtyDm5);
            add(txtValueDm5);
            add(txtQtyDm1);
            add(txtValueDm1);
            add(txtTotalValue);
        }});

        initValueField(new ArrayList<JTextField>() {{
            add(txtValueDm100);
            add(txtValueDm50);
            add(txtValueDm10);
            add(txtValueDm5);
            add(txtValueDm1);
            add(txtTotalValue);
        }});

        setNonEditable(new ArrayList<JTextField>() {{
            add(txtValueDm100);
            add(txtValueDm50);
            add(txtValueDm10);
            add(txtValueDm5);
            add(txtValueDm1);
            add(txtTotalValue);
        }});

        txtQtyDm100.setNextFocusableComponent(txtQtyDm50);
        txtQtyDm50.setNextFocusableComponent(txtQtyDm10);
        txtQtyDm10.setNextFocusableComponent(txtQtyDm5);
        txtQtyDm5.setNextFocusableComponent(txtQtyDm1);

        panel.add(getCenterLabel("Denomination"));
        panel.add(getCenterLabel("Quantity"));
        panel.add(getCenterLabel("Value"));

        panel.add(getRightAlignedLabel("100"));
        panel.add(txtQtyDm100);
        panel.add(txtValueDm100);

        panel.add(getRightAlignedLabel("50"));
        panel.add(txtQtyDm50);
        panel.add(txtValueDm50);

        panel.add(getRightAlignedLabel("10"));
        panel.add(txtQtyDm10);
        panel.add(txtValueDm10);

        panel.add(getRightAlignedLabel("5"));
        panel.add(txtQtyDm5);
        panel.add(txtValueDm5);

        panel.add(getRightAlignedLabel("1"));
        panel.add(txtQtyDm1);
        panel.add(txtValueDm1);

        panel.add(getRightAlignedLabel("Total"));
        panel.add(new JLabel(""));
        panel.add(txtTotalValue);

        return panel;
    }

    private void setupSynchronizeField(JTextField txtQuantity, JTextField txtValue, int denomination) {
        txtQuantity.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { this.focusGained(e); }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField txt     = (JTextField)e.getSource();
                String strQuantity = txt.getText();
                int quantity       = strQuantity.isEmpty() ? 0 : Integer.parseInt(strQuantity);

                txtValue.setText(formatter.format(quantity * denomination));

                int totalValue = (int)Double.parseDouble(txtValueDm100.getText()) +
                                 (int)Double.parseDouble(txtValueDm50.getText()) +
                                 (int)Double.parseDouble(txtValueDm10.getText()) +
                                 (int)Double.parseDouble(txtValueDm5.getText()) +
                                 (int)Double.parseDouble(txtValueDm1.getText());

                txtTotalValue.setText(formatter.format(totalValue));
              }
        });
        // Alternative: Trigger on every keystroke
        /*
        txtQuantity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // same logic as above
            }
        });
        */
    }

    private void setRightAlign(ArrayList<JTextField> txtFieldList) {
        for (JTextField txt : txtFieldList) {
            txt.setHorizontalAlignment(SwingConstants.RIGHT);
        }
    }

    private void initValueField(ArrayList<JTextField> txtFieldList) {
        for (JTextField txt : txtFieldList) {
            txt.setText(formatter.format(0));
        }
    }

    private void setNonEditable(ArrayList<JTextField> txtFieldList) {
        for (JTextField txt : txtFieldList) {
            txt.setEditable(false);
        }
    }

    private JPanel getButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        panel.add(getOkButton());
        panel.add(getCancelButton());

        return panel;
    }

    private JButton getOkButton() {
        JButton btnOk = new JButton("Ok");

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromTeller = txtFromTeller.getText();
                String toTeller   = txtToTeller.getText();
                String qtyDm100   = txtQtyDm100.getText();
                String qtyDm50    = txtQtyDm50.getText();
                String qtyDm10    = txtQtyDm10.getText();
                String qtyDm5     = txtQtyDm5.getText();
                String qtyDm1     = txtQtyDm1.getText();

                if (!(fromTeller.isEmpty() || toTeller.isEmpty())) {
                    String message = "From teller = " + fromTeller + "\n" +
                                     "To teller = "   + toTeller   + "\n";

                    message += qtyDm100.isEmpty() ? "" : "Denom = 100 Qty = " + qtyDm100 + "\n";
                    message += qtyDm50.isEmpty()  ? "" : "Denom = 50 Qty = "  + qtyDm50  + "\n";
                    message += qtyDm10.isEmpty()  ? "" : "Denom = 10 Qty = "  + qtyDm10  + "\n";
                    message += qtyDm5.isEmpty()   ? "" : "Denom = 5 Qty = "   + qtyDm5   + "\n";
                    message += qtyDm1.isEmpty()   ? "" : "Denom = 1 Qty = "   + qtyDm1   + "\n";

                    JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        return btnOk;
    }

    private JButton getCancelButton() {
        JButton btnCancel = new JButton("Cancel");

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                objFundTransfer.dispose();
            }
        });

        return btnCancel;
    }

    public JLabel getCenterLabel(String text) {
        return new JLabel(text, SwingConstants.CENTER);
    }

    public JLabel getRightAlignedLabel(String text) {
        return new JLabel(text, SwingConstants.RIGHT);
    }

    public static void main(String[] args) {
        objFundTransfer = new FundTransfer();

        objFundTransfer.setSize(400, 500);
        objFundTransfer.setLocationRelativeTo(null);
        objFundTransfer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        objFundTransfer.setVisible(true);
    }
}

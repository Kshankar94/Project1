package activity9;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import activity9.AddProductUI.AddButtonController;

public class AddCustomerUI {
	public JFrame view;
	public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);

    public JButton btnAdd = new JButton("Add Customer");
    public JButton btnCancel = new JButton("Cancel");

    public AddCustomerUI() {
    	 view = new JFrame();
         view.setTitle("Add Customer");
         view.setSize(600, 400);
         view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

         Container pane = view.getContentPane();
         pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Customer ID:"));
        line1.add(txtCustomerID);
        pane.add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name:"));
        line2.add(txtName);
        pane.add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Address:"));
        line3.add(txtAddress);
        pane.add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone:"));
        line4.add(txtPhone);
        pane.add(line4);

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnAdd);
        buttonPane.add(btnCancel);
        pane.add(buttonPane);
        
        btnAdd.addActionListener(new

                AddButtonController());
        btnCancel.addActionListener(
            (actionEvent) -> view.dispose()
            );
    }
    
    
    public void run() {
        view.setVisible(true);
    }

    class AddButtonController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();

            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "customerID could not be EMPTY!!!");
                return;
            }
            try {
                customer.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "customerID is INVALID (not a number)!!!");
                return;
            }

            s = txtName.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "customer Name could not be EMPTY!!!");
                return;
            }
            customer.mName = s;

            s = txtAddress.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Address cannot be EMPTY!!!");
                return;
            }
            customer.mAddress = s;
            
            s = txtPhone.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Phone number cannot be EMPTY!!!");
                return;
            }
            customer.mPhone = s;

//            s = txtQuantity.getText();
//            if (s.length() == 0) {
//                JOptionPane.showMessageDialog(null,
//                        "Quantity could not be EMPTY!!!");
//                return;
//            }
//
//            try {
//                product.mQuantity = Double.parseDouble(s);
//            } catch (NumberFormatException e) {
//                JOptionPane.showMessageDialog(null,
//                        "Quantity is INVALID (not a number)!!!");
//                return;
//            }
            IDataAccess adapter = StoreManager.getInstance().getDataAccess();

            if (adapter.saveCustomer(customer))
                JOptionPane.showMessageDialog(null,
                        "Customer is saved successfully!");
            else {
                System.out.println(adapter.getErrorMessage());
            }


        }
    }
}

package codeOct29.src.edu.auburn;

import javax.swing.*;


import codeOct29.src.edu.auburn.ManageProductUI.SaveButtonListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageCustomerUI {
	public JFrame view;
	public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);

    public JButton btnLoad = new JButton("Load");
    public JButton btnSave = new JButton("Save");


    public ManageCustomerUI() {
        view = new JFrame();
        view.setTitle("Manage Customers");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnLoad);
        buttonPane.add(btnSave);
        pane.add(buttonPane);

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("CustomerID:"));
        line.add(txtCustomerID);
        pane.add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Name:"));
        line.add(txtName);
        pane.add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Address:"));
        line.add(txtAddress);
        pane.add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Phone:"));
        line.add(txtPhone);
        pane.add(line);
        btnLoad.addActionListener(new LoadButtonListener());
        btnSave.addActionListener(new SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();
            String s = txtCustomerID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID could not be EMPTY!!!");
                return;
            }
            try {
                customer.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID  is INVALID (not a number)!!!");
                return;
            }
            IDataAccess adapter = StoreClient.getInstance().getDataAccess();
            customer = adapter.loadCustomer(customer.mCustomerID);
            if (customer == null) {
                JOptionPane.showMessageDialog(null,
                        "Customer does NOT exist!");
                return;
            }
            txtName.setText(customer.mName);
            txtAddress.setText(customer.mAddress);
            txtPhone.setText(customer.mPhone);
        }
    }

    class SaveButtonListener implements ActionListener {
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

            IDataAccess adapter = StoreClient.getInstance().getDataAccess();
            if (adapter.saveCustomer(customer))
                JOptionPane.showMessageDialog(null,
                        "Customer is saved successfully!");
            else {
                System.out.println(adapter.getErrorMessage());
            }
        }
    }
}

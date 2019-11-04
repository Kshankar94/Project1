package codeOct29.src.edu.auburn;

import javax.swing.*;

import activity9.CustomerModel;
import activity9.IDataAccess;
import activity9.ProductModel;
import activity9.PurchaseModel;
import activity9.StoreManager;
import codeOct29.src.edu.auburn.ManageProductUI.SaveButtonListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class ManagePurchaseUI {
	public JFrame view;
	public JLabel labPurchaseDate = new JLabel("Purchase Date: ");
    public JLabel labPurchaseID = new JLabel("Purchase ID: ");
    public JLabel labCustomerID = new JLabel("Customer ID: ");
    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductID = new JLabel("Product ID: ");
    public JLabel labProductName = new JLabel("Product Name: ");
    public JLabel labProductPrice = new JLabel("Product Price: ");
    public JLabel labProductQuantity = new JLabel("Purchase Quantity: ");

    public JTextField txtProductID = new JTextField(10);
    public JTextField txtPurchaseID = new JTextField(10);
    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtPurchaseDate = new JTextField(10);
    public JTextField txtProductName = new JTextField(20);
    public JTextField txtCustomerName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);
    public JTextField txtCost = new JTextField(20);
    public JTextField txtTax = new JTextField(20);
    public JTextField txtTotalCost = new JTextField(20);
    
    public JButton btnLoad = new JButton("Load");
    public JButton btnSave = new JButton("Save");
    
    ProductModel product;
    CustomerModel customer;
    PurchaseModel purchase;

    
    public ManagePurchaseUI() {
    	 view = new JFrame();
         view.setTitle("Add Purchase");
         view.setSize(500, 400);
         view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));
         
         JPanel pane = new JPanel();
         pane.setLayout(new FlowLayout());
         pane.add(btnLoad); pane.add(btnSave);
         view.getContentPane().add(pane);

         pane = new JPanel();
         pane.setLayout(new GridLayout(11,2, 0,6));
         view.getContentPane().add(pane);
 
         pane.add(new JLabel("Purchase Date:")); pane.add(txtPurchaseDate);
         pane.add(labPurchaseID); pane.add(txtPurchaseID);
         pane.add(labCustomerID); pane.add(txtCustomerID);
         pane.add(labCustomerName); pane.add(txtCustomerName);
         pane.add(labProductID); pane.add(txtProductID);
         pane.add(labProductName); pane.add(txtProductName);
         pane.add(labProductPrice); pane.add(txtPrice);
         pane.add(labProductQuantity); pane.add(txtQuantity);
         pane.add(new JLabel("Cost")); pane.add(txtCost);
         pane.add(new JLabel("Tax")); pane.add(txtTax);
         pane.add(new JLabel("Total Cost")); pane.add(txtTotalCost);


        

         txtProductID.addFocusListener(new ProductNameLoader());
         txtCustomerID.addFocusListener(new customerNameLoader());
         txtQuantity.addFocusListener(new CostCalculator());
         btnLoad.addActionListener(new LoadButtonListener());     	
}
    
    class CostCalculator implements FocusListener  {

        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            String s = txtQuantity.getText();
            try {
                purchase.mQuantity = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Quantity");
                return;
            }

            if (purchase.mQuantity > product.mQuantity) {
                JOptionPane.showMessageDialog(null, "Purchase Quantity > Available Quantity!");
                return;
            }

            purchase.mCost = purchase.mQuantity * product.mPrice;
            txtCost.setText(Double.toString(purchase.mCost));
            purchase.mTax = purchase.mCost * 0.09;
            txtTax.setText(Double.toString(purchase.mTax));
            purchase.mTotalCost = purchase.mCost + purchase.mTax;
            txtTotalCost.setText(Double.toString(purchase.mTotalCost));

        }
    }

    class ProductNameLoader implements FocusListener  {

        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            String s = txtProductID.getText();
            try {
                purchase.mProductID = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid ProductID");
                return;
            }
            if (product == null || product.mProductID != purchase.mProductID) {
            	IDataAccess adapter = (IDataAccess) StoreClient.getInstance().getDataAccess();
            	product = adapter.loadProduct(purchase.mProductID);
            }
            if (product == null) {
                JOptionPane.showMessageDialog(null, "No Product with ID = " + purchase.mProductID);
                txtProductName.setText("");
                txtPrice.setText("");
                return;
            }

            txtProductName.setText(product.mName);
            txtPrice.setText(Double.toString(product.mPrice));

        }
    }

    
    class customerNameLoader implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			String s = txtCustomerID.getText();
			System.out.println(s);
            try {
                purchase.mCustomerID = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid CustomerID");
                return;
            }
            if (customer == null || customer.mCustomerID != purchase.mCustomerID) {
            	 IDataAccess adapter = (IDataAccess) StoreClient.getInstance().getDataAccess();
            	customer = adapter.loadCustomer(purchase.mCustomerID);
            	}
            if (customer == null) {
                JOptionPane.showMessageDialog(null, "No customer name with ID = " + purchase.mCustomerID);
                txtProductName.setText("");
                txtPrice.setText("");
                return;
            }

            txtCustomerName.setText(customer.mName);
//            txtPrice.setText(Double.toString(product.mPrice));
			
		}
    
    	
    }
    
    class LoadButtonListener implements ActionListener {

        
    	 @Override
         public void actionPerformed(ActionEvent actionEvent) {
             PurchaseModel purchaseModel = new PurchaseModel();
             String s = txtPurchaseID.getText();
             if (s.length() == 0) {
                 JOptionPane.showMessageDialog(null,
                         "PurchaseID could not be EMPTY!!!");
                 return;
             }
             try {
                 purchaseModel.mPurchaseID = Integer.parseInt(s);
             }
             catch (NumberFormatException e) {
                 JOptionPane.showMessageDialog(null,
                         "PurchaseID is INVALID (not a number)!!!");
                 return;
             }
             IDataAccess adapter = (IDataAccess) StoreClient.getInstance().getDataAccess();
             purchaseModel = adapter.loadPurchase(purchaseModel.mPurchaseID);
             if (purchaseModel == null) {
                 JOptionPane.showMessageDialog(null,
                         "Purchase does NOT exist!");
                 return;
             }
             txtCustomerID.setText(String.valueOf(purchaseModel.mCustomerID));
             txtProductID.setText(String.valueOf(purchaseModel.mProductID));
             txtTotalCost.setText(String.valueOf(purchaseModel.mTotalCost));
             txtPrice.setText(String.valueOf(purchaseModel.mPrice));
             txtTax.setText(String.valueOf(purchaseModel.mTax));
             txtQuantity.setText(String.valueOf(purchaseModel.mQuantity));
         }
    }
    
    
    
   


    public void run() {
        purchase = new PurchaseModel();

        txtPurchaseDate.setText(Calendar.getInstance().getTime().toString());
        txtCustomerName.setEnabled(false);
        txtProductName.setEnabled(false);
        txtCost.setEnabled(false);
        txtTax.setEnabled(false);
        txtTotalCost.setEnabled(false);
        view.setVisible(true);
    }
    
}
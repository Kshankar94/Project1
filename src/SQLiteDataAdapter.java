package activity9;

import java.sql.Connection;
import java.sql.Statement;

import activity8.customerModel;

import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAccess {
    Connection conn = null;
    int errorCode = 0;
    SQLiteDataAdapter adapter;
    public boolean connect(String path) {
    	
        try {
            // db parameters
            String url = "jdbc:sqlite:" + path;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("conn");
            System.out.println(conn);
            return true;

        } catch (SQLException e) {
        	 System.out.println("conn failed");
            System.out.println(e.getMessage());
            errorCode = CONNECTION_OPEN_FAILED;
            return false;
        }

    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED: return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED: return "Cannot load the product!";
        };
        return "OK";
    }

    public ProductModel loadProduct(int productID) {
        try {
            ProductModel product = new ProductModel();

            String sql = "SELECT ProductID, ProductName, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductID");
            product.mName = rs.getString("ProductName");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");
            return product;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return null;
        }
    }
    
    @Override
    public boolean saveProduct(ProductModel product) {
    	
    
     	 try {
     		 String sql = "INSERT INTO Products(ProductID,ProductName,Price,Quantity,Vendor,TaxRate) VALUES(?,?,?,?,?,?)";
     		 PreparedStatement pstmt = conn.prepareStatement(sql);
     	            pstmt.setInt(1, product.mProductID);
     	            pstmt.setString(2, product.mName);
     	            pstmt.setDouble(3,product.mPrice);  
     	            pstmt.setInt(4, (int) product.mQuantity);
     	            pstmt.setString(5, "Herba");
     	            pstmt.setDouble(6, 4.56);
     	            pstmt.executeUpdate();
     	            return true;
     	        } catch (SQLException e) {
     	        	System.out.println("exception");
     	            System.out.println(e.getMessage());
     	        }
         
         return false;
     }
    
    
    
    
    
    @Override
    public CustomerModel loadCustomer(int customerId) {
        CustomerModel customer = new CustomerModel();
//        adapter.connect("Customers.db");
        
        try {
        	
            String sql = "SELECT CustomerID, Name, Phone, Address FROM Customers WHERE CustomerID = " + customerId;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.mName = rs.getString("Name");
            customer.mCustomerID = rs.getInt("CustomerId");
            customer.mAddress = rs.getString("Phone");
            customer.mPhone = rs.getString("Address");


        } catch (Exception e) {
        	
            System.out.println(e.getMessage());
        }
        return customer;
    }

	@Override
	  public boolean saveCustomer(CustomerModel customer) {
   	 try {
   		 String sql = "INSERT INTO Customers(CustomerID,Name,Phone,Address) VALUES(?,?,?,?)";
   		 PreparedStatement pstmt = conn.prepareStatement(sql);
   	            pstmt.setInt(1,  customer.mCustomerID );
   	            pstmt.setString(2, customer.mName);
   	            pstmt.setString(3, customer.mPhone);  
   	            pstmt.setString(4,   customer.mAddress);
   	            pstmt.executeUpdate();
   	            return true;
   	        } catch (SQLException e) {
   	        	System.out.println("excepti");
   	            System.out.println(e.getMessage());
   	        }
       
       return false;
   }

	  @Override
	    public PurchaseModel loadPurchase(int purchaseId) {
		  PurchaseModel purchase = new PurchaseModel();
//	        adapter.connect("Customers.db");
	        
	        try {
	        	
	            String sql = "SELECT PurchaseID, CustomerID, ProductID, Quantity, Price, TotalTax,TotalCost,PurchaseDate FROM Customers WHERE PurchaseID = " + purchaseId;
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            purchase.mPurchaseID = rs.getInt("PurchaseID");
	            purchase.mCustomerID = rs.getInt("CustomerID");
	            purchase.mProductID = rs.getInt("ProductID");
	            purchase.mQuantity = rs.getInt("Quantity");
	            purchase.mPrice = rs.getDouble("Price");
	            purchase.mTax = rs.getDouble("TotalTax");
	            purchase.mTotalCost = rs.getDouble("TotalCost");
	            purchase.mDate = rs.getString("PurchaseDate");
	            

	        } catch (Exception e) {
	        	
	            System.out.println(e.getMessage());
	        }
	        return purchase;
	    }


	  @Override
	  public boolean savePurchase(PurchaseModel purchase) {
   	 try {
   		 String sql = "INSERT INTO Purchases(PurchaseID, CustomerID, ProductID, Quantity, Price, TotalTax,TotalCost,PurchaseDate) VALUES(?,?,?,?,?,?,?,?)";
   		 PreparedStatement pstmt = conn.prepareStatement(sql);
  
   	            pstmt.setInt(1,  purchase.mPurchaseID );
   	            pstmt.setInt(2, purchase.mCustomerID);
   	            pstmt.setInt(3, purchase.mProductID);  
   	            pstmt.setInt(4, purchase.mQuantity);
   	            pstmt.setDouble(5, purchase.mPrice);
   	            pstmt.setDouble(6, purchase.mTax);
   	            pstmt.setDouble(7, purchase.mTotalCost);
   	            pstmt.setString(8,purchase.mDate);
   	            
   	            pstmt.executeUpdate();
   	            return true;
   	        } catch (SQLException e) {
   	        	System.out.println("exception save purchase");
   	            System.out.println(e.getMessage());
   	        }
       
       return false;
   }  

}

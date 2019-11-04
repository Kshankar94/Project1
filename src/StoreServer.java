package codeOct29.src.edu.auburn;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class StoreServer {
    static Gson gson = new Gson();
    static String dbfile = "C:\\Users\\ttn0007\\IdeaProjects\\StoreManager\\data\\store.db";
    static IDataAccess dao = new SQLiteDataAdapter(dbfile);

    public static void main(String[] args) {
        int port = 1000;
        try {
            // not a permanent connection!

            ServerSocket server = new ServerSocket(port);
            while (true) {
                try {
                    Socket pipe = server.accept();
                    Scanner in = new Scanner(pipe.getInputStream());
                    PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);

                    String msg = in.nextLine();
                    MessageModel request = gson.fromJson(msg, MessageModel.class);
                    String res = gson.toJson(process(request));
                    out.println(res);
                    System.out.println("Response to client: " + res);
                    pipe.close(); // close this socket!!!
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MessageModel process(MessageModel request) {
        if (request.code == MessageModel.GET_PRODUCT) {
            int id = Integer.parseInt(request.data);
            System.out.println("GET command from Client with id = " + id);
            ProductModel model = dao.loadProduct(id);
            if (model == null)
                return new MessageModel(IDataAccess.PRODUCT_LOAD_ID_NOT_FOUND, null);
            else
                return new MessageModel(IDataAccess.PRODUCT_LOAD_OK,gson.toJson(model));
        }
        if (request.code == MessageModel.PUT_PRODUCT) {
            ProductModel model = gson.fromJson(request.data, ProductModel.class);
            System.out.println("PUT command from Client with product = " + model);
            boolean saved = dao.saveProduct(model);
            if (saved) // save successfully!
                return new MessageModel(IDataAccess.PRODUCT_SAVE_OK, null);
            else
                return new MessageModel(IDataAccess.PRODUCT_SAVE_FAILED, null);
        }
        // continue with other get and put commands for customers, purchases...
        
        if (request.code == MessageModel.GET_CUSTOMER) {
            int id = Integer.parseInt(request.data);
            System.out.println("GET command from Client with id = " + id);
            CustomerModel model = dao.loadCustomer(id);
            if (model == null)
                return new MessageModel(IDataAccess.CUSTOMER_LOAD_ID_NOT_FOUND, null);
            else
                return new MessageModel(IDataAccess.CUSTOMER_LOAD_OK,gson.toJson(model));
        }
        if (request.code == MessageModel.PUT_CUSTOMER) {
        	CustomerModel model = gson.fromJson(request.data, CustomerModel.class);
            System.out.println("PUT command from Client with customer = " + model);
            boolean saved = dao.saveCustomer(model);
            if (saved) // save successfully!
                return new MessageModel(IDataAccess.CUSTOMER_SAVE_OK, null);
            else
                return new MessageModel(IDataAccess.CUSTOMER_SAVE_FAILED, null);
        }

        
        if (request.code == MessageModel.GET_PURCHASE) {
            int id = Integer.parseInt(request.data);
            System.out.println("GET command from Client with id = " + id);
            PurchaseModel model = dao.loadPurchase(id);
            if (model == null)
                return new MessageModel(IDataAccess.PURCHASE_LOAD_ID_NOT_FOUND, null);
            else
                return new MessageModel(IDataAccess.PURCHASE_LOAD_OK,gson.toJson(model));
        }
        if (request.code == MessageModel.PUT_PURCHASE) {
        	PurchaseModel model = gson.fromJson(request.data, PurchaseModel.class);
            System.out.println("PUT command from Client with PURCHASE = " + model);
            boolean saved = dao.savePurchase(model);
            if (saved) // save successfully!
                return new MessageModel(IDataAccess.PURCHASE_SAVE_OK, null);
            else
                return new MessageModel(IDataAccess.PURCHASE_SAVE_FAILED, null);
        }
        return null;
    }
}
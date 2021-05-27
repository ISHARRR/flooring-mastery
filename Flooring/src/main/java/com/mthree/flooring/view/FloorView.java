/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.view;

//import com.mthree.flooring.models.Customer;
import com.mthree.flooring.dao.FloorDao;
import com.mthree.flooring.dao.OrderPersistenceException;
import com.mthree.flooring.models.Order;
import com.mthree.flooring.models.Product;
import com.mthree.flooring.models.Tax;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ishar
 */
@Component
public class FloorView {

    @Autowired
    UserIO io;
    FloorDao dao;

    public ArrayList<Integer> idArrayList = new ArrayList<>();
    Map<String, Tax> taxMap = new HashMap<>();
    Map<String, Product> productMap = new HashMap<>();

    public FloorView(UserIO io, FloorDao dao) {
        this.io = io;
        this.dao = dao;
    }

    // ----- Banners
    public void createNewOrderBanner() {
        io.print("=== Adding Order ===");
    }

    public void getOrderbyDate() {
        io.print("=== Order By Date ===");
    }

    public void listOrderBanner() {
        io.print("=== All Orders ===");
    }

    public void removeOrderBanner() {
        io.print("=== Removing Order ===");
    }

    public void updateOrderBanner() {
        io.print("=== Update Order ===");
    }

    public void taskCompletedBanner() {
        io.print("=== Task Compeleted ===");
    }

    public void exportOrderDataBanner() {
        io.print("=== Exporting Data ===");
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }

    public int printMenuAndGetSelection(int min, int max) throws FileNotFoundException, OrderPersistenceException {
        loadTaxs();
        loadProducts();
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add new Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Search Order by Date");
        io.print("6. Export All Data");
        io.print("7. Exit");

        return io.readInt("Please select from the above choices.", min, max);
    }

    public int newId() throws FileNotFoundException {
        loadIds();
        Collections.sort(idArrayList);
        int lasteEl;
        if (idArrayList.isEmpty()) {
            lasteEl = 0;

        } else {
            lasteEl = idArrayList.get(idArrayList.size() - 1);
        }
        idArrayList.add(lasteEl + 1);
        saveIds();
        return lasteEl + 1;
    }

    public Order createNewOrder() throws FileNotFoundException {
        boolean flag = true;
        
        int id = newId();
        String stateAbbreviation = null;
        String productType = null;
        
        LocalDate date = io.readDate("Enter Date: YYYY-MM-DD");
        String customerName = io.readString("Enter Customer Name");
//        String stateAbbreviation = io.readString("Enter State Abbreviation");
//        String productType = io.readString("Enter Product Type");
        while (flag) {
            stateAbbreviation = io.readString("Enter State Abbreviation");
            productType = io.readString("Enter Product Type");
            if (taxMap.containsKey(stateAbbreviation) && productMap.containsKey(productType)) {
                flag = false;
            } else {
                System.out.println("--- Invalid State or Product ---");
            }
        }


        BigDecimal area = io.readBDecimal("Enter Area");

        Order createdOrder = new Order(id, date, customerName,
                new Tax(stateAbbreviation, taxMap.get(stateAbbreviation).getStateName(), taxMap.get(stateAbbreviation).getTaxRate()),
                new Product(productType, productMap.get(productType).getCostPerSquareFoot(), productMap.get(productType).getLaborCostPerSquareFoot()),
                area);

        return createdOrder;
    }

    public Order updateOrder(int orderId) throws FileNotFoundException, OrderPersistenceException {
        Order currentOrder = dao.updateOrder(orderId);
        boolean flag = true;

        int id = currentOrder.getOrderNumber();
        LocalDate date = currentOrder.getDate();
        String stateAbbreviation = null;
        String productType = null;
        
        String customerName = io.readString("Enter Customer Name");
        while (flag) {
            stateAbbreviation = io.readString("Enter State Abbreviation");
            productType = io.readString("Enter Product Type");
            if (taxMap.containsKey(stateAbbreviation) && productMap.containsKey(productType)) {
                flag = false;
            } else {
                System.out.println("Invalid State or Product");
            }
        }

        BigDecimal area = io.readBDecimal("Enter Area");

        Order createdOrder = new Order(id, date, customerName,
                new Tax(stateAbbreviation, taxMap.get(stateAbbreviation).getStateName(), taxMap.get(stateAbbreviation).getTaxRate()),
                new Product(productType, productMap.get(productType).getCostPerSquareFoot(), productMap.get(productType).getLaborCostPerSquareFoot()),
                area);

        return createdOrder;
    }

    public int orderId() {
        int id = io.readInt("Enter order ID");
        return id;
    }

    public LocalDate orderDate() {
        LocalDate id = io.readDate("Enter order Date");
        return id;
    }
//----------------------------- daaataaa

    public void loadIds() throws FileNotFoundException {
        final String FILENAME = "ids.txt";
        FileInputStream fileIn = new FileInputStream(FILENAME);
        Scanner scan = new Scanner(fileIn);
        while (scan.hasNext()) {
            String ids = scan.next();
            idArrayList.add(Integer.parseInt(ids));
        }
    }

    public void saveIds() throws FileNotFoundException {
        final String FILENAME = "ids.txt";
        Set<Integer> id = new HashSet<Integer>(idArrayList);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(FILENAME))) {
        }
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(FILENAME))) {
            for (Integer temp : id) {
                pw.println(temp);
            }
            pw.close();
        }
    }

    public void loadTaxs() throws FileNotFoundException, OrderPersistenceException {
        final String FILE = "Data/Taxes.txt";

        Scanner scanner = null;

        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(FILE)));
        } catch (FileNotFoundException e) {
            throw new OrderPersistenceException("Could not load Tax data into memory.", e);
        }

        scanner.nextLine();

        String currentLine;

        Tax currentTax;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTaxes(currentLine);
            taxMap.put(currentTax.getStateAbbreviation(), currentTax);
        }
        scanner.close();
    }

    private Tax unmarshallTaxes(String taxAsText) {
        String[] taxtokens = taxAsText.split(",");

        String StateAbbreviation = taxtokens[0];
        String stateName = taxtokens[1];
//        String taxRateString = taxtokens[2];
        BigDecimal taxRate = new BigDecimal(taxtokens[2]).setScale(2, RoundingMode.HALF_EVEN);

        Tax taxFromFile = new Tax(StateAbbreviation, stateName, taxRate);

        return taxFromFile;
    }

    public void loadProducts() throws FileNotFoundException, OrderPersistenceException {
        final String FILE = "Data/Products.txt";

        Scanner scanner = null;

        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(FILE)));
        } catch (FileNotFoundException e) {
            throw new OrderPersistenceException("Could not load Products data into memory.", e);
        }

        scanner.nextLine();

        String currentLine;

        Product currentProduct;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProducts(currentLine);
            productMap.put(currentProduct.getProductType(), currentProduct);
        }
        scanner.close();
    }

    private Product unmarshallProducts(String taxAsText) {
        String[] taxtokens = taxAsText.split(",");

        String ProductType = taxtokens[0];
        BigDecimal CostPerSquareFoot = new BigDecimal(taxtokens[1]).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal LaborCostPerSquareFoot = new BigDecimal(taxtokens[2]).setScale(2, RoundingMode.HALF_EVEN);

        Product productFromFile = new Product(ProductType, CostPerSquareFoot, LaborCostPerSquareFoot);

        return productFromFile;
    }

}

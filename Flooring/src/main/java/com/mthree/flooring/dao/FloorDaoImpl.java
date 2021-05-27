/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ishar
 */
import com.mthree.flooring.models.Order;
import com.mthree.flooring.models.Product;
import com.mthree.flooring.models.Tax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NullPointerException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FloorDaoImpl implements FloorDao {

    Map<Integer, Order> orders = new HashMap<>();
    Map<Integer, Order> backupOrders = new HashMap<>();
    private final String DELIMITER = ",";
    private String ORDER_FILE;
    private String ORDER_FILE_BACKUP;

    @Autowired
    public FloorDaoImpl() {
        ORDER_FILE = "Orders/Orders_";
        ORDER_FILE_BACKUP = "Backup/DataExport.txt";
    }

    public FloorDaoImpl(String ordersTextFile, String ordersTextFile1) {
        ORDER_FILE = ordersTextFile;
        ORDER_FILE_BACKUP = ordersTextFile1;
    }

    @Override
    public List<Order> getAllOrder() throws OrderPersistenceException {
        try {
            loadOrders(ORDER_FILE);
            return new ArrayList(orders.values());
        } catch (IOException ex) {
            Logger.getLogger(FloorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Order addOrder(Order order) throws OrderPersistenceException {
        try {
            loadOrders(ORDER_FILE);
        } catch (IOException ex) {
            Logger.getLogger(FloorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Order addedOrder = orders.put(order.getOrderNumber(), order);
        try {
            writeOrders(ORDER_FILE);
        } catch (IOException ex) {
            Logger.getLogger(FloorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addedOrder;

    }

    @Override
    public Order removeOrder(int orderId) throws OrderPersistenceException {

        try {
            loadOrders(ORDER_FILE);
        } catch (IOException ex) {
            Logger.getLogger(FloorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            removeLine(orders.get(orderId).getDate().toString());
        } catch (IOException ex) {
            Logger.getLogger(FloorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Order removedOrder = orders.remove(orderId);
        try {
            writeOrders(ORDER_FILE);
        } catch (IOException ex) {
            Logger.getLogger(FloorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removedOrder;

    }

    @Override
    public List<Order> exportOrderData() throws OrderPersistenceException {
        try {
            loadOrders(ORDER_FILE_BACKUP);
            writeOrders(ORDER_FILE_BACKUP);

        } catch (IOException ex) {
            Logger.getLogger(FloorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Order updateOrder(int orderId) throws OrderPersistenceException {
        try {
            loadOrders(ORDER_FILE);
            Order upatedOrder = orders.get(orderId);
            writeOrders(ORDER_FILE);
            return upatedOrder;
        } catch (IOException ex) {
            Logger.getLogger(FloorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Order> searchByDate(LocalDate date) throws OrderPersistenceException {
        try {
            loadOrders(ORDER_FILE);
            List<Order> datedOrders = new ArrayList<>();
            Set keys = orders.keySet();

            keys.stream().filter(k -> (orders.get(k).getDate().equals(date))).forEachOrdered(k -> {
                datedOrders.add(orders.get(k));
            });

            return datedOrders;
        } catch (IOException ex) {
            Logger.getLogger(FloorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<String> allDatesFromFolder() throws OrderPersistenceException, IOException {
        String dirLocation = "Orders";
        List<File> files = Files.list(Paths.get(dirLocation))
                .map(Path::toFile)
                .collect(Collectors.toList());

        List<String> daysRange = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            daysRange.add(files.get(i).toString().substring(14, 24));

        }

        return daysRange;
    }

    public List<String> allDatesFromMap() throws OrderPersistenceException, IOException {
        Set<Integer> keys = orders.keySet();
        List<String> daysRange = new ArrayList<>();

        for (Integer i : keys) {
            daysRange.add(orders.get(i).getDate().toString());
        }

//        for (int i = 0; i < keys.size(); i++) {
//            daysRange.add(orders.get(i).getDate().toString());
//
//        }
        return daysRange;
    }

    // daaaattaaaaa stuff
    private String marshallItem(Order aOrderAsText) {
        String orderAsText = aOrderAsText.getOrderNumber() + DELIMITER;
        orderAsText += aOrderAsText.getDate() + DELIMITER;
        orderAsText += "'" + aOrderAsText.getCustomer() + "'" + DELIMITER;
        orderAsText += aOrderAsText.getTaxes().getStateAbbreviation() + DELIMITER;
        orderAsText += aOrderAsText.getTaxes().getStateName() + DELIMITER;
        orderAsText += aOrderAsText.getTaxes().getTaxRate() + DELIMITER;
        orderAsText += aOrderAsText.getProduct().getProductType() + DELIMITER;
        orderAsText += aOrderAsText.getProduct().getCostPerSquareFoot() + DELIMITER;
        orderAsText += aOrderAsText.getProduct().getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += aOrderAsText.getArea() + DELIMITER;
        orderAsText += aOrderAsText.getMaterialCost() + DELIMITER;
        orderAsText += aOrderAsText.getLaborCost() + DELIMITER;
        orderAsText += aOrderAsText.getTax() + DELIMITER;
        orderAsText += aOrderAsText.getTotal();
        return orderAsText;

    }

    private Order unmarshallItem(String orderAsText) {
        String[] ordertokens = orderAsText.split(",(?!\\s)");

        int orderId = Integer.parseInt(ordertokens[0]);
        LocalDate date = LocalDate.parse(ordertokens[1]);
//        String name = "'" +
        String customerName = ordertokens[2].replaceAll("\'", "");//.replaceAll("34", "");
        Tax taxes = new Tax(ordertokens[3], ordertokens[4],
                new BigDecimal(ordertokens[5]).setScale(2, RoundingMode.HALF_EVEN));
        Product products = new Product(ordertokens[6],
                new BigDecimal(ordertokens[7]).setScale(2, RoundingMode.HALF_EVEN),
                new BigDecimal(ordertokens[8]).setScale(2, RoundingMode.HALF_EVEN));
        BigDecimal area = new BigDecimal(ordertokens[9]).setScale(2, RoundingMode.HALF_EVEN);
//        BigDecimal costPerSquareFoot = new BigDecimal(ordertokens[10]).setScale(2, RoundingMode.HALF_EVEN);
//        BigDecimal labourCostPSF = new BigDecimal(ordertokens[11]).setScale(2, RoundingMode.HALF_EVEN);

        Order orderFromFile = new Order(orderId, date, customerName, taxes, products, area);

        return orderFromFile;
    }

    private void loadOrders(String ORDER_FILE) throws OrderPersistenceException, IOException {
        Scanner scanner = null;

        List<String> allDates = allDatesFromFolder();

        for (int i = 0; i < allDates.size(); i++) {

            try {
                scanner = new Scanner(
                        new BufferedReader(
                                new FileReader("Orders/Orders_" + allDates.get(i) + ".txt")));

                String currentLine;

                Order currentOrder;
                if(scanner.hasNextLine()){
                    scanner.nextLine();
                }
                
                if ("Orders/Orders_".equals(ORDER_FILE)) {
                    while (scanner.hasNextLine()) {
                        currentLine = scanner.nextLine();
                        currentOrder = unmarshallItem(currentLine);
                        orders.put(currentOrder.getOrderNumber(), currentOrder);
                    }
                } else {
                    while (scanner.hasNextLine()) {
                        currentLine = scanner.nextLine();
                        currentOrder = unmarshallItem(currentLine);
                        backupOrders.put(currentOrder.getOrderNumber(), currentOrder);
                    }
                }
                scanner.close();
            } catch (FileNotFoundException | NullPointerException e) {
                throw new OrderPersistenceException("Could not load Order data into memory.", e);
            }
        }
    }

    private void writeOrders(String ORDER_FILE) throws OrderPersistenceException, IOException {
        PrintWriter out;

        List allDates = allDatesFromMap();
        for (int i = 0; i < allDates.size(); i++) {
            try {
                if ("Orders/Orders_".equals(ORDER_FILE)) {
                    out = new PrintWriter(new FileWriter(ORDER_FILE + allDates.get(i) + ".txt"));
                } else {
                    out = new PrintWriter(new FileWriter(ORDER_FILE_BACKUP));
                }
                out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

                String orderAsText;
                List<Order> orderList = this.getAllOrder();
                for (Order currentOrder : orderList) {
                    if (currentOrder.getDate().toString().equals(allDates.get(i))) {
                        orderAsText = marshallItem(currentOrder);
                        out.println(orderAsText);
                        out.flush();
                    } else if (!(ORDER_FILE.equals("Orders/Orders_"))) {
                        orderAsText = marshallItem(currentOrder);
                        out.println(orderAsText);
                        out.flush();
                    }
                }
                out.close();

            } catch (IOException e) {
                throw new OrderPersistenceException("Could not save Order data.", e);
            }

        }
    }

    public void removeLine(String lineContent) throws IOException {
        String fileName = "Orders/Orders_" + lineContent + ".txt";
        File file = new File(fileName);
        Path path = Paths.get(fileName);
        List<String> out = Files.lines(file.toPath())
                .filter(line -> !line.contains(lineContent))
                .collect(Collectors.toList());
        Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

//        if (file.length() == 0) {
//            file.delete();
//        }
        long lines = 0;
        try {

            // much slower, this task better with sequence access
            //lines = Files.lines(path).parallel().count();
            lines = Files.lines(path).count();
            if(lines == 1){
                file.delete();
            }

        } catch (IOException e) {
        }

    }
}

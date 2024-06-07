package model;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that collects purchase objects into an Array List.
 *
 * @version JDK 21.0
 * @author Jordan Festin
 */
public class Spending {

    /**
     * Instance field for the total amount of money spent.
     */
    private BigDecimal myTotal;

    /**
     * Instance field for the array list that contains Purchase objects.
     */
    private final List<Purchase> myPurchases;

    /**
     * Instance field for the file path.
     */

    private final String myPath;

    /**
     * Default constructor initializes the array list and sets default total to zero.
     */
    public Spending(final Project theProject) {
        myPath = theProject.getMyDirectory() + File.separator + "spending";
        makeDir();
        myPurchases = readPurchasesFromFile();
        setTotal();
    }

    /**
     * sets total to given amount.
     */
    public void setTotal() {
        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        for (Purchase purchase: myPurchases) {
            total = total.add(purchase.getPrice().setScale(2,RoundingMode.HALF_EVEN));
        }
        myTotal = total.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Retrieves the total amount spent.
     *
     * @return BigDecimal
     */
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        for (Purchase purchase: myPurchases) {
            total = total.add(purchase.getPrice().setScale(2,RoundingMode.HALF_EVEN));
        }
        return myTotal.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Retrieves the array list of purchases.
     *
     * @return List</Purchase>
     */
    public List<Purchase> getPurchases() {
        return myPurchases;
    }

    /**
     * Adds a purchase to the arraylist.
     *
     * @param thePurchase thePurchase
     */
    public void addPurchase(final Purchase thePurchase) {
        myPurchases.add(thePurchase);
        myTotal = myTotal.add(thePurchase.getPrice()).setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * removes a purchase from the array list.
     *
     * @param theName theName
     */
    public void removePurchase(final String theName) {
        Iterator<Purchase> itr = myPurchases.iterator();
        while (itr.hasNext()) {
            Purchase currentPurchase = itr.next();
            String currentName = currentPurchase.getName();
            if (Objects.equals(currentName, theName)) {
                itr.remove();
                return;
            }
        }
    }

    /**
     * Sums up the total price of all the purchased items in the list.
     */
    public void countTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (Purchase i : myPurchases) {

           total = total.add(i.getPrice());
        }

        myTotal = total;
    }

    /**
     * This is the method to make the purchases directory for data permanence.
     */

    private void makeDir() {
        File directory = new File(myPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                JOptionPane.showMessageDialog(null, "Failed to create application directory in Documents.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This is the method to write the purchases into a file called purchases.txt
     */

    public void writePurchases() {
        File purchasesFile = new File(myPath, "purchases.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(purchasesFile))) {
            for (Purchase purchase : myPurchases) {
                writer.write(purchase.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to write purchases to file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This is the method to read the data in purchases.txt.
     *
     * @return a new arraylist of purchase objects according to purchases.txt
     */

    private ArrayList<Purchase> readPurchasesFromFile() {
        ArrayList<Purchase> result = new ArrayList<>();
        File purchasesFile = new File(myPath, "purchases.txt");

        if (purchasesFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(purchasesFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    BigDecimal price = new BigDecimal(reader.readLine());
                    String date = reader.readLine();
                    Purchase purchase = new Purchase(line, price, date);
                    result.add(purchase);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to read purchases from file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return result;
    }

}

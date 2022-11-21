package baggatta.c482bb.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Models a product object
 * @author Brandon Baggatta
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;

    private String name;

    private double price;

    private int stock;

    private int min;

    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets the ID
     * @param id the id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * sets the name of the product
     * @param name the name
     */
    public void setName(String name){
        
    }

    /**
     * sets the price of the product
     * @param price price
     */
    public void setPrice(double price){
        
    }

    /**
     * sets the inventory level of the product
     * @param stock stock
     */
    public void setStock(int stock){
        this.stock = stock;
        
    }

    /**
     * sets the minimum allowed inventory
     * @param min min
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * sets the maximum allowed inventory
     * @param max max
     */
    public void setMax(int max){
        this.max = max;
    }

    /**
     * gets the ID of the product
     * @return id
     */
    public int getId(){

        return id;
    }

    /**
     * gets the name of the product
     * @return the name
     */
    public String getName(){

        return name;
    }

    /**
     * gets the price of the product
     * @return price
     */
    public double getPrice(){

        return price;
    }

    /**
     * gets the inventory level of the product
     * @return stock
     */
    public int getStock(){

        return stock;
    }

    /**
     * gets the minimum allowed inventory level for the product
     * @return min
     */
    public int getMin(){

        return min;
    }

    /**
     * gets the maximum allowed inventory level for the product
     * @return max
     */
    public int getMax(){

        return max;
    }

    /**
     * adds an associated part to the product
     * @param part the part
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * deletes an associate part from a product
     * @param selectedAssociatedPart the selected part
     * @return boolean indicating success or failure
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * gets the list of associated parts for the product
     * @return the associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }


   
}



package baggatta.c482bb.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This models the inventory of parts and products
 *
 * @author Brandon Baggatta
 */
public class Inventory {

    private static int newPartID = 1;
    /**
     * A list of all parts in the inventory
     */

    private static int newProductID = 1;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * A list of all products in the inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the inventory
     * @param newPart the part object to add
     */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    /**
     * Adds a new part to the inventory
     * @param newProduct the product object to add
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Searches list of parts by ID
     * @param partId the part id
     * @return the part object
     */
    public static Part lookupPart(int partId){
        Part partToLookup = null;

        for (Part part : allParts) {
            if (part.getId() == partId) {
                partToLookup = part;
            }
        }

        return partToLookup;
    }


    /**
     * Searches the list of products
     * @param productId the product ID
     * @return the product object
     */
    public static Product lookupProduct(int productId){
        Product productToLookup = null;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productToLookup = product;
            }
        }
        return productToLookup;
    }

    /**
     *
     * @return
     */
    public static ObservableList<Part> lookupPart() {
        return allParts;
    }

    public static ObservableList<Product> lookupProduct() {
        return allProducts;
    }

    public static void updatePart(int index, Part selectedPart){

    }

    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);

    }

    /**
     * Deletes the selected part from the part list
     * @param selectedPart the selected part
     * @return true or false
     */
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * deletes the selected product from the product list
     * @param selectedProduct the selected product
     * @return true or false
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
        }
        return true;
    }

    /**
     * A list of parts in the inventory
     *
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * a list of products in the inventory
     *
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


    /**
     * creates a new part ID in sequence
     * @return the new part ID
     */
    public static int getNewPartId() {
        return ++newPartID;
    }

    /**
     * creates a new product ID in sequence
     * @return the new product ID
     */
    public static int getNewProductID() {return ++newProductID; }
}

package sample;

import java.util.Date;

/**
 * Represents a manufacturing production record of a product.
 */
public class Production {

  /**
   * The product that was manufactured.
   */
  private Product product;

  /**
   * The amount of the product that was manufactured.
   */
  private int quantity;

  /**
   * The date and time the product was manufactured on.
   */
  private Date manufacturedOn;

  /**
   * Default constructor.
   *
   * @param product  The manufactured product.
   * @param quantity The quantity of the product that was manufactured.
   */
  Production(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
    manufacturedOn = new Date();
  }

  /**
   * Overloaded constructor that accepts a predefined date.
   *
   * @param product        A Product object containing the product that was manufactured.
   * @param quantity       The amount of product that was manufactured.
   * @param manufacturedOn The time and date of production.
   */
  Production(Product product, int quantity, Date manufacturedOn) {
    this.product = product;
    this.quantity = quantity;
    this.manufacturedOn = manufacturedOn;
  }

  /**
   * Getter for the product name.
   *
   * @return String The product name.
   */
  public String getName() {
    return product.getName();
  }

  /**
   * Setter for the product name.
   *
   * @param name The product name.
   */
  public void setName(String name) {
    product.setName(name);
  }

  /**
   * Getter for the production quantity.
   *
   * @return int The amount of product that was manufactured.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Setter for the production quantity.
   *
   * @param quantity The amount of product that was manufactured.
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Getter for the manufacture date.
   *
   * @return Date The time and date of manufacturing.
   */
  public Date getManufacturedOn() {

    // Return a new Date so the user cannot modify its contents outside of this method.
    return new Date(manufacturedOn.getTime());
  }

  /**
   * Setter for the manufacture date.
   *
   * @param manufacturedOn The time and date of manufacturing.
   */
  public void setManufacturedOn(Date manufacturedOn) {

    // Return a new Date so the user cannot modify its contents outside of this method.
    this.manufacturedOn = new Date(manufacturedOn.getTime());
  }

  /**
   * Overrides the toString() method so it returns the values of all attributes.
   *
   * @return String Contains a full description of the production in the following format
   * <br>Product Name:
   * <br>Quantity:
   * <br>Manufacture Date:
   */
  @Override
  public String toString() {
    return String.format("Product Name: %s%nQuantity: %d%nManufacture Date: %tc%n",
        product.getName(), quantity, manufacturedOn);
  }
}

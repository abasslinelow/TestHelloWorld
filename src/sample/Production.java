package sample;

import java.util.Date;

/**
 * This class creates a production record of each product that is
 * created. It allows the user to specify a quantity of products.
 */
class Production {

  /**
   * The product that was created.
   */
  private Product product;

  /**
   * The amount of that product that was created.
   */
  private int quantity;

  /**
   * The date and time they were manufactured on.
   */
  private Date manufacturedOn;

  /**
   * Default constructor.
   * @param product The product.
   * @param quantity The quantity of the product.
   */
  Production(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
    manufacturedOn = new Date();
  }

  /**
   * Overloaded constructor that accepts a predefined date.
   * @param product A Product object containing the product that was produced.
   * @param quantity The amount of product that was produced.
   * @param manufacturedOn The time and date of production.
   */
  Production(Product product, int quantity, Date manufacturedOn) {
    this.product = product;
    this.quantity = quantity;
    this.manufacturedOn = manufacturedOn;
  }

  /**
   * Getter for the product name.
   * @return String The product name.
   */
  String getName() { return product.getName();}

  /**
   * Setter for the product name.
   * @param name The product name.
   */
  void setName(String name) { product.setName(name);}

  /**
   * Getter for the production quantity.
   * @return int The amount of product that was manufactured.
   */
  int getQuantity() { return quantity;}

  /**
   * Setter for the production quantity.
   * @param quantity The amount of product that was manufactured.
   */
  void setQuantity(int quantity) { this.quantity = quantity;}

  /**
   * Getter for the manufacture date.
   * @return Date The time and date of manufacturing.
   */
  Date getManufactureDate() { return manufacturedOn; }

  /**
   * Setter for the manufacture date.
   * @param manufacturedOn The time and date of manufacturing.
   */
  void setManufactureDate(Date manufacturedOn) { this.manufacturedOn = manufacturedOn; }

  /**
   * Overrides the toString() method so it returns the values of all attributes.
   * @return String Contains a full description of the production in the following format
   * Product Name:
   * Quantity:
   * Manufacture Date:
   */
  @Override
  public String toString() {
    return String.format("Product Name: %s%nQuantity: %d%nManufacture Date: %tc%n",
        product.getName(), quantity, manufacturedOn);
  }
}

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
    this.quantity = quantity;
    this.product = product;
    manufacturedOn = new Date();
  }

}

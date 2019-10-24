package sample;

/**
 * Defines a blueprint for all Product objects to be created. All products
 * must have an ID, a name, and a manufacturer.
 */
public interface Item {

  /**
   * Getter for the ID number.
   * @return Returns the ID number.
   */
  int getId();

  /**
   * Getter for the name of the product.
   * @return Returns the name of the product.
   */
  String getName();

  /**
   * Setter for the name of the product.
   * @param name The name of the product.
   */
  void setName(String name);

  /**
   * Getter for the manufacturer of the product.
   * @return Returns the manufacturer of the product.
   */
  String getManufacturer();

  /**
   * Setter for the manufacturer of the product.
   * @param manufacturer The manufacturer of the product.
   */
  void setManufacturer(String manufacturer);
}

package sample;

/**
 * Dictates that all products must have methods for getting id,
 * as well as getting and setting the product name and manufacturer.
 * All Product objects to conform to this interface.
 */
public interface Item {

  /**
   * Gets the ID number of the product.
   * @return The ID number of the product.
   */
  int getId();

  /**
   * Gets the name of the product.
   * @return The name of the product.
   */
  String getName();

  /**
   * Sets the name of the product.
   * @param name The name of the product.
   */
  void setName(String name);

  /**
   * Gets the manufacturer of the product.
   * @return The manufacturer of the product.
   */
  String getManufacturer();

  /**
   * Sets the manufacturer of the product.
   * @param manufacturer The manufacturer of the product.
   */
  void setManufacturer(String manufacturer);
}

package sample;

/**
 * This class contains a generic Product class that is used abstractly
 * as a template for specific types of products as defined in the
 * ItemType enum.
 */
public abstract class Product implements Item {

  /**
   * The id number of the product.
   */
  private int id;

  /**
   * The name of the product.
   */
  private String name;

  /**
   * The manufacturer of the product.
   */
  private String manufacturer;

  /**
   * The "type" of product - see ItemType enum.
   */
  private String type;

  /**
   * Default constructor.
   * @param name The name of the product.
   */
  Product(String name) {this.name = name;}

  /**
   * Getter for the id of the product.
   * @return int The id of the product.
   */
  public int getId() {return id;}

  /**
   * Getter for the name of the product.
   * @return String The name of the product.
   */
  public String getName() {return name;}

  /**
   * Setter for the name of the product.
   * @param name The name of the product.
   */
  public void setName(String name) {this.name = name;}

  /**
   * Returns the manufacturer of the product.
   * @return String The manufacturer of the product.
   */
  public String getManufacturer() {return manufacturer;}

  /**
   * Setter for the manufacturer of the product.
   * @param manufacturer The manufacturer of the product.
   */
  public void setManufacturer(String manufacturer) {this.manufacturer = manufacturer;}

  /**
   * Overrides the toString() method to display the details of an object
   * that inherits the Product class. Display is in this form
   * Name:
   * Manufacturer:
   * Media Type:
   * @return String The complete formatted string description.
   */
  @Override
  public String toString() {
    return String.format(
        "Name: %s%n"
        + "Manufacturer: %s%n"
        + "Media Type: %s%n",
        name, manufacturer, type);
  }
}

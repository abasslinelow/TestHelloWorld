package sample;

/**
 * Temporary class for testing the Product class functionality.
 */
class Widget extends Product {

  /**
   * Default constructor.
   *
   * @param name The name of the widget.
   * @param type The type of the widget. (See ItemType enum)
   */
  Widget(String name, String type) {
    super(name);
    setType(ItemType.AUDIO.type);
  }

  /**
   * Overloaded constructor that allows the product ID to be set.
   */
  Widget(int id, String name, String type) {
    super(name);
    setId(id);
    setType(ItemType.AUDIO.type);
  }
}

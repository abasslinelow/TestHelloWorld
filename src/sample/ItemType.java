package sample;

/**
 * Specifies the "type" of a product - that is, whether it is audio-based or
 * visual-based, and whether it is mobile or not. For each type, there is
 * a corresponding code which the enum ties to the type.
 */
public enum ItemType {

  AUDIO("Audio", "AU"),
  VISUAL("Visual", "VI"),
  AUDIO_MOBILE("AudioMobile", "AM"),
  VISUAL_MOBILE("VisualMobile", "VM");

  /**
   * The type of multimedia that the device plays.
   */
  String type;

  /**
   * The code that corresponds to the type.
   */
  String code;

  /**
   * Default constructor.
   * @param type The type of the multimedia product.
   * @param code The code that corresponds to the type.
   */
  ItemType(String type, String code) {
    this.type = type;
    this.code = code;
  }
}

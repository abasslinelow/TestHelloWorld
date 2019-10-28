package sample;

/**
 * Specifies the "type" of a product - that is, whether it is audio-based or visual-based, and
 * whether it is mobile or not. For each type, there is a corresponding "code" which the enum ties
 * to the type.
 */
public enum ItemType {

  /**
   * Audio players (type: "Audio", code: "AU").
   */
  AUDIO("Audio", "AU"),

  /**
   * Visual players (type: "Visual", code: "VI").
   */
  VISUAL("Visual", "VI"),

  /**
   * Mobile audio players (type: "AudioMobile", code: "AM").
   */
  AUDIO_MOBILE("AudioMobile", "AM"),

  /**
   * Mobile video players (type: "VisualMobile", code: "VM").
   */
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
   *
   * @param type The type of the multimedia product.
   * @param code The code that corresponds to the type.
   */
  ItemType(String type, String code) {
    this.type = type;
    this.code = code;
  }

  /**
   * Gets the media type associated with a given media code.
   *
   * @param passedCode A media code associated with a media player.
   * @return String The media type associated with the given code.
   */
  static String getTypeFromCode(String passedCode) {

    for (ItemType itemType : ItemType.values()) {
      if (passedCode.equals(itemType.code)) {
        return itemType.type;
      }
    }
    return null;
  }

  /**
   * Gets the media code associated with a given media type.
   *
   * @param passedType A media type associated with a media player.
   * @return String The media code associated with the given type.
   */
  static String getCodeFromType(String passedType) {

    for (ItemType itemType : ItemType.values()) {
      if (passedType.equals(itemType.type)) {
        return itemType.code;
      }
    }
    return null;
  }
}

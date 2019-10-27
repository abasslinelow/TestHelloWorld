package sample;

/**
 * An audio player. It stores the name, manufacturer, multimedia type, and
 * multimedia code that belong to the device (see ItemType enum). In addition
 * to these attributes, it has methods for controlling the audio player's
 * playback (see MultimediaControl interface).
 * @see ItemType
 * @see MultimediaControl
 *
 */
public class AudioPlayer extends Product implements MultimediaControl {

  /**
   * Specifies the audio type.
   * @see ItemType
   */
  private String audioSpecification;

  /**
   * Specifies the media type.
   * @see ItemType
   */
  private String mediaType;

  /**
   * Default constructor.
   * @param name The name of the product.
   * @param manufacturer The manufacturer of the product.
   * @param audioSpecification The "type" of product (see ItemType enum)
   */
  AudioPlayer(String name, String manufacturer, String audioSpecification) {
    super(name);
    setManufacturer(manufacturer);
    this.audioSpecification = audioSpecification;

    // I am unclear why there is a mediaType in this class, as the super class already
    // has a "type" variable. Nevertheless, to get the toString to function properly,
    // we must determine which media type was passed into the audioSpecification
    // variable, then store the corresponding code to the mediaType attribute as well
    // as the type attribute in the Product superclass.
    // NOTE: I would prefer to pass the media type directly into the constructor, but
    // I am not sure if this will invalidate automatic testing.
    mediaType = ItemType.getCodeFromType(audioSpecification);
    setType(mediaType);
  }

  /**
   * Play the current audio track.
   */
  @Override
  public void play() {
    System.out.print("Playing audio\n");
  }

  /**
   * Stop the current audio track.
   */
  @Override
  public void stop() {
    System.out.print("Stopping audio\n");
  }

  /**
   * Skip back to the previous audio track.
   */
  @Override
  public void previous() {
    System.out.print("Previous audio track\n");
  }

  /**
   * Skip ahead to the next audio track.
   */
  @Override
  public void next() {
    System.out.print("Next audio track\n");
  }

  /**
   * Prints all private fields belonging to this audio player in the following format.
   * <br>Name:
   * <br>Manufacturer:
   * <br>Media Type:
   * <br>Audio Spec:
   * @return String Returns a String in the above format.
   */
  @Override
  public String toString() {
    return super.toString() + String.format("Audio Spec: %s%n",
        audioSpecification);
  }
}

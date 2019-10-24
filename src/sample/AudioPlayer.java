package sample;

/**
 * This class represents an audio player. It stores the name, manufacturer,
 * multimedia type, and multimedia code that belong to the device. (See the
 * ItemType enum for more information on type and code.) In addition to these
 * attributes, it also had methods for controlling the media player's playback.
 */
public class AudioPlayer extends Product implements MultimediaControl {
  private String audioSpecification;
  private String mediaType;

  /**
   * Default constructor.
   * @param name The name of the product.
   * @param manufacturer The manufacturer of the product.
   * @param audioSpecification The "type" of product (see ItemType enum)
   */
  AudioPlayer(String name, String manufacturer, String audioSpecification) {
    super(name);
    this.setManufacturer(manufacturer);
    this.audioSpecification = audioSpecification;
  }

  /**
   * Play the current track.
   */
  public void play() {
    System.out.print("Playing\n");
  }

  /**
   * Stop the current track.
   */
  public void stop() {
    System.out.print("Stopping\n");
  }

  /**
   * Skip back to the previous track.
   */
  public void previous() {
    System.out.print("Previous track\n");
  }

  /**
   * Skip ahead to the next track.
   */
  public void next() {
    System.out.print("Next track\n");
  }

  /**
   * Prints all private fields belonging to this audio player in the following format
   * Name:
   * Manufacturer:
   * Media Type:
   * Audio Spec:
   * @return String Returns a String in the above format.
   */
  @Override
  public String toString() {
    return super.toString() + String.format("Audio Spec: %s%n",
        audioSpecification);
  }
}

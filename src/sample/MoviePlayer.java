package sample;

/**
 * A movie player. It stores the name, manufacturer, screen, and monitor type
 * that belong to this device (see Screen and MonitorType). In addition
 * to these attributes, it has methods for controlling the movie player's
 * playback (see MultimediaControl interface).
 * @see Screen
 * @see MonitorType
 * @see MultimediaControl
 *
 */
public class MoviePlayer extends Product implements MultimediaControl {

  /**
   * The Screen object that belongs to the player. This holds the resolution,
   * refresh rate, and response rate of the visual display screen.
   * @see Screen
   */
  private Screen screen;

  /**
   * The monitor type of the movie player.
   * @see MonitorType
   */
  private MonitorType monitorType;

  /**
   * The default constructor.
   * @param name The name of the movie player.
   * @param manufacturer The manufacturer of the movie player.
   * @param screen The screen that the movie player has.
   * @param monitorType The monitor type of the movie screen.
   */
  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name);
    this.screen = screen;
    this.monitorType = monitorType;
    setManufacturer(manufacturer);
    setType(ItemType.VISUAL.code);
  }

  /**
   * Play the current video track.
   */
  @Override
  public void play() {
    System.out.print("Playing video\n");
  }

  /**
   * Stop the current video track.
   */
  @Override
  public void stop() {
    System.out.print("Stopping video\n");
  }

  /**
   * Go to the previous video track.
   */
  @Override
  public void previous() {
    System.out.print("Previous video track\n");
  }

  /**
   * Go to the next video track.
   */
  @Override
  public void next() {
    System.out.print("Next video track\n");
  }

  /**
   * Prints all private fields belonging to this video player in the following format.
   * <br>Name:
   * <br>Manufacturer:
   * <br>Media Type:
   * <br>Monitor Type:
   * <br>Resolution:
   * <br>Refresh Rate:
   * <br>Response Rate:
   *
   * @return String Returns a String in the above format.
   */
  @Override
  public String toString() {
    return
      super.toString()
      + "Monitor Type: " + monitorType + "\n"
      + screen.toString();
  }
}

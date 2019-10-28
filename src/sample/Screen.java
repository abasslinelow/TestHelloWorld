package sample;

/**
 * Stores information about the screen attached to a Product. Specifically, it stores the screen
 * resolution, refresh rate, and response rate.
 */
public class Screen implements ScreenSpec {

  /**
   * The screen resolution size, eg 800x600.
   */
  private String resolution;

  /**
   * The refresh rate of the screen in Hz.
   */
  private int refreshrate;

  /**
   * The response time of the screen in milliseconds.
   */
  private int responsetime;

  /**
   * The default constructor.
   *
   * @param resolution   The screen resolution size.
   * @param refreshrate  The refresh rate (in Hz).
   * @param responsetime The response time (in ms).
   */
  Screen(String resolution, int refreshrate, int responsetime) {
    this.resolution = resolution;
    this.refreshrate = refreshrate;
    this.responsetime = responsetime;
  }

  /**
   * Getter for the resolution.
   *
   * @return String The resolution of the screen.
   */
  @Override
  public String getResolution() {
    return resolution;
  }

  /**
   * Getter for the refresh rate.
   *
   * @return int The refresh rate of the screen.
   */
  @Override
  public int getRefreshRate() {
    return refreshrate;
  }

  /**
   * Getter for the response rate.
   *
   * @return int The response rate of the screen.
   */
  @Override
  public int getResponseTime() {
    return responsetime;
  }

  /**
   * Overrides the toString() method to display the details of the screen in the following format.
   * <br>Resolution:
   * <br>Refresh Rate:
   * <br>Response Rate:
   *
   * @return String The complete formatted string description.
   */
  @Override
  public String toString() {
    return String.format(
        "Resolution: %s%n"
            + "Refresh Rate: %s%n"
            + "Response Rate: %s%n",
        resolution, refreshrate, responsetime);
  }
}

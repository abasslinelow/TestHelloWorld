package sample;

/**
 * Dictates that all screens attached to a multimedia player contain methods
 * to get the screen's resolution size, refresh rate, and response time.
 */
public interface ScreenSpec {

  /**
   * Gets the resolution of the screen.
   * @return String The resolution size of the screen.
   */
  String getResolution();

  /**
   * Gets the refresh rate of the screen.
   * @return int The refresh rate of the screen (in Hz).
   */
  int getRefreshRate();

  /**
   * Gets the response time of the screen.
   * @return int the response time of the screen (in milliseconds).
   */
  int getResponseTime();
}

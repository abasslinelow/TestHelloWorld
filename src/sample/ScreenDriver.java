package sample;

/**
 * A simple main loop to test the creation of an Screen object.
 */

public class ScreenDriver {

  /**
   * The main loop of the driver.
   *
   * @param args A command-line argument passed to the problem.
   */
  public static void main(String[] args) {

    // Instantiate a new Screen.
    Screen myScreen = new Screen(
        "1024x768",
        10,
        10
    );

    // Print the details of the screen to confirm attributes are
    // being stored properly.
    System.out.printf("getResolution(): %s%n", myScreen.getResolution());
    System.out.printf("getRefreshrate(): %s%n", myScreen.getRefreshRate());
    System.out.printf("getResponseTime(): %s%n", myScreen.getResponseTime());
    System.out.print("\n");

    // Ensure the toString() method formats properly.
    System.out.print("toString(): \n" + myScreen.toString());
  }
}

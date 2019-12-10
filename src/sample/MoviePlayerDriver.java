package sample;

/**
 * A simple main loop to test the creation of an MoviePlayer object.
 */

public class MoviePlayerDriver {

  /**
   * The main loop of the driver.
   *
   * @param args A command-line argument passed to the problem.
   */
  public static void main(String[] args) {

    // Create a new MoviePlayer.
    MoviePlayer myPlayer = new MoviePlayer(
        "myPlayer",
        "Mypple",
        ItemType.VISUAL.type,
        new Screen("800 x 1280", 60, 10),
        MonitorType.LED
    );
    myPlayer.setType(ItemType.VISUAL_MOBILE.type);

    // Test each function of the MoviePlayer to ensure functionality.
    myPlayer.play();
    myPlayer.stop();
    myPlayer.previous();
    myPlayer.next();

    // Print the details of the MoviePlayer to confirm attributes are
    // being stored properly.
    System.out.print("\n");
    System.out.print(myPlayer.toString());
  }
}

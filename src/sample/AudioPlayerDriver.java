package sample;

/**
 * A simple main loop to test the creation of an AudioPlayer object.
 */

public class AudioPlayerDriver {

  /**
   * The main loop of the driver.
   * @param args A command-line argument passed to the problem.
   */
  public static void main(String[] args) {

    // Create a new AudioPlayer and set its type.
    AudioPlayer myPlayer = new AudioPlayer(
        "iPod",
        "Apple",
        ItemType.AUDIO_MOBILE.type
        );

    // Test each function of the AudioPlayer to ensure functionality.
    myPlayer.play();
    myPlayer.stop();
    myPlayer.previous();
    myPlayer.next();

    // Print the details of the AudioPlayer to confirm attributes are
    // being stored properly.
    System.out.print("\n");
    System.out.print(myPlayer.toString());
  }
}

package sample;

/**
 * A simple main loop to test the creation of an AudioPlayer object.
 */

public class AudioPlayerDriver {
  public static void main(String[] args) {

    // Create a new AudioPlayer.
    AudioPlayer myPlayer = new AudioPlayer(
        "iPad",
        "Apple",
        "AudioMobile"
        );

    // Test each function of the AudioPlayer to ensure functionality.
    myPlayer.play();
    myPlayer.stop();
    myPlayer.previous();
    myPlayer.next();

    // Print the details of the AudioPlayer to confirm attributes are
    // being stored properly.
    System.out.print(myPlayer.toString());
  }
}

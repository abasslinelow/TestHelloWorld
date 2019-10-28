package sample;

/**
 * Demonstrates polymorphism by showing that any class implementing MultimediaControl can be
 * instantiated, and that the methods defined in MultimediaControl can be used regardless of the
 * class that implements it.
 * <p>In this example, I create both an audio and video player. I
 * create an audio MultimediaControl object by instantiating it with the AudioPlayer class. I do the
 * same for the MoviePlayer class. I then use the methods of both (which override the methods in
 * MultimediaControl) to demonstrate polymorphism.</p>
 */

public class MultimediaControlDriver {

  /**
   * The main loop of the driver.
   *
   * @param args A command-line argument passed to the problem.
   */
  public static void main(String[] args) {

    // Create a new AudioPlayer and print its attributes.
    MultimediaControl myAudioPlayer = new AudioPlayer(
        "iPod",
        "Apple",
        ItemType.AUDIO_MOBILE.type
    );
    System.out.print("AUDIO PLAYER\n");
    System.out.print(myAudioPlayer.toString());
    System.out.print("\n");

    // Create a new MoviePlayer and print its attributes.
    MultimediaControl myMoviePlayer = new MoviePlayer(
        "iPhone X",
        "Apple",
        new Screen("2436x1125", 120, 5),
        MonitorType.LED
    );
    System.out.print("MOVIE PLAYER\n");
    System.out.print(myMoviePlayer.toString());
    System.out.print("\n");

    // Test each function of both of the players to ensure they both can
    // use the functions implemented from MultimediaControl regardless of
    // what kind of device they are.
    myAudioPlayer.play();
    myMoviePlayer.play();

    myAudioPlayer.stop();
    myMoviePlayer.stop();

    myAudioPlayer.previous();
    myMoviePlayer.previous();

    myAudioPlayer.next();
    myMoviePlayer.next();
  }
}

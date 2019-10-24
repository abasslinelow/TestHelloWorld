package sample;

/**
 * This interface contains a blueprint for Multimedia Player objects.
 */
public interface MultimediaControl {

  /**
   * Play the current song.
   */
  void play();

  /**
   * Stop the current song.
   */
  void stop();

  /**
   * Skip back to the previous song.
   */
  void previous();

  /**
   * Skip ahead to the next song.
   */
  void next();
}

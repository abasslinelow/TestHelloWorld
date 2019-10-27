package sample;

/**
 * Dictates that all multimedia player objects shall play and stop songs, as well as
 * skip to the previous and next tracks.
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

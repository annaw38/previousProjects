/**
 * Backend interface for class that defines a movie and exposes its movie properties
 * 
 */
public interface MovieInterface {
  
  /**
   * Constructor for Movie 
   */
  /*public Movie() {
    this.title = title;
    this.genre = genre;
    this.year = year;
    this.country = country;
    this.duration = duration;
  }
  */
  
  //Getters of movie's properties: title, genre, year, country, and duration
  /**
   * Gets the title of the movie to be added
   * @return the title of the movie to be added
   */
  public String getTitle();
  
  /**
   * Gets the genre of the movie to be added
   * @return the genre of the movie to be added
   */
  public String getGenre();
  
  /**
   * Gets the year of the movie to be added
   * @return the year of the movie to be added
   */
  public int getYear();
  
  /**
   * Gets the country of the movie to be added
   * @return the country of the movie to be added
   */
  public String getCountry();
  
  /**
   * Gets the duration of the movie to be added
   * @return the duration of the movie to be added
   */
  public String getDuration();
  
}
// --== CS400 Fall 2023 File Header Information ==--
// Name: Anna Wang
// Email: awang282@wisc.edu
// Group: <your group's name: C20
// TA: Manas Trivedi
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

/**
 * Movie class for Movie Timer app
 *
 */
public class Movie implements MovieInterface{
  
  //data fields 
  private String title;
  private String genre;
  private int year;
  private String country;
  private int duration;
  
  //Movie Constructor
  /**
   * Creates a new Movie and initializes its data fields
   * @param title the original title of the movie
   * @param genre the genre of the movie
   * @param year the year the movie was released
   * @param country the country the movie was filmed in 
   * @param duration the duration of the movie in minutes
   */
  public Movie(String title, String genre, int year, String country, int duration) {
    this.title = title;
    this.genre = genre;
    this.year = year;
    this.country = country;
    this.duration = duration;
  }
  
  /**
   * Compares the duration of the movie with another movie, returns a positive or negative 1 or 0.
   */
  @Override
  public int compareTo(Movie o) {
    //if the other movie's duration is smaller than this movie's duration then return -1
    if(o.getDuration() > this.duration) {
      return -1;
    }
    
    //if the this' duration is larger than other movie's duration then return 1
    else if(o.getDuration()<this.duration) {
      return 1;
    }
    
    //else return 0 because they have the same duration
    else {
      return 0;
    }
  }
  
  /**
   * Gets the original title of this movie
   * @return the original title of this movie
   */
  @Override
  public String getTitle() {
    return title;
  }
  /**
   * Gets the genre of this movie
   * @return the genre of the movie
   */
  @Override
  public String getGenre() {
    return genre;
  }
  /**
   * Gets the year this movie was released
   * @return the year this movie was released
   */
  @Override
  public int getYear() {
    return year;
  }
  /**
   * Gets the country this movie was filmed
   * @return the country this movie was filmed
   */
  @Override
  public String getCountry() {
    return country;
  }
  /**
   * Gets the duration of this movie (in minutes)
   * @return the duration of the movie
   */
  @Override
  public int getDuration() {
    return duration;
  }
  

}

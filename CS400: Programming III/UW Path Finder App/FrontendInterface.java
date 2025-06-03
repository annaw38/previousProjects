public interface FrontendInterface{
    /**
     * Constructor comment specifying expected parameters
     * @param backend the backend to use
     * @param scanner for reading input
     * public Frontend(Backend backend, Scanner scanner);
     */


    /**
     * Starts the interactive command loop.
     */
    public void start();

    /**
     * Displays the main menu options.
     */
    public void showMainMenu();

    /**
     * Implements the load data file command.
     */
    public void loadDataCommand();

    /**
     * Implements the show dataset statistics command.
     */
    public void showStatsCommand();

    /**
     * Prompts the user for a start and end building.
     */
    public void getRouteInput();


    /**
     * Displays the shortest path results to the user.
     */
    public void displayRoute(String from, String to);

    /**
     * Exits the application.
     */
    public void exitCommand();

}
import java.util.List;
public interface PathResultInterface {
    /** Get a list of all the stops in our path.
     * @return A list of NodeT values representing the stops.
     * @implSpec getPathStops().size() == getSegmentCosts().size() + 1
     */
    public List<String> getPathStops();
    /** Get a list of all of the walking times between stops.
     * @return A list of Double values, all greater than 0.0.
     * @implSpec g
     */ 
    public List<Double> getSegmentCosts();
    /** Get the total cost of the path resulting from our search.
     *
     * @return A number greater than (or equal to, if the start and 
     * 	       end nodes are the same) 0.0.
     * @implSpec getPathStops().size() == getSegmentCosts().size() + 1
     * 		 This equation must hold.
     */
    default double getTotalCosts() {
	// doubleValue 
	return getSegmentCosts().stream().reduce(0.0, (a, b) -> a + b);
    }
}

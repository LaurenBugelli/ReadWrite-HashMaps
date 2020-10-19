package bugelli;
/**
 * The stopwatch class keeps track of time. 
 * @since 10/11/2020
 * @author Lauren Bugelli
 * @version 1
 */
public class Stopwatch {
	 /**
     * duration is the cumulative amount of time, in milliseconds, that has passed on the stopwatch between start() and stop() calls.
     *
     */
    private long duration = 0;
    private long start = 0;
    
    /**
     * When this flag is true, the stopwatch is still going (stop() hasn't been called since the last start() call),
     * and the duration doesn't update until it's stopped, so the time returned needs account for the actively counting time.
     */
    private boolean active = false;

    /**
     * Starts the stopwatch.
     */
    public void start() {
        this.start = System.currentTimeMillis();
        this.active = true;
    }
    
    /**
     * Stops the stopwatch.
     */
    public void stop() {
        if (this.active) this.duration += (System.currentTimeMillis() - this.start);
        this.active = false;
    }

    /**
     * Returns the duration of the stopwatch as a long, in milliseconds.
     * 
     * 
     * @return the duration of the stopwatch, in milliseconds
     */
    public long getMilliseconds() {
        if (this.active) return this.duration + System.currentTimeMillis() - this.start;
        else return this.duration;
    }
    

}

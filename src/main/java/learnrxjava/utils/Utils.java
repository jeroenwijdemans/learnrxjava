package learnrxjava.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import learnrxjava.examples.SubscribeOnObserveOnExample;

public class Utils {

    /**
     * Add a delay by doing a Thread.sleep()
     * 
     * Used for educational purposes. Thread.sleep in an asynchronous context is
     * almost always a bad idea. Come to think about it, Thread.sleep in a synchronous
     * context is a bad idea too.
     *
     * @param millis number of milliseconds to wait before resuming
     */
    public static void delay(int millis) {
        try {
            //System.out.println("Delaying work on thread " + Thread.currentThread().getName());
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(SubscribeOnObserveOnExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

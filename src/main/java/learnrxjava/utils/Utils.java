package learnrxjava.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import learnrxjava.examples.SubscribeOnObserveOnExample;

public class Utils {

    /**
     * Add a delay by doing a Thread.sleep()
     * 
     * @param millis number of milliseconds to wait before resuming
     */
    public static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(SubscribeOnObserveOnExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

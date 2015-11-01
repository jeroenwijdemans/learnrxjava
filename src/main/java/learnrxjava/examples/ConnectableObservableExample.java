package learnrxjava.examples;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import rx.Observable;
import rx.observables.ConnectableObservable;

public class ConnectableObservableExample {

    public static void main(String[] args) {
        // Use publish not replay
        ConnectableObservable<Long> hotObservable = Observable.interval(1, TimeUnit.SECONDS).publish();
        hotObservable.connect();
        hotObservable.subscribe(System.out::println);
        
        delay();
        
        hotObservable.subscribe(e -> System.out.println("Late to the party " + e));
        
        delay();
        
    }

    private static void delay() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(SubscribeOnObserveOnExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package learnrxjava.examples;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import learnrxjava.utils.Utils;
import rx.Observable;
import rx.observables.ConnectableObservable;

public class ConnectableObservableExample {

    public static void main(String[] args) {
        ConnectableObservable<Long> hotObservable = Observable.interval(1, TimeUnit.SECONDS).publish();
        hotObservable.connect();
        hotObservable.subscribe(System.out::println);
        
        Utils.delay(3000);
        
        hotObservable.subscribe(e -> System.out.println("Late to the party " + e));
        
        Utils.delay(3000);
        
    }

}

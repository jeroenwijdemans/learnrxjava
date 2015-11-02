package learnrxjava.examples;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import learnrxjava.utils.Utils;
import static learnrxjava.utils.Utils.delay;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

// TODO finish documentation (is referenced from exercise)

/**
 * Explanation of subscribeOn() and observeOn()
 */
public class SubscribeOnObserveOnExample {

    public static void main(String[] args) {

        // run all on same thread -> alternating pattern
        // subscribe on other thread
        // observe on other thread
        // subscribe and observe on other thread
        
        // Create an observable that outputs 
        Observable<Integer> nums = nums(100);
        
        runOnSameThread();
        
//        nums.subscribe(new MySubscriber());
//        nums.subscribeOn(Schedulers.computation()).subscribe(new MySubscriber());
//        nums.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(new MySubscriber());
        
        // Give the other threads time to finish
//        delay(1000);
        
        System.out.println("Thread " + Thread.currentThread().getName() + " has finished...");
    }
    
    private static void runOnSameThread() {
        Observable<Integer> nums = nums(100);
        
        // Run everything on the same thread
        // You should witness an alternating pattern of emissions and onNext 
        // calls. Think about this. Is this asynchronous?
        nums.subscribe(new MySubscriber());
    }
    
    /**
     * @param n number of integers to output
     * @return an Observable that outputs n incrementing integers starting from 0 and that displays
     *         the thread it's working on
     */
    private static Observable<Integer> nums(int n) {
        return Observable.create(subscriber -> {
            for (int i=0; i<100; i++) {
                System.out.println("Pushing value " + i + " on thread " + Thread.currentThread().getName());
                subscriber.onNext(i);
            }
            subscriber.onCompleted();
        });
    }
}

/**
 * Convenience class. Outputs on which thread it's receiving events
 */
class MySubscriber extends Subscriber<Integer> {

    @Override
    public void onCompleted() {
        System.out.println("OnCompleted: Thread = " + Thread.currentThread().getName());
    }

    @Override
    public void onError(Throwable thrwbl) {
        System.out.println("OnError: Thread = " + Thread.currentThread().getName());
    }

    @Override
    public void onNext(Integer t) {
        System.out.println("OnNext ("+t+"): Thread = " + Thread.currentThread().getName());
    }

}

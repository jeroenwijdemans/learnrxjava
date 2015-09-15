package learnrxjava.examples;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SubscribeOnObserveOnExample {

    public static void main(String[] args) {
        // Main thread, synchronous
        //Observable<Long> nums = Observable.interval(1, TimeUnit.SECONDS).take(20);
//        Observable<Long> nums = Observable.from(Arrays.asList(1, 2, 3, 4, 5));
        //Observable<Integer> nums = Observable.range(1, 500);
        Observable<Integer> nums = Observable.create(subscriber -> {
            for (int i=0; i<100; i++) {
                System.out.println("Pushing value " + i + " on thread " + Thread.currentThread().getName());
                subscriber.onNext(i);
            }
            subscriber.onCompleted();
        });
//        nums.subscribe(new MySubscriber());
        
//        nums.subscribeOn(Schedulers.computation()).subscribe(new MySubscriber());
        nums.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(new MySubscriber());
        
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(SubscribeOnObserveOnExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " has finished...");
    }

}

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

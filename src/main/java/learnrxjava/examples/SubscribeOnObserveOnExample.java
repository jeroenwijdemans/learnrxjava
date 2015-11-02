package learnrxjava.examples;

import static learnrxjava.utils.Utils.delay;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Explanation of subscribeOn() and observeOn().
 * 
 * IMPORTANT NOTE! Examples may vary on used hardware (multicore, speed, etc.)
 * 
 * Try to predict the output before running the examples.
 * @see http://reactivex.io/documentation/operators/subscribeon.html
 */
public class SubscribeOnObserveOnExample {

    public static void main(String[] args) {
        String mainThread = Thread.currentThread().getName();
        System.out.println("Thread " + mainThread + " has finished...");
        
        // Uncomment and comment the methods below to experience the differences
        
        // 1
        runOnSameThread();
  
        // 2
        //subscribeOnDifferentThread();
        
        // 3
        //observeOnDifferentThread();
        
        // 4 This is the fully reactive stuff we're talking about!
        //subscribeAndObserveOnDifferentThreads();
        
        System.out.println("Thread " + mainThread + " has finished...");
    }
    
    /**
     * Run everything on the same thread. Think about this. Is this asynchronous?
     */
    private static void runOnSameThread() {
        System.out.println("Run everything on the same thread (main)");
        Observable<Integer> nums = nums(10);
        nums.subscribe(new MySubscriber());
        System.out.println("----------------------------------------\n");
    }

    /**
     * Subscribe on a different thread. With subscribeOn you can instruct an 
     * Observable to do its work on a particular Scheduler. This means both the
     * emission of values as well as the observation of them. Take a moment
     * to think about the implications: what does this mean for the output 
     * pattern on the thread you run the Observable and Observer run?
     */
    private static void subscribeOnDifferentThread() {
        System.out.println("Subscribe on different thread");
        Observable<Integer> nums = nums(10);
        nums.subscribeOn(Schedulers.newThread()).subscribe(new MySubscriber());
        System.out.println("----------------------------------------\n");
    }
    
    /**
     * Observe on a different thread. The ObserveOn operator is similar, but 
     * more limited. It instructs the Observable to send notifications to 
     * observers on a specified Scheduler.
     */
    private static void observeOnDifferentThread() {
        System.out.println("Observe on different thread");
        Observable<Integer> nums = nums(100); // Play with this number a bit
                                              // Can you see why we increased
                                              // it for this exercise?
        nums.observeOn(Schedulers.newThread()).subscribe(new MySubscriber());
        System.out.println("----------------------------------------\n");
        
        delay(1000); // What happens to the output when you remove this line?
    }
    
    /**
     * Subscribe and observe on different threads. As a bonus we used one of the
     * built in threadpools of RxJava called Schedulers. We chose the computation
     * pool. Feel free to experiment with changing the scheduler and see if you
     * can predict the influence on the output.
     */
    private static void subscribeAndObserveOnDifferentThreads() {
        System.out.println("Subscribe and observe on different threads");
        Observable<Integer> nums = nums(100);
        // We used the computation threadpool. Feel free to change and experiment.
        nums.subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe(new MySubscriber());
        System.out.println("----------------------------------------\n");
        
        delay(1000); 
    }
    
    /**
     * @param n number of integers to output
     * @return an Observable that outputs n incrementing integers starting from 0 and that displays
     *         the thread it's working on
     */
    private static Observable<Integer> nums(int n) {
        return Observable.create(subscriber -> {
            for (int i=0; i<n; i++) {
                System.out.println("Pushing value " + i + " on thread " + 
                        Thread.currentThread().getName());
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

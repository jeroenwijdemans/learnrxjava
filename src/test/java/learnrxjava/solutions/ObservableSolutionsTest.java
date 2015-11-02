package learnrxjava.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.SECONDS;
import learnrxjava.exercises.ObservableExercises;
import learnrxjava.exercises.ObservableExercisesTest;
import static org.junit.Assert.assertTrue;
import rx.Observable;
import rx.Scheduler;
import rx.observables.ConnectableObservable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

public class ObservableSolutionsTest extends ObservableExercisesTest {

    public ObservableExercises getImpl() {
        return new ObservableSolutions();
    }

    @Override
    public void exercise27() {
        TestScheduler testScheduler = Schedulers.test();
        TestSubscriber<Long> immediateSubscriber = new TestSubscriber<>();
        TestSubscriber<Long> delayedSubscriber = new TestSubscriber<>();
        
        Observable<Long> nums = Observable.interval(1, SECONDS, testScheduler).take(10);
        
        getImpl().exercise27(nums, testScheduler, immediateSubscriber, delayedSubscriber);
        
        testScheduler.advanceTimeBy(100, SECONDS);
        
        List<Long> expectedImmediate = Arrays.asList(0L,1L,2L,3L,4L,5L,6L,7L,8L,9L);
        List<Long> expectedDelayed = Arrays.asList(0L,1L,2L,3L,4L,5L,6L,7L,8L,9L);
        
        immediateSubscriber.assertReceivedOnNext(expectedImmediate);
        delayedSubscriber.assertReceivedOnNext(expectedDelayed); 
    }
    
    @Override
    public void exercise28() {
        TestScheduler testScheduler = Schedulers.test();
        TestSubscriber<Long> immediateSubscriber = new TestSubscriber<>();
        TestSubscriber<Long> delayedSubscriber = new TestSubscriber<>();
        
        ConnectableObservable<Long> nums = Observable.interval(1, SECONDS, testScheduler).take(10).publish();
        nums.connect();
                
        getImpl().exercise27(nums, testScheduler, immediateSubscriber, delayedSubscriber);
        
        testScheduler.advanceTimeBy(100, SECONDS);
        
        List<Long> expectedImmediate = Arrays.asList(0L,1L,2L,3L,4L,5L,6L,7L,8L,9L);
        List<Long> expectedDelayed = Arrays.asList(5L,6L,7L,8L,9L);
        
        immediateSubscriber.assertReceivedOnNext(expectedImmediate);
        delayedSubscriber.assertReceivedOnNext(expectedDelayed); 
    }

    @Override
    public void exercise29() {
        Observable<Integer> unverifiedObservable = Observable.from(Arrays.asList(1,2,3,4,5,6,7,8,9,10))
            .filter(x -> x % 2 == 0)
            .map(x -> x * 2 + 1);

        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        unverifiedObservable.subscribe(testSubscriber);
        testSubscriber.assertReceivedOnNext(Arrays.asList(5, 9, 13, 17, 21));
    }
    
    @Override
    public void exercise30() {
        Scheduler scheduler = Schedulers.test(); 
        Observable<Long> sluggishObservable = Observable.interval(1, TimeUnit.DAYS, scheduler).take(10);
        
        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
        sluggishObservable.subscribe(testSubscriber);
        
        TestScheduler testScheduler = (TestScheduler) scheduler;
        testScheduler.advanceTimeBy(10, DAYS);
        
        testSubscriber.assertReceivedOnNext(Arrays.asList(0L, 1L,2L,3L,4L,5L,6L,7L,8L,9L));
    }
    
    @Override
    public void exercise31() {
        assertTrue(true);
    }

    @Override
    public void exercise32() {
        Observable<Long> nums = Observable.interval(
                1 
                , TimeUnit.MICROSECONDS
                , Schedulers.immediate() 
        ).take(1000);
        
        List<Long> result = new ArrayList<>();
        nums.subscribe(x -> result.add(x));
        assertTrue("Items received: " + result.size(), result.size() == 1000);
    }
    
    @Override
    public void exercise33() {
        assertTrue(true);
    }

    

    

    

    
}

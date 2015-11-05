package learnrxjava.exercises;

import java.util.*;

import learnrxjava.types.*;
import learnrxjava.utils.Utils;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;

import static java.util.concurrent.TimeUnit.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static rx.Observable.*;

import rx.observables.ConnectableObservable;
import rx.observables.GroupedObservable;
import rx.subjects.Subject;

public class ObservableExercisesTest {
    static final Random PRNG = new Random();

    public ObservableExercises getImpl() {
        return new ObservableExercises();
    }

    @Test
    public void exercise00() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise00(from(asList("Remko", "Hedzer"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        assertEquals(2, ts.getOnNextEvents().size());
        assertEquals(asList("Hello Remko!", "Hello Hedzer!"), ts.getOnNextEvents());
    }

    @Test
    public void exercise01() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exercise01(range(1, 10)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        assertEquals(5, ts.getOnNextEvents().size());
        assertEquals(asList(2, 4, 6, 8, 10), ts.getOnNextEvents());
    }

    @Test
    public void exercise02() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise02(range(1, 10)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("2-Even", "4-Even", "6-Even", "8-Even", "10-Even"));
    }

    @Test
    public void exercise03() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exercise03(gimmeSomeMovies()).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(70111470, 654356453, 65432445, 675465));
    }

    @Test
    public void exercise04() {
        int result = getImpl().exercise04(range(1, 10));
        assertEquals(55, result);
    }

    @Test
    public void exercise05() {
        Observable<Integer> faulty = Observable.error(new RuntimeException("Faulty as Microsoft Windows"));
        String result = getImpl().exercise05(faulty);
        assertEquals("Faulty as Microsoft Windows", result);
    }

    @Test
    public void exercise06() {
        String result = getImpl().exercise06(range(1, 10));
        assertEquals("found 10 items", result);
    }

    @Test
    public void exercise07() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise07("Just - a single value").subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Just - a single value"));
    }

    @Test
    public void exercise08() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise08(1).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("The number 42 divided by your input is: 42"));

        getImpl().exercise08(0).subscribe(ts);
        ts.awaitTerminalEvent();
        assertEquals(ArithmeticException.class, ts.getOnErrorEvents().get(0).getClass());
    }

    @Test
    public void exercise09() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise09().subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Hello World!"));
    }

    @Test
    public void exercise10() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise10(just("one", "two", "blue", "red"), just("fish", "fish", "fish", "fish", "fish")).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("one fish", "two fish", "blue fish", "red fish"));
    }

    @Test
    public void exercise11() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise11(gimmeSomeMovies()).subscribe(ts);
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Die Hard"));
    }

    @Test
    public void exercise12() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise12(gimmeSomeMoreMovies(), 5.0).subscribe(ts);
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Fracture"));
    }

    @Test
    public void exercise13() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise13(gimmeSomeMoreMovies()).subscribe(ts);
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Die Hard", "MIB2", "Interstellar", "Bad Boys", "The Chamber"));
    }
    
    @Test
    public void exercise14() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise14(gimmeSomeMoreMovies(), 3).subscribe(ts);
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Bad Boys", "The Chamber", "Fracture"));
    }

    @Test
    public void exercise15() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise15(gimmeSomeMoreMovies()).subscribe(ts);
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("MIB2", "Interstellar", "Fracture", "Gladiator", "Valhalla Rising"));
    }

    @Test
    public void exercise16() {
        TestSubscriber<Boolean> ts = new TestSubscriber<>();
        getImpl().exercise16(gimmeSomeMoreMovies()).subscribe(ts);
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(false, false, false, true
                , false, false, false, false, false, false, false, false, false));
    }

    @Test
    public void exercise17() {
        TestSubscriber<Boolean> ts = new TestSubscriber<>();
        getImpl().exercise17(gimmeSomeMovies().first()).subscribe(ts);
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Collections.singletonList(false));

        TestSubscriber<Boolean> ts2 = new TestSubscriber<>();
        getImpl().exercise17(gimmeSomeMovies().last()).subscribe(ts2);
        ts2.assertNoErrors();
        ts2.assertReceivedOnNext(Collections.singletonList(true));
    }
    
    @Test
    public void exercise18() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        TestScheduler scheduler = Schedulers.test();
        getImpl().exercise18(scheduler).subscribe(ts);
        scheduler.advanceTimeBy(10, SECONDS);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("one 1", "two 2", "three 3", "four 4", "five 5"));
    }

    @Test
    public void exercise19() {
        TestScheduler scheduler = Schedulers.test();
        TestSubscriber<Long> ts = new TestSubscriber<>();
        Observable<Long> odd = Observable.interval(1, SECONDS, scheduler).filter(i -> i % 2 != 0);
        Observable<Long> even = Observable.interval(1, SECONDS, scheduler).filter(i -> i % 2 == 0);
        getImpl().exercise19(odd, even).subscribe(ts);
        scheduler.advanceTimeBy(10, SECONDS);
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L));
    }
    
    @Test
    public void exercise20() {
        TestSubscriber<Map<Integer, Integer>> ts = new TestSubscriber<>();
        // as we can't rely on the ordering this time, we use different assertions for exercise03
        Map<Integer, Integer> map = getImpl().exercise20(gimmeSomeMovies()).toMap(i -> i).toBlocking().single();
        ts.assertNoErrors();
        assertTrue(map.containsKey(70111470));
        assertTrue(map.containsKey(654356453));
        assertTrue(map.containsKey(65432445));
        assertTrue(map.containsKey(675465));
    }

    @Test
    public void exercise21() {
        assertTrue(getImpl().exercise21());
    }

    @Test
    public void exercise22() {
        TestScheduler scheduler = Schedulers.test();
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise22(gimmeSomeMoviesEvery(3, SECONDS, scheduler), scheduler).subscribe(ts);
        scheduler.advanceTimeBy(4, SECONDS);
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Die Hard", "Bad Boys"));
        scheduler.advanceTimeBy(4, SECONDS);
        ts.assertNoErrors();
        ts.assertTerminalEvent();
    }

    @Test
    public void exercise23() {
        TestSubscriber<List<Integer>> ts = new TestSubscriber<>();
        TestScheduler scheduler = Schedulers.test();

        Observable<Integer> burstyParallelNumbers = getBurstyNumbers(10000, 3000, scheduler);

        AtomicInteger totalBuffers = new AtomicInteger();
        AtomicInteger totalNumbers = new AtomicInteger();
        AtomicInteger minBufferSize = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger maxBufferSize = new AtomicInteger(Integer.MIN_VALUE);
        getImpl().exercise23(burstyParallelNumbers, scheduler).doOnNext(numbersBuffer -> {
            totalBuffers.incrementAndGet();
            totalNumbers.addAndGet(numbersBuffer.size());
            boolean casSuccess;
            do {
                int currentMin = minBufferSize.get();
                int newMin = Math.min(currentMin, numbersBuffer.size());
                casSuccess = minBufferSize.compareAndSet(currentMin, newMin);
            } while (!casSuccess);
            do {
                int currentMax = maxBufferSize.get();
                int newMax = Math.max(currentMax, numbersBuffer.size());
                casSuccess = maxBufferSize.compareAndSet(currentMax, newMax);
            } while (!casSuccess);
        }).subscribe(ts);

        scheduler.advanceTimeBy(3500, MILLISECONDS);

        ts.assertTerminalEvent();
        assertEquals(10000, totalNumbers.get());
        assertTrue(totalBuffers.get() >= 3000 / 500);
        assertNotEquals(minBufferSize.get(), 500);
        assertNotEquals(maxBufferSize.get(), 500);
    }

    private Observable<Integer> getBurstyNumbers(int numberOfBurstyItems, int maxDelay, TestScheduler scheduler) {
        return Observable
            .range(0, numberOfBurstyItems)
            .map(Observable::just)
            .flatMap(i -> i.delay(PRNG.nextInt(maxDelay), MILLISECONDS, scheduler));
    }

    @Test
    public void exercise24() {
        TestSubscriber<Observable<Integer>> ts = new TestSubscriber<>();
        TestScheduler scheduler = Schedulers.test();

        Observable<Integer> burstyParallelNumbers = getBurstyNumbers(10000, 3500, scheduler);
        getImpl().exercise24(burstyParallelNumbers, scheduler).subscribe(ts);

        scheduler.advanceTimeBy(3500, MILLISECONDS);

        ts.assertTerminalEvent();
        ts.assertNoErrors();
        List<Observable<Integer>> onNextEvents = ts.getOnNextEvents();
        assertEquals(4, onNextEvents.size());
        onNextEvents.forEach(intObs -> {
            int count = intObs.count().toBlocking().first();
            assertTrue(count >= 500);
            assertTrue(count <= 700);
        });
    }

    @Test
    public void exercise25() {
        TestSubscriber<Movie> ts = new TestSubscriber<>();
        TestScheduler scheduler = Schedulers.test();

        Func1<Movie, Observable<Movie>> advertFunction = m -> Observable.just(new Movie(m.id * 2, "ad 1", 5), new Movie(m.id * 4, "ad 2", 4));
        getImpl().exercise25(gimmeSomeMoreMoviesFlatEvery(1, SECONDS, scheduler), advertFunction, scheduler).subscribe(ts);

        assertEquals(0, ts.getOnNextEvents().size());

        scheduler.advanceTimeBy(1, SECONDS);
        ts.assertNoErrors();
        assertEquals(0, ts.getOnNextEvents().size());

        scheduler.advanceTimeBy(10, SECONDS);
        ts.assertNoErrors();
        assertEquals(2, ts.getOnNextEvents().size());

        scheduler.advanceTimeBy(1, SECONDS);
        ts.assertNoErrors();
        assertEquals(4, ts.getOnNextEvents().size());

        scheduler.advanceTimeBy(1, SECONDS);
        ts.assertNoErrors();
        assertEquals(6, ts.getOnNextEvents().size());

        scheduler.advanceTimeBy(9, SECONDS);
        ts.assertNoErrors();
        assertEquals(24, ts.getOnNextEvents().size());

        scheduler.advanceTimeBy(1, SECONDS);
        ts.assertNoErrors();
        assertEquals(26, ts.getOnNextEvents().size());

        ts.assertTerminalEvent();
    }
    
    @Test
    public void exercise26() {
        TestSubscriber<Double> ts = new TestSubscriber<>();
        TestScheduler scheduler = Schedulers.test();

        getImpl().exercise26(gimmeSomeMoreMoviesFlatEvery(50, MILLISECONDS, scheduler), scheduler).subscribe(ts);

        scheduler.advanceTimeBy(1, SECONDS);
        ts.assertNoErrors();
        ts.assertTerminalEvent();
        assertEquals(1, ts.getOnNextEvents().size());
        assertEquals(4.6, ts.getOnNextEvents().get(0), 0.00001);
    }

    /**
     * Welcome my friend. You now reached the other side. Time to start looking at
     * things from here. May you gain the insights to advance your journey.
     * 
     * Observables come in multiple flavors. Sometimes they are served hot, sometimes
     * cold. In the next exercises we will look at the difference. And since we're
     * here now, we will help you to tame testing these beasts.
     */
   
    /**
     * Exercise 27 - Observables are a dish best served cold
     * 
     * A cold observable will always start emitting items from the beginning,
     * even if you subscribe later.
     */
    @Test
    public void exercise27() {
        // Don't worry about these TestSchedulers and TestSubscribers yet.
        // More details will follow later.
        TestScheduler testScheduler = Schedulers.test();
        TestSubscriber<Long> immediateSubscriber = new TestSubscriber<>();
        TestSubscriber<Long> delayedSubscriber = new TestSubscriber<>();
        
        // Emits 10 items in total, one every second
        final Observable<Long> nums = Observable.interval(1, SECONDS, testScheduler).take(10);
        
        getImpl().exercise27(nums, testScheduler, immediateSubscriber, delayedSubscriber);
        
        // We will use the awesome power of the TestScheduler to advance time 
        // at our will. Enough to make sure we end the stream.
        testScheduler.advanceTimeBy(100, SECONDS);
        
        // ------------ ASSIGNMENT ----------------------------
        // Use your knowledge about cold Observables to predict the output.
        // Try to reason about the output first. You could just look
        // at the test output, but where'd be the fun in that?
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        List<Long> expectedImmediate = Arrays.asList( /* Insert your expectations here */ );
        List<Long> expectedDelayed = Arrays.asList( /* Insert your expectations here */ );

        
        fail(); // Remove this line when you finished your implementation
        immediateSubscriber.assertReceivedOnNext(expectedImmediate);
        delayedSubscriber.assertReceivedOnNext(expectedDelayed);
        
        // Congratulations, assuming you reached this point, you may now move on.
    }
    
    /**
     * Exercise 28 - Be careful, hot
     * 
     * A hot observable resembles an ordinary Observable, except that it does 
     * not begin emitting items when it is subscribed to, but only when its 
     * connect() method is called. In this way you can wait for all intended 
     * Subscribers to subscribe to the Observable before the Observable begins 
     * emitting items.
     */
    @Test
    public void exercise28() {
        // Still no worries, you will be enlightened soon. We promise.
        TestScheduler testScheduler = Schedulers.test();
        TestSubscriber<Long> immediateSubscriber = new TestSubscriber<>();
        TestSubscriber<Long> delayedSubscriber = new TestSubscriber<>();
        
        // Note the use of publish to create a 'hot' observable (in RxJava known as a ConnectableObservable)
        ConnectableObservable<Long> nums = Observable.interval(1, SECONDS, testScheduler).take(10).publish();
        
        // We need to 'connect' to the Observable to trigger it to start submitting items
        nums.connect();
        
        // We reuse the implementation of exercise 27. Can you see why?
        getImpl().exercise27(nums, testScheduler, immediateSubscriber, delayedSubscriber);
        
        // Advance time enough to make sure we end the stream
        testScheduler.advanceTimeBy(100, SECONDS); 
        
        // ------------ ASSIGNMENT ----------------------------
        // Again, try not to take a peek at the answer. We trust you.
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        List<Long> expectedImmediate = Arrays.asList( /* Insert your expectations here */ );
        List<Long> expectedDelayed = Arrays.asList( /* Insert your expectations here */ );

        fail(); // Remove this line when you finished your implementation
        immediateSubscriber.assertReceivedOnNext(expectedImmediate);
        delayedSubscriber.assertReceivedOnNext(expectedDelayed);
        
        // For extra credit:
        // Now you've learned the difference between hot and cold Observables, can you
        // tell of which type the examples are? Insert your answers ('hot' or 'cold') at
        // the ?.
        
        // Mouseclicks:                    ?
        // Grades to calculate an average: ?
        // Stock information:              ?
        // Video frames:                   ?
        // Can you think of more examples?
        
        // You can read more about ConnectableObservable here:
        // https://github.com/ReactiveX/RxJava/wiki/Connectable-Observable-Operators
    }
    
    /**
     * Exercise 29 - Your first test
     * 
     * We promised you enlightenment about testing. We will now complete that promise.
     * 
     * We start easy. Verify the output of the unverifiedObservable. You can use
     * the other tests in this suite as inspiration.
     */
    @Test
    public void exercise29() {
        Observable<Integer> unverifiedObservable = Observable.from(Arrays.asList(1,2,3,4,5,6,7,8,9,10))
            .filter(x -> x % 2 == 0)
            .map(x -> x * 2 + 1);

        // ------------ ASSIGNMENT ----------------------------
        // First we will need a subscriber, but not any will suffice. We
        // are looking for one with test capabilities
        // TODO add implementation

        // Now subscribe that special subscriber to the the unverifiedObservable
        // TODO add implementation

        // Finally use the subscriber to assert what you hold to be the truth
        // about the unverifiedObservable
        // TODO add implementation
        // ------------ ASSIGNMENT ----------------------------

        fail(); // You may remove this line when done
    }
    
    /**
     * Exercise 30 - Testing with time
     * 
     * To make it interesting we will add a touch of time now. We do want our
     * unit test to keep running fast though, so your main assignment here is 
     * to find a way to control time. Use your powers wisely though.
     */
    @Test
    public void exercise30() {
        // Below waits an observable that emits an item every day. Yawn.
        // You will
        // ------------ ASSIGNMENT ----------------------------
        // Change the initialization and pick the right scheduler. You
        // should be able to find it in this file.
        Scheduler scheduler = null; // TODO add implementation

        // Emits an item every day. Now you know why we'd like to control time.
        Observable<Long> sluggishObservable = Observable.interval(1, DAYS, scheduler).take(10);
        
        // Again we will need a subscriber
        // TODO add implementation

        // SPOILER ALERT!!! Do not read ahead until you implemented the question above!
        //
        // Of course we didn't need a cast if we used the specialized type
        // above. But we wouldn't want it to be too easy, would we?
        // Shame on you if ignored the spoiler alert and read until here. This
        // is your last chance to play fair.
        TestScheduler testScheduler = (TestScheduler) scheduler;
        
        // Use the scheduler to advance time
        // TODO add implementation

        // Assert to see if your time travel worked
        // TODO add implementation

        // ------------ ASSIGNMENT ----------------------------
        
        fail(); // You may remove this line when done
        
        // Congratulations if this test finished the same day instead of 10 days later.
        // Although, seriously, who would wait that long?
    }
    
    /**
     * Exercise 31 - And... it's gone
     * 
     * An exercise of mind this one. Run the test and try to explain... 
     */
    @Test
    public void exercise31() {
        Observable<Long> nums = Observable.interval(1, MICROSECONDS).take(1000);
        List<Long> result = new ArrayList<>();
        
        nums.subscribe(x -> { 
            // Uncomment the following line to receive more information
            // System.out.println("Adding to result: " + x); 
            result.add(x); 
        });
        
        // Why does this test fail?
        // If you think you have the answer, comment the assertion below and continue
        assertTrue("Items received: " + result.size(), result.size() == 1000);
        
        // Uncomment this line to continue
        //assertTrue(true);
    }
    
    /**
     * Exercise 32 - Tying the threads together
     * 
     * You might have guessed what's going on in the previous exercise. When using 
     * interval the callback for onNext passed as an argument to the subscribe() 
     * method is called on a different thread. Assuming you do not work on laptop
     * with heavily outdated hardware, before the Observable is able to blast 
     * through all the items and call the callback for each item, the main Thread
     * will already have moved on and executed the assertion.
     * 
     * Tip: Name your Threads. You see this happen often in a non-blocking
     * environment. Naming your Threads and including the name in the log messages
     * can help greatly when debugging bugs.
     * 
     * Now let's find out how to fix this.
     * 
     */
    @Test
    public void exercise32() {
        Observable<Long> nums = Observable.interval(
                1 
                , MICROSECONDS
                // ------------ ASSIGNMENT ----------------------------        
                // You can use the subscribeOn() method to execute the callback
                // on a specific thread. Use the Schedulers class to pass the 
                // right thread. (RxJava has some built in Threadpools called
                // Schedulers) 
                // ------------ ASSIGNMENT ----------------------------
                // TODO add implementation
        ).take(1000);
        
        List<Long> result = new ArrayList<>();
        
        nums
            .subscribe(x -> { 
            // Uncomment the following line to receive more information
            //System.out.println("Thread " + Thread.currentThread().getName() + ". Adding to result: " + x); 
            result.add(x); 
        });
        
        assertTrue("Items received: " + result.size(), result.size() == 1000);
    }

    /**
     * Exercise 33 - RTFCode
     * 
     * Executing on different threads forms the heart of an asynchronous or non-
     * blocking system. Working with threads is usually hard and error prone. 
     * RxJava tries to simplify things by introducing the concept of Schedulers.
     * In short, you could view a Scheduler as Threadpool.
     * 
     * Now take another look at the Schedulers class. Can you think of the 
     * purpose of each Scheduler? And what would that purpose mean for the
     * characteristics of that Scheduler? (Think of threadpool properties 
     * such as number of threads, etc.)
     * 
     * In RxJava you can also choose to subscribe and observe on different 
     * threads using Schedulers. 
     * 
     * Execute learnrxjava.examples.SubscribeOnObserveOnExample and play around 
     * with subscribeOn and observeOn until you get the concepts.
     */
    @Test
    public void exercise33() {
        // Change to true when done
        assertTrue(false);
    }
    
    /**   
     * This ends our intermezzo here. Time for you to return to the safety of 
     * already implemented tests and just focusing on the implementation. You
     * may return to ObservableExercises and pick up from where you left off.
     */

    @Test
    public void exercise34() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        getImpl().exercise34(Utils.faultyObservable(10)).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Arrays.asList(0,1,2,3,4,5,6,7,8,9,-1));
    }

    @Test
    public void exercise35() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        getImpl().exercise35(Utils.faultyObservable(5), Observable.range(5, 5)).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
    }

    @Test
    public void exercise36() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise36(Utils.intermittentlyFailing(3)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Success!"));
    }

    @Test
    public void exercise37() {
        TestSubscriber<GroupedObservable<String, Double>> ts = new TestSubscriber<>();
        getImpl().exercise37(gimmeSomeMoreMovies()).subscribe(ts);
        ts.assertNoErrors();
        List<GroupedObservable<String, Double>> ratingsPerActor = ts.getOnNextEvents();
        assertThat(ratingsPerActor.size(), is(7));        
        
        Map<String, List<Double>> expectedRatingsPerActor = new HashMap<>();
        expectedRatingsPerActor.put("Bruce Willis", Arrays.asList(4.0));
        expectedRatingsPerActor.put("Will Smith", Arrays.asList(5.0, 4.0));
        expectedRatingsPerActor.put("Tommy Lee Jones", Arrays.asList(5.0));
        expectedRatingsPerActor.put("Matt Damon", Arrays.asList(5.0));
        expectedRatingsPerActor.put("Anne Hathaway", Arrays.asList(5.0));
        expectedRatingsPerActor.put("Matthew McConaughey", Arrays.asList(5.0));
        expectedRatingsPerActor.put("Martin Lawrence", Arrays.asList(4.0));
        
        ratingsPerActor.stream().forEach(actorRatings -> {
            TestSubscriber<Double> ts1 = new TestSubscriber<>();
            actorRatings.subscribe(ts1);
            ts1.assertReceivedOnNext(expectedRatingsPerActor.get(actorRatings.getKey()));
        });
    }

    @Test
    public void exercise38() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exercise38(just(3, 6, 8, 9, 4, 12, 4, 2)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(12));
    }

    @Test
    public void exercise39() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exercise39(just(3, 6, 8, 9, 4, 12, 4, 2)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(3, 6, 8, 9, 9, 12, 12, 12));
    }

    @Test
    public void exercise40() {
        TestSubscriber<Map<Integer, JSON>> ts = new TestSubscriber<>();

        Observable<Movies> movies = just(
                new Movies(
                        "New Releases",
                        Arrays.asList(
                                new Movie(
                                        70111470,
                                        "Die Hard",
                                        4.0,
                                        Collections.emptyList(),
                                        Arrays.asList(
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"),
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/DieHard200.jpg")
                                        )),
                                new Movie(
                                        654356453,
                                        "Bad Boys",
                                        5.0,
                                        Collections.emptyList(),
                                        Arrays.asList(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                                                new BoxArt(140, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg")
                                        ))
                        )
                ),
                new Movies(
                        "Thrillers",
                        Arrays.asList(
                                new Movie(
                                        65432445,
                                        "The Chamber",
                                        3.0,
                                        Collections.emptyList(),
                                        Arrays.asList(
                                                new BoxArt(130, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"),
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber200.jpg")
                                        )),
                                new Movie(
                                        675465,
                                        "Fracture",
                                        4.0,
                                        Collections.emptyList(),
                                        Arrays.asList(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                                                new BoxArt(120, 200, "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"),
                                                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
                                        ))
                        )
                )
        );

        Map<Integer, JSON> map = getImpl().exercise40(movies).toMap(i -> (int) i.get("id")).toBlocking().single();
        assertTrue(map.containsKey(70111470));
        Assert.assertEquals(map.get(70111470).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/DieHard150.jpg, id=70111470, title=Die Hard}");
        assertTrue(map.containsKey(654356453));
        Assert.assertEquals(map.get(654356453).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg, id=654356453, title=Bad Boys}");
        assertTrue(map.containsKey(65432445));
        Assert.assertEquals(map.get(65432445).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg, id=65432445, title=The Chamber}");
        assertTrue(map.containsKey(675465));
        Assert.assertEquals(map.get(675465).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/Fracture120.jpg, id=675465, title=Fracture}");
    }

    @Test
    public void exercise41() {
        TestSubscriber<List<String>> ts = new TestSubscriber<>();
        getImpl().exercise41(from(asList("Remko", "Hedzer", "Dirk", "Teije", "Gerlo", "Robbert"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        List<String> sortedNames = Arrays.asList("Dirk", "Gerlo", "Hedzer", "Remko", "Robbert", "Teije");
        ts.assertReceivedOnNext(Arrays.asList(sortedNames));
    }

    @Test
    public void exercise42() {
        TestSubscriber<List<String>> ts = new TestSubscriber<>();
        getImpl().exercise42(from(asList("Remko", "Hedzer", "Dirk", "Teije", "Gerlo", "Robbert"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        List<String> sortedNames = Arrays.asList("Dirk", "Remko", "Teije", "Gerlo", "Hedzer", "Robbert");
        ts.assertReceivedOnNext(Arrays.asList(sortedNames));
    }

    @Test
    public void exercise43() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise43(from(asList("aap", "noot", "mies", "Remko", "Robbert", "Hedzer", "Dirk", "Teije", "Hedzer", "Gerlo", "Robbert"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        List<String> sortedNames = Arrays.asList("Remko", "Robbert", "Hedzer", "Dirk", "Teije", "Gerlo");
        ts.assertReceivedOnNext(sortedNames);
    }

    @Test
    public void exercise44() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exercise44(range(1, 10)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
    }

    @Test
    public void exercise45() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise45("From Dusk till Dawn").subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertReceivedOnNext(Arrays.asList("From Dusk till Dawn"));
        assertTrue(ts.getOnErrorEvents().get(0) instanceof IllegalStateException);
    }

    @Test
    public void exercise46() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        TestSubscriber<String> ts2 = new TestSubscriber<>();
        TestSubscriber<String> ts3 = new TestSubscriber<>();
        TestScheduler scheduler = new TestScheduler();

        Subject replaySubject = getImpl().exercise46("From Dusk till Dawn", scheduler);
        replaySubject.subscribe(ts);
        // verify immediate replay
        ts.assertReceivedOnNext(Arrays.asList("From Dusk till Dawn"));

        scheduler.advanceTimeBy(1, HOURS);
        replaySubject.onNext("foo");
        // verify the second item
        ts.assertReceivedOnNext(Arrays.asList("From Dusk till Dawn", "foo"));
        replaySubject.subscribe(ts3);
        // verify immediate replay of both items
        ts3.assertReceivedOnNext(Arrays.asList("From Dusk till Dawn", "foo"));

        scheduler.advanceTimeBy(4, HOURS);
        replaySubject.onNext("bar");
        // verify immediate replay of all 3 items
        ts.assertReceivedOnNext(Arrays.asList("From Dusk till Dawn", "foo", "bar"));
        replaySubject.subscribe(ts2);
        // verify replay of last 2 items - the first has expired after advancing time by 1+4=5 hours
        ts2.assertReceivedOnNext(Arrays.asList("foo", "bar"));

        replaySubject.onCompleted();
        ts.awaitTerminalEvent();
        ts2.awaitTerminalEvent();
        ts3.awaitTerminalEvent();
    }

    /*
     * **************
     * below are helper methods
     * **************
     */
    private void sleep(int i) {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            // ignore
        }
    }

    private Observable<Movies> gimmeSomeMoviesEvery(long value, TimeUnit timeUnit, Scheduler scheduler) {
        Observable<Long> interval = Observable.interval(value, timeUnit, scheduler);
        return Observable.zip(gimmeSomeMovies(), interval, (movie, t) -> movie);
    }

    private Observable<Movie> gimmeSomeMoreMoviesFlatEvery(long value, TimeUnit timeUnit, Scheduler scheduler) {
        Observable<Long> interval = Observable.interval(value, timeUnit, scheduler);
        final Observable<Movie> movies = gimmeSomeMoreMovies().flatMap(_movies -> _movies.videos);
        return Observable.zip(movies, interval, (movie, t) -> movie);
    }

    private Observable<Movies> gimmeSomeMovies() {
        return just(
                new Movies(
                        "New Releases",
                        Arrays.asList(
                                new Movie(70111470, "Die Hard", 4.0, 18),
                                new Movie(654356453, "Bad Boys", 5.0, 12))),
                new Movies(
                        "Dramas",
                        Arrays.asList(
                                new Movie(65432445, "The Chamber", 4.0, 12),
                                new Movie(675465, "Fracture", 5.0, 8)))
        );
    }

    private Observable<Movies> gimmeSomeMoreMovies() {
        return just(
                new Movies(
                        "New Releases",
                        Arrays.asList(
                                new Movie(70111470, "Die Hard", 4.0,
                                        Arrays.asList(
                                                new Bookmark(470, 23432),
                                                new Bookmark(453, 234324),
                                                new Bookmark(445, 987834)
                                        ),
                                        Arrays.asList(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/DieHard200.jpg"),
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"),
                                                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/DieHard300.jpg")
                                        ),
                                        Arrays.asList(
                                                new InterestingMoment("End", 45632456),
                                                new InterestingMoment("Start", 234534),
                                                new InterestingMoment("Middle", 3453434)
                                        ),
                                        Arrays.asList(
                                                "Bruce Willis"
                                        )
                                ),
                                new Movie(70111472, "MIB2", 5.0,
                                        Arrays.asList(
                                                new Bookmark(470, 23432),
                                                new Bookmark(453, 234324),
                                                new Bookmark(445, 987834)
                                        ),
                                        Arrays.asList(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/MIB2200.jpg"),
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/MIB2150.jpg"),
                                                new BoxArt(400, 200, "http://cdn-0.nflximg.com/images/2891/MIB2300.jpg")
                                        ),
                                        Arrays.asList(
                                                new InterestingMoment("End", 753492),
                                                new InterestingMoment("Start", 153263),
                                                new InterestingMoment("Middle", 3453434)
                                        ),
                                        Arrays.asList(
                                                "Will Smith",
                                                "Tommy Lee Jones"
                                        )
                                ),
                                new Movie(70111473, "Interstellar", 5.0,
                                        Arrays.asList(
                                                new Bookmark(470, 23432),
                                                new Bookmark(453, 234324),
                                                new Bookmark(445, 987834)
                                        ),
                                        Arrays.asList(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Interstellar200.jpg"),
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/Interstellar150.jpg"),
                                                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Interstellar300.jpg")
                                        ),
                                        Arrays.asList(
                                                new InterestingMoment("End", 423732456),
                                                new InterestingMoment("Start", 1254223),
                                                new InterestingMoment("Middle", 3146434)
                                        ),
                                        Arrays.asList(
                                                "Matt Damon",
                                                "Anne Hathaway",
                                                "Matthew McConaughey"
                                        )
                                ),
                                new Movie(654356453, "Bad Boys", 4.0,
                                        Arrays.asList(
                                                new Bookmark(470, 23432),
                                                new Bookmark(453, 234324),
                                                new Bookmark(445, 987834)
                                        ),
                                        Arrays.asList(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys150.jpg"),
                                                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys300.jpg")
                                        ),
                                        Arrays.asList(
                                                new InterestingMoment("End", 45632456),
                                                new InterestingMoment("Start", 234534),
                                                new InterestingMoment("epic", 234900),
                                                new InterestingMoment("Middle", 3453434)
                                        ),
                                        Arrays.asList(
                                                "Will Smith",
                                                "Martin Lawrence"
                                        )
                                        
                                ))),
                new Movies(
                        "Dramas",
                        Arrays.asList(
                                new Movie(65432445, "The Chamber", 4.0),
                                new Movie(675465, "Fracture", 5.0),
                                new Movie(675466, "Gladiator", 4.5),
                                new Movie(675467, "Valhalla Rising", 6.0),
                                new Movie(675468, "The Experiment", 3.0),
                                new Movie(675469, "All quiet on the Western front", 4.3),
                                new Movie(675470, "Hitman", 4.3),
                                new Movie(675471, "Fury", 4.8),
                                new Movie(6754, "My Little Pony", 4.7)))
        );
    }
}

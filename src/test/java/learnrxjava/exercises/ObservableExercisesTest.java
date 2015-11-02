package learnrxjava.exercises;

import java.util.ArrayList;
import learnrxjava.types.InterestingMoment;
import learnrxjava.types.Movie;
import learnrxjava.types.Movies;
import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.observables.BlockingObservable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.List;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.SECONDS;
import learnrxjava.types.Bookmark;
import learnrxjava.types.BoxArt;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static rx.Observable.*;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.observables.GroupedObservable;

public class ObservableExercisesTest {

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
        ts.assertReceivedOnNext(Arrays.asList("My Little Pony"));
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
    
    // TODO performance
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

    // TODO performance
    @Test
    public void exercise23() {
        final int NUMBER_OF_BURSTY_ITEMS = 10000;

        Observable<Integer> burstyParallelNumbers =
            Observable
                .range(0, NUMBER_OF_BURSTY_ITEMS)
                .subscribeOn(Schedulers.computation())
                .doOnNext(i -> /* simulate computational work */ sleep(1));

        AtomicInteger totalBuffers = new AtomicInteger();
        AtomicInteger totalNumbers = new AtomicInteger();
        AtomicInteger minBufferSize = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger maxBufferSize = new AtomicInteger(Integer.MIN_VALUE);
        getImpl().exercise23(burstyParallelNumbers).doOnNext(numbersBuffer -> {
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
        }).toBlocking().last();

        assertEquals(NUMBER_OF_BURSTY_ITEMS, totalNumbers.get());
        // 3000ms is the approx. runtime on a 4-core CPU for 10000 items (so test might fail on 8-core or higher)
        assertTrue(totalBuffers.get() >= 3000 / 500 || totalBuffers.get() == 1); // sometimes, some delay seems to cause all 10000 items to be buffered in one buffer?!

        assertNotEquals(minBufferSize.get(), 500);
        assertNotEquals(maxBufferSize.get(), 500);
    }

    // TODO performance
    @Test
    public void exercise24() {
        TestSubscriber<Observable<Integer>> ts = new TestSubscriber<>();
        Observable<Integer> objectObservable = Observable.range(0, 3000).doOnNext(integer -> sleep(1));

        getImpl().exercise24(objectObservable).subscribe(ts);

        ts.assertNoErrors();
        List<Observable<Integer>> onNextEvents = ts.getOnNextEvents();
        assertEquals(Integer.valueOf(15), onNextEvents.get(0).skip(10).take(1).toBlocking().first());
        assertTrue(onNextEvents.size() > 3);
    }

    // TODO exercise25
    
    @Test
    public void exercise26() {
        TestSubscriber<Double> testSubscriber = new TestSubscriber<>();
        getImpl().exercise26(gimmeSomeMoreMovies()).subscribe(testSubscriber);
        
        //TODO finish test
        fail();
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
        
        // ------------ INSERT CODE HERE! ----------------------------
        // Use your knowledge about cold Observables to predict the output.
        // Try to reason about the output first. You could just look
        // at the test output, but where'd be the fun in that?
        List<Long> expectedImmediate = Arrays.asList( /* Insert your expectations here */ );
        List<Long> expectedDelayed = Arrays.asList( /* Insert your expectations here */ );
        // ------------ INSERT CODE HERE! ----------------------------
        
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
        
        // ------------ INSERT CODE HERE! ----------------------------
        // Again, try not to take a peek at the answer. We trust you.
        List<Long> expectedImmediate = Arrays.asList( /* Insert your expectations here */ );
        List<Long> expectedDelayed = Arrays.asList( /* Insert your expectations here */ );
        // ------------ INSERT CODE HERE! ----------------------------
        
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

        // ------------ INSERT CODE HERE! ----------------------------
        // First we will need a subscriber, but not any will suffice. We
        // are looking for one with test capabilities
        
        // Now subscribe that special subscriber to the the unverifiedObservable
        
        // Finally use the subscriber to assert what you hold to be the truth
        // about the unverifiedObservable
        
        // ------------ INSERT CODE HERE! ----------------------------

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
        // ------------ INSERT CODE HERE! ----------------------------
        // Change the initialization and pick the right scheduler. You
        // should be able to find it in this file.
        Scheduler scheduler = null; 
        // ------------ INSERT CODE HERE! ----------------------------
        
        // Emits an item every day. Now you know why we'd like to control time.
        Observable<Long> sluggishObservable = Observable.interval(1, TimeUnit.DAYS, scheduler).take(10);
        
        // ------------ INSERT CODE HERE! ----------------------------
        // Again we will need a subscriber
        
        // SPOILER ALERT!!! Do not read ahead until you implemented the question above!
        //
        // Of course we didn't need a cast if we used the specialized type
        // above. But we wouldn't want it to be too easy, would we?
        // Shame on you if ignored the spoiler alert and read until here. This
        // is your last chance to play fair.
        TestScheduler testScheduler = (TestScheduler) scheduler;
        
        // ------------ INSERT CODE HERE! ----------------------------
        // Use the scheduler to advance time
        
        // Assert to see if your time travel worked
        
        // ------------ INSERT CODE HERE! ----------------------------
        
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
        Observable<Long> nums = Observable.interval(1, TimeUnit.MICROSECONDS).take(1000);
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
                , TimeUnit.MICROSECONDS
                // ------------ INSERT CODE HERE! ----------------------------        
                // You can use the subscribeOn() method to execute the callback
                // on a specific thread. Use the Schedulers class to pass the 
                // right thread. (RxJava has some built in Threadpools called
                // Schedulers) 
                // ------------ INSERT CODE HERE! ----------------------------        
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
        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
        Observable<Long> faultyObservable = Observable.create(subscriber -> {
            for (long i = 0; i < 10; i++) {
                subscriber.onNext(i);
            }
            subscriber.onError(new RuntimeException("Failing like a boss"));
        });
        getImpl().exercise34(faultyObservable).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Arrays.asList(0L,1L,2L,3L,4L,5L,6L,7L,8L,9L,-1L));
    }

    // TODO renumber exercise40
    @Test
    public void exercise40() {
        TestSubscriber<GroupedObservable<String, Double>> ts = new TestSubscriber<>();
        getImpl().exercise40(gimmeSomeMoreMovies()).subscribe(ts);
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
                                new Movie(6754, "My Little Pony", 5.0)))
        );
    }
}

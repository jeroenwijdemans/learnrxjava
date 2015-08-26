package learnrxjava.exercises;

import java.util.ArrayList;
import learnrxjava.types.BoxArt;
import learnrxjava.types.JSON;
import learnrxjava.types.Movie;
import learnrxjava.types.Movies;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static rx.Observable.*;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

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
        assertEquals(ts.getOnNextEvents(), asList("Hello Remko!", "Hello Hedzer!"));
    }
    
    @Test
    public void exercise01() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exercise01(range(1, 10)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        assertEquals(5, ts.getOnNextEvents().size());
        assertEquals(ts.getOnNextEvents(), asList(2,4,6,8,10));
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
        getImpl().exercise11().subscribe(ts);
        // note that we don't assert on timing. Should be possible using TestScheduler though.
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("one 1", "two 2", "three 3", "four 4", "five 5"));
    }

    @Test
    public void exercise12() {
        TestSubscriber<Map<Integer, Integer>> ts = new TestSubscriber<>();
        // as we can't rely on the ordering this time, we use different assertions for exercise03
        Map<Integer, Integer> map = getImpl().exercise12(gimmeSomeMovies()).toMap(i -> i).toBlocking().single();
        assertTrue(map.containsKey(70111470));
        assertTrue(map.containsKey(654356453));
        assertTrue(map.containsKey(65432445));
        assertTrue(map.containsKey(675465));
    }

    @Test
    public void exercise13() {
        assertTrue(getImpl().exercise13());
    }

    @Test
    public void exercise14() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise14(gimmeSomeMovies()).subscribe(ts);
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Die Hard"));
    }
    
    @Test
    public void exercise15() {
        TestScheduler scheduler = Schedulers.test();
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise15(gimmeSomeMoviesEvery(3, SECONDS, scheduler), scheduler).subscribe(ts);
        scheduler.advanceTimeBy(4, SECONDS);
        ts.assertReceivedOnNext(Arrays.asList("Die Hard", "Bad Boys"));
        scheduler.advanceTimeBy(8, SECONDS);
        ts.assertTerminalEvent();
    }
    
    /*
     * **************
     * below are helper methods
     * **************
     */
    private Observable<Movies> gimmeSomeMoviesEvery(long value, TimeUnit timeUnit, Scheduler scheduler) {
        Observable<Long> interval = Observable.interval(value, timeUnit, scheduler);
        return Observable.zip(gimmeSomeMovies(), interval, (movie, t) -> {
            return movie;
        });
    }
    
    private Observable<Movies> gimmeSomeMovies() {
        return just(
                new Movies(
                        "New Releases", // name
                        Arrays.asList( // videos
                                new Movie(70111470, "Die Hard", 4.0),
                                new Movie(654356453, "Bad Boys", 5.0))),
                new Movies(
                        "Dramas",
                        Arrays.asList(
                                new Movie(65432445, "The Chamber", 4.0),
                                new Movie(675465, "Fracture", 5.0)))
        );
    }
}

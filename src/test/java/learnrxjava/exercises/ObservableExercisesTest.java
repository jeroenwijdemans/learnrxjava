package learnrxjava.exercises;

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
import static java.util.concurrent.TimeUnit.SECONDS;
import learnrxjava.types.Bookmark;
import learnrxjava.types.BoxArt;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static rx.Observable.*;
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
    
    @Test
    public void exercise18() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise18().subscribe(ts);
        // note that we don't assert on timing. Should be possible using TestScheduler though.
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
        getImpl().exercise19(odd, even, scheduler).subscribe(ts);
        scheduler.advanceTimeBy(10, SECONDS);
        ts.assertReceivedOnNext(Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L));
    }
    
    @Test
    public void exercise20() {
        TestSubscriber<Map<Integer, Integer>> ts = new TestSubscriber<>();
        // as we can't rely on the ordering this time, we use different assertions for exercise03
        Map<Integer, Integer> map = getImpl().exercise20(gimmeSomeMovies()).toMap(i -> i).toBlocking().single();
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
        ts.assertReceivedOnNext(Arrays.asList("Die Hard", "Bad Boys"));
        scheduler.advanceTimeBy(4, SECONDS);
        ts.assertTerminalEvent();
    }

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

    @Test
    public void exercise24() {
        TestSubscriber<Observable<Integer>> ts2 = new TestSubscriber<>();
        Observable<Integer> objectObservable = Observable.range(0, 2500).doOnNext(integer -> sleep(1));

        getImpl().exercise24(objectObservable).subscribe(ts2);

        ts2.assertNoErrors();
        List<Observable<Integer>> onNextEvents = ts2.getOnNextEvents();
        assertEquals(Integer.valueOf(15), onNextEvents.get(0).skip(10).take(1).toBlocking().first());
        assertTrue(onNextEvents.size() > 3);
    }

    @Test
    public void exercise29() {
        TestSubscriber<GroupedObservable<String, Double>> ts = new TestSubscriber<>();
        getImpl().exercise29(gimmeSomeMoreMovies()).subscribe(ts);
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
                        "New Releases", // name
                        Arrays.asList( // videos
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

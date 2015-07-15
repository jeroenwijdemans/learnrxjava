package learnrxjava.exercises;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static rx.Observable.*;

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

        Observable<Movies> movies = just(
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

        getImpl().exercise03(movies).subscribe(ts);
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

        Observable<Movies> movies = just(
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

        // as we can't rely on the ordering this time, we use different assertions for exercise03
        Map<Integer, Integer> map = getImpl().exercise12(movies).toMap(i -> i).toBlocking().single();
        assertTrue(map.containsKey(70111470));
        assertTrue(map.containsKey(654356453));
        assertTrue(map.containsKey(65432445));
        assertTrue(map.containsKey(675465));
    }

    @Test
    public void exercise13() {
        assertTrue(getImpl().exercise13());
    }

        // TODO rename/rework from here on downwards
    @Test
    public void exerciseSortLexicographically() {
        TestSubscriber<List<String>> ts = new TestSubscriber<>();
        getImpl().exerciseSortLexicographically(from(asList("Remko", "Hedzer", "Dirk", "Teije", "Gerlo", "Robbert"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        List<String> sortedNames = Arrays.asList("Dirk", "Gerlo", "Hedzer", "Remko", "Robbert", "Teije");
        ts.assertReceivedOnNext(Arrays.asList(sortedNames));
    }

    @Test
    public void exerciseSortByLength() {
        TestSubscriber<List<String>> ts = new TestSubscriber<>();
        getImpl().exerciseSortByLength(from(asList("Remko", "Hedzer", "Dirk", "Teije", "Gerlo", "Robbert"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        List<String> sortedNames = Arrays.asList("Dirk", "Remko", "Teije", "Gerlo", "Hedzer", "Robbert");
        ts.assertReceivedOnNext(Arrays.asList(sortedNames));
    }

    @Test
    public void exerciseSkipThenDistinct() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exerciseSkipThenDistinct(from(asList("aap", "noot", "mies", "Remko", "Robbert", "Hedzer", "Dirk", "Teije", "Hedzer", "Gerlo", "Robbert"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        List<String> sortedNames = Arrays.asList("Remko", "Robbert", "Hedzer", "Dirk", "Teije", "Gerlo");
        ts.assertReceivedOnNext(sortedNames);
    }

    @Test
    public void exerciseReduce() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exerciseReduce(just(3, 6, 8, 9, 4, 12, 4, 2)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(12));
    }

    @Test
    public void exerciseMovie() {
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

        Map<Integer, JSON> map = getImpl().exerciseMovie(movies).toMap(i -> (int) i.get("id")).toBlocking().single();
        System.out.println(map);
        assertTrue(map.containsKey(70111470));
        assertEquals(map.get(70111470).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/DieHard150.jpg, id=70111470, title=Die Hard}");
        assertTrue(map.containsKey(654356453));
        assertEquals(map.get(654356453).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg, id=654356453, title=Bad Boys}");
        assertTrue(map.containsKey(65432445));
        assertEquals(map.get(65432445).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg, id=65432445, title=The Chamber}");
        assertTrue(map.containsKey(675465));
        assertEquals(map.get(675465).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/Fracture120.jpg, id=675465, title=Fracture}");
    }

    @Test
    public void handleError() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().handleError(error(new RuntimeException("failure"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("default-value"));
    }

    @Test
    public void retry() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        AtomicInteger c = new AtomicInteger();
        Observable<String> o = Observable.create(s -> {
            if (c.incrementAndGet() <= 1) {
                s.onError(new RuntimeException("fail"));
            } else {
                s.onNext("success!");
                s.onCompleted();
            }
        });
        getImpl().retry(o).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("success!"));
    }

    /**
     * The data stream fails intermittently so return the stream
     * with retry capability.
     */
    public Observable<String> retry(Observable<String> data) {
        return data.retry();
    }

}

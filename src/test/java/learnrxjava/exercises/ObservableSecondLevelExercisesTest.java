package learnrxjava.exercises;

import learnrxjava.types.BoxArt;
import learnrxjava.types.JSON;
import learnrxjava.types.Movie;
import learnrxjava.types.Movies;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static rx.Observable.*;

public class ObservableSecondLevelExercisesTest {

    public ObservableSecondLevelExercises getImpl() {
        return new ObservableSecondLevelExercises();
    }

    @Test
    public void exercise01() {
        TestSubscriber<List<String>> ts = new TestSubscriber<>();
        getImpl().exercise01(from(asList("Remko", "Hedzer", "Dirk", "Teije", "Gerlo", "Robbert"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        List<String> sortedNames = Arrays.asList("Dirk", "Gerlo", "Hedzer", "Remko", "Robbert", "Teije");
        ts.assertReceivedOnNext(Arrays.asList(sortedNames));
    }

    @Test
    public void exercise02() {
        TestSubscriber<List<String>> ts = new TestSubscriber<>();
        getImpl().exercise02(from(asList("Remko", "Hedzer", "Dirk", "Teije", "Gerlo", "Robbert"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        List<String> sortedNames = Arrays.asList("Dirk", "Remko", "Teije", "Gerlo", "Hedzer", "Robbert");
        ts.assertReceivedOnNext(Arrays.asList(sortedNames));
    }

    @Test
    public void exercise03() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise03(from(asList("aap", "noot", "mies", "Remko", "Robbert", "Hedzer", "Dirk", "Teije", "Hedzer", "Gerlo", "Robbert"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        List<String> sortedNames = Arrays.asList("Remko", "Robbert", "Hedzer", "Dirk", "Teije", "Gerlo");
        ts.assertReceivedOnNext(sortedNames);
    }

    @Test
    public void exercise04_not_completed_leads_to_timeout() {
        TestScheduler testScheduler = Schedulers.test();

        CompletableFuture<Integer> videoId = new CompletableFuture<>();

        Observable<Integer> videoIdStream = getImpl().exercise04(videoId);

        TestSubscriber<Integer> ts = new TestSubscriber<>();
        videoIdStream.observeOn(testScheduler).subscribe(ts);

        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS);
        assertEquals(0, ts.getOnNextEvents().size());

        ts.awaitTerminalEvent();
        assertTrue("did not receive the expected Timeout", ts.getOnErrorEvents().get(0) instanceof TimeoutException);
    }

    @Test
    public void exercise04_completed() {
        TestScheduler testScheduler = Schedulers.test();

        CompletableFuture<Integer> videoId = new CompletableFuture<>();

        Observable<Integer> videoIdStream = getImpl().exercise04(videoId);

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // catch me if you can-can!
            }
            videoId.complete(42);
        }).start();

        TestSubscriber<Integer> ts = new TestSubscriber<>();
        videoIdStream.observeOn(testScheduler).subscribe(ts);

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);

        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(42));
    }

    @Test
    public void exercise05() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exercise05(just(3, 6, 8, 9, 4, 12, 4, 2)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(12));
    }

    @Test
    public void exercise06() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exercise06(just(3, 6, 8, 9, 4, 12, 4, 2)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(3, 6, 8, 9, 9, 12, 12, 12));
    }

    @Test
    public void exercise07() {
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

        Map<Integer, JSON> map = getImpl().exercise07(movies).toMap(i -> (int) i.get("id")).toBlocking().single();
        System.out.println(map);
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
    public void exercise08() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise08(error(new RuntimeException("failure"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("fallback-data"));
    }

    @Test
    public void exercise10() {
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
        getImpl().exercise10(o).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("success!"));
    }

    @Test
    public void exercise11() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exercise11(range(1, 10)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
    }

    @Test
    public void exercise12() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exercise12("From Dusk till Dawn").subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertReceivedOnNext(Arrays.asList("From Dusk till Dawn"));
        assertTrue(ts.getOnErrorEvents().get(0) instanceof IllegalStateException);
    }

    @Test
    public void exercise13() {
        getImpl().exercise13();
    }
}
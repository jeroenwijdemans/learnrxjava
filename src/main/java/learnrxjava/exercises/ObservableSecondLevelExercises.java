package learnrxjava.exercises;

import learnrxjava.types.JSON;
import learnrxjava.types.Movies;
import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * My Padawan, advanced to the second level you have?
 *
 * So far, you have mastered the following reactive operators:
 * Creational:
 * - create
 * - just
 * Operators:
 * - map
 * - filter
 * - concatMap, flatMap
 * - zip
 * - interval
 * Subscription:
 * - subscribe
 *
 * This level you will be introduced to, amongst others, the following operators and creators:
 * - from
 * - reduce / scan
 * - materialize / dematerialize
 * - error
 * - retry
 * - distinct
 * - toSortedList
 * - range
 */
public class ObservableSecondLevelExercises {

    /**
     * Exercise 01 - Sort lexicographically
     *
     * Sorting is a simple operation. Keep in mind that sorting requires buffering _all_ items - the last one may very well
     * be the smallest. This is reflected by the fact that a _List_ of the input type is returned.
     *
     * The sort buffer may require quite some memory when streaming a large number of items, so be careful!
     */
    public Observable<List<String>> exercise01(Observable<String> data) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Use Observable's 'toSortedList' operator
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 02 - sort by length
     *
     * Sorting is by default done by using the Comparable implementation of the items themselves.
     *
     * In this exercise, you will build an external sorter. Should be simple enough: supply the comparison as a lambda expression.
     */
    public Observable<List<String>> exercise02(Observable<String> data) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Use an overload of Observable's 'toSortedList' operator, supplying a sortFunction
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 03 - skip, then distinct
     *
     * One way of protecting against overload - e.g. if the stream needs to be sorted - is to filter out items from the stream.
     *
     * Of course, you'll have to decide, based on your specific domain, in which cases 'forgetting' items from the stream is desirable.
     *
     * Filtering can be done in any number of ways. Examples are:
     * - sample(time): return one item per time interval
     * - distinct: remove duplicates
     * - first: take _only_ the first item
     * - takeWhile(condition): take while 'condition' is true
     * - skip(n), skip(time): skip n items, or skip until the time interval has passed
     * - take(n), take(time), takeLast(n), takeLast(time): take the first or last n items
     * - take(time), takeLast(time): take the first or last items in the specified time interval
     *
     * ... and many others, like skip(time), last, first(condition), firstOrDefault(default), lastOrDefault(default), elementAt(x), elementAtOrDefault(x, default),
     *   skipLast(n), distinctUntilChanged, etc.
     *
     * But, I'm getting ahead of myself. That's a pretty hefty list of operators, which we can't possibly all cover in this workshop.
     *
     * You've only already seen filter. This exercises will introduce you to two other 'pruning' operators: skip(n) and distinct().
     */
    public Observable<String> exercise03(Observable<String> movieIds) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Skip the first 3 movies. Of the remaining movies, return only the movies that are different from what has already been emitted
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 04 - from
     *
     * What if you have an existing API based on Future<T>? No problem,
     * you can upconvert it to an Observable using the from() operator that you've seen already.
     * <p/>
     * No problem? Not entirely. Once you subscribe to this Observable, Future.get() is immediately invoked.
     * That is a blocking operation, so you'd better add a timeout!
     * The rxjava javadoc describes this somewhat cryptic: "This Observable is blocking; you cannot unsubscribe from it."
     * <p/>
     * Not being able to unsubscribe sounds harmless, but what this means is that you'd better have the Future completing
     * *in another thread*, otherwise your Observable stream might never receive *any* events.
     * - Or - add a timeout, so you'll get an onError() event.
     * <p/>
     * In this exercise, you'll do the latter: create on Observable from a Future using a timeout.
     * <p/>
     * The tests for this exercise will verify both the completed and the timeout situations.
     *
     * @param videoId a future videoId
     * @return Observable of videoIds
     */
    public Observable<Integer> exercise04(Future<Integer> videoId) {
        // ------------ INSERT CODE HERE! ----------------------------
        // use from() with a timeout of 2 TimeUnit.SECONDS
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 05 - reduce
     *
     * Retrieve the largest number.
     * <p/>
     * Use reduce to select the maximum value in a list of numbers.
     * <p/>
     * Reduction works iteratively, processing each item one by one, applying it to the result
     * of the previous step. The process is started by combining the first two emitted items.
     * <p/>
     * The combiner function is named 'accumulator'.
     * <p/>
     * Only the _last_ result of invoking the accumulated is emitted, so reduce() emits (at most) one item.
     */
    public Observable<Integer> exercise05(Observable<Integer> nums) {
        // ------------ INSERT CODE HERE! ----------------------------
        // use reduce()
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 06 - scan
     *
     * scan works just like reduce, with the distinction that it emits all intermediate results.
     * The number of emitted results equals the number emitted by the Observable being scanned.
     * TODO toon het verschil, bijv.
     *         Observable.range(0, 10).reduce(new ArrayList<>(), (list, i) -> {
     list.add(i);
     return list;
     }).forEach(System.out::println);

     System.out.println("... vs ...");

     Observable.range(0, 10).scan(new ArrayList<>(), (list, i) -> {
     list.add(i);
     return list;
     }).forEach(System.out::println);
     */
    public Observable<Integer> exercise06(Observable<Integer> nums) {
        // ------------ INSERT CODE HERE! ----------------------------
        // use scan()
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 07 - reduce, composed
     *
     * Retrieve the id, title, and <b>smallest</b> box art url for every video.
     * <p/>
     * Now let's try combining reduce() with our other functions to build more complex queries.
     * <p/>
     * This is a variation of the problem we solved earlier, where we retrieved the url of the boxart with a
     * width of 150px. This time we'll use reduce() instead of filter() to retrieve the _smallest_ box art in
     * the boxarts list.
     * <p/>
     * @see ComposableListExercises#exercise19()
     * and  ComposableListSolutions#exercise19()
     */
    public Observable<JSON> exercise07(Observable<Movies> movies) {
        // ------------ INSERT CODE HERE! ----------------------------
        // use reduce() - see ComposableListSolutions#exercise19() for more hints
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 08 - onErrorResumeNext
     * TODO + 09: onErrorReturn
     *
     * This will re-emit all values in the stream as-is, handling errors
     * silently by replacing them with "fallback-data".
     */
    public Observable<String> exercise08(Observable<String> data) {
        // ------------ INSERT CODE HERE! ----------------------------
        // resume from an error using Observable.just("fallback-data")
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 10 - retry
     *
     * The supplied data stream (supplied by the unit test) fails intermittently.
     * Your task is to return the stream, augmented with retry capability.
     */
    public Observable<String> exercise10(Observable<String> data) {
        // ------------ INSERT CODE HERE! ----------------------------
        // use retry()
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 11 - (de)materialize
     *
     * In some cases, it's useful to be able to make the 3 event types (element, error or complete)
     * explicit. This is named 'materialize'. The reverse is 'dematerialize'. Effectively, this (un)wraps
     * events with a Notification, which has an enum Kind property with values OnNext, OnError and OnCompleted.
     * <p/>
     * Materializing an Observable enables you to switch on the type, in stead of supplying a closure for each
     * event.
     * <p/>
     * Your assignment: complete the given code to make it equivalent to: map(i -> i + 1)
     * <p/>
     * As you can see, materializing adds a lot of boilerplate. Use it only when required for complicated use cases - see the next exercise.
     * <p/>
     * Note the use of flatMap to concatenate the individual single-event Observables returned by the switch-construction.
     * @param videoIds an Observable of videoIds
     * @return Observable of videoIds, increased by one
     */
    public Observable<Integer> exercise11(Observable<Integer> videoIds) {
        return videoIds.materialize().flatMap((Notification<Integer> notification) -> {
                switch (notification.getKind()) {
                    case OnNext:
                        // ------------ INSERT CODE HERE! ----------------------------
                        // use Notification.createOnNext()
                        // ------------ INSERT CODE HERE! ----------------------------
                        // TODO add implementation
                        return Observable.error(new RuntimeException("Not Implemented"));
                    case OnError:
                        return Observable.error(notification.getThrowable());
                    case OnCompleted:
                    default:
                        return Observable.just(notification);
                }
            }
        ).dematerialize();
    }

    /**
     * Exercise 12 - retry and (de)materialize, composed
     *
     * A 'reading' exercise. Its drives the point home of using materialize() only when really needed.
     * The focal point, however, is the retry() operator. The conditionalRetry() method 'lifts'
     * it to (infinitely) retry only *recoverable* errors.
     *
     * This feature is illustrated by simply concatenating the results of an (eventually) succeeding Observable, and one
     * that fails with an unrecoverable IllegalStateException.
     *
     * There is no code to be added for this 'exercise'. Simply read, execute & learn.
     */
    public Observable<String> exercise12(String name) {
        Observable<String> oWithRuntimeException = failingObservable(3, s -> {
            s.onNext(name);
            s.onCompleted();
        });
        Observable<String> oWithIllegalStateException = failingObservable(3, s -> s.onError(new IllegalStateException()));

        Observable<String> helloObservable = conditionalRetry(oWithRuntimeException, t -> t instanceof IllegalStateException);
        Observable<String> failingObservable = conditionalRetry(oWithIllegalStateException, t -> t instanceof IllegalStateException);

        return helloObservable.concatWith(failingObservable);
    }

    /**
     * This is a helper method. For the next exercise, kindly scroll a bit more.
     *
     * Creates an Observable that will fail the first 'fails' times with a RuntimeException.
     * This simulates a recoverable error.
     * Finally, the supplied action will be invoked, passing in the Subscriber.
     *
     * @param fails number of simulated failures
     * @param action subscription action
     * @param <T> type of emitted items
     * @return recoverable Observable
     */
    private <T> Observable<T> failingObservable(int fails, Observable.OnSubscribe<T> action) {
        final AtomicInteger c = new AtomicInteger();
        return Observable.create((Subscriber<? super T> s) -> {
            System.out.println("Try: " + c.get());
            if (c.incrementAndGet() < fails) {
                s.onError(new RuntimeException("recoverable"));
            } else {
                action.call(s);
            }
        });
    }

    /**
     * This is a helper method. For the next exercise, kindly scroll a bit more.
     *
     * Modifies the supplied Observable to be conditionally retryable. Inifinite retries will be performed,
     * unless the error event matches the supplied isFatal condition.
     *
     * @param o Observable to make conditionally retryable
     * @param isFatal predicate to determine whether an error event is fatal
     * @return conditionally retryable Observable
     */
    private <T> Observable<T> conditionalRetry(final Observable<T> o, Predicate<Throwable> isFatal) {
        return o.materialize().flatMap(n -> {
            if (n.isOnError()) {
                if (isFatal.test(n.getThrowable())) {
                    /* this will emit the IllegalStateException during dematerialize();
                       effectively IllegalStateException won't be retried by retry() because
                       it's wrapped in a Notification of Kind.OnError.
                     */
                    return Observable.just(n);
                } else /* RuntimeException - let's 'rethrow' to let retry() kick in */ {
                    return Observable.error(n.getThrowable());
                }
            } else {
                return Observable.just(n);
            }
        }).retry().dematerialize();
    }

    /**
     * Exercise 13 - unit testing with timing
     *
     * You might have peeked already at the unit test code. It uses the rxjava-supplied TestSubscriber (heavily)
     * and TestScheduler (in a few cases).
     *
     * This exercise shows how you can 'play' with time using the TestScheduler, for unit testing purposes.
     *
     * Please check the unit testing code for more examples of how to use TestSubscriber.
     */
    public void exercise13() {
        TestScheduler test = Schedulers.test();
        TestSubscriber<String> ts = new TestSubscriber<>();

        Observable.interval(200, TimeUnit.MILLISECONDS, test)
                .map(i -> {
                    return i + " value";
                }).subscribe(ts);

        test.advanceTimeBy(200, TimeUnit.MILLISECONDS);
        ts.assertReceivedOnNext(Arrays.asList("0 value"));

        test.advanceTimeTo(1000, TimeUnit.MILLISECONDS);
        // ------------ INSERT CODE HERE! ----------------------------
        // assert all items which must have been emitted after 1000ms.
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        throw new RuntimeException("Not Implemented");
    }
}

package learnrxjava.solutions;

import learnrxjava.exercises.ObservableExercises;
import learnrxjava.exercises.ObservableSecondLevelExercises;
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

public class ObservableSolutionsSecondLevel extends ObservableSecondLevelExercises {

    public Observable<List<String>> exercise01(Observable<String> data) {
        return data.toSortedList();
    }

    public Observable<List<String>> exercise02(Observable<String> data) {
        return data.toSortedList((string1, string2) -> string1.length() - string2.length());
    }

    public Observable<String> exercise03(Observable<String> movieIds) {
        return movieIds.skip(3).distinct();
    }
    public Observable<Integer> exercise04(Future<Integer> videoId) {
        return Observable.from(videoId, 2, TimeUnit.SECONDS);
    }

    public Observable<Integer> exercise05(Observable<Integer> nums) {
        return nums.reduce((max, item) -> {
            if (item > max) {
                return item;
            } else {
                return max;
            }
        });
    }

    public Observable<Integer> exercise06(Observable<Integer> nums) {
        return nums.scan(Math::max);
    }

    public Observable<JSON> exercise07(Observable<Movies> movies) {
        return movies.flatMap(ml -> ml.videos.<JSON> flatMap(v -> v.boxarts.reduce((max, box) -> {
            int maxSize = max.height * max.width;
            int boxSize = box.height * box.width;
            if (boxSize < maxSize) {
                return box;
            } else {
                return max;
            }
        }).map(maxBoxart -> ObservableExercises.json("id", v.id, "title", v.title, "boxart", maxBoxart.url))));
    }

    public Observable<String> exercise08(Observable<String> data) {
        return data.onErrorResumeNext(Observable.just("fallback-data"));
    }

    public Observable<String> exercise10(Observable<String> data) {
        return data.retry();
    }

    public Observable<Integer> exercise11(Observable<Integer> videoIds) {
        return videoIds.materialize().flatMap((Notification<Integer> notification) -> {
                    switch (notification.getKind()) {
                        case OnNext:
                            return Observable.just(Notification.createOnNext(notification.getValue() + 1));
                        case OnError:
                            return Observable.error(notification.getThrowable());
                        case OnCompleted:
                        default:
                            return Observable.just(notification);
                    }
                }
        ).dematerialize();
    }

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

    private <T> Observable<T> conditionalRetry(final Observable<T> o, Predicate<Throwable> isFatal) {
        return o.materialize().flatMap(n -> {
            if (n.isOnError()) {
                if (isFatal.test(n.getThrowable())) {
                    return Observable.just(n);
                } else /* RuntimeException - let's 'rethrow' to let retry() kick in */ {
                    return Observable.error(n.getThrowable());
                }
            } else {
                return Observable.just(n);
            }
        }).retry().dematerialize();
    }

    public void exercise13() {
        TestScheduler test = Schedulers.test();
        TestSubscriber<String> ts = new TestSubscriber<>();

        Observable.interval(200, TimeUnit.MILLISECONDS, test)
                .map(i -> i + " value").subscribe(ts);

        test.advanceTimeBy(200, TimeUnit.MILLISECONDS);
        ts.assertReceivedOnNext(Arrays.asList("0 value"));

        test.advanceTimeTo(1000, TimeUnit.MILLISECONDS);
        ts.assertReceivedOnNext(Arrays.asList("0 value", "1 value", "2 value", "3 value", "4 value"));
    }
}
package learnrxjava.solutions;

import learnrxjava.exercises.ObservableExercises;
import learnrxjava.types.JSON;
import learnrxjava.types.Movie;
import learnrxjava.types.Movies;
import rx.Notification;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.observables.MathObservable;
import rx.schedulers.TestScheduler;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

import java.util.AbstractMap;
import java.util.List;

import static java.util.concurrent.TimeUnit.*;

public class ObservableSolutions extends ObservableExercises {
    
    @Override
    public Observable<String> exercise00(Observable<String> names) {
        return names.map(name -> "Hello " + name + "!");
    }

    @Override
    public Observable<Integer> exercise01(Observable<Integer> nums) {
        return nums.filter(i -> i % 2 == 0);
    }

    @Override
    public Observable<String> exercise02(Observable<Integer> nums) {
        return nums.filter(i -> i % 2 == 0).map(i -> i + "-Even");
    }

    @Override
    public Observable<Integer> exercise03(Observable<Movies> movies) {
        return movies.<Integer> concatMap(ml -> {
            return ml.videos.map(v -> v.id);
        });
    }

    @Override
    public int exercise04(Observable<Integer> nums) {
        Sum sum = new Sum();

        nums.subscribe(new OnNext<Integer>() {
            @Override
            public void onNext(Integer t) {
                sum.increment(t);
            }
        });

        return sum.getSum();
    }

    @Override
    public String exercise05(Observable<Integer> faultyNums) {
        StringBuilder message = new StringBuilder();

        // Faulty nums is an Observable that will throw an exception
        faultyNums.subscribe(new OnError<Integer>() {
            @Override
            public void onError(Throwable t) {
                message.append(t.getMessage());
            }
        });

        return message.toString();
    }

    @Override
    public String exercise06(Observable<Integer> someNumbers) {
        StringBuilder message = new StringBuilder();

        someNumbers.subscribe(new OnComplete<Integer>() {
            int count = 0;
            @Override
            public void onNext(Integer t) {
                count++;
            }

            @Override
            public void onCompleted() {
                message.append(String.format("found %d items", count));
            }
        });

        return message.toString();
    }

    @Override
    public Observable<String> exercise07(String name) {
        return Observable.create(subscriber -> {
            subscriber.onNext(name);
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<String> exercise08(int divisor) {
        return Observable.create(subscriber -> {
            try {
                int quotient = 42 / divisor;
                subscriber.onNext(String.format("The number 42 divided by your input is: %d", quotient));
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
    
    @Override
    public Observable<String> exercise09() {
        return Observable.just("Hello World!");
    }
    
    @Override
    public Observable<String> exercise10(Observable<String> a, Observable<String> b) {
        return Observable.zip(a, b, (x, y) -> x + " " + y);
    }

    @Override
    public Observable<String> exercise11(Observable<Movies> movieLists) {
        return movieLists.first().concatMap(movieList -> movieList.videos.first().map(video -> video.title));
    }

    @Override
    public Observable<String> exercise12(Observable<Movies> movieLists, double rating) {
        return movieLists.last().concatMap(movieList -> 
                movieList.videos.last(movie -> movie.rating == rating)
                        .map(video -> video.title)
        );
    }
   
    @Override
    public Observable<String> exercise13(Observable<Movies> movieLists) {
        return movieLists.concatMap(movieList -> movieList.videos).take(5).map(video -> video.title);
    }

    @Override
    public Observable<String> exercise14(Observable<Movies> movieLists, int pageLength) {
        return movieLists.concatMap(movieList -> movieList.videos).skip(pageLength).take(pageLength).map(video -> video.title);
    }
    
    @Override
    public Observable<String> exercise15(Observable<Movies> movieLists) {
        return movieLists.concatMap(movieList -> movieList.videos).filter(video -> video.rating >= 4.5).limit(5).map(video -> video.title);
    }

    @Override
    public Observable<Boolean> exercise16(Observable<Movies> movies) {
        return movies.concatMap(movies1 -> movies1.videos).concatMap(movie -> movie.interestingMoments.exists(interestingMoment -> "epic".equals(interestingMoment.type)));
    }

    @Override
    public Observable<Boolean> exercise17(Observable<Movies> marathonCandidates) {
        return marathonCandidates.concatMap(movies -> movies.videos).all(movies -> movies.minimalAge < 18);
    }

    @Override
    public Observable<String> exercise18(Scheduler scheduler) {
        Observable<String> data = Observable.just("one", "two", "three", "four", "five");
        Observable<Long> interval = Observable.interval(1, SECONDS, scheduler);
        return Observable.zip(data, interval, (d, t) -> {
            return d + " " + (t + 1);
        });
    }

    @Override
    public Observable<Long> exercise19(Observable<Long> odd, Observable<Long> even) {
        return odd.mergeWith(even);
    }

    
    @Override
    public Observable<Integer> exercise20(Observable<Movies> movies) {
        return movies.<Integer> flatMap(ml -> {
            return ml.videos.map(v -> v.id);
        });
    }

    @Override
    public boolean exercise21() {
        return true;
    }

    @Override
    public Observable<String> exercise22(Observable<Movies> movieLists, Scheduler scheduler) {
        return movieLists.sample(4, SECONDS, scheduler).flatMap(movieList -> movieList.videos).map(video -> video.title);
    }


















    @Override
    public Observable<List<Integer>> exercise23(Observable<Integer> burstySuggestedVideoIds, Scheduler scheduler) {
        return burstySuggestedVideoIds.buffer(500, MILLISECONDS, scheduler);
    }

    @Override
    public Observable<Observable<Integer>> exercise24(Observable<Integer> burstySuggestedVideoIds, Scheduler scheduler) {
        return burstySuggestedVideoIds.window(200, 1000, MILLISECONDS, scheduler);
    }

    @Override
    public Observable<Movie> exercise25(Observable<Movie> movies, Func1<Movie, Observable<Movie>> advertFunction, Scheduler scheduler) {
        return movies
                .map(advertFunction)
                .flatMap(ads -> ads.delay(10, SECONDS, scheduler));
    }

    @Override
    public Observable<Double> exercise26(Observable<Movie> movieLists, Scheduler scheduler) {
        return MathObservable.averageDouble(
                movieLists
                        .throttleLast(200, MILLISECONDS, scheduler)
                        .map(movie1 -> movie1.rating));
    }

    /**
     * Solutions for exercises 27 - 33 can be found in class ObservableSolutionsTest
     */

    @Override
    public Observable<Integer> exercise34(Observable<Integer> faultyObservable) {
        return faultyObservable.onErrorReturn(e -> -1);
    }

    @Override
    public Observable<Integer> exercise35(Observable<Integer> planA, Observable<Integer> planB) {
        return planA.onErrorResumeNext(planB);
    }

    @Override
    public Observable<String> exercise36(Observable<String> intermittentStream) {
        return intermittentStream.retry(3);
    }

    @Override
    public Observable<GroupedObservable<String, Double>> exercise37(Observable<Movies> movieLists) {
		final Observable<Movie> movies = movieLists.flatMap(movieList -> movieList.videos);
		final Observable<AbstractMap.SimpleEntry<String, Double>> actorRatings = movies
				.flatMap(movie -> movie.topCast.map(actor -> new AbstractMap.SimpleEntry<String, Double>(actor, movie.rating)));
		return actorRatings.groupBy(entry -> entry.getKey(), entry -> entry.getValue());
    }

    @Override
    public Observable<Integer> exercise38(Observable<Integer> nums) {
        return nums.reduce((max, item) -> {
            if (item > max) {
                return item;
            } else {
                return max;
            }
        });
    }

    @Override
    public Observable<Integer> exercise39(Observable<Integer> nums) {
        return nums.scan(Math::max);
    }

    @Override
    public Observable<JSON> exercise40(Observable<Movies> movies) {
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

    @Override
    public Observable<List<String>> exercise41(Observable<String> data) {
        return data.toSortedList();
    }

    @Override
    public Observable<List<String>> exercise42(Observable<String> data) {
        return data.toSortedList((string1, string2) -> string1.length() - string2.length());
    }

    @Override
    public Observable<String> exercise43(Observable<String> movieIds) {
        return movieIds.skip(3).distinct();
    }

    @Override
    public Observable<Integer> exercise44(Observable<Integer> videoIds) {
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

    // Note: exercise45() is only in ObservableExercises

    public Subject<String,String> exercise46(String initialMovieName, TestScheduler scheduler) {
        ReplaySubject<String> subject = ReplaySubject.createWithTime(5, HOURS, scheduler);
        subject.onNext(initialMovieName);
        return subject;
    }

    /*
     * **************
     * below are helper methods
     * **************
     */

    // This function can be used to build JSON objects within an expression
    public static JSON json(Object... keyOrValue) {
        JSON json = new JSON();

        for (int counter = 0; counter < keyOrValue.length; counter += 2) {
            json.put((String) keyOrValue[counter], keyOrValue[counter + 1]);
        }

        return json;
    }

}

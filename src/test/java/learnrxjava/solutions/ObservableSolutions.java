package learnrxjava.solutions;

import learnrxjava.exercises.ObservableExercises;
import learnrxjava.types.JSON;
import learnrxjava.types.Movies;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public class ObservableSolutions extends ObservableExercises {
    /**
     * Exercise 1
     * <p/>
     * Transform the incoming Observable from just a list of names to a
     * friendly greeting, i.e. "Hello [Name]!".
     * <p/>
     * For example:
     * ["Remko", "Hedzer"] -> ["Hello Remko!", "Hello Hedzer!"]
     */
    public Observable<String> exercise01(Observable<String> names) {
        return names.map(name -> "Hello " + name + "!");
    }

    /**
     * Exercise 2
     * <p/>
     * Given an observable of numbers, filter out the even numbers:
     * <p/>
     * [1, 2, 3, 4, 5] -> [2, 4]
     */
    public Observable<Integer> exercise02(Observable<Integer> nums) {
        return nums.filter(i -> i % 2 == 0);
    }

    /**
     * Exercise 3
     * <p/>
     * Just like with our ComposableList we can compose different functions
     * with Observables.
     * <p/>
     * Given an observable of numbers, filter out the even ones and transform them
     * to a String like the following:
     * <p/>
     * [1,2,3,4,5,6] -> ["2-Even", "4-Even", "6-Even"]
     */
    public Observable<String> exercise03(Observable<Integer> nums) {
        return nums.filter(i -> i % 2 == 0).map(i -> i + "-Even");
    }

    /**
     * Flatten out all video in the stream of Movies into a stream of videoIDs
     *
     * @param movies
     * @return Observable of Integers of Movies.videos.id
     */
    public Observable<Integer> exerciseConcatMap(Observable<Movies> movies) {
        return movies.<Integer> concatMap(ml -> {
            return ml.videos.map(v -> v.id);
        });
    }

    /**
     * Exercise 4
     * <p/>
     * When using an Iterable (like a normal List) we (the consumer) have to pull
     * the values out of the producer (the List). However, Observables are push
     * based, which turns things around. Now the producer (the Observable) chooses
     * the moment to push a value to us (the consumer) and we have to react to that
     * event, i.e. the reception of a new value.
     * <p/>
     * We specify how to react to such an event by supplying the Observable with
     * a subscription. On this subscription we give the Observable a handle (callback)
     * to use when it has another value.
     * <p/>
     * In the next exercise we will try to get a first look and feel for how this works.
     * The nums Observable will "push" (or "emit") values and you have add these values
     * together thus producing their count.
     * <p/>
     * For example:
     * [1,2,3,4,5,6] -> 21
     */
    public int exercise04(Observable<Integer> nums) {
        Sum sum = new Sum();

        // Here we subscribe to the Observable with our specific subscription. In our
        // subscription we can specify how to react to produced / pushed / emitted values.
        nums.subscribe(new OnNext<Integer>() {
            @Override
            public void onNext(Integer t) {
                sum.increment(t);
            }
        });

        return sum.getSum();
    }

    /**
     * Exercise 5
     * <p/>
     * The previous exercise should have looked familiar. In fact it is the standard
     * Observable pattern as described by the Gang of Four [GoF]. However, this
     * standard pattern misses two important concepts. Two concepts that are present on
     * an Iterable.
     * <p/>
     * Let's revisit Iterable. As we saw Iterable (by means of iterator) provides us with
     * a couple of scenarios:
     * <p/>
     * next()    - to get the next element,
     * hasNext() - to check if there are more elements and
     * it can throw an exception if anything is wrong.
     * <p/>
     * Thus far we've only seen the next() equivalent for Observables (onNext()), but we
     * still lack two.
     * <p/>
     * First we will implement the onError() method, which is called if the observable
     * throws an exception.
     */
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

    /**
     * Let's complete (pun intended!) our survey of the three Observable interface methods
     * by using onCompleted() to aggregate the result of onNext()'ing through a stream
     * of numbers.
     *
     * onComplete() can also be used to free any resources, if and when required.
     */
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

    public Observable<String> exercise07(String name) {
        return Observable.create(subscriber -> {
            subscriber.onNext(name);
            subscriber.onCompleted();
        });
    }

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

    /**
     * Return an Observable that emits a single value "Hello World"
     *
     * @return "Hello World!"
     */
    public Observable<String> exercise09() {
        return Observable.just("Hello World!");
    }

    /**
     * Combine 2 streams into pairs using zip.
     *
     * a -> "one", "two", "red", "blue"
     * b -> "fish", "fish", "fish", "fish"
     * output -> "one fish", "two fish", "red fish", "blue fish"
     */
    public Observable<String> exercise10(Observable<String> a, Observable<String> b) {
        return Observable.zip(a, b, (x, y) -> x + " " + y);
    }

    /**
     * Now that we're familiar with just and zip, we can begin to add a touch of timing.
     * We can exploit the fact that zip requires both values to be present at the same time - and thus
     * has to wait until the last of each pair has arrived - to slow down a fast-paced stream. Zipping that
     * with the interval Observable will do just that.
     * <p/>
     * @return an Observable with items "one 1", "two 2", etc., each 1 second apart
     */
    public Observable<String> exercise11() {
        Observable<String> data = Observable.just("one", "two", "three", "four", "five");
        Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);
        return Observable.zip(data, interval, (d, t) -> {
            return d + " " + (t+1);
        });
    }

    /**
     * Transform the incoming Observable from "Hello" to "Hello [Name]" where [Name] is your name.
     *
     * @param hello
     */
    public Observable<String> exerciseMap(Observable<String> hello) {
        return hello.map(t -> t + " Ben!");
    }

    /**
     * Given a stream of numbers, choose the even ones and return a stream like:
     * <p>
     * 2-Even
     * 4-Even
     * 6-Even
     */
    public Observable<String> exerciseFilterMap(Observable<Integer> nums) {
        return nums.filter(i -> i % 2 == 0).map(i -> i + "-Even");
    }

    /**
     * Flatten out all video in the stream of Movies into a stream of videoIDs
     *
     * Use flatMap this time instead of concatMap. In Observable streams
     * it is almost always flatMap that is wanted, not concatMap as flatMap
     * uses merge instead of concat and allows multiple concurrent streams
     * whereas concat only does one at a time.
     *
     * We'll see more about this later when we add concurrency.
     *
     * @param movies
     * @return Observable of Integers of Movies.videos.id
     */
    public Observable<Integer> exerciseFlatMap(Observable<Movies> movies) {
        return movies.<Integer> flatMap(ml -> {
            return ml.videos.map(v -> v.id);
        });
    }

    /**
     * Retrieve the largest number.
     *
     * Use reduce to select the maximum value in a list of numbers.
     */
    public Observable<Integer> exerciseReduce(Observable<Integer> nums) {
        return nums.reduce((max, item) -> {
            if (item > max) {
                return item;
            } else {
                return max;
            }
        });
    }

    /**
     * Retrieve the id, title, and smallest box art url for every video.
     *
     * Now let's try combining reduce() with our other functions to build more complex queries.
     *
     * This is a variation of the problem we solved earlier, where we retrieved the url of the boxart with a
     * width of 150px. This time we'll use reduce() instead of filter() to retrieve the _smallest_ box art in
     * the boxarts list.
     *
     * See Exercise 19 of ComposableListExercises
     */
    public Observable<JSON> exerciseMovie(Observable<Movies> movies) {
        return movies.flatMap(ml -> {
            return ml.videos.<JSON> flatMap(v -> {
                return v.boxarts.reduce((max, box) -> {
                    int maxSize = max.height * max.width;
                    int boxSize = box.height * box.width;
                    if (boxSize < maxSize) {
                        return box;
                    } else {
                        return max;
                    }
                }).map(maxBoxart -> {
                    return json("id", v.id, "title", v.title, "boxart", maxBoxart.url);
                });
            });
        });
    }

    /**
     * Don't modify any values in the stream but do handle the error
     * and replace it with "default-value".
     */
    public Observable<String> handleError(Observable<String> data) {
        return data.onErrorResumeNext(Observable.just("default-value"));
    }

    /**
     * The data stream fails intermittently so return the stream
     * with retry capability.
     */
    public Observable<String> retry(Observable<String> data) {
        return data.retry();
    }

    // This function can be used to build JSON objects within an expression
    private static JSON json(Object... keyOrValue) {
        JSON json = new JSON();

        for (int counter = 0; counter < keyOrValue.length; counter += 2) {
            json.put((String) keyOrValue[counter], keyOrValue[counter + 1]);
        }

        return json;
    }

}

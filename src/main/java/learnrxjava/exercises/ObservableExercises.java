package learnrxjava.exercises;

import learnrxjava.types.JSON;
import learnrxjava.types.Movie;
import learnrxjava.types.Movies;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.observables.GroupedObservable;

/**
 * Now you have mastered the ComposableList, it is time to move on. The exercises
 * in this class will help you gain an understanding of Observables. Before we go
 * in depth about explaining what Observables are, first think of them as 
 * ComposableLists or some other collection.
 * 
 * This means you can apply the same compositional methods (map, filter, etc) to 
 * Observables as to ComposableLists.
 *
 * An Observable is a lot like an Event as sent to the age-old JDK EventListener.
 * Like an Event, an Observable represents a sequence
 * of values that a data producer pushes to the consumer. However unlike an Event,
 * an Observable can signal to a listener that it has completed, and will send no more data.
 *
 * Observables can send data to consumers asynchronously. Observables are a sequence
 * of values, delivered one after the other. Therefore it's possible that an Observable
 * can go on sending data to a listener forever just like an infinite stream.
 *
 * Querying (pull-based) Lists only gives us a snapshot. By contrast, querying (push-based)
 * Observables allows us to create data sets that react and update as the system changes over time. This
 * enables a very powerful type of programming known as reactive programming.
 */
public class ObservableExercises {

    /**
     * Exercise 0 - mapping
     * <p/>
     * Transform the incoming Observable from just a list of names to a
     * friendly greeting, i.e. "Hello [Name]!".
     * <p/>
     * For example:
     * ["Remko", "Hedzer"] -> ["Hello Remko!", "Hello Hedzer!"]
     */
    public Observable<String> exercise00(Observable<String> names) {

        // ------------ INSERT CODE HERE! ----------------------------
        // Change the Strings in the names Observable using map.
        // Hint: You can use autocomplete.
        // ------------ INSERT CODE HERE! ----------------------------

        // return names. // TODO add implementation

        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 1 - filtering
     * <p/>
     * Given an observable of numbers, filter out the even numbers:
     * <p/>
     * [1, 2, 3, 4, 5] -> [2, 4]
     */
    public Observable<Integer> exercise01(Observable<Integer> nums) {

        // ------------ INSERT CODE HERE! ----------------------------
        // Filter out the even numbers        
        // ------------ INSERT CODE HERE! ----------------------------
        // return nums. // TODO add implementation

        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 2 - composition
     * <p/>
     * Just like with our ComposableList we can compose different functions
     * with Observables.
     * <p/>
     * Given an observable of numbers, filter out the even ones and transform them
     * to a String like the following:
     * <p/>
     * [1,2,3,4,5,6] -> ["2-Even", "4-Even", "6-Even"]
     */
    public Observable<String> exercise02(Observable<Integer> nums) {

        // ------------ INSERT CODE HERE! ----------------------------
        // Compose filter and map
        // ------------ INSERT CODE HERE! ----------------------------
        // return nums. // TODO add implementation

        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 3 - composing two operators
     * <p/>
     * Flatten out all videos in the stream of Movies into a stream of videoIDs.
     * Parameter movieLists effectively is an Observable of Observable of videoIDs.
     * Operator concatMap will sequentially concatenate the items emitted by the inner Observable,
     * resulting in a single 'layer' of Observable.
     * <p/>
     * Use the map operator to convert a Video to its id.
     * <p/>
     * @see ComposableListExercises#exercise11() - you can reuse your solution to that exercise here.
     * <p/>
     * @param movieLists
     * @return Observable of Integers of Movies.videos.id
     */
    public Observable<Integer> exercise03(Observable<Movies> movieLists) {
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 4 - Observable basics
     * <p/>
     * When using an Iterable (like a normal List) we (the consumer) have to pull
     * the values out of the producer (the List). However, Observables are push
     * based, which turns things around. Now the producer (the Observable) chooses
     * the moment to push a value to us (the consumer) and we have to react to that
     * event, i.e. the reception of a new value.
     * <p/>
     * We specify how to react to such an event by supplying the Observable with
     * a subscription, by invoking Observable.subscribe(). On this subscription we
     * give the Observable a handle ('callback') to use when it has another value,
     * by passing in an instance of Subscriber (encapsulated in OnNext&lt;Integer&gt; here, for your
     * convenience).
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
                // ------------ INSERT CODE HERE! ----------------------------
                // Update sum with the running total
                // Use auto complete
                // ------------ INSERT CODE HERE! ----------------------------
                // TODO add implementation
            }

        });

        //return sum.getSum();
        throw new UnsupportedOperationException("Not Implemented");
    }

    /**
     * Exercise 5 - error
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
                // ------------ INSERT CODE HERE! ----------------------------
                // Extract the error message and return it
                // ------------ INSERT CODE HERE! ----------------------------
                // TODO add implementation

                throw new UnsupportedOperationException("Not Implemented");
            }

        });

        return message.toString();
    }

    /**
     * Exercise 6 - completion
     * <p/>
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
                // ------------ INSERT CODE HERE! ----------------------------
                // Increment count by one
                // ------------ INSERT CODE HERE! ----------------------------
                // TODO add implementation
            }

            @Override
            public void onCompleted() {
                // ------------ INSERT CODE HERE! ----------------------------
                // Set the message to "found <count> items"
                // ------------ INSERT CODE HERE! ----------------------------
                // TODO add implementation
            }

        });

        //return message.toString();
        throw new UnsupportedOperationException("Not Implemented");
    }

    /**
     * Exercise 7 - production
     * <p/>
     * Now we'll explore the producer side of the push mechanism.
     * <p/>
     * An Observable produces values; an Observer, with its sub-interface Subscriber represents the consumer side
     * of things.
     * <p/>
     * The previous three exercises focused on the consumer side, by accepting onNext, onComplete and onError events.
     * Here, we will _produce_ these events. The most basic producer creates an Observable that emits just a single value.
     * After emitting that value, the subscriber must be informed that the stream is complete.
     * @param name value to emit
     * @return
     */
    public Observable<String> exercise07(String name) {
        return Observable.create(subscriber -> {
            // ------------ INSERT CODE HERE! ----------------------------
            // Signal 2 events to the subscriber that has been handed to us by Observable.create().
            // The first event must emit the name, the second must signal 'end-of-names'.
            // ------------ INSERT CODE HERE! ----------------------------
            // TODO add implementation
            throw new UnsupportedOperationException("Not Implemented");
        });
    }

    /**
     * Exercise 8 - wrapping up the basics
     * <p/>
     * Now we'll add error propagation to the mix. A producer can emit an error - in the form of a Throwable -
     * to signal an exceptional situation. The enables the consumer to take corrective action.
     * <p/>
     * @param divisor will divide the number 42
     * @return Observable that emits a single value "The number 42 divided by your input is: <number>"
     */
    public Observable<String> exercise08(int divisor) {
        return Observable.create(subscriber -> {
            try {
                int quotient = 42 / divisor;
                // ------------ INSERT CODE HERE! ----------------------------
                // Emit just the value "The number 42 divided by your input is: <quotient>"
                // ------------ INSERT CODE HERE! ----------------------------
                // TODO add implementation
                throw new UnsupportedOperationException("Not Implemented");
            } catch (Exception e) {
                // ------------ INSERT CODE HERE! ----------------------------
                // emit - not throw! - the Exception
                // ------------ INSERT CODE HERE! ----------------------------
                // TODO add implementation
                throw new UnsupportedOperationException("Not Implemented");
            }
        });
    }

    /**
     * Exercise 9 - just do it
     * <p/>
     * rxjava actually has an operator to do that which you have just (!) programmed in exercises 07 and 08.
     * <p/>
     * Its name is - just as you expected - Observable.just().
     * <p/>
     * Contrary to what you'd expect with this name, it's actually also possible to have it emit _multiple_
     * values, by supplying up to 9 arguments.
     */
    public Observable<String> exercise09() {
        // ------------ INSERT CODE HERE! ----------------------------
        // emit a single value "Hello World!"
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 10 - zip it up
     * <p/>
     * @see ComposableListExercises#exercise22() for your first encounter with zip.
     * Here, we will use it to simply combine 2 streams of Strings into pairs using zip.
     * <p/>
     * Example data appearing in the input streams:
     * a -> "one", "two", "red", "blue"
     * b -> "fish", "fish", "fish", "fish", "fish", "fish", "fish", "fish"
     * output to be produced -> "one fish", "two fish", "red fish", "blue fish"
     * <p/>
     * Note that any items without a counterpart in the sibling input stream will be dropped - the number of items in the
     * output will be equal to _the minimum_ of the number of items in both of the input streams.
     */
    public Observable<String> exercise10(Observable<String> a, Observable<String> b) {
        // ------------ INSERT CODE HERE! ----------------------------
        // zip up Observable a and b using a combiner function that concatenates the input from both values
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    // The following exercises are meant to obtain some API knowledge

    /**
     * Exercise 11 - First come, first served
     * <p/>
     * Observable comes with a lot of nifty convenience methods. first() is one of
     * them. It can help you when you don't feel the need to look further.
     * <p/>
     * So don't look further and finish the exercise. Just for fun, we threw in
     * a little flatMap as well...
     * 
     * @param movieLists an observable of lists of movies to work your magic on
     * @return the title of the first video of the first list of movies
     */
    public Observable<String> exercise11(Observable<Movies> movieLists) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Use Observable.first(), you might need some concatMap too...
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }
    
    /**
     * Exercise 12 - The 'last' exercise
     * <p/>
     * This time we are looking for the title of the last movie that the 
     * Observable emits that matches a specific rating
     * <p/>
     *
     * @param movieLists an observable of movies to work your magic on
     * @return the title of the latest movie that matches a rating
     */
    public Observable<String> exercise12(Observable<Movies> movieLists, double rating) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Get the last movie title that the Observable emits that matches the given rating.
        // Use Observable.last()
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 13 - Take it or leave it
     * <p/>
     * Return an Observable that returns the first 5 movies
     * <p/>
     *
     * @param movieLists an observable of lists of movies to work your magic on
     * @return the titles of the first 5 movies
     */
    public Observable<String> exercise13(Observable<Movies> movieLists) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Get the first 5 movie titles
        // Use Observable.take()
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 14 - Don't skip this one
     * <p/>
     * Return an observable that returns the titles of the movies on the second page
     * <p/>
     *
     * @param movieLists an observable of lists of movies to work your magic on
     * @return the movies on the second page
     */
    public Observable<String> exercise14(Observable<Movies> movieLists, int pageLength) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Skip the movies on the first page and return the titles of the movies 
        // that are on the second page. Use Observable.skip()
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 15 - No limit
     * <p/>
     * Return an observable that only emits the best movies.
     * <p/>
     *
     * @param movieLists an observable of movies to work your magic on
     * @return all movies with a rating equal or higher than 4.5 and no more than 5
     */
    public Observable<String> exercise15(Observable<Movies> movieLists) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Return all movies that are equal to or higher than 4.5 in rating. 
        // Also just give me 5 good ones or less. Use Observable.limit().
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 16 - Are there epic movies?
     * <p/>
     * Return an Observable that emits true if there are any epic interestingMoments in a movie
     * <p/>
     *
     * @param movies an observable of movies to work your magic on
     * @return An Observable that emits true if a movie has an epic interestingMoment
     */
    public Observable<Boolean> exercise16(Observable<Movies> movies) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Return true is there is an epic movie.
        // Use Observable.exists();
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 17 - Are these movies suitable?
     * <p/>
     * Return true if all movies in the marathonCandidates are suitable for everyone;
     * <p/>
     *
     * @param marathonCandidates an observable of movies to work your magic on
     * @return An Observable that emits true if the marathonCandidates are suitable
     */
    public Observable<Boolean> exercise17(Observable<Movies> marathonCandidates) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Return true if all movies have a minimalAge lower than 18
        // Use Observable.all();
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 18 - Timing is everything
     * <p/>
     * Now that we're familiar with just and zip, we can begin to add a touch of timing.
     * We can exploit the fact that zip requires both values to be present at the same time - and thus
     * has to wait until the last of each pair has arrived - to slow down a fast-paced stream. Zipping that
     * with the interval Observable will do just that.
     * <p/>
     * @return an Observable with items "one 1", "two 2", etc., each 1 second apart
     */
    public Observable<String> exercise18() {
        Observable<String> data = Observable.just("one", "two", "three", "four", "five");

        // ------------ INSERT CODE HERE! ----------------------------
        // use Observable.interval to get an item emitted each second.
        // Use Observable.zip to achieve the desired output
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 19 - Merge hell
     * <p/>
     * Timing matters when choosing which method to use. Merging is an operation
     * that preserves timing, as you will see below. Go ahead and take a look at
     * the corresponding unit test as well.
     * 
     * @param odd  an observable that emits an odd number every odd second
     * @param even an observable that emits an even number every even second
     * @param scheduler the scheduler you should use to merge
     * @return an Observable with the results
     */
    public Observable<Long> exercise19(Observable<Long> odd, Observable<Long> even, Scheduler scheduler) {
        // ------------ INSERT CODE HERE! ----------------------------
        // use Observable.mergeWith to interleave the two streams
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }
    
    /**
     * Exercise 20 - flatMap vs. concatMap; ordering and concurrency
     * <p/>
     * Let's revisit the nice domain of videos, of which we temporarily strayed
     * in the dull non-domain-oriented exercises above.
     * <p/>
     * Flatten out all video in the stream of Movies into a stream of videoIDs.
     * <p/>
     * Use flatMap this time instead of concatMap. In Observable streams
     * it is almost always flatMap that is wanted, not concatMap as flatMap
     * uses merge instead of concat and allows multiple concurrent streams
     * whereas concat only does one at a time.
     * <p/>
     * We'll see more about this later when we add concurrency.
     *
     * @param movieLists
     * @return Observable of Integers of Movies.videos.id
     */
    public Observable<Integer> exercise20(Observable<Movies> movieLists) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Use flatMap
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 21 - RTFM!
     * <p/>
     * Let's read up on marble diagrams. Marble diagrams depict data flows for each of the reactive Observables.
     * They help enormously in understanding what's going on.
     * <p/>
     * For your convenience, they're included in the rxjava javadoc. You can revisit
     * your solutions to two previous exercises, and lookup the javadoc in your IDE.
     * <p/>
     * @see ObservableExercises#exercise03(Observable) - here you used concatMap
     * @see ObservableExercises#exercise20(Observable) - here you used flatMap
     * <p/>
     * Look carefully at both diagrams. Where do they differ? Can you relate this to section 3 in
     * the description in exercise 20?
     * If and when you understand, please say so in the code below, and progress to the next exercise.
     * <p/>
     * For more info, please visit:
     * <ul>
     *     <li>https://github.com/ReactiveX/RxJava/wiki</li>
     *     <li>http://reactivex.iohttp://reactivex.io/documentation/operators/flatmap.html</li>
     * </ul>
     * @return true when you understand what's going on
     */
    public boolean exercise21() {
        return false;
    }

    /**
     * Exercise 22 - A sample of a fine exercise
     * <p/>
     * Lazy as we are, we are not going to continuously watch a possibly infinite stream.
     * That would like watching the output of your compiler. And who does that?
     * Much better, we are going to take sample now and then and look at that.
     * <p/>
     * For this exercise, we are going to sample every four seconds. 
     * 
     * @see http://reactivex.io/RxJava/javadoc/rx/Observable.html#sample(long, java.util.concurrent.TimeUnit)
     * @param movieLists an observable of lists of movies that will spit out something every three seconds
     * @param scheduler the scheduler you should use for sampling
     * @return the titles of the movies at the point of sampling (after four seconds)
     */
    public Observable<String> exercise22(Observable<Movies> movieLists, Scheduler scheduler) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Use Observable.sample() and the provided scheduler
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 23 - buffer that burst, or the bubble will burst!
     *
     * Picture this: an external service pushes ids of suggested video's to watch.
     * Being an external service on a network, its traffic can be 'bursty': the one moment a lot of data,
     * the other hardly anything. The bursts might swamp us.
     *
     * The buffer operator partially insulates us from these bursts, by buffering them up and emitting the
     * buffers as a List of items. On what conditions these Lists are emitted is entirely configurable,
     * by way of 12 different overloads of Observable.buffer()!
     *
     * The objective: buffer the incoming burstySuggestedVideoIds in intervals of 500ms.
     *
     * Your task: look up the buffer operator at http://reactivex.io/documentation/operators/buffer.html.
     * Click through to the RxJava Language-Specific Information, and find the required variant.

     * @param burstySuggestedVideoIds
     * @return buffered Lists of suggestedVideoIds at 500ms intervals
     */
    public Observable<List<Integer>> exercise23(Observable<Integer> burstySuggestedVideoIds) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Use Observable.buffer()
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    public Observable<Observable<Integer>> exercise24(Observable<Integer> burstySuggestedVideoIds) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Use Observable.window()
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        //return Observable.error(new RuntimeException("Not Implemented"));
        return burstySuggestedVideoIds.window(200, 500, TimeUnit.MILLISECONDS);
    }

    /**
     * Exercise 29 - Group By
     * 
     * This exercise is more advanced. Don't worry if it takes some time to wrap your head around it.
     * 
     * @param movieLists an observable of movies to work your magic on
     * @return the ratings of all the movies made per actor
     */
    public Observable<GroupedObservable<String, Double>> exercise29(Observable<Movies> movieLists) {
        // ------------ INSERT CODE HERE! ----------------------------
        // Use Observable.groupBy()
        // Hint1: You can find the actors in the topCast property of a Movie
        // Hint2: You can use AbstractMap.SimpleEntry<K, V> to store the relation between an actor and its movies
        // ------------ INSERT CODE HERE! ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
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

    public static abstract class OnNext<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            // NOOP
        }

        @Override
        public void onError(Throwable e) {
            // NOOP
        }
    }

    public static abstract class OnComplete<T> extends Subscriber<T> {
        @Override
        public void onNext(T t) {
            // NOOP
        }

        @Override
        public void onError(Throwable e) {
            // NOOP
        }
    }

    public static abstract class OnError<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            // NOOP
        }

        @Override
        public void onNext(T t) {
            // NOOP
        }
    }

    public static class Sum {
        private int sum;

        public void increment(int withAmount) {
            sum += withAmount;
        }

        public int getSum() {
            return sum;
        }
    }
}

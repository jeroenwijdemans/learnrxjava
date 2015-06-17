package learnrxjava.exercises;

import learnrxjava.types.JSON;
import learnrxjava.types.Movies;
import rx.Observable;
import rx.Subscriber;

/**
 * Now you have mastered the ComposableList, it is time to move on. The exercises
 * in this class will help you gain an understanding of Observables. Before we go
 * in depth about explaining what Observables are, first think of them as 
 * ComposableLists or some other collection.
 * 
 * This means you can apply the same compositional methods (map, filter, etc) to 
 * Observables as to ComposableLists.
 */
public class ObservableExercises {

    /**
     * Exercise 1
     * 
     * Transform the incoming Observable from just a list of names to a
     * friendly greeting, i.e. "Hello [Name]!".
     *
     * For example: 
     * ["Remko", "Hedzer"] -> ["Hello Remko!", "Hello Hedzer!"]
     */
    public Observable<String> exercise01(Observable<String> names) {
        
        // ------------ INSERT CODE HERE! ----------------------------
        // Change the Strings in the names Observable using map.
        // Hint: You can use autocomplete.
        // ------------ INSERT CODE HERE! ----------------------------
        
        // return names. // TODO add implementation
        
        return Observable.error(new RuntimeException("Not Implemented"));
    }
    
    /**
     * Exercise 2
     * 
     * Given an observable of numbers, filter out the even numbers:
     * 
     * [1, 2, 3, 4, 5] -> [2, 4]
     */
    public Observable<Integer> exercise02(Observable<Integer> nums) {
        
        // ------------ INSERT CODE HERE! ----------------------------
        // Filter out the even numbers        
        // ------------ INSERT CODE HERE! ----------------------------
        // return nums. // TODO add implementation

        return Observable.error(new RuntimeException("Not Implemented"));
    }
    
    /**
     * Exercise 3
     * 
     * Just like with our ComposableList we can compose different functions
     * with Observables. 
     * 
     * Given an observable of numbers, filter out the even ones and transform them
     * to a String like the following:
     * 
     * [1,2,3,4,5,6] -> ["2-Even", "4-Even", "6-Even"]
     */
    public Observable<String> exercise03(Observable<Integer> nums) {
        
        // ------------ INSERT CODE HERE! ----------------------------
        // Compose filter and map
        // ------------ INSERT CODE HERE! ----------------------------
        // return nums. // TODO add implementation
        
        return Observable.error(new RuntimeException("Not Implemented"));
    }
    
    // TODO Difference between Iterable and Observable
    // - pull vs push
    
    // Exercise onNext
    
    /**
     * Exercise 4
     * 
     * When using an Iterable (like a normal List) we (the consumer) have to pull 
     * the values out of the producer (the List). However, Observables are push
     * based, which turns things around. Now the producer (the Observable) chooses
     * the moment to push a value to us (the consumer) and we have to react to that 
     * event, i.e. the reception of a new value.
     * 
     * We specify how to react to such an event by supplying the Observable with
     * a subscription. On this subscription we give the Observable a handle (callback)
     * to use when it has another value.
     * 
     * In the next exercise we will try to get a first look and feel for how this works.
     * The nums Observable will "push" (or "emit") values and you have add these values
     * together thus producing their sum.
     * 
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
                // Use autocomplete
                // ------------ INSERT CODE HERE! ----------------------------
                // TODO add implementation
                throw new UnsupportedOperationException("Not Implemented");
            }
            
        });
        
        return sum.getSum();
    }
    
    /**
     * Exercise 5
     * 
     * The previous exercise should have looked familiar. In fact it is the standard
     * Observable pattern as described by the Gang of Four [GoF]. However, this
     * standard pattern misses two important concepts. Two concepts that are present on
     * an Iterable.
     * 
     * Let's revisit Iterable. As we saw Iterable (by means of iterator) provides us with
     * a couple of scenarios:
     * 
     * next()    - to get the next element, 
     * hasNext() - to check if there are more elements and 
     * it can throw an exception if anything is wrong.
     * 
     * Thus far we've only seen the next() equivalent for Observables (onNext()), but we
     * still lack two.
     * 
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
    
    // Exercise onError
    // Exercise onCompleted
    
    // Timing
    // onNext called with time interval
    // zip
    // concatMap vs flatMap
    
    // Asynchronous
    
    // Throttling, etc
    
    
    /**
     * Return an Observable that emits a single value "Hello World!"
     * 
     * Make us of the Observable class and take a look at the method just() method;
     */
    public Observable<String> exerciseHello() {
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    

    

    /**
     * Flatten out all video in the stream of Movies into a stream of videoIDs
     * 
     * @param movieLists
     * @return Observable of Integers of Movies.videos.id
     */
    public Observable<Integer> exerciseConcatMap(Observable<Movies> movieLists) {
        return Observable.error(new RuntimeException("Not Implemented"));
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
     * @param movieLists
     * @return Observable of Integers of Movies.videos.id
     */
    public Observable<Integer> exerciseFlatMap(Observable<Movies> movieLists) {
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Retrieve the largest number.
     * 
     * Use reduce to select the maximum value in a list of numbers.
     */
    public Observable<Integer> exerciseReduce(Observable<Integer> nums) {
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Retrieve the id, title, and <b>smallest</b> box art url for every video.
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
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Combine 2 streams into pairs using zip.
     * 
     * a -> "one", "two", "red", "blue"
     * b -> "fish", "fish", "fish", "fish"
     * output -> "one fish", "two fish", "red fish", "blue fish"
     */
    public Observable<String> exerciseZip(Observable<String> a, Observable<String> b) {
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Don't modify any values in the stream but do handle the error
     * and replace it with "default-value".
     */
    public Observable<String> handleError(Observable<String> data) {
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * The data stream fails intermittently so return the stream
     * with retry capability.
     */
    public Observable<String> retry(Observable<String> data) {
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /*
     * An Observable is a lot like an Event. Like an Event, an Observable is a sequence 
     * of values that a data producer pushes to the consumer. However unlike an Event, 
     * an Observable can signal to a listener that it has completed, and will send no more data.
     *
     * Observables can send data to consumers asynchronously. Observables are a sequence 
     * of values, delivered one after the other. Therefore it's possible that an Observable 
     * can go on sending data to a listener forever just like an infinite stream.
     *			
     * Querying Lists only gives us a snapshot. By contrast, querying Observables allows
     * us to create data sets that react and update as the system changes over time. This 
     * enables a very powerful type of programming known as reactive programming.
     *
     * Let's start off by contrasting Observable with Events...
     */
    
    // This function can be used to build JSON objects within an expression
    private static JSON json(Object... keyOrValue) {
        JSON json = new JSON();

        for (int counter = 0; counter < keyOrValue.length; counter += 2) {
            json.put((String) keyOrValue[counter], keyOrValue[counter + 1]);
        }

        return json;
    }
    
    private static abstract class OnNext<T> extends Subscriber<T> {

        @Override
        public void onCompleted() {
            // NOOP
        }

        @Override
        public void onError(Throwable e) {
            // NOOP
        }

    }
    
    private static abstract class OnError<T> extends Subscriber<T> {

        @Override
        public void onCompleted() {
            // NOOP
        }

        @Override
        public void onNext(T t) {
            // NOOP
        }

        
    }
    
    private static class Sum {
        private int sum;
        
        public void increment(int withAmount) {
            sum += withAmount;
        }

        public int getSum() {
            return sum;
        }
        
    }
}

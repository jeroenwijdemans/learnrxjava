package learnrxjava.solutions;

import learnrxjava.exercises.ObservableExercises;
import learnrxjava.exercises.ObservableExercisesTest;
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

public class ObservableSolutionsTest extends ObservableExercisesTest {

    public ObservableExercises getImpl() {
        return new ObservableSolutions();
    }

}

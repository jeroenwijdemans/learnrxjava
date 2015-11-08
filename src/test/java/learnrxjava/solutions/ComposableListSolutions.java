package learnrxjava.solutions;

import learnrxjava.types.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class ComposableListSolutions<T> extends ArrayList<T> implements ComposableList<T> {
    private static final long serialVersionUID = 1L;

    // for
    public static void exercise1() {
        ComposableListSolutions<String> names = ComposableListSolutions.of("Ben", "Jafar", "Matt", "Priya", "Brian");

        for (String name : names) {
            System.out.println(name);
        }
    }

    // foreach
    public static void exercise2() {
        ComposableListSolutions<String> names = ComposableListSolutions.of("Ben", "Jafar", "Matt", "Priya", "Brian");

        names.forEach(name -> {
            System.out.println(name);
        });
    }

    // forEach
    public static ComposableList<JSON> exercise3() {
        ComposableListSolutions<Video> newReleases = ComposableListSolutions.of(
                new Video(70111470, "Die Hard", 4.0),
                new Video(654356453, "Bad Boys", 5.0),
                new Video(65432445, "The Chamber", 4.0),
                new Video(675465, "Fracture", 5.0));

        ComposableListSolutions<JSON> videoAndTitlePairs = new ComposableListSolutions<JSON>();
        
        newReleases.forEach(video -> {
           videoAndTitlePairs.add(json("id", video.id, "title", video.title));
        });
        
        return videoAndTitlePairs;
    }

    // map impl
    public <R> ComposableList<R> map(Function<T, R> projectionFunction) {
        ComposableListSolutions<R> results = new ComposableListSolutions<R>();
        this.forEach(itemInList -> {
            results.add(projectionFunction.apply(itemInList));
        });
        
        return results;
    }

    // map use
    public static ComposableList<JSON> exercise5() {
        ComposableListSolutions<Video> newReleases = ComposableListSolutions.of(
            new Video(70111470, "Die Hard", 4.0),
            new Video(654356453, "Bad Boys", 5.0),
            new Video(65432445, "The Chamber", 4.0),
            new Video(675465, "Fracture", 5.0));
         
        return newReleases.map(video -> {
           return json("id", video.id, "title", video.title); 
        });
    }

    // filter custom
    public static ComposableList<Video> exercise6() {
        ComposableListSolutions<Video> newReleases = ComposableListSolutions.of(
            new Video(
                    70111470,
                    "Die Hard",
                    4.0
            ),
            new Video(
                    654356453,
                    "Bad Boys",
                    5.0
            ),
            new Video(
                    65432445,
                    "The Chamber",
                    4.0
            ),
            new Video(
                    675465,
                    "Fracture",
                    5.0
            ));

        ComposableListSolutions<Video> highRatedVideos = new ComposableListSolutions<Video>();

        newReleases.forEach(video -> {
            if(video.rating == 5) {
                highRatedVideos.add(video);
            }
        });
        
        return highRatedVideos;
    }

    // filter impl
    public ComposableList<T> filter(Predicate<T> predicateFunction) {
        ComposableListSolutions<T> results = new ComposableListSolutions<T>();
        this.forEach(itemInList -> {
            if(predicateFunction.test(itemInList)) {
                results.add(itemInList);
            }
        });

        return results;
    }

    // filter use
    public static ComposableList<Integer> exercise8() {
        ComposableListSolutions<Video> newReleases
            = ComposableListSolutions.of(
                    new Video(
                            70111470,
                            "Die Hard",
                            4.0
                    ),
                    new Video(
                            654356453,
                            "Bad Boys",
                            5.0
                    ),
                    new Video(
                            65432445,
                            "The Chamber",
                            4.0
                    ),
                    new Video(
                            675465,
                            "Fracture",
                            5.0
                    ));

        return newReleases.filter(v -> v.rating == 5).map(v -> v.id);
    }

    // tree/nesting
    public static ComposableList<Integer> exercise9() {
        ComposableListSolutions<MovieList> movieLists = ComposableListSolutions.of(new MovieList(
                "New Releases",
                ComposableListSolutions.of(
                        new Video(70111470, "Die Hard", 4.0),
                        new Video(654356453, "Bad Boys", 5.0))),
            new MovieList(
                "Dramas",
                ComposableListSolutions.of(
                        new Video(65432445, "The Chamber", 4.0),
                        new Video(675465, "Fracture", 5.0))));

        ComposableListSolutions<Integer> allVideoIdsInMovieLists = new ComposableListSolutions<Integer>();

        movieLists.forEach(ml -> {
            ml.videos.forEach(v -> {
                allVideoIdsInMovieLists.add(v.id);
            });
        });

        return allVideoIdsInMovieLists;
    }

    // concatMap impl
    public <R> ComposableList<R> concatMap(
        Function<T, ComposableList<R>> projectionFunctionThatReturnsList) {
        ComposableListSolutions<R> results = new ComposableListSolutions<R>();
        for (T itemInList : this) {
            ComposableList<R> l = projectionFunctionThatReturnsList.apply(itemInList);
            l.forEach(r -> {
                results.add(r);
            });
        }
        
        return results;
    }

    // concatMap+map use
    public static ComposableList<Integer> exercise11() {
        ComposableListSolutions<MovieList> movieLists = ComposableListSolutions.of(
                new MovieList("New Releases", ComposableListSolutions.of( 
                        new Video(70111470, "Die Hard", 4.0),
                        new Video(654356453, "Bad Boys", 5.0))),
                new MovieList("Dramas",ComposableListSolutions.of(
                        new Video(65432445, "The Chamber", 4.0),
                        new Video(675465, "Fracture", 5.0))));

         return movieLists.
             concatMap(movieList -> 
                 movieList.videos.map(video -> video.id));
    }

    // complex!!
    public static ComposableList<JSON> exercise12() {
        ComposableListSolutions<MovieList> movieLists = ComposableListSolutions.of(new MovieList(
                "Instant Queue",
                ComposableListSolutions.of(new Video(
                        70111470,
                        "Die Hard",
                        5.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg")
                        )),
                    new Video(
                        654356453,
                        "Bad Boys",
                        4.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys150.jpg")
                        ))
                )
            ),
            new MovieList(
                "New Releases",
                ComposableListSolutions.of(new Video(
                        65432445,
                        "The Chamber",
                        4.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber150.jpg")
                        )),
                    new Video(
                        675465,
                        "Fracture",
                        5.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/Fracture150.jpg"),
                            new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
                        ))
                )
            )
        );

        // can be more compact - oneliner!
        return movieLists.concatMap(ml -> {
           return ml.videos.concatMap(v -> {
               ComposableList<BoxArt> boxart = v.boxarts.filter(ba -> ba.width == 150 && ba.height == 200);
               return boxart.map(ba -> {
                   return json("id", v.id, "title", v.title, "boxart", ba.url);                   
               });
           });
        });
    }

    // reduce custom
    // not in learnrxjs
    public static BoxArt exercise13() {
        ComposableListSolutions<BoxArt> boxarts = ComposableListSolutions.of(
            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/Fracture150.jpg"),
            new BoxArt(425, 150, "http://cdn-0.nflximg.com/images/2891/Fracture425.jpg"),
            new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
        );

        int currentSize = 0;
        int maxSize = -1;
        BoxArt largestBoxart = null;

        for (BoxArt boxart: boxarts) {
            currentSize = boxart.width * boxart.height;
            if (currentSize > maxSize) {
                largestBoxart = boxart;
                maxSize = currentSize;
            }
        }

        return largestBoxart;
    }

    // reduce impl
    public ComposableList<T> reduce(BiFunction<T, T, T> combiner) {
        int counter = 1;
        T accumulatedValue = null;

        if (this.size() == 0) {
            return this;
        } else {
            accumulatedValue = this.get(0);
            while (counter < this.size()) {
                accumulatedValue = combiner.apply(accumulatedValue, this.get(counter));
                counter++;
            }
            return ComposableListSolutions.of(accumulatedValue);
        }
    }

    // reduce with initial impl
    public <R> ComposableList<R> reduce(R initialValue, BiFunction<R, T, R> combiner) {
        int counter;
        R accumulatedValue;

        if (this.size() == 0) {
            return new ComposableListSolutions<R>();
        } else {
            counter = 0;
            accumulatedValue = initialValue;
            while (counter < this.size()) {
                accumulatedValue = combiner.apply(accumulatedValue, this.get(counter));
                counter++;
            }

            return ComposableListSolutions.of(accumulatedValue);
        }
    }

    // reduce use
    public static ComposableList<Integer> exercise16() {
        ComposableListSolutions<Integer> ratings = ComposableListSolutions.of(2, 3, 5, 1, 4);
        // onliner: ratings.reduce(Math::max)
        return ratings.reduce((max, item) -> {
            if(item > max) {
                return item;
            } else {
                return max;
            }
        });
    }

    // reduce+map use
    public static ComposableList<String> exercise17() {
        ComposableListSolutions<BoxArt> boxarts = ComposableListSolutions.of(
                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/Fracture150.jpg"),
                new BoxArt(425, 150, "http://cdn-0.nflximg.com/images/2891/Fracture425.jpg"),
                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
        );

        // onliner: boxarts.reduce((max, box) -> Math.max(max.size(), box.size())).map(boxart -> boxart.url)
        return boxarts.reduce((max, box) -> {
            int maxSize = max.height * max.width;
            int boxSize = box.height * box.width;
            if(boxSize > maxSize) {
                return box;
            } else {
                return max;
            }
        }).map(boxart -> boxart.url);
    }

    // reduce on list; unmodifiableMap is important!
    public static ComposableList<Map<Integer, String>> exercise18() {
        ComposableListSolutions<Video> videos = ComposableListSolutions.of(
            new Video(
                65432445,
                "The Chamber",
                5.0
            ),
            new Video(
                675465,
                "Fracture",
                4.0
            ),
            new Video(
                70111470,
                "Die Hard",
                5.0
            ),
            new Video(
                654356453,
                "Bad Boys",
                3.0
            )
        );

        return videos.
            reduce(
                Collections.unmodifiableMap(new HashMap<Integer, String>()),
                (accumulatedMap, video) -> {
                    Map<Integer, String> newMap = new HashMap<Integer, String>(accumulatedMap);
                    newMap.put(video.id, video.title);
                    return Collections.unmodifiableMap(newMap);
                });
    }

    // reduce+map use; comparable to ex.17 (but use Math::min!)
    public static ComposableList<JSON> exercise19() {
        ComposableListSolutions<MovieList> movieLists = ComposableListSolutions.of(new MovieList(
                    "New Releases",
                    ComposableListSolutions.of(new Video(
                            70111470,
                            "Die Hard",
                            4.0,
                            null,
                            ComposableListSolutions.of(
                                    new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"),
                                    new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/DieHard200.jpg")
                            )),
                        new Video(
                            654356453,
                            "Bad Boys",
                            5.0,
                            null,
                            ComposableListSolutions.of(
                                    new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                                    new BoxArt(140, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg")
                            ))
                    )
                ),
                new MovieList(
                    "Thrillers",
                    ComposableListSolutions.of(new Video(
                            65432445,
                            "The Chamber",
                            3.0,
                            null,
                            ComposableListSolutions.of(
                                new BoxArt(130, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"),
                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber200.jpg")
                            )),
                        new Video(
                            675465,
                            "Fracture",
                            4.0,
                            null,
                            ComposableListSolutions.of(
                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                                new BoxArt(120, 200, "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"),
                                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
                            ))
                    )
                )
        );

        // can be more compact
        return movieLists.
            concatMap(ml -> {
                // <JSON> needed? Lambda + generics = complex!
                return ml.videos.<JSON>concatMap(v -> {
                    return v.boxarts.reduce((max, box) -> {
                        int maxSize = max.height * max.width;
                        int boxSize = box.height * box.width;
                        if(boxSize < maxSize) {
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

    // zip custom
    public static ComposableList<JSON> exercise20() {
        ComposableListSolutions<Video> videos = ComposableListSolutions.of(
                new Video(
                        70111470,
                        "Die Hard",
                        4.0
                ),
                new Video(
                        654356453,
                        "Bad Boys",
                        5.0
                ),
                new Video(
                        65432445,
                        "The Chamber",
                        4.0
                ),
                new Video(
                        675465,
                        "Fracture",
                        5.0
                )
        );
        
        ComposableListSolutions<Bookmark> bookmarks = ComposableListSolutions.of(
                new Bookmark(470, 23432),
                new Bookmark(453, 234324),
                new Bookmark(445, 987834)
        );

        ComposableListSolutions<JSON> videoIdAndBookmarkIdPairs = new ComposableListSolutions<JSON>();

        for (int counter = 0; counter < Math.min(videos.size(), bookmarks.size()); counter++) {
            videoIdAndBookmarkIdPairs.add(json("videoId", videos.get(counter).id, "bookmarkId", bookmarks.get(counter).id));
        }

         return videoIdAndBookmarkIdPairs;
    }

    // zip impl
    public static <T0,T1,R> ComposableList<R> zip(ComposableList<T0> left, ComposableList<T1> right, BiFunction<T0,T1, R> combinerFunction) {
        ComposableListSolutions<R> results = new ComposableListSolutions<R>();

        for (int counter = 0; counter < Math.min(left.size(), right.size()); counter++) {
            // Add code here to apply the combinerFunction to the left and right-hand items in the 
            // respective lists, and add the result to the results List
            results.add(combinerFunction.apply(left.get(counter), right.get(counter)));
        }

        return results;
    }

    // zip use
    public static ComposableList<JSON> exercise22() {
        ComposableListSolutions<Video> videos = ComposableListSolutions.of(
                new Video(
                70111470,
                "Die Hard",
                4.0
            ),
            new Video(
                654356453,
                "Bad Boys",
                5.0
            ),
            new Video(
                65432445,
                "The Chamber",
                4.0
            ),
            new Video(
                675465,
                "Fracture",
                5.0
            )
        );
        
        ComposableListSolutions<Bookmark> bookmarks = ComposableListSolutions.of(
            new Bookmark(470, 23432),
            new Bookmark(453, 234324),
            new Bookmark(445, 987834)
        );

        return ComposableListSolutions.zip(
                videos.map(v -> v.id), 
                bookmarks.map(b -> b.id), 
                (v, b) -> json("videoId", v, "bookmarkId", b));
    }

    // comparable to ex.17/19
    // combines all: concatMap, reduce, filter, zip
    public static ComposableList<JSON> exercise23() {
        ComposableListSolutions<MovieList> movieLists = ComposableListSolutions.of(new MovieList(
                "New Releases",
                ComposableListSolutions.of(new Video(
                        70111470,
                        "Die Hard",
                        4.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"),
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/DieHard200.jpg")
                        ),
                        ComposableListSolutions.of(
                            new InterestingMoment("End", 213432),
                            new InterestingMoment("Start", 64534),
                            new InterestingMoment("Middle", 323133)
                        )
                    ),
                    new Video(
                        654356453,
                        "Bad Boys",
                        5.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                            new BoxArt(140, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg")
                        ),
                        ComposableListSolutions.of(
                            new InterestingMoment("End", 54654754),
                            new InterestingMoment("Middle", 6575665)
                        )
                    )
                )
            ),
            new MovieList(
                "Instant Queue",
                ComposableListSolutions.of(new Video(
                        65432445,
                        "The Chamber",
                        4.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(130, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"),
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber200.jpg")
                        ),
                        ComposableListSolutions.of(
                            new InterestingMoment("End", 132423),
                            new InterestingMoment("Start", 54637425),
                            new InterestingMoment("Middle", 3452343)
                        )
                    ),
                    new Video(
                        675465,
                        "Fracture",
                        5.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                            new BoxArt(120, 200, "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"),
                            new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
                        ),
                        ComposableListSolutions.of(
                            new InterestingMoment("End", 45632456),
                            new InterestingMoment("Start", 234534),
                            new InterestingMoment("Middle", 3453434)
                        )
                    )
                )
            )
        );

        // can be more compact
        return movieLists.
            concatMap(movieList -> {
                return movieList.videos.<JSON>concatMap(video -> {
                    ComposableList<BoxArt> smallestBoxArt = video.boxarts.reduce((smallest, box) -> {
                        int smallestSize = smallest.height * smallest.width;
                        int boxSize = box.height * box.width;
                        if(boxSize < smallestSize) {
                            return box;
                        } else {
                            return smallest;
                        }
                    });
                    
                    ComposableList<InterestingMoment> moment = video.interestingMoments.filter(m -> m.type == "Middle");
                    return ComposableListSolutions.zip(smallestBoxArt, moment, (s, m) -> {
                        return json("id", video.id, "title", video.title, "time", m.time, "url", s.url);                        
                    });
                });
            });
    }

    // This function can be used to build JSON objects within an expression
    private static JSON json(Object... keyOrValue) {
        JSON json = new JSON();

        for (int counter = 0; counter < keyOrValue.length; counter += 2) {
            json.put((String) keyOrValue[counter], keyOrValue[counter + 1]);
        }

        return json;
    }

    // Static list builder method to allow us to easily build lists
    @SafeVarargs
    public static <T> ComposableListSolutions<T> of(T... args) {
        ComposableListSolutions<T> results = new ComposableListSolutions<T>();
        for (T value : args) {
            results.add(value);
        }
        return results;
    }
    
    public static <T> ComposableListSolutions<T> of(List<T> list) {
        ComposableListSolutions<T> results = new ComposableListSolutions<T>();
        for (T value : list) {
            results.add(value);
        }
        return results;
    }
}

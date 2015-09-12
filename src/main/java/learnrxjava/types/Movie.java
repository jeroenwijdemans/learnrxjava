package learnrxjava.types;

import static java.util.Collections.emptyList;
import java.util.List;

import rx.Observable;

public class Movie {
    @Override
    public String toString() {
        return "Video{" + "id=" + id + ", title=" + title + ", rating=" + rating + ", bookmarks=" + _bookmarks + ", boxarts=" + _boxarts + ", interestingMoments=" + _interestingMoments + '}';
    }

    public int id;
    public String title;
    public double rating;
    public Observable<Bookmark> bookmarks;
    public Observable<BoxArt> boxarts;
    public Observable<InterestingMoment> interestingMoments;
    public Observable<String> topCast;
    public int minimalAge;
    
    private List<Bookmark> _bookmarks;
    private List<BoxArt> _boxarts;
    private List<InterestingMoment> _interestingMoments;
    private List<String> _topCast;
    
    public Movie(int id, String title, double rating) {
        this(id, title, rating, emptyList());
    }

    public Movie(int id, String title, double rating, int minimalAge) {
        this(id, title, rating, emptyList());
        this.minimalAge = minimalAge;
    }

    public Movie(int id, String title, double rating, List<InterestingMoment> interestingMoments) {
        this(id, title, rating, emptyList(), emptyList(), interestingMoments, emptyList());
    }

    public Movie(int id, String title, double rating, List<Bookmark> bookmarks, List<BoxArt> boxarts) {
        this(id, title, rating, bookmarks, boxarts, emptyList(), emptyList());
    }

    public Movie(int id, String title, double rating, List<Bookmark> bookmarks, List<BoxArt> boxarts, List<InterestingMoment> interestingMoments) {
        this(id, title, rating, bookmarks, boxarts, interestingMoments, emptyList());
    }
    
    public Movie(int id, String title, double rating, List<Bookmark> bookmarks, List<BoxArt> boxarts, List<InterestingMoment> interestingMoments, List<String> topCast) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.bookmarks = Observable.from(bookmarks);
        this.boxarts = Observable.from(boxarts);
        this.interestingMoments = Observable.from(interestingMoments);
        this.topCast = Observable.from(topCast);
        
        this._bookmarks = bookmarks;
        this._boxarts = boxarts;
        this._interestingMoments = interestingMoments;
        this._topCast = topCast;
    }
    
}
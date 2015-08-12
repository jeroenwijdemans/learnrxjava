package learnrxjava.examples;

import rx.Observable;
import rx.schedulers.Schedulers;

public class CodeVoorRemkoOmTeVerwerken {

    public static void main(String[] args) {
        // add concurrency (manually)
        Observable.create(subscriber -> {
            new Thread(() -> {
                try {
                    subscriber.onNext("Got Data!");
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }).start();
        }).subscribe(System.out::println);

        // add concurrency (using a Scheduler)
        Observable.create(subscriber -> {
            try {
                subscriber.onNext("Got Data!");
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(System.out::println);

        // infinite
        Observable.create(subscriber -> {
            int i = 0;
            while (!subscriber.isUnsubscribed()) {
                subscriber.onNext(i++);
            }
        }).take(10).subscribe(System.out::println);
    }

}

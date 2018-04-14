package com.example.tsl057.rxjavaplaygroundjava;

import android.util.Log;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void observable_rx_test() throws Exception {
        Observable<String> obs = Observable.range(1, 20).map(integer -> {
            Thread.sleep(200);
            return "" + integer;
        });

        final Disposable dis = obs
                .subscribeOn(Schedulers.io())
                .subscribe(string -> {
                    System.out.println(string);
                }, error -> System.out.println(error.getLocalizedMessage()));

        dis.dispose();
        Thread.sleep(5000);
    }

    @Test
    public void single_rx_test() throws Exception {
        Single<String> obs = Single.create(emitter -> {
            Thread.sleep(1000);
            if(!emitter.isDisposed()) {
                emitter.onSuccess("hello");
            }
        });

        final Disposable dis = obs
                .subscribeOn(Schedulers.io())
                .subscribe(string -> {
                    System.out.println(string);
                }, error -> System.out.println(error.getLocalizedMessage()));

        dis.dispose();
        Thread.sleep(1000);
    }

    @Test
    public void single_from_callable() throws Exception {
        Single<String> obs = Single.fromCallable(() -> "hello");

        final Disposable dis = obs
                .subscribeOn(Schedulers.io())
                .subscribe(string -> {
                    System.out.println(string);
                }, error -> System.out.println(error.getLocalizedMessage()));

        dis.dispose();
        Thread.sleep(1000);
    }
}
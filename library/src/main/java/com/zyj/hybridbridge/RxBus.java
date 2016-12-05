package com.zyj.hybridbridge;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * dec:
 * createBy yjzhao
 * createTime 2016/11/16 14:47
 */

public class RxBus {
    private final Subject<Object, Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance() {
        return RxBusInstance.rxBus;
    }

    private static final class RxBusInstance {

        static final RxBus rxBus = new RxBus();
    }


    public void post(Object o) {
        bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }


}

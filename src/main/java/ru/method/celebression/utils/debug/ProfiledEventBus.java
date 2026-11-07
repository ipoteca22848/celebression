package ru.method.celebression.utils.debug;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.ICancellable;
import meteordevelopment.orbit.IEventBus;
import meteordevelopment.orbit.listeners.IListener;
import meteordevelopment.orbit.listeners.LambdaListener;
import ru.method.celebression.Celebression;
import ru.method.celebression.utils.debug.FullFrameProfiler;

public final class ProfiledEventBus
implements IEventBus {
    private final EventBus delegate = new EventBus();

    public void registerLambdaFactory(String string, LambdaListener.Factory factory) {
        this.delegate.registerLambdaFactory(string, factory);
    }

    public <T> T post(T t) {
        return this.dispatch(t);
    }

    public <T extends ICancellable> T post(T t) {
        return this.dispatch(t);
    }

    private <T> T dispatch(T t) {
        if (ru.method.celebression.service.UnhookService.isActive()) return t;
        if (!FullFrameProfiler.isEnabled()) {
            return (T)this.delegate.post(t);
        }
        long l = FullFrameProfiler.startSection();
        Object object = this.delegate.post(t);
        this.finishPost(t, l);
        return (T)object;
    }

    public void subscribe(Object object) {
        this.delegate.subscribe(object);
        this.bindOwner(object);
    }

    public void subscribe(Class<?> clazz) {
        this.delegate.subscribe(clazz);
        this.bindOwner(clazz);
    }

    public void subscribe(IListener iListener) {
        this.delegate.subscribe(iListener);
        FullFrameProfiler.bindListener(iListener, iListener.getClass().getSimpleName());
    }

    public void unsubscribe(Object object) {
        this.delegate.unsubscribe(object);
    }

    public void unsubscribe(Class<?> clazz) {
        this.delegate.unsubscribe(clazz);
    }

    public void unsubscribe(IListener iListener) {
        this.delegate.unsubscribe(iListener);
    }

    public void bindExistingListeners() {
        try {
            Map<Object, List<IListener>> map = this.readInstanceCache();
            if (map != null) {
                for (Object owner : map.keySet()) {
                    this.bindOwner(owner);
                }
            }
            Map<Class<?>, List<IListener>> staticCache = this.readStaticCache();
            if (staticCache != null) {
                for (Class<?> clazz : staticCache.keySet()) {
                    this.bindOwner(clazz);
                }
            }
        }
        catch (ReflectiveOperationException ignored) {
        }
    }

    public static void bindAllFromInstance() {
        Celebression celebression = Celebression.getInstance();
        if (celebression == null) {
            return;
        }
        IEventBus iEventBus = celebression.getEventHandler();
        if (iEventBus instanceof ProfiledEventBus) {
            ProfiledEventBus profiledEventBus = (ProfiledEventBus)iEventBus;
            profiledEventBus.bindExistingListeners();
        }
    }

    private void finishPost(Object object, long l) {
        long l2 = System.nanoTime() - l;
        FullFrameProfiler.recordEventPost(object, l2);
        FullFrameProfiler.endSection("events.total", l);
    }

    private void bindOwner(Object object) {
        if (object == null) {
            return;
        }
        String string = object.getClass().getSimpleName();
        try {
            List<IListener> list = this.readInstanceCache().get(object);
            if (list == null) {
                return;
            }
            for (IListener iListener : list) {
                FullFrameProfiler.bindListener(iListener, string);
            }
        }
        catch (ReflectiveOperationException ignored) {
        }
    }

    private void bindOwner(Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        String string = clazz.getSimpleName();
        try {
            List<IListener> list = this.readStaticCache().get(clazz);
            if (list == null) {
                return;
            }
            for (IListener iListener : list) {
                FullFrameProfiler.bindListener(iListener, string);
            }
        }
        catch (ReflectiveOperationException ignored) {
        }
    }

    private Map<Object, List<IListener>> readInstanceCache() throws ReflectiveOperationException {
        Field field = EventBus.class.getDeclaredField("listenerCache");
        field.setAccessible(true);
        return (Map)field.get(this.delegate);
    }

    private Map<Class<?>, List<IListener>> readStaticCache() throws ReflectiveOperationException {
        Field field = EventBus.class.getDeclaredField("staticListenerCache");
        field.setAccessible(true);
        return (Map)field.get(this.delegate);
    }
}

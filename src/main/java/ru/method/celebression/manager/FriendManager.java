package ru.method.celebression.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Generated;

public class FriendManager {
    private final List<String> ni = new CopyOnWriteArrayList<String>();
    private final AtomicInteger nj = new AtomicInteger();

    public int er() {
        return this.nj.get();
    }

    public void bt(String string) {
        if (!string.isEmpty()) {
            this.ni.add(string);
            this.nj.incrementAndGet();
        }
    }

    public void bu(String string) {
        if (this.ni.remove(string)) {
            this.nj.incrementAndGet();
        }
    }

    public void clear() {
        if (this.ni.isEmpty()) {
            return;
        }
        this.ni.clear();
        this.nj.incrementAndGet();
    }

    public boolean av(String string) {
        return this.ni.contains(string);
    }

    public boolean bv(String string) {
        if (string == null) {
            return false;
        }
        for (String string2 : this.ni) {
            if (!string2.equalsIgnoreCase(string)) continue;
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return this.ni.isEmpty();
    }

    @Generated
    public List<String> es() {
        return this.ni;
    }
}


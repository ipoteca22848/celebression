package ru.method.celebression.ui;


public final class NotificationIcon {
    private final String akN;

    public NotificationIcon(String string) {
        this.akN = string;
    }

    @Override
    public final String toString() {
        return "NotificationIcon[" + "icon=" + this.akN + "]";
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(this.akN);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof NotificationIcon other)) return false;
        return java.util.Objects.deepEquals(this.akN, other.akN);
    }

    public String mN() {
        return this.akN;
    }
}


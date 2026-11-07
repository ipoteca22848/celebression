package ru.method.celebression.punchy.platform.services;

public interface IPlatformHelper {
    public String getPlatformName();

    public boolean isModLoaded(String var1);

    default public String getModDisplayName(String string) {
        return string;
    }

    public boolean isDevelopmentEnvironment();
}


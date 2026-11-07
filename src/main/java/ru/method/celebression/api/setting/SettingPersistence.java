package ru.method.celebression.api.setting;

import java.lang.reflect.Field;
import java.util.List;
import ru.method.celebression.Celebression;
import ru.method.celebression.api.setting.Setting;

public final class SettingPersistence {
    private SettingPersistence() {
    }

    public static void a(Object object, List<Setting<?>> list, String string) {
        if (object == null || list == null) {
            return;
        }
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!Setting.class.isAssignableFrom(field.getType())) continue;
            try {
                field.setAccessible(true);
                Setting setting = (Setting)field.get(object);
                if (setting == null || list.contains(setting)) continue;
                list.add(setting);
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                Celebression.LOGGER.warn("[Settings] Failed to read setting {}#{}", (Object)string, (Object)field.getName(), (Object)reflectiveOperationException);
            }
        }
    }
}


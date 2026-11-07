package ru.method.celebression.punchy.compat;

import ru.method.celebression.animation.Animation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import ru.method.celebression.punchy.platform.Services;

public final class EmfFirstPersonHandCompat {
    private static final String EMF_MOD_ID = "entity_model_features";
    private static final String EMF_SHORT_MOD_ID = "emf";
    private static final String ETF_MOD_ID = "entity_texture_features";
    private static final String ETF_SHORT_MOD_ID = "etf";
    private static final String CONTEXT_CLASS = "traben.entity_model_features.models.animation.EMFAnimationEntityContext";
    private static final String EMF_STATE_CLASS = "traben.entity_model_features.models.animation.state.EMFEntityRenderState";
    private static final String ETF_ENTITY_CLASS = "traben.entity_texture_features.utils.ETFEntity";
    private static final String ETF_STATE_CLASS = "traben.entity_texture_features.features.state.ETFEntityRenderState";
    private static boolean checked;
    private static Field firstPersonHandField;
    private static Method setCurrentEntityIterationMethod;
    private static Method resetMethod;
    private static Method etfForEntityMethod;

    private EmfFirstPersonHandCompat() {
    }

    public static Scope enter(Object object) {
        Field field = EmfFirstPersonHandCompat.getField();
        if (field == null) {
            return Scope.NOOP;
        }
        try {
            boolean bl = field.getBoolean(null);
            EmfFirstPersonHandCompat.setPlayerContext(object);
            field.setBoolean(null, true);
            return new Scope(field, bl);
        }
        catch (Throwable throwable) {
            return Scope.NOOP;
        }
    }

    private static Field getField() {
        if (checked) {
            return firstPersonHandField;
        }
        checked = true;
        if (Services.PLATFORM != null && (Services.PLATFORM.isModLoaded("entity_model_features") || Services.PLATFORM.isModLoaded("emf"))) {
            try {
                Class<?> clazz = Class.forName("traben.entity_model_features.models.animation.EMFAnimationEntityContext");
                Field field = clazz.getField("isFirstPersonHand");
                if (field.getType() == Boolean.TYPE) {
                    firstPersonHandField = field;
                }
                resetMethod = clazz.getMethod("reset", new Class[0]);
                Class<?> clazz2 = Class.forName("traben.entity_model_features.models.animation.state.EMFEntityRenderState");
                setCurrentEntityIterationMethod = clazz.getMethod("setCurrentEntityIteration", clazz2);
                if (Services.PLATFORM.isModLoaded("entity_texture_features") || Services.PLATFORM.isModLoaded("etf")) {
                    Class<?> clazz3 = Class.forName("traben.entity_texture_features.utils.ETFEntity");
                    Class<?> clazz4 = Class.forName("traben.entity_texture_features.features.state.ETFEntityRenderState");
                    etfForEntityMethod = clazz4.getMethod("forEntity", clazz3);
                }
            }
            catch (Throwable throwable) {
                firstPersonHandField = null;
                setCurrentEntityIterationMethod = null;
                resetMethod = null;
                etfForEntityMethod = null;
            }
            return firstPersonHandField;
        }
        return null;
    }

    private static void setPlayerContext(Object object) {
        if (object != null && setCurrentEntityIterationMethod != null && etfForEntityMethod != null) {
            try {
                Object object2 = etfForEntityMethod.invoke(null, object);
                if (object2 != null) {
                    setCurrentEntityIterationMethod.invoke(null, object2);
                }
            }
            catch (Throwable throwable) {
            }
        }
    }

    static void resetContext() {
        if (resetMethod != null) {
            try {
                resetMethod.invoke(null, new Object[0]);
            }
            catch (Throwable throwable) {
            }
        }
    }

    public static final class Scope
    implements AutoCloseable {
        static final Scope NOOP = new Scope(null, false);
        private final Field field;
        private final boolean previous;

        Scope(Field field, boolean bl) {
            this.field = field;
            this.previous = bl;
        }

        @Override
        public void close() {
            if (this.field != null) {
                try {
                    this.field.setBoolean(null, this.previous);
                }
                catch (Throwable throwable) {
                }
                EmfFirstPersonHandCompat.resetContext();
            }
        }
    }
}


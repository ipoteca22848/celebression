package ru.method.celebression.integration.ui.util.math.geometry;

import ru.method.celebression.integration.ui.util.math.geometry.Bound;

public enum Side {
    LEFT {
        @Override
        public void expand(Bound bound, float amount) {
            bound.x -= amount;
            bound.width += amount;
        }
    },
    RIGHT {
        @Override
        public void expand(Bound bound, float amount) {
            bound.width += amount;
        }
    },
    TOP {
        @Override
        public void expand(Bound bound, float amount) {
            bound.y -= amount;
            bound.height += amount;
        }
    },
    BOTTOM {
        @Override
        public void expand(Bound bound, float amount) {
            bound.height += amount;
        }
    },
    ALL {
        @Override
        public void expand(Bound bound, float amount) {
            bound.x -= amount;
            bound.y -= amount;
            bound.width += 2 * amount;
            bound.height += 2 * amount;
        }
    };

    public abstract void expand(Bound bound, float amount);
}
package cc.brainbook.compounddrawabledemo;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Handles compound drawable touch events of TextView or its derived classes.
 * Will intercept every event that happened inside (calculated) compound drawable bounds, extended by fuzz.
 *
 * https://gist.github.com/amaksoft/dbfb0fa827619dcb64b6a587efde34d9
 * https://code.i-harness.com/en/q/363c49
 */
@SuppressWarnings("WeakerAccess")
public abstract class CompoundDrawableTouchListener implements View.OnTouchListener {

    private final int fuzz;

    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    private static final int[] DRAWABLE_INDEXES = {LEFT, TOP, RIGHT, BOTTOM};

    /**
     * Default constructor
     */
    public CompoundDrawableTouchListener() {
        this(0);
    }

    /**
     * Constructor with fuzz
     *
     * @param fuzz desired fuzz in px
     */
    public CompoundDrawableTouchListener(int fuzz) {
        this.fuzz = fuzz;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (!(view instanceof TextView)) {
            return false;
        }

        final TextView textView = (TextView) view;
        final Drawable[] drawables = textView.getCompoundDrawables();
        int x = (int) event.getX();
        int y = (int) event.getY();

        for (int i : DRAWABLE_INDEXES) {
            if (drawables[i] == null) continue;
            final Rect bounds = getRelativeBounds(i, drawables[i], textView);
            final Rect fuzzedBounds = addFuzz(bounds);

            if (fuzzedBounds.contains(x, y)) {
                final MotionEvent relativeEvent = MotionEvent.obtain(
                    event.getDownTime(),
                    event.getEventTime(),
                    event.getAction(),
                    event.getX() - bounds.left,
                    event.getY() - bounds.top,
                    event.getMetaState());
                return onDrawableTouch(view, i, bounds, relativeEvent);
            }
        }

        return false;
    }

    /**
     * Calculates compound drawable bounds relative to wrapping view
     *
     * @param index compound drawable index
     * @param drawable the drawable
     * @param view wrapping view
     * @return {@link Rect} with relative bounds
     */
    private Rect getRelativeBounds(int index, @NonNull Drawable drawable, View view) {
        final Rect drawableBounds = drawable.getBounds();
        final Rect bounds = new Rect(drawableBounds);

        switch (index) {
            case LEFT:
                bounds.offsetTo(view.getPaddingLeft(),
                    view.getHeight() / 2 - bounds.height() / 2);
                break;

            case TOP:
                bounds.offsetTo(view.getWidth() / 2 - bounds.width() / 2,
                    view.getPaddingTop());
                break;

            case RIGHT:
                bounds.offsetTo(view.getWidth() - view.getPaddingRight() - bounds.width(),
                    view.getHeight() / 2 - bounds.height() / 2);
                break;

            case BOTTOM:
                bounds.offsetTo(view.getWidth() / 2 - bounds.width() / 2,
                    view.getHeight() - view.getPaddingBottom() - bounds.height());
                break;
        }

        return bounds;
    }

    /**
     * Expands {@link Rect} by given value in every direction relative to its center
     *
     * @param source given {@link Rect}
     * @return result {@link Rect}
     */
    private Rect addFuzz(Rect source) {
        final Rect result = new Rect();
        result.left = source.left - fuzz;
        result.right = source.right + fuzz;
        result.top = source.top - fuzz;
        result.bottom = source.bottom + fuzz;
        return result;
    }

    /**
     * Compound drawable touch-event handler
     *
     * @param v wrapping view
     * @param drawableIndex index of compound drawable which received the event
     * @param drawableBounds {@link Rect} with compound drawable bounds relative to wrapping view. Fuzz not included
     * @param event event with coordinated relative to wrapping view - i.e. within {@code drawableBounds}. If using fuzz, may return negative coordinates.
     */
    protected abstract boolean onDrawableTouch(View v, int drawableIndex, Rect drawableBounds, MotionEvent event);
}
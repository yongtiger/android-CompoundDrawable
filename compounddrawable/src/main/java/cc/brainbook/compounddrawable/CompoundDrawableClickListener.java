package cc.brainbook.compounddrawable;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Handles compound drawable click events.
 *
 * https://gist.github.com/amaksoft/dbfb0fa827619dcb64b6a587efde34d9
 * https://code.i-harness.com/en/q/363c49
 */
@SuppressWarnings("WeakerAccess")
public abstract class CompoundDrawableClickListener extends CompoundDrawableTouchListener {

    /**
     * Default constructor
     */
    public CompoundDrawableClickListener() {
        super();
    }

     /**
     * Constructor with fuzz
      *
     * @param fuzz desired fuzz in px
     */
    public CompoundDrawableClickListener(int fuzz) {
        super(fuzz);
    }

    @Override
    protected boolean onDrawableTouch(View v, int drawableIndex, Rect drawableBounds, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            onDrawableClick(v, drawableIndex);
        }
        return true; 
    }

    /**
     * Compound drawable touch-event handler
     *
     * @param v wrapping view
     * @param drawableIndex index of compound drawable which received the event
     */
    protected abstract void onDrawableClick(View v, int drawableIndex);
}
package solversteam.aveway.utiltes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

/**
 * Created by Ibrahim on 12/02/2017.
 */

public class IndicatedHorizontalScrollView extends HorizontalScrollView
{
    private static final int[] INDICATOR_ATTRS = {
            android.R.attr.drawableLeft,
            android.R.attr.drawableRight
    };

    private static final int[] PRESSED_STATE = {
            android.R.attr.state_pressed
    };

    private static final int LEFT        = 0;
    private static final int RIGHT       = 1;

    private final Drawable[] mIndicator  = new Drawable[2];
    private final boolean[]  mEnabled    = new boolean[2];
    private final Rect mContainer  = new Rect();
    private final Rect       mBounds     = new Rect();

    private boolean mPressed = false;


    public IndicatedHorizontalScrollView(Context context)
    {
        super(context);
        initView(context, null);
    }

    public IndicatedHorizontalScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView(context, attrs);
    }

    public IndicatedHorizontalScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs)
    {
        setWillNotDraw(false);
        TypedArray a = context.obtainStyledAttributes(attrs, INDICATOR_ATTRS);
        setIndicatorDrawable(LEFT,  a.getDrawable(LEFT));
        setIndicatorDrawable(RIGHT, a.getDrawable(RIGHT));
        a.recycle();
    }

    public void setIndicatorDrawable(int indicator, Drawable drawable)
    {
        Drawable old = mIndicator[indicator];
        if(old != null){
            old.setCallback(null);
        }
        mIndicator[indicator] = drawable;
        if(mEnabled[indicator] = (drawable != null)){
            drawable.setCallback(this);
            drawable.setState(null);
        }
    }

    protected void onClickIndicator(int indicator)
    {
        View child = findChildForScroll(indicator == LEFT ?
                View.FOCUS_LEFT : View.FOCUS_RIGHT);
        if(child != null){
            scrollToChild(child);
        }
        playSoundEffect(indicator == LEFT ?
                SoundEffectConstants.NAVIGATION_LEFT :
                SoundEffectConstants.NAVIGATION_RIGHT);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        updateIndicators();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        updateIndicators();
    }

    private void updateIndicators()
    {
        mEnabled[LEFT]  = (mIndicator[LEFT]  != null) && canScrollHorizontally(-1);
        mEnabled[RIGHT] = (mIndicator[RIGHT] != null) && canScrollHorizontally( 1);

        getDrawingRect(mContainer);

        if(mIndicator[LEFT] != null){
            Gravity.apply(Gravity.LEFT | Gravity.CENTER_VERTICAL,
                    mIndicator[LEFT].getIntrinsicWidth(),
                    mIndicator[LEFT].getIntrinsicHeight(),
                    mContainer, mBounds);
            mIndicator[LEFT].setBounds(mBounds);
        }

        if(mIndicator[RIGHT] != null){
            Gravity.apply(Gravity.RIGHT | Gravity.CENTER_VERTICAL,
                    mIndicator[RIGHT].getIntrinsicWidth(),
                    mIndicator[RIGHT].getIntrinsicHeight(),
                    mContainer, mBounds);
            mIndicator[RIGHT].setBounds(mBounds);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if(mPressed){
            return true;
        }

        if(ev.getActionMasked() == MotionEvent.ACTION_DOWN){
            final int x = (int)ev.getX() + getScrollX();
            final int y = (int)ev.getY() + getScrollY();
            if(inIndicatorBounds(LEFT, x, y) || inIndicatorBounds(RIGHT, x, y)){
                mPressed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if(mPressed){
            final int action = ev.getActionMasked();
            final int x = (int)ev.getX() + getScrollX();
            final int y = (int)ev.getY() + getScrollY();
            switch(action){
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    inIndicatorBounds(LEFT,  x, y);
                    inIndicatorBounds(RIGHT, x, y);
                    break;

                case MotionEvent.ACTION_UP:
                    if(inIndicatorBounds(LEFT, x, y)){
                        onClickIndicator(LEFT);
                    }
                    else if(inIndicatorBounds(RIGHT, x, y)){
                        onClickIndicator(RIGHT);
                    }
                    if(mIndicator[LEFT] != null && mIndicator[LEFT].setState(null)){
                        invalidate();
                    }
                    if(mIndicator[RIGHT] != null && mIndicator[RIGHT].setState(null)){
                        invalidate();
                    }
                    mPressed = false;
                    break;

                case MotionEvent.ACTION_CANCEL:
                    mPressed = false;
                    break;
            }
            return true;
        }

        return super.onTouchEvent(ev);
    }

    private boolean inIndicatorBounds(int indicator, int x, int y)
    {
        if(mEnabled[indicator]){
            boolean inBounds = mIndicator[indicator].getBounds().contains(x, y);
            if(mIndicator[indicator].setState(inBounds ? PRESSED_STATE : null)){
                invalidate();
            }
            return inBounds;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        if(mEnabled[LEFT]){
            mIndicator[LEFT].draw(canvas);
        }
        if(mEnabled[RIGHT]){
            mIndicator[RIGHT].draw(canvas);
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who)
    {
        if(who == mIndicator[LEFT] || who == mIndicator[RIGHT]){
            return true;
        }
        return super.verifyDrawable(who);
    }

    protected View findChildForScroll(int direction)
    {
        int pos = getScrollX() - getPaddingLeft();
        ViewGroup panel = ((ViewGroup)getChildAt(0));
        final int count = panel.getChildCount();
        if(direction == View.FOCUS_LEFT){
            for(int i = count - 1; i >= 0; i--){
                View child = panel.getChildAt(i);
                if(child.getLeft() < pos){
                    return child;
                }
            }
        }
        else if(direction == View.FOCUS_RIGHT){
            pos += getWidth();
            for(int i = 0; i < count; i++){
                View child = panel.getChildAt(i);
                if(child.getRight() > pos){
                    return child;
                }
            }
        }
        return null;
    }

    private void scrollToChild(View child)
    {
        child.getDrawingRect(mBounds);
        requestChildRectangleOnScreen(child, mBounds, false);
    }

    @Override
    public boolean canScrollHorizontally(int direction)
    {
        final int offset = computeHorizontalScrollOffset();
        final int range  = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
        if(range == 0){
            return false;
        }
        return (direction < 0) ? (offset > 0) : (offset < range - 1);
    }
}
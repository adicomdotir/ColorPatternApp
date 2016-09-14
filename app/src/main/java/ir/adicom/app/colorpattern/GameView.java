package ir.adicom.app.colorpattern;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by adicom on 9/14/16.
 */
public class GameView extends ImageView {
    int r = 0;
    int offset = 0;
    Paint paint;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        r = getWidth()/2;
        offset = 10;
        paint.setColor(Color.YELLOW);
        canvas.drawRect(offset,offset,r-offset,r-offset,paint);
        paint.setColor(Color.RED);
        canvas.drawRect(offset,r+offset,r-offset,r+r-offset,paint);
        paint.setColor(Color.BLUE);
        canvas.drawRect(r+offset,offset,r+r-offset,r-offset,paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(r+offset,r+offset,r+r-offset,r+r-offset,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (x > offset && y > offset && x < r - offset && y < r - offset) {
                    // Log.e("TAG", "Yellow");
                } else if (x > offset && y > r + offset && x < r - offset && y < r + r - offset) {
                    // Log.e("TAG", "Red");
                } else if (x > r + offset && y > offset && x < r + r - offset && y < r - offset) {
                    // Log.e("TAG", "Blue");
                } else if (x > r + offset && y > r + offset && x < r + r - offset && y < r + r - offset) {
                    // Log.e("TAG", "Green");
                }
                break;
            }
        }
        return true;
    }
}

package ir.adicom.app.colorpattern;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by adicom on 9/14/16.
 */
public class GameView extends ImageView {
    int r = 0;
    int offset = 0;
    Paint paint;
    int c1,c2,c3,c4;
    Context context;
    int pattern[] = {1,2,3,4,3,2,1};
    int myPattern[] = new int[10];
    int index = 0;
    boolean start = false;
    boolean gameWin = false;

    public GameView(Context context) {
        super(context);
        this.context = context;
        // init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        // init();
    }

    public void init() {
        setColor();
        final Handler mainHandler = new Handler(context.getMainLooper());
        final Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<pattern.length; i++) {
                    switch (pattern[i]) {
                        case 1:
                            c1 = Color.YELLOW;
                            mainHandler.post(myRunnable);
                            useTimer();
                            break;
                        case 2:
                            c2 = Color.RED;
                            mainHandler.post(myRunnable);
                            useTimer();
                            break;
                        case 3:
                            c3 = Color.BLUE;
                            mainHandler.post(myRunnable);
                            useTimer();
                            break;
                        case 4:
                            c4 = Color.GREEN;
                            mainHandler.post(myRunnable);
                            useTimer();
                            break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                start = true;
            }
        }).start();
    }

    private void setColor() {
        c1 = getResources().getColor(R.color.darkYellow);
        c2 = getResources().getColor(R.color.darkRed);
        c3 = getResources().getColor(R.color.darkBlue);
        c4 = getResources().getColor(R.color.darkGreen);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        r = getWidth()/2;
        offset = 10;
        paint.setColor(c1);
        canvas.drawRect(offset,offset,r-offset,r-offset,paint);
        paint.setColor(c2);
        canvas.drawRect(offset,r+offset,r-offset,r+r-offset,paint);
        paint.setColor(c3);
        canvas.drawRect(r+offset,offset,r+r-offset,r-offset,paint);
        paint.setColor(c4);
        canvas.drawRect(r+offset,r+offset,r+r-offset,r+r-offset,paint);
        if(index >= pattern.length) {
            gameWin = true;
            for (int i=0; i<pattern.length; i++) {
                if(pattern[i] != myPattern[i])
                    gameWin = false;
            }
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(50);
            if (gameWin)
                canvas.drawText("You Win",0,500,p);
            else
                canvas.drawText("You Lose",0,500,p);;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (start && index<pattern.length) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    if (x > offset && y > offset && x < r - offset && y < r - offset) {
                        myPattern[index] = 1;
                        index++;
                        c1 = Color.YELLOW;
                        useTimer();
                        // Log.e("TAG", "Yellow");
                    } else if (x > offset && y > r + offset && x < r - offset && y < r + r - offset) {
                        myPattern[index] = 2;
                        index++;
                        c2 = Color.RED;
                        useTimer();
                        // Log.e("TAG", "Red");
                    } else if (x > r + offset && y > offset && x < r + r - offset && y < r - offset) {
                        myPattern[index] = 3;
                        index++;
                        c3 = Color.BLUE;
                        useTimer();
                        // Log.e("TAG", "Blue");
                    } else if (x > r + offset && y > r + offset && x < r + r - offset && y < r + r - offset) {
                        myPattern[index] = 4;
                        index++;
                        c4 = Color.GREEN;
                        useTimer();
                        // Log.e("TAG", "Green");
                    }
                    invalidate();
                    break;
                }
            }
        }
        return true;
    }

    public void useTimer() {
        final Handler mainHandler = new Handler(context.getMainLooper());
        final Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                setColor();
                invalidate();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mainHandler.post(myRunnable);
            }
        }).start();
    }
}

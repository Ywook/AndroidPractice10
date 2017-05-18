package com.examples.androidpractice10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mac on 2017. 5. 18..
 */

public class MyCanvas extends View {
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mPaint = new Paint();

    Boolean stamp = false, rotate = false, move = false, scale = false, skew = false;
    String operationType = "";
    public MyCanvas(Context context) {
        super(context);
        mPaint.setColor(Color.BLACK);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.BLACK);

    }

    public void setStemp(Boolean b){
        this.stamp = b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mBitmap!= null){
            canvas.drawBitmap(mBitmap,0,0,mPaint);

            if(operationType.equalsIgnoreCase("eraser")){
                clear();
            }else if(operationType.equalsIgnoreCase("open")){

            }else if(operationType.equalsIgnoreCase("save")){

            }else if(operationType.equalsIgnoreCase("rotate")){
                rotate =true;
            }else if(operationType.equalsIgnoreCase("move")){
                move = true;
            }else if(operationType.equalsIgnoreCase("scale")){
                scale = true;
            }else if(operationType.equalsIgnoreCase("skew")){
                skew = true;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.YELLOW);
    }

    int oldX = -1, oldY = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        if(stamp){
            if(event.getAction() == event.ACTION_DOWN){
                drawStamp(x,y);
            }
        }else{
            if(event.getAction() == event.ACTION_DOWN){
                oldX = x; oldY = y;
            }else if(event.getAction() == event.ACTION_MOVE){
                if(oldX != -1){
                    mCanvas.drawLine(oldX, oldY , x, y, mPaint);
                    invalidate();
                    oldX = x; oldY = y;
                }

            }else if(event.getAction() == event.ACTION_UP){
                if(oldX != -1){
                    mCanvas.drawLine(oldX, oldY , x, y, mPaint);
                    invalidate();
                }
                oldX = -1; oldY = -1;
            }

        }
        return true;
    }

    public void setOperationType(String operationType){
        this.operationType = operationType;
        invalidate();
    }

    public void drawStamp(int x, int y){
        Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        if(rotate) mCanvas.rotate(30, getWidth()/2, getHeight()/2);
        if(move) {
            x += 10;
            y += 10;
        }
        if(scale);
        if(skew);

        mCanvas.drawBitmap(img, x, y, mPaint);
        img.recycle();
        invalidate();

    }

    private void clear(){
        mBitmap.eraseColor(Color.WHITE);
        invalidate();
    }
}

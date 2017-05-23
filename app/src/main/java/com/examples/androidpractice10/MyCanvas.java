package com.examples.androidpractice10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Mac on 2017. 5. 18..
 */

public class MyCanvas extends View {
    Bitmap resize =null;
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mPaint = new Paint();
    Paint paint = new Paint();

    Boolean stamp = false, rotate = false, move = false, scale = false, skew = false;
    String operationType = "";

    public MyCanvas(Context context) {
        super(context);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint.setColor(Color.BLACK);
        paint.setColor(Color.BLACK);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint.setColor(Color.BLACK);
        paint.setColor(Color.BLACK);

    }

    public void setStemp(Boolean b) {
        this.stamp = b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            if (operationType.equalsIgnoreCase("eraser")) {
                clear();
            } else if(operationType.equalsIgnoreCase("open")){
                canvas.drawBitmap(resize,(getWidth()-resize.getWidth())/2, (getHeight() - resize.getHeight())/2, paint);
            }
            else if (operationType.equalsIgnoreCase("rotate")) {
                rotate = true;
            } else if (operationType.equalsIgnoreCase("move")) {
                move = true;
            } else if (operationType.equalsIgnoreCase("scale")) {
                scale = true;
            } else if (operationType.equalsIgnoreCase("skew")) {
                skew = true;
            }
            canvas.drawBitmap(mBitmap, 0, 0, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.YELLOW);
        mCanvas.save();
    }

    int oldX = -1, oldY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (stamp) {
            if (event.getAction() == event.ACTION_DOWN) {
                drawStamp(x, y);
            }
        } else {
            if (event.getAction() == event.ACTION_DOWN) {
                oldX = x;
                oldY = y;
            } else if (event.getAction() == event.ACTION_MOVE) {
                if (oldX != -1) {
                    mCanvas.drawLine(oldX, oldY, x, y, paint);
                    invalidate();
                    oldX = x;
                    oldY = y;
                }

            } else if (event.getAction() == event.ACTION_UP) {
                if (oldX != -1) {
                    mCanvas.drawLine(oldX, oldY, x, y, paint);
                    invalidate();
                }
                oldX = -1;
                oldY = -1;
            }

        }
        return true;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
        invalidate();
    }

    public void drawStamp(int x, int y) {
        Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        if (rotate) {
            mCanvas.rotate(30, getWidth() / 2, getHeight() / 2);
        }
        if (move) {
            x += 10;
            y += 10;
        }
        if (scale){
            Bitmap bigBitmap = Bitmap.createScaledBitmap(img, (int)(img.getWidth()*1.5), (int)(img.getHeight()*1.5), false);
            img = bigBitmap;
        }
        if (skew){
            mCanvas.skew(0.2f,0);
        }

        mCanvas.drawBitmap(img, x, y, mPaint);
        img.recycle();
        invalidate();

    }

    private void clear() {
        mBitmap.eraseColor(Color.WHITE);
        mCanvas.restore();
        mCanvas.save();
        stamp = false;
        rotate = false;
        move = false;
        scale = false;
        skew = false;
        this.setOperationType("");
    }

    public void open(String file_name, Context context) {
        File file = new File(file_name);
        if (!file.exists()) {
            Toast.makeText(context.getApplicationContext(), "저장된 파일이 없습니다.", Toast.LENGTH_SHORT).show();
            return ;
        }
        clear();
        Bitmap bitmap = BitmapFactory.decodeFile(file_name);
        resize = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/2, bitmap.getHeight()/2, false);
        mCanvas.drawBitmap(resize,(getWidth()-resize.getWidth())/2, (getHeight() - resize.getHeight())/2, mPaint);
        this.invalidate();
        Toast.makeText(context.getApplicationContext(), "불러오기 완료", Toast.LENGTH_SHORT).show();
    }

    public void setPenColor(Boolean b){
        if(b) paint.setColor(Color.RED);
        else paint.setColor(Color.BLUE);
    }

    public void setPenWidth(boolean b){
        if(b) paint.setStrokeWidth(5);
        else paint.setStrokeWidth(3);
    }

    public void setMaskFilter(Boolean b){
        if(b){
            if(stamp == true){
                BlurMaskFilter blur = new BlurMaskFilter(100,
                        BlurMaskFilter.Blur.INNER);
                mPaint.setMaskFilter(blur);
            }
        }else{
            mPaint.setMaskFilter(null);
        }
        this.invalidate();
    }

    public boolean save(String file_name) {
        try {
            FileOutputStream out = new FileOutputStream(file_name, false);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return false;
    }
}

package com.example.edoardo.intrablet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Edoardo on 18/05/2015.
 */
public class FirmaView extends View {
    public static int rwt = 209;
    public static int rht = 259;
    public static int wt = 456;
    public static int ht = 563;
    public static int firmax = 228;
    public static int firmay = 50;
    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private Canvas drawCanvas;
    private String filePath;

    public Bitmap getCanvasBitmap() {
        return canvasBitmap;
    }

    public void setCanvasBitmap(Bitmap canvasBitmap) {
        this.canvasBitmap = canvasBitmap;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    private Bitmap canvasBitmap;
    private int paintcolor = Color.parseColor("#000000");

    public FirmaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    public void delete(){
        drawCanvas.drawColor(Color.WHITE);
        invalidate();
    }


    public void firma(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        drawCanvas.drawText(sdf.format(new Date()),firmax,firmay,paint);
    }

    public void setupDrawing(){
        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(paintcolor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);


        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        if(filePath != null && !filePath.equals("")){
            File readFile = new File(filePath);
            if(readFile.exists()){
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap prova = BitmapFactory.decodeFile(filePath, option);

                canvasBitmap = Bitmap.createScaledBitmap(prova, ht, wt, true);
                // ruoto
                canvasBitmap = rotateImage(canvasBitmap, 90);

            }
        }

        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0,0, canvasPaint);
        canvas.drawPath(drawPath,drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public static  Bitmap rotateImage(Bitmap src, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }
}

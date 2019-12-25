package com.mactrical.mindoter.monitorView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by CDL-DTY3 on 09/09/17.
 */

public class GraphView extends View {
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */

    private Paint mgraphcolor;

    private int mgraphWidth;
    private int mgraphHeight;


    int nYAxis;
    int nXAxis1 = 0;
    int nXAxis2 = 1;

    private Bitmap canvasBitMap = null;
    private Canvas bitmapCanvas = null;

    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mgraphcolor = new Paint();
        mgraphcolor.setColor(Color.GREEN);
        mgraphcolor.setStrokeWidth(1);
        mgraphcolor.setDither(true);
        mgraphcolor.setStyle(Paint.Style.STROKE);
        mgraphcolor.setStrokeJoin(Paint.Join.ROUND);
        mgraphcolor.setStrokeCap(Paint.Cap.ROUND);
        mgraphcolor.setAntiAlias(true);
        mgraphcolor.setShadowLayer(20,13,0,Color.argb(250,0,150,0));

        setLayerType(LAYER_TYPE_SOFTWARE, mgraphcolor);
        //mgraphcolor.setAlpha(255);

        startTime();



    }

    public GraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        mgraphWidth     = contentWidth;
        mgraphHeight    = contentHeight;

        if(canvasBitMap == null)
        canvasBitMap = Bitmap.createBitmap(mgraphWidth, mgraphHeight, Bitmap.Config.ARGB_8888);

        if(bitmapCanvas == null)
            bitmapCanvas = new Canvas(canvasBitMap);

        canvas.drawBitmap(canvasBitMap,0,0,mgraphcolor);

        drawGraph(bitmapCanvas);
    }

    private void drawGraph(Canvas mGraphCanvas){


        nYAxis++;
        if(nYAxis > 100){
            nYAxis = 0;
        }

        nXAxis1++;
        nXAxis2++;

        if(nXAxis2 > mgraphWidth){
            nXAxis1 = 0;
            nXAxis2 = 1;
            canvasBitMap.eraseColor(Color.TRANSPARENT);
        }
        mGraphCanvas.drawLine(nXAxis1,nYAxis+mgraphHeight/2,nXAxis2,nYAxis+mgraphHeight/2,mgraphcolor);
    }


    private void startTime(){
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                       postInvalidate();
                    }
                }, 0, 40, TimeUnit.MILLISECONDS);
    }
}
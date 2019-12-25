package com.mactrical.mindoter.monitorView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mactrical.mindoter.R;

/**
 * TODO: document your custom view class.
 */
public class GridView extends View {

    private Paint mgridpaint;
    private Paint msmallgridPaint;

    private int mGridViewWidth;
    private int mGridViewHeight;

    private Canvas mGridCanvas;


    public GridView(Context context){
        super(context);
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public GridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        mgridpaint = new Paint();
        mgridpaint.setColor(Color.rgb(0,100,0));
        mgridpaint.setAlpha(120);


        msmallgridPaint = new Paint();
        msmallgridPaint.setColor(Color.rgb(0,100,0));
        msmallgridPaint.setAlpha(0);


        mGridCanvas = canvas;

        mGridViewWidth  = contentWidth;
        mGridViewHeight = contentHeight;

       drawGrid();
    }


    private void drawGrid(){

        /* Vertical Lines */
        for(int nxAxis = 0 ; nxAxis < mGridViewWidth; ){

            mGridCanvas.drawLine(nxAxis,0,nxAxis,mGridViewHeight,mgridpaint);

            nxAxis+=25;
        }

        /* Horizontal Lines */

        for(int nxAxis = 0 ; nxAxis < mGridViewHeight; ){

            mGridCanvas.drawLine(0,nxAxis,mGridViewWidth,nxAxis,mgridpaint);

            nxAxis+=25;
        }

//        /* Vertical Lines */
//        for(int nxAxis = 0 ; nxAxis < mGridViewWidth; ){
//
//            mGridCanvas.drawLine(nxAxis,0,nxAxis,mGridViewHeight,mgridpaint);
//
//            nxAxis+=5;
//        }
//
//        /* Horizontal Lines */
//
//        for(int nxAxis = 0 ; nxAxis < mGridViewHeight; ){
//
//            mGridCanvas.drawLine(0,nxAxis,mGridViewWidth,nxAxis,mgridpaint);
//
//            nxAxis+=5;
//        }

    }

}

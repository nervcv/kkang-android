package com.example.heo.step12application;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CustomView extends View {

    Context context;

    int value;
    int textColor;

    // 화면 출력 이미지
    Bitmap plusBitmap;
    Bitmap minusBitmap;

    // 이미지가 화면에 출력되는 좌표 정보
    Rect plusRectDst;
    Rect minusRectDst;

    ArrayList<OnMyChangeListener> listeners;

    public CustomView(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    // 생성자의 공통 코드
    private void init(AttributeSet attrs) {

        plusBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.plus);
        minusBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.minus);

        plusRectDst=new Rect(10, 10, 210, 210);
        minusRectDst=new Rect(400, 10, 600, 210);

        if(attrs!=null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
            textColor = typedArray.getColor(R.styleable.CustomView_customColor, Color.RED);
        }

        listeners=new ArrayList<>();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(attrs);
        /*
            this.context = context;

            if(attrs!=null){
                TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
                textColor = typedArray.getColor(R.styleable.CustomView_customColor, Color.RED);
            }


            for (int i = 0; i<attrs.getAttributeCount(); i++){
                attributes[i] = attrs.getAttributeName(i) + " = " + attrs.getAttributeValue(i);
            }
        */
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
            canvas.drawColor(Color.alpha(Color.CYAN));

            RectF rectF = new RectF(15, 15, 160, 160);
            Paint paint = new Paint();

            paint.setAntiAlias(true);
            // paint.setColor(Color.RED);
            paint.setColor(textColor);

            canvas.drawArc(rectF, 0, 360, false, paint);
        */

        // 화면 지우기
        canvas.drawColor(Color.alpha(Color.CYAN));

        // 이미지의 사각형 정보
        Rect plusRectSource=new Rect(0,0, plusBitmap.getWidth(), plusBitmap.getHeight());
        Rect minusRectSource=new Rect(0,0,minusBitmap.getWidth(), minusBitmap.getHeight());

        Paint paint=new Paint();

        // plus 이미지 그리기
        canvas.drawBitmap(plusBitmap, plusRectSource, plusRectDst, null);

        // value 문자열 그리기
        paint.setTextSize(80);
        paint.setColor(textColor);
        canvas.drawText(String.valueOf(value), 280, 150, paint);

        // minus 이미지 그리기
        canvas.drawBitmap(minusBitmap, minusRectSource, minusRectDst, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // setMeasuredDimension(500, 500);

        // 레이아웃 XML 파일의 크기 설정을 참조해서 뷰 자신의 크기를 작절하게 판단

        // MeasureSpec.AT_MOST : 뷰 내부에서 지정하라는 의미, 레이아웃 XML에서 wrap_content 로 선언한 경우
        // MeasureSpec.EXACTLY : 뷰를 이용하는 액티비티 쪽에서 크기를 결정한 경우, match_parent, 100px 로 선언한 경우
        // MeasureSpec.UNSPECIFIED : 모드가 설정되지 않았을 경우

        int width = 0;
        int height = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            width = 700;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            height = 250;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }

        setMeasuredDimension(width, height);
    }

    // Observer 등록을 위한 함수, 뷰 내부에서 데이터가 발생할 때 매개변수로 등록한 객체의 함수를 호출해서 전달
    public void setOnMyChangeListener(OnMyChangeListener listener){
        listeners.add(listener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        // 플러스 아이콘이 터치되면
        if (plusRectDst.contains(x, y) && event.getAction() == MotionEvent.ACTION_DOWN) {
            // 데이터 변경
            value++;
            // 화면 갱신
            invalidate();
            for (OnMyChangeListener listener : listeners) {
                // observer 데이터 전달, 해당 이벤트를 등록한 객체의 함수를 호출
                listener.onChange(value);
            }
            return true;
        } else if (minusRectDst.contains(x, y) && event.getAction() == MotionEvent.ACTION_DOWN) {
            value--;

            invalidate(); // invalidate() 함수만 호출하면 내부적으로 onDraw() 함수가 다시 호출되어 변경된 값으로 다시 그리게 됩니다.

            for (OnMyChangeListener listener : listeners) {
                listener.onChange(value);
            }
            return true;
        }

        return false;
    }
}

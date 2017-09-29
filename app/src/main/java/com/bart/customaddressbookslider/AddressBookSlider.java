package com.bart.customaddressbookslider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：${Bart} on 2017/9/29 11:25
 * 邮箱：botao_zheng@163.com
 */

public class AddressBookSlider extends View{

    // 用户选中的位置,没点击时为-1
    private int index = -1;
    private int width;
    private int height;

    // 字母宽高及点击/没点击状态下的画笔
    private float textWidth;
    private float textHeight;
    private Paint mTextPaint;
    private Paint mSelectedTextPaint;

    private OnItemSelectedListener mListener;

    private String[] mTextArray = {
            "#", "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "G",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    public AddressBookSlider(Context context) {
        super(context);
    }

    public AddressBookSlider(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    // 测量过程
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*
        getDefaultSize():
        返回一个默认的值，如果MeasureSpec没有强制限制的话则使用提供的大小.
        否则在允许范围内可任意指定大小.
        第一个参数size为提供的默认大小，第二个参数为测量的大小
         */
        width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        setMeasuredDimension(width,height);

        // View的高度除以27得到每个字母的高度，-2以让字母间有一定间隙
        textHeight = height/27f - 2;
        mTextPaint.setTextSize(textHeight);
        mSelectedTextPaint.setTextSize(textHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 取W的宽度为字母宽度
        textWidth = mTextPaint.measureText("W");

        for(int i = 0;i < 27;i++){
            // 如果是选中的文字，显示红色
            if(index == i){
                canvas.drawText(""+ mTextArray[i],width-textWidth,height/27*(i+1),mSelectedTextPaint);
            }else{
                canvas.drawText(""+ mTextArray[i],width-textWidth,height/27*(i+1),mTextPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                // 获得事件坐标
                float x= event.getX();
                float y = event.getY();
                // 如果焦点在控件范围内，2 * textWidth是为了提高体验，因为如果点击范围
                // 只是字母大小，准确点击到对应字母存在一定的难度，所以扩大一倍点击范围
                if(x >= (width - 2 * textWidth)){
                    index = (int) (y/(height/27));
                    if(mListener != null){
                        // 如果设置了监听器，也就是传入了OnItemSelectedListener接口的对象，
                        // 那么就利用该对象去调接口方法，将index以及被选中的字母信息回调给用户，
                        // 用户就可以在回调方法中使用index以及mTextArray[index]
                        mListener.onItemSelected(index, mTextArray[index]);
                    }
                    // 刷新，重新onDraw（）
                    invalidate();
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                index = -1;
                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }

    // 获得接口对象的方法
    public void setOnItemSelectedListener(OnItemSelectedListener listener){
        this.mListener = listener;
    }

    private void initPaint() {

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mSelectedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectedTextPaint.setColor(Color.RED);
        mSelectedTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int index,String selectedText);
    }

}

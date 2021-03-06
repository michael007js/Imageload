package com.sss.imageload.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 这个组件存在的意义在于为了兼容现在与未来将出现的各大框架，目前来讲仅仅就是为了兼容Fresco，顺带加上了触摸变色的效果。
 * 与其他图片加载框架不一样，Fresco使用的是自家的组件DraweeView，但是DraweeView的顶层继承的也是ImageView，所以就目前来讲继承SimpleDraweeView可以通杀市面上所有的框架。
 * 但是非死不可表示了未来有可能会替换掉DraweeView，所以为了长远考虑就搞了这个类，随便他怎么搞，如果后续升级了框架，无需修改项目中的XML了，只要在这里换一个继承其他什么都不用干就可以实现无缝切换。
 * 当然，也是为了防止以后有其他的框架跟Fresco一个尿性，使用其他乱七八糟的组件。
 * Created by Administrator on 2018/7/16.
 */

public class ImageloadView extends SimpleDraweeView {
    private boolean enableChangedColor;//触摸变色

    public ImageloadView setEnableChangedColor(boolean enableChangedColor) {
        this.enableChangedColor = enableChangedColor;
        return this;
    }

    public ImageloadView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ImageloadView(Context context) {
        super(context);
    }

    public ImageloadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageloadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ImageloadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (enableChangedColor) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Drawable drawable = getDrawable();
                    if (drawable != null) {
                        drawable.mutate().setColorFilter(Color.LTGRAY,PorterDuff.Mode.MULTIPLY);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    Drawable drawableUp = getDrawable();
                    if (drawableUp != null) {
                        drawableUp.mutate().clearColorFilter();
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);


    }
}

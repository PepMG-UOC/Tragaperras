package com.example.tragaperras.ImageViewScrolling;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tragaperras.R;

public class ImageViewScrolling extends FrameLayout {

    private static int ANIMATION_DUR =150;
    ImageView current_image,next_image;

    int last_result=0,old_value=0;

    IEventEnd eventEnd;

    public void setEventEnd(IEventEnd eventEnd) {
        this.eventEnd = eventEnd;
    }

    public ImageViewScrolling( Context context) {
        super(context);
        init(context);
    }

    public ImageViewScrolling( Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.image_view_scrolling,this);
        current_image = (ImageView) getRootView().findViewById(R.id.current_image);
        next_image = (ImageView) getRootView().findViewById(R.id.next_image);

        next_image.setTranslationY(getHeight());
    }

    public void setValorRandom(int image, int rotate_count){
        current_image.animate().translationY(-getHeight()).setDuration(ANIMATION_DUR).start();
        next_image.setTranslationY(next_image.getHeight());
        next_image.animate().translationY(0)
                .setDuration(ANIMATION_DUR)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        setImage(current_image,old_value%6);  //Ponemos la cantidad de imagenes que queremos
                        current_image.setTranslationY(0);
                        if(old_value!=rotate_count) {
                            setValorRandom(image,rotate_count);
                            old_value++;
                        }
                        else {
                            last_result=0;
                            old_value=0;
                            setImage(next_image,image);
                            eventEnd.eventEnd(image%6,rotate_count);
                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                });
    }

    private void setImage(ImageView image_view, int value) {
        if(value== Util.CANDY1)
            image_view.setImageResource(R.drawable.dragon1);
        else if(value== Util.CANDY2)
            image_view.setImageResource(R.drawable.dragon2);
        else if(value== Util.CANDY3)
            image_view.setImageResource(R.drawable.dragon3);
        else if(value== Util.CANDY4)
            image_view.setImageResource(R.drawable.dragon4);
        else if(value== Util.CANDY5)
            image_view.setImageResource(R.drawable.dragon5);
        else
            image_view.setImageResource(R.drawable.dragon6);

        image_view.setTag(value);
        last_result=value;
    }

    public int getValue() {
        return Integer.parseInt(next_image.getTag().toString());
    }
}

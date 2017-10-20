package com.tgd.slick_mobile;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Crée par Jerome le 31/03/2017 pour le projet TestAndroidSlick
 */

public class FadeOutTransition extends Animation {

    public FadeOutTransition(){
        setDuration(600);
        setBackgroundColor(android.graphics.Color.BLACK);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        t.setAlpha(1-interpolatedTime);

    }
}

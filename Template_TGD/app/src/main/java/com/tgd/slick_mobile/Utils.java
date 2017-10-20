package com.tgd.slick_mobile;

import android.util.TypedValue;

/**
 * Crée par Jerome le 31/03/2017 pour le projet TestAndroidSlick
 */

public class Utils {

    public static float factorX;
    public static float factorY;
    public static float diag;
    public static float density;

    public static float SPtoPX(float size) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,size,BasicGameState.currentContext().getResources().getDisplayMetrics());
    }

    public static float DPtoPX(float size) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,size,BasicGameState.currentContext().getResources().getDisplayMetrics());

    }
}

package com.tgd.slick_mobile;

import android.content.Context;
import android.graphics.Typeface;

import com.tgd.tgd_game.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Crée par Jerome le 03/10/2017 pour le projet TGD_GAME
 */

public class ResourceLoader {

    private static HashMap<String, Typeface> listFont =new HashMap<>();

    public static Typeface getResourceAsStream(String path) throws FileNotFoundException {
        String name = path.substring(path.lastIndexOf("/")+1);
        return  getFont(BasicGameState.currentContext(),name);
    }

    public static Typeface getFont(Context context, String ttfName) {
        if (listFont.get(ttfName) != null) {
            return listFont.get(ttfName);
        } else {
//            listFont.put(ttfName, Typeface.createFromAsset(context.getAssets(), "fonts/" + ttfName));
            return Typeface.DEFAULT;//listFont.get(ttfName);
        }
    }

}

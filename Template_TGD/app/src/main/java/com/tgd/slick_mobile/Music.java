package com.tgd.slick_mobile;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Crée par Jerome le 31/03/2017 pour le projet TestAndroidSlick
 */

public class Music {
    private static final String TAG = "Music";
    private MediaPlayer player;
    private Context context;

    public Music(String name) throws SlickException {
        context=BasicGameState.currentContext();

        name =name.toLowerCase();
        name=name.replace("./music/","");
        name=name.replace("music/","");
        name=name.replace("musics/","");
        name=name.replace("son/","");
        name=name.replace("./son/","");
        name=name.replace("/","_");
        name=name.replace(" ","_");
        name=name.replace("-","_").replace(".ogg","").replace(".wav","").toLowerCase();

        int resID = context.getResources().getIdentifier(name , "raw", context.getPackageName());
        Log.d(TAG,"Musique a charger : "+name);

        if(resID==0)throw new SlickException("Le fichier "+name+" n'existe pas.");
        else{
            Log.d(TAG,"Music trouvé avec succes : "+name);
            player = MediaPlayer.create(context, resID);

        }

    }

    public void play() {
        if(player==null)return;
        player.start();
    }
    public void stop() {
        player.stop();
    }

    public void play(int i, float v) {
        if(player==null)return;
        player.start();

    }

    public void loop() {
        player.setLooping(true);
        play();
    }
}

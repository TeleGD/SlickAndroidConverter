package com.tgd.slick_mobile;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Crée par Jerome le 31/03/2017 pour le projet TestAndroidSlick
 */

public class Sound {
    private final MediaPlayer player;
    private Context context;
    private MediaPlayer mediaPlayer;

    public Sound(String name) throws SlickException {
        context=BasicGameState.currentContext();

        name=name.replace("./Music/","");
        name=name.replace("Music/","");
        name=name.replace("Musics/","");
        name=name.replace("musics/","");
        name=name.replace("music/","");
        name=name.replace("sound/","");
        name=name.replace("Sound/","");
        name=name.replace("Sounds/","");
        name=name.replace("sounds/","");
        name=name.replace("son/","");
        name=name.replace("sons/","");
        name=name.replace("Sons/","");
        name=name.replace("Son/","");
        name=name.replace("./son/","");
        name=name.replace("/","_");
        name=name.replace(" ","_");
        name=name.replace("-","_").replace(".ogg","").replace(".wav","").toLowerCase();

        int resID = context.getResources().getIdentifier(name , "raw", context.getPackageName());
        if(resID==0)throw new SlickException("Le fichier "+name+" n'existe pas.");
        else player = MediaPlayer.create(context, resID);

    }

    public void play() {
        player.start();
    }
    public void stop() {
        player.stop();
    }

    public void loop() {
        player.setLooping(true);
    }
}

package com.somboi.rodaimpian.saves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

public class PlayerSaves {
    private Save save;
    private FileHandle file = Gdx.files.local(StringRes.PLAYERSAVE);

    public PlayerSaves() {
        save = getSave();
    }

    private Save getSave() {
        Save save = new Save();

        if (file.exists()) {
            Json json = new Json();
            //read the file and decrypt it
            save = json.fromJson(Save.class, Base64Coder.decodeString(file.readString()));
        }
        return save;
    }

    public void save(Player player) {
        save.data.put(StringRes.PLAYERSAVE, player);
        saveToJson(); //Save the data immediately
    }
    public void savePlayerNew(PlayerNew playerNew) {
        save.data.put(StringRes.PLAYERSAVENEW, playerNew);
        saveToJson(); //Save the data immediately
    }

    public void savePlayerOnline(PlayerOnline playerOnline){
        save.data.put(StringRes.PLAYERONLINESAVE, playerOnline);
        saveToJson(); //Save the data immediately
    }
    public PlayerNew loadPlayerNew() {
        //Return data if the data contains key, otherwise return null
        if (save.data.containsKey(StringRes.PLAYERSAVENEW))
            return (PlayerNew) save.data.get(StringRes.PLAYERSAVENEW);
        else
            return null;
    }
    public Player load() {
        //Return data if the data contains key, otherwise return null
        if (save.data.containsKey(StringRes.PLAYERSAVE))
            return (Player) save.data.get(StringRes.PLAYERSAVE);
        else
            return null;
    }

    public PlayerOnline loadPlayerOnline() {
        //Return data if the data contains key, otherwise return null
        if (save.data.containsKey(StringRes.PLAYERONLINESAVE))
            return (PlayerOnline) save.data.get(StringRes.PLAYERONLINESAVE);
        else
            return null;
    }

    public void saveToJson() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        file.writeString(Base64Coder.encodeString(json.prettyPrint(save)), false);
    }
    /**
     * T replaces the type to be read as needed * public <T> T loadDataValue (String key, Class type) {if (save.data.containsKey (key)) return (T) save.data.get (key) ; else return null;//this if () avoids exception, but check for null on load.}
     */
    private static class Save {
        public ObjectMap<String, Object> data = new ObjectMap<String, Object>();
    }
}

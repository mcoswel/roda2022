package com.somboi.gdx.saves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.somboi.gdx.assets.QuestionsGenerator;

public class PlayerSave {
    private Save save;
    private FileHandle file = Gdx.files.local("playersaves");

  /*  public PlayerSave() {
        save = getSave();
    }

  *//*  private Save getSave() {
        Save save = new Save();

        if (file.exists()) {
            Json json = new Json();
            //read the file and decrypt it
            save = json.fromJson(Save.class, Base64Coder.decodeString(file.readString()));
        }
        return save;
    }

    public void saveQuestion(QuestionsGenerator questionsGenerator) {
        save.data.put("playersave", questionsGenerator);
        saveToJson(); //Save the data immediately
    }

    public QuestionsGenerator loadFromInternal() {
        //Return data if the data contains key, otherwise return null
        Save save = new Save();

        if (save.data.containsKey("playersave"))
            return (QuestionsGenerator) save.data.get("playersave");
        else
            return null;
    }

    public QuestionsGenerator loadFromInternal(FileHandle file) {
        //Return data if the data contains key, otherwise return null
        if (file.exists()) {
            Json json = new Json();
            //read the file and decrypt it
            save = json.fromJson(Save.class, Base64Coder.decodeString(file.readString()));
        }
        if (save.data.containsKey("playersave"))
            return (QuestionsGenerator) save.data.get("playersave");
        else
            return null;
    }

    public void saveToJson() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        file.writeString(Base64Coder.encodeString(json.prettyPrint(save)), false);
    }
*/
    /**
     * T replaces the type to be read as needed * public <T> T loadDataValue (String key, Class type) {if (save.data.containsKey (key)) return (T) save.data.get (key) ; else return null;//this if () avoids exception, but check for null on load.}
     */
    private static class Save {
        public ObjectMap<String, Object> data = new ObjectMap<String, Object>();
    }
}

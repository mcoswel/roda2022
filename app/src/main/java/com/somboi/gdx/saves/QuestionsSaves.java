package com.somboi.gdx.saves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.somboi.gdx.assets.QuestionsGenerator;
import com.somboi.gdx.assets.StringRes;

public class QuestionsSaves {
    private Save save;
    private FileHandle file = Gdx.files.local(StringRes.QUESTIONS);

    public QuestionsSaves() {
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

    public void saveQuestion(QuestionsGenerator questionsGenerator) {
        save.data.put("quesions", questionsGenerator);
        saveToJson(); //Save the data immediately
    }

    public QuestionsGenerator loadFromLocal() {
        //Return data if the data contains key, otherwise return null
        if (save.data.containsKey("quesions"))
            return (QuestionsGenerator) save.data.get("quesions");
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
        if (save.data.containsKey("quesions"))
            return (QuestionsGenerator) save.data.get("quesions");
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

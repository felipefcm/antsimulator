
package ffcm.ecs.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

public class EntityTemplateManager
{
    public static EntityTemplateManager instance = new EntityTemplateManager();

    private ObjectMap<String, JsonValue> dataMap;

    public EntityTemplateManager()
    {
        dataMap = new ObjectMap<>();
    }

    public boolean ProcessTemplateFile(FileHandle file)
    {
        JsonReader reader = new JsonReader();
        reader.parse(file);



        return true;
    }
}

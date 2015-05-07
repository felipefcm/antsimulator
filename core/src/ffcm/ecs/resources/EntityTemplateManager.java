
package ffcm.ecs.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

public class EntityTemplateManager
{
    public static EntityTemplateManager instance = new EntityTemplateManager();

    private ObjectMap<String, EntityTemplate> templateMap;

    public EntityTemplateManager()
    {
        templateMap = new ObjectMap<>();
    }

    public boolean ProcessTemplateFile(FileHandle file)
    {
        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(file);

        for(JsonValue child = root.child; child != null; child = child.next)
        {
            String name = child.name;


        }

        return true;
    }
}

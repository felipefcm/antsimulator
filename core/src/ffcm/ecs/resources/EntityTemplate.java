
package ffcm.ecs.resources;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

public class EntityTemplate
{
    private ObjectMap<String, JsonValue> dataMap;

    public EntityTemplate()
    {
        dataMap = new ObjectMap<>();
    }
}

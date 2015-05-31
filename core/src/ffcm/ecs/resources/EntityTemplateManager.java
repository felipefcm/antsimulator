
package ffcm.ecs.resources;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

import ffcm.antsim.resource.Log;

public class EntityTemplateManager
{
    private ObjectMap<String, EntityTemplate> templateMap;
    private Array<FileHandle> processedTemplates;

    public EntityTemplateManager()
    {
        templateMap = new ObjectMap<>();
        processedTemplates = new Array<>();
    }

    public boolean ProcessTemplateFile(String internalFilePath)
    {
        return ProcessTemplateFile(Gdx.files.internal(internalFilePath));
    }

    public boolean ProcessTemplateFile(FileHandle file)
    {
        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(file);

        EntityTemplate template = new EntityTemplate();

        JsonValue entityClassValue = root.child;
        String className = entityClassValue.name;

        if(templateMap.containsKey(className))
        {
            Log.Error("Will not load entity to template because it was already loaded: " + className);
            return false;
        }

        Log.Debug("Loading Entity: " + className);

        for(JsonValue child = entityClassValue.child; child != null; child = child.next)
        {
            String componentClassName = child.name;
            Log.Debug("Found component: " + componentClassName);

            Class<? extends Component> componentClass;

            try
            {
                componentClass = (Class<? extends Component>) Class.forName(componentClassName);
            }
            catch(ClassNotFoundException e)
            {
                Log.Error("Error while trying to get component class: " + componentClassName + " - " + e.getMessage());
                continue;
            }
            catch(ClassCastException e)
            {
                Log.Error("Error while trying to cast class: " + componentClassName + " to Class<? extends Component> - " + e.getMessage());
                continue;
            }

            template.AddComponent(componentClass, child);
        }

        if(template.GetNumComponents() <= 0)
        {
            Log.Error("EntityTemplate will not be added because it is empty");
            return false;
        }

        templateMap.put(className, template);
        processedTemplates.add(file);

        return true;
    }

    public EntityTemplate GetTemplate(Class<? extends Entity> entityClass)
    {
        if(!templateMap.containsKey(entityClass.getCanonicalName()))
            return null;

        return templateMap.get(entityClass.getCanonicalName());
    }

    public void Clear()
    {
        templateMap.clear();
    }

    public boolean ReloadTemplates()
    {
        Clear();

        for(FileHandle f : processedTemplates)
            if(!ProcessTemplateFile(f))
                return false;

        return true;
    }
}

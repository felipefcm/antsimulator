
package ffcm.ecs.resources;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

import ffcm.antsim.resource.Log;

public class EntityTemplate
{
    private ObjectMap<String, Component> componentMap;

    public EntityTemplate()
    {
        componentMap = new ObjectMap<>();
    }

    public void AddComponent(Class<? extends Component> componentClass, JsonValue data)
    {
        Component component;

        try
        {
            component = componentClass.newInstance();
        }
        catch(InstantiationException|IllegalAccessException e)
        {
            Log.Error("Error while instantiating component for template: " + componentClass.getCanonicalName() + " - Make sure the component have a default constructor");
            return;
        }

        if(component instanceof ILoadableFromJSON)
        {
            ((ILoadableFromJSON) component).LoadFromJSON(data);
        }
        else
        {
            Log.Error("Error: Component " + componentClass.getCanonicalName() + " does not implement ILoadableFromJSON interface");
            return;
        }

        componentMap.put(componentClass.getCanonicalName(), component);
    }

    public boolean HasComponent(Class<? extends Component> componentClass)
    {
        return componentMap.containsKey(componentClass.getCanonicalName());
    }

    public <T extends Component> T GetComponent(Class<T> componentClass)
    {
        if(!HasComponent(componentClass))
            return null;

        return (T) componentMap.get(componentClass.getCanonicalName());
    }

    public int GetNumComponents()
    {
        return componentMap.size;
    }
}

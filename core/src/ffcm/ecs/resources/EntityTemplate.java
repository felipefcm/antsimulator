
package ffcm.ecs.resources;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.resource.Log;

public class EntityTemplate
{
    private Array<Component> components;

    public EntityTemplate()
    {
        components = new Array<>();
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

        components.add(component);
    }

    public int GetNumComponents()
    {
        return components.size;
    }
}

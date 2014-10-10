
package ffcm.ecs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.resource.Log;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;

public abstract class Entity
{
	private static int nextId = 1;
	
	private int id;
	public HashMap<String, IComponent> components;
	
	public Entity()
	{
		id = nextId++;
		components = new HashMap<String, IComponent>();
	}
	
	public int GetId()
	{
		return id;
	}
	
	public void AddComponent(IComponent component)
	{
		String className = component.getClass().getSimpleName();
		
		if(components.containsKey(className))
		{
			Log.Error("Trying to add a component already inserted in entity: " + component.getClass().getSimpleName());
			return;
		}
		
		components.put(className, component);
	}
	
	public void LoadFromDisk(JsonValue jsonObj)
	{
		JsonValue comps = jsonObj.get("components");
		
		if(comps == null)
		{
			Log.Error("No components in '" + jsonObj.name + "' entity description");
			return;
		}
		
		JsonValue comp = comps.child;
		
		while(comp != null)
		{
			if(comp.name.equalsIgnoreCase("transform"))
			{
				CTransform transform = ComponentFactory._instance.CreateTransform(comp);
				AddComponent(transform);
			}
			
			if(comp.name.equalsIgnoreCase("velocity"))
			{
				CVelocity velocity = ComponentFactory._instance.CreateVelocity(comp);
				AddComponent(velocity);
			}
			
			if(comp.name.equalsIgnoreCase("drawable"))
			{
				CDrawable drawable = ComponentFactory._instance.CreateDrawable(comp);
				AddComponent(drawable);
			}
			
			comp = comp.next;
		}
	}
	
	public <T extends IComponent> T GetComponent(Class<T> type)
	{
		IComponent comp = components.get(type.getSimpleName());
		
		return type.cast(comp);
	}
	
	public void Clone(final Entity src)
	{
		//It is enough to copy only the 'components' map.
		//To create a deep copy we must iterate over every key/value pair
		
		Set<String> keys = src.components.keySet();
		Iterator<String> it = keys.iterator();
		
		while(it.hasNext())
		{
			String key = it.next();
			
			IComponent comp = src.components.get(key);
			
			components.put(key, comp.Clone());
		}
	}
}










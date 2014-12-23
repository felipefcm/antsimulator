
package ffcm.ecs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import ffcm.antsim.resource.Log;

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
	
	//public abstract void LoadFromDisk(JsonValue jsonObj);
	public abstract void AddNodes(NodeMap nodeMap);
	
	public int GetId()
	{
		return id;
	}
	
	public final void AddComponent(IComponent component)
	{
		String className = component.getClass().getSimpleName();
		
		if(components.containsKey(className))
		{
			Log.Error("Trying to add a component already inserted in entity: " + className);
			return;
		}
		
		components.put(className, component);
	}
	
	public final <T extends IComponent> T GetComponent(Class<T> type)
	{
		IComponent comp = components.get(type.getSimpleName());
		
		return type.cast(comp);
	}
	
	public final void Clone(final Entity src)
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










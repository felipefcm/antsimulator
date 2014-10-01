
package ffcm.ecs;

import java.util.HashMap;

import ffcm.antsim.resource.Log;

public class Entity
{
	private static int nextId = 1;
	
	private int id;
	private HashMap<Class, Object> components;
	
	public Entity()
	{
		id = nextId++;
		components = new HashMap<Class, Object>();
	}
	
	public int GetId()
	{
		return id;
	}
	
	public void AddComponent(Object component)
	{
		if(components.containsKey(component.getClass()))
		{
			Log.Error("Trying to add a component already inserted in entity: " + component.getClass().getSimpleName());
			return;
		}
		
		components.put(component.getClass(), component);
	}
}

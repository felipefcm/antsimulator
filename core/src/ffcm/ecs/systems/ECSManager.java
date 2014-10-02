
package ffcm.ecs.systems;

import java.util.ArrayList;

import ffcm.antsim.resource.Log;
import ffcm.ecs.Entity;
import ffcm.ecs.ISystem;

public class ECSManager
{
	public static ECSManager _instance = new ECSManager();
	
	private ArrayList<ISystem> systems;
	
	public ECSManager()
	{
		systems = new ArrayList<ISystem>();
	}
	
	public void AddSystem(ISystem system)
	{
		if(systems.contains(system))
		{
			Log.Error("Trying to add a system already added: " + system.getClass().getSimpleName());
			return;
		}
		
		systems.add(system);
		
		system.Start();
	}
	
	public void RemoveSystem(ISystem system)
	{
		systems.remove(system);
		
		system.End();
	}
	
	public void AddEntity(Entity entity)
	{
	}
	
	public void RemoveEntity(Entity entity)
	{
	}
	
	public void Update()
	{
		for(int i = 0; i < systems.size(); ++i)
			systems.get(i).Update();
	}
}


package ffcm.ecs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import ffcm.antsim.resource.Log;

public class ECSManager
{
	public static ECSManager _instance = new ECSManager();
	
	private LinkedList<Entity> entities;
	private ArrayList<ISystem> systems;
	private NodeMap nodeMap;
	
	private Comparator<ISystem> systemComparator;
	
	public ECSManager()
	{
		entities = new LinkedList<Entity>();
		systems = new ArrayList<ISystem>();
		nodeMap = new NodeMap();
		
		systemComparator = new Comparator<ISystem>()
		{
			@Override
			public int compare(ISystem o1, ISystem o2)
			{
				return o1.GetPriority() - o2.GetPriority();
			}	
		};
	}
	
	public void AddSystem(ISystem system)
	{
		if(systems.contains(system))
		{
			Log.Error("Trying to add a system already added: " + system.getClass().getSimpleName());
			return;
		}
		
		systems.add(system);
		systems.sort(systemComparator);
				
		system.Start();
	}
	
	public void RemoveSystem(ISystem system)
	{
		systems.remove(system);
		
		system.End();
	}
	
	public void AddEntity(Entity entity)
	{
		entities.add(entity);
		
		entity.AddNodes(nodeMap);
	}
	
	public void RemoveEntity(Entity entity)
	{
	}
	
	public void Update()
	{
		for(int i = 0; i < systems.size(); ++i)
			systems.get(i).Update(nodeMap);
	}
}


package ffcm.ecs;

import java.util.ArrayList;
import java.util.LinkedList;

import ffcm.antsim.resource.Log;
import ffcm.ecs.node.NodeMap;

public class ECSManager
{
	public static ECSManager _instance = new ECSManager();
	
	private LinkedList<Entity> entities;
	private ArrayList<ISystem> systems;
	private NodeMap nodeMap;
	
	public ECSManager()
	{
		entities = new LinkedList<Entity>();
		systems = new ArrayList<ISystem>();
		nodeMap = new NodeMap();
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
		entities.add(entity);
		
		/*
		CTransform transformComponent = entity.GetComponent(CTransform.class);
		CVelocity velocityComponent = entity.GetComponent(CVelocity.class);
		CDrawable drawableComponent = entity.GetComponent(CDrawable.class);
		CWander wanderComponent = entity.GetComponent(CWander.class);
		CSelectable selectableComponent = entity.GetComponent(CSelectable.class);
		
		MovableNode movableNode = new MovableNode();
		DrawableNode drawableNode = new DrawableNode();
		WanderNode wanderNode = new WanderNode();
		
		if(transformComponent != null)
		{
			movableNode.transform = transformComponent;
		}
		
		if(velocityComponent != null)
		{
			movableNode.velocity = velocityComponent;
		}
		
		if(drawableComponent != null)
		{
			drawableNode.drawable = drawableComponent;
			drawableNode.transform = transformComponent;
		}
		
		if(wanderComponent != null)
		{
			wanderNode.transform = transformComponent;
			wanderNode.velocity = velocityComponent;
			wanderNode.wander = wanderComponent;
		}
		
		if(selectableComponent != null)
		{
			
		}
		
		moveSystem.AddNode(movableNode);
		drawSystem.AddNode(drawableNode);
		wanderSystem.AddNode(wanderNode);
		*/
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

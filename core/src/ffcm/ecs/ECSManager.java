
package ffcm.ecs;

import java.util.ArrayList;

import ffcm.antsim.resource.Log;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.comps.CWander;
import ffcm.ecs.node.DrawableNode;
import ffcm.ecs.node.MovableNode;
import ffcm.ecs.node.WanderNode;
import ffcm.ecs.systems.DrawSystem;
import ffcm.ecs.systems.MoveSystem;
import ffcm.ecs.systems.WanderBehaviourSystem;

public class ECSManager
{
	public static ECSManager _instance = new ECSManager();
	
	private ArrayList<ISystem> systems;
	
	private MoveSystem moveSystem;
	private DrawSystem drawSystem;
	private WanderBehaviourSystem wanderSystem;
	
	public ECSManager()
	{
		systems = new ArrayList<ISystem>();
		
		moveSystem = new MoveSystem();
		drawSystem = new DrawSystem();
		wanderSystem = new WanderBehaviourSystem();
		
		AddSystem(wanderSystem);
		AddSystem(moveSystem);
		AddSystem(drawSystem);
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
		CTransform transformComponent = entity.GetComponent(CTransform.class);
		CVelocity velocityComponent = entity.GetComponent(CVelocity.class);
		CDrawable drawableComponent = entity.GetComponent(CDrawable.class);
		CWander wanderComponent = entity.GetComponent(CWander.class);
		
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
		
		moveSystem.AddNode(movableNode);
		drawSystem.AddNode(drawableNode);
		wanderSystem.AddNode(wanderNode);
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

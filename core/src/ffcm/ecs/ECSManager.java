
package ffcm.ecs;

import java.util.ArrayList;

import ffcm.antsim.resource.Log;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.node.DrawableNode;
import ffcm.ecs.node.MovableNode;
import ffcm.ecs.systems.DrawSystem;
import ffcm.ecs.systems.MoveSystem;

public class ECSManager
{
	public static ECSManager _instance = new ECSManager();
	
	private ArrayList<ISystem> systems;
	
	private MoveSystem moveSystem;
	private DrawSystem drawSystem;
	
	public ECSManager()
	{
		systems = new ArrayList<ISystem>();
		
		moveSystem = new MoveSystem();
		drawSystem = new DrawSystem();
		
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
		Object transformComponent = entity.GetComponent(CTransform.class);
		Object velocityComponent = entity.GetComponent(CVelocity.class);
		Object drawableComponent = entity.GetComponent(CDrawable.class);
		
		MovableNode movableNode = new MovableNode();
		DrawableNode drawableNode = new DrawableNode();
		
		if(transformComponent != null)
		{
			movableNode.transform = (CTransform) transformComponent;
		}
		
		if(velocityComponent != null)
		{
			movableNode.velocity = (CVelocity) velocityComponent;
		}
		
		if(drawableComponent != null)
		{
			drawableNode.drawable = (CDrawable) drawableComponent;
			drawableNode.transform = (CTransform) transformComponent;
		}
		
		moveSystem.AddNode(movableNode);
		drawSystem.AddNode(drawableNode);
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

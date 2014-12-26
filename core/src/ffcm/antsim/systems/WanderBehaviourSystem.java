
package ffcm.antsim.systems;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import ffcm.antsim.comps.CVelocity;
import ffcm.antsim.comps.CWander;
import ffcm.antsim.nodes.WanderNode;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.INode;
import ffcm.ecs.ISystem;
import ffcm.ecs.NodeMap;

public class WanderBehaviourSystem implements ISystem
{	
	public WanderBehaviourSystem()
	{
	}
	
	@Override
	public void Start()
	{
	}

	@Override
	public void Update(final NodeMap nodeMap)
	{
		LinkedList<INode> nodeList = nodeMap.GetNodes(WanderNode.class);
		
		if(nodeList == null)
			return;
		
		Iterator<INode> it = nodeList.iterator();
		
		while(it.hasNext())
		{
			WanderNode node = (WanderNode) it.next();
			
			CWander wander = node.wander;
			CVelocity velocity = node.velocity;
			
			Vector2 circleCenter = velocity.vector.cpy();
			circleCenter.nor().scl(wander.circleDistance);
			
			Random random = ResourceManager._instance.random;
			
			Vector2 disp = new Vector2(0, 1.0f);
			disp.scl(wander.circleRadius);
			
			//wander.wanderAngle += random.nextFloat() * 15.0f * (random.nextBoolean() ? 1.0f : -1.0f);
			wander.wanderAngle += random.nextFloat() * 30.0f - 30.0f * 0.5f;
			wander.wanderAngle = (int) wander.wanderAngle % 360.0f;
			
			disp.setAngle(wander.wanderAngle);
			
			Vector2 wanderForce = circleCenter.cpy().add(disp);
			
			Vector2 steerForce = wanderForce.sub(velocity.vector);
			
			velocity.vector.add(steerForce).nor().scl(velocity.maxVelocity); //max velocity
		}
	}

	@Override
	public void End()
	{
	}

	@Override
	public int GetPriority()
	{
		return HighPriority;
	}
}


package ffcm.antsim.systems;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import ffcm.antsim.nodes.WanderNode;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.ISystem;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.comps.CWander;

public class WanderBehaviourSystem implements ISystem
{
	private LinkedList<WanderNode> wanderNodes;
	
	public WanderBehaviourSystem()
	{
		wanderNodes = new LinkedList<WanderNode>();
	}
	
	@Override
	public void Start()
	{
	}

	@Override
	public void Update()
	{
		Iterator<WanderNode> it = wanderNodes.iterator();
		
		while(it.hasNext())
		{
			WanderNode node = it.next();
			
			CWander wander = node.wander;
			CVelocity velocity = node.velocity;
			
			Vector2 circleCenter = velocity.vector.cpy();
			circleCenter.nor().scl(wander.circleDistance);
			
			Random random = ResourceManager._instance.random; 
			
			//pick a random displacement vector inside the circle
			Vector2 disp = new Vector2(0, 1.0f);
			disp.setAngle(wander.wanderAngle);
			disp.nor().scl(wander.circleRadius);
			
			wander.wanderAngle += random.nextFloat() * 15.0f * (random.nextBoolean() ? 1.0f : -1.0f);
			wander.wanderAngle = (int) wander.wanderAngle % 360.0f;
			
			Vector2 wanderForce = circleCenter.cpy().add(disp);
			
			Vector2 steerForce = wanderForce.sub(velocity.vector);
			
			velocity.vector.add(steerForce).nor().scl(0.5f); //max velocity
		}
	}
	
	public void AddNode(WanderNode node)
	{
		wanderNodes.add(node);
	}

	@Override
	public void End()
	{
	}
}

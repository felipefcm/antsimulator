
package ffcm.ecs.systems;

import java.util.Iterator;
import java.util.LinkedList;

import ffcm.ecs.ISystem;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.node.MovableNode;

public class MoveSystem implements ISystem
{
	private LinkedList<MovableNode> movableNodes;
	
	public MoveSystem()
	{
		movableNodes = new LinkedList<MovableNode>();
	}
	
	@Override
	public void Start()
	{
	}
	
	@Override
	public void Update()
	{
		Iterator<MovableNode> it = movableNodes.iterator();
		
		while(it.hasNext())
		{
			MovableNode node = it.next();
			
			CTransform transform = node.transform;
			CVelocity velocity = node.velocity;
			
			transform.rotation = velocity.vector.angle() - 90.0f;
			
			transform.position.add(velocity.vector);
		}
	}
	
	public void AddNode(MovableNode node)
	{
		movableNodes.add(node);
	}

	@Override
	public void End()
	{
	}
}

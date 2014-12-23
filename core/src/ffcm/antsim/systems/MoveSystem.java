
package ffcm.antsim.systems;

import java.util.Iterator;
import java.util.LinkedList;

import ffcm.antsim.comps.CTransform;
import ffcm.antsim.comps.CVelocity;
import ffcm.antsim.nodes.MovableNode;
import ffcm.ecs.INode;
import ffcm.ecs.ISystem;
import ffcm.ecs.NodeMap;

public class MoveSystem implements ISystem
{	
	public MoveSystem()
	{
	}
	
	@Override
	public void Start()
	{
	}
	
	@Override
	public void Update(final NodeMap nodeMap)
	{
		LinkedList<INode> nodeList = nodeMap.GetNodes(MovableNode.class);
		
		if(nodeList == null)
			return;
		
		Iterator<INode> it = nodeList.iterator();
		
		while(it.hasNext())
		{
			MovableNode node = (MovableNode) it.next();
			
			CTransform transform = node.transform;
			CVelocity velocity = node.velocity;
			
			transform.rotation = velocity.vector.angle() - 90.0f;
			
			transform.position.add(velocity.vector);
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

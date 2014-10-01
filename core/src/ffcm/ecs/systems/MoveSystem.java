
package ffcm.ecs.systems;

import java.util.Iterator;
import java.util.LinkedList;

import ffcm.ecs.ISystem;
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
			
			
		}
	}

	@Override
	public void End()
	{
		
	}
}


package ffcm.ecs.systems;

import java.util.Iterator;
import java.util.LinkedList;

import ffcm.ecs.ISystem;
import ffcm.ecs.node.DrawableNode;

public class DrawSystem implements ISystem
{
	private LinkedList<DrawableNode> drawableNodes;
	
	public DrawSystem()
	{
		drawableNodes = new LinkedList<DrawableNode>();
	}
	
	@Override
	public void Start()
	{
		
	}
	
	@Override
	public void Update()
	{
		Iterator<DrawableNode> it = drawableNodes.iterator();
		
		while(it.hasNext())
		{
			DrawableNode node = it.next();
			
			
		}
	}

	@Override
	public void End()
	{
		
	}	
}

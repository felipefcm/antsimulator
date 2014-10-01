
package ffcm.ecs.systems;

import java.util.LinkedList;

import ffcm.ecs.node.DrawableNode;
import ffcm.ecs.node.MovableNode;

public class SystemManager
{
	public static SystemManager _instance = new SystemManager();
	
	private LinkedList<MovableNode> movableNodes;
	private LinkedList<DrawableNode> drawableNodes;
	
	public SystemManager()
	{
		movableNodes = new LinkedList<MovableNode>();
		drawableNodes = new LinkedList<DrawableNode>();
	}
	
	public void Update()
	{
		
	}
}

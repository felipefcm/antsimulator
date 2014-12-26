
package ffcm.antsim.systems;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.math.Rectangle;

import ffcm.antsim.World;
import ffcm.antsim.nodes.SpatialNode;
import ffcm.antsim.resource.QuadTree;
import ffcm.ecs.INode;
import ffcm.ecs.ISystem;
import ffcm.ecs.NodeMap;

public class SpatialPartitioningSystem implements ISystem
{
	private QuadTree quadTree;
	
	@Override
	public void Start()
	{
		quadTree = new QuadTree(new Rectangle(0, 0, World.WorldSize.x, World.WorldSize.y));
	}

	@Override
	public void End()
	{
		
	}

	@Override
	public void Update(NodeMap nodeMap)
	{
		quadTree.FastClear();
		
		LinkedList<INode> nodes = nodeMap.GetNodes(SpatialNode.class);
		
		if(nodes == null)
			return;
		
		Iterator<INode> it = nodes.iterator();
		
		while(it.hasNext())
		{
			SpatialNode node = (SpatialNode) it.next();
			
			quadTree.Add(node.transform.position);
		}
	}

	@Override
	public int GetPriority()
	{
		return HighPriority;
	}
}

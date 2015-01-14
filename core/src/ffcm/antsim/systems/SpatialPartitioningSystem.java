
package ffcm.antsim.systems;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import ffcm.antsim.World;
import ffcm.antsim.comps.CTransform;
import ffcm.antsim.nodes.SpatialNode;
import ffcm.antsim.resource.quadtree.QuadTree;
import ffcm.ecs.Entity;
import ffcm.ecs.INode;
import ffcm.ecs.ISystem;
import ffcm.ecs.NodeMap;

public class SpatialPartitioningSystem implements ISystem
{
	public QuadTree<Entity> quadTree;
	
	@Override
	public void Start()
	{
		quadTree = new QuadTree<Entity>(new Rectangle(0, 0, World.WorldSize.x, World.WorldSize.y));
	}

	@Override
	public void End()
	{
	}

	@Override
	public void Update(NodeMap nodeMap)
	{
		//quadTree.FastClear();
		quadTree.Clear();
		
		LinkedList<INode> nodes = nodeMap.GetNodes(SpatialNode.class);
		
		if(nodes == null)
			return;
		
		Iterator<INode> it = nodes.iterator();
		
		while(it.hasNext())
		{
			SpatialNode node = (SpatialNode) it.next();
			
			CTransform transform = node.transform;
			Sprite sprite = node.sprite;
			
			node.quadTreeData.bounds.set
			(
				transform.position.x, transform.position.y,
				sprite.getWidth(), sprite.getHeight()
			);
			
			quadTree.Add(node.quadTreeData);
		}
	}

	@Override
	public int GetPriority()
	{
		return NormalPriority;
	}
}

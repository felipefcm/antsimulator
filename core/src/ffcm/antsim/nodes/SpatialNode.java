
package ffcm.antsim.nodes;

import ffcm.antsim.comps.CTransform;
import ffcm.antsim.resource.quadtree.QuadTreeDataNode;
import ffcm.ecs.Entity;
import ffcm.ecs.INode;

public class SpatialNode implements INode
{
	public CTransform transform;
	public QuadTreeDataNode<Entity> quadTreeDataNode;
	
	public SpatialNode(CTransform transform, Entity entity)
	{
		this.transform = transform;
		
		quadTreeDataNode = new QuadTreeDataNode<Entity>();
		quadTreeDataNode.info = entity;
		quadTreeDataNode.position = transform.position;
	}
}


package ffcm.antsim.nodes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import ffcm.antsim.comps.CTransform;
import ffcm.antsim.resource.quadtree.QuadTreeData;
import ffcm.ecs.Entity;
import ffcm.ecs.INode;

public class SpatialNode implements INode
{
	public QuadTreeData<Entity> quadTreeData;
	public Sprite sprite;
	public CTransform transform;
	
	public SpatialNode(Sprite sprite, CTransform transform, Entity entity)
	{
		this.sprite = sprite;
		this.transform = transform;
		
		quadTreeData = new QuadTreeData<Entity>();
		quadTreeData.info = entity;
		quadTreeData.bounds = new Rectangle();
	}
}


package ffcm.antsim;

import ffcm.antsim.comps.CDrawable;
import ffcm.antsim.comps.CTransform;
import ffcm.antsim.comps.CVelocity;
import ffcm.antsim.comps.CWander;
import ffcm.antsim.nodes.DrawableNode;
import ffcm.antsim.nodes.MovableNode;
import ffcm.antsim.nodes.SpatialNode;
import ffcm.antsim.nodes.WanderNode;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.Entity;
import ffcm.ecs.NodeMap;

public class Ant extends Entity
{		
	public Ant()
	{
	}
	
	@Override
	public void AddNodes(NodeMap nodeMap)
	{
		CTransform transform = GetComponent(CTransform.class);
		CVelocity velocity = GetComponent(CVelocity.class);
		CWander wander = GetComponent(CWander.class);
		CDrawable drawable = GetComponent(CDrawable.class);
		
		drawable.sprite.setSize(World.WorldSize.x / 200.0f, World.WorldSize.y / (200.0f * 0.686f));
		drawable.sprite.setOriginCenter();
		
		transform.rotation = ResourceManager._instance.random.nextInt() % 360;
		
		wander.terrain = AntSim.antSim.world.terrain;
		
		MovableNode movableNode = new MovableNode(transform, velocity);
		DrawableNode drawableNode = new DrawableNode(drawable, transform);
		WanderNode wanderNode = new WanderNode(wander, transform, velocity);
		SpatialNode spatialNode = new SpatialNode(transform);
		
		nodeMap.AddNode(MovableNode.class, movableNode);
		nodeMap.AddNode(DrawableNode.class, drawableNode);
		nodeMap.AddNode(WanderNode.class, wanderNode);
		nodeMap.AddNode(SpatialNode.class, spatialNode);
	}
}

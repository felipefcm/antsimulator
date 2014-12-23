
package ffcm.antsim;

import ffcm.antsim.comps.CDrawable;
import ffcm.antsim.comps.CTransform;
import ffcm.antsim.comps.CVelocity;
import ffcm.antsim.comps.CWander;
import ffcm.antsim.nodes.DrawableNode;
import ffcm.antsim.nodes.MovableNode;
import ffcm.antsim.nodes.WanderNode;
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
		
		MovableNode movableNode = new MovableNode(transform, velocity);
		DrawableNode drawableNode = new DrawableNode(drawable, transform);
		WanderNode wanderNode = new WanderNode(wander, transform, velocity);
		
		nodeMap.AddNode(MovableNode.class, movableNode);
		nodeMap.AddNode(DrawableNode.class, drawableNode);
		nodeMap.AddNode(WanderNode.class, wanderNode);
	}
}

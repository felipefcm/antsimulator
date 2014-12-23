
package ffcm.antsim.nodes;

import ffcm.antsim.comps.CTransform;
import ffcm.antsim.comps.CVelocity;
import ffcm.ecs.INode;

public class MovableNode implements INode
{
	public CTransform transform;
	public CVelocity velocity;
	
	public MovableNode(CTransform transform, CVelocity velocity)
	{
		this.transform = transform;
		this.velocity = velocity;
	}
}

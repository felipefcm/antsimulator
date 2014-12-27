
package ffcm.antsim.nodes;

import ffcm.antsim.Terrain;
import ffcm.antsim.comps.CTransform;
import ffcm.antsim.comps.CVelocity;
import ffcm.antsim.comps.CWander;
import ffcm.ecs.INode;

public class WanderNode implements INode
{
	public CWander wander;
	public CTransform transform;
	public CVelocity velocity;
	public Terrain terrain;
	
	public WanderNode(CWander wander, CTransform transform, CVelocity velocity)
	{
		this.wander = wander;
		this.transform = transform;
		this.velocity = velocity;
	}
}

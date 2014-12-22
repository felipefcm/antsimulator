
package ffcm.antsim.nodes;

import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.comps.CWander;
import ffcm.ecs.node.INode;

public class WanderNode implements INode
{
	public CWander wander;
	public CTransform transform;
	public CVelocity velocity;
}

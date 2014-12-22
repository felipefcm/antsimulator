
package ffcm.antsim.nodes;

import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.node.INode;

public class MovableNode implements INode
{
	public CTransform transform;
	public CVelocity velocity;
}

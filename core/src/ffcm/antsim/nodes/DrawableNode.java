
package ffcm.antsim.nodes;

import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.node.INode;

public class DrawableNode implements INode
{
	public CDrawable drawable;
	public CTransform transform;
}

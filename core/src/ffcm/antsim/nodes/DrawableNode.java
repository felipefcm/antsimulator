
package ffcm.antsim.nodes;

import ffcm.antsim.comps.CDrawable;
import ffcm.antsim.comps.CTransform;
import ffcm.ecs.INode;

public class DrawableNode implements INode
{	
	public CDrawable drawable;
	public CTransform transform;
	
	public DrawableNode(CDrawable drawable, CTransform transform)
	{
		this.drawable = drawable;
		this.transform = transform;
	}
}

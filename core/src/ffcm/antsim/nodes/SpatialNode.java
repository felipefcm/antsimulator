
package ffcm.antsim.nodes;

import ffcm.antsim.comps.CTransform;
import ffcm.ecs.INode;

public class SpatialNode implements INode
{
	public CTransform transform;
	
	public SpatialNode(CTransform transform)
	{
		this.transform = transform;
	}
}

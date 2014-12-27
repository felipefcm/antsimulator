
package ffcm.antsim.systems;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import ffcm.antsim.nodes.SelectableNode;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.INode;
import ffcm.ecs.ISystem;
import ffcm.ecs.NodeMap;

public class SelectSystem implements ISystem
{
	private SpatialPartitioningSystem spatialSystem;
	
	private ShapeRenderer shapeRenderer;
	
	private Vector2 clickPos;
	private boolean hasClick = false;
	
	public SelectSystem(final SpatialPartitioningSystem spatialSystem)
	{
		this.spatialSystem = spatialSystem;
	}
	
	@Override
	public void Start()
	{
		shapeRenderer = ResourceManager._instance.shapeRenderer;
	}

	@Override
	public void End()
	{
		
	}

	@Override
	public void Update(NodeMap nodeMap)
	{
		if(!hasClick)
			return;
		
		hasClick = false;
		
		//spatialSystem.quadTree.
		
		LinkedList<INode> nodeList = nodeMap.GetNodes(SelectableNode.class);
		
		if(nodeList == null)
			return;
		
		Iterator<INode> nodeIt = nodeList.iterator();
		
		while(nodeIt.hasNext())
		{
			SelectableNode node = (SelectableNode) nodeIt.next();
			
			
		}
	}
	
	public void Clicked(final Vector2 worldPos)
	{
		clickPos = worldPos;
		hasClick = true;
	}

	@Override
	public int GetPriority()
	{
		return NormalPriority;
	}	
}

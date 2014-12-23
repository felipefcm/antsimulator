
package ffcm.antsim.systems;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ffcm.antsim.comps.CDrawable;
import ffcm.antsim.comps.CTransform;
import ffcm.antsim.nodes.DrawableNode;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.INode;
import ffcm.ecs.ISystem;
import ffcm.ecs.NodeMap;

public class DrawSystem implements ISystem
{	
	public DrawSystem()
	{
	}
	
	@Override
	public void Start()
	{
	}
	
	@Override
	public void Update(final NodeMap nodeMap)
	{
		LinkedList<INode> nodeList = nodeMap.GetNodes(DrawableNode.class);
		
		if(nodeList == null)
			return;
		
		Iterator<INode> it = nodeList.iterator();
		
		SpriteBatch spriteBatch = ResourceManager._instance.spriteBatch;
		
		spriteBatch.setProjectionMatrix(ResourceManager._instance.viewport.getCamera().combined);
		spriteBatch.begin();
		{
			while(it.hasNext())
			{
				DrawableNode node = (DrawableNode) it.next();
				
				CTransform transform = node.transform;
				CDrawable drawable = node.drawable;
				
				drawable.sprite.setPosition(transform.position.x, transform.position.y);
				drawable.sprite.setRotation(transform.rotation);
				
				drawable.sprite.draw(spriteBatch);
			}
		}
		spriteBatch.end();
	}
	
	@Override
	public void End()
	{
	}	
}

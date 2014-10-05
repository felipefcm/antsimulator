
package ffcm.ecs.systems;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.ISystem;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.node.DrawableNode;

public class DrawSystem implements ISystem
{
	private LinkedList<DrawableNode> drawableNodes;
	
	public DrawSystem()
	{
		drawableNodes = new LinkedList<DrawableNode>();
	}
	
	@Override
	public void Start()
	{
		
	}
	
	@Override
	public void Update()
	{
		Iterator<DrawableNode> it = drawableNodes.iterator();
		
		SpriteBatch spriteBatch = ResourceManager._instance.spriteBatch;
		
		spriteBatch.setProjectionMatrix(ResourceManager._instance.viewport.getCamera().combined);
		spriteBatch.begin();
		
		while(it.hasNext())
		{
			DrawableNode node = it.next();
			
			CTransform transform = node.transform;
			CDrawable drawable = node.drawable;
			
			drawable.sprite.setPosition(transform.position.x, transform.position.y);
			drawable.sprite.setRotation(transform.rotation);
			
			drawable.sprite.draw(spriteBatch);
		}
		
		spriteBatch.end();
	}
	
	public void AddNode(DrawableNode node)
	{
		drawableNodes.add(node);
	}

	@Override
	public void End()
	{
		
	}	
}

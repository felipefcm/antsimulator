
package ffcm.ecs.systems;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.ISystem;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CPosition;
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
		
		while(it.hasNext())
		{
			DrawableNode node = it.next();
			
			CPosition position = node.position;
			CDrawable drawable = node.drawable;
			
			SpriteBatch spriteBatch = ResourceManager._instance.spriteBatch; 
			
			drawable.sprite.setPosition(position.position.x, position.position.y);
			
			//TODO optimize spritebatch use
			spriteBatch.begin();
			{
				drawable.sprite.draw(spriteBatch);
			}
			spriteBatch.end();
		}
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

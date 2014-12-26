
package ffcm.antsim.systems;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ffcm.antsim.comps.CDrawable;
import ffcm.antsim.comps.CTransform;
import ffcm.antsim.nodes.DrawableNode;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.INode;
import ffcm.ecs.ISystem;
import ffcm.ecs.NodeMap;

public class DrawSystem implements ISystem
{	
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	
	public DrawSystem()
	{
	}
	
	@Override
	public void Start()
	{
		spriteBatch = ResourceManager._instance.spriteBatch;
		shapeRenderer = ResourceManager._instance.shapeRenderer;
		camera = (OrthographicCamera) ResourceManager._instance.viewport.getCamera();
	}
	
	@Override
	public void Update(final NodeMap nodeMap)
	{
		LinkedList<INode> nodeList = nodeMap.GetNodes(DrawableNode.class);
		
		if(nodeList == null)
			return;
		
		Iterator<INode> it = nodeList.iterator();
		
		spriteBatch.setProjectionMatrix(camera.combined);
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
		
		/*
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		{
			it = nodeList.iterator();
			
			while(it.hasNext())
			{
				DrawableNode node = (DrawableNode) it.next();
				
				CTransform transform = node.transform;
				CDrawable drawable = node.drawable;
				
				//Rectangle bound = drawable.sprite.getx;
				
				shapeRenderer.rect(drawable.sprite.getX(), drawable.sprite.getY(), drawable.sprite.getWidth(), drawable.sprite.getHeight());
			}
		}
		shapeRenderer.end();
		*/
	}
	
	@Override
	public void End()
	{
	}

	@Override
	public int GetPriority()
	{
		return NormalPriority;
	}	
}

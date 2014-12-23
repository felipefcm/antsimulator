
package ffcm.antsim;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.comps.CDrawable;
import ffcm.antsim.comps.CTransform;
import ffcm.antsim.comps.CVelocity;
import ffcm.antsim.comps.CWander;
import ffcm.antsim.nodes.DrawableNode;
import ffcm.antsim.nodes.MovableNode;
import ffcm.antsim.nodes.WanderNode;
import ffcm.antsim.resource.Log;
import ffcm.ecs.ComponentFactory;
import ffcm.ecs.Entity;
import ffcm.ecs.NodeMap;

public class Ant extends Entity
{		
	public Ant()
	{	
	}
	
	@Override
	public void AddNodes(NodeMap nodeMap)
	{
		CTransform transform = GetComponent(CTransform.class);
		CVelocity velocity = GetComponent(CVelocity.class);
		CWander wander = GetComponent(CWander.class);
		CDrawable drawable = GetComponent(CDrawable.class);
		
		MovableNode movableNode = new MovableNode(transform, velocity);
		DrawableNode drawableNode = new DrawableNode(drawable, transform);
		WanderNode wanderNode = new WanderNode(wander, transform, velocity);
		
		nodeMap.AddNode(MovableNode.class, movableNode);
		nodeMap.AddNode(DrawableNode.class, drawableNode);
		nodeMap.AddNode(WanderNode.class, wanderNode);
	}
	
	@Override
	public void LoadFromDisk(JsonValue jsonObj)
	{
		JsonValue comps = jsonObj.get("components");
		
		if(comps == null)
		{
			Log.Error("No components in '" + jsonObj.name + "' entity description");
			return;
		}
		
		JsonValue comp = comps.child;
		
		while(comp != null)
		{
			if(comp.name.equalsIgnoreCase("transform"))
			{
				CTransform transform = ComponentFactory._instance.CreateTransform(comp);
				AddComponent(transform);
			}
			
			if(comp.name.equalsIgnoreCase("velocity"))
			{
				CVelocity velocity = ComponentFactory._instance.CreateVelocity(comp);
				AddComponent(velocity);
			}
			
			if(comp.name.equalsIgnoreCase("drawable"))
			{
				CDrawable drawable = ComponentFactory._instance.CreateDrawable(comp);
				AddComponent(drawable);
			}
			
			if(comp.name.equalsIgnoreCase("wander"))
			{
				CWander wander = ComponentFactory._instance.CreateWander(comp);
				AddComponent(wander);
			}
			
			comp = comp.next;
		}
	}
}

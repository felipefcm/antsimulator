
package ffcm.antsim;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.resource.Log;
import ffcm.ecs.ComponentFactory;
import ffcm.ecs.Entity;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.comps.CWander;
import ffcm.ecs.node.NodeMap;

public class Ant extends Entity
{		
	public Ant()
	{	
	}
	
	@Override
	public void AddNodes(NodeMap nodeMap)
	{
		
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

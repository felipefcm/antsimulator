
package ffcm.antsim;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.resource.Log;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.Entity;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;

public class Ant extends Entity
{	
	CTransform transform;
	CVelocity velocity;
	CDrawable drawable;
	
	public static Ant CreateAnt()
	{
		Ant ant = new Ant();
		
		ant.drawable.CreateFromTextureRegion(ResourceManager._instance.antTextureRegionMap.get("ant"));
		
		ant.AddComponent(ant.transform);
		ant.AddComponent(ant.velocity);
		ant.AddComponent(ant.drawable);
		
		return ant;
	}
	
	public Ant()
	{
		transform = new CTransform();
		drawable = new CDrawable();
		velocity = new CVelocity();
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
				JsonValue position = comp.getChild("position");
				JsonValue rotation = comp.getChild("position");
			}
			
			if(comp.name.equalsIgnoreCase("drawable"))
			{
				
			}
			
			comp = comp.next;
		}
	}
}

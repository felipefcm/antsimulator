
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
		JsonValue comps = entityDescription.get("components");
		
		if(comps == null)
		{
			Log.Debug("No components in '" + entityDescription.name + "' entity description");
			return entity;
		}
		
		JsonValue comp = comps.child;
		
		while(comp != null)
		{
			Component cp = ComponentFactory._instance.CreateComponent(comp.name);
			cp.InitComponent(comp);
			
			entity.InsertComponent(cp);
			
			comp = comp.next;
		}
	}
}

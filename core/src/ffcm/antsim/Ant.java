
package ffcm.antsim;

import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.Entity;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CPosition;
import ffcm.ecs.comps.CVelocity;

public class Ant extends Entity
{	
	CPosition position;
	CVelocity velocity;
	CDrawable drawable;
	
	public static Ant CreateAnt()
	{
		Ant ant = new Ant();
		
		ant.drawable.CreateFromTextureRegion(ResourceManager._instance.antTextureRegionMap.get("ant"));
		
		ant.AddComponent(ant.position);
		ant.AddComponent(ant.velocity);
		ant.AddComponent(ant.drawable);
		
		return ant;
	}
	
	public Ant()
	{
		position = new CPosition();
		drawable = new CDrawable();
		velocity = new CVelocity();
	}
}

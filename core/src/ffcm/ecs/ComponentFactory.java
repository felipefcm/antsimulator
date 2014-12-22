
package ffcm.ecs;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CSelectable;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.comps.CWander;

public class ComponentFactory
{
	public static ComponentFactory _instance = new ComponentFactory();
	
	public ComponentFactory()
	{
	}
	
	public CTransform CreateTransform(final JsonValue jsonObj)
	{
		CTransform transform = new CTransform();
		
		float[] pos = jsonObj.get("position").asFloatArray();
		
		transform.position.set(pos[0], pos[1]);
		transform.rotation = jsonObj.get("rotation").asFloat();
		
		return transform;
	}
	
	public CVelocity CreateVelocity(final JsonValue jsonObj)
	{
		CVelocity cVelocity = new CVelocity();
		
		float[] velocity = jsonObj.asFloatArray();		
		
		cVelocity.vector.set(velocity[0], velocity[1]);
		
		return cVelocity;
	}
	
	public CDrawable CreateDrawable(final JsonValue jsonObj)
	{
		CDrawable drawable = new CDrawable();
		
		String spriteName = jsonObj.get("sprite").asString();
		
		drawable.CreateFromTextureRegion(ResourceManager._instance.spriteTextureRegionMap.get(spriteName));
		
		return drawable;
	}
	
	public CWander CreateWander(final JsonValue jsonObj)
	{
		CWander wander = new CWander();
		
		wander.circleRadius = jsonObj.get("radius").asFloat();
		wander.circleDistance = jsonObj.get("distance").asFloat();
		
		return wander;
	}
	
	public CSelectable CreateSelectable(final JsonValue jsonObj)
	{
		return null;
	}
}

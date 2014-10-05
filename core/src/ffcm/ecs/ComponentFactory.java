
package ffcm.ecs;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CTransform;

public class ComponentFactory
{
	public static ComponentFactory _instance = new ComponentFactory();
	
	public ComponentFactory()
	{
	}
	
	public CTransform GetTransform(final JsonValue jsonObj)
	{
		CTransform transform = new CTransform();
		
		float[] pos = jsonObj.get("position").asFloatArray();
		
		transform.position.set(pos[0], pos[1]);
		transform.rotation = jsonObj.get("rotation").asFloat();
		
		return transform;
	}
	
	public CDrawable GetDrawable(final JsonValue jsonObj)
	{
		CDrawable drawable = new CDrawable();
		
		drawable.CreateFromTextureRegion(ResourceManager._instance.antTextureRegionMap.get(jsonObj.get("sprite").asString()));
		
		return drawable;
	}
}

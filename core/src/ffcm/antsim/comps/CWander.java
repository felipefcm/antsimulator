
package ffcm.antsim.comps;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.Terrain;
import ffcm.ecs.IComponent;

public class CWander implements IComponent
{
	public float circleRadius;
	public float circleDistance;
	public float wanderAngle;
	public Terrain terrain;
	
	public CWander()
	{
		circleRadius = 1.0f;
		circleDistance = 1.0f;
		wanderAngle = 0;
	}
	
	@Override
	public IComponent Clone()
	{
		CWander wander = new CWander();
		
		wander.circleRadius = circleRadius;
		wander.circleDistance = circleDistance;
		wander.wanderAngle = wanderAngle;
		
		return wander;
	}

	@Override
	public IComponent CreateFromJson(JsonValue jsonObj)
	{
		circleRadius = jsonObj.get("radius").asFloat();
		circleDistance = jsonObj.get("distance").asFloat();
		
		return this;
	}
}

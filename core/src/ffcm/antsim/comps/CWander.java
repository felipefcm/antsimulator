
package ffcm.antsim.comps;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.ecs.IComponent;

public class CWander implements IComponent
{
	public Float circleRadius;
	public Float circleDistance;
	public Float wanderAngle;
	public Boolean avoidObstacles;
	
	public CWander()
	{
		circleRadius = 1.0f;
		circleDistance = 1.0f;
		wanderAngle = 0.0f;
		avoidObstacles = true;
	}
	
	@Override
	public IComponent Clone()
	{
		CWander wander = new CWander();
		
		wander.circleRadius = circleRadius;
		wander.circleDistance = circleDistance;
		wander.wanderAngle = wanderAngle;
		wander.avoidObstacles = avoidObstacles;
		
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

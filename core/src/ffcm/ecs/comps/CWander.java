
package ffcm.ecs.comps;

import ffcm.ecs.IComponent;

public class CWander implements IComponent
{
	public float circleRadius;
	public float circleDistance;
	public float wanderAngle;
	
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
}

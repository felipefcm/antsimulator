
package ffcm.antsim.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.math.Vector2;

import ffcm.ecs.comps.CSprite;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.comps.CWander;
import ffcm.ecs.resources.EntityTemplate;
import ffcm.ecs.systems.ai.WanderSteeringSystem;

public class Ant extends Entity implements WanderSteeringSystem.IWanderSteeringCallback
{
	public CTransform transform;
	public CVelocity velocity;
	public CSprite sprite;
	public CWander wander;

	public Ant()
	{
		add(transform = new CTransform());
		add(velocity = new CVelocity());
		add(sprite = new CSprite());
		add(wander = new CWander());
	}

	public Ant(final EntityTemplate template)
	{
		add(transform = new CTransform());
		add(velocity = new CVelocity());
		add(sprite = new CSprite(template.GetComponent(CSprite.class)));
		add(wander = new CWander(template.GetComponent(CWander.class)));
	}

	@Override
	public boolean WillApplyAcceleration(SteeringAcceleration<Vector2> accel)
	{


		return true;
	}
}

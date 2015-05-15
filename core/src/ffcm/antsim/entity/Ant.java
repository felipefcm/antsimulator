
package ffcm.antsim.entity;

import com.badlogic.ashley.core.Entity;

import ffcm.ecs.comps.CSprite;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.comps.CWander;
import ffcm.ecs.resources.EntityTemplate;

public class Ant extends Entity
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

		transform.scale.set(0.5f, 0.5f);
	}

	public Ant(final EntityTemplate template)
	{
		add(transform = new CTransform());
		add(velocity = new CVelocity());
		add(sprite = new CSprite(template.GetComponent(CSprite.class)));
		add(wander = new CWander(template.GetComponent(CWander.class)));

		transform.scale.set(0.5f, 0.5f);
	}
}

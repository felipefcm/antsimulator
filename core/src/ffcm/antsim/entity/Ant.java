
package ffcm.antsim.entity;

import com.badlogic.ashley.core.Entity;

import ffcm.ecs.comps.CSprite;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CWander;

public class Ant extends Entity
{
	public CTransform transform;
	public CSprite sprite;
	public CWander wander;

	public Ant()
	{
		add(transform = new CTransform());
		add(sprite = new CSprite());
		//add(wander = new CWander());
	}
}

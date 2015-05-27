
package ffcm.antsim.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import ffcm.antsim.AntSim;
import ffcm.antsim.AntWorld;
import ffcm.antsim.screen.SimulationScreen;
import ffcm.ecs.comps.CSpatial;
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
	public CSpatial spatialInfo;

	private AntWorld world;

	public Ant()
	{
		add(transform = new CTransform());
		add(velocity = new CVelocity());
		add(sprite = new CSprite());
		add(wander = new CWander());
		add(spatialInfo = new CSpatial(this));

		world = ((SimulationScreen) AntSim.instance.getScreen()).world;
	}

	public Ant(final EntityTemplate template)
	{
		add(transform = new CTransform());
		add(velocity = new CVelocity());
		add(sprite = new CSprite(template.GetComponent(CSprite.class)));
		add(wander = new CWander(template.GetComponent(CWander.class)));
		add(spatialInfo = new CSpatial(this));

		world = ((SimulationScreen) AntSim.instance.getScreen()).world;
	}

	@Override
	public boolean WillApplyAcceleration(SteeringAcceleration<Vector2> accel)
	{
		Vector2 nextWorldPos = transform.position.cpy()
		                        .add(sprite.sprite.getWidth() * 0.5f, sprite.sprite.getHeight() * 0.5f)
                                .add(velocity.linear.cpy().scl(Gdx.graphics.getDeltaTime() * 3.0f));

		TiledMapTileLayer.Cell cell = world.terrain.GetCell("collidable", nextWorldPos);

		if(cell != null)
		{
			accel.scl(-1.5f);
            return true;
		}

		return true;
	}
}

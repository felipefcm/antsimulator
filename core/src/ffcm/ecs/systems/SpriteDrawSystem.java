
package ffcm.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

import ffcm.ecs.comps.CRigidBody;
import ffcm.ecs.comps.CSprite;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.Mapper;
import ffcm.ecs.render.IRenderable;
import ffcm.ecs.render.RenderManager;
import ffcm.ecs.render.RenderState;

public class SpriteDrawSystem extends EntitySystem implements IRenderable
{
    private ImmutableArray<Entity> entities;

    public SpriteDrawSystem(int priority)
    {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        entities = engine.getEntitiesFor(Family.all(CSprite.class).one(CTransform.class, CRigidBody.class).get());

        RenderManager.instance.RegisterRenderable(RenderManager.RenderPass.Light, this);
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        RenderManager.instance.UnregisterRenderable(RenderManager.RenderPass.Light, this);
    }

    @Override
    public void update(float deltaTime)
    {

    }

    @Override
    public void Render(RenderState renderState)
    {
        renderState.spriteBatch.begin();
        {
            for(Entity e : entities)
            {
                CTransform transform = Mapper.transform.get(e);
                CRigidBody rigidBody = Mapper.rigidBody.get(e);
                CSprite sprite = Mapper.sprite.get(e);

                if(sprite.textureRegion == null || !sprite.visible)
                    continue;

                if(transform != null)
                {
                    renderState.spriteBatch.draw
                    (
                        sprite.textureRegion,
                        transform.position.x,
                        transform.position.y,
                        transform.origin.x,
                        transform.origin.y,
                        sprite.textureRegion.getRegionWidth(),
                        sprite.textureRegion.getRegionHeight(),
                        transform.scale.x,
                        transform.scale.y,
                        transform.rotation * MathUtils.radDeg
                    );
                }
                else //has rigid body
                {
                    Body body = rigidBody.body;

                    renderState.spriteBatch.draw
                    (
                        sprite.textureRegion,
                        body.getPosition().x,
                        body.getPosition().y,
                        body.getLocalCenter().x,
                        body.getLocalCenter().y,
                        sprite.textureRegion.getRegionWidth(),
                        sprite.textureRegion.getRegionHeight(),
                        1.0f,
                        1.0f,
                        body.getAngle() * MathUtils.radDeg
                    );
                }
            }
        }
        renderState.spriteBatch.end();
    }
}

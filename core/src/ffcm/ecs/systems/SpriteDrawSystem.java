
package ffcm.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

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
        entities = engine.getEntitiesFor(Family.all(CTransform.class, CSprite.class).get());

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
                CSprite sprite = Mapper.sprite.get(e);

                if(sprite.sprite == null || !sprite.visible)
                    continue;

                //if entity has a rigidbody component then we must adjust position according to box2d
                if(Mapper.rigidBody.has(e))
                    sprite.sprite.setPosition(transform.position.x - sprite.sprite.getWidth() * 0.5f, transform.position.y - sprite.sprite.getHeight() * 0.5f);
                else
                    sprite.sprite.setPosition(transform.position.x, transform.position.y);

                sprite.sprite.setRotation(transform.rotation);
                sprite.sprite.setScale(transform.scale.x, transform.scale.y);

                sprite.sprite.draw(renderState.spriteBatch);
            }
        }
        renderState.spriteBatch.end();
    }
}

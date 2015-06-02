
package ffcm.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import ffcm.ecs.comps.CSpriteAnimation;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.Mapper;
import ffcm.ecs.render.IRenderable;
import ffcm.ecs.render.RenderManager;
import ffcm.ecs.render.RenderState;

public class SpriteAnimationSystem extends EntitySystem implements IRenderable
{
    private ImmutableArray<Entity> entities;

    public SpriteAnimationSystem(int priority)
    {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        entities = engine.getEntitiesFor(Family.all(CSpriteAnimation.class, CTransform.class).get());

        RenderManager.instance.RegisterRenderable(RenderManager.RenderPass.TransparentLit, this);
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
    }

    @Override
    public void update(float deltaTime)
    {
        if(entities.size() <= 0)
            return;

        for(Entity e : entities)
        {
            CSpriteAnimation spriteAnimation = Mapper.spriteAnimation.get(e);

            spriteAnimation.time += deltaTime;

            while(spriteAnimation.time >= spriteAnimation.changeTime)
            {
                spriteAnimation.time -= spriteAnimation.changeTime;
                spriteAnimation.frame = ++spriteAnimation.frame % spriteAnimation.GetNumFrames();
            }
        }
    }

    @Override
    public void Render(RenderState renderState)
    {
        if(entities.size() <= 0)
            return;

        renderState.spriteBatch.begin();
        {
            for(Entity e : entities)
            {
                CSpriteAnimation spriteAnimation = Mapper.spriteAnimation.get(e);
                CTransform transform = Mapper.transform.get(e);

                renderState.spriteBatch.draw
                (
                    spriteAnimation.GetFrame(spriteAnimation.frame),
                    transform.position.x,
                    transform.position.y
                );
            }
        }
        renderState.spriteBatch.end();
    }
}

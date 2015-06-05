
package ffcm.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;

import ffcm.ecs.comps.CParticleSource;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.Mapper;
import ffcm.ecs.render.IRenderable;
import ffcm.ecs.render.RenderManager;
import ffcm.ecs.render.RenderState;

public class ParticleDrawSystem extends EntitySystem implements EntityListener, IRenderable
{
    private ImmutableArray<Entity> entities;

    public ParticleDrawSystem(int priority)
    {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        Family family = Family.all(CParticleSource.class).get();

        entities = engine.getEntitiesFor(family);
        engine.addEntityListener(family, this);

        RenderManager.instance.RegisterRenderable(RenderManager.RenderPass.TransparentLit, this);
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity)
    {
        CTransform transform = Mapper.transform.get(entity);

        if(transform != null)
        {
            CParticleSource particle = Mapper.particle.get(entity);
            particle.particleEffect.setPosition(transform.position.x, transform.position.y);
        }
    }

    @Override
    public void entityRemoved(Entity entity)
    {
        CParticleSource particle = Mapper.particle.get(entity);
        particle.particleEffect.dispose();
    }

    @Override
    public void update(float deltaTime)
    {
        for(Entity e : entities)
        {
            CParticleSource particle = Mapper.particle.get(e);

            if(particle.particleEffect.isComplete() && particle.loop)
                particle.particleEffect.reset();
        }
    }

    @Override
    public void Render(RenderState renderState)
    {
        float deltaTime = Gdx.graphics.getDeltaTime();

        renderState.spriteBatch.begin();
        {
            for(Entity e : entities)
            {
                CParticleSource particle = Mapper.particle.get(e);

                particle.particleEffect.draw(renderState.spriteBatch, deltaTime);
            }
        }
        renderState.spriteBatch.end();
    }
}

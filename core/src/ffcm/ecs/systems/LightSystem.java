
package ffcm.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import box2dLight.RayHandler;
import ffcm.ecs.comps.CLightSource;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.Mapper;
import ffcm.ecs.render.IRenderable;
import ffcm.ecs.render.RenderManager;
import ffcm.ecs.render.RenderState;

public class LightSystem extends EntitySystem implements EntityListener, IRenderable
{
    private ImmutableArray<Entity> entities;

    private PhysicsSystem physicsSystem;

    private int bufferWidth;
    private int bufferHeight;

    public RayHandler rayHandler;

    //buffer size determine the size of the FBO used by the light system
    public LightSystem(PhysicsSystem physicsSystem, int bufferWidth, int bufferHeight, int priority)
    {
        super(priority);
        this.physicsSystem = physicsSystem;
        this.bufferWidth = bufferWidth;
        this.bufferHeight = bufferHeight;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        Family family = Family.all(CLightSource.class).get();

        entities = engine.getEntitiesFor(family);
        engine.addEntityListener(family, this);

        RenderManager.instance.RegisterRenderable(RenderManager.RenderPass.Light, this);

        RayHandler.useDiffuseLight(true);
        //RayHandler.setGammaCorrection(true);

        rayHandler = new RayHandler(physicsSystem.world, bufferWidth, bufferHeight);
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        rayHandler.dispose();

        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity)
    {
        CTransform transform = Mapper.transform.get(entity);
        CLightSource source = Mapper.light.get(entity);

        if(transform != null)
            source.light.setPosition(transform.position);

        source.light.setActive(true);
    }

    @Override
    public void entityRemoved(Entity entity)
    {
        CLightSource source = Mapper.light.get(entity);

        source.light.dispose();
    }

    @Override
    public void update(float deltaTime)
    {
    }

    @Override
    public void Render(RenderState renderState)
    {
        if(entities.size() <= 0)
            return;

        rayHandler.setCombinedMatrix(renderState.mainCamera.combined.cpy().scl(PhysicsSystem.MeterToPixel));
        rayHandler.updateAndRender();
    }
}

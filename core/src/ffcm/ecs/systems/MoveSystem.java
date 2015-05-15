
package ffcm.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ffcm.ecs.ECSManager;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.comps.Mapper;
import ffcm.ecs.render.RenderManager;

public class MoveSystem extends IteratingSystem
{
    public boolean debug = true;

    public MoveSystem(int priority)
    {
        super(Family.all(CTransform.class, CVelocity.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CTransform transform = Mapper.transform.get(entity);
        CVelocity velocity = Mapper.velocity.get(entity);

        transform.position.add(velocity.linear.cpy().scl(deltaTime));
        transform.rotation = velocity.linear.angle();

        //no render pass for debug rendering
        if(debug)
        {
            ShapeRenderer shapeRenderer = RenderManager.instance.state.shapeRenderer;

            shapeRenderer.setProjectionMatrix(ECSManager.instance.ecsConfig.mainCamera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            {
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.line(transform.position, transform.position.cpy().add(velocity.linear));
            }
            shapeRenderer.end();
        }
    }
}

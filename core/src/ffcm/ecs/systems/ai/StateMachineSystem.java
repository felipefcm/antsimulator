
package ffcm.ecs.systems.ai;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import ffcm.ecs.comps.Mapper;
import ffcm.ecs.comps.ai.CStateMachine;

public class StateMachineSystem extends IteratingSystem
{
    public StateMachineSystem(int priority)
    {
        super(Family.all(CStateMachine.class).get(), priority);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        super.addedToEngine(engine);
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        super.removedFromEngine(engine);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CStateMachine stateMachine = Mapper.stateMachine.get(entity);

        stateMachine.machine.update();
    }
}

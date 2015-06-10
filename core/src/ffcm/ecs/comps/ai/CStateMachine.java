
package ffcm.ecs.comps.ai;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;

public class CStateMachine<T> extends Component
{
    public StateMachine<T> machine;

    public CStateMachine(T owner, State<T> initialState)
    {
        machine = new DefaultStateMachine<>(owner, initialState);
    }

    public CStateMachine(CStateMachine<T> machine)
    {
        this.machine = machine.machine;
    }


}

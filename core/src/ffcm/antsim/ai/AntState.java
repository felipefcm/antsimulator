
package ffcm.antsim.ai;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

import ffcm.antsim.entity.Ant;

public enum AntState implements State<Ant>
{
    Wander()
    {
        @Override
        public void update(Ant entity)
        {

        }
    };

    @Override
    public void enter(Ant entity)
    {

    }

    @Override
    public void exit(Ant entity)
    {

    }

    @Override
    public boolean onMessage(Ant entity, Telegram telegram)
    {
        return false;
    }
}


package ffcm.antsim.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;

import ffcm.antsim.resource.Log;
import ffcm.antsim.resource.Resources;
import ffcm.antsim.screen.SimulationScreen;

public enum SimulationState implements State<SimulationScreen>
{
    GLOBAL()
    {
        @Override
        public void enter(SimulationScreen entity)
        {
        }

        @Override
        public void update(SimulationScreen entity)
        {
            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                entity.stateMachine.changeState(IDLE);
        }

        @Override
        public void exit(SimulationScreen entity)
        {
        }

        @Override
        public boolean onMessage(SimulationScreen entity, Telegram telegram)
        {
            return false;
        }
    },

    IDLE()
    {
        @Override
        public void enter(SimulationScreen entity)
        {
        }

        @Override
        public void update(SimulationScreen entity)
        {
        }

        @Override
        public void exit(SimulationScreen entity)
        {
        }

        @Override
        public boolean onMessage(SimulationScreen entity, Telegram telegram)
        {
            switch(telegram.message)
            {
                case SimulationState.SET_SELECT_ITEM_MSG:
                    entity.stateMachine.changeState(SELECT_ITEM);
                break;
            }

            return false;
        }
    },

    SELECT_ITEM()
    {
        @Override
        public void enter(SimulationScreen entity)
        {
        }

        @Override
        public void update(SimulationScreen entity)
        {
            Vector2 screenPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldPos = Resources.instance.viewport.unproject(screenPos);

            Log.Debug("MOUSE POS:" + worldPos.toString());
        }

        @Override
        public void exit(SimulationScreen entity)
        {
        }

        @Override
        public boolean onMessage(SimulationScreen entity, Telegram telegram)
        {
            return false;
        }
    };

    public static final int SET_SELECT_ITEM_MSG = 1;
}

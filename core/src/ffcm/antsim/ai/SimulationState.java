
package ffcm.antsim.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ffcm.antsim.entity.Food;
import ffcm.antsim.resource.Log;
import ffcm.antsim.resource.Resources;
import ffcm.antsim.screen.SimulationScreen;
import ffcm.ecs.resources.quadtree.QuadTreeData;
import ffcm.ecs.systems.SpatialPartitioningSystem;

public enum SimulationState implements State<SimulationScreen>
{
    GLOBAL()
    {
        @Override
        public void enter(SimulationScreen entity)
        {
            //global state
        }

        @Override
        public void update(SimulationScreen entity)
        {
            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                entity.stateMachine.changeState(IDLE);

            State<SimulationScreen> currentState = entity.stateMachine.getCurrentState();

            if(currentState == SELECT_ITEM || currentState == SELECT_AREA)
            {
                worldPos.set(Gdx.input.getX(), Gdx.input.getY());
                Resources.instance.viewport.unproject(worldPos);
            }
        }

        @Override
        public void exit(SimulationScreen entity)
        {
            //global state
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
                    selectClass = Food.class;
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
            SpatialPartitioningSystem system = Resources.instance.spatialPartitioningSystem;

            searchArea.x = worldPos.x - searchArea.width * 0.5f;
            searchArea.y = worldPos.y - searchArea.height * 0.5f;

            QuadTreeData[] entities = system.quadTree.SearchArea(searchArea);

            for(QuadTreeData data : entities)
            {
                if(selectClass.isInstance(data.info)) //TODO make possible to select other types
                {
                    Log.Debug("Found food! id=" + ((Entity) data.info).getId());
                }
            }
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

    SELECT_AREA()
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
            return false;
        }
    };

    public static final int SET_SELECT_ITEM_MSG = 1;

    //SELECT states will keep updating this value
    private static Vector2 worldPos = new Vector2();

    private static final Rectangle searchArea = new Rectangle(0, 0, 30.0f, 30.0f);

    private static Class selectClass;
}


package ffcm.antsim.ecs.comps;

import com.badlogic.ashley.core.Component;

public class CFoodDecay extends Component
{
    public float timeToDecay; //in seconds
    public float decayTimer;
    public boolean usingDecaySprite;

    public CFoodDecay()
    {
        timeToDecay = 5f;
        decayTimer = 0f;
        usingDecaySprite = false;
    }

    public CFoodDecay(final CFoodDecay food)
    {
        timeToDecay = food.timeToDecay;
        decayTimer = food.decayTimer;
    }
}

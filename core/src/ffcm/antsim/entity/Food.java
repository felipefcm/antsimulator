
package ffcm.antsim.entity;

import com.badlogic.ashley.core.Entity;

import ffcm.antsim.ecs.comps.CFoodDecay;
import ffcm.ecs.comps.CSpriteAnimation;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.resources.EntityTemplate;

public class Food extends Entity
{
    public CTransform transform;
    public CSpriteAnimation decayAnimation;
    public CFoodDecay foodDecay;

    public Food()
    {
        add(transform = new CTransform());
        add(decayAnimation = new CSpriteAnimation(0));
        add(foodDecay = new CFoodDecay());
    }

    public Food(final EntityTemplate template)
    {
        add(transform = new CTransform());
        add(decayAnimation = new CSpriteAnimation(0));
        add(foodDecay = new CFoodDecay());
    }
}

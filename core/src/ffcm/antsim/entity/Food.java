
package ffcm.antsim.entity;

import com.badlogic.ashley.core.Entity;

import ffcm.antsim.ecs.comps.CFoodDecay;
import ffcm.ecs.comps.CSprite;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.resources.EntityTemplate;

public class Food extends Entity
{
    public CTransform transform;
    public CSprite sprite;
    public CFoodDecay foodDecay;

    public Food()
    {
        add(transform = new CTransform());
        add(sprite = new CSprite());
        add(foodDecay = new CFoodDecay());
    }

    public Food(final EntityTemplate template)
    {
        add(transform = new CTransform());
        add(sprite = new CSprite(template.GetComponent(CSprite.class)));
        add(foodDecay = new CFoodDecay());
    }
}

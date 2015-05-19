
package ffcm.antsim.entity;

import com.badlogic.ashley.core.Entity;

import ffcm.ecs.comps.CSprite;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.resources.EntityTemplate;

public class Nest extends Entity
{
    public CTransform transform;
    public CSprite sprite;

    public Nest()
    {
        add(transform = new CTransform());
        add(sprite = new CSprite());
    }

    public Nest(final EntityTemplate template)
    {
        add(transform = new CTransform());
        add(sprite = new CSprite(template.GetComponent(CSprite.class)));
    }
}

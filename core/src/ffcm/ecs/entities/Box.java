
package ffcm.ecs.entities;

import com.badlogic.ashley.core.Entity;

import ffcm.ecs.comps.CCollider;
import ffcm.ecs.comps.CRigidBody;
import ffcm.ecs.comps.CSprite;
import ffcm.ecs.comps.CTransform;

public class Box extends Entity
{
    public CTransform transform;
    public CSprite sprite;

    public CRigidBody rigidBody;
    public CCollider collider;

    public Box()
    {
        add(transform = new CTransform());
        add(sprite = new CSprite());
        add(rigidBody = new CRigidBody());
        add(collider = new CCollider());
    }
}

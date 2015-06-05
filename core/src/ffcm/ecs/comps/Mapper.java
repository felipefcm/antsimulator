
package ffcm.ecs.comps;

import com.badlogic.ashley.core.ComponentMapper;

public class Mapper
{
    public static final ComponentMapper<CTransform> transform = ComponentMapper.getFor(CTransform.class);
    public static final ComponentMapper<CVelocity> velocity = ComponentMapper.getFor(CVelocity.class);
    public static final ComponentMapper<CSprite> sprite = ComponentMapper.getFor(CSprite.class);
    public static final ComponentMapper<CRigidBody> rigidBody = ComponentMapper.getFor(CRigidBody.class);
    public static final ComponentMapper<CCollider> collider = ComponentMapper.getFor(CCollider.class);
    public static final ComponentMapper<CParticleSource> particle = ComponentMapper.getFor(CParticleSource.class);
    public static final ComponentMapper<CLightSource> light = ComponentMapper.getFor(CLightSource.class);
    public static final ComponentMapper<CSpriteAnimation> spriteAnimation = ComponentMapper.getFor(CSpriteAnimation.class);
    public static final ComponentMapper<CWander> wander = ComponentMapper.getFor(CWander.class);
    public static final ComponentMapper<CSpatial> spatial = ComponentMapper.getFor(CSpatial.class);
}

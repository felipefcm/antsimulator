
package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;

public class CRigidBody extends Component
{
    public BodyDef bodyDef;
    public FixtureDef fixtureDef;
    public MassData massData;
    public Body body;

    public CRigidBody()
    {
        bodyDef = new BodyDef();

        fixtureDef = new FixtureDef();

        massData = new MassData();
    }
}

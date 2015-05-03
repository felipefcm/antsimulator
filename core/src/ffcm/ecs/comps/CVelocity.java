
package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class CVelocity extends Component
{
    public Vector2 linear;
    public float angular;

    public CVelocity()
    {
        linear = new Vector2(0, 0);
        angular = 0;
    }
}

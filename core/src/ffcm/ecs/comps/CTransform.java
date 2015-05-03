
package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class CTransform extends Component
{
    public Vector2 position;
    public Vector2 scale;
    public float rotation;

    public CTransform()
    {
        position = new Vector2(0, 0);
        scale = new Vector2(1.0f, 1.0f);
        rotation = 0;
    }
}


package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CSprite extends Component
{
    public Sprite sprite;
    public boolean visible = true;

    public CSprite()
    {
    }

    public CSprite(CSprite sprite)
    {
        this.sprite = sprite.sprite;
        visible = sprite.visible;
    }
}


package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.JsonValue;

import ffcm.ecs.ECSManager;
import ffcm.ecs.resources.ILoadableFromJSON;

public class CSprite extends Component implements ILoadableFromJSON
{
    public Sprite sprite;
    public boolean visible = true;

    public CSprite()
    {
    }

    public CSprite(CSprite sprite)
    {
        this.sprite = new Sprite(sprite.sprite);
        visible = sprite.visible;
    }

    @Override
    public void LoadFromJSON(JsonValue jsonValue)
    {
        String atlasPath = jsonValue.get("atlas").asString();
        String spriteName = jsonValue.get("name").asString();

        TextureAtlas atlas = ECSManager.instance.ecsConfig.assetManager.get(atlasPath, TextureAtlas.class);

        sprite = new Sprite(atlas.findRegion(spriteName));
    }
}

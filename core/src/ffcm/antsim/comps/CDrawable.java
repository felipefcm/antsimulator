
package ffcm.antsim.comps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.IComponent;

public class CDrawable implements IComponent
{
	public Sprite sprite;
	
	public CDrawable()
	{
		sprite = null;
	}
	
	public void CreateFromTexture(Texture texture)
	{
		sprite = new Sprite(texture);
	}
	
	public void CreateFromTextureRegion(TextureRegion region)
	{
		sprite = new Sprite(region);
	}

	@Override
	public IComponent Clone()
	{
		CDrawable drawable = new CDrawable();
		drawable.sprite = sprite;
		
		return drawable;
	}

	@Override
	public IComponent CreateFromJson(final JsonValue jsonObj)
	{
		String spriteName = jsonObj.get("sprite").asString();
		
		CreateFromTextureRegion(ResourceManager._instance.spriteTextureRegionMap.get(spriteName));
		
		return this;
	}
}

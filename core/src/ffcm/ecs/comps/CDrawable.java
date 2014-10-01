
package ffcm.ecs.comps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CDrawable
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
}

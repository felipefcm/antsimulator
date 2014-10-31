
package ffcm.antsim;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.resource.PerlinNoise2d;
import ffcm.antsim.resource.ResourceManager;

public class Terrain
{
	private PerlinNoise2d perlin;
	private Vector2 size;
	private Texture texture;
	private Sprite sprite;
	
	private Viewport viewport;
	private SpriteBatch spriteBatch;
	
	public Terrain(Vector2 size)
	{
		perlin = new PerlinNoise2d(ResourceManager._instance.random, 16);
		this.size = size;
		
		viewport = ResourceManager._instance.viewport;
		spriteBatch = ResourceManager._instance.spriteBatch;
		
		GenerateTerrainTexture();
	}
	
	private void GenerateTerrainTexture()
	{
		Pixmap pixmap = new Pixmap((int) (size.x / 2.0f), (int) (size.y / 2.0f), Format.RGB888);
		
		for(int r = 0; r < pixmap.getHeight(); ++r)
		{
			for(int c = 0; c < pixmap.getWidth(); ++c)
			{
				float frequency = 1.0f / (float) pixmap.getWidth();
				float amplitude = 1.0f;
				
				float value = 0;
				
				for(int oc = 0; oc < 12; ++oc)
				{
					value += perlin.GetNoiseValue(c * frequency, r * frequency) * amplitude;
					frequency *= 2.0f; //lacunarity
					amplitude *= 0.5f; //gain
				}
				
				value = Math.abs(value);
				
				pixmap.drawPixel(c, r, Color.rgba8888(value, value, value, 1.0f));
			}
		}
		
		texture = new Texture(pixmap);
		sprite = new Sprite(texture);
		
		sprite.setScale(2.0f);
		sprite.setPosition(-texture.getWidth() / 2.0f, -texture.getHeight() / 2.0f);
		
		pixmap.dispose();
	}
	
	public void Draw()
	{
		if(sprite == null)
			return;
		
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		spriteBatch.begin();
		{
			sprite.draw(spriteBatch);
		}
		spriteBatch.end();
	}
	
	public void Dispose()
	{
		if(texture != null)
			texture.dispose();
	}
}













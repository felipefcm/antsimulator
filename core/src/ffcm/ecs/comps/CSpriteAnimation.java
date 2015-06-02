
package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class CSpriteAnimation extends Component
{
    private Array<TextureRegion> regions;

    public int frame = 0;
    public float time = 0;
    public float changeTime = 0;

    private int fps = 24;

    public CSpriteAnimation(int fps)
    {
        this(null, fps);
    }

    public CSpriteAnimation(Array<TextureRegion> regions, int fps)
    {
        this.regions = regions;

        SetFPS(fps);
    }

    public CSpriteAnimation(CSpriteAnimation animation)
    {
        regions = animation.regions;
        frame = animation.frame;
        time = animation.time;
        changeTime = animation.changeTime;
        fps = animation.fps;
    }

    public void SetFPS(int fps)
    {
        this.fps = fps;

        time = 0;
        changeTime = 1 / (float) fps;
    }

    public void SetRegions(Array<TextureRegion> regions)
    {
        this.regions = regions;
    }

    public void CreateFromTexture(Texture texture, int rows, int cols)
    {
        int regionWidth = (int)(texture.getWidth() / (float) cols);
        int regionHeight = (int)(texture.getHeight() / (float) rows);

        regions = new Array<>(rows * cols);

        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < cols; ++j)
            {
                TextureRegion region = new TextureRegion
                (
                    texture,
                    j * regionWidth,
                    i * regionHeight,
                    regionWidth,
                    regionHeight
                );

                regions.add(region);
            }
        }
    }

    public void AddFrame(TextureRegion region)
    {
        if(regions == null)
            return;

        regions.add(region);
    }

    public int GetNumFrames()
    {
        if(regions == null)
            return -1;

        return regions.size;
    }

    public TextureRegion GetFrame(int numFrame)
    {
        if(regions == null)
            return null;

        if(numFrame >= regions.size)
            return null;

        return regions.get(numFrame);
    }
}

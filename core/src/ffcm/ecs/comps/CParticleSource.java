
package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class CParticleSource extends Component
{
    public ParticleEffect particleEffect;
    public boolean loop = true;

    public CParticleSource()
    {
        particleEffect = new ParticleEffect();
    }

    public void LoadEffect(FileHandle emitterFile, FileHandle imagesDir)
    {
        particleEffect.load(emitterFile, imagesDir);
    }
}

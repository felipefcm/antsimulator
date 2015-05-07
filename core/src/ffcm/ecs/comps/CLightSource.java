
package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.Light;
import box2dLight.PointLight;
import ffcm.ecs.systems.LightSystem;
import ffcm.ecs.systems.PhysicsSystem;

public class CLightSource extends Component
{
    public Light light;

    private LightSystem lightSystem;

    public CLightSource(LightSystem lightSystem, PhysicsSystem physicsSystem)
    {
        this.lightSystem = lightSystem;
    }

    public CLightSource(CLightSource source)
    {
        light = source.light;
        lightSystem = source.lightSystem;
    }

    public void SetAsPointLight(int numRays, Color color, float distance)
    {
        SetAsPointLight(numRays, color, distance, 0, 0);
    }

    public void SetAsPointLight(int numRays, Color color, float distance, float x, float y)
    {
        light = new PointLight
        (
            lightSystem.rayHandler,
            numRays,
            color,
            distance * PhysicsSystem.PixelToMeter,
            x * PhysicsSystem.PixelToMeter, y * PhysicsSystem.PixelToMeter
        );

        light.setActive(false);
    }

    public void SetAsConeLight(int numRays, Color color, float distance)
    {
        SetAsConeLight(numRays, color, distance, 0, 0, -90.0f, 45.0f);
    }

    public void SetAsConeLight(int numRays, Color color, float distance, float x, float y, float directionDegree, float coneDegree)
    {
        light = new ConeLight
        (
            lightSystem.rayHandler,
            numRays,
            color,
            distance * PhysicsSystem.PixelToMeter,
            x * PhysicsSystem.PixelToMeter, y * PhysicsSystem.PixelToMeter,
            directionDegree,
            coneDegree
        );

        light.setActive(false);
    }

    public void SetAsDirectionalLight(int numRays, Color color, float directionDegree)
    {
        light = new DirectionalLight
        (
            lightSystem.rayHandler,
            numRays,
            color,
            directionDegree
        );

        light.setActive(false);
    }
}

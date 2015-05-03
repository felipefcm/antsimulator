
package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import ffcm.ecs.systems.PhysicsSystem;

public class CCollider extends Component
{
    public enum ShapeType
    {
        None,
        Polygon,
        Circle,
        Edge,
        Chain
    }

    public Shape shape;
    public ShapeType shapeType;

    public CCollider()
    {
        shapeType = ShapeType.None;
    }

    public void SetAsBox(float width, float height)
    {
        PolygonShape poly = new PolygonShape();

        poly.setAsBox
        (
            width * 0.5f * PhysicsSystem.PixelToMeter,
            height * 0.5f * PhysicsSystem.PixelToMeter
        );

        shape = poly;
        shapeType = ShapeType.Polygon;
    }

    public void SetAsBox(float width, float height, Vector2 center, float angle)
    {
        PolygonShape poly = new PolygonShape();

        poly.setAsBox
        (
            width * 0.5f * PhysicsSystem.PixelToMeter,
            height * 0.5f * PhysicsSystem.PixelToMeter,
            center.scl(PhysicsSystem.PixelToMeter),
            angle * MathUtils.degRad
        );

        shape = poly;
        shapeType = ShapeType.Polygon;
    }

    public void SetAsCircle(float radius)
    {
        CircleShape circle = new CircleShape();
        circle.setRadius(radius * PhysicsSystem.PixelToMeter);

        shape = circle;
        shapeType = ShapeType.Circle;
    }

    public void SetAsEdge(Vector2 v1, Vector2 v2)
    {
        EdgeShape edge = new EdgeShape();
        edge.set(v1.scl(PhysicsSystem.PixelToMeter), v2.scl(PhysicsSystem.PixelToMeter));

        shape = edge;
        shapeType = ShapeType.Edge;
    }

    public void SetAsChain(Vector2[] points)
    {
        ChainShape chain = new ChainShape();

        for(Vector2 v : points)
            v.scl(PhysicsSystem.PixelToMeter);

        chain.createChain(points);

        shape = chain;
        shapeType = ShapeType.Chain;
    }

    public void SetAsChainLoop(Vector2[] points)
    {
        ChainShape chain = new ChainShape();

        for(Vector2 v : points)
            v.scl(PhysicsSystem.PixelToMeter);

        chain.createLoop(points);

        shape = chain;
        shapeType = ShapeType.Chain;
    }
}


package ffcm.antsim.entity;

import ffcm.ecs.ECSManager;

public class EntityFactory
{
    public static EntityFactory instance = new EntityFactory();

    public Ant CreateAnt()
    {
        Ant ant = new Ant(ECSManager.instance.entityTemplateManager.GetTemplate(Ant.class));

        ant.sprite.sprite.setBounds(0, 0, 8.0f, 5.2f);
        ant.sprite.sprite.setOriginCenter();

        ECSManager.instance.ecsEngine.addEntity(ant);

        return ant;
    }

    public Nest CreateNest()
    {
        Nest nest = new Nest(ECSManager.instance.entityTemplateManager.GetTemplate(Nest.class));

        ECSManager.instance.ecsEngine.addEntity(nest);

        return nest;
    }
}

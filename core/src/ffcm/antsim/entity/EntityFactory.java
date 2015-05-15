
package ffcm.antsim.entity;

import ffcm.ecs.ECSManager;

public class EntityFactory
{
    public static EntityFactory instance = new EntityFactory();

    public Ant CreateAnt()
    {
        Ant ant = new Ant(ECSManager.instance.entityTemplateManager.GetTemplate(Ant.class));

        ECSManager.instance.ecsEngine.addEntity(ant);

        return ant;
    }
}

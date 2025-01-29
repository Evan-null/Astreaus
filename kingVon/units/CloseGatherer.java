package teams.student.kingVon.units;

import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;

public class CloseGatherer extends KingVonUnit
{
    public CloseGatherer(KingVon p)
    {
        super(p);
    }

    public void design()
    {
        setFrame(Frame.LIGHT);
        setModel(Model.TRANSPORT);
        setStyle(Style.BUBBLE);
        add(Collector.class);
    }

    public void action()
    {
        returnResources();
        gatherResources();
    }

    public void returnResources()
    {
        if(isFull() || (!isEmpty() && getNearestResource() == null))
        {
            moveTo(getHomeBase());
            deposit();
        }
    }

    public void gatherResources()
    {
        if(hasCapacity())
        {
            Resource r = getNearestResource();
            if((r != null) && (getDistance(r) < getMaxRange()))
            {
                moveTo(r);
                ((Collector) getWeaponOne()).use(r);
            }
            else if (getDistance(r) < getMaxRange() * .2 && getDistance(r) > getMaxRange())
            {
                moveTo(r);
            }
            else
            {
                moveTo(getHomeBase().getCenterX() - 20, getHomeBase().getCenterY());
            }
        }
    }




}

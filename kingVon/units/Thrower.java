package teams.student.kingVon.units;

import components.weapon.economy.Collector;
import engine.Main;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;

import static objects.resource.ResourceManager.getResources;

public class Thrower extends KingVonUnit
{
    boolean stopped;

    public Thrower(KingVon p)
    {
        super(p);
        stopped = false;
    }

    public void design()
    {
        setFrame(Frame.LIGHT);
        setModel(Model.TRANSPORT);
        setStyle(Style.PINCER);
        add(Collector.class);
    }

    public void action()
    {
        Resource r = getNearestResourceNotDumped();

        throwResources(r);
        gatherResources(r);
    }

    public void gatherResources(Resource r)
    {
        if (hasCapacity())
        {
            if (r != null)
            {
                moveTo(r);
                ((Collector) getWeaponOne()).use(r);
            }
            else
            {
                moveTo(getHomeBase());
            }
        }
    }

    public void throwResources(Resource r)
    {
        if ((isFull()) || (!isEmpty() && getCurEffectiveHealth() < getMaxEffectiveHealth()) || (!isEmpty() && r == null))
        {
            if (!stopped)
            {
                turn(stopAngle());
                if (getCurSpeed() > .05)
                {
                    move();
                }
                else
                {
                    stopped = true;
                }
            }
            else
            {
                moveTo(getHomeBase());
                if (getCurSpeed() == getMaxSpeed())
                {
                    dump();
                }
            }
        }
    }

    public boolean isMovingTowardsHome(Resource r)
    {
        float vectorAngle = (float) (Math.toDegrees(Math.atan2(r.getSpeedY(), r.getSpeedX())));
        float homeToAngle = r.getAngleToward(getHomeBase().getCenterX(), getHomeBase().getCenterY());

        vectorAngle = (vectorAngle + 360) % 360;
        homeToAngle = (homeToAngle + 360) % 360;

        if ((vectorAngle >= (homeToAngle - 5) && vectorAngle <= (homeToAngle + 5)) && (r.getPercentSpeed() > .1))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Resource getNearestResourceNotDumped()
    {
        Resource temp = null;
        float minDist = 100000;

        for(Resource r : getResources())
        {
            if (!isMovingTowardsHome(r) && r.isInBounds() && (r.getDistance(getHomeBase()) > 750) && r.getDistance(this) < minDist)
            {
                temp = r;
                minDist = r.getDistance(this);
            }
        }
        return temp;
    }

    public float stopAngle ()
    {
        float vectorAngle = (float) Math.toDegrees(Math.atan2(getSpeedY(), getSpeedX()));
        float stopAngle = vectorAngle + 180;

        if (stopAngle >= 360)
        {
            stopAngle = stopAngle - 360;
        }

        return stopAngle - getTheta();
    }

}


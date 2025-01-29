package teams.student.kingVon.units;

import components.mod.healing.ApolloMod;
import components.mod.healing.PerseusMod;
import components.upgrade.Shield;
import components.weapon.utility.RepairBeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;

import java.util.ArrayList;

public class Healer2 extends KingVonUnit
{
    public Healer2 (KingVon p)
    {
        super(p);
    }
    public void design()
    {
        setFrame(Frame.LIGHT);
        setModel(Model.STRIKER);
        setStyle(Style.SHARK);

        add(RepairBeam.class);
        add(ApolloMod.class);
    }

    public void movement()
    {
        Unit ally = getHealTarget();

        if (this.getCenterX() > ally.getCenterX())
        {
            moveTo(getHomeBase());
        }
        else if((getDistance(ally) > getMaxRange()))
        {
            moveTo(ally);
        }
    }

    public void action()
    {
        Unit ally = getHealTarget();
        getWeaponOne().use(ally);
        movement();
    }

    public Unit getHealTarget()
    {
        ArrayList<Unit> attackers = new ArrayList<Unit>();
        for(Unit u : getAllies())
        {
            if(u != this && u.isDamaged() && !(u instanceof Miner) && !(u instanceof Gatherer) && !(u instanceof Healer2))
            {
                return u;
            }
        }
        return getNearestAllyFighter();
    }

    public Unit getNearestAllyFighter()
    {
        for(Unit u : getAlliesExcludeBaseShip())
        {
            if(u != this && !(u instanceof Miner) && !(u instanceof Gatherer)&& !(u instanceof Healer2))
            {
                return u;
            }
        }
        return null;
    }
}

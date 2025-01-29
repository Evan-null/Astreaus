package teams.student.kingVon.units;

import components.mod.healing.PerseusMod;
import components.mod.offense.KratosMod;
import components.upgrade.HeavyShield;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.RepairBeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;

public class Healer extends KingVonUnit
{
    public Healer (KingVon p)
    {
        super(p);
    }

    public void design()
    {
        setFrame(Frame.MEDIUM);
        setModel(Model.STRIKER);
        setStyle(Style.SHARK);

        add(RepairBeam.class);
        add(PerseusMod.class);
        add(Shield.class);
    }

    public void movement()
    {

        Unit ally = getHealTarget();

        if(getDistance(ally) < getMaxRange() * 1.5 && getDistance(ally) > getMaxRange())
        {
            moveTo(ally);
        }
        else
        {
            moveTo(getHomeBase());
        }
    }

    public void action()
    {
        Unit ally = getHealTarget();
        if(ally != null)
        {
            moveTo(ally);
            getWeaponOne().use(ally);
        }
        else
        {
            moveTo(getHomeBase());
        }
    }

    public Unit getHealTarget()
    {
        for(Unit u : getAllies())
        {
            if(u != this && u.isDamaged() && u instanceof Defence)
            {
                return u;
            }
        }
        return null;
    }
}

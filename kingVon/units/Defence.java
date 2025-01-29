package teams.student.kingVon.units;

import components.mod.offense.KratosMod;
import components.mod.offense.ZeusMod;
import components.upgrade.HeavyShield;
import objects.entity.unit.Unit;
import teams.student.kingVon.KingVonUnit;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;

public class Defence extends KingVonUnit
{
    public Defence(KingVon p)
    {
        super(p);
    }

    public void design()
    {
        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.ORB);

        add(Autocannon.class);
        add(KratosMod.class);
        add(HeavyShield.class);
        add(HeavyShield.class);
    }

    public void movement()
    {
        Unit enemy = getNearestEnemy();

        if(getDistance(enemy) < getMaxRange() * 5 && getDistance(enemy) > getMaxRange())
        {
            moveTo(enemy);
        }
        else
        {
            moveTo(getHomeBase());
        }
    }
}

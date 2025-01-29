package teams.student.kingVon.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.economy.Collector;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import player.Player;
import teams.student.kingVon.KingVonUnit;

import java.util.ArrayList;

public class Flanker extends KingVonUnit {

    private static final float MIN_DISTANCE_FROM_HOME = 1200f;


    public Flanker(Player p) {
        super(p);
    }

    public void design() {
        setFrame(Frame.LIGHT);
        setModel(Model.STRIKER);
        setStyle(Style.DAGGER);

        add(Autocannon.class);
        add(Laser.class);
//        add(Shield.class);
//        add(Plating.class);
    }

    public void action()
    {
        Unit target = getNearestDistantGatherer();

        if (target == null)
        {

            target = getEnemyBase();
            if(target == null)
            {
                move();
                return;
            }

            if(getDistance(target) > getMaxRange() * .75f)
            {
                moveTo(target);
            }
            else
            {
                turnTo(target);
                move();
            }
        }
        else
        {
            if(getDistance(target) > getMaxRange() * .75f)
            {
                moveTo(target);
            }
            else
            {
                turnTo(target);
                move();
            }
        }


        if (!getWeaponOne().onCooldown() && getDistance(target) <= getMaxRange())
        {
            getWeaponOne().use(target);
        }
    }

    private Unit getNearestDistantGatherer()
    {
        ArrayList<Unit> enemies = getEnemies();


        if(enemies.isEmpty())
        {
            return null;
        }
        Unit nearestGatherer = null;
        float minDistance = Float.MAX_VALUE;

        for (Unit u : enemies)
        {
            if (u.hasWeapon(Collector.class) && getHomeBase().getDistance(u) > MIN_DISTANCE_FROM_HOME)
            {
                float distance = getDistance(u);
                if(distance < minDistance)
                {
                    nearestGatherer = u;
                    minDistance = distance;
                }
            }
        }
        return nearestGatherer;
    }
}
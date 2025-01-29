package teams.student.kingVon.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import player.Player;
import teams.student.kingVon.KingVonUnit;

import java.util.ArrayList;

public class Fighter extends KingVonUnit {

	private boolean isInFormation = true;
	private static final float DEFEND_RANGE = 1000f;
	private static final float ORBIT_RADIUS = 500f;
	int timer;

	public Fighter(Player p) {
		super(p);
		timer = 0;
	}

	public void design() {
		setFrame(Frame.HEAVY);
		setModel(Model.CRUISER);
		setStyle(Style.ARROW);
		add(Autocannon.class);
		add(Laser.class);
		add(Shield.class);
		add(Plating.class);
	}

	public void action() {
		Unit enemy = getNearestEnemy();
		timer++;

		if (isInFormation)
		{
			if (getPlayer().countMyUnits(Fighter.class) > 15)
			{
				isInFormation = false;

			}
			else
			{
				moveTo(getHomeBase());
				if(enemy != null && getDistance(enemy) <= DEFEND_RANGE)
				{
					getWeaponOne().use(enemy);
					getWeaponTwo().use(enemy);
				}

			}
		}
		moveTo(enemy);

		if (!isInFormation)
		{
			movement();
			Unit target = getLowestHealthEnemyInRadius(getMaxRange());
			if(target != null)
			{
				getWeaponOne().use(target);
				getWeaponTwo().use(target);
			}

		}

		moveTo(enemy);
	}

	@Override
	public void movement()
	{
		if(!isInFormation) {
			Unit target = getLowestHealthEnemyInRadius(getMaxRange());
			if (target != null) {
				float angle = getAngleToward(target.getCenterX(), target.getCenterY());
				float orbitX = target.getCenterX() + ORBIT_RADIUS * (float) Math.cos(Math.toRadians(angle) + (float) ((this.hashCode() % 100) *3.6) );
				float orbitY = target.getCenterY() + ORBIT_RADIUS * (float) Math.sin(Math.toRadians(angle) + (float) ((this.hashCode() % 100)*3.6)) ;

				moveTo(orbitX, orbitY);
			}
			else
			{
				moveTo(getHomeBase());
			}

		}

	}


	private Unit getLowestHealthEnemyInRadius(float radius)
	{
		ArrayList<Unit> enemies = getEnemiesInRadius(radius, Unit.class);
		if(enemies.isEmpty())
		{
			return null;
		}
		Unit lowestHealthUnit = null;
		float minHealth = Float.MAX_VALUE;
		for(Unit u : enemies)
		{
			float health = u.getCurEffectiveHealth();
			if(health < minHealth)
			{
				minHealth = health;
				lowestHealthUnit = u;
			}
		}
		return lowestHealthUnit;
	}


	public void early()
	{

	}
	public void mid()
	{

	}
}
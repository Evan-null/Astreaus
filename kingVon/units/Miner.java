package teams.student.kingVon.units;


import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.energy.Laser;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;

public class Miner extends KingVonUnit
{
	int timer;
	public Miner(KingVon p)
	{
		super(p);
		timer = 0;
	}
	
	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.ARTILLERY);
		setStyle(Style.BOXY);
		if (timer > 45000)
		{
			add(Laser.class);
		}
		else
		{
			add(Drillbeam.class);
		}
	}

	public void action() 
	{
		timer++;
		if (timer > 45000)
		{
			endGameStrategy();
		}
		else
		{
			harvest(getNearestNode(), getWeaponOne());
		}
	}

	public void harvest(Node n, Weapon w)
	{
		// Approach the node
		if(getDistance(n) > w.getMaxRange() * .5f)
		{
			moveTo(n);
		}
		// Back up if I'm close to my minimum range
		else if(getDistance(n) < w.getMinRange() * 1.5f)
		{
			turnTo(n);
			turnAround();
			move();
		}
		w.use(n);
	}

	public void endGameStrategy()
	{
		if (getPlayer().countMyUnits() >= getPlayer().getMaxFleetSize())
		{
			die();
		}
		else
		{
			Unit enemy = getNearestEnemy();

			if(enemy != null && getWeaponOne() != null)
			{
				getWeaponOne().use(enemy);
			}

			if(enemy != null)
			{
				if(getDistance(enemy) > getMaxRange())
				{
					moveTo(enemy);
				}
				else
				{
					turnTo(enemy);
					turnAround();
					move();
				}
			}
		}
	}
}

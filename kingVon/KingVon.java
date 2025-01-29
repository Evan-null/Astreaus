package teams.student.kingVon;

import components.weapon.economy.Collector;
import engine.Main;
import engine.states.Game;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.kingVon.units.*;


public class KingVon extends Player
{
	int timer;

	public void setup()
	{		
		setName("KingVon");
		setTeamImage("src/teams/student/KingVon/teamLogo.png");
		setTitle("KingVon Team");
		timer = 0;

		setColorPrimary(170, 170, 170);
		setColorSecondary(200, 200, 50);
		setColorAccent(255, 255, 255);
	}

	// timer < 10,000			early
	// 10,000 < timer < 20,000	early middle
	// 20,000 < timer < 30,000	middle
	// 30,000 < timer < 40,000 	late middle
	// 45,000 < timer			endgame

	
	public void strategy() 
	{
		timer++;

		float gathererPercent = getFleetValueUnitPercentage(Gatherer.class);
		float minerPercent = getFleetValueUnitPercentage(Miner.class);
		float flankerPercent = getFleetValueUnitPercentage(Flanker.class);
		float fighterPercent = getFleetValueUnitPercentage(Fighter.class);

		if (timer < 15000)
		{
			if (getMyUnits(Fighter.class).size() < 2 )
			{
				buildUnit(new Fighter(this));
			}
			else if (gathererPercent < 0.5)
			{
				buildUnit(new Gatherer(this));
			}
			else if (minerPercent < 0.25)
			{
				buildUnit(new Miner(this));
			}
			else if (flankerPercent < 0.15)
			{
				buildUnit(new Flanker(this));
			}
			else
			{
				buildUnit(new Fighter(this));
			}
		}
		else if (timer < 35000)
		{
			if (getMyUnits(Fighter.class).size() < 2 )
			{
				buildUnit(new Fighter(this));
			}
			else if (gathererPercent < 0.15)
			{
				buildUnit(new Gatherer(this));
			}
			else if (minerPercent < 0.15)
			{
				buildUnit(new Miner(this));
			}
			else
			{
				buildUnit(new Fighter(this));
			}
		}
		else if (timer > 45000)
		{
			buildUnit(new Fighter(this));
		}
	}

	private boolean anyEnemyHasCollector()
	{
		for (Unit u : getEnemyUnits())
		{
			if (u.hasComponent(Collector.class))
			{
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g)
	{

	}



//	float throwerPercent = getFleetValueUnitPercentage(Thrower.class);
//	float closePercent = getFleetValueUnitPercentage(CloseGatherer.class);
//	float minerPercent = getFleetValueUnitPercentage(Miner.class);
//	float flankerPercent = getFleetValueUnitPercentage(Flanker.class);
//	float fighterPercent = getFleetValueUnitPercentage(Fighter.class);
//
//
//		if (getMyUnits(Fighter.class).size() < 2 )
//	{
//		buildUnit(new Fighter(this));
//		return;
//	}
//		if (throwerPercent < 0.2f)
//	{
//		buildUnit(new Thrower(this));
//		return;
//	}
//		else if (closePercent < 0.1f)
//	{
//		buildUnit(new CloseGatherer(this));
//		return;
//	}
//		else if (minerPercent < 0.1f)
//	{
//		buildUnit(new Miner(this));
//		return;
//	}
//
//		if (!anyEnemyHasCollector()) {
//	if (fighterPercent < .8f) {
//		buildUnit(new Fighter(this));
//	}
//	return;
//}
//		else
//	{
//		if (flankerPercent < .8f) {
//			buildUnit(new Flanker(this));
//		}
//		else {
//			buildUnit(new Fighter(this));
//		}
//
//	}

	//		if(getFleetValueUnitPercentage(Thrower.class) < .4f)
//		{
//			buildUnit(new Thrower(this));
//		}
//		else if(getFleetValueUnitPercentage(CloseGatherer.class) < .3f)
//		{
//			buildUnit(new CloseGatherer(this));
//		}
//		else if(getFleetValueUnitPercentage(Miner.class) < .3f)
//		{
//			buildUnit(new Miner(this));
//		}
//		else
//		{
//			buildUnit(new Fighter(this));
//		}
	
}

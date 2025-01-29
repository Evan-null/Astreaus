package teams.student.kingVon.units;

import components.mod.offense.HadesMod;
import components.upgrade.HeavyPlating;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;


public class Tank extends KingVonUnit
{
    int timer = 0;

    public Tank (KingVon p)
    {
        super(p);
    }

    public void design()
    {
        setFrame(Frame.LIGHT);
        setModel(Model.CRUISER);
        setStyle(Style.WEDGE);

        add(Autocannon.class);
        add(HadesMod.class);
    }

    public void movement()
    {
        Unit enemy = getNearestEnemy();

        if (timer < 100)
        {
            if (enemy != null) {
                if (getDistance(enemy) > getMaxRange()) {
                    moveTo(enemy);
                } else {
                    turnTo(enemy);
                    turnAround();
                    move();
                }
            }
        }
        else
        {
            turn(stopAngle());
            if (getCurSpeed() > 0)
            {
                move();
            }
        }
    }

    public void action()
    {
        super.action();
        timer++;
    }

    public float stopAngle()
    {
        float motionAngle = (float) Math.toDegrees(Math.atan2(getSpeedY(), getSpeedX()));
        float stopAngle = motionAngle + 180;
        if (stopAngle >= 360)
        {
            stopAngle -= 360;
        }
        float currentAngle = getTheta();
        float angleDifference = stopAngle - currentAngle;
        if (angleDifference > 180)
        {
            angleDifference -= 360;
        }
        else if (angleDifference < -180)
        {
            angleDifference += 360;
        }
        return angleDifference;
    }
}

package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class AbstractProp extends AbstractFlyingObject {
    public AbstractProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX,locationY,speedX,speedY);

    }
    @Override
    public void forward() {
        super.forward();
        if(speedY>0&&locationY>= Main.WINDOW_HEIGHT){
            vanish();
        }
    }
    public abstract void function(HeroAircraft heroAircraft);

}
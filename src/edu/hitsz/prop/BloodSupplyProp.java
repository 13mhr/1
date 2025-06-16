package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;

public class BloodSupplyProp extends AbstractProp {
    public BloodSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void function(HeroAircraft heroAircraft){
        heroAircraft.addHealth(10);
        this.vanish();
    }
}

package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombSupplyProp;

public class BombPropFactory extends AbstractPropFactory {
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY){
        return new BombSupplyProp(locationX,locationY,speedX,speedY);
    }
}

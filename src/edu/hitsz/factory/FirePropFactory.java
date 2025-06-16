package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.FireSupplyProp;

public class FirePropFactory extends AbstractPropFactory{
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY){
        return new FireSupplyProp(locationX,locationY,speedX,speedY);
    }
}

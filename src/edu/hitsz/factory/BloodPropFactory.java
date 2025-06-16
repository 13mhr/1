package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodSupplyProp;

public class BloodPropFactory extends AbstractPropFactory{
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY){
        return new BloodSupplyProp(locationX,locationY,speedX,speedY);
    }
}

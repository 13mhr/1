package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodSupplyProp;
import edu.hitsz.prop.BulletPlusProp;

public class BulletPlusFactory extends AbstractPropFactory{
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BulletPlusProp(locationX, locationY, speedX, speedY);
    }
}

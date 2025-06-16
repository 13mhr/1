package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.AbstractProp;
import observer.Subscriber;

import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemyAircraft implements Subscriber {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override

    public AbstractProp produceProp(){
        return null;
    }
    public void update(){
        this.vanish();
    }


}

package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.*;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.strategy.ShootStrategy;
import observer.Subscriber;

import java.util.LinkedList;
import java.util.List;

public class ElitePlusEnemy extends AbstractEnemyAircraft implements Subscriber {
    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int direction, int shootNum, int power, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp,direction,shootNum,power,shootStrategy);
    }

    @Override
    public AbstractProp produceProp (){
        AbstractPropFactory propFactory;
        double x=Math.random();
        if(x<0.4){
            propFactory=new BloodPropFactory();
            return propFactory.createProp(this.getLocationX(),this.getLocationY(),0,10);
        }
        else if (x>0.4&&x<0.65){
            propFactory=new FirePropFactory();
            return propFactory.createProp(this.getLocationX(),this.getLocationY(),0,10);
        }
        else if (x>0.65&&x<0.85){

            propFactory=new BulletPlusFactory();
            return propFactory.createProp(this.getLocationX(),this.getLocationY(),0,10);
        }
        else if (x>0.85){
            propFactory=new BombPropFactory();
            return propFactory.createProp(this.getLocationX(),this.getLocationY(),0,10);
        }
        else {
            return null;
        }
    }
    public void update(){
        this.decreaseHp(30);
    }




}

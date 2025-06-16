package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.AbstractGame;
import edu.hitsz.strategy.CircularShoot;
import edu.hitsz.strategy.DirectShoot;

import static java.lang.Math.min;

public class BulletPlusProp extends AbstractProp{
    public BulletPlusProp(int locationX,int locationY,int speedX,int speedY){
        super(locationX,locationY,speedX,speedY);
    }
    public void function(HeroAircraft heroAircraft){
        Runnable r = ()->{
            try {
                heroAircraft.setShootNum(20);
                heroAircraft.setShootStrategy(new CircularShoot());
                System.out.println("FirePlusSupply active!");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {

                    heroAircraft.setShootNum(1);
                    heroAircraft.setShootStrategy(new DirectShoot());
                    System.out.println("FirePlusSupply end!");

            }
        };

        this.vanish();
        AbstractGame.firePropNum = 1;
        new Thread(r).start();

    }
}

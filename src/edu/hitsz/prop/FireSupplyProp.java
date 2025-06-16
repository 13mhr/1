package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.AbstractGame;
import edu.hitsz.strategy.DirectShoot;
import edu.hitsz.strategy.ScatterShoot;

import static java.lang.Math.min;

public class FireSupplyProp extends AbstractProp {
    public FireSupplyProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX,locationY,speedX,speedY);
    }
    public void function(HeroAircraft heroAircraft){
        Runnable r = ()->{
            try {
                heroAircraft.setShootNum(3);
                heroAircraft.setShootStrategy(new ScatterShoot());
                System.out.println("FireSupply active!");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {



                    heroAircraft.setShootNum(1);
                    heroAircraft.setShootStrategy(new DirectShoot());
                    System.out.println("FireSupply end!");

            }
        };

        this.vanish();
        AbstractGame.firePropNum = 1;
        new Thread(r).start();

    }
}

package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.DirectShoot;

public class EliteEnemyFactory extends AbstractEnemyFactory{
    private static int speedY = 10;
    private static int hp = 60;
    private static int speedX=5;

    /*创建精英敌机实例*/
    @Override
    public AbstractEnemyAircraft createEnemy() {
        int shootNum = 1;     //子弹一次发射数量
        int power = 5;       //子弹伤害
        int direction = 1;  //子弹射击方向 (向上发射：1，向下发射：-1)
        int locationX = (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);



        /*返回产生的精英敌机*/
        return new EliteEnemy(locationX, locationY, speedX, speedY, hp, direction, shootNum, power,new DirectShoot());
    }
    public static void changeAttribute(double diffRate){
        int hpOrig=60;
        int speedYOrig=10;
        hp=(int)(hpOrig*diffRate);
        speedY=(int)(speedYOrig*diffRate);
    }

}

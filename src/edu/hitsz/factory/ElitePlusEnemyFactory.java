package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.strategy.ScatterShoot;

import java.util.List;

public class ElitePlusEnemyFactory extends AbstractEnemyFactory {
    private static int speedY = 10;
    private static int hp = 120;
    private static int speedX=5;

    /*创建精英敌机实例*/
    @Override
    public AbstractEnemyAircraft createEnemy() {
        int shootNum = 3;     //子弹一次发射数量
        int power = 10;       //子弹伤害
        int direction = 1;  //子弹射击方向 (向上发射：1，向下发射：-1)
        int locationX = (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);



        /*返回产生的精英敌机*/
        return new ElitePlusEnemy(locationX, locationY, speedX, speedY, hp, direction, shootNum, power,new ScatterShoot());
    }
    public static void changeAttribute(double diffRate){
        int hpOrig=120;
        int speedYOrig=10;
        hp=(int)(hpOrig*diffRate);
        speedY=(int)(speedYOrig*diffRate);
    }


}

package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobEnemyFactory extends AbstractEnemyFactory{
    private static int speedY = 5;
    private static int hp = 30;
    private static int speedX = 0;

    /*创建普通敌机实例*/
    @Override
    public AbstractEnemyAircraft createEnemy() {

        int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()) + ImageManager.MOB_ENEMY_IMAGE.getWidth() * 0.5);
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);


        /*返回产生的普通敌机*/
        return new MobEnemy(locationX, locationY, speedX, speedY, hp);
    }
    public static void changeAttribute(double diffRate){
        int hpOrig=30;
        int speedYOrig=5;
        hp=(int)(hpOrig*diffRate);
        speedY=(int)(speedYOrig*diffRate);
    }

}

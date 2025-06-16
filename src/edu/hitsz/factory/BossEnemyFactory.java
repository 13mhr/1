package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.CircularShoot;

public class BossEnemyFactory extends AbstractEnemyFactory{
    public AbstractEnemyAircraft createEnemy() {

        int locationX = (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth()));
        int locationY = 100+(int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        int speedY = 0;
        int speedX = 3;
        int hp = 500;
        int shootNum = 20;     //子弹一次发射数量
        int power = 20;       //子弹伤害
        int direction = 1;  //子弹射击方向 (向上发射：1，向下发射：-1)

        /*返回产生的boss敌机*/
        return new BossEnemy(locationX, locationY, speedX, speedY, hp, direction, shootNum, power,new CircularShoot());
    }
}

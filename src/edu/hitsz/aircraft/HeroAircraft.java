package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.DirectShoot;
import edu.hitsz.strategy.ShootStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**攻击方式 */

    /**
     * 子弹一次发射数量

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private volatile static HeroAircraft heroAircraft;
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int direction, int shootNum, int power, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp,direction,shootNum,power,shootStrategy);
    }
    public static HeroAircraft getHeroAircraft(){
        if(heroAircraft==null){
            synchronized (HeroAircraft.class){
                if(heroAircraft==null){
                    heroAircraft = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 1000,-1,1,30, new DirectShoot());

                }
            }
        }
        return heroAircraft;
    }
    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }
    public void addHealth( int increase)
    {

        this.hp += increase;
        if(this.hp>1000)
            hp=1000;
    }


    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */



}

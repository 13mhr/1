package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class DirectShoot implements ShootStrategy{

    @Override
    public List<BaseBullet> doShoot(AbstractAircraft aircraft) {
        List<BaseBullet> res=new LinkedList<>();
        int x=aircraft.getLocationX();
        int y=aircraft.getLocationY()+aircraft.getDirection()*2;
        int speedX=0;
        int speedY=aircraft.getSpeedY()+aircraft.getDirection()*5;
        BaseBullet bullet;
        for(int i=0; i<aircraft.getShootNum(); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if (aircraft instanceof HeroAircraft){
                bullet = new HeroBullet(x + (i*2 - aircraft.getShootNum() + 1)*10, y, speedX, speedY, aircraft.getPower());
                res.add(bullet);
            }
            else {
                bullet = new EnemyBullet(x + (i*2 - aircraft.getShootNum() + 1)*10, y, speedX, speedY, aircraft.getPower());
                res.add(bullet);
            }

        }
        return res;
    }
}

package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class CircularShoot implements ShootStrategy{
    @Override
    public List<BaseBullet> doShoot(AbstractAircraft aircraft) {
        List<BaseBullet> res=new LinkedList<>();
        int x=aircraft.getLocationX();
        int y=aircraft.getLocationY();
        int speed=5;
        BaseBullet bullet;
        for(int i=0; i<aircraft.getShootNum(); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if (aircraft instanceof HeroAircraft){
                bullet = new HeroBullet((int) (x + 100*Math.cos(Math.toRadians(18*i))), (int) (y+100*Math.sin(Math.toRadians(18*i))), (int) (speed*Math.cos(Math.toRadians(18*i))),(int) (speed*Math.sin(Math.toRadians(18*i))) , aircraft.getPower());
                res.add(bullet);
            }
            else {
                bullet = new EnemyBullet((int) (x + 100*Math.cos(Math.toRadians(18*i))), (int) (y+100*Math.sin(Math.toRadians(18*i))), (int) (speed*Math.cos(Math.toRadians(18*i))),(int) (speed*Math.sin(Math.toRadians(18*i))) , aircraft.getPower());
                res.add(bullet);
            }

        }
        return res;
    }
}

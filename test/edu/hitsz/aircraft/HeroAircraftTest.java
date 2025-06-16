package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
class HeroAircraftTest {
    private HeroAircraft heroAircraft;
    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class---**");
        heroAircraft =HeroAircraft.getHeroAircraft();
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        heroAircraft =null;
    }

    @Test
    void decreaseHp() {
        System.out.println("**--- Test decreaseHp method executed---**");
        int decrease=10;
        int maxHp= heroAircraft.getHp();
        for(int i=1;i<=12;i++)
        {
            heroAircraft.decreaseHp(decrease);
            assertEquals(heroAircraft.getHp(),max(maxHp-i*decrease,0));
            if(heroAircraft.getHp()==0){
                assertTrue(heroAircraft.notValid());
            }
        }
    }

    @Test
    void addHealth() {
        System.out.println("**--- Test addHealth method executed ---**");
        int maxHp=heroAircraft.getHp();
        int origHp=60;
        heroAircraft.setHp(origHp);
        int increase=10;
        for(int x=1;x<=5;x++){
            heroAircraft.addHealth(increase);
            assertEquals(heroAircraft.getHp(),min(origHp+x*increase,maxHp));

        }
    }

    @Test
    void shoot() {
        System.out.println("**--- Test shoot method executed ---**");
        List<BaseBullet> res=heroAircraft.shoot();
        assertNotNull(res);
        for(BaseBullet bullet:res){
            assertEquals(bullet.getLocationX(),heroAircraft.getLocationX());
            assertEquals(bullet.getLocationY(),heroAircraft.getLocationY()-2);
            assertEquals(bullet.getSpeedX(),0);
            assertEquals(bullet.getSpeedY(),heroAircraft.getSpeedY()-5);
            assertEquals(bullet.getPower(),30);
        }
    }
}
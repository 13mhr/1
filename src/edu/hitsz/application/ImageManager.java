package edu.hitsz.application;


import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.BloodSupplyProp;
import edu.hitsz.prop.BombSupplyProp;
import edu.hitsz.prop.BulletPlusProp;
import edu.hitsz.prop.FireSupplyProp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_EASY_IMAGE;
    public static BufferedImage BACKGROUND_MEDIUM_IMAGE;
    public static BufferedImage BACKGROUND_HARD_IMAGE;
    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage DROP_BLOOD_IMAGE;
    public static BufferedImage DROP_BOMB_IMAGE;
    public static BufferedImage DROP_BULLET_IMAGE;
    public static BufferedImage ELITEPLUS_ENEMY_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;
    public static BufferedImage BULLET_PLUS_IMAGE;



    static {
        try {

            BACKGROUND_EASY_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            BACKGROUND_MEDIUM_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
            BACKGROUND_HARD_IMAGE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));
            DROP_BLOOD_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));
            DROP_BOMB_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bomb.png"));
            DROP_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            ELITEPLUS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitePlus.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));
            BULLET_PLUS_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bulletPlus.png"));






            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BloodSupplyProp.class.getName(), DROP_BLOOD_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BombSupplyProp.class.getName(), DROP_BOMB_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FireSupplyProp.class.getName(), DROP_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(ElitePlusEnemy.class.getName(),ELITEPLUS_ENEMY_IMAGE );
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(),BOSS_ENEMY_IMAGE );
            CLASSNAME_IMAGE_MAP.put(BulletPlusProp.class.getName(), BULLET_PLUS_IMAGE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

}

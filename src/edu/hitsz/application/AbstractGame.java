package edu.hitsz.application;

import edu.hitsz.Users.User;
import edu.hitsz.Users.UserDao;
import edu.hitsz.Users.UserDaoImpl;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.*;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodSupplyProp;
import edu.hitsz.prop.BombSupplyProp;
import observer.Subscriber;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public  abstract class AbstractGame extends JPanel {

    protected int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;
    public static String difficulty;
    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;
    public static int firePropNum=0;
    /**标志boss敌机是否在屏幕上*/
    protected boolean bossOnScreen = false;
    protected int diffChangeDuration;
    protected int elitePlusTime;



    protected final HeroAircraft heroAircraft;
    protected final List<AbstractEnemyAircraft> enemyAircrafts;
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    protected final LinkedList<AbstractProp> props;
    protected AbstractEnemyFactory enemyFactory;


    /**
     * 屏幕中出现的敌机最大数量
     */
    protected int enemyMaxNumber ;
    protected double enemyRate;
    /**
     * 当前得分
     */
    private static int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration;
    private int cycleTime =0;
    protected int diffCycleTime=0;
    protected MusicThread bgmMusic;
    protected MusicThread bgmBossMusic;

    /**
     * 游戏结束标志
     */
    /**boss敌机产生过的次数，用于逐次提升boss机血量*/
    protected int BossScore;
    public static int getScore(){
        return score;
    }

    public AbstractGame() throws IOException {
        heroAircraft = HeroAircraft.getHeroAircraft();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();
        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());
        /*ThreadFactory gameThread = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("Game Thread");
                return t;
            }
        };
        executorService = new ScheduledThreadPoolExecutor(1, gameThread);*/

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);
        /*播放背景音乐*/
        bgmMusic = new MusicThread("src/videos/bgm.wav");
        bgmMusic.start();

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            /*time += timeInterval;
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
            }
            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                // 新敌机产生
                if(time%18000==0){
                    enemyFactory=new ElitePlusEnemyFactory();
                    enemyAircrafts.add(enemyFactory.createEnemy());
                }
                if(score>=BossScore){
                    bossOnScreen=true;
                    enemyFactory=new BossEnemyFactory();
                    enemyAircrafts.add(enemyFactory.createEnemy());
                    bgmBossMusic = new MusicThread("src/videos/bgm_boss.wav");
                    bgmBossMusic.start();
                    BossScore+=150;
                }
                if (enemyAircrafts.size() < enemyMaxNumber) {

                    double x=Math.random();
                    if(x<0.3) {
                        enemyFactory=new EliteEnemyFactory();
                        enemyAircrafts.add(enemyFactory.createEnemy());
                    }
                    else{
                        enemyFactory=new MobEnemyFactory();
                        enemyAircrafts.add(enemyFactory.createEnemy());

                    }


                }
                // 飞机射出子弹
                shootAction();
            }*/
            // 计时
            timeCountAction();

            // 控制背景音乐和boss敌机音乐等循环播放
            musicControlAction();


            changeDiffAction();
            // 产生普通敌机和精英敌机
            createEnemyAction();
            //timeCountAndNewCycleJudge();
            //产生BOSS敌机

            createBossAction();


            // 飞机射出子弹
            shootAction();

            newTimeAction();

            // 子弹移动
            bulletsMoveAction();
            //掉落物移动
            propMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();
            //游戏结束后处理
            gameOverCheckAction();



        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************


    /** 计时并周期性输出时间*/
    private void timeCountAction(){
        time += timeInterval;
        cycleTime += timeInterval;
        diffCycleTime+=timeInterval;
        if (newCycleJudge()){
            System.out.println(time);
        }
    }


    /** 周期性执行（控制频率）*/
    protected boolean newCycleJudge() {
        return (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime);
    }
    protected boolean newDiffChangeJudge(){
        return (diffCycleTime >= diffChangeDuration && diffCycleTime - timeInterval < diffCycleTime);

    }

   /*private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

      /**判断是否更新到新的周期*/
   private void newTimeAction(){
       if(newCycleJudge()){
           // 跨越到新的周期
           cycleTime %= cycleDuration;
       }
       if(newDiffChangeJudge()){
           diffCycleTime %=diffChangeDuration;
       }
   }

    protected abstract void changeDiffAction();

    /** 控制背景音乐和boss敌机音乐等循环播放*/
    private void musicControlAction(){
        /*设置背景音乐循环播放*/
        if(!bgmMusic.isAlive()){
            bgmMusic = new MusicThread("src/videos/bgm.wav");
            bgmMusic.start();
        }

        /*设置boss敌机背景音乐循环播放*/
        if(bossOnScreen && !bgmBossMusic.isAlive()){
            bgmBossMusic = new MusicThread("src/videos/bgm_boss.wav");
            bgmBossMusic.start();
        }
    }
    //产生敌机
    private void createEnemyAction() {
        if (newCycleJudge()) {
            // 新敌机产生
            if (time % elitePlusTime == 0) {
                enemyFactory = new ElitePlusEnemyFactory();
                enemyAircrafts.add(enemyFactory.createEnemy());
            }
            /*if (score >= BossScore) {
                bossOnScreen = true;
                enemyFactory = new BossEnemyFactory();
                enemyAircrafts.add(enemyFactory.createEnemy());
                bgmBossMusic = new MusicThread("src/videos/bgm_boss.wav");
                bgmBossMusic.start();
                BossScore += 150;
            }*/
            if (enemyAircrafts.size() < enemyMaxNumber) {
                double x=Math.random();
                if (x < enemyRate) {
                    enemyFactory = new EliteEnemyFactory();
                    enemyAircrafts.add(enemyFactory.createEnemy());
                } else {
                    enemyFactory = new MobEnemyFactory();
                    enemyAircrafts.add(enemyFactory.createEnemy());

                }


            }
        }
    }
    protected  abstract void createBossAction();
    private void shootAction() {
        // TODO 敌机射击\
        if(newCycleJudge()) {
            for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft instanceof EliteEnemy) {
                    enemyBullets.addAll(enemyAircraft.executeShoot());
                } else if (enemyAircraft instanceof ElitePlusEnemy) {
                    enemyBullets.addAll(enemyAircraft.executeShoot());
                } else if (enemyAircraft instanceof BossEnemy) {
                    enemyBullets.addAll(enemyAircraft.executeShoot());

                }

            }
            // 英雄射击
            heroBullets.addAll(heroAircraft.executeShoot());
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void propMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }
    private void aircraftsMoveAction() {
        for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for(BaseBullet bullet : enemyBullets){
            if(bullet.notValid()){
                continue;
            }
            if(heroAircraft.crash(bullet)){
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机

        // Todo: 我方获得道具，道具生效



            for (BaseBullet bullet : heroBullets) {
                if (bullet.notValid()) {
                    continue;
                }
                for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                    if (enemyAircraft.notValid()) {
                        // 已被其他子弹击毁的敌机，不再检测
                        // 避免多个子弹重复击毁同一敌机的判定
                        continue;
                    }
                    if (enemyAircraft.crash(bullet)) {
                        // 敌机撞击到英雄机子弹
                        // 敌机损失一定生命值

                        enemyAircraft.decreaseHp(bullet.getPower());
                        bullet.vanish();
                        MusicThread hitMusic= new MusicThread("src/videos/bullet_hit.wav");
                        hitMusic.start();
                        if (enemyAircraft.notValid()) {
                            if(enemyAircraft instanceof MobEnemy){
                                score+=5;
                            }
                            // TODO 获得分数，产生道具补给
                            AbstractProp prop=enemyAircraft.produceProp();
                            /*if(enemyAircraft instanceof EliteEnemy||enemyAircraft instanceof ElitePlusEnemy)
                            {
                                props.add(prop);
                            }
                            if(enemyAircraft instanceof BossEnemy){
                                props.add(prop);
                                props.add(prop);
                                props.add(prop);
                            }*/
                            if(enemyAircraft instanceof EliteEnemy||enemyAircraft instanceof ElitePlusEnemy) {
                                if (prop != null) {
                                    props.add(prop);
                                }
                                score+=10;
                            }
                            else if(enemyAircraft instanceof BossEnemy){
                                for(int i=1;i<=3;i++){
                                    AbstractProp prop_1=enemyAircraft.produceProp();
                                    if(prop_1!=null){
                                        props.add(prop_1);
                                    }
                                }
                                bgmBossMusic.setMusicInterrupt(true);
                                bossOnScreen = false;
                                score+=15;
                            }

                        }


                    }
                    // 英雄机 与 敌机 相撞，均损毁
                    if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                        enemyAircraft.vanish();
                        heroAircraft.decreaseHp(Integer.MAX_VALUE);
                    }
                }
            }

            for(AbstractProp prop:props){
                if(prop.notValid()){
                    continue;
                }
                if(heroAircraft.crash(prop)){
                    MusicThread propMusic = new MusicThread("src/videos/get_supply.wav");
                    propMusic.start();
                    prop.vanish();
                    if(prop instanceof BloodSupplyProp) {
                        heroAircraft.addHealth(10);
                    }
                    if(prop instanceof BombSupplyProp){
                        for(AbstractEnemyAircraft enemyAircraft:enemyAircrafts){
                            if(enemyAircraft instanceof MobEnemy){
                                score+=5;
                                ((BombSupplyProp)prop).addSubscriber((Subscriber) enemyAircraft);
                            }
                            else if(enemyAircraft instanceof EliteEnemy){
                                score+=10;
                                ((BombSupplyProp)prop).addSubscriber((Subscriber) enemyAircraft);
                            }
                            else if(enemyAircraft instanceof ElitePlusEnemy){
                                score+=10;
                                ((BombSupplyProp)prop).addSubscriber((Subscriber) enemyAircraft);
                            }
                        }
                        for(BaseBullet baseBullet:enemyBullets){
                            ((BombSupplyProp)prop).addSubscriber((Subscriber) baseBullet);
                        }
                    }
                    prop.function(heroAircraft);
                }



            }


        }



    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }
    /** 游戏结束检查*/
    private void gameOverCheckAction(){
        if (heroAircraft.getHp() <= 0) {
            // 游戏结束
            executorService.shutdown();
            //关闭背景音乐
           bgmMusic.setMusicInterrupt(true);
            //bgmBossMusic.interrupt();
            //若boss敌机还在屏幕上，则关闭boss敌机背景音乐
            if(bossOnScreen){
               bgmBossMusic.setMusicInterrupt(true);
                //bgmBossMusic.interrupt();
            }
            MusicThread gameOverMusic = new MusicThread("src/videos/game_over.wav");
            gameOverMusic.start();

            System.out.println("Game Over!");

            this.setVisible(false);
            synchronized (Main.MAIN_LOCK){
                //通知主线程结束等待
                Main.MAIN_LOCK.notify();
            }
        }
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        /*g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }*/
        paintBackground(g);
        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }
    protected abstract void paintBackground(Graphics g);
    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}

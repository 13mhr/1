package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.factory.*;

import java.awt.*;
import java.io.IOException;

public class MediumGame extends AbstractGame{
    private double diffRate=1;
    public MediumGame() throws IOException {
        cycleDuration=500;//生成敌机和子弹频率
        enemyMaxNumber=7;//敌机最大数量
        enemyRate=0.4;//精英敌机生成概率
        BossScore=300;//BOSS敌机的阈值
        diffChangeDuration=5000;//难度刷新的频率
        elitePlusTime=2000;//超级精英敌机出现的频率
    }

    @Override
    protected void changeDiffAction() {
        if(newDiffChangeJudge()){
            double diffMaxRate=1.5;
            if(diffRate<diffMaxRate){
                enemyRate+=0.01;
                cycleDuration-=10;
                System.out.printf("提高难度！精英机概率：%.2f，敌机周期：%.2f，敌机属性提升倍率：%.2f,\n", enemyRate,(double)cycleDuration/timeInterval,diffRate);
                MobEnemyFactory.changeAttribute(diffRate);
                EliteEnemyFactory.changeAttribute(diffRate);
                ElitePlusEnemyFactory.changeAttribute(diffRate);
                diffRate+=0.1;
            }
            else{
                System.out.println("提升已经最大了");
            }
        }
    }

    @Override
    protected void createBossAction() {
       if(newCycleJudge()){
           int haveBoss=0;
           for(AbstractEnemyAircraft enemyAircraft:enemyAircrafts){
               if(enemyAircraft instanceof BossEnemy){
                   haveBoss=1;
                   break;
               }
           }
           if(getScore()>=BossScore&&haveBoss==0){
               enemyFactory=new BossEnemyFactory();
               enemyAircrafts.add(enemyFactory.createEnemy());
               BossScore+=300;
               bossOnScreen=true;
               bgmBossMusic=new MusicThread("src/videos/bgm_boss.wav");
               bgmBossMusic.start();
           }
       }

    }

    protected void paintBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_MEDIUM_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_MEDIUM_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }
    }
}

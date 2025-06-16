package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.ElitePlusEnemyFactory;
import edu.hitsz.factory.MobEnemyFactory;

import java.awt.*;
import java.io.IOException;

public class HardGame extends AbstractGame{
    double diffRate=1;
    double bossRate=1;
    public HardGame() throws IOException {
        cycleDuration=400;
        enemyMaxNumber=10;
        BossScore=200;
        enemyRate=0.5;
        diffChangeDuration=4800;
        elitePlusTime=1600;
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
                diffRate+=0.01;
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
                BossEnemy bossEnemy= (BossEnemy) enemyFactory.createEnemy();
                bossEnemy.setHp((int) (500*bossRate));
                enemyAircrafts.add(bossEnemy);
                System.out.println("产生boss机");
                System.out.printf("BOSS敌机血量倍率：%.2f,\n",bossRate);
                BossScore+=200;
                bossRate+=0.2;
                bossOnScreen=true;
                bgmBossMusic=new MusicThread("src/videos/bgm_boss.wav");
                bgmBossMusic.start();
            }
        }

    }

    protected void paintBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_HARD_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_HARD_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }
    }
}

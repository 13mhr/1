package edu.hitsz.application;

import edu.hitsz.aircraft.MobEnemy;

import java.awt.*;
import java.io.IOException;

public class EasyGame extends AbstractGame{
    public EasyGame() throws IOException {
        cycleDuration=600;
        enemyMaxNumber=3;
        enemyRate=0.2;
        elitePlusTime=2400;
    }

    @Override
    protected void changeDiffAction() {

    }

    @Override
    protected void createBossAction() {
    }

    protected void paintBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_EASY_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_EASY_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }
    }


}

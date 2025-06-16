package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;
import observer.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class BombSupplyProp extends AbstractProp {
    public BombSupplyProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX,locationY,speedX,speedY);
    }
    /*观察者列表*/
    private List<Subscriber> subscriberList = new ArrayList<>();
    //增加观察者
    public void addSubscriber(Subscriber subscriber){
        subscriberList.add(subscriber);
    }

    //删除观察者
    public void removeSubscriber(Subscriber subscriber){
        subscriberList.remove(subscriber);
    }
    //通知所有观察者
    public void notifyAllSubscribers(){
        for (Subscriber subscriber : subscriberList){
            subscriber.update();
        }
    }

    public void function(HeroAircraft heroAircraft){
        MusicThread bombMusic=new MusicThread("src/videos/bomb_explosion.wav");
        bombMusic.start();
        notifyAllSubscribers();
        System.out.println("BombSupply active!");
    }
}

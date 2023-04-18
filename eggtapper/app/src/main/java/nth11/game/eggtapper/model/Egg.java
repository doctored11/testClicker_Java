package nth11.game.eggtapper.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import nth11.game.eggtapper.R;

public class Egg {
    private int strength;
    private int maxValue;

    private int texture = R.drawable.egg_stage_0;


    public Egg (int strength){
        this.strength=strength;
        this.maxValue=strength;
    }
    public void reduceStrength(int count){

        if (count<0) count =1;
        this.strength-=count;
        if ( this.strength<=0){
            this.strength=0;
        }
    }
    public int getStrenght(){
        return this.strength;
    }
    public int getPercentStrenght(){
        return strength*100/maxValue;

    }
    public boolean statusChecker(){
        return strength <= 0;
    }

    public int strengthChecker(){
        int strengthInPercent = getPercentStrenght();
        if (strengthInPercent>96){
            return R.drawable.egg_stage_0;
        }
        if (strengthInPercent>90){
             return R.drawable.egg_stage_1;
        }
        if (strengthInPercent>85){
             return R.drawable.egg_stage_2;
        }
        if (strengthInPercent>75){
             return R.drawable.egg_stage_3;
        }
        if (strengthInPercent>60){
             return R.drawable.egg_stage_4;
        }
        if (strengthInPercent>43){
             return R.drawable.egg_stage_5;
        }
        if (strengthInPercent>30){
             return R.drawable.egg_stage_6;
        }
        if (strengthInPercent>20){
             return R.drawable.egg_stage_7;
        }
        if (strengthInPercent>12){
            return R.drawable.egg_stage_8;
        }
        if (strengthInPercent>6){
            return R.drawable.egg_stage_9;
        }
        if (strengthInPercent>1){
            return R.drawable.egg_stage_10;
        }
        return R.drawable.egg_stage_destroy;
    }


}

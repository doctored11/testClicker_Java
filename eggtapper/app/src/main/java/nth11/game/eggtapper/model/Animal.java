package nth11.game.eggtapper.model;

import android.graphics.Bitmap;

import nth11.game.eggtapper.R;

public class Animal {
    private int id;
    private int sprite = R.drawable.testfull_duck;

    private double strength;
    private Bitmap bitmap;

    public Animal(int id) {
        this.id = id;
        this.strength= (long) (Math.random()*(35-10)+10);
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }
    public  int getId(){
        return id;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }

    public void reduceStrength(){

        this.strength-=1;
        if ( this.strength<=0){
            this.strength=0;
        }
    }
    public void reduceStrength( double damage){

        this.strength-=damage;
        if ( this.strength<=0){
            this.strength=0;
        }
    }

    public boolean statusChecker(){
        return strength <= 0;
    }

//    будут еще поля - тест

}

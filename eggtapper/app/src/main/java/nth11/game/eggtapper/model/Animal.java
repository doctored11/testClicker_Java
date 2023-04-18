package nth11.game.eggtapper.model;

import android.graphics.Bitmap;

import nth11.game.eggtapper.R;

public class Animal {
    private int id;
    private int sprite = R.drawable.testfull_duck;
    private Bitmap bitmap;

    public Animal(int id) {
        this.id = id;
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

//    будут еще поля - тест

}

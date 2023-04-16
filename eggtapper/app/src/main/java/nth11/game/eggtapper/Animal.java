package nth11.game.eggtapper;

public class Animal {
    private int id;
    private int sprite = R.drawable.testfull_duck;

    public Animal(int id) {
        this.id = id;
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }

//    будут еще поля - тест

}

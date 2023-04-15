package nth11.game.eggtapper;

public class Egg {
    private int strength;
    private int maxValue;

    private int texture = R.drawable.egg1;


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
        if (strengthInPercent>65){
             return R.drawable.egg1;
        }
        if (strengthInPercent>45){
             return R.drawable.egg2;
        }
        if (strengthInPercent>25){
             return R.drawable.egg3;
        }
        if (strengthInPercent>15){
             return R.drawable.egg4;
        }
        if (strengthInPercent>10){
             return R.drawable.egg5;
        }
        if (strengthInPercent>3){
             return R.drawable.egg6;
        }
        if (strengthInPercent>0){
             return R.drawable.egg7;
        }
        return R.drawable.egg1;
    }
    public int getTexture(){

        return texture;
    }

}

package nth11.game.eggtapper;

public class Egg {
    private int strength;
    private int maxValue;

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

}

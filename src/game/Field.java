package game;

/**
 * Created by Thundersama on 23.06.2016.
 */
public class Field{
    private Stone[][] field;

    public Field(Stone[][] fieldarray){
        this.field = fieldarray;

    }

    public boolean setPosition(Stone stone, int x, int y){
        return true;
    }


    private void notifyView(){

    }

    private boolean endGame(){
        return false;
    }


    public static void main(String[] args){

    }
}

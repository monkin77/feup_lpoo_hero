import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element{
    private String symbol = "M";

    public Monster(int x, int y){
        super(x, y);
    }

    public Position move(){
        Random random = new Random();
        int moveOption = random.nextInt(8); // random int between 0-3
        switch(moveOption){
            case 0:
                return new Position(this.position.getX()+1, this.position.getY()+1);
            case 1:
                return new Position(this.position.getX()+1, this.position.getY());
            case 2:
                return new Position(this.position.getX()+1, this.position.getY()-1);
            case 3:
                return new Position(this.position.getX(), this.position.getY()+1);
            case 4:
                return new Position(this.position.getX(), this.position.getY()-1);
            case 5:
                return new Position(this.position.getX()-1, this.position.getY()+1);
            case 6:
                return new Position(this.position.getX()-1, this.position.getY());
            case 7:
                return new Position(this.position.getX()-1, this.position.getY()-1);
            default:
                return new Position(this.position.getX()+1, this.position.getY());
        }
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(this.position.getX(), this.position.getY()), this.symbol);
    }
}

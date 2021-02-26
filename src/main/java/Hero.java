import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Hero {
    private Position position;

    public Hero(int x, int y){
        this.position = new Position(x, y);
    }

    public int getX() {
        return this.position.getX();
    }

    public void setX(int x) {
        this.position.setX(x);
    }

    public int getY() {
        return this.position.getY();
    }

    public void setY(int y) {
        this.position.setY(y);
    }

    public Position moveUp(){
        return new Position(this.position.getX(), this.position.getY()-1);
    }

    public Position moveDown(){
        return new Position(this.position.getX(), this.position.getY()+1);
    }

    public Position moveRight(){
        return new Position(this.position.getX()+1, this.position.getY());
    }

    public Position moveLeft(){
        return new Position(this.position.getX()-1, this.position.getY());
    }

    public void draw(TextGraphics graphics){
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF33"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(this.position.getX(), this.position.getY()), "X");
    }

    public void setPosition(Position position){
        this.position = position;
    }

}

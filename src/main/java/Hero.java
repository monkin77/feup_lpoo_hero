import com.googlecode.lanterna.TextCharacter;
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

    public void draw(Screen screen){
        screen.setCharacter(this.position.getX(), this.position.getY(), TextCharacter.fromCharacter('X')[0]);
    }

    public void setPosition(Position position){
        this.position = position;
    }

}

import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {
    protected Position position;

    public Element(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition(){
        return this.position;
    }

    public void setPosition(Position position){
        this.position = position;
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

    public abstract void draw(TextGraphics graphics);
}

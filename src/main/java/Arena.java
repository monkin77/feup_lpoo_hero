import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private int width, height;
    private Hero hero;
    private List<Wall> walls;

    public Arena(int width, int height){
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10);
        this.walls = createWalls();
    }

    public void processKey( KeyStroke key) throws IOException {
        System.out.println(key);

        switch(key.getKeyType()){
            case ArrowUp:
                moveHero(this.hero.moveUp());
                break;
            case ArrowDown:
                moveHero(this.hero.moveDown());
                break;
            case ArrowLeft:
                moveHero(this.hero.moveLeft());
                break;
            case ArrowRight:
                moveHero(this.hero.moveRight());
                break;
        }
    }

    private void moveHero(Position position){
        if(this.canHeroMove(position))
            this.hero.setPosition(position);
    }

    private boolean canHeroMove(Position position){
        if(position.getX() < 1 || position.getX() >= this.width-1)  // Don't allow to touch walls
            return false;
        if(position.getY() < 1 || position.getY() >= this.height-1) // Don't allow to touch walls
            return false;
        return true;
    }

    public void draw(TextGraphics graphics) throws IOException{
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        for(Wall wall : this.walls)
            wall.draw(graphics);
        this.hero.draw(graphics);
    }

    private List<Wall> createWalls(){
        List<Wall> walls = new ArrayList<>();

        for(int c = 0; c < this.width; c++){
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, this.height-1));
        }

        for(int r = 1; r < this.height; r++){
            walls.add(new Wall(0, r));
            walls.add(new Wall(this.width-1, r));
        }

        return walls;
    }

}

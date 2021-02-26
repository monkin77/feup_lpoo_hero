import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Arena {
    private int width, height;
    private Hero hero;

    public Arena(int width, int height, int x, int y){
        this.width = width;
        this.height = height;
        this.hero = new Hero(x, y);
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
        if(position.getX() < 0 || position.getX() >= this.width)
            return false;
        if(position.getY() < 0 || position.getY() >= this.height)
            return false;
        return true;
    }

    public void draw(Screen screen) throws IOException{
        this.hero.draw(screen);
    }



}

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Arena {
    private int width, height;
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    public Arena(int width, int height){
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    public Arena(String fileName){
        readMap(fileName);
        this.hero = new Hero(1, 1);
        this.coins = createCoins();
        this.monsters = createMonsters();
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
        this.moveMonsters();
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

        for(Wall wall : this.walls){
            if(wall.getPosition().equals(position))
                return false;
        }

        for(Coin coin : this.coins){
            if(coin.getPosition().equals(position)){
                retrieveCoins(coin);
                return true;
            }
        }

        return true;
    }

    private void retrieveCoins(Coin coinToRemove){
        this.coins.remove(coinToRemove);
    }

    public void draw(TextGraphics graphics) throws IOException{
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        for(Wall wall : this.walls)
            wall.draw(graphics);
        for(Coin coin : this.coins)
            coin.draw(graphics);
        for(Monster monster : this.monsters)
            monster.draw(graphics);
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

    private List<Coin> createCoins(){
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            while(true){
                int randomX = random.nextInt(width -2) + 1;
                int randomY = random.nextInt(height-2) + 1;
                Position currPos = new Position(randomX, randomY);
                boolean collided = false;
                for(int j = 0; j < coins.size(); j++){
                    if(coins.get(j).position.equals(currPos))
                        collided = true;
                }
                for(Wall wall : this.walls){
                    if(currPos.equals(wall.position))
                        collided = true;
                }
                if(!collided){
                    Coin newCoin = new Coin(randomX, randomY);
                    coins.add(newCoin);
                    break;
                }
            }
        }
        return coins;
    }

    private List<Monster> createMonsters(){
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            while(true){
                int randomX = random.nextInt(width -2) + 1;
                int randomY = random.nextInt(height-2) + 1;
                Position currPos = new Position(randomX, randomY);
                boolean collided = false;
                for(int j = 0; j < monsters.size(); j++){
                    if(monsters.get(j).position.equals(currPos))
                        collided = true;
                }
                if(!collided){
                    Monster newMonster = new Monster(randomX, randomY);
                    monsters.add(newMonster);
                    break;
                }
            }
        }
        return monsters;
    }

    private void moveMonsters(){
        for(Monster monster : this.monsters){
            while(true){
                Position newMonsterPos = monster.move();
                if(canMonsterMove(newMonsterPos)){
                    monster.setPosition(newMonsterPos);
                    break;
                }
            }
        }
    }

    private boolean canMonsterMove(Position position){
        if(position.getX() < 0 || position.getX() >= this.width)
            return false;
        if(position.getY() < 0 || position.getY() >= this.height)
            return false;

        for(Wall wall : this.walls){
            if(wall.getPosition().equals(position))
                return false;
        }

        for(Coin coin : this.coins){
            if(coin.getPosition().equals(position))
                return false;
        }

        for(Monster monster : this.monsters)
            if(monster.getPosition().equals(position))
                return false;

        return true;
    }

    /**
     * 
     * @return true if monster collides with hero
     */
    public boolean verifyMonsterCollisions(){
        // check if monsters touch hero
        for(Monster monster : this.monsters){
            if(monster.getPosition().equals(this.hero.getPosition())){
                return true;
            }
        }
        return false;
    }

    private void readMap(String fileName){
        try{
            File myFile = new File(fileName);
            System.out.println("Attempting to open file ...");
            int x = 0, y = 0;
            int width = 0;
            if(myFile.exists()){
                this.walls = new ArrayList<>();
                System.out.println("Opened file");
                FileReader fr = new FileReader(myFile);
                BufferedReader br = new BufferedReader(fr);
                int c = 0;
                boolean reachedEnd = false;
                while((c = br.read()) != -1){
                    char character = (char) c;  // convert int to char
                    if(c == '\n'){
                        y++;
                        if(!reachedEnd){
                            reachedEnd = true;
                            width = x;
                        }
                        x = 0;
                        //System.out.println("new line char");
                    }
                    else if(c == '\r'){
                        ;
                        //System.out.println("Carriage return char");
                    }
                    else{
                        if(c == '/'){   // if wall
                            this.walls.add(new Wall(x, y));
                        }
                        else if(c == ' ') {  // empty space
                            ;
                        }
                        x++;
                        //System.out.println(character);
                    }
                }
            }
            this.width = width;
            this.height = y - 1;    // there is 1 extra 'n'
        }
        catch(IOException e){
            System.out.println("File not found");
            e.printStackTrace();
            this.width = 40;
            this.height = 20;
            this.walls = createWalls();
        }
    }

}

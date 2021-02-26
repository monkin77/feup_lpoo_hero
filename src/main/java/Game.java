import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    private int x = 10, y = 10;

    public Game(){
        try {
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            this.screen = new TerminalScreen(terminal);

            this.screen.setCursorPosition(null);     // we don't need a cursor
            this.screen.startScreen();               // screens must be started
            this.screen.doResizeIfNecessary();       // resize screen if necessary
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void processKey(com.googlecode.lanterna.input.KeyStroke key) throws IOException {
        System.out.println(key);

        switch(key.getKeyType()){
            case ArrowUp:
                this.y--;
                break;
            case ArrowDown:
                this.y++;
                break;
            case ArrowLeft:
                this.x--;
                break;
            case ArrowRight:
                this.x++;
                break;
        }

        if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
            this.screen.close();
        }
    }

    private void draw() throws IOException{
        this.screen.clear();
        this.screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
        this.screen.refresh();
    }

    public void run() throws IOException {
        while(true){
            draw();

            KeyStroke key = screen.readInput();

            if(key.getKeyType() == KeyType.EOF)         // if the screen was closed
                break;

            processKey(key);
        }
    }
}

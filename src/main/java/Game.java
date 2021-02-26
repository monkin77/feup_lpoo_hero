import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game{
    private Screen screen;
    private Arena arena;

    public Game(){
        this.arena = new Arena(40, 20);
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

    private void processKey(KeyStroke key) throws IOException {
        this.arena.processKey(key);
    }

    private void draw() throws IOException{
        this.screen.clear();
        this.arena.draw(this.screen.newTextGraphics());
        this.screen.refresh();
    }

    public void run() throws IOException {
        while(true){
            draw();

            KeyStroke key = screen.readInput();

            if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
                this.screen.close();
            }

            if(key.getKeyType() == KeyType.EOF)         // if the screen was closed
                break;

            processKey(key);
        }
    }

}

public class Position {
    private int x, y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o){        // overloads the standard equals for this class
        if(this == o) return true;

        if(o == null) return false;

        if(getClass() != o.getClass()) return false;

        Position p = (Position) o;
        return this.x == p.getX() && this.y == p.getY();
    }
}

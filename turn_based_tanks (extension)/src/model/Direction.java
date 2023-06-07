package model;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    private Direction clockwise90;

    static {
        NORTH.clockwise90 = EAST;
        EAST.clockwise90 = SOUTH;
        SOUTH.clockwise90 = WEST;
        WEST.clockwise90 = NORTH;
    }

    public Direction clockwise90() {
        return clockwise90;
    }
}

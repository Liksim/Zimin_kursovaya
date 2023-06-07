package generators;

import model.Direction;
import model.Field;
import model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardFieldGeneratorTest {

    @Test
    void createTest() {
        StandardFieldGenerator generator = new StandardFieldGenerator();
        assertNotNull(generator);
    }

    @Test
    void generateField() {
        Game listener = new Game();
        StandardFieldGenerator generator = new StandardFieldGenerator();

        Field field = generator.generateField(listener);

        assertNotNull(field);
        assertSame(field.cells()[5][5].neighborInDirection(Direction.NORTH), field.cells()[4][5]);
        assertSame(field.cells()[5][5].neighborInDirection(Direction.EAST), field.cells()[5][6]);
        assertSame(field.cells()[5][5].neighborInDirection(Direction.SOUTH), field.cells()[6][5]);
        assertSame(field.cells()[5][5].neighborInDirection(Direction.WEST), field.cells()[5][4]);

        assertSame(field.cells()[9][9].neighborInDirection(Direction.NORTH), field.cells()[8][9]);
        assertSame(field.cells()[9][9].neighborInDirection(Direction.EAST), null);
        assertSame(field.cells()[9][9].neighborInDirection(Direction.SOUTH), null);
        assertSame(field.cells()[9][9].neighborInDirection(Direction.WEST), field.cells()[9][8]);
    }
}
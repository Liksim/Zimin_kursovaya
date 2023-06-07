package model;

import model.objects.Tank;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    @Test
    void createTest() {
        Cell[][] cells = new Cell[0][0];
        ArrayList<Tank> tanks = new ArrayList<>();
        Field field = new Field(10, 10, cells, tanks);
        assertNotNull(field);
        assertSame(field.cells(), cells);
        assertSame(field.tanks(), tanks);
    }

    private boolean assertArrayEquals(ArrayList<Tank> tanks, ArrayList<Tank> expTanks) {
        if (tanks == null && expTanks == null) {
            return true;
        }
        else if(tanks == null || expTanks == null) {
            return false;
        }
        else if(tanks.size() != expTanks.size()) {
            return false;
        }

        for (int i = 0; i < tanks.size(); i++){
            if(tankEquals(tanks.get(i), expTanks.get(i))){
                return false;
            }
        }
        return true;
    }

    private boolean tankEquals(Tank first, Tank second){
        return false;
    }
}
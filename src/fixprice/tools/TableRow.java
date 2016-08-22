package fixprice.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kotov.aleksandr on 18.08.2016.
 */
public class TableRow {
    private List<TableCell> rowCells;
    public int num;


    public TableRow(int num) {
        rowCells = new ArrayList<>();
        this.num = num;
    }

    public void addRowCell(TableCell cell) {
        rowCells.add(cell);
    }

    public int getNum() {
        return num;
    }

    public List<TableCell> getRowCells() {
        return rowCells;
    }

    public int getWidth() {
        int result = 0;
        for (TableCell cell : rowCells) {
            result += cell.getDataWidth();
        }
        return result;
    }

    public int getCellCount(){
        return rowCells.size();
    }

    public int getHeight() {
        int height = 0;
        for (TableCell tableCell: rowCells) {
            int cellHeight = tableCell.getDataHeight();
            if(cellHeight>height) height =cellHeight;
        }
        return height;
    }
}

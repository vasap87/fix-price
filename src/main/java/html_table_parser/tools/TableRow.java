package html_table_parser.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kotov.aleksandr on 18.08.2016.
 */
public class TableRow {
    private List<TableCell> rowCells;
    private Table parentTable;
    private int num;



    public TableRow(Table table, int num) {
        this.parentTable = table;
        this.num = num;
        rowCells = new ArrayList<>();
    }

    public Table getParentTable() {
        return parentTable;
    }

    public int getNum() {
        return num;
    }

    public void addRowCell(TableCell cell) {
        rowCells.add(cell);
    }



    public List<TableCell> getRowCells() {
        return rowCells;
    }


    public int getHeight() {
        int height = 0;
        for (TableCell tableCell: rowCells) {
            if(tableCell.getRowspan()==1) {
                int cellHeight = tableCell.getDataHeight();
                if (cellHeight > height) height = cellHeight;
            }
        }
        return height;
    }

    public int getStartPos() {
        int res = 1;
        for (TableCell cell : rowCells) {
            if (cell.getStartPos() > res) res = cell.getStartPos();
        }
        return res;
    }
}

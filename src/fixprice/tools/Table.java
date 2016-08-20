package fixprice.tools;

import java.util.List;

/**
 * Created by kotov.aleksandr on 18.08.2016.
 */
public class Table {
    public static final char LINE_SEPARATOR = '-';
    public static final char COLUMN_SEPARATOR = '|';
    public static final char TABLE_BORDER = '+';
    private List<TableRow> tableRows;
    private int align;

    public Table(int align) {
        this.align = align;
    }

    public void addTableRows(List<TableRow> tableRows) {
        this.tableRows = tableRows;
    }

    public int getMaxWidth() {
        int result = 0;
        for (TableRow tableRow : tableRows) {
            int tableRowWidth = tableRow.getWidth();
            if (tableRowWidth > result) result = tableRowWidth;
        }
        return result;
    }

    public TableRow getTableRowByIndex(int index){
        return tableRows.get(index);
    }

    public List<TableRow> getTableRows() {
        return tableRows;
    }
}

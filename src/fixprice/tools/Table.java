package fixprice.tools;

import java.util.ArrayList;
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

    public TableRow getTableRowByIndex(int index) {
        return tableRows.get(index);
    }

    public List<TableRow> getTableRows() {
        return tableRows;
    }

    public List<Integer> getColumnWidths() {
        List<Integer> result = new ArrayList<>();

        //считаем кол-во столбцов в таблице
        int countColumn = 0;
        for (TableRow tableRow : tableRows) {
            int countCellInCurrentRow = tableRow.getRowCells().size();
            if (countCellInCurrentRow > countColumn) countColumn = countCellInCurrentRow;
        }

        //для каждого столбца ищем максимальный размер содержимого
        for (int i = 0; i < countColumn; i++) {
            int columnWidth = 0;
            for (TableRow tableRow : tableRows) {
                if (tableRow.getRowCells().size() >= i + 1) {
                    int currentColumnWidth = tableRow.getRowCells().get(i).getDataWidth();
                    if(currentColumnWidth>columnWidth) columnWidth = currentColumnWidth;
                } else {
                    continue;
                }
            }
            result.add(columnWidth);
        }
        return result;
    }
}

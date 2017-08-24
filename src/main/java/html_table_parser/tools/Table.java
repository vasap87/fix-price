package html_table_parser.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kotov.aleksandr on 18.08.2016.
 */
public class Table {
    private List<TableRow> tableRows;
    private List<EmptySpace> emptySpaces;
    private String tablePreview;

    public void addTableRows(List<TableRow> tableRows) {
        this.tableRows = tableRows;
    }


    public String getTablePreview() {
        tablePreview = getPreview().toString();
        return tablePreview;
    }

    public List<TableRow> getTableRows() {
        return tableRows;
    }

    private List<Integer> getColumnWidths() {
        List<Integer> result = new ArrayList<>();

        int columnCount = getMaxColumnCount();
        for (int i = 0; i <= columnCount; i++) {
            int res = 0;
            for (TableRow row : tableRows) {
                for (TableCell cell : row.getRowCells()) {
                    if (cell.getColumnPos() == i) {
                        if (cell.getDataWidth() > res) res = cell.getDataWidth();
                    }
                }
            }
            result.add(res);
        }
        return result;
    }

    private int getMaxColumnCount() {
        int result = 0;
        for (TableRow row : tableRows) {
            int tempSize = 0;
            for (TableCell cell : row.getRowCells()) {
                tempSize = cell.getColumnPos();
            }
            if (tempSize > result) result = tempSize;
        }
        return result;
    }

    private void setFillEmptySpaces() {
        int countColumns = getCountColumns();
        emptySpaces = new ArrayList<>();
        for (int i = 0; i < tableRows.size(); i++) {
            emptySpaces.add(new EmptySpace(i, countColumns));
        }
    }

    private int getCountColumns() {
        int count = 0;
        for (TableRow row : tableRows) {
            int temp = 0;
            for (TableCell cell : row.getRowCells()) {
                temp += cell.getColspan();
            }
            if (temp > count) count = temp;
        }
        return count;
    }

    private void build() {
        setFillEmptySpaces(); //заполняем по умолчанию коллекцию свободных мест
        for (int i = 0; i < tableRows.size(); i++) {
            TableRow row = tableRows.get(i);
            for (int j = 0; j < row.getRowCells().size(); j++) {
                TableCell cell = row.getRowCells().get(j);
                cell.updateDataHeight();
                int colspan = cell.getColspan();
                int rowspan = cell.getRowspan();
                int deltaX = colspan - 1;
                int deltaY = rowspan - 1;
                EmptySpace emptySpace = emptySpaces.get(i); //получаем объект с пустыми местами в строке
                int column = emptySpace.getFirstEmpty();    //получаем индекс первого свободного места в строке
                cell.setColumnPos(column);                  //устанавливаем для ячейки в качестве столбца полученный индекс
                emptySpace.setBusy(column);                 //устанавливаем для полученого индекса столбца признак занятого
                if (deltaX > 0) {                               //если надо заблокировать ещё ячейки в этом ряду
                    for (int k = column; k <= column + deltaX; k++) {
                        emptySpace.setBusy(k);
                    }
                }
                if (deltaY > 0) {                               //если надо заблокировать эту ячейку в нескольких строках ниже
                    for (int k = i + 1; k <= i + deltaY; k++) {//со следующей строки до строки с индексом (текущая + дельтаY)
                        for (int g = column; g <= column + deltaX; g++) { //с текущего столбца до столбца с индексом (текущий + дельтаX)
                            if (k >= 0 && k < emptySpaces.size()) {
                                emptySpaces.get(k).setBusy(g);
                            }
                        }
                    }
                }
                cell.setY(i);

            }

        }
    }

    private void setCellsPreview() {
        List<Integer> columnWidths = getColumnWidths();
        int columnCount = getMaxColumnCount();
        for (int i = 0; i <= columnCount; i++) {//перебираем столбцы
            for (int j = 0; j < getTableRows().size(); j++) {
                TableRow row = getTableRows().get(j);
                for (TableCell cell : row.getRowCells()) {
                    if (cell.getColumnPos() == i) {
                        cell.setPreview(cellPreview(cell, columnWidths));
                    }
                }
            }
        }
    }

    private StringBuilder cellPreview(TableCell cell, List<Integer> columnWidths) {
        int cellHeight = cell.getDataHeight();
        int cellWidth = getCellWidth(columnWidths, cell.getColumnPos(), cell.getColspan());
        StringBuilder currentCell = new StringBuilder();
        for (int i = 0, p = 0; i < cellHeight; i++) {
            if (i == 0 || i == cellHeight - 1) {
                currentCell.append('+');
                appendSymbol(currentCell, '-', cellWidth);
                currentCell.append("+");
            } else {
                currentCell.append('|');
                int dataWidth = cell.getData().length();
                if (!cell.isDrowedData()) {
                    for (int j = 0; j < dataWidth; j++) {
                        currentCell.append(cell.getData().charAt(j));
                    }
                    appendSymbol(currentCell, ' ', cellWidth - cell.getData().length());
                    cell.setStartPos(i);
                    cell.setDrowedData(true);
                } else {
                    // TODO: 30.08.2016 если есть вложенная таблица рисуем её
                    if(cell.isInnerTables()){
                        StringBuilder partOfInnerTable = new StringBuilder(cell.getInnerTablesPreview()[p]);
                        for (int j = 0; j < partOfInnerTable.length(); j++) {
                            currentCell.append(partOfInnerTable.charAt(j));
                        }
                        p++;
                        appendSymbol(currentCell, ' ', cellWidth - partOfInnerTable.length());
                    }else{
                        appendSymbol(currentCell, ' ', cellWidth);
                    }

                }
                currentCell.append("|");
            }
            currentCell.append('\n');
        }
        return currentCell;
    }

    private int getCellWidth(List<Integer> columnWidths, int columnPos, int colspan) {
        int result = 0;
        for (int i = 0; i < colspan; i++) {
            result += columnWidths.get(columnPos + i);
        }
        return result + colspan - 1;
    }


    private void appendSymbol(StringBuilder res, char ch, int counts) {
        for (int i = 0; i < counts; i++) {
            res.append(ch);
        }
    }


    private StringBuilder getPreview() {
        build();
        setCellsPreview();
        StringBuilder sb = new StringBuilder();
        int columnCount = getMaxColumnCount();
        for (int i = 0; i <= columnCount; i++) {                //перебираем столбцы
            for (int j = 0; j < getTableRows().size(); j++) {
                TableRow row = getTableRows().get(j);
                for (TableCell cell : row.getRowCells()) {
                    if (cell.getColumnPos() == i) {
                        if (i == 0) {
                            sb.append(appendCell(cell, j));
                        } else {
                            sb = insertCell(sb, cell);
                        }
                    }
                }
            }
        }
        return sb;
    }

    private StringBuilder insertCell(StringBuilder sb, TableCell cell) {
        String[] sbs = sb.toString().split("\n");
        int beforeSymbols = getPreviousSymbols(cell);
        String[] cellPreviews = cell.getPreview().toString().split("\n");
        int beginRow = 0;
        for (int i = 0; i < sbs.length; i++) {
            StringBuilder temp = new StringBuilder(sbs[i]);
            if (temp.length() == beforeSymbols) {
                beginRow = i;
                break;
            }
        }

        if (cell.getY() == 0) { //если первая строка, вставляем всё строки ячейки без первого столбца символов
            for (int i = 0, j = beginRow; i < cellPreviews.length; i++, j++) {
                StringBuilder temp = new StringBuilder(sbs[j]);
                for (int k = 1; k < cellPreviews[i].length(); k++) {
                    temp.append(cellPreviews[i].charAt(k));
                }
                sbs[j] = temp.toString();
            }
        } else {      //если не первая, вставляем без верхней строки сиволов и без первого столбца
            for (int i = 1, j = beginRow; i < cellPreviews.length; i++, j++) {
                StringBuilder temp = new StringBuilder(sbs[j]);
                for (int k = 1; k < cellPreviews[i].length(); k++) {
                    temp.append(cellPreviews[i].charAt(k));
                }
                sbs[j] = temp.toString();
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sbs.length; i++) {
            result.append(sbs[i]).append('\n');
        }
        return result;
    }

    private int getPreviousSymbols(TableCell cell) {
        int cellColumn = cell.getColumnPos();
        int result = 0;
        for (int i = 0; i < cellColumn; i++) {
            result += getColumnWidths().get(i);
        }
        return result + cellColumn + 1;
    }

    private StringBuilder appendCell(TableCell cell, int row) {
        StringBuilder sb;
        StringBuilder cellStr = cell.getPreview();
        if (row == 0) {
            sb = cellStr;
        } else {
            int indexFirstNewRow = cellStr.indexOf("\n") + 1;
            sb = new StringBuilder(cellStr.substring(indexFirstNewRow, cellStr.length()));
        }
        return sb;
    }

}

package html_table_parser.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kotov.aleksandr on 18.08.2016.
 */
public class TableCell {
    private TableRow parentRow;
    List<Table> innerTables;
    private int colspan;
    private int rowspan;
    private int columnPos;
    private String data;
    private boolean isInnerTables;
    private boolean drowedData;
    private int y;
    private int startPos;
    private String innerTablesPreview;
    private int dataHeight;
    private int dataWidth;


    private StringBuilder preview;


    public TableCell(TableRow parentRow, List<Table> innerTables, int colspan, int rowspan, String data) {
        this.parentRow = parentRow;
        this.colspan = colspan;
        this.rowspan = rowspan;
        this.data = data;
        if (innerTables != null) {
            this.innerTables = innerTables;
            setInnerTablesPreview();
            isInnerTables = true;
        } else {
            this.innerTables = new ArrayList<>();
        }
        previousDataWidth();
        previousDataHeight();
    }

    public String[] getInnerTablesPreview() {
        return innerTablesPreview.split("\n");
    }

    private void setInnerTablesPreview() {
        StringBuilder sb = new StringBuilder();
        for (Table innerTable : innerTables) {
            sb.append(innerTable.getTablePreview());
        }
        innerTablesPreview = sb.toString();
    }

    public void setPreview(StringBuilder preview) {
        this.preview = preview;
    }

    public StringBuilder getPreview() {
        return preview;
    }


    public int getY() {
        return y;
    }


    public int getDataWidth() {
        return dataWidth;
    }
    private void previousDataWidth(){
        if (innerTables.size() == 0) {
            dataWidth = data.length();
        } else {
            String[] previewParts = innerTablesPreview.split("\n");
            int tableWidth = previewParts[0].length();
            if (data.length() > tableWidth) tableWidth = data.length();
            dataWidth = tableWidth;
        }
    }

    public int getColumnPos() {
        return columnPos;
    }

    public void setColumnPos(int columnPos) {
        this.columnPos = columnPos;
    }

    public String getData() {
        return data;
    }

    public int getColspan() {
        return colspan;
    }

    public int getRowspan() {
        return rowspan;
    }

    public int getDataHeight() {
        return dataHeight;
    }
    private void previousDataHeight(){
        int res;
        if (innerTables.size() == 0) {
            res = rowspan * 2 + 1;
        } else {
            int result = rowspan * 2 + 1;
            String[] previewParts = innerTablesPreview.split("\n");
            int tableHeigth = previewParts.length;
            result += tableHeigth;
            res = result;
        }
        dataHeight = res;
    }

    public void updateDataHeight(){
        if (rowspan == 1) {
            if (parentRow.getHeight() > dataHeight) dataHeight = parentRow.getHeight();
        } else {
            int tempHeight = 0;
            int rowNum = parentRow.getNum();
            for (int i = rowNum; i < rowNum + rowspan; i++) {
                tempHeight += parentRow.getParentTable().getTableRows().get(i).getHeight();
            }
            dataHeight = tempHeight - rowspan +1;
        }
    }

    public boolean isInnerTables() {
        return isInnerTables;
    }

    public boolean isDrowedData() {
        return drowedData;
    }

    public void setDrowedData(boolean isDrowed) {
        drowedData = isDrowed;
    }


    @Override
    public String toString() {
        return "TableCell{" +
                "colspan=" + colspan +
                ", rowspan=" + rowspan +
                ", columnPos=" + columnPos +
                ", data='" + data + '\'' +
                '}';
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setY(int y) {
        this.y = y;
    }
}

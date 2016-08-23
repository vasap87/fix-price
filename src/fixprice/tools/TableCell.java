package fixprice.tools;

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
    private String data;
    private boolean hasInnerTables;

    public TableCell(TableRow parentRow, List<Table> innerTables, int colspan, int rowspan, String data) {
        this.parentRow = parentRow;
        this.colspan = colspan;
        this.rowspan = rowspan;
        this.data = data;
        if(innerTables!=null) this.innerTables = innerTables;
        else{
            this.innerTables = new ArrayList<>();
        }
//        System.out.println(data + " colspan="+colspan + " rowspan="+rowspan+ " row="+parentRow.getNum());

    }

    public void addInnerTable(Table table){
        innerTables.add(table);
    }

    public int getDataWidth() {
        if(innerTables.size()==0){
            return data.length();
        }else{
            int width = 0;
            for (Table innerTable : innerTables) {
                int tableWidth = innerTable.getMaxWidth();
                if (tableWidth > width) width = tableWidth;
            }
            String[] arrData = data.split("\t");
            for (String pData: arrData) {
                if(pData.length()>width) width=pData.length();
            }
            return width;
        }
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
        int result = 0;
        String[] dataParts = data.split("\t");
        if (dataParts.length>0) result+= dataParts.length;
        if (innerTables.size()>0) {
            int innerTableRowsRezult=0;
            for (Table innerTable: innerTables) {
                for (TableRow innerTableRow: innerTable.getTableRows()) {
                    innerTableRowsRezult+=innerTableRow.getHeight();
                }
            }
            result+=(innerTableRowsRezult*2+1);
        }
        return result;
    }
}

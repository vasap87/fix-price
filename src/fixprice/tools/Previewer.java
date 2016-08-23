package fixprice.tools;

import java.util.List;

/**
 * Created by admin on 22.08.2016.
 */
public class Previewer {
    private static Previewer instance = new Previewer();

    private Previewer() {

    }

    public static Previewer getInstance() {
        return instance;
    }

    public String getPreview(Table table) {
        StringBuilder res = new StringBuilder();
        List<Integer> columnWidths = table.getColumnWidths();
        for (TableRow row : table.getTableRows()) {
            int rowHeight = row.getHeight();
            for (int j = 1; j <= rowHeight; j++) {
                if (j == 1 || j == rowHeight) {
                    res.append(appendBorder(row, columnWidths));
                } else {
                    res.append((appendData(row, columnWidths)));
                }
            }
            res.append('\n');
        }
        return res.toString();
    }

    private StringBuilder appendData(TableRow row, List<Integer> columnWidths) {
        return null;
    }

    private String appendBorder(TableRow row, List<Integer> columnWidths) {
        StringBuilder res = new StringBuilder("+");
        for (int i = 0; i < row.getRowCells().size(); i++) {
            if(row.getRowCells().get(i).getColspan()>1){
                for (int j = 0; j < row.getRowCells().get(i).getColspan(); j++) {
                    for (int k = 0; k < columnWidths.get(i+j); k++) {
                        res.append('-');
                    }
                }
                res.append('+');
            }else {
                for (int k = 0; k < columnWidths.get(i); k++) {
                    res.append('-');
                }
                res.append('+');
            }
        }
        return res.toString();
    }
}

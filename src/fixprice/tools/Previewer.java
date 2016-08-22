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
            for (int i = 0; i < row.getRowCells().size(); i++) {
                for (int j = 1; j < rowHeight; j++) {
                    if(j==1|| j == rowHeight){

                    }
                }
            }
        }
        return res.toString();
    }
}

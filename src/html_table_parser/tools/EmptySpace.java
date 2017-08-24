package html_table_parser.tools;

import java.util.Arrays;

/**
 * Created by kotov.aleksandr on 30.08.2016.
 */
public class EmptySpace {
    int row;
    int[] values;

    public EmptySpace (int row, int columns){
        this.row = row;
        values = new int[columns];
        //по умолчанию создадимм массив с единичками, обозначающими что все столбцы сводобны
        Arrays.fill(values, 1);
    }

    public void setBusy(int column){
        if(column<values.length) {
            values[column] = 0;
        }
    }

    public boolean isBusy(int column){
        return values[column] == 0;
    }

    public int getFirstEmpty(){
        int res = -1;
        for (int i = 0; i < values.length; i++) {
            if(values[i]==1){
                res = i;
                break;
            }
        }
        return res;
    }
}

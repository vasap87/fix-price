package fixprice.tools;

/**
 * Created by kotov.aleksandr on 19.08.2016.
 */
public class Panel {
    private int widht;
    private int heiht;
    private Table table;

    public Panel(Table table) {
        this.table = table;
    }

    public String getConsoleView(){
        int width = table.getMaxWidth();

        return ""+width;
    }
}

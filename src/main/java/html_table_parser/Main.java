package html_table_parser;

import html_table_parser.parseHTML.HtmlParser;
import html_table_parser.tools.Previewer;
import html_table_parser.tools.Table;

/**
 * Created by kotov.aleksandr on 18.08.2016.
 */
public class Main {
    public static void main(String[] args) {
        HtmlParser parser = new HtmlParser("inputFile.html");
        parser.parse();
        Table table = parser.getTable();
        Previewer.getInstance().getPreview(table);
    }
}

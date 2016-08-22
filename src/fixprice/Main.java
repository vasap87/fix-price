package fixprice;

import fixprice.parseHTML.HtmlParser;
import fixprice.tools.Previewer;


/**
 * Created by kotov.aleksandr on 18.08.2016.
 */
public class Main {
    public static void main(String[] args) {
        HtmlParser parser = new HtmlParser("inputFile.html");
        parser.parse();

        System.out.println(Previewer.getInstance().getPreview(parser.getTable()));


    }
}

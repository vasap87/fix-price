package fixprice;

import fixprice.parseHTML.HtmlParser;
import fixprice.tools.Table;
import fixprice.tools.TableCell;
import fixprice.tools.TableRow;
import wagu.Block;
import wagu.Board;

import java.util.List;

/**
 * Created by kotov.aleksandr on 18.08.2016.
 */
public class Main {
    public static void main(String[] args) {
        HtmlParser parser = new HtmlParser("inputFile.html");
        parser.parse();

        TableRow firstRow = parser.getTable().getTableRowByIndex(0);
        List<TableCell> cells = firstRow.getRowCells();
        Board board = new Board(firstRow.getWidth() + firstRow.getCellCount() + 1);

        Block firstCell = new Block(board, cells.get(0).getDataWidth(), 1, cells.get(0).getData());
        board.setInitialBlock(firstCell);

        Block secondCell = new Block(board, cells.get(1).getDataWidth(), 1, cells.get(1).getData());
        firstCell.setRightBlock(secondCell);

        Block thirdCell = new Block(board, cells.get(2).getDataWidth(), 1, cells.get(2).getData());
        secondCell.setRightBlock(thirdCell);

        System.out.println(board.invalidate().build().getPreview());


    }
}

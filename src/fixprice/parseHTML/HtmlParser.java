package fixprice.parseHTML;

import fixprice.tools.Table;
import fixprice.tools.TableCell;
import fixprice.tools.TableRow;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kotov.aleksandr on 16.08.2016.
 */
public class HtmlParser {

    private String path;
    private Table table;

    /**
     * @param path путь к файлу который парсим
     */
    public HtmlParser(String path) {
        this.path = path;
    }


    public void parse() {
        //парсим файл при помощи библиотеки Jsoup. метод parse принимает данные разного типа, можно указать URL
        try {
            Document doc = Jsoup.parse(new File(path), "UTF-8");
            //ищем первую таблицу в документе
            Element eTable = doc.getElementsByTag("table").first();
            this.table = new Table(0);
            parseTable(eTable, table);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseTable(Element eTable, Table table) {
        Elements tBody = eTable.children();
        List<TableRow> tableRows = new ArrayList<>();
        for (Element body : tBody) {
            Elements rows = body.children();
            //парсим отдельно каждую строку
            for (int i = 0; i < rows.size(); i++) {
                TableRow tableRow = new TableRow(i);
                tableRows.add(tableRow);
                parseRow(rows.get(i), tableRow);
            }
        }
        table.addTableRows(tableRows);
    }

    private void parseRow(Element row, TableRow tableRow) {
        //строка содержит столбцы
        Elements columns = row.children();
        for (Element eCell : columns) {
            int colspan = 0, rowspan = 0;
            if (eCell.hasAttr("colspan")) colspan = Integer.parseInt(eCell.attr("colspan"));
            if (eCell.hasAttr("rowspan")) rowspan = Integer.parseInt(eCell.attr("rowspan"));
            StringBuilder data = new StringBuilder(eCell.text());
            StringBuilder resData = new StringBuilder();
            Elements eInnerTables = eCell.getElementsByTag("table");
            if (eInnerTables.size() > 0) {
                List<Table> innerTables = new ArrayList<>();
                for (Element eInnerTable : eInnerTables) {
                    Table innerTable = new Table(1);
                    innerTables.add(innerTable);
                    parseTable(eInnerTable, innerTable);
                }
                for (Element eInnerTable : eInnerTables) {
                    resData.append(data.substring(0, data.indexOf(eInnerTable.text())).trim()).append("\t");
                    data.delete(0, data.indexOf(eInnerTable.text()) + eInnerTable.text().length());
                }
                tableRow.addRowCell(new TableCell(tableRow, innerTables, colspan, rowspan, resData.toString()));
            } else {
                tableRow.addRowCell(new TableCell(tableRow, null, colspan, rowspan, data.toString()));
            }
        }
    }

    public Table getTable() {
        return table;
    }
}

package html_table_parser.parseHTML;

import html_table_parser.tools.Table;
import html_table_parser.tools.TableCell;
import html_table_parser.tools.TableRow;
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
            this.table = new Table();
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
                //i = номер строки
                TableRow tableRow = new TableRow(table, i);
                parseRow(rows.get(i), tableRow);
                tableRows.add(tableRow);
            }
        }
        table.addTableRows(tableRows);
    }

    private void parseRow(Element row, TableRow tableRow) {
        //строка содержит столбцы
        Elements columns = row.children();
        for (int i = 0; i < columns.size(); i++) {
            Element eCell = columns.get(i);
            int colspan = 1, rowspan = 1;
            if (eCell.hasAttr("colspan")) colspan = Integer.parseInt(eCell.attr("colspan"));
            if (eCell.hasAttr("rowspan")) rowspan = Integer.parseInt(eCell.attr("rowspan"));
            // TODO: 24.08.17 взять текст только этого элемента, без вложенных
            StringBuilder data = new StringBuilder(eCell.data());
            StringBuilder resData = new StringBuilder();
            Elements eInnerTables = eCell.getElementsByTag("table");
            if (eInnerTables.size() > 0) {
                List<Table> innerTables = new ArrayList<>();
                for (Element eInnerTable : eInnerTables) {
                    Table innerTable = new Table();
                    innerTables.add(innerTable);
                    parseTable(eInnerTable, innerTable);
                }
                tableRow.addRowCell(new TableCell(tableRow, innerTables, colspan, rowspan, data.toString()));
            } else {
                tableRow.addRowCell(new TableCell(tableRow, null, colspan, rowspan, data.toString()));
            }
        }
    }


    public Table getTable() {
        return table;
    }
}

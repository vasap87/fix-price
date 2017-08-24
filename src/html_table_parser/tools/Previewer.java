package html_table_parser.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by kotov.aleksandr on 22.08.2016.
 */
public class Previewer {
    private static Previewer instance = new Previewer();


    private Previewer() {

    }

    public static Previewer getInstance() {
        return instance;
    }

    public void getPreview(Table table) {
        inFileDraw(table.getTablePreview());
    }

    //метод пишет в файл символы
    private void inFileDraw(String sb) {
        FileWriter fr = null;
        try {
            fr = new FileWriter(new File("outputFile.txt"), false);
            fr.write(sb.toString());
            fr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("результат записан в файл: outputFile.txt");
        System.out.print(sb);
    }


}

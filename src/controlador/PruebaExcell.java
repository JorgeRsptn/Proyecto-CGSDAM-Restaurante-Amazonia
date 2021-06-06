package controlador;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;


public class PruebaExcell {

	 /**
     * Crea una hoja Excel y la guarda.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Se crea el libro
        HSSFWorkbook libro = new HSSFWorkbook();

        // Se crea una hoja dentro del libro
        HSSFSheet hoja = libro.createSheet();

        // Se crea una fila dentro de la hoja
        HSSFRow fila = hoja.createRow(0);

        // Se crea una celda dentro de la fila
        HSSFCell celda = fila.createCell((short) 0);

        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto = new HSSFRichTextString("hola mundo");
        celda.setCellValue(texto);

        // Se salva el libro.
        try {
            FileOutputStream elFichero = new FileOutputStream("holamundo.xls");
            libro.write(elFichero);
            elFichero.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

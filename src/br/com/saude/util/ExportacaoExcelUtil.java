//  Trata-se de um teste para importar tudo pra uma planilha, deu erro e fiquei com dor de cabeça, falo com o prof depois
package br.com.saude.util;

import br.com.saude.dao.PressaoDAO.Registro;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportacaoExcelUtil {

    public void exportarRegistros(String nomePaciente, List<Registro> registros) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Registros de Pressão");

        // Estilo do cabeçalho
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Cabeçalho
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Data");
        header.createCell(1).setCellValue("Sistólica");
        header.createCell(2).setCellValue("Diastólica");
        for (Cell cell : header) {
            cell.setCellStyle(headerStyle);
        }

        // Dados
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int rowNum = 1;
        for (Registro r : registros) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getData().format(fmt));
            row.createCell(1).setCellValue(r.getSistolica());
            row.createCell(2).setCellValue(r.getDiastolica());
        }

        // Autosize colunas
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // Caminho e salvamento
        String fileName = "Registros_" + nomePaciente.replaceAll("\\s+", "_") + ".xlsx";
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            workbook.write(out);
            System.out.println("Arquivo salvo como " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
}

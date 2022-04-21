package client.mainWindow.pages.labPages.labPage.report;

import client.mainWindow.pages.labPages.labPage.base.stage.StageController;
import com.dansoftware.pdfdisplayer.PDFDisplayer;
import com.google.gson.JsonObject;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import org.apache.commons.io.FilenameUtils;

import org.controlsfx.dialog.ProgressDialog;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;

public class ReportController {

    @FXML
    private ScrollPane viewerRoot;

    @FXML
    private Button reportRulesButton;

    @FXML
    private Button sendReportButton;

    @FXML
    private Button closeViewerButton;

    private ReportModel reportModel;
    private ReportView reportView;

    private File file;
    private File outputFile;
    private PDFDisplayer displayer;
    private String inputfilepath;

    private ImageView dragNdrop = new ImageView("drag-and-drop.png");

    @FXML
    public void initialize() {

        displayer = new PDFDisplayer();

        viewerRoot.setContent(dragNdrop);

        viewerRoot.setOnDragOver(event -> {
            if (event.getGestureSource() != viewerRoot
                    && event.getDragboard().hasFiles()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        viewerRoot.setOnDragDropped(event -> {
            System.out.println("something dropped dropped");
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                if(db.getFiles().size() == 1) {
                    file = db.getFiles().get(0);
                    success = true;
                    System.out.println("file is "+file.getName());
                    String extension = FilenameUtils.getExtension(file.getName());
                    if(extension.equals("pdf")){
                        openPdf(file);
                    }else if(extension.equals("docx") || extension.equals("doc")){
                        convertToPdf(file);
                    }

                }
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        });

        reportRulesButton.setOnAction(event -> {
            viewerRoot.setContent(null);
            viewerRoot.setContent(displayer.toNode());
            try {
                displayer.loadPDF(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("docx_rules.pdf")).toURI()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        closeViewerButton.setOnAction(event -> {
            viewerRoot.setContent(null);
            viewerRoot.setContent(dragNdrop);
        });

    }

    private void convertToPdf(File file){

        try {

            ConvertToPdfTask convertToPdfTask = new ConvertToPdfTask(file);
            convertToPdfTask.setOnSucceeded(event -> openPdf(convertToPdfTask.getValue()));
            Thread thread = new Thread(convertToPdfTask);
            thread.setDaemon(true);

            new ProgressDialog(convertToPdfTask);

            thread.start();

        }catch (Exception e){

        }

    }

    private void openPdf(File file) {

        viewerRoot.setContent(null);

        try {
            displayer.loadPDF(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewerRoot.setContent(displayer.toNode());

    }

    private class ConvertToPdfTask extends Task<File>{

        File file;

        public ConvertToPdfTask(File file){
            this.file = file;
        }

        @Override
        protected File call() throws Exception {

            inputfilepath = file.getPath();

            InputStream templateInputStream = new FileInputStream(inputfilepath);
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

            outputFile = new File(inputfilepath + ".pdf");
            FileOutputStream os = new FileOutputStream(outputFile);
            Docx4J.toPDF(wordMLPackage, os);
            os.flush();
            os.close();

            return outputFile;

        }

    }

}

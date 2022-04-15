package tools;

import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    private static class BufferedImageTranscoder extends ImageTranscoder {
        private BufferedImage img = null;

        @Override
        public BufferedImage createImage(int width, int height) {
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        @Override
        public void writeImage(BufferedImage img, TranscoderOutput to) {
            this.img = img;
        }

        public BufferedImage getBufferedImage() {
            return img;
        }
    }

    /**
     *
     * @param urlSVG
     * @return
     */
    public static Image getImageSVG(String urlSVG) {
        BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
        try (InputStream file = ClassLoader.getSystemResourceAsStream(urlSVG)) {
            TranscoderInput transIn = new TranscoderInput(file);
            try {
                transcoder.transcode(transIn, null);
                Image img = SwingFXUtils.toFXImage(transcoder.getBufferedImage(), null);
                return img;
            } catch (TranscoderException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        catch (IOException io) {
            io.printStackTrace();
            return null;
        }
    }

    /**
     * Function for converting cyrillicText from Cp1251 to UTF-8 charset
     * (uses for stage title)
     * @param cyrillic - cyrillic text in Cp1251 charset
     * @return cyrillic text in UTF-8 charset
     * @throws UnsupportedEncodingException - because of getBytes()
     */
    public static String getCyrillicText(String cyrillic) throws UnsupportedEncodingException {
        try {
            return new String(cyrillic.getBytes("Cp1251"), StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException ignored) {}
        return null;
    }

    /**
     * Метод изменения путей в HTML на абсолютные
     *
     * Алгоритм работы метода:
     * 0. Получить текст из файла HTML.
     * 1. Получить директорию, где располагается файл HTML.
     * 2. Найти очередное вхождение src=" или src= " (завести регулярку).
     * Если вхождений нет, вернуть полученную строку.
     * 3. Считать всё до след. кавычки и извлечь из текста.
     * 4. Класть считанную строку в аккумулятор.
     * 5. Добавить после src = " + entity.file:/ + директория HTML + относительный путь ".
     * 6. Очистить аккумулятор.
     * 7. Вернуться к пункту 2.
     *
     * @param path - относительный путь к HTML
     * @return - текст из HTML файла с абсолютными путями
     */
    public static String replaceSrcWithAbsolutePath(String path) {

        String text = "";
        try {
            File htmlFile = new File(path);
            String htmlPath = htmlFile.getAbsolutePath();
            htmlPath = htmlPath.substring(0, htmlPath.lastIndexOf('\\'));

            text = FileUtils.readFileToString(htmlFile, "UTF-8");
            Pattern pattern = Pattern.compile("src( )?=( )?\"((?!file:)+)");

            StringBuilder acc = new StringBuilder(); int index = 0;

            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                index = matcher.end();
                while (text.charAt(index) != '\"') {
                    acc.append(text.charAt(index));
                    text = text.substring(0, index) + text.substring(index+1);
                }
                text = text.substring(0, index) + "entity.file:/" + htmlPath + "\\" + acc + "\"" + text.substring(index + 1);

                matcher = pattern.matcher(text);

                acc.delete(0, acc.length());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void addHTMLOutputListener(WebEngine webEngine) {
        webEngine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        Document doc = webEngine.getDocument();
                        try {
                            Transformer transformer = TransformerFactory.newInstance().newTransformer();
                            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                            transformer.transform(new DOMSource(doc),
                                    new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }
}

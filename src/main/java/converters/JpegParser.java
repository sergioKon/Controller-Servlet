package converters;

import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class JpegParser extends DataParser {
    public JpegParser(){
        this.extension= ".jpeg";
        this.bufferSize= 1024;
    }
    public void  saveToFile(InputStream fileInputStream ) {
        BufferedImage image;
        try (FileOutputStream fileOutputStream = new FileOutputStream("dice-test.jpg")){
             image = ImageIO.read(fileInputStream);
            final BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            convertedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
            final boolean canWrite = ImageIO.write(convertedImage, "jpg", fileOutputStream);
            if (!canWrite) {
                throw new IllegalStateException("Failed to write image.");
            }
        } catch (IOException e) {
           log.error(e);
        }
    }
}


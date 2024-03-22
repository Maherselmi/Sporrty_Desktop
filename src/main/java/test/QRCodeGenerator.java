package test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeGenerator {

    public static void main(String[] args) {
        String text = "Hello, QR code!";
        String filePath = "qrcode.png";
        int size = 250;
        String fileType = "png";

        try {
            // Créer le QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);

            // Convertir la matrice de bits en image BufferedImage
            BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            // Écrire l'image BufferedImage dans un fichier
            ImageIO.write(bufferedImage, fileType, new File(filePath));

            System.out.println("QR code créé avec succès!");

        } catch (WriterException | IOException e) {
            System.out.println("Erreur lors de la création du QR code : " + e.getMessage());
        }
    }
}

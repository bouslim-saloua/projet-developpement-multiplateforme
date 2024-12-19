package com.emsi.quiz.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QrCodeReader {
	 public static String readQRCode(String filePath) throws IOException, NotFoundException {
	        BufferedImage bufferedImage = ImageIO.read(new File(filePath));
	        LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
	        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));

	        Result result = new MultiFormatReader().decode(binaryBitmap);
	        return result.getText();
	    }
}

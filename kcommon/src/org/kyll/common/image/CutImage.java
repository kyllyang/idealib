package org.kyll.common.image;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

public class CutImage {
	public static void main(String... args) throws Exception {
		BufferedImage imageIn = ImageIO.read(new File("H:\\download\\femat.png"));
		int width = imageIn.getWidth();
		int height = imageIn.getHeight();
		Image imageSrc = imageIn.getScaledInstance(width, height, Image.SCALE_DEFAULT);

		int i = 0;
		for (int y = 2; y < height; y+=17) {
			for (int x = 2; x < width; x+=17) {
				ImageFilter filter = new CropImageFilter(x, y, 16, 16);
				Image imageDest = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(imageSrc.getSource(), filter));
				BufferedImage imageOut = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
				Graphics g = imageOut.getGraphics();
				g.drawImage(imageDest, 0, 0, null);
				g.dispose();
				File file = new File("H:\\download\\test\\" + (++i) + ".png");
				ImageIO.write(imageOut, "PNG", file);
				System.out.println(file);
			}
		}
	}   
}

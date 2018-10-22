import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Concatenador {


	public static void main(String[] args) throws IOException {
		
		//Inicializa os argumentos
		int x0 = Integer.parseInt(args[0]); 
		int y0 = Integer.parseInt(args[1]);
		int x1 = Integer.parseInt(args[2]); 
		int	y1 = Integer.parseInt(args[3]);
		int z = Integer.parseInt(args[4]);
		String path = args[5];
		
		// troca '\' por '\\'
		path.replaceAll("\\\\", "\\\\\\\\");
		
		int x = x1 - x0;
		int y = y1 - y0;

		//monta a matriz de imagens
		BufferedImage images[][] = new BufferedImage[x+1][y+1];
		for(int i = 0; i <= x; i++) {
			for(int j = 0; j <= y; j++) {
				int cx = x1 - i;
				int cy = y1 - j;
				images[i][j] = ImageIO.read(
						new URL("http://mt1.google.com/vt/lyrs=s&x=" + cx + "&y=" + cy + "&z=" + z));
			}
		}
		
		//Inicializa o tamanho da imagem
		int widthTotal = 0;
		for(int i = 0; i <= x; i++) {
			widthTotal += images[i][0].getHeight();
		}
		int heightTotal = 0;
		for(int j = 0; j <= y; j++) {
			heightTotal += images[0][j].getHeight();
		}
		
		//concatena
		int heightCurr = 0;
		int widthCurr = 0;
		BufferedImage concatImage = new BufferedImage(widthTotal, heightTotal, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = concatImage.createGraphics();
		for(int j = y; j >= 0; j--) {
			for(int i = x; i >= 0; i--) {
				g2d.drawImage(images[i][j], widthCurr, heightCurr, null);
				widthCurr += images[i][0].getWidth();
			}
			heightCurr += images[0][j].getHeight();
			widthCurr = 0;
		}
		g2d.dispose();
		
		//exporta a imagem
		ImageIO.write(concatImage, "jpeg", new File(path));

	}


}

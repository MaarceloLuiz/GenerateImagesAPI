import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageGenerator
{
    public void create(InputStream inputStream, String fileName, String text) throws Exception
    {
        text = text.toUpperCase();
        
        BufferedImage originalImage = ImageIO.read(inputStream);

        String[] parts = text.split(":");
        if(parts.length == 1)
        {
            parts = text.split(",");
        }

        int fontSize = getFontSize(parts,(Graphics2D)originalImage.getGraphics(),originalImage.getWidth()-40);
        int alturaLinha = getLineSize(parts,(Graphics2D)originalImage.getGraphics(),fontSize);
        int captionSpace = parts.length * alturaLinha + 20;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int newHeight = height + captionSpace;

        BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);
        
        Graphics2D graphics = (Graphics2D) newImage.getGraphics();
        graphics.drawImage(originalImage, 0, 0, null);

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, fontSize);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(font);

        int[] superiorsSpacing = new int[parts.length];
        int[] sidesSpacing = new int[parts.length];

        for (int i=0; i<parts.length;i++) 
        { 
            FontMetrics fontMetrics = graphics.getFontMetrics();
            Rectangle2D rectangle = fontMetrics.getStringBounds(parts[i], graphics);
            double textWidth = rectangle.getWidth();
            double textHeight = rectangle.getHeight();
            int leftSpacing = ((width-(int)textWidth)/2);
            //int superiorSpacing = newHeight- (int)heightLine * (parts.length-i);
            int superiorSpacing = newHeight -20 - (int)textHeight * i;//- (int)textHeight * (parts.length-i);

            superiorsSpacing[i] = superiorSpacing;
            sidesSpacing[i] = leftSpacing; 
        }

        for (int i=0; i<parts.length;i++) 
        {
            graphics.drawString(parts[i], sidesSpacing[i], superiorsSpacing[i]);
        }
        

        File directory = new File("IMAGES");
        if(!directory.exists())
        {
            directory.mkdir();
        }

        ImageIO.write(newImage, "png", new File("IMAGES/" + fileName));

    }

    private int getFontSize(String[] parts, Graphics2D graphics, int maxWidth)
    {
        boolean stop = false;
        int fontSize = 1;

        while(!stop)
        {
            Font font = new Font("Comic Sans MS", Font.BOLD, fontSize);
            graphics.setFont(font);


            for (int i=0; i<parts.length;i++) 
            { 
                FontMetrics fontMetrics = graphics.getFontMetrics();
                Rectangle2D rectangle = fontMetrics.getStringBounds(parts[i], graphics);
                double textWidth = rectangle.getWidth();
                if(textWidth>maxWidth)
                {
                    stop = true;
                }
            }

            fontSize++;
        }

        return fontSize-1;
    }

    private int getLineSize(String[] parts, Graphics2D graphics, int fontSize)
    {
        double heightLine = 0;

        Font font = new Font("Comic Sans MS", Font.BOLD, fontSize);
        graphics.setFont(font);

        for (int i=0; i<parts.length;i++) 
        { 
            FontMetrics fontMetrics = graphics.getFontMetrics();
            Rectangle2D rectangle = fontMetrics.getStringBounds(parts[i], graphics);
            double textHeight = rectangle.getHeight();
            if(textHeight>heightLine)
            {
                heightLine = textHeight;
            }
        }

        return (int) heightLine;
    }
}
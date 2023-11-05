package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import static java.lang.Math.*;

public class Rasterization {

    public static void drawRectangle(
            final GraphicsContext graphicsContext,
            final int x, final int y,
            final int width, final int height,
            final Color color)
    {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        for (int row = y; row < y + height; row++)
            for (int col = x; col < x + width; col++)
                pixelWriter.setColor(col, row, color);

    }

    public static void drawCircleAcr(
            final GraphicsContext graphicsContext,
            final int x,final int y,
            final int radius,
            final Color startColor,
            final Color endColor,
            final int startAngle,final int arcAngle)
    {

        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        int angle, x1, y1;
        for(int i = startAngle; i < arcAngle; i ++)
        {
            angle = i;
            x1 = (int) Math.round(radius * cos(angle * PI / 180));
            y1 = (int) Math.round(radius * sin(angle * PI / 180));
            double fraction = (double)(i - startAngle) / (double)(arcAngle - startAngle); //отношение разницы между текущим углом и начальным углом дуги к разнице между начальным и конечным углами дуги
            Color color = interpolate(startColor, endColor, fraction);
            pixelWriter.setColor(x + x1, y + y1, color);
        }
    }
    public static Color interpolate(Color startColor, Color endColor, double fraction) {
        double red =  (startColor.getRed() * (1 - fraction) + endColor.getRed() * fraction);
        double green =  (startColor.getGreen() * (1 - fraction) + endColor.getGreen() * fraction);
        double blue =  (startColor.getBlue() * (1 - fraction) + endColor.getBlue() * fraction);

        return new Color(red, green, blue,1);
    }

}

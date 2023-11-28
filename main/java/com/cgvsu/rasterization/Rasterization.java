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
            final Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        for (int row = y; row < y + height; row++)
            for (int col = x; col < x + width; col++)
                pixelWriter.setColor(col, row, color);

    }

    public static void drawCircleAcr(
            final GraphicsContext graphicsContext,
            final double x, final double y,
            final double radius,
            final Color startColor,
            final Color endColor,
            final double startAngle, final double endAngle) {

        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        double step = (180 / PI) / radius;
        for (double angle = startAngle; angle < endAngle; angle += step) {
            double x1 = radius * cos(angle * PI / 180);
            double y1 = radius * sin(angle * PI / 180);
            double fraction = (angle - startAngle) / (endAngle - startAngle); //отношение разницы между текущим углом и начальным углом дуги к разнице между начальным и конечным углами дуги
            Color color = interpolate(startColor, endColor, fraction);
            pixelWriter.setColor((int) Math.round(x + x1), (int) Math.round(y + y1), color);
        }
    }


    public static void drawCircleArc2(
            final GraphicsContext graphicsContext,
            final int xc, final int yc,
            int radius,
            final int startAngle, final int endAngle) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        int error = 2 - 2 * radius;
        int x = -radius;
        int y = 0;
        //int angle = startAngle;
        do {
                pixelWriter.setColor(xc - x, yc + y, Color.BLACK);
                pixelWriter.setColor(xc + x, yc - y, Color.BLACK);
                pixelWriter.setColor(xc - x, yc - y, Color.BLACK);
                pixelWriter.setColor(xc + x, yc + y, Color.BLACK);

            radius = error;

            if (radius <= y) {
                y++;
                error = error + y * 2 + 1;

            }
            if (radius > x || error > y) {
                x++;
                error = error + x * 2 + 1;
            }

        } while (x < 0);

    }


    public static Color interpolate(Color startColor, Color endColor, double fraction) {
        double red = (startColor.getRed() * (1 - fraction) + endColor.getRed() * fraction);
        double green = (startColor.getGreen() * (1 - fraction) + endColor.getGreen() * fraction);
        double blue = (startColor.getBlue() * (1 - fraction) + endColor.getBlue() * fraction);

        return new Color(red, green, blue, 1);
    }

}

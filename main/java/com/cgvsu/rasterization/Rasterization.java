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

        double startPointX = xc + radius * cos(startAngle * PI / 180);
        double startPointY = yc + radius * sin(startAngle * PI / 180);

        double endPointX = xc + radius * cos(endAngle * PI / 180);
        double endPointY = yc + radius * sin(endAngle * PI / 180);

        do {
                if(isPointOnArc(xc,yc,startAngle,endAngle,xc-x,yc+y) ){
                    pixelWriter.setColor(xc - x, yc + y, Color.BLACK);
                }
                if(isPointOnArc(xc,yc,startAngle,endAngle,xc+x,yc-y)){
                    pixelWriter.setColor(xc + x, yc - y, Color.BLACK);
                }
                if(isPointOnArc(xc,yc,startAngle,endAngle,xc-x,yc-y)){
                    pixelWriter.setColor(xc - x, yc - y, Color.BLACK);
                }
                if(isPointOnArc(xc,yc,startAngle,endAngle,xc+x,yc+y)){
                    pixelWriter.setColor(xc + x, yc + y, Color.BLACK);
                }


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

    public static boolean isPointOnArc(double centerX, double centerY, double startAngle, double endAngle, double x, double y) {
        startAngle = Math.toRadians(startAngle);
        endAngle = Math.toRadians(endAngle);
        double angle = Math.atan2(y - centerY, x - centerX); // угол между центром окружности и заданной точкой

        if (angle < 0) {
            angle += 2 * Math.PI; // приводим угол к диапазону от 0 до 2π
        }
        if (startAngle <= endAngle) {
            return angle >= startAngle && angle <= endAngle; // проверяем, находится ли угол между начальным и конечным углами
        } else {
            return angle >= startAngle || angle <= endAngle; // для случая, когда конечный угол меньше начального
        }
    }

}

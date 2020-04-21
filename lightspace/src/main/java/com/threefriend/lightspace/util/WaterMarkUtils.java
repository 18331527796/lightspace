package com.threefriend.lightspace.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class WaterMarkUtils {

    private static BufferedImage image;
    private static int imageWidth = 430;  //图片的宽度
    private static int imageHeight = 480; //图片的高度

    //生成图片文件
    @SuppressWarnings("restriction")
    public static void createImage(String fileLocation) {
        BufferedOutputStream bos = null;
        if (image != null) {
            try {
                FileOutputStream fos = new FileOutputStream(fileLocation);
                bos = new BufferedOutputStream(fos);

//                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
//                encoder.encode(image);
                ImageIO.write(image, "jpg", bos);
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {//关闭输出流
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String graphicsGeneration(String scene,String name, String path) {
        int H_title = 50;     //头部高度
        int H_mainPic = 430;  //轮播广告高度

        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);

        //***********************页面头部
        Graphics title = image.createGraphics();
        //设置区域颜色
        title.setColor(new Color(255, 255, 255));
        //填充区域并确定区域大小位置
        title.fillRect(0, 0, imageWidth, H_title);
        //设置字体颜色，先设置颜色，再填充内容
        title.setColor(Color.BLACK);
        //设置字体
        Font titleFont = new Font("楷体", Font.BOLD, 40);
        title.setFont(titleFont);
        FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(titleFont);
        int x = (430 - fm.stringWidth(name)) / 2;
        title.drawString(name, x, (H_title) / 2 + 20);

        //***********************插入中间广告图
        Graphics mainPic = image.getGraphics();
        BufferedImage bimg = null;
        try {
            bimg = javax.imageio.ImageIO.read(new java.io.File(path + scene + name + ".jpg"));
        } catch (Exception ignored) {
        }

        if (bimg != null) {
            mainPic.drawImage(bimg, 0, H_title, imageWidth, H_mainPic, null);
            mainPic.dispose();
        }
        createImage(path + scene + name + ".jpg");
        return path + scene + name + ".jpg";
    }


}
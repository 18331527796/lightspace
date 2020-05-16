package com.threefriend.lightspace.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DrowMailUtils {

	private static BufferedImage image;
	private static int imageWidth = 595; // 图片的宽度
	private static int imageHeight = 842; // 图片的高度
	private static final String words = ""
			+ "&及时预警，发现孩子视力问题，是近视防控中非常重要的一环， 一个&学"
			+ "期一次的筛查，并不能及时有效的发现孩子视力问题。特别是疫情防控&期"
			+ "间，我们不便于组织孩子们集中检测视力，也不能及时了解孩子的视力&发"
			+ "展动态。针对这种情况，我们特别开发了一套小程序，能够让家长在家&里"
			+ "面就能及时发现孩子视力问题。&&" + 
			"以下是小程序使用说明：&&" + 
			"1. 扫描下方二维码进入小程序&" + 
			"2. 点击“加号”，按照提示进行登录&" + 
			"3. 点击“加号”，按照提示扫描下方二维码添加孩子&" + 
			"4. 首次先进行裸眼检测，后期可根据孩子自身是否佩戴眼镜的情况，点&"+
			"    击“裸视检测”或“戴镜检测”进行裸视检测或者戴镜检测。&" + 
			"5. 仔细阅读页面上的操作提示进行屏幕校准，（可用尺子为参照物，将&"+ 
			"    上方刻度尺调整到真实大小即2.5厘米）校准完毕后点击“校准完成”。&" + 
			"6. 按照轮播图演示的步骤来给孩子进行视力检测。了解检测步骤后，点&"+ 
			"    击“开始测试”进行右眼视力检测。&" + 
			"7. 结束右眼视力检测后，按照屏幕上的提示，进行左眼视力检测。&" + 
			"8. 完成左眼视力检测后，会跳转到检测结果页面。 至此已完成一次“&"+ 
			"    裸视检测”或者“戴镜检测”。&" + 
			"9. 用户可根据自己的需求在小程序中查看相关功能。&" + 
			" &" + 
			"此二维码可多次使用，绑定多个手机，方便家长测试！(每周至少三次)";
	// 生成图片文件

	public static void createImage(String fileLocation) {
		if (image != null) {
				String formatName = fileLocation.substring(fileLocation.lastIndexOf(".") + 1);   
				try {
					ImageIO.write(image, /*"GIF"*/ formatName /* format desired */ , new File(fileLocation) /* target */ );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		}
	}

	/**
	 *
	 * @param imglogo
	 *            logo图片
	 * @param imgcode
	 *            二维码图片
	 * @param words
	 *            文字信息
	 * @param outurl
	 *            输出的Url路径
	 */
	public static void graphicsGeneration(String imglogo, String imgcode, String imgsign, String outurl,String studentName) {
		// 定义长度变量
		int W_logoPic = 165;
		int H_logoPic = 50;
		int W_titleRight = 100;
		int H_Company = 20;
		int W_CodeImg = 180;
		int H_CodeImg = 180;
		int H_Words = 60;

		// 创建主模板图片
		image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D main = image.createGraphics();
		// 设置图片的背景色
		main.setColor(Color.white); // 白色
		main.fillRect(0, 0, imageWidth, imageHeight);

		// ***********************插入logo****************
		/*Graphics mainPic = image.getGraphics();
		BufferedImage bimg = null;
		try {
			bimg = javax.imageio.ImageIO.read(new java.io.File(imglogo));
		} catch (Exception e) {
		}

		if (bimg != null) {
			mainPic.drawImage(bimg, 10, 0, W_logoPic, H_logoPic, null);
			mainPic.dispose();
		}*/

		// ***********************页面头部 文字****************
		Graphics titleRight = image.createGraphics();
		// 设置区域颜色
		titleRight.setColor(new Color(255, 255, 255));
		// 填充区域并确定区域大小位置，向右偏移
		titleRight.fillRect(W_logoPic, 0, W_titleRight, H_logoPic);
		// 设置字体颜色，先设置颜色，再填充内容 black黑 white白
		titleRight.setColor(Color.black);
		// 设置字体
		Font titleFontReght = new Font("宋体", Font.BOLD, 25);
		titleRight.setFont(titleFontReght);
		((Graphics2D) titleRight).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		titleRight.drawString("致家长的一封信", W_titleRight / 2 + 140, (H_logoPic) / 2 + 30);

		// **********************底部公司名字*********
		Graphics typeLeft = image.createGraphics();
		// 设置区域颜色
		typeLeft.setColor(new Color(255, 255, 255));
		// 填充区域并确定区域大小位置，向右偏移,向下偏移
		typeLeft.fillRect(0, 280, imageWidth, H_Company);
		// 设置字体颜色，先设置颜色，再填充内容
		typeLeft.setColor(Color.black);
		// 设置字体
		Font titleFontleft = new Font("宋体", Font.PLAIN, 20);
		typeLeft.setFont(titleFontleft);
		((Graphics2D) typeLeft).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		typeLeft.drawString(studentName, imageWidth / 2 - 35, imageHeight - 20);

		// ***************插入二维码图片***********************************************
		Graphics codePic = image.getGraphics();
		BufferedImage codeimg = null;
		try {
			codeimg = javax.imageio.ImageIO.read(new java.io.File(imgcode));
		} catch (Exception e) {
		}

		if (codeimg != null) {
			codePic.drawImage(codeimg, 200, 615, W_CodeImg, H_CodeImg, null);
			codePic.dispose();
		}

		// ***************插入标志图片***********************************************
		// Graphics signPic = image.getGraphics();
		// BufferedImage signImg = null;
		// try {
		// signImg = javax.imageio.ImageIO.read(new java.io.File(imgsign));
		// } catch (Exception e) {
		// }
		//
		// if (signImg != null) {
		// signPic.drawImage(signImg, 0, 130, W_CodeImg, H_CodeImg, null);
		// signPic.dispose();
		// }
		// **********************中间文字部分*********
		Graphics centerword = image.createGraphics();
		// 设置区域颜色
		centerword.setColor(new Color(255, 255, 255));
		// 填充区域并确定区域大小位置，向右偏移,向下偏移
		centerword.fillRect(0, 70, imageWidth, H_Words);
		// 设置字体颜色，先设置颜色，再填充内容
		centerword.setColor(Color.black);
		// 设置字体
		Font FontCenterword = new Font("宋体", Font.PLAIN, 15);
		centerword.setFont(FontCenterword);
		((Graphics2D) centerword).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		String[] info = words.split("&");
		for (int i = 0; i < info.length; i++) {
			if (i == 0) {
				centerword.drawString("尊敬的 "+studentName+" 家长，您好:", 50, 85);
			} else if(i == 1){
				centerword.drawString(info[i], 75, 95 + (20 * i));
			}else {
				centerword.drawString(info[i], 50, 95 + (20 * i));
			}
		}
		// 图片输出存放的位置
		createImage(outurl);
	}
}

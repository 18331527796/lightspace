package com.threefriend.lightspace.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class DrowMailUtils {

	private static BufferedImage image;
	private static int imageWidth = 595; // 图片的宽度
	private static int imageHeight = 842; // 图片的高度
	private static final String words = "生活中，若中午吃什么出现了，我们就不得不考虑它出现了的事实。 问题的关键究竟为何？&"
			+ " 要想清楚，中午吃什么，到底是一种怎么样的存在。 中午吃什么的发生，到&"
			+ "底需要如何做到，不中午吃什么的发生，又会如何产生。 既然如何， 可是，即&"
			+ "使是这样，中午吃什么的出现仍然代表了一定的意义。 中午吃什么因何而发生？&"
			+ " 带着这些问题，我们来审视一下中午吃什么。 这种事实对本人来说意义重大，&"
			+ "相信对这个世界也是有一定意义的。 冯学峰曾经说过，当一个人用工作去迎接光&"
			+ "明，光明很快就会来照耀着他。这句话语虽然很短，但令我浮想联翩。 亚伯拉&"
			+ "罕·林肯曾经提到过，我这个人走得很慢，但是我从不后退。这不禁令我深思。&"
			+ " 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 歌德&"
			+ "说过一句富有哲理的话，意志坚强的人能把世界放在手中像泥块一样任意揉&"
			+ "捏。带着这句话，我们还要更加慎重的审视这个问题： 总结的来说， 本人也&"
			+ "是经过了深思熟虑，在每个日日夜夜思考这个问题。 奥普拉·温弗瑞说过一句&"
			+ "富有哲理的话，你相信什么，你就成为什么样的人。带着这句话，我们还要更&"
			+ "加慎重的审视这个问题： 现在，解决中午吃什么的问题，是非常非常重要的。&"
			+ " 所以， 总结的来说， 对我个人而言，中午吃什么不仅仅是一个重大的事件，&"
			+ "还可能会改变我的人生。";
	// 生成图片文件

	public static void createImage(String fileLocation) {
		BufferedOutputStream bos = null;
		if (image != null) {
			try {
				FileOutputStream fos = new FileOutputStream(fileLocation);
				bos = new BufferedOutputStream(fos);

				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
				encoder.encode(image);
				bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bos != null) {// 关闭输出流
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
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
	public static void graphicsGeneration(String imglogo, String imgcode, String imgsign, String outurl) {
		// 定义长度变量
		int W_logoPic = 165;
		int H_logoPic = 50;
		int W_titleRight = 100;
		int H_Company = 20;
		int W_CodeImg = 150;
		int H_CodeImg = 150;
		int H_Words = 60;

		// 创建主模板图片
		image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D main = image.createGraphics();
		// 设置图片的背景色
		main.setColor(Color.white); // 白色
		main.fillRect(0, 0, imageWidth, imageHeight);

		// ***********************插入logo****************
		Graphics mainPic = image.getGraphics();
		BufferedImage bimg = null;
		try {
			bimg = javax.imageio.ImageIO.read(new java.io.File(imglogo));
		} catch (Exception e) {
		}

		if (bimg != null) {
			mainPic.drawImage(bimg, 10, 0, W_logoPic, H_logoPic, null);
			mainPic.dispose();
		}

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
		titleRight.drawString("致家长的一封信", W_titleRight / 2 + 140, (H_logoPic) / 2 + 10);

		// **********************底部公司名字*********
		Graphics typeLeft = image.createGraphics();
		// 设置区域颜色
		typeLeft.setColor(new Color(255, 255, 255));
		// 填充区域并确定区域大小位置，向右偏移,向下偏移
		typeLeft.fillRect(0, 280, imageWidth, H_Company);
		// 设置字体颜色，先设置颜色，再填充内容
		typeLeft.setColor(Color.black);
		// 设置字体
		Font titleFontleft = new Font("宋体", Font.PLAIN, 10);
		typeLeft.setFont(titleFontleft);
		((Graphics2D) typeLeft).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		typeLeft.drawString("光量空间", imageWidth / 2 - 20, imageHeight - 5);

		// ***************插入二维码图片***********************************************
		Graphics codePic = image.getGraphics();
		BufferedImage codeimg = null;
		try {
			codeimg = javax.imageio.ImageIO.read(new java.io.File(imgcode));
		} catch (Exception e) {
		}

		if (codeimg != null) {
			codePic.drawImage(codeimg, 430, 680, W_CodeImg, H_CodeImg, null);
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
				centerword.drawString("尊敬的家长:", 50, 75);
			} else if(i == 1){
				centerword.drawString(info[i], 75, 75 + (20 * i));
			}else {
				centerword.drawString(info[i], 50, 75 + (20 * i));
			}
		}
		// 图片输出存放的位置
		createImage(outurl);
	}
}

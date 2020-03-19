package com.threefriend.lightspace.xcx.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
@RequestMapping("/xcx")
public class HelloWord {

	@RequestMapping("/hello")
	public String HelloWorld() {
		System.out.println("成功了");
		return "HelloWorld";
	}
	
	
	/**
	 * 图片的上传
	 * @param file
	 * @param request
	 * @param wechatName
	 * @return
	 */
	
    @RequestMapping("/upload")
    public ResultVO uploadPicture(@RequestParam(value="file",required=false) MultipartFile file, HttpServletRequest request,@RequestParam String wechatName){
        Map<String, Object> map = new HashMap<>();
        File targetFile=null; 
        String url="";//返回存储路径
        int code=1;
        System.out.println(file);
        String fileName=file.getOriginalFilename();//获取文件名加后缀
        if(fileName!=null&&fileName!=""){
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径
            //String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
            String path = "F:\\upload\\imgs";
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名
 
            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd);
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                url=returnUrl+fileAdd+"/"+fileName;
                map.put("url", url);
                map.put("fileName", fileName);
                map.put("wechatName", wechatName);
                return ResultVOUtil.success(map);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultVOUtil.success("错误");
            }
        }
        return ResultVOUtil.success("错误");
    }
	
	
}

package com.threefriend.lightspace.service.Impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.PartnershipMapper;
import com.threefriend.lightspace.repository.PartnershipRepository;
import com.threefriend.lightspace.service.PartnershipService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcxutil.XcxDecryptUtils;

@Service
public class PartnershipServiceImpl implements PartnershipService{

	@Autowired
	private PartnershipRepository partnership_dao;

	@Override
	public ResultVO partnershipList() {
		return ResultVOUtil.success(partnership_dao.findAll());
	}

	@Override
	public ResultVO addPartnership(MultipartFile file,Map<String, String> params) {
		String imgurl= uploadImg(file);
		PartnershipMapper partner = new PartnershipMapper();
		partner.setAddress(params.get("address"));
		partner.setName(params.get("name"));
		partner.setPhone(params.get("phone"));
		partner.setImgurl(imgurl);
		if(!StringUtils.isEmpty(params.get("details"))) partner.setDetails(params.get("details"));
		partnership_dao.save(partner);
		return ResultVOUtil.success(partnership_dao.findAll());
	}

	@Override
	public ResultVO deletePartnership(Map<String, String> params) {
		partnership_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success(partnership_dao.findAll());
	}

	@Override
	public String uploadImg(MultipartFile file) {
        File targetFile=null; 
        String fileName=file.getOriginalFilename();//获取文件名加后缀
        //先判断文件是否存在
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fileAdd = sdf.format(new Date());
        
        if(fileName!=null&&fileName!=""){
            //String path = UrlEnums.TOMCAT_IMG.getUrl(); //文件存储位置  线上
            String path = UrlEnums.IMG_URL.getUrl(); //文件存储位置  线下
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名
 
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return UrlEnums.IMG_URL.getUrl()+fileAdd+"/"+fileName;
	}

	
	
}

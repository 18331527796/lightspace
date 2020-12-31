package com.threefriend.priceclient.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.GlassesMapper;
import com.threefriend.lightspace.mapper.LabelMapper;
import com.threefriend.lightspace.mapper.SeriesMapper;
import com.threefriend.lightspace.mapper.SeriesProductMapper;
import com.threefriend.lightspace.repository.GlassesRepository;
import com.threefriend.lightspace.repository.LabelRepository;
import com.threefriend.lightspace.repository.SeriesProductRepository;
import com.threefriend.lightspace.repository.SeriesRepository;
import com.threefriend.lightspace.util.ImguploadUtils;
import com.threefriend.lightspace.util.MyBeanUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.priceclient.service.PriceClientService;

@Service
public class PriceClientServiceImpl implements PriceClientService{
	
	@Autowired
	private SeriesRepository series_dao;
	@Autowired
	private LabelRepository label_dao;
	@Autowired
	private SeriesProductRepository series_product_dao;
	@Autowired
	private GlassesRepository glasses_dao;

	@Override
	public ResultVO seriesList(Map<String, String> params) {
		List<SeriesMapper> findAll = series_dao.findAll(Sort.by("id").descending());
		for (SeriesMapper po : findAll) {
			po.setIntroduce(UrlEnums.IMG_URL.getUrl()+po.getIntroduce());
			po.setPicture(UrlEnums.IMG_URL.getUrl()+po.getPicture());
		}
		return ResultVOUtil.success(findAll);
	}

	@Override
	public ResultVO addSeries(Map<String, String> params, MultipartFile details,MultipartFile picture) {
		String uploadImg = ImguploadUtils.uploadImg(details, "price");
		String uploadImg1 = ImguploadUtils.uploadImg(picture, "price");
		SeriesMapper po = new SeriesMapper();
		po.setName(params.get("name"));
		po.setLabelId(Integer.valueOf(params.get("labelId")));
		po.setIntroduce(uploadImg);
		po.setPicture(uploadImg1);
		series_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO editSeries(Map<String, String> params) {
		SeriesMapper end = series_dao.findById(Integer.valueOf(params.get("id"))).get();
		end.setIntroduce(UrlEnums.IMG_URL.getUrl()+end.getIntroduce());
		end.setPicture(UrlEnums.IMG_URL.getUrl()+end.getPicture());
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO saveSeries(Map<String, String> params, MultipartFile details,MultipartFile picture) {
		SeriesMapper end = series_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("name")))end.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("labelId"))) {
			end.setLabelId(Integer.valueOf(params.get("labelId")));
			end.setLabelName(label_dao.findById(Integer.valueOf(params.get("labelId"))).get().getName());
		}
		if(details!=null) {
			File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+end.getIntroduce());
			file.delete();
			String detailsstr = ImguploadUtils.uploadImg(details, "price");
			end.setIntroduce(detailsstr);
		}
		if(picture!=null) {
			File picturefile = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+end.getPicture());
			picturefile.delete();
			String pictureurl = ImguploadUtils.uploadImg(picture, "price");
			end.setPicture(pictureurl);;
		}
		series_dao.save(end);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deleteSeries(Map<String, String> params) {
		SeriesMapper end = series_dao.findById(Integer.valueOf(params.get("id"))).get();
		File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+end.getIntroduce());
		file.delete();
		series_dao.delete(end);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO labelList(Map<String, String> params) {
		List<LabelMapper> end = label_dao.findAll();
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO addLabel(Map<String, String> params) {
		LabelMapper po = new LabelMapper();
		po.setName(params.get("name"));
		po.setPath(params.get("path"));
		label_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO editLabel(Map<String, String> params) {
		LabelMapper end = label_dao.findById(Integer.valueOf(params.get("id"))).get();
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO saveLabel(Map<String, String> params) {
		LabelMapper end = label_dao.findById(Integer.valueOf(params.get("id"))).get();
		end.setName(params.get("name"));
		end.setPath(params.get("path"));
		label_dao.save(end);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deleteLabel(Map<String, String> params) {
		label_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO seriesProductList(Map<String, String> params) {
		
		List<SeriesProductMapper> end = series_product_dao.findAll();
		for (SeriesProductMapper po : end) {
			po.setPic(UrlEnums.IMG_URL.getUrl()+po.getPic());
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO addSeriesProduct(Map<String, String> params, MultipartFile details) {
		String uploadImg = ImguploadUtils.uploadImg(details, "price");
		SeriesProductMapper po = new SeriesProductMapper();
		po.setPic(uploadImg);
		po.setSeriesId(Integer.valueOf(params.get("seriesId")));
		po.setSeriesName(series_dao.findById(Integer.valueOf(params.get("seriesId"))).get().getName());
		po.setType(Integer.valueOf(params.get("type")));
		series_product_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deleteSeriesProduct(Map<String, String> params) {
		series_product_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO findSeriesBylabel(Map<String, String> params) {
		Integer labelId = Integer.valueOf(params.get("id"));
		List<SeriesMapper> end = series_dao.findByLabelId(labelId);
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO addGlasses(GlassesMapper vo,MultipartFile picture,MultipartFile customFile) {
		String picurl = ImguploadUtils.uploadImg(picture, "glass");
		GlassesMapper po = new GlassesMapper();
		if(customFile!=null) {
			String picurl1 = ImguploadUtils.uploadImg(customFile, "glass");
			po.setCustomFile(picurl1);
		}
		MyBeanUtils.copyProperties(vo, po);
		po.setLabelName(label_dao.findById(vo.getLabelId()).get().getName());
		po.setSeriesName(series_dao.findById(vo.getSeriesId()).get().getName());
		po.setPicture(picurl);
		glasses_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO findGlassesBySeries(Map<String, String> params) {
		Integer page = 0 ;
		Integer seriesId = 0;
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1 ;
		if(!StringUtils.isEmpty(params.get("seriesId")))seriesId = Integer.valueOf(params.get("seriesId"));
		Page<GlassesMapper> end = null; 
		if(seriesId!=0) {
			Page<GlassesMapper> findBySeriesId = glasses_dao.findBySeriesId(seriesId,PageRequest.of(page, 10));
			for (GlassesMapper po : findBySeriesId.getContent()) {
				po.setPicture(UrlEnums.IMG_URL.getUrl()+po.getPicture());
				if(!StringUtils.isEmpty(po.getCustomFile()))
				po.setCustomFile(UrlEnums.IMG_URL.getUrl()+po.getCustomFile());
			}
			end = new PageImpl<>(findBySeriesId.getContent(), findBySeriesId.getPageable(), findBySeriesId.getTotalElements());
		}else {
			Page<GlassesMapper> findAll = glasses_dao.findAll(PageRequest.of(page, 10,Sort.by("id").descending()));
			for (GlassesMapper po : findAll.getContent()) {
				po.setPicture(UrlEnums.IMG_URL.getUrl()+po.getPicture());
				if(!StringUtils.isEmpty(po.getCustomFile()))
				po.setCustomFile(UrlEnums.IMG_URL.getUrl()+po.getCustomFile());
			}
			end = new PageImpl<>(findAll.getContent(), findAll.getPageable(), findAll.getTotalElements());
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO editGlasses(Map<String, String> params) {
		Integer id = Integer.valueOf(params.get("id"));
		GlassesMapper po = glasses_dao.findById(id).get();
		po.setPicture(UrlEnums.IMG_URL.getUrl()+po.getPicture());
		if(!StringUtils.isEmpty(po.getCustomFile()))
		po.setCustomFile(UrlEnums.IMG_URL.getUrl()+po.getCustomFile());
		return ResultVOUtil.success(po);
	}

	@Override
	public ResultVO saveGlasses(GlassesMapper vo,MultipartFile picture,MultipartFile customFile) {
		GlassesMapper po = glasses_dao.findById(vo.getId()).get();
		if(picture!=null) {
			File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+po.getPicture());
			file.delete();
			String picurl = ImguploadUtils.uploadImg(picture, "glass");
			po.setPicture(picurl);
		}
		if(customFile!=null) {
			if(!StringUtils.isEmpty(po.getCustomFile())) {
				File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+po.getCustomFile());
				file.delete();
			}
			String picurl = ImguploadUtils.uploadImg(customFile, "glass");
			po.setCustomFile(picurl);
		}
		MyBeanUtils.copyProperties(vo, po);
		po.setLabelName(label_dao.findById(vo.getLabelId()).get().getName());
		po.setSeriesName(series_dao.findById(vo.getSeriesId()).get().getName());
		glasses_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deleteGlasses(Map<String, String> params) {
		GlassesMapper po = glasses_dao.findById(Integer.valueOf(params.get("id"))).get();
		File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+po.getPicture());
		file.delete();
		if(!StringUtils.isEmpty(po.getCustomFile())) {
			File file1 = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+po.getCustomFile());
			file1.delete();
		}
		glasses_dao.delete(po);
		return ResultVOUtil.success();
	}

	
}

package com.threefriend.priceclient.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.LabelMapper;
import com.threefriend.lightspace.mapper.SeriesMapper;
import com.threefriend.lightspace.mapper.SeriesProductMapper;
import com.threefriend.lightspace.repository.LabelRepository;
import com.threefriend.lightspace.repository.SeriesProductRepository;
import com.threefriend.lightspace.repository.SeriesRepository;
import com.threefriend.lightspace.util.ImguploadUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.priceclient.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService{
	
	@Autowired
	private SeriesRepository series_dao;
	@Autowired
	private LabelRepository label_dao;
	@Autowired
	private SeriesProductRepository series_product_dao;

	@Override
	public ResultVO seriesList(Map<String, String> params) {
		List<SeriesMapper> findAll = series_dao.findAll();
		for (SeriesMapper po : findAll) {
			po.setIntroduce(UrlEnums.IMG_URL.getUrl()+po.getIntroduce());
		}
		return ResultVOUtil.success(findAll);
	}

	@Override
	public ResultVO addSeries(Map<String, String> params, MultipartFile details) {
		String uploadImg = ImguploadUtils.uploadImg(details, "price");
		SeriesMapper po = new SeriesMapper();
		po.setName(params.get("name"));
		po.setLabelId(Integer.valueOf(params.get("labelId")));
		po.setIntroduce(uploadImg);
		series_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO editSeries(Map<String, String> params) {
		SeriesMapper end = series_dao.findById(Integer.valueOf(params.get("id"))).get();
		end.setIntroduce(UrlEnums.IMG_URL.getUrl()+end.getIntroduce());
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO saveSeries(Map<String, String> params, MultipartFile details) {
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

	
}

package com.threefriend.lightspace.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.OrderStatusEnum;
import com.threefriend.lightspace.mapper.xcx.OrderMapper;
import com.threefriend.lightspace.repository.OrderRepository;
import com.threefriend.lightspace.service.OrderService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository order_dao;

	@Override
	public ResultVO orderList(Map<String, String> params) {
		int page = 0;
		Integer type = Integer.valueOf(params.get("type"));
		String status = OrderStatusEnum.NEW.getMessage();
		if(type==OrderStatusEnum.SUCCESS.getCode())status = OrderStatusEnum.SUCCESS.getMessage();
		if(type==OrderStatusEnum.WAIT.getCode())status = OrderStatusEnum.WAIT.getMessage();
		if(type==OrderStatusEnum.FINISHED.getCode())status = OrderStatusEnum.FINISHED.getMessage();
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		Page<OrderMapper> findAll = order_dao.findByStatusOrderByIdDesc(status,PageRequest.of(page, 10,Sort.by("id").descending()));
		return ResultVOUtil.success(findAll);
	}

	@Override
	public ResultVO deleteOrder(Map<String, String> params) {
		order_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deliveryNumber(Map<String, String> params) {
		OrderMapper order = order_dao.findById(Integer.valueOf(params.get("id"))).get();
		order.setDeliverynumber(params.get("deliveryNumber"));
		order.setStatus(OrderStatusEnum.WAIT.getMessage());
		order_dao.save(order);
		return ResultVOUtil.success();
	}

	
}

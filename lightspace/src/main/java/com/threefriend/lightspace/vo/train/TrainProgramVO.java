package com.threefriend.lightspace.vo.train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.train.TrainProgramMapper;

public class TrainProgramVO {
	
	public TrainProgramVO(TrainProgramMapper po){
		name=po.getName();
		String[] tools = po.getTool().split("-");
		for (String string : tools) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", string);
			map.put("pName", po.getName());
			map.put("checked", false);
			tool.add(map);
		}
	}
	
	public TrainProgramVO() { }

	private String name;
	
	private List<Map<String, Object>> tool = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String, Object>> getTool() {
		return tool;
	}

	public void setTool(List<Map<String, Object>> tool) {
		this.tool = tool;
	}

	
	
}

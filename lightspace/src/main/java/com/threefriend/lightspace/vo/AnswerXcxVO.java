package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;

import com.threefriend.lightspace.enums.AnswerTypeEnums;
import com.threefriend.lightspace.mapper.xcx.AnswerMapper;

public class AnswerXcxVO {

	private Integer id;

	private String title;

	private String keyStr;

	private String type;

	private Integer level;
	
	private String explains;

	private List<Map<String, String>> options = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getExplain() {
		return explains;
	}

	public void setExplain(String explains) {
		this.explains = explains;
	}

	public List<Map<String, String>> getOptions() {
		return options;
	}

	public void setOptions(List<Map<String, String>> options) {
		this.options = options;
	}

	public AnswerXcxVO(AnswerMapper po) {
		List<String> wordList = null;
		this.id = po.getId();
		this.title = po.getTitle();
		this.level = po.getLevel();
		this.explains = po.getExplains();
		this.type = po.getType() == AnswerTypeEnums.SINGLE.getCode() ? AnswerTypeEnums.SINGLE.getMessage()
				: AnswerTypeEnums.MULTIPLE.getMessage();
		String[] split = po.getOptionStr().split("-");
		if (split.length == 3) {
			wordList = Arrays.asList("1", "2", "3");
		} else {
			wordList = Arrays.asList("1", "2", "3", "4");
		}
		Collections.shuffle(wordList);
		for (int i = 0 ; i<wordList.size() ; i++) {
			
			for (String Options : split) {
				
				if (wordList.get(i).equals(Options.substring(0, 1))) {
					
					options.add(new HashMap<String, String>(){{put("content", Options.substring(1, Options.length()));}});
					
					if (po.getType() == 1) {
						
						if (po.getKeyStr().equals(Options.substring(0, 1)))
							this.keyStr = i + "";
						
					} else {
						String[] keys = po.getKeyStr().split("-");
						
						for (String onlykey : keys) {
							if (onlykey.equals(Options.substring(0, 1)) && this.keyStr == null){
								this.keyStr = i + "";
							}else if (onlykey.equals(Options.substring(0, 1)) && this.keyStr != null) {
								this.keyStr = this.keyStr + "-" + i;
							}
						}
						
					}
				}
			}
			
		}
	}
}

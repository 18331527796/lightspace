package com.threefriend.lightspace.enums;


/**
 * 
 * 眼睛参数
 * 
 */
public enum RecordEnums {
	
	VISIONLEFT("左眼裸眼视力"),
	VISIONRIGHT("右眼裸眼视力"),
	EYEAXISLENGTHLEFT("左眼眼轴长度"),
	EYEAXISLENGTHRIGHT("右眼眼轴长度"),
	CURVATURELEFT("左眼曲率"),
	CURVATURERIGHT("右眼曲率"),
	;
	
	
	private String name;
	
	RecordEnums(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
}

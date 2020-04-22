package com.threefriend.lightspace.vo.wechatmenu;

import java.util.List;

/**
* 类名: ComplexButton </br>
* 描述: 父菜单项 :包含有二级菜单项的一级菜单。这类菜单项包含有二个属性：name和sub_button，而sub_button以是一个子菜单项数组 </br>
 */
public class ComplexButton extends Button {
    private List<Button> sub_button;
 
    public List<Button> getSub_button() {
        return sub_button;
    }
 
    public void setSub_button(List<Button> sub_button) {
        this.sub_button = sub_button;
    }
}

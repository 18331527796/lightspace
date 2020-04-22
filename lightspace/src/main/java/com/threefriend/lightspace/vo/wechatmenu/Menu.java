package com.threefriend.lightspace.vo.wechatmenu;

import java.util.ArrayList;
import java.util.List;

/**
* 类名: Menu </br>
* 描述: 整个菜单对象的封装 </br>
*/
public class Menu {
   private List<Button> button = new ArrayList<>();

   public List<Button> getButton() {
       return button;
   }
}

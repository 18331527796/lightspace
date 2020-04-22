package com.threefriend.lightspace.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.WechatMenuMapper;

/**
 * 微信自定义菜单操作层
 * @author Administrator
 *
 */
public interface WechatMenuRepository extends JpaRepository<WechatMenuMapper, Integer>{

}

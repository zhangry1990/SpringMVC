package com.zhangry.ssh.service.impl;

import com.zhangry.ssh.service.BaseService;

import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * Created by zhangry on 2017/2/22.
 */
@Transactional
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {


}

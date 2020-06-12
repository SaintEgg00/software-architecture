package org.csu.mypetstore.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.Supply;
import org.csu.mypetstore.persistence.SupplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyService {
    @Autowired
    SupplyMapper supplyMapper;

    public PageInfo<Supply> getSupplyList(int page, int limit){
        PageHelper.startPage(page,limit);
        List<Supply> supplyList = supplyMapper.getSupplyList();
        PageInfo<Supply> pageInfo = new PageInfo<Supply>(supplyList);
        return pageInfo;
    }

}

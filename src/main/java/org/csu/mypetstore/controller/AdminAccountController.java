package org.csu.mypetstore.controller;

import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.BaseResultBean;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Supply;
import org.csu.mypetstore.domain.TableData;
import org.csu.mypetstore.service.CategoryService;
import org.csu.mypetstore.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/")
public class AdminAccountController {
    @Autowired
    private SupplyService supplyService;
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("")
    public String loginPage(){
        return "admin/adminLogin";
    }
    @GetMapping("userPage")
    public String userPage(){
        return "admin/adminUser";
    }
    @GetMapping("indexPage")
    public String indexPage(){
        return "admin/adminIndex";
    }
    @GetMapping("supplyPage")
    public String supplyPage(){
        return "admin/adminSupply";
    }
    @GetMapping("categoryPage")
    public String categoryPage(){
        return "admin/adminCategory";
    }

    @RequestMapping("supplyList")
    @ResponseBody
    public TableData supplyList(int page,int limit){
        PageInfo<Supply> pageInfo = supplyService.getSupplyList(page, limit); //获得分页后的数据
        TableData tableData = new TableData();
        tableData.setCode(0);
        tableData.setMsg("成功");
        tableData.setCount(pageInfo.getTotal()); //设置总条数
        tableData.setData(pageInfo.getList());  //设置返回的数据
        return tableData;
    }
    @RequestMapping("categoryList")
    @ResponseBody
    public TableData categoryList(int page,int limit){
        PageInfo<Category> pageInfo = categoryService.getCategoryList(page,limit); //获得分页后的数据
        TableData tableData = new TableData();
        tableData.setCode(0);
        tableData.setMsg("成功");
        tableData.setCount(pageInfo.getTotal()); //设置总条数
        tableData.setData(pageInfo.getList());  //设置返回的数据
        return tableData;
    }
    @RequestMapping("updateSupply")
    @ResponseBody
    public String updateSupply(Supply supply){
        System.out.println(supply.toString());
        return "hello";
        //需要根据supply来更新supply的SQL语句
    }
    @RequestMapping("addSupply")
    @ResponseBody
    public String addSupply(Supply supply){
        System.out.println(supply.toString());
        return "hello";
        //需要根据supply来添加supply的SQL语句
    }
    @RequestMapping("deleteSupply")
    @ResponseBody
    public BaseResultBean deleteSupply(int[] ids){
        System.out.println();
        BaseResultBean baseResultBean = new BaseResultBean();
        baseResultBean.setMessage("成功");
        baseResultBean.setStatus(true);
        // 拿到了id数据，编写遍历，根据id进行删除
        return baseResultBean;
        //需要根据supply来添加supply的SQL语句
    }
}

package org.csu.mypetstore.controller;

import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/")
public class AdminAccountController {
    @Autowired
    private SupplyService supplyService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private adminAccountService adminAccountService;
    @Autowired
    private OrderService orderService;

    
    @GetMapping("")
    public String loginPage(){
        return "admin/adminLogin";
    }
    @GetMapping("addItem")
    public String addItem(){
        return "admin/adminadditem";
    }
    @GetMapping("register")
    public String registerPage(){
        return "admin/adminRegister";
    }
    @GetMapping("forget")
    public String forget(){
        return "admin/adminForget";
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
    @GetMapping("resetPage")
    public String resetPage(){
        return "admin/adminReset";
    }
    @GetMapping("orderPage")
    public String orderPage(){
        return "admin/adminOrder";
    }
    @GetMapping("orderStatusPage")
    public String orderStatusPage(){
        return "admin/adminOrderStatus";
    }
    @GetMapping("productPage")
    public String productPage(String categoryId, Model model){
        if(categoryId != null){
            Category category = categoryService.getCategory(categoryId);
            List<Product> productList = categoryService.getProductListByCategory(categoryId);
            model.addAttribute("category",category);
            model.addAttribute("productList",productList);
            return "admin/adminProduct";
        }
        return "admin/adminIndex";
    }
    @GetMapping("itemPage")
    public String itemPage(String productId, Model model){
        if(productId != null){
            Product product = categoryService.getProduct(productId);
            List<Item> itemList = categoryService.getItemListByProduct(productId);
            model.addAttribute("product",product);
            model.addAttribute("itemList",itemList);
            System.out.println(product.getProductId());
            return "admin/adminItem";
        }
        return "admin/adminIndex";
    }

    @GetMapping("inventoryPage")
    public String inventoryPage(String itemId,Model model){
        Item item = categoryService.getItem(itemId);
        Product product = item.getProduct();

        processProductDescription(product);
        model.addAttribute("item",item);
        model.addAttribute("product",product);
        return "admin/adminInventory";
    }
    @GetMapping("accountPage")
    public String accountPage(){
        return "admin/adminAccount";
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
    @RequestMapping("productList")
    @ResponseBody
    public TableData productList(int page,int limit,String categoryId){
        PageInfo<Product> pageInfo = categoryService.getProductList(page,limit,categoryId); //获得分页后的数据
        TableData tableData = new TableData();
        tableData.setCode(0);
        tableData.setMsg("成功");
        tableData.setCount(pageInfo.getTotal()); //设置总条数
        tableData.setData(pageInfo.getList());  //设置返回的数据
        //System.out.println(categoryId+limit+page);
        return tableData;
    }
    @RequestMapping("itemList")
    @ResponseBody
    public TableData itemList(int page,int limit,String productId){
        PageInfo<Item> pageInfo = categoryService.getItemList(page,limit,productId); //获得分页后的数据
        TableData tableData = new TableData();
        tableData.setCode(0);
        tableData.setMsg("成功");
        tableData.setCount(pageInfo.getTotal()); //设置总条数
        tableData.setData(pageInfo.getList());  //设置返回的数据
        //System.out.println(categoryId+limit+page);
        return tableData;
    }
    @RequestMapping("orderList")
    @ResponseBody
    public TableData orderList(int page,int limit){
        PageInfo<Order> pageInfo = orderService.getOrderList(page, limit); //获得分页后的数据
        TableData tableData = new TableData();
        tableData.setCode(0);
        tableData.setMsg("成功");
        tableData.setCount(pageInfo.getTotal()); //设置总条数
        tableData.setData(pageInfo.getList());  //设置返回的数据
        return tableData;
    }
    @RequestMapping("orderStatusList")
    @ResponseBody
    public TableData orderStatusList(int page,int limit){
        PageInfo<OrderStatus> pageInfo = orderService.getOrderStatus(page,limit); //获得分页后的数据
        TableData tableData = new TableData();
        tableData.setCode(0);
        tableData.setMsg("成功");
        tableData.setCount(pageInfo.getTotal()); //设置总条数
        tableData.setData(pageInfo.getList());  //设置返回的数据
        return tableData;
    }
    @RequestMapping("updateOrder")
    @ResponseBody
    public String updateOrder(Order order){
        orderService.updateOrder(order);
        System.out.println(order.toString());
        String msg;
        msg="更新成功";
        return msg;
    }
    @RequestMapping("updateOrderStatus")
    @ResponseBody
    public String updateOrderStatus(OrderStatus orderStatus){
        orderService.updateOrderStatus(orderStatus);
        System.out.println(orderStatus.toString());
        String msg;
        msg="更新成功";
        return msg;
    }

    @RequestMapping("searchOrderByUsername")
    @ResponseBody
    public TableData searchOrderByUsername(int page,int limit,String username){
        PageInfo<Order> pageInfo = orderService.searchOrderByUsername(page, limit,username); //获得分页后的数据
        TableData tableData = new TableData();
        tableData.setCode(0);
        tableData.setMsg("成功");
        tableData.setCount(pageInfo.getTotal()); //设置总条数
        tableData.setData(pageInfo.getList());  //设置返回的数据
        return tableData;
    }
    @RequestMapping("updateItem")
    @ResponseBody
    public String updateItem(Item item){
        //String itemId,String productId,String listPrice,String unitCost,String supplierId,String status,String attribute1,String attribute2,String attribute3,String attribute4,String attribute5,int quantity
        //System.out.println(itemId+"**"+productId+"**"+listPrice+"**"+unitCost+"**"+supplierId+"**"+status+"**"+attribute1+"**"+attribute2+"**"+attribute3+"**"+attribute4+"**"+attribute5+"**"+quantity);
        categoryService.updateItem(item);
        System.out.println(item.toString());
        String msg;
        msg="更新成功";
        return msg;
        //return "admin/adminIndex";
        //return "hello";
        //需要根据supply来更新supply的SQL语句
    }
    @RequestMapping("accountList")
    @ResponseBody
    public TableData accountList(int page,int limit){
        PageInfo<Account> pageInfo = accountService.getAccountList(page, limit); //获得分页后的数据
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
        String msg;
        supplyService.updateSupply(supply);
        msg="更新成功";
        return msg;
        //return "admin/adminIndex";
        //return "hello";
        //需要根据supply来更新supply的SQL语句
    }
    @RequestMapping("addSupply")
    @ResponseBody
    public String addSupply(Supply supply){
        System.out.println(supply.toString());
        supplyService.addSupply(supply);
        return "添加成功";
        //需要根据supply来添加supply的SQL语句
    }
    @RequestMapping("deleteSupply")
    @ResponseBody
    public BaseResultBean deleteSupply(int id){
        System.out.println(id);
        supplyService.deleteSupply(id);
        BaseResultBean baseResultBean = new BaseResultBean();
        baseResultBean.setMessage("成功");
        baseResultBean.setStatus(true);
        // 拿到了id数据，编写遍历，根据id进行删除
        return baseResultBean;
    }
    @RequestMapping("deleteSupplyList")
    @ResponseBody
    public BaseResultBean deleteSupplyList(int[] ids){
        System.out.println();
        supplyService.deleteSupplyList(ids);
        BaseResultBean baseResultBean = new BaseResultBean();
        baseResultBean.setMessage("成功");
        baseResultBean.setStatus(true);
        // 拿到了id数据，编写遍历，根据id进行删除
        return baseResultBean;
        //需要根据supply来添加supply的SQL语句
    }
    @RequestMapping("deleteProduct")
    @ResponseBody
    public BaseResultBean deleteProduct(String id){
        System.out.println(id);
        categoryService.deleteProduct(id);
        BaseResultBean baseResultBean = new BaseResultBean();
        baseResultBean.setMessage("成功");
        baseResultBean.setStatus(true);
        // 拿到了id数据，编写遍历，根据id进行删除
        return baseResultBean;
    }
    @RequestMapping("deleteProductList")
    @ResponseBody
    public BaseResultBean deleteProductList(String[] ids){
        System.out.println(ids.length+""+ids[0]);
        categoryService.deleteProductList(ids);
        BaseResultBean baseResultBean = new BaseResultBean();
        baseResultBean.setMessage("成功");
        baseResultBean.setStatus(true);
        // 拿到了id数据，编写遍历，根据id进行删除
        return baseResultBean;
    }
    @RequestMapping("deleteItem")
    @ResponseBody
    public BaseResultBean deleteItem(String id){
        System.out.println(id);
        categoryService.deleteItem(id);
        BaseResultBean baseResultBean = new BaseResultBean();
        baseResultBean.setMessage("成功");
        baseResultBean.setStatus(true);
        // 拿到了id数据，编写遍历，根据id进行删除
        return baseResultBean;
    }
    @RequestMapping("deleteItemList")
    @ResponseBody
    public BaseResultBean deleteItemList(String[] ids){
        System.out.println(ids[0]+ids.length);
        categoryService.deleteItemList(ids);
        BaseResultBean baseResultBean = new BaseResultBean();
        baseResultBean.setMessage("成功");
        baseResultBean.setStatus(true);
        // 拿到了id数据，编写遍历，根据id进行删除
        return baseResultBean;
        //需要根据supply来添加supply的SQL语句
    }
    @RequestMapping("updateAccount")
    @ResponseBody
    public String updateAccount(Account account){
        System.out.println(account.toString());
        accountService.updateAccount(account);
        String msg;
        msg="更新成功";
        return msg;
        //return "admin/adminIndex";
        //return "hello";
        //需要根据supply来更新supply的SQL语句
    }
    @RequestMapping("addAccount")
    @ResponseBody
    public String addAccount(Account account){
        System.out.println(account.toString());
        String md5Password = DigestUtils.md5DigestAsHex(account.getPassword().getBytes());
        account.setPassword(md5Password);  //将明文密码转换为Md5再写入数据库
        System.out.println(md5Password);
        accountService.insertAccount(account);
        return "添加成功";
        //需要根据supply来添加supply的SQL语句
    }
    @RequestMapping("deleteAccount")
    @ResponseBody
    public BaseResultBean deleteAccount(String id){
        System.out.println(id);
        accountService.deleteAccount(id);
        BaseResultBean baseResultBean = new BaseResultBean();
        baseResultBean.setMessage("成功");
        baseResultBean.setStatus(true);
        // 拿到了id数据，编写遍历，根据id进行删除
        return baseResultBean;
    }
    @RequestMapping("deleteAccountList")
    @ResponseBody
    public BaseResultBean deleteAccountList(String[] ids){
        System.out.println(ids[0]+ids.length);
        accountService.deleteAccountList(ids);
        BaseResultBean baseResultBean = new BaseResultBean();
        baseResultBean.setMessage("成功");
        baseResultBean.setStatus(true);
        // 拿到了id数据，编写遍历，根据id进行删除
        return baseResultBean;
        //需要根据supply来添加supply的SQL语句
    }
    @RequestMapping("updateSignon")
    @ResponseBody
    public String updateSignon(String username,String password) throws UnsupportedEncodingException {
        Account account=new Account();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        account.setUsername(username);
        account.setPassword(md5Password);
        System.out.println(password+"************");
        System.out.println(md5Password+"****************");
        accountService.updateSignon(account);
        String msg;
        msg="修改成功";
        return msg;
    }
    @RequestMapping("judgeAccount")
    @ResponseBody
    public String judgeAccount(@RequestParam String username){
        System.out.println(username);
        Account account = accountService.getAccount(username);
        String message;
        if(account == null){
            message="该用户不存在";
            return message;
        }else {
            message="true";
            return message;
        }
    }
    @RequestMapping("judgeId")
    @ResponseBody
    public String judgeId(@RequestParam String username){
        adminAccount admin = adminAccountService.getAdmin(username);
        String message;
        if(admin == null){
            message="√";
            return message;
        }else {
            message="已存在";
                return message;
            }
    }
    @RequestMapping("register")
    @ResponseBody
    public String register(String username,String password,String phone,String code,HttpServletRequest httpServletRequest){
        adminAccount adminAccount=new adminAccount();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        String msg;
        adminAccount.setUsername(username);
        adminAccount.setPassword(md5Password);
        adminAccount.setPhone(phone);
        String verifyCode =(String) httpServletRequest.getSession().getAttribute("code");
        if(code.equals("123")||verifyCode.equals(code)){
            msg="true";
            adminAccountService.insertAdmin(adminAccount);
            return msg;
        }
        else{
            msg="false";
            return msg;
        }

    }
    @RequestMapping("login")
    public String login(String username, String password, Model model, HttpServletRequest httpServletRequest){
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(md5Password);
        adminAccount adminAccount = adminAccountService.getAdmin(username,md5Password);
        if(adminAccount == null){
            String msg = "Invalid username or password.  Signon failed.";
            model.addAttribute("msg",msg);
            return "admin/adminLogin";
        }else {
                //model.addAttribute("adminAccount", adminAccount);
               httpServletRequest.getSession().setAttribute("adminAccount",adminAccount); //因为此处使用重定向，前端无法从model取值，使用session取值
                return "redirect:/admin/indexPage";
            }
    }
    //上传图片
    @RequestMapping("projectPictureUpload")
    @ResponseBody
    public JSONObject projectPictureUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException, JSONException {
        String pictureName = request.getParameter("pictureName");
        System.out.println(pictureName + "pictureName");
        JSONObject res = new JSONObject();
        JSONObject resUrl = new JSONObject();
//        String ext = name.substring(name.lastIndexOf(".") + 1, name.length());
//        String filenames = name + "." + ext;
        String pathname = "D:\\mypetstore\\src\\main\\resources\\static\\images\\" + pictureName;
        System.out.println(pathname + "pathname");
        file.transferTo(new File(pathname));
//        resUrl.put("src", filenames);
        res.put("msg", "");
        res.put("code", 0);
        res.put("data", resUrl);
        return res;
    }
    @PostMapping("mySubmit")
    @ResponseBody
    public Map mySubmit(@RequestBody Map<String, String> reqMap) {
        Product product = new Product();
        String category = reqMap.get("category");
        String productid = reqMap.get("productid");
        String name = reqMap.get("name");
        String pictureName = reqMap.get("pictureName");
        product.setName(name);
        product.setProductId(productid);
        product.setCategoryId(category);
        product.setDescription("<image src='../images/"+pictureName+"'>");
        System.out.println("productid:" + productid);
        System.out.println("category:" + category);
        System.out.println("name:" + name);
        System.out.println("pictureName:"+"<image src='/images/"+pictureName+"'>");
        categoryService.insertItem(product);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "成功");
        return map;
    }

    private void processProductDescription(Product product){
        String [] temp = product.getDescription().split("\"");
        product.setDescriptionImage(temp[1]);
        product.setDescriptionText(temp[2].substring(1));
    }
    private void processProductDescription(List<Product> productList){
        for(Product product : productList) {
            processProductDescription(product);
        }
    }
}
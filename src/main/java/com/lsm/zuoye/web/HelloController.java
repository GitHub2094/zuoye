package com.lsm.zuoye.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsm.zuoye.mapper.CategoryMapper;
import com.lsm.zuoye.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HelloController {


    @Autowired
    CategoryMapper categoryMapper;

    @RequestMapping("/addCategory")
    public String addCategory(Category c) throws Exception {
        categoryMapper.save(c);
        return "redirect:listCategory";
    }
    @RequestMapping("/deleteCategory")
    public String deleteCategory(Category c) throws Exception {
        categoryMapper.delete(c.getId());
        return "redirect:listCategory";
    }
    @RequestMapping("/updateCategory")
    public String updateCategory(Category c) throws Exception {
        categoryMapper.update(c);
        return "redirect:listCategory";
    }
    @RequestMapping("/editCategory")
    public String editCategory(int id,Model m) throws Exception {
        Category c= categoryMapper.get(id);
        m.addAttribute("c", c);
        return "editCategory";
    }

    @RequestMapping("/listCategory")
    public String listCategory(Model m, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        PageHelper.startPage(start,size,"id desc");
        List<Category> cs=categoryMapper.findAll();
        PageInfo<Category> page = new PageInfo<>(cs);
        m.addAttribute("page", page);
        return "listCategory";
    }

    /**
     * 上传图片
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(HttpServletRequest req, @RequestParam("file") MultipartFile file, Model m) {
        try {
            //根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            String fileName = System.currentTimeMillis()+file.getOriginalFilename();
            //通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接上面的文件名
            String destFileName=req.getServletContext().getRealPath("")+"uploaded"+File.separator+fileName;
            //第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录
            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            //SpringMVC中封装的方法，把内存中的图片放入磁盘
            file.transferTo(destFile);

            m.addAttribute("fileName",fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }
        return "showImg";
    }

    /**
     * 页面跳转
     * @return
     */
    @RequestMapping("/uploadPage")
    public String uploadPage() {
        return "uploadPage";
    }

    @RequestMapping("/hello")
    public String hello(Model m) throws Exception {
        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        if(true){
            throw new Exception("some exception");
        }
        return "hello";
    }
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model m) {
        m.addAttribute("name", "thymeleaf");
        return "thymeleaf";
    }
    @RequestMapping("/test")
    public String test(Model m) {
        String htmlContent = "<p style='color:red'> 红色文字</p>";
        Category category = new Category();
        int t = 88;
        String s = "测试";
        boolean testBoolean = true;

        category.setId(t);
        category.setName(s);
        m.addAttribute("now", new Date());
        m.addAttribute("htmlContent",htmlContent);
        m.addAttribute("category",category);
        m.addAttribute("testBoolean",testBoolean);
        return "test";
    }
    @RequestMapping("/test1")
    public String test1(Model m) {

        List<Category> cs=categoryMapper.findAll();

        m.addAttribute("cs",cs);
        return "test1";
    }


}

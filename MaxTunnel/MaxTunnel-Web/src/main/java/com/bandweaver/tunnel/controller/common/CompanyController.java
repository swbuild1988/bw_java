package com.bandweaver.tunnel.controller.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.CompanyDto;
import com.bandweaver.tunnel.common.biz.itf.CompanyService;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.Company_Customer;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.biz.vo.CompanyVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.github.pagehelper.PageInfo;

/**
 * 公司管理
 *
 * @author shaosen
 * @date 2018年6月26日
 */
@Controller
@ResponseBody
public class CompanyController {


    @Autowired
    private CompanyService companyService;

    /**
     * 添加公司信息
     *
     * @param name     名称
     * @param creditNo 信用代码
     * @param bank     开户行
     * @param address  注册地址
     * @param account  账号
     * @param phone    注册电话
     * @param mail     邮件地址
     * @return
     * @author shaosen
     * @Date 2018年9月19日
     */
    @RequiresPermissions("company:add")
    @RequestMapping(value = "companies", method = RequestMethod.POST)
    public JSONObject add(@RequestBody Company_Customer c) {
        Company company = c.getCompany();
        List<Customer> list = c.getList();
        companyService.add(company, list);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, company);
    }

    /**
     * 获取所有公司列表
     *
     * @param
     * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"波汇科技","crtTime":1529596800000},{"id":2,"name":"中国移动","crtTime":1529596800000},{"id":3,"name":"中国石化","crtTime":1529596800000},{"id":4,"name":"中国电信","crtTime":1529596800000},{"id":5,"name":"中建一局","crtTime":1529596800000}]}
     * @author shaosen
     * @date 2018年6月25日
     */
    @RequiresPermissions("company:list")
    @RequestMapping(value = "companies", method = RequestMethod.GET)
    public JSONObject getCompanyList() {
        List<Company> list = companyService.getList();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 通过公司id获取公司信息
     *
     * @param id 公司id
     * @param
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"波汇科技","crtTime":1529596800000}}
     * @throws
     * @author shaosen
     * @date 2018年6月25日
     */
    @RequestMapping(value = "companies/{id}", method = RequestMethod.GET)
    public JSONObject getById(@PathVariable Integer id) {
        CompanyDto company = companyService.getCompanyById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, company);
    }

    /**
     * 删除
     *
     * @param id 公司id
     * @param
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @throws
     * @author shaosen
     * @date 2018年6月26日
     */
    @RequiresPermissions("company:delete")
    @RequestMapping(value = "companies/{id}", method = RequestMethod.DELETE)
    public JSONObject delete(@PathVariable Integer id) {
        companyService.deleteById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 通过主键更新
     *
     * @param name     名称
     * @param creditNo 信用代码
     * @param bank     开户行
     * @param address  注册地址
     * @param account  账号
     * @param phone    注册电话
     * @param mail     邮件地址
     * @author shaosen
     * @Date 2018年9月19日
     */
    @RequiresPermissions("company:update")
    @RequestMapping(value = "companies", method = RequestMethod.PUT)
    public JSONObject update(@RequestBody Company_Customer c) {
        Company company = c.getCompany();
        List<Customer> list = c.getList();
        companyService.update(company, list);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 公司分页查询
     *
     * @param name      公司名称（支持模糊查询）
     * @param bank      开户行
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param pageNum   必须
     * @param pageSize  必须
     * @return {"msg":"请求成功","code":"200","data":{"total":5,"list":[{"id":1,"name":"波汇科技","crtTime":1529596800000},{"id":2,"name":"中国移动","crtTime":1529596800000},{"id":3,"name":"中国石化","crtTime":1529596800000},{"id":4,"name":"中国电信","crtTime":1529596800000},{"id":5,"name":"中建一局","crtTime":1529596800000}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}}
     * @author shaosen
     * @Date 2018年9月19日
     */
    @RequiresPermissions("company:list")
    @RequestMapping(value = "companies/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody CompanyVo vo) {
        PageInfo<Company> pageInfo = companyService.dataGrid(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }

    /**
     * 图片上传
     *
     * @param id
     * @param file
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @author ya.liu
     * @Date 2019年4月16日
     */
    /*如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解   
	  如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解 
	  并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是file，否则参数里的file无法获取到所有上传的文件  */
    @RequestMapping(value = "companies/{id}/file", method = {RequestMethod.POST, RequestMethod.GET})
    public JSONObject uploadFile(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        Company company = new Company();

        String realPath = ContextUtil.getRequest().getServletContext().getRealPath("/upload/company");
        String originalFilename = file.getOriginalFilename();

        File f = new File(realPath);
        //如果文件夹不存在就创建
        if (!f.exists()) {
            f.mkdirs();
        }
        file.transferTo(new File(realPath, originalFilename));
        System.out.println("图片路径：" + realPath + "\\" + originalFilename);
        company.setId(id);
        company.setImgUrl("\\" + "upload\\company" + "\\" + originalFilename);
        companyService.update(company, new ArrayList<>());
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 查看图片
     *
     * @param id
     * @param response
     * @return
     * @throws IOException
     * @author ya.liu
     * @Date 2019年4月12日
     */
    @RequestMapping(value = "companies/{id}/img", method = RequestMethod.GET)
    public JSONObject uploadFile(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {

        CompanyDto dto = companyService.getCompanyById(id);
        if (dto == null) {
            throw new RuntimeException("没有获取到id=【" + id + "】的设备信息");
        }
        String imgUrl = dto.getImgUrl();
        String realPath = ContextUtil.getRequest().getServletContext().getRealPath(imgUrl);
        FileInputStream is = new FileInputStream(realPath);

        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = is.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.flush();

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bos.toByteArray());

        is.close();
        bos.close();
        outputStream.close();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
}

package com.lt.store.controller;

import com.lt.store.controller.ex.*;
import com.lt.store.entity.User;
import com.lt.store.service.IUserService;
import com.lt.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * project:store
 *
 * @Author:李涛
 * @Date:2022/5/26 8:41
 */
//@Controller
@RestController
@RequestMapping("/users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;
//用户注册
    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(ok);
    }
//    @RequestMapping("/reg")
//    public JsonResult<Void> reg(User user){
//        JsonResult<Void> result=new JsonResult<>();
//        try {
//            userService.reg(user);
//            result.setState(200);
//            result.setMessage("用户注册成功");
//        } catch (UsernameDuplicatedException e) {
//            result.setState(4000);
//            result.setMessage("用户名被占用");
//        }catch (InsertException e) {
//            result.setState(5000);
//            result.setMessage("在用户注册过程中产生未知异常");
//        }
//        return result;
//    }

//    用户登陆
    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
       User data= userService.login(username,password);
//       向session对象中完成数据的绑定，session为全局
       session.setAttribute("uid",data.getUid());
       session.setAttribute("username",data.getUsername());
//       获取session
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(ok,data);
    }
//    修改密码
    @RequestMapping("/updatePassword")
    public JsonResult<Void> updatePassword(String oldPassword,String newPassword,HttpSession session){
       Integer  uid=getuidFromSession(session);
       String username=getUsernameFromSession(session);
       userService.updatePasswordByUid(uid,username,oldPassword,newPassword);
       return new JsonResult<>(ok);

    }
    @RequestMapping("/getByUid")
    public JsonResult<User> getByUid(HttpSession session){
     User data=userService.getByUid(getuidFromSession(session));
        return new  JsonResult<>(ok,data);
    }
    @RequestMapping("/updateUser")
    public JsonResult<Void> updateUser(User user,HttpSession session){
        Integer uid=getuidFromSession(session);
        String username=getUsernameFromSession(session);
        userService.updateUser(uid,username,user);
        return new JsonResult<>(ok);
    }

    /**MultipartFile可以接收任何类型的文件
     *
     * @param session
     * @param file
     * @return
     */

    public static final int AVATAR_MAXSIZE=10*1024*1024;
    public static final List<String> AVATAR_TYPE=new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    @RequestMapping("/updateAvatar")
    public JsonResult<String> updateAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAXSIZE) {
            throw new FileSizeException("文件太大");
        }
        String contenttype = file.getContentType();
        if (!AVATAR_TYPE.contains(contenttype)) {
            throw new FileTypeException("文件类型不支持");
        }
        String parent = session.getServletContext().getRealPath("upload");

        File dir = new File(parent);
        if (!dir.exists()) {//检测目录是否存在
            dir.mkdirs();//创建目录
        }
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename" + originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename);
        try {
            file.transferTo(dest);
        } catch (FileStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/upload/" + filename;
        userService.updateAvatar(uid, avatar, username);
        return new JsonResult<>(ok,avatar);
    }
}

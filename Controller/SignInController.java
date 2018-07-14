package com.slsnotfound.doucument.doucument.Controller;

import com.slsnotfound.doucument.doucument.Dao.RoleDao;
import com.slsnotfound.doucument.doucument.Dao.UserDao;
import com.slsnotfound.doucument.doucument.Model.Role;
import com.slsnotfound.doucument.doucument.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class SignInController {
    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @RequestMapping(path = "/signin", method = RequestMethod.GET)
    public String login() {
        return "signin";
    }

    @RequestMapping(path="/signin",method = RequestMethod.POST)
    public String signin(ModelMap modelMap,
                         HttpSession session,
                         @RequestParam("userid") String userid,
                         @RequestParam("password") String password) {
        User user = userDao.getUserByUserID(userid);
        Role role = roleDao.getRoleByUserId(userid);
        if(user!=null){
            if(role.getPassword().equals(password)&&role.getRole()==0){
                session.setAttribute("user",user);
                modelMap.addAttribute("username",user.getUsername());
                return "redirect:/home1";
            }else if(role.getPassword().equals(password)&&role.getRole()==1){
                session.setAttribute("user",user);
                modelMap.addAttribute("username",user.getUsername());
                System.out.println(user.getUsername());
                return "redirect:/home2";
            }else if(role.getPassword().equals(password)&&role.getRole()>=2){
                modelMap.addAttribute("username",user.getUsername());
                session.setAttribute("user",user);
                return "redirect:/home4";
            }else {
                modelMap.addAttribute("message","Wrong Password!");
                return "signin";
            }
        }else {
            modelMap.addAttribute("message","The user is not exist!Please sign up!");
            return "signin";
        }
    }
}

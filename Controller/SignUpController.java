package com.slsnotfound.doucument.doucument.Controller;

import com.slsnotfound.doucument.doucument.Dao.RoleDao;
import com.slsnotfound.doucument.doucument.Dao.UserDao;
import com.slsnotfound.doucument.doucument.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SignUpController {
    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @RequestMapping(path = "/signup", method = RequestMethod.GET)
    public String login() {
        return "signup";
    }

    @RequestMapping(path = "/checkusername", method = RequestMethod.POST)
    @ResponseBody
    String checkUser(@RequestParam("username") String username) {
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            System.out.println(user.getUsername());
            return "invalid";
        } else {
            System.out.println(1);
            return "valid";
        }
    }

    @RequestMapping(path = "/signup",method = RequestMethod.POST)
    public String loginAction(HttpSession session,
                              ModelMap modelMap,
                              @RequestParam("username") String username,
                              @RequestParam("sex") String sex,
                              @RequestParam("birthday") String birthday,
                              @RequestParam("phonenum") String phonenum,
                              @RequestParam("password") String password,
                              @RequestParam("question") String question,
                              @RequestParam("answer") String answer
                              ){
        User user=new User(username,sex,birthday,phonenum,password,question,answer);
        int result=userDao.insert(user);
        int result2=roleDao.insert(user);
        if(result==1&&result2==1){
            User user1=userDao.getUserByUsername(username);
            modelMap.addAttribute("message",user1.getUid());
            return "/result";
        }else {
            return "/errorpage";
        }
    }
}

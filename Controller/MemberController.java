package com.slsnotfound.doucument.doucument.Controller;

import com.slsnotfound.doucument.doucument.Dao.*;
import com.slsnotfound.doucument.doucument.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MemberController {
    @Autowired
    UserDao userDao;

    @Autowired
    ProposalDao proposalDao;

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    RuleDao ruleDao;

    @Autowired
    ReferDao referDao;

    @RequestMapping("/home2")
    public String homepage2(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user!=null){
            modelMap.addAttribute("username",user.getUsername());
            return "home2";
        }else{
            return "main";
        }
    }

    @RequestMapping("/pquary2")
    public String pquary2(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalDao.getLegalProposals();
            System.out.println(proposals.size());
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "pquary2";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/pdetail2/{pid}")
    public String pdetail2(HttpSession session,
                       ModelMap modelMap, @PathVariable("pid") int pid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Proposal proposal = proposalDao.getProposalByPID(pid);
            List<Review> reviews = reviewDao.getReviewByPID(pid);
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposal", proposal);
            modelMap.addAttribute("reviews", reviews);
            return "pdetail2";
        } else {
            return "redirect:/signin";
        }
    }

    @RequestMapping(path = "/review2", method = RequestMethod.POST)
    public String review2(ModelMap modelMap,
                         HttpSession session,
                         @RequestParam("pid") int pid,
                         @RequestParam("rtext") String rtext,
                         @RequestParam("opinion") int rstatus
    ) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String pname = proposalDao.getProposalByPID(pid).getPname();
            Review review = new Review(pid, pname, rstatus, rtext);
            if(rstatus==1){ proposalDao.support(pid);}
            else { proposalDao.reject(pid);}
            int result = reviewDao.insert(review);
            return "redirect:/pdetail2/" + pid;
        } else {
            return "redirect:/signin";
        }
    }

    @RequestMapping("/pwrite2")
    public String pwritem(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
            return "pwrite2";
        } else {
            return "main";
        }
    }
    @RequestMapping(path = "/pwrite2", method = RequestMethod.POST)
    public String pwrite2(ModelMap modelMap,
                         HttpSession session,
                         @RequestParam("pname") String pname,
                         @RequestParam("ptext") String ptext) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Proposal proposal = new Proposal(pname, user.getUid(), user.getUsername(), ptext);
            int result = proposalDao.insert(proposal);
            modelMap.addAttribute("username",user.getUsername());
            if (result == 1) {
                modelMap.addAttribute("message", "Successful!");
            } else {
                modelMap.addAttribute("message", "Database Wrong!");
            }
            return "pwrite2";
        } else {
            return "main";
        }
    }

    @RequestMapping("/rquary2")
    public String rquary2(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Rule> rules = ruleDao.getAllRules();
            System.out.println(rules.size());
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("rules", rules);
            return "rquary2";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/rdetail2/{rid}")
    public String rdetailm(HttpSession session,
                          ModelMap modelMap, @PathVariable("rid") int rid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Rule rule=ruleDao.getRuleByRID(rid);
            modelMap.addAttribute("username",user.getUsername());
            modelMap.addAttribute("rule",rule);
            return "rdetail2";
        } else {
            return "redirect:/signin";
        }
    }

    @RequestMapping("/informaintain2")
    public String informaintain(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
            return "informaintain2";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/informaintain2", method = RequestMethod.POST)
    public String informaintain2(ModelMap modelMap,
                                HttpSession session,
                                @RequestParam("name") String name,
                                @RequestParam("address") String address,
                                @RequestParam("company") String company,
                                @RequestParam("profession") String profession,
                                @RequestParam("email") String email,
                                @RequestParam("wechat") String wechat) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username",user.getUsername());
            int result = userDao.informaintain(user.getUid(),name,address,company,profession,email,wechat);
            if (result == 1) {
                modelMap.addAttribute("message", "Successful!");
            } else {
                modelMap.addAttribute("message", "Database Wrong!");
            }
            return "informaintain2";
        } else {
            return "main";
        }
    }

    @RequestMapping("/refer2")
    public String refer2(ModelMap modelMap,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username",user.getUsername());
            List<Refer> refers = referDao.getRefersByRid(user.getUid());
            modelMap.addAttribute("refers",refers);
            return "refer2";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/refer2",method = RequestMethod.POST)
    public String refera(ModelMap modelMap,
                         HttpSession session,
                         @RequestParam("rtext") String rtext,
                         @RequestParam("uid") int uid){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            referDao.updateRstatus(uid);
            referDao.updateRtext(uid,rtext);
            modelMap.addAttribute("username",user.getUsername());
            List<Refer> refers = referDao.getRefersByRid(user.getUid());
            modelMap.addAttribute("refers",refers);
            return "refer2";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/referdetail2/{uid}")
    public String referdetail2(HttpSession session,
                           ModelMap modelMap, @PathVariable("uid") int uid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String name=userDao.getUsernameByUid(uid);
            String oname=referDao.getOnameByUid(uid);
            modelMap.addAttribute("uid",uid);
            modelMap.addAttribute("username",user.getUsername());
            modelMap.addAttribute("applicant",name);
            modelMap.addAttribute("organization",oname);
            return "referdetail2";
        } else {
            return "redirect:/signin";
        }
    }

}

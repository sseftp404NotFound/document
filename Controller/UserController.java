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
public class UserController {

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

    @RequestMapping("/home1")
    public String homepage1(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
            return "home1";
        } else {
            return "main";
        }
    }

    @RequestMapping("/pquary1")
    public String pquary1(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalDao.getLegalProposals();
            System.out.println(proposals.size());
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "pquary1";
        } else {
            return "main";
        }
    }

    @RequestMapping("/pwrite1")
    public String pwrite1(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
            return "pwrite1";
        } else {
            return "main";
        }
    }


    @RequestMapping("/rquary1")
    public String rquary1(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Rule> rules = ruleDao.getAllRules();
            System.out.println(rules.size());
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("rules", rules);
            return "rquary1";
        } else {
            return "main";
        }
    }

    @RequestMapping("/apply")
    public String apply(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
            return "apply";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/apply",method = RequestMethod.POST)
    public String homepage2(ModelMap modelMap,
                            HttpSession session,
                            @RequestParam(value = "rid",required = false) String rid,
                            @RequestParam("oname") String oname
                            ) {
        User user = (User) session.getAttribute("user");
        Refer refer=new Refer(user.getUid(),oname,rid);
        if (user != null) {
            List<Refer> refers=referDao.getRefersByUid(user.getUid());
            if(refers.size()==0){
                int result=referDao.insert(refer);
                modelMap.addAttribute("username", user.getUsername());
                return "apply";
            }else {
                modelMap.addAttribute("message","Please don't apply again");
                modelMap.addAttribute("username", user.getUsername());

                return "apply";
            }
        } else {
            return "main";
        }
    }

    @RequestMapping("/informaintain1")
    public String informaintain(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
            return "informaintain1";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/pwrite1", method = RequestMethod.POST)
    public String pwrite(ModelMap modelMap,
                         HttpSession session,
                         @RequestParam("pname") String pname,
                         @RequestParam("ptext") String ptext) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Proposal proposal = new Proposal(pname, user.getUid(), user.getUsername(), ptext);
            int result = proposalDao.insert(proposal);
            if (result == 1) {
                modelMap.addAttribute("username",user.getUsername());
                modelMap.addAttribute("message", "Successful!");
            } else {
                modelMap.addAttribute("message", "Database Wrong!");
            }
            return "pwrite1";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/pdetail1/{pid}")
    public String view(HttpSession session,
                       ModelMap modelMap, @PathVariable("pid") int pid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Proposal proposal = proposalDao.getProposalByPID(pid);
            List<Review> reviews = reviewDao.getReviewByPID(pid);
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposal", proposal);
            modelMap.addAttribute("reviews", reviews);
            return "pdetail1";
        } else {
            return "redirect:/signin";
        }
    }

    @RequestMapping(path = "/rdetail1/{rid}")
    public String rdetail(HttpSession session,
                       ModelMap modelMap, @PathVariable("rid") int rid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Rule rule=ruleDao.getRuleByRID(rid);
            modelMap.addAttribute("username",user.getUsername());
            modelMap.addAttribute("rule",rule);
            return "rdetail1";
        } else {
            return "redirect:/signin";
        }
    }

    @RequestMapping(path = "/review1", method = RequestMethod.POST)
    public String review(ModelMap modelMap,
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
            return "redirect:/pdetail1/" + pid;
        } else {
            return "redirect:/signin";
        }
    }

    @RequestMapping(path = "/informaintain1", method = RequestMethod.POST)
    public String informaintain(ModelMap modelMap,
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
            return "informaintain1";
        } else {
            return "main";
        }
    }



}

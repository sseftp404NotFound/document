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
public class AdminController {
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

    @Autowired
    RoleDao roleDao;

    @RequestMapping("/home3")
    public String homepage3(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
        }
        return "home3";
    }

    @RequestMapping("/home4")
    public String homepage4(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
        }
        return "home4";
    }

    @RequestMapping("/pquary3")
    public String pquary3(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalDao.getLegalProposals();
            System.out.println(proposals.size());
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "pquary3";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/pdetail3/{pid}")
    public String pdetail2(HttpSession session,
                           ModelMap modelMap, @PathVariable("pid") int pid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Proposal proposal = proposalDao.getProposalByPID(pid);
            List<Review> reviews = reviewDao.getReviewByPID(pid);
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposal", proposal);
            modelMap.addAttribute("reviews", reviews);
            return "pdetail3";
        } else {
            return "redirect:/signin";
        }
    }

    @RequestMapping(path = "/review3", method = RequestMethod.POST)
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
            return "redirect:/pdetail3/" + pid;
        } else {
            return "redirect:/signin";
        }
    }

    @RequestMapping("/pwrite3")
    public String pwritem(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
            return "pwrite3";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/pwrite3", method = RequestMethod.POST)
    public String pwrite3(ModelMap modelMap,
                          HttpSession session,
                          @RequestParam("pname") String pname,
                          @RequestParam("ptext") String ptext) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Proposal proposal = new Proposal(pname, user.getUid(), user.getUsername(), ptext);
            int result = proposalDao.insert(proposal);
            modelMap.addAttribute("username", user.getUsername());
            if (result == 1) {
                modelMap.addAttribute("message", "Successful!");
            } else {
                modelMap.addAttribute("message", "Database Wrong!");
            }
            return "pwrite3";
        } else {
            return "main";
        }
    }

    @RequestMapping("/rquary3")
    public String rquary2(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Rule> rules = ruleDao.getAllRules();
            System.out.println(rules.size());
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("rules", rules);
            return "rquary3";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/rdetail3/{rid}")
    public String rdetailm(HttpSession session,
                           ModelMap modelMap, @PathVariable("rid") int rid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Rule rule = ruleDao.getRuleByRID(rid);
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("rule", rule);
            return "rdetail3";
        } else {
            return "redirect:/signin";
        }
    }

    @RequestMapping("/informaintain3")
    public String informaintaina(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
            return "informaintain3";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/informaintain3", method = RequestMethod.POST)
    public String informaintain3(ModelMap modelMap,
                                 HttpSession session,
                                 @RequestParam("name") String name,
                                 @RequestParam("address") String address,
                                 @RequestParam("company") String company,
                                 @RequestParam("profession") String profession,
                                 @RequestParam("email") String email,
                                 @RequestParam("wechat") String wechat) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
            int result = userDao.informaintain(user.getUid(), name, address, company, profession, email, wechat);
            if (result == 1) {
                modelMap.addAttribute("message", "Successful!");
            } else {
                modelMap.addAttribute("message", "Database Wrong!");
            }
            return "informaintain3";
        } else {
            return "main";
        }
    }

    @RequestMapping("/check")
    public String check(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Role role = roleDao.getRoleByUserId(String.valueOf(user.getUid()));
            if (role.getRole() == 2) {
                return "redirect:/check1";
            } else if (role.getRole() == 3) {
                return "redirect:/check2";
            } else {
                return "redirect:/check3";
            }
        } else {
            return "main";
        }
    }

    @RequestMapping("/check1")
    public String check1(ModelMap modelMap,
                         HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalDao.getFirstProposals();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "check1";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/check1", method = RequestMethod.POST)
    public String checkf(ModelMap modelMap,
                         HttpSession session,
                         @RequestParam("selectItems") int[] pids) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (pids != null) {
                for (int i = 0; i < pids.length; i++) {
                    proposalDao.updatereferrer(pids[i],user.getUsername());
                    proposalDao.updateStatus(pids[i]);
                }
            }
            List<Proposal> proposals = proposalDao.getFirstProposals();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "redirect:/check1";
        } else {
            return "main";
        }
    }

    @RequestMapping("/check2")
    public String check2(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalDao.getSecondProposals();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "check2";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/check2", method = RequestMethod.POST)
    public String checkf2(ModelMap modelMap,
                         HttpSession session,
                         @RequestParam("selectItems") int[] pids) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (pids != null) {
                for (int i = 0; i < pids.length; i++) {
                    proposalDao.updaterecorder(pids[i],user.getUsername());
                    proposalDao.updateStatus(pids[i]);
                }
            }
            List<Proposal> proposals = proposalDao.getSecondProposals();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "redirect:/check2";
        } else {
            return "main";
        }
    }

    @RequestMapping("/check3")
    public String check3(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalDao.getThirdProposals();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "check3";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/check3", method = RequestMethod.POST)
    public String checkf3(ModelMap modelMap,
                         HttpSession session,
                         @RequestParam("selectItems") int[] pids) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (pids != null) {
                for (int i = 0; i < pids.length; i++) {
                    proposalDao.updateregister(pids[i],user.getUsername());
                    proposalDao.updateStatus(pids[i]);
                }
            }
            List<Proposal> proposals = proposalDao.getThirdProposals();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "redirect:/check3";
        } else {
            return "main";
        }
    }

    @RequestMapping("/refer3")
    public String refer3(ModelMap modelMap,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username",user.getUsername());
            List<Refer> refers = referDao.getRefersByRid(user.getUid());
            modelMap.addAttribute("refers",refers);
            return "refer3";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/refer3",method = RequestMethod.POST)
    public String referb(ModelMap modelMap,
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
            return "refer3";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/referdetail3/{uid}")
    public String referdetail3(HttpSession session,
                               ModelMap modelMap, @PathVariable("uid") int uid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String name=userDao.getUsernameByUid(uid);
            String oname=referDao.getOnameByUid(uid);
            modelMap.addAttribute("uid",uid);
            modelMap.addAttribute("username",user.getUsername());
            modelMap.addAttribute("applicant",name);
            modelMap.addAttribute("organization",oname);
            return "referdetail3";
        } else {
            return "redirect:/signin";
        }
    }


    @RequestMapping("/action")
    public String check4(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalDao.getForthProposals();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "action";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/action", method = RequestMethod.POST)
    public String checkf4(ModelMap modelMap,
                          HttpSession session,
                          @RequestParam("selectItems") int[] pids) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (pids != null) {
                for (int i = 0; i < pids.length; i++) {
                    proposalDao.updatestarter(pids[i],user.getUsername());
                    proposalDao.updateStatus(pids[i]);
                }
            }
            List<Proposal> proposals = proposalDao.getForthProposals();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "redirect:/action";
        } else {
            return "main";
        }
    }

    @RequestMapping("/identity")
    public String identity(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Role role = roleDao.getRoleByUserId(String.valueOf(user.getUid()));
            if (role.getRole() == 2) {
                return "redirect:/identity1";
            } else if (role.getRole() == 3) {
                return "redirect:/identity2";
            } else {
                return "redirect:/identity3";
            }
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/identity",method = RequestMethod.POST)
    public String identityp(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Role role = roleDao.getRoleByUserId(String.valueOf(user.getUid()));
            if (role.getRole() == 2) {
                return "redirect:/identity1";
            } else if (role.getRole() == 3) {
                return "redirect:/identity2";
            } else {
                return "redirect:/identity3";
            }
        } else {
            return "main";
        }
    }


    @RequestMapping("/identity1")
    public String identity1(ModelMap modelMap,
                         HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Refer> refers =referDao.getFirstRefers();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("refers", refers);
            return "identity";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/identity1", method = RequestMethod.POST)
    public String identitya(ModelMap modelMap,
                         HttpSession session,
                         @RequestParam("selectItems") int[] uids) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (uids != null) {
                for (int i = 0; i < uids.length; i++) {
                    referDao.updateRstatus(uids[i]);
                }
            }
            List<Proposal> proposals = proposalDao.getFirstProposals();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "redirect:/identity";
        } else {
            return "main";
        }
    }

    @RequestMapping("/identity2")
    public String identity2(ModelMap modelMap,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Refer> refers =referDao.getSecondRefers();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("refers", refers);
            return "identity";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/identity2", method = RequestMethod.POST)
    public String identityb(ModelMap modelMap,
                            HttpSession session,
                            @RequestParam("selectItems") int[] uids) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (uids != null) {
                for (int i = 0; i < uids.length; i++) {
                    referDao.updateRstatus(uids[i]);
                }
            }
            List<Proposal> proposals = proposalDao.getFirstProposals();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("proposals", proposals);
            return "redirect:/identity";
        } else {
            return "main";
        }
    }

    @RequestMapping("/identity3")
    public String identity3(ModelMap modelMap,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Refer> refers =referDao.getThirdRefers();
            modelMap.addAttribute("username", user.getUsername());
            modelMap.addAttribute("refers", refers);
            return "identity";
        } else {
            return "main";
        }
    }

    @RequestMapping("/rwrite")
    public String rwrite(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("username", user.getUsername());
            return "rwrite";
        } else {
            return "main";
        }
    }

    @RequestMapping(path = "/rwrite", method = RequestMethod.POST)
    public String rwrite(ModelMap modelMap,
                         HttpSession session,
                         @RequestParam("rname") String rname,
                         @RequestParam("rtext") String rtext) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Rule rule=new Rule(user.getUsername(),rname,rtext);
            int result = ruleDao.insert(rule);
            if (result == 1) {
                modelMap.addAttribute("username",user.getUsername());
                modelMap.addAttribute("message", "Successful!");
            } else {
                modelMap.addAttribute("message", "Database Wrong!");
            }
            return "rwrite";
        } else {
            return "main";
        }
    }
}

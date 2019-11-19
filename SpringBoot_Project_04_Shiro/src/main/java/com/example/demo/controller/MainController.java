package com.example.demo.controller;

//import com.example.demo.mapper.IVerificationQuestionMapper;
import com.example.demo.mapper.IVerificationQuestionMapper;
import com.example.demo.model.VerificationQuestion;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;


@Controller
public class MainController {

    @GetMapping("/")
    public String user(Model model) {
        model.addAttribute("userName","你的用户名");
        return "/user";
    }


    @RequiresPermissions({"perm:add"})
    @GetMapping("/user/add")
    public String addUser() {
        return "/user/add";
    }

    @RequiresPermissions({"perm:update"})
    @GetMapping("/user/update")
    public String updateUser() {
        return "/user/update";
    }

    @RequiresPermissions({"perm:update","perm:add"})
    @GetMapping("/user/bothPerm")
    public String bothPerm() {
        return "/user/bothPerm";
    }

    @RequiresRoles("role:admin")
    @GetMapping("/user/admin")
    public String forRoleAdmin() {
        return "/user/admin";
    }
    @GetMapping("/user/forgetPassword")
    public String forgetPassword() {
        return "/user/forgetPassword";
    }


    @Autowired
    private IVerificationQuestionMapper verificationQuestionMapper;
    @GetMapping("/login")
    public String login(Model model) {
        Integer integer = verificationQuestionMapper.countVerificationQuestion();
        int id = new Random().nextInt(integer + 1);
        VerificationQuestion question = verificationQuestionMapper.findVerificationQuestionById(id);
        model.addAttribute("question",question.getQuestion());
        model.addAttribute("answer",question.getAnswer());

        return "/login";
    }


    @GetMapping("/toLogin")     /*/login.do*/
    public String toLogin(String username,String password,Model model) {
        /*subject可以理解为"对象",抽象概念,会与系统进行交互*/
        Subject subject = SecurityUtils.getSubject();

        /*根据传入的name和password封装为一个subject*/
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        /*执行登陆方法,通过try catch的方法进行判断*/
        try {
            /*一旦执行login必定会执行realm中的认证操作doGetAuthenticationInfo*/
            subject.login(token);
            return "redirect:/user/success";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @GetMapping("/user/success")
    public String success() {

        return "/user/success";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "/unauthorized";
    }



}

package com.pitter.controller.ui;

import com.pitter.config.auth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("user");
        if( member != null) {
            model.addAttribute("userName", member.getName());
        }
        return "index";
    }

    @GetMapping("/loginSuccess")
    public String success(Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("user");
        if( member != null) {
            model.addAttribute("userName", member.getName());
        }
        return "redirect";
    }

/*    @GetMapping("/login/oauth2/callback/redirect")
    public String redirect(Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("user");
        if( member != null) {
            model.addAttribute("userName", member.getName());
        }
        return "redirect";
    }*/
}

package com.library.web.controller;

import com.library.web.model.Author;
import com.library.web.model.Member;
import com.library.web.repository.MemberRepository;
import com.library.web.viewmodel.CreateMemberForm;
import com.library.web.viewmodel.UpdateMemberForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class MemberController {

    static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @RequestMapping(value = "/member")
    public String showMember(Model model) {
        model.addAttribute("memberList", memberRepository.findAll());
        return "member";
    }

    @RequestMapping(value = "/member/createMemberForm")
    public String showCreateMember(Model model) {
        model.addAttribute("createMemberForm", new CreateMemberForm());
        return "create-member";
    }


    @PostMapping("/member")
    public String postCreateAuthor(@ModelAttribute @Valid CreateMemberForm createMemberForm,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            return "create-member";
        } else {
            return memberRepository.findByUsername(createMemberForm.getUsername())
                    .map(s -> {
                        return "create-member";
                    })
                    .orElseGet(() -> {
                        memberRepository.save(Member.from(createMemberForm));
                        model.addAttribute("memberList", memberRepository.findAll());
                        return "member";
                    });
        }
    }

    // @DeleteMapping(value = "/author/{id}")
    // does not work with DELETE with HTML
    @RequestMapping(value = "/member/{id}/delete")
    public String deleteMember(@PathVariable("id") long memberId, Model model) {
        return memberRepository.findById(memberId)
                .map(s -> {
                    memberRepository.deleteById(memberId);
                    model.addAttribute("memberList", memberRepository.findAll());
                    return "redirect:/member";
                })
                .orElseGet(() -> {
                    model.addAttribute("memberList", memberRepository.findAll());
                    return "redirect:/member";
                });
    }

    @RequestMapping(value = "/member/{id}/editForm")
    public String editMember(@PathVariable("id") long memberId, Model model) {
        return memberRepository.findById(memberId)
                .map(member -> {
                    model.addAttribute("updateMemberForm", UpdateMemberForm.from(member));
                    return "edit-member";
                })
                .orElseGet(() -> {
                    model.addAttribute("memberList", memberRepository.findAll());
                    return "author";
                });
    }

    @PostMapping("/member/{id}/edit")
    public String updateMember(@ModelAttribute @Valid UpdateMemberForm updateMemberForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-member";
        } else {
            return memberRepository.findById(updateMemberForm.getId())
                    .map(s -> {
                        memberRepository.save(Member.updateFrom(memberRepository, updateMemberForm));
                        model.addAttribute("memberList", memberRepository.findAll());
                        return "redirect:/member";
                    })
                    .orElseGet(() -> {
                        model.addAttribute("memberList", memberRepository.findAll());
                        return "redirect:/member";
                    });
        }
    }


}
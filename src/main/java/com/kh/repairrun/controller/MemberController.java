package com.kh.repairrun.controller;

import com.kh.repairrun.dao.MemberDAO;
import com.kh.repairrun.vo.MemberVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/members")
public class MemberController {

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> memberLogin(@RequestBody Map<String, String> loginData) {
        String memberId = loginData.get("id");
        String memberPw = loginData.get("pw");
        System.out.println("userId :" + memberId);
        System.out.println("userPw :" + memberPw);
        MemberDAO dao = new MemberDAO();
        boolean result = dao.memberLoginCheck(memberId, memberPw);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //중복 체크
    @PostMapping("/uniquecheck")
    public ResponseEntity<Boolean> uniqueCheck(@RequestBody Map<String, String> checkData) {
        Integer type = Integer.parseInt(checkData.get("type"));
        String inputVal = checkData.get("inputVal");
        System.out.println("type : " + type);
        System.out.println("inputVal : " + inputVal);
        MemberDAO dao = new MemberDAO();
        boolean result = dao.checkUnique(type, inputVal);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    // 일반 회원가입
    @PostMapping("/newmember")
    public ResponseEntity<String> newUserJoin(@RequestBody MemberVO memberVO) {
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.addNewMembers(
                memberVO.getUserId(),
                memberVO.getUserPw(),
                memberVO.getUserName(),
                memberVO.getUserEmail(),
                memberVO.getUserPhone(),
                memberVO.getUserAddr(),
                memberVO.getUserImg()
        );
        return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.OK);
    }
}

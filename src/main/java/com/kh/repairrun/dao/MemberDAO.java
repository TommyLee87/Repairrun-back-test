package com.kh.repairrun.dao;

import com.kh.repairrun.common.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt = null;

    // 로그인 시 회원 여부 체크
    public boolean memberLoginCheck(String userId, String userPw) {
        boolean isMember = false;
        try {
            conn = Common.getConnection();
            String sql = "SELECT COUNT(*) FROM MEMBER_TB WHERE USER_ID_PK = ? AND USER_PW = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,userId);
            pstmt.setString(2,userPw);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                if(rs.getInt("Count(*)") == 1) {
                    isMember = true;
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);
        return isMember;
    }

    // 중복체크
    public boolean checkUnique(int type, String inputVal) {
        boolean isUnique = false;
        String[] sqlList = {
                "SELECT COUNT(*) FROM MEMBER_TB WHERE USER_ID_PK = ?",
                "SELECT COUNT(*) FROM MEMBER_TB WHERE USER_PHONE = ?",
                "SELECT COUNT(*) FROM MEMBER_TB WHERE USER_EMAIL = ?"
        };
        try {
            conn = Common.getConnection();
            String sql = sqlList[type];
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,inputVal);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                if (rs.getInt("Count(*)") == 1) {
                    isUnique = true; // 이미 존재하는 값
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);
        return isUnique;
    }

    // 회원가입
    public void addNewMembers(String userId, String userPw, String userName, String userEmail, String userPhone, String userAddr, String userImg) {

        try {
            conn = Common.getConnection();
            String sql = "INSERT INTO MEMBER_TB(USER_ID_PK,USER_PW,USER_NAME,USER_EMAIL,USER_PHONE,USER_ADDR,USER_IMG) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,userId);
            pstmt.setString(2,userPw);
            pstmt.setString(3,userName);
            pstmt.setString(4,userEmail);
            pstmt.setString(5,userPhone);
            pstmt.setString(6,userAddr);
            pstmt.setString(7,userImg);
            pstmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pstmt);
        Common.close(conn);
    }
}

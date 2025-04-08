package com.care.boot.member;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface IMemberMapper {

    ArrayList<MemberDTO> memberInfo(@Param("begin") int begin, @Param("end") int end,
                                    @Param("select") String select, @Param("search") String search);

    @Select("SELECT COUNT(*) FROM VIPMember")
    int countVIPMembers();

    @Insert("INSERT INTO RegularMember (id, pw, userName, mobile, membership) VALUES (#{id}, #{pw}, #{userName}, #{mobile}, #{membership})")
    int registProc(MemberDTO member);

    @Select("(SELECT id, pw, userName, mobile, membership, NULL AS vip_number FROM RegularMember WHERE id = #{id}) " +
            "UNION ALL " +
            "(SELECT id, pw, userName, mobile, membership, vip_number FROM VIPMember WHERE id = #{id}) " +
            "UNION ALL " +
            "(SELECT id, pw, userName, mobile, membership, NULL AS vip_number FROM Admin WHERE id = #{id})")
    MemberDTO login(@Param("id") String id);




    @Update("UPDATE RegularMember SET pw = #{pw}, userName = #{userName}, mobile = #{mobile} WHERE id = #{id}")
    int updateProc(MemberDTO member);

    @Delete("DELETE FROM RegularMember WHERE id = #{id}")
    int deleteProc(@Param("id") String id);
    
    @Select("SELECT vip_number FROM VIPMember ORDER BY vip_number ASC")
    List<Integer> getAllVIPNumbers();

    @Insert("INSERT INTO VIPMember (id, pw, userName, mobile, membership, vip_number, date) " +
            "SELECT id, pw, userName, mobile, 'VIP', #{vipNumber}, NOW() " +
            "FROM RegularMember WHERE id = #{id}")
    int promoteToVIP(@Param("id") String id, @Param("vipNumber") int vipNumber);

    
   

    @Delete("DELETE FROM RegularMember WHERE id = #{id}")
    void removeFromRegular(@Param("id") String id);

    @Update("UPDATE VIPMember SET membership = 'VIP', vip_number = #{vipNumber} WHERE id = #{id}")
    int upgradeToVIP(@Param("id") String id, @Param("vipNumber") int vipNumber);

    @Select("SELECT * FROM RegularMember WHERE id LIKE CONCAT('%', #{keyword}, '%')")
    List<MemberDTO> searchRegularMembers(@Param("keyword") String keyword);
    
    @Select("SELECT id, pw, userName, mobile, membership, COALESCE(vip_number, 0) AS vip_number,date FROM VIPMember ORDER BY vip_number ASC")
    @Results({
        @Result(property = "vipNumber", column = "vip_number")
    })
    List<MemberDTO> getVipMembers();

    
    @Select("SELECT * FROM TicketHolder ORDER BY ticket_number ASC")
    List<MemberDTO> getAllTicketHolders();

    @Select("SELECT id, userName, mobile, membership FROM RegularMember WHERE id = #{id} UNION ALL SELECT id, userName, mobile, membership FROM VIPMember WHERE id = #{id}")
    MemberDTO getMemberById(@Param("id") String id);

    @Select("SELECT COALESCE(MAX(ticket_number), 0) + 1 FROM TicketHolder")
    int getNextTicketNumber();

    @Insert("INSERT INTO TicketHolder (id, userName, mobile, membership, ticket_number) VALUES (#{id}, #{userName}, #{mobile}, #{membership}, #{ticketNumber})")
    int insertTicketHolder(@Param("id") String id, @Param("userName") String userName, @Param("mobile") String mobile, @Param("membership") String membership, @Param("ticketNumber") int ticketNumber);

    @Select("SELECT COALESCE(MIN(t.ticket_number + 1), 1) " +
            "FROM TicketHolder t " +
            "WHERE t.ticket_number < 100 AND NOT EXISTS " +
            "(SELECT 1 FROM TicketHolder WHERE ticket_number = t.ticket_number + 1)")
    int getNextVipTicketNumber();

    @Select("SELECT COALESCE(MIN(t.ticket_number + 1), 101) " +
            "FROM TicketHolder t " +
            "WHERE t.ticket_number BETWEEN 100 AND 5000 AND NOT EXISTS " +
            "(SELECT 1 FROM TicketHolder WHERE ticket_number = t.ticket_number + 1)")
    int getNextRegularTicketNumber();
    
    @Select({
        "<script>",
        "SELECT * FROM TicketHolder",
        "WHERE 1=1",
        "<if test='keyword != null and keyword != \"\"'>",
        "AND id LIKE CONCAT('%', #{keyword}, '%')",
        "</if>",
        "</script>"
    })
    List<MemberDTO> searchTicketHolders(@Param("keyword") String keyword);

    @Select("SELECT * FROM RegularMember")
    List<MemberDTO> getRegularMembers();
    
    @Select("SELECT id, vip_number AS vipNumber, userName, mobile, membership, date FROM VIPMember WHERE id LIKE CONCAT('%', #{keyword}, '%')")
    List<MemberDTO> searchVipMembers(@Param("keyword") String keyword);
    
    @Insert("INSERT INTO RegularMember (id, pw, userName, mobile, membership) " +
            "SELECT id, pw, userName, mobile, 'Regular' FROM VIPMember WHERE id = #{id}")
    int downgradeToRegular(@Param("id") String id);

    @Delete("DELETE FROM VIPMember WHERE id = #{id}")
    void removeFromVIP(@Param("id") String id);



    
}

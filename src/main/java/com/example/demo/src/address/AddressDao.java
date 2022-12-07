package com.example.demo.src.address;

import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AddressDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createAddress(PostAddressReq postAddressReq){

        String createAddressQuery = "insert into ADDRESS (USER_ID,ADDRESS_NAME,DETAIL_ADDRESS,ADDRESS_PICK) VALUES (?,?,?,?)";

        Object[] createAddressParams = new Object[]{postAddressReq.getUserId(),postAddressReq.getAddressName(),postAddressReq.getDetailAddress(),postAddressReq.getAddressPick()};

        this.jdbcTemplate.update(createAddressQuery, createAddressParams);

        String lastInsertIdQuery = "select last_insert_id()";

        System.out.println(lastInsertIdQuery);

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public PostAddressRes getAddressByAddressId(int addressId) {

        String getAddressQuery = "select * from ADDRESS where ADDRESS_ID = ?";

        return this.jdbcTemplate.queryForObject(getAddressQuery,
                (rs,rowNum) -> new PostAddressRes(
                        rs.getInt("ADDRESS_ID"),
                        rs.getInt("USER_ID"),
                        rs.getString("ADDRESS_NAME"),
                        rs.getString("DETAIL_ADDRESS"),
                        rs.getBoolean("ADDRESS_PICK")),
                addressId);
    }

    public int modifyAddress(PostAddressReq postAddressReq) {

        String modifyAddressQuery = "update ADDRESS set DETAIL_ADDRESS = ? where ADDRESS_ID = ?";

        Object[] modifyAddressParams = new Object[]{postAddressReq.getDetailAddress(), postAddressReq.getAddressId()};

        return this.jdbcTemplate.update(modifyAddressQuery, modifyAddressParams);
    }

    public int deleteAddress(int AddressId) {
        String deleteAddressQuery = "update ADDRESS set DELETE_YN = 1 where ADDRESS_ID = ? ";
        Object[] modifyAddressParams = new Object[]{AddressId};
        return this.jdbcTemplate.update(deleteAddressQuery, modifyAddressParams);
    }

    public List<PostAddressRes> getAllAddress(){

        String getAddressQuery = "select * from ADDRESS where DELETE_YN = 0";

        return this.jdbcTemplate.query(getAddressQuery,
                (rs,rowNum) -> new PostAddressRes(
                        rs.getInt("ADDRESS_ID"),
                        rs.getInt("USER_ID"),
                        rs.getString("ADDRESS_NAME"),
                        rs.getString("DETAIL_ADDRESS"),
                        rs.getBoolean("ADDRESS_PICK")));
    }

    public List<PostAddressRes> getAddressByKeyword(String keyWord) {

        String getRestaurantQuery = "select * from ADDRESS where ADDRESS_NAME like ? AND DELETE_YN = 0";

        System.out.println(getRestaurantQuery);

        String Param = "%" + keyWord + "%";
        System.out.println(Param);

        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new PostAddressRes(
                        rs.getInt("ADDRESS_ID"),
                        rs.getInt("USER_ID"),
                        rs.getString("ADDRESS_NAME"),
                        rs.getString("DETAIL_ADDRESS"),
                        rs.getBoolean("ADDRESS_PICK")),
                Param);
    }

    public List<PostAddressRes> getAddressByUserIdx(int userIdx) {

        String getRestaurantQuery = "select * from ADDRESS where USER_ID = ? AND DELETE_YN = 0";

        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new PostAddressRes(
                        rs.getInt("ADDRESS_ID"),
                        rs.getInt("USER_ID"),
                        rs.getString("ADDRESS_NAME"),
                        rs.getString("DETAIL_ADDRESS"),
                        rs.getBoolean("ADDRESS_PICK")),
                userIdx);
    }

    public void setAddressStatusTrueByAddressId(int AddressId) {
        String setAddressQuery = "update ADDRESS set ADDRESS_PICK = 1 where ADDRESS_ID = ? ";
        Object[] setAddressParams = new Object[]{AddressId};

        jdbcTemplate.update(setAddressQuery, setAddressParams);
    }

    public void setAddressStatusFalseByUserId(int userId) {
        String setAddressQuery = "update ADDRESS set ADDRESS_PICK = 0 where USER_ID = ? ";
        Object[] setAddressParams = new Object[]{userId};
        jdbcTemplate.update(setAddressQuery, setAddressParams);
    }
}

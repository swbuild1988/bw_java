package com.bandweaver.tunnel.common.platform.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class BooleanTypeHandler implements TypeHandler<Boolean> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Boolean aBoolean, JdbcType jdbcType) throws SQLException {
            preparedStatement.setInt(i, aBoolean ? 1 : 0);
    }

    @Override
    public Boolean getResult(ResultSet resultSet, String s) throws SQLException {
        return tranferType(resultSet.getInt(s));
    }

    @Override
    public Boolean getResult(ResultSet resultSet, int i) throws SQLException {
        return tranferType(resultSet.getInt(i));
    }

    @Override
    public Boolean getResult(CallableStatement callableStatement, int i) throws SQLException {
        return tranferType(callableStatement.getInt(i));
    }

    private Boolean tranferType(int val){
        return val > 0;
    }
}

package com.lz.manage.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lz.manage.model.dto.surveyQuestion.Option;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonToListTypeHandler extends BaseTypeHandler<List<Option>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Option> parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, objectMapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting list to JSON", e);
        }
    }

    @Override
    public List<Option> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        if (json == null) return Collections.emptyList();
        try {
            return Arrays.asList(objectMapper.readValue(json, Option[].class));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error parsing JSON to list", e);
        }
    }

    @Override
    public List<Option> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public List<Option> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Collections.emptyList();
    }
}

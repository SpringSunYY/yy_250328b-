package com.lz.manage.model.dto.surveyAnswer;

import com.lz.manage.model.domain.SurveyAnswer;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户填写的完整问卷记录Vo对象 tb_survey_answer
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class SurveyAnswerRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private Long surveyUserId;
    List<SurveyAnswerInsert> surveyAnswerInserts;
}

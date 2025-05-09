<template>
  <div class="survey-wrapper">
    <el-card class="survey-info-card" shadow="always">
      <ImagePreview :src="surveyInfo.coverUrl" alt="封面图" class="cover-image"/>
      <h2>{{ surveyInfo.surveyTitle }}</h2>
      <p>{{ surveyInfo.surveyDesc }}</p>

    </el-card>
    <el-form label-position="top">
      <div
        v-for="(question, index) in questionList"
        :key="question.id"
        class="question-card"
      >
        <el-divider content-position="left">
          <strong>{{ index + 1 }}. {{ question.questionTitle }}</strong>
          <span v-if="question.isRequired === '1'" style="color: red">（必填）</span>
          <span v-if="question.questionType === '0'" style="color: green">--单选</span>
          <span v-if="question.questionType === '1'" style="color: green">--多选</span>
          <span v-if="question.questionType === '2'" style="color: green">--填空</span>
        </el-divider>

        <!-- 单选题 -->
        <el-radio-group
          v-if="question.questionType === '0'"
          v-model="answers.find(ans => ans.questionId === question.id).answer"
          class="option-group"
        >
          <el-radio
            v-for="opt in question.questionOptions"
            :key="opt.label"
            :label="opt.label"
            class="option-item"
          >
            {{ opt.label }}. {{ opt.value }}
          </el-radio>
        </el-radio-group>

        <!-- 多选题 -->
        <el-checkbox-group
          v-else-if="question.questionType === '1'"
          v-model="answers.find(ans => ans.questionId === question.id).answer"
          class="option-group"
        >
          <el-checkbox
            v-for="opt in question.questionOptions"
            :key="opt.label"
            :label="opt.label"
            class="option-item"
          >
            {{ opt.label }}. {{ opt.value }}
          </el-checkbox>
        </el-checkbox-group>

        <!-- 填空题 -->
        <el-input
          v-else-if="question.questionType === '2'"
          type="textarea"
          v-model="answers.find(ans => ans.questionId === question.id).answer"
          :rows="4"
          class="input-area"
          placeholder="请输入您的回答"
        />
      </div>

      <div class="submit-btns">
        <el-button type="primary" size="medium" @click="handleSubmit(1)">
          提交答卷
        </el-button>
        <el-button size="medium" @click="handleSubmit(0)">保存草稿</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { addByAnswerList, getSurveyAnswer, listSurveyAnswer } from '@/api/manage/surveyAnswer'
import { getSurveyUser } from '@/api/manage/surveyUser'
import { getResearchSurvey } from '@/api/manage/researchSurvey'
import { questionList } from '@/api/manage/surveyQuestion'

export default {
  name: 'ResearchSurveyAnswer',
  data() {
    return {
      surveyUserId: null,
      surveyInfo: {},
      userId: 1,
      deptId: 103,
      surveyId: 1,
      questionList: [],
      answers: []
    }
  },
  created() {
    this.surveyUserId = this.$route.params && this.$route.params.surveyUserId
    //查询用户调研
    getSurveyUser(this.surveyUserId).then(response => {
      this.getSurveyInfo(response.data.surveyId)
      this.getQuestion(response.data.surveyId)
    })
  },
  methods: {
    //获取题目信息
    getQuestion(id) {
      questionList({ surveyId: id }).then(response => {
        this.questionList = response.rows

        // 初始化 answers 数组
        this.answers = this.questionList.map((q) => {
          return {
            questionId: q.id,
            questionType: q.questionType,
            answer: q.questionType === '1' ? [] : '', // 多选初始化为数组
            id: null // 初始化为空，后续查询时赋值
          }
        })
        this.getSurveyAnswer(this.surveyUserId)
      })
    },
    getSurveyAnswer(id) {
      listSurveyAnswer({ surveyUserId: id }).then(response => {
        const existingAnswers = response.rows

        existingAnswers.forEach((answer) => {
          const answerItem = this.answers.find(
            (ans) => ans.questionId === answer.questionId
          )

          if (!answerItem) {
            console.warn(`找不到对应的题目，questionId=${answer.questionId}`)
            return
          }

          answerItem.id = answer.id
          if (answer.questionType === '1') {
            answerItem.answer = answer.answer ? answer.answer.split(',') : []
          } else {
            answerItem.answer = answer.answer
          }
        })
      })
    },

    //查询问卷信息
    getSurveyInfo(id) {
      getResearchSurvey(id).then(response => {
        this.surveyInfo = response.data
      })
    },
    handleSubmit(status) {
      console.log('📝 提交数据：', status)
      // 仅在提交时校验
      if (status === 1) {
        for (const q of this.questionList) {
          const ans = this.answers.find((ans) => ans.questionId === q.id).answer
          if (q.isRequired === '1') {
            const isEmpty =
              q.questionType === '1' ? ans.length === 0 : !ans || ans.trim() === ''
            if (isEmpty) {
              this.$message.error(`第 ${q.questionOrder * 1 + 1} 题 "${q.questionTitle}" 为必填项`)
              return
            }
          }
        }
      }

      // 模拟提交数据
      const resultList = this.questionList.map((q) => {
        const answerObj = this.answers.find((ans) => ans.questionId === q.id) || {}
        const rawAnswer = this.answers.find((ans) => ans.questionId === q.id).answer
        const answer = q.questionType === '1' ? rawAnswer.join(',') : rawAnswer
        return {
          id: answerObj.id || null, // 是否已有答案记录
          surveyId: this.surveyId,
          questionId: q.id,
          questionType: q.questionType,
          answer,
          questionOrder: q.questionOrder,
          submitStatus: status
        }
      })
      const result = {
        surveyUserId: this.surveyUserId,
        surveyAnswerInserts: resultList
      }
      console.log('📋 提交内容：', result)

      addByAnswerList(result).then((res) => {
        this.$message.success(status === 1 ? '提交成功' : '草稿已保存')
      })
    }
  }
}
</script>

<style scoped>
.survey-info-card {
  margin-bottom: 24px;
}

.cover-image {
  width: 100%;
  border-radius: 8px;
  object-fit: cover;
}

.survey-wrapper {
  max-width: 800px;
  margin: 0 auto;
  padding: 30px;
  background-color: #fafafa;
  border-radius: 12px;
}

.question-card {
  background-color: #fff;
  padding: 20px 24px;
  margin-bottom: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.option-group {
  display: flex;
  flex-direction: column;
  margin-top: 12px;
}

.option-item {
  margin: 6px 0;
  font-size: 15px;
}

.input-area {
  margin-top: 12px;
  font-size: 15px;
}

.submit-btns {
  text-align: center;
  margin-top: 40px;
}
</style>

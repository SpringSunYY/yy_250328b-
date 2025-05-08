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
export default {
  name: 'ResearchSurveyAnswer',
  data() {
    return {
      surveyInfo: {
        surveyTitle: '测试调研信息0504',
        coverUrl: '/profile/upload/2025/05/04/20250310_20250504161111A001.png',
        surveyDesc: '只是一次测试',
        createTime: '2025-05-04',
        remark: '是的只是一次测试'
      },
      userId: 1,
      deptId: 103,
      surveyId: 1,
      questionList: [
        {
          id: '2',
          questionType: '1',
          questionTitle: 'AAA',
          isRequired: '1',
          questionOptions: [
            { label: 'A', value: '啊啊啊' },
            { label: 'B', value: '不不不' }
          ],
          questionOrder: '0'
        },
        {
          id: '3',
          questionType: '2',
          questionTitle: '什么是什么',
          isRequired: '0',
          questionOptions: [],
          questionOrder: '1'
        },
        {
          id: '1',
          questionType: '0',
          questionTitle: '312',
          isRequired: '1',
          questionOptions: [
            { label: 'A', value: '你是什么人' },
            { label: 'B', value: '312' },
            { label: 'C', value: '321' }
          ],
          questionOrder: '2'
        }
      ],
      answers: [
        { questionId: '2', answer: ['A'], questionOrder: 0 },
        { questionId: '3', answer: '这是我写的daw答案', questionOrder: 1 },
        { questionId: '1', answer: ['你是什么人', '321'], questionOrder: 2 }
      ]
    }
  },
  created() {
    // 模拟从后端返回的答案数组（你应该从接口中拿）
    const existingAnswers = [
      { questionId: '2', answer: ['A', 'B'], questionOrder: 0 },
      { questionId: '3', answer: '这是我写的答案', questionOrder: 1 },
      { questionId: '1', answer: 'A', questionOrder: 2 }
    ]

    // 将已有答案填入 answers 数组
    existingAnswers.forEach((answer) => {
      const answerItem = this.answers.find(
        (ans) => ans.questionId === answer.questionId
      )
      if (answerItem) {
        answerItem.answer = answer.answer
      }
    })
  },
  methods: {
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
      const result = this.questionList.map((q) => {
        const rawAnswer = this.answers.find((ans) => ans.questionId === q.id).answer
        const answer = q.questionType === '1' ? rawAnswer.join(',') : rawAnswer
        return {
          surveyId: this.surveyId,
          questionId: q.id,
          questionType: q.questionType,
          answer,
          questionOrder: q.questionOrder
        }
      })

      console.log('📋 提交内容：', result)
      this.$message.success(status === 1 ? '提交成功（静态）' : '草稿已保存（静态）')
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

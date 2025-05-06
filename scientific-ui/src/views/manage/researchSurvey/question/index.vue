<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="答卷" prop="surveyId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.surveyId"-->
      <!--          placeholder="请输入答卷"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="题目类型" prop="questionType">
        <el-select v-model="queryParams.questionType" placeholder="请选择题目类型" clearable>
          <el-option
            v-for="dict in dict.type.question_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="题目顺序" prop="questionOrder">
        <el-input
          v-model="queryParams.questionOrder"
          placeholder="请输入题目顺序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="部门" prop="deptId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.deptId"-->
      <!--          placeholder="请输入部门"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="是否必答" prop="isRequired">
        <el-select v-model="queryParams.isRequired" placeholder="请选择是否必答" clearable>
          <el-option
            v-for="dict in dict.type.question_is_required"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:surveyQuestion:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:surveyQuestion:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:surveyQuestion:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:surveyQuestion:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="surveyQuestionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="答卷" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="surveyName"
      />
      <el-table-column label="题目类型" align="center" v-if="columns[2].visible" prop="questionType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.question_type" :value="scope.row.questionType"/>
        </template>
      </el-table-column>
      <el-table-column label="题干内容" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="questionTitle"
      />
      <el-table-column label="题目内容" align="center" v-if="columns[4].visible">
        <template slot-scope="scope">
          <div v-if="Array.isArray(scope.row.questionOptions)">
            <div v-for="(item, index) in scope.row.questionOptions" :key="index">
              {{ item.label }}. {{ item.value }}
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="题目顺序" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="questionOrder"
      />
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="userName"
      />
      <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="deptName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[8].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否必答" align="center" v-if="columns[9].visible" prop="isRequired">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.question_is_required" :value="scope.row.isRequired"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:surveyQuestion:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:surveyQuestion:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改调研题目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="答卷" prop="surveyId">-->
        <!--          <el-input v-model="form.surveyId" placeholder="请输入答卷"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="题目类型" prop="questionType">
          <el-radio-group v-model="form.questionType">
            <el-radio
              v-for="dict in dict.type.question_type"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="题干内容" prop="questionTitle">
          <el-input v-model="form.questionTitle" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item v-if="form.questionType==='0'||form.questionType==='1'" label="题目内容" prop="questionOptions">
          <div
            v-for="(item, index) in form.questionOptions"
            :key="index"
            style="display: flex; align-items: center; margin-bottom: 10px;"
          >
            <span style="width: 20px;">{{ item.label }}.</span>
            <el-input
              v-model="item.value"
              placeholder="请输入选项内容"
              style="flex: 1;"
            />
            <el-button type="text" icon="el-icon-delete" @click="removeOption(index)"/>
          </div>
          <el-button type="primary" plain icon="el-icon-plus" @click="addOption">添加选项</el-button>
        </el-form-item>
        <el-form-item label="题目顺序" prop="questionOrder">
          <el-input-number :min="0" v-model="form.questionOrder" placeholder="请输入题目顺序"/>
        </el-form-item>
        <!--        <el-form-item label="创建人" prop="userId">-->
        <!--          <el-input v-model="form.userId" placeholder="请输入创建人"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="部门" prop="deptId">-->
        <!--          <el-input v-model="form.deptId" placeholder="请输入部门"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="是否必答" prop="isRequired">
          <el-radio-group v-model="form.isRequired">
            <el-radio
              v-for="dict in dict.type.question_is_required"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listSurveyQuestion,
  getSurveyQuestion,
  delSurveyQuestion,
  addSurveyQuestion,
  updateSurveyQuestion
} from '@/api/manage/surveyQuestion'

export default {
  name: 'SurveyQuestion',
  dicts: ['question_type', 'question_is_required'],
  data() {
    return {
      surveyId: null,
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: true },
        { key: 1, label: '答卷', visible: true },
        { key: 2, label: '题目类型', visible: true },
        { key: 3, label: '题干内容', visible: true },
        { key: 4, label: '题目内容', visible: true },
        { key: 5, label: '题目顺序', visible: true },
        { key: 6, label: '创建人', visible: false },
        { key: 7, label: '部门', visible: false },
        { key: 8, label: '创建时间', visible: false },
        { key: 9, label: '是否必答', visible: true }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 调研题目表格数据
      surveyQuestionList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否必答时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        surveyId: null,
        questionType: null,
        questionTitle: null,
        questionOrder: null,
        userId: null,
        deptId: null,
        createTime: null,
        isRequired: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        surveyId: [
          { required: true, message: '答卷不能为空', trigger: 'blur' }
        ],
        questionType: [
          { required: true, message: '题目类型不能为空', trigger: 'change' }
        ],
        questionTitle: [
          { required: true, message: '题干内容不能为空', trigger: 'blur' }
        ],
        questionOrder: [
          { required: true, message: '题目顺序不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '部门不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ],
        isRequired: [
          { required: true, message: '是否必答不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.surveyId = this.$route.params && this.$route.params.surveyId
    this.getList()
  },
  methods: {
    resetOptionLabels() {
      this.form.questionOptions.forEach((item, index) => {
        item.label = String.fromCharCode(65 + index) // 65 是 A
      })
    },
    addOption() {
      if (this.form.questionOptions === null) {
        this.form.questionOptions = []
      }
      const letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
      const nextIndex = this.form.questionOptions.length
      const nextLetter = letters[nextIndex] || '?'
      if (this.form.questionOptions.length >= letters.length) {
        this.$modal.msgError('超过字母范围')
        return   // 如果 index 超过字母范围
      }

      this.form.questionOptions.push({
        label: nextLetter,  // 不再是数字，而是字母
        value: ''
      })
      this.resetOptionLabels() // 重新赋字母
    },
    removeOption(index) {
      if (this.form.questionOptions === null) {
        this.form.questionOptions = []
      }
      this.form.questionOptions.splice(index, 1)
      this.resetOptionLabels()
    },
    /** 查询调研题目列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      this.queryParams.surveyId = this.surveyId
      listSurveyQuestion(this.queryParams).then(response => {
        this.surveyQuestionList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        surveyId: this.surveyId,
        questionType: null,
        questionTitle: null,
        questionOptions: null,
        questionOrder: null,
        userId: null,
        deptId: null,
        createTime: null,
        isRequired: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加调研题目'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSurveyQuestion(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改调研题目'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSurveyQuestion(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addSurveyQuestion(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除调研题目编号为"' + ids + '"的数据项？').then(function() {
        return delSurveyQuestion(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/surveyQuestion/export', {
        ...this.queryParams
      }, `surveyQuestion_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

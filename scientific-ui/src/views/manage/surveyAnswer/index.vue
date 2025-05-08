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
      <el-form-item label="答卷" prop="surveyId">
        <el-input
          v-model="queryParams.surveyId"
          placeholder="请输入答卷"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="题目" prop="questionId">
        <el-input
          v-model="queryParams.questionId"
          placeholder="请输入题目"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
      <el-form-item label="用户" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="题目顺序" prop="questionOrder">
        <el-input
          v-model="queryParams.questionOrder"
          placeholder="请输入题目顺序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
      <el-form-item label="提交时间">
        <el-date-picker
          v-model="daterangeSubmitTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="提交状态" prop="submitStatus">
        <el-select v-model="queryParams.submitStatus" placeholder="请选择提交状态" clearable>
          <el-option
            v-for="dict in dict.type.answer_submit_status"
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
          v-hasPermi="['manage:surveyAnswer:add']"
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
          v-hasPermi="['manage:surveyAnswer:edit']"
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
          v-hasPermi="['manage:surveyAnswer:remove']"
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
          v-hasPermi="['manage:surveyAnswer:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="surveyAnswerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="答卷" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="surveyId"
      />
      <el-table-column label="题目" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="questionId"
      />
      <el-table-column label="题目类型" align="center" v-if="columns[3].visible" prop="questionType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.question_type" :value="scope.row.questionType"/>
        </template>
      </el-table-column>
      <el-table-column label="答案" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="answer"
      />
      <el-table-column label="用户" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="userId"
      />
      <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="deptId"
      />
      <el-table-column label="题目顺序" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="questionOrder"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[8].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="提交时间" align="center" v-if="columns[9].visible" prop="submitTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.submitTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="提交状态" align="center" v-if="columns[10].visible" prop="submitStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.answer_submit_status" :value="scope.row.submitStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:surveyAnswer:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:surveyAnswer:remove']"
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

    <!-- 添加或修改用户填写的完整问卷记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="答卷" prop="surveyId">
          <el-input v-model="form.surveyId" placeholder="请输入答卷"/>
        </el-form-item>
        <el-form-item label="题目" prop="questionId">
          <el-input v-model="form.questionId" placeholder="请输入题目"/>
        </el-form-item>
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
        <el-form-item label="答案" prop="answer">
          <el-input v-model="form.answer" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="用户" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户"/>
        </el-form-item>
        <el-form-item label="部门" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入部门"/>
        </el-form-item>
        <el-form-item label="题目顺序" prop="questionOrder">
          <el-input v-model="form.questionOrder" placeholder="请输入题目顺序"/>
        </el-form-item>
        <el-form-item label="提交状态" prop="submitStatus">
          <el-radio-group v-model="form.submitStatus">
            <el-radio
              v-for="dict in dict.type.answer_submit_status"
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
  listSurveyAnswer,
  getSurveyAnswer,
  delSurveyAnswer,
  addSurveyAnswer,
  updateSurveyAnswer
} from '@/api/manage/surveyAnswer'

export default {
  name: 'SurveyAnswer',
  dicts: ['question_type', 'answer_submit_status'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: true },
        { key: 1, label: '答卷', visible: true },
        { key: 2, label: '题目', visible: true },
        { key: 3, label: '题目类型', visible: true },
        { key: 4, label: '答案', visible: true },
        { key: 5, label: '用户', visible: true },
        { key: 6, label: '部门', visible: true },
        { key: 7, label: '题目顺序', visible: true },
        { key: 8, label: '创建时间', visible: true },
        { key: 9, label: '提交时间', visible: true },
        { key: 10, label: '提交状态', visible: true }
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
      // 用户填写的完整问卷记录表格数据
      surveyAnswerList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 提交状态时间范围
      daterangeCreateTime: [],
      // 提交状态时间范围
      daterangeSubmitTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        surveyId: null,
        questionId: null,
        questionType: null,
        userId: null,
        deptId: null,
        questionOrder: null,
        createTime: null,
        submitTime: null,
        submitStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        surveyId: [
          { required: true, message: '答卷不能为空', trigger: 'blur' }
        ],
        questionId: [
          { required: true, message: '题目不能为空', trigger: 'blur' }
        ],
        questionType: [
          { required: true, message: '题目类型不能为空', trigger: 'change' }
        ],
        userId: [
          { required: true, message: '用户不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '部门不能为空', trigger: 'blur' }
        ],
        questionOrder: [
          { required: true, message: '题目顺序不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ],
        submitTime: [
          { required: true, message: '提交时间不能为空', trigger: 'blur' }
        ],
        submitStatus: [
          { required: true, message: '提交状态不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询用户填写的完整问卷记录列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeSubmitTime && '' != this.daterangeSubmitTime) {
        this.queryParams.params['beginSubmitTime'] = this.daterangeSubmitTime[0]
        this.queryParams.params['endSubmitTime'] = this.daterangeSubmitTime[1]
      }
      listSurveyAnswer(this.queryParams).then(response => {
        this.surveyAnswerList = response.rows
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
        surveyId: null,
        questionId: null,
        questionType: null,
        answer: null,
        userId: null,
        deptId: null,
        questionOrder: null,
        createTime: null,
        submitTime: null,
        submitStatus: null
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
      this.daterangeSubmitTime = []
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
      this.title = '添加用户填写的完整问卷记录'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSurveyAnswer(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改用户填写的完整问卷记录'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSurveyAnswer(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addSurveyAnswer(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除用户填写的完整问卷记录编号为"' + ids + '"的数据项？').then(function() {
        return delSurveyAnswer(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/surveyAnswer/export', {
        ...this.queryParams
      }, `surveyAnswer_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

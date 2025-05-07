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
      <el-form-item label="答卷" prop="surveyId" v-if="isQueryResearchSurvey">
        <el-select
          v-model="queryParams.surveyId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入答卷标题"
          :remote-method="selectResearchSurveyList"
          :loading="researchSurveyLoading"
        >
          <el-option
            v-for="item in researchSurveyList"
            :key="item.id"
            :label="item.surveyTitle"
            :value="item.surveyId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户" prop="userId" v-if="isQueryUser">
        <el-select
          v-model="queryParams.userId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入用户账号"
          :remote-method="selectUserList"
          :loading="userServiceLoading"
        >
          <el-option
            v-for="item in userServiceList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker
          v-model="daterangeStartTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker
          v-model="daterangeEndTime"
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
      <!--      <el-form-item label="部门" prop="deptId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.deptId"-->
      <!--          placeholder="请输入部门"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="创建人" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.survey_user_status"
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
          v-hasPermi="['manage:surveyUser:add']"
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
          v-hasPermi="['manage:surveyUser:edit']"
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
          v-hasPermi="['manage:surveyUser:remove']"
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
          v-hasPermi="['manage:surveyUser:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="surveyUserList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="答卷" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="surveyName"
      />
      <el-table-column label="用户" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="userName"
      />
      <el-table-column label="开始时间" align="center" v-if="columns[3].visible" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" v-if="columns[4].visible" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="提交时间" align="center" v-if="columns[5].visible" prop="submitTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.submitTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="deptName"
      />
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="createBy"
      />
      <el-table-column label="状态" align="center" v-if="columns[8].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.survey_user_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:surveyUser:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:surveyUser:remove']"
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

    <!-- 添加或修改用户调研对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="答卷" prop="surveyId" v-if="isQueryResearchSurvey">
          <el-select
            :disabled="form.id!==null"
            v-model="form.surveyId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入答卷标题"
            :remote-method="selectResearchSurveyList"
            :loading="researchSurveyLoading"
          >
            <el-option
              v-for="item in researchSurveyList"
              :key="item.id"
              :label="item.surveyTitle"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户" prop="userId" v-if="isQueryUser">
          <el-select
            :disabled="form.id!==null"
            v-model="form.userId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入用户账号"
            :remote-method="selectUserList"
            :loading="userServiceLoading"
          >
            <el-option
              v-for="item in userServiceList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable
                          v-model="form.startTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择开始时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
                          v-model="form.endTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="提交时间" prop="submitTime">
          <el-date-picker clearable
                          v-model="form.submitTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择提交时间"
          >
          </el-date-picker>
        </el-form-item>
        <!--        <el-form-item label="部门" prop="deptId">-->
        <!--          <el-input v-model="form.deptId" placeholder="请输入部门"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="状态" prop="status">-->
        <!--          <el-radio-group v-model="form.status">-->
        <!--            <el-radio-->
        <!--              v-for="dict in dict.type.survey_user_status"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.value"-->
        <!--            >{{ dict.label }}-->
        <!--            </el-radio>-->
        <!--          </el-radio-group>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSurveyUser, getSurveyUser, delSurveyUser, addSurveyUser, updateSurveyUser } from '@/api/manage/surveyUser'
import { checkPermi } from '@/utils/permission'
import { listUser } from '@/api/system/user'
import { listResearchSurvey } from '@/api/manage/researchSurvey'

export default {
  name: 'SurveyUser',
  dicts: ['survey_user_status'],
  data() {
    return {
      //答卷信息
      isQueryResearchSurvey: false,
      researchSurveyList: [],
      researchSurveyLoading: false,
      researchSurveyQueryParams: {
        surveyTitle: '',
        pageNum: 1,
        pageSize: 100
      },
      isQueryUser: false,
      //用户信息
      userServiceList: [],
      userServiceLoading: false,
      userServiceQueryParams: {
        userName: '',
        pageNum: 1,
        pageSize: 100
      },
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: false },
        { key: 1, label: '答卷', visible: true },
        { key: 2, label: '用户', visible: true },
        { key: 3, label: '开始时间', visible: true },
        { key: 4, label: '结束时间', visible: true },
        { key: 5, label: '提交时间', visible: true },
        { key: 6, label: '部门', visible: false },
        { key: 7, label: '创建人', visible: true },
        { key: 8, label: '状态', visible: true }
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
      // 用户调研表格数据
      surveyUserList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 状态时间范围
      daterangeStartTime: [],
      // 状态时间范围
      daterangeEndTime: [],
      // 状态时间范围
      daterangeSubmitTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        surveyId: null,
        userId: null,
        startTime: null,
        endTime: null,
        submitTime: null,
        deptId: null,
        createBy: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        surveyId: [
          { required: true, message: '答卷不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '用户不能为空', trigger: 'blur' }
        ],
        startTime: [
          { required: true, message: '开始时间不能为空', trigger: 'blur' }
        ],
        endTime: [
          { required: true, message: '结束时间不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '部门不能为空', trigger: 'blur' }
        ],
        createBy: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    if (checkPermi(['manage:surveyUser:add'])) {
      this.isQueryUser = true
      this.getUserList()
    }
    if (checkPermi(['manage:researchSurvey:list'])) {
      this.isQueryResearchSurvey = true
      this.getResearchSurveyList()
    }
    this.getList()
  },
  methods: {
    checkPermi,
    //获取答卷推荐列表
    selectResearchSurveyList(query) {
      if (query !== '') {
        this.researchSurveyLoading = true
        this.researchSurveyQueryParams.surveyTitle = query
        setTimeout(() => {
          this.getResearchSurveyList()
        }, 200)
      } else {
        this.userServiceList = []
      }
    },
    /**
     * 获取用户信息列表
     */
    getResearchSurveyList() {
      //添加查询参数
      if (this.surveyId != null) {
        this.researchSurveyQueryParams.surveyId = this.form.surveyId
      } else {
        this.researchSurveyQueryParams.surveyId = null
      }
      if (this.researchSurveyQueryParams.surveyTitle != null) {
        this.researchSurveyQueryParams.surveyId = null
      }
      listResearchSurvey(this.researchSurveyQueryParams).then(res => {
        this.researchSurveyList = res.rows
        this.researchSurveyLoading = false
      })
    },
    /**
     * 获取用户列表推荐
     * @param query
     */
    selectUserList(query) {
      if (query !== '') {
        this.userServiceLoading = true
        this.userServiceQueryParams.userName = query
        setTimeout(() => {
          this.getUserList()
        }, 200)
      } else {
        this.userServiceList = []
      }
    },
    /**
     * 获取用户信息列表
     */
    getUserList() {
      //添加查询参数
      if (this.form.userId != null) {
        this.userServiceQueryParams.userId = this.form.userId
      } else {
        this.userServiceQueryParams.userId = null
      }
      if (this.userServiceQueryParams.userName != null) {
        this.userServiceQueryParams.userId = null
      }
      listUser(this.userServiceQueryParams).then(res => {
        this.userServiceList = res.rows
        this.userServiceLoading = false
      })
    },
    /** 查询用户调研列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeStartTime && '' != this.daterangeStartTime) {
        this.queryParams.params['beginStartTime'] = this.daterangeStartTime[0]
        this.queryParams.params['endStartTime'] = this.daterangeStartTime[1]
      }
      if (null != this.daterangeEndTime && '' != this.daterangeEndTime) {
        this.queryParams.params['beginEndTime'] = this.daterangeEndTime[0]
        this.queryParams.params['endEndTime'] = this.daterangeEndTime[1]
      }
      if (null != this.daterangeSubmitTime && '' != this.daterangeSubmitTime) {
        this.queryParams.params['beginSubmitTime'] = this.daterangeSubmitTime[0]
        this.queryParams.params['endSubmitTime'] = this.daterangeSubmitTime[1]
      }
      listSurveyUser(this.queryParams).then(response => {
        this.surveyUserList = response.rows
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
        userId: null,
        startTime: null,
        endTime: null,
        submitTime: null,
        deptId: null,
        createBy: null,
        status: null
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
      this.daterangeStartTime = []
      this.daterangeEndTime = []
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
      this.title = '添加用户调研'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSurveyUser(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改用户调研'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSurveyUser(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addSurveyUser(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除用户调研编号为"' + ids + '"的数据项？').then(function() {
        return delSurveyUser(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/surveyUser/export', {
        ...this.queryParams
      }, `surveyUser_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

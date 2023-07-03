<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input
        style="width: 220px; margin-right: 10px"
        placeholder="请输入姓名"
        v-model="name"
        suffix-icon="el-icon-search"
        clearable
      >
      </el-input>
      <el-select
        v-model="sex"
        clearable
        placeholder="请选择性别"
        style="margin-right: 10px"
      >
        <el-option
          v-for="item in sexs"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button type="primary" icon="el-icon-search" @click="getUserList"
        >查询</el-button
      >
      <el-button type="success" icon="el-icon-close" @click="clearInput"
        >清空</el-button
      >
      <el-button
        type="info"
        icon="el-icon-plus"
        style="float: right; margin-right: 20px"
        @click="openDialog(null)"
        circle
      ></el-button>
    </div>

    <el-table
      :data="tableData"
      :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
      border
    >
      <el-table-column prop="id" label="ID" width="80"> </el-table-column>
      <el-table-column prop="no" label="账号" width="180"> </el-table-column>
      <el-table-column prop="name" label="姓名" width="180"> </el-table-column>
      <el-table-column prop="age" label="年龄" width="80"> </el-table-column>
      <el-table-column prop="sex" label="性别" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.sex == 1 ? '' : 'warning'">{{
            scope.row.sex == 1 ? "男" : "女"
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="180">
      </el-table-column>
      <el-table-column prop="roleId" label="角色" width="140">
        <template slot-scope="scope">
          <el-tag
            :type="
              scope.row.roleId === 0
                ? 'danger'
                : scope.row.roleId === 1
                ? 'success'
                : 'primary'
            "
            disable-transition
            >{{
              scope.row.roleId === 0
                ? "超级管理员"
                : scope.row.roleId === 1
                ? "普通管理员"
                : "普通员工"
            }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="isValid" label="操作">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="small"
            @click="openDialog(scope.row.id)"
            >编辑</el-button
          >
          <el-button
            type="danger"
            size="small"
            @click="deleteUser(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[2, 5, 10, 15]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <!-- 对话框 -->
    <el-dialog
      @close="closeDialog"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="userForm" :rules="rules" ref="userRef">
        <el-form-item label="账号" prop="no" :label-width="formLabelWidth">
          <el-input
            v-model="userForm.no"
            autocomplete="off"
            style="width: 80%"
          ></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
          <el-input
            v-model="userForm.name"
            autocomplete="off"
            style="width: 80%"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          prop="password"
          :label-width="formLabelWidth"
          v-if="userForm.id == null || userForm.id == undefined"
        >
          <el-input
            v-model="userForm.password"
            autocomplete="off"
            style="width: 80%"
          ></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age" :label-width="formLabelWidth">
          <el-input
            v-model="userForm.age"
            autocomplete="off"
            style="width: 80%"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" :label-width="formLabelWidth">
          <el-select v-model="userForm.sex" placeholder="性别">
            <el-option label="男" :value="1"></el-option>
            <el-option label="女" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" :label-width="formLabelWidth">
          <el-input
            v-model="userForm.phone"
            autocomplete="off"
            style="width: 80%"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitUser(userForm.id)"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "WmsWebMainVue",

  data() {
    //手机号验证规则
    var checkPhoneNum = (rule, value, callback) => {
      var reg = /^(?:(?:\+|00)86)?1\d{10}$/;
      if (!reg.test(value)) {
        return callback(new Error("手机号格式不正确"));
      }
      callback();
    };

    //用户名不可重复设置
    let checkDuplicate = (rule, value, callback) => {
      if (this.userForm.id) {
        return callback();
      }
      this.$axios
        .get("http://localhost:9090/user/findByNo?no=" + this.userForm.no)
        .then((res) => {
          if (res.data.code != 200) {
            callback();
          } else {
            callback(new Error("账号已经存在"));
          }
        });
    };

    //年龄校验
    let checkAge = (rule, value, callback) => {
      if (value > 100) {
        callback(new Error("年龄输⼊过⼤"));
      } else {
        callback();
      }
    };

    return {
      tableData: [],
      pageSize: 5,
      pageNum: 1,
      total: 0,
      name: "",
      sex: "",
      sexs: [
        {
          value: "1",
          label: "男",
        },
        {
          value: "0",
          label: "女",
        },
      ],
      dialogTableVisible: false,
      dialogFormVisible: false,
      userForm: {
        no: "",
        name: "",
        password: "",
        age: "",
        sex: "",
        phone: "",
      },
      formLabelWidth: "120px",
      title: "",

      rules: {
        no: [
          { required: true, message: "请输入账户名", trigger: "blur" },
          {
            min: 3,
            max: 15,
            message: "长度应在 3 到 15 个字符",
            trigger: "blur",
          },
          {
            validator: checkDuplicate,
            trigger: "blur",
          },
        ],
        name: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          {
            min: 2,
            max: 15,
            message: "长度应在 2 到 15 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          {
            min: 5,
            max: 15,
            message: "长度在 5 到 15 个字符",
            trigger: "blur",
          },
        ],
        age: [
          { required: true, message: "请输⼊年龄", trigger: "blur" },
          {
            pattern: /^([1-9][0-9]{0,1}|100)$/,
            message: "年龄必须为1-100的正整数字",
            trigger: "blur",
          },
          { validator: checkAge, trigger: "blur" },
        ],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          {
            validator: checkPhoneNum,
            message: "手机号格式错误",
            trigger: "blur",
          },
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
      },
    };
  },

  methods: {
    //查询用户信息（分页）
    getUserList() {
      this.$axios
        .get("http://localhost:9090/user/pageList", {
          params: {
            pageSize: this.pageSize,
            pageNum: this.pageNum,
            name: this.name,
            sex: this.sex,
          },
        })
        .then((res) => {
          this.tableData = res.data.data.userList;
          this.total = res.data.data.total;
        });
    },

    handleSizeChange(val) {
      this.pageNum = 1;
      this.pageSize = val;
      this.getUserList();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getUserList();
    },
    clearInput() {
      (this.name = ""), (this.sex = "");
    },

    //清空对话框数据和数据校验结果闭关对话框
    closeDialog() {
      this.userForm = {};
      this.$refs.userRef.clearValidate();
      this.dialogFormVisible = false;
    },
    submitUser(operate) {
      //-============================================传id进去即可
      //提交前先验证
      this.$refs.userRef.validate((valid) => {
        if (valid) {
          if (operate == null || operate == undefined) {
            //post请求
            this.$axios
              .post("http://localhost:9090/user/addUser", this.userForm)
              .then(() => {
                //console.log(res);
                //清空对话框数据和数据校验结果闭关对话框
                this.closeDialog();
                //重新查询显示
                this.getUserList();
                //弹出提示
                this.$message({
                  message: "添加成功！",
                  type: "success",
                });
              });
          } else {
            //用户信息修改
            this.updateUserForm();
          }
        } else {
          console.log("错误提交！");
          return false;
        }
      });
    },

    openDialog(operate) {
      if (operate == null) {
        //打开对话框
        this.dialogFormVisible = true;
        this.title = "新增用户";
      } else {
        this.title = "修改用户";
        this.dialogFormVisible = true;
        this.getById(operate);
      }
    },
    resetForm() {
      this.$refs.userRef.resetFields();
    },

    getById(id) {
      this.$axios.get(`http://localhost:9090/user/${id}`).then((res) => {
        this.userForm = res.data.data;
      });
    },

    //修改用户信息
    updateUserForm() {
      this.$axios
        .put("http://localhost:9090/user", this.userForm)
        .then((this.dialogFormVisible = false));
      //弹出提示
      this.$message({
        message: "修改成功！",
        type: "success",
      });

      //异步执行（上面执行好后才会执行这个）
      this.$nextTick(() => {
        this.getUserList();
      });
    },

    //删除用户信息
    deleteUser(id) {
      //校验是否确定删除
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //确认删除
          this.$axios.delete(`http://localhost:9090/user/${id}`).then(() => {
            //弹出提示
            this.$message({
              message: "删除成功！",
              type: "success",
            });

            //异步执行（上面执行好后才会执行这个）
            this.$nextTick(() => {
              this.getUserList();
            });
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },

  created() {
    this.getUserList();
  },
};
</script>

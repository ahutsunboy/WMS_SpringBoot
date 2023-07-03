<template>
  <div style="display: flex;">
    <div>
        <i :class="icon" style="font-size: 25px;" @click="toCollapse" ></i>
    </div>
    <div style="flex: 1; font-size: 30px; color: grey;text-align: center;">
        <span>仓库管理系统</span>
    </div>
    <div style="">
        <span style="margin-right:10px">{{user.name}}</span>
    </div>
    <el-dropdown>
      <i class="el-icon-arrow-down" style="margin-right: 15px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "WmsWebHeaderVue",

  data(){
    return{
      user:JSON.parse(sessionStorage.getItem("CurUser")),
    }
  },

  props:{
    icon:String
  },

  methods:{
    toUser(){
        console.log("toUser")
        this.$router.push("/Home")
    },
    logout(){
      this.$confirm('确定要退出登录吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //确定退出
          this.$router.push("/");
          sessionStorage.clear();
        }).catch(() => {
             
        });
       
    },
    toCollapse(){
      this.$emit('collapse')
    }
  },

  created(){
    this.$router.push("/Home");
  }

};
</script>

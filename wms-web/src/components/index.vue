<template>
    <el-container style="height: 100%; border: 1px solid #eee" >
      <el-aside :width="aside_width" style="background-color: rgb(238, 241, 246) height: 100%;  margin-left: -1px;">
        <!-- 引入AsideVue -->
       <asideVue :isCollapse="collapse"></asideVue>
      </el-aside>

      <el-container style="height: 100%;">
        <el-header style="text-align: right; font-size: 12px height: 100%;">
          <!-- 引入HeaderVue -->
        <headerVue @collapse="doCollapse" :icon="icon"></headerVue>
        </el-header>

        <el-main style="height: 100%;">
          <!-- 引入MainVue -->
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
</template>

<script>
import asideVue from './AsideVue.vue';
import headerVue from './HeaderVue.vue';

export default {
  components: { asideVue ,headerVue},
  data() {
    return{
      collapse:false,
      aside_width:'200px',
      icon:"el-icon-s-fold"
    };
  },
 
  methods:{
    doCollapse(){
      this.collapse = !this.collapse;
      if(!this.collapse){
        this.aside_width = '200px';
        this.icon = "el-icon-s-fold";
      }else{
        this.aside_width = '64px';
        this.icon="el-icon-s-unfold"
      }
    },

    
  },



  
}; 
</script>

<style>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
  
}
.el-main {
  padding: 10px;
}


</style>
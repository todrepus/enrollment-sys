<template>
  <img alt="Vue logo" src="../../assets/logo.png">
  <div v-if="isLogin && isAdmin">
    <button @click="movePage('/admin')">관리페이지</button>
  </div>

  <div v-if="isLogin && !isAdmin">
    <button @click="movePage('/mypage')">마이페이지</button>
  </div>

  <div v-if="isLogin">
    <button @click="logout">로그아웃</button>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import {GET_LOGIN_MEMBER} from './store'
import { computed } from 'vue';
import * as http from '../http'
export default {
  
  name: 'App',
  setup(){
    const store = useStore();
    const isLogin = computed(()=> store.state.loginMember != null);
    const isAdmin = computed(()=> store.state.loginMember.role == "관리자");
    const logout = () => http.logout();
    const movePage = (url) => {location.href = url;}
    store.dispatch(GET_LOGIN_MEMBER);

    return {isLogin, isAdmin, logout, movePage};
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

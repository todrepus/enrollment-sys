<template>
  <!-- Nav tabs -->
  <div>
  <HeaderVue></HeaderVue>
  </div>
  <ul class="nav nav-tabs" id="myTab" role="tablist">
    <li class="nav-item" role="presentation">
      <button class="nav-link active" id="member-tab" data-bs-toggle="tab" data-bs-target="#member" type="button" role="tab" aria-controls="member" aria-selected="true"
      @click="selected='member'">회원</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="course-tab" data-bs-toggle="tab" data-bs-target="#course" type="button" role="tab" aria-controls="profile" aria-selected="false"
      @click="selected='course'">강의</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="room-tab" data-bs-toggle="tab" data-bs-target="#room" type="button" role="tab" aria-controls="profile" aria-selected="false"
      @click="selected='room'">강의실</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="department-tab" data-bs-toggle="tab" data-bs-target="#department" type="button" role="tab" aria-controls="profile" aria-selected="false"
      @click="selected='department'">학과</button>
    </li>
  </ul>
  
  <!-- Tab panes -->
  <div class="tab-content">
    <div class="tab-pane active" id="member" role="tabpanel" aria-labelledby="member-tab">
      <MemberBox></MemberBox>
      </div>
    <div class="tab-pane" id="course" role="tabpanel" aria-labelledby="course-tab">
      <CourseBox></CourseBox>
    </div>
    <div class="tab-pane" id="room" role="tabpanel" aria-labelledby="room-tab">
      <RoomBox></RoomBox>
    </div>
    <div class="tab-pane" id="department" role="tabpanel" aria-labelledby="department-tab">
      <DepartmentBox></DepartmentBox>
    </div>
  </div>

  <div v-if="maxPage != 0">
    <ul class="nav justify-content-center">
      <li :key="i" class="nav-item" v-for="i in pageNum">
        <button class="nav-link mx-1 btn btn-outline-secondary" @click="movePage(i+startPage-1)">{{ i + startPage }}</button>
      </li>
    </ul>
  </div>
</template>

<script>
import MemberBox from './components/Member/MemberBox.vue';
import CourseBox from './components/Course/CourseBox.vue';
import RoomBox from './components/Room/RoomBox.vue';
import DepartmentBox from './components/Department/DepartmentBox.vue';
import HeaderVue from '../components/HeaderVue.vue';

import { useStore } from 'vuex';
import { ref, computed } from 'vue';
import { GET_LOGIN_MEMBER } from './store';

export default {
    name: "App",
    components : {
      MemberBox, CourseBox, RoomBox, DepartmentBox, HeaderVue
    },
    setup() {
      const store = useStore();
      let selected = ref("member");
      const nowPage = computed(()=>store.state[selected.value].page.nowPage);
      const maxPage = computed(()=>store.state[selected.value].page.maxPage);
      const movePage = (page) => {
        if (page == nowPage.value)
          return;
        store.dispatch(`${selected.value}/movePage`, page)
      };

      const num = ref(store.state[selected.value].page.num);
      const startPage = computed(()=>Math.floor((nowPage.value+1)/store.state[selected.value].page.num));
      
      const pageNum = computed(() => (startPage.value+num.value <= maxPage.value ? startPage.value + num.value : maxPage.value));
      
      store.dispatch(GET_LOGIN_MEMBER);
      
      return {selected, nowPage, maxPage, movePage, startPage, pageNum};
    }
}
</script>



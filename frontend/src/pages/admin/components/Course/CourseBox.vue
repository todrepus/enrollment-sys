<template>
    <!-- Hover added -->
    <div class="d-flex justify-content-end m-4">
      <button type="button" class="btn btn-primary mx-3" @click="showAddModal"> 강의추가
        <svg xmlns="http://www.w3.org/2000/svg" width="17" height="17" fill="currentColor" class="bi bi-plus"
          viewBox="0 0 16 16">
          <path
            d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
        </svg>
      </button>
    </div>
    <hr>
  
    <table class="table table-hover">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">강의명</th>
          <th scope="col">학과</th>
          <th scope="col">교수명</th>
          <th scope="col">강의실</th>
          <th scope="col">강의시간</th>
          <th scope="col"></th>
        </tr>
      </thead>
      <tbody>
        <CourseElementVue :key="i" :course="data" :idx="i"
         v-for="(data, i) in courseList"></CourseElementVue>
      </tbody>
    </table>

    <!-- 강의 추가 모달-->
    <CourseAddModalVue v-if="addModalVisible"></CourseAddModalVue>
    <!-- /////////////-->
    <!-- 강의 편집 모달-->
    <CourseEditModalVue
      v-if="editModalVisible"></CourseEditModalVue>
    <!-- /////////////-->
  
  </template>
    
  <script>
    import CourseElementVue from './CourseElement.vue';
    import CourseAddModalVue from './CourseAddModal.vue';
    import CourseEditModalVue from './CourseEditModal.vue';
    import { useStore } from 'vuex';
    import {NAMESPACE} from '@/pages/admin/store/modules/course';
    import * as actions from '@/pages/admin/store/modules/course/actions';
import { computed } from 'vue';

  export default {
    name: 'CourseBox',
    components: {
        CourseElementVue, CourseAddModalVue, CourseEditModalVue
    },
    setup(){
      console.debug('setup')
      const store = useStore();
      const showAddModal = () => {store.dispatch(`${NAMESPACE}/${actions.SHOW_ADD_MODAL}`)};
      const courseList = computed(() => store.state.course.data.courses);
      const addModalVisible = computed(()=>store.state.course.view.addModalVisible);
      const editModalVisible = computed(()=>store.state.course.view.editModalVisible);

      return {showAddModal, courseList, addModalVisible, editModalVisible};
    },
    beforeCreate(){
      this.$store.dispatch(`${NAMESPACE}/${actions.GET_COURSES_ON_PAGE}`, 0)

    }
  }
  </script>
    
  
    
<template>
    <!-- Hover added -->
    <div class="d-flex justify-content-end m-4">
      <button type="button" class="btn btn-primary mx-3" @click="setAddModalVisible(true)"> 강의추가
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
          <th scope="col">강의코드</th>
          <th scope="col">강의명</th>
          <th scope="col">학과</th>
          <th scope="col">교수명</th>
          <th scope="col">강의실</th>
          <th scope="col">강의시간</th>
          <th scope="col"></th>
        </tr>
      </thead>
      <tbody>
        <CourseElementVue :key="i" :course="data" :idx="i" :visible="setEditModalVisible" :editInit="editInit"
         v-for="(data, i) in elements"></CourseElementVue>
      </tbody>
    </table>

    <!-- 강의 추가 모달-->
    <CourseAddModalVue :visible="setAddModalVisible" :addCourse="addCourse" v-if="addModalVisible"></CourseAddModalVue>
    <!-- /////////////-->
    <!-- 강의 편집 모달-->
    <CourseEditModalVue :visible="setEditModalVisible" :editCourse="editCourse" :oldCourse=nowEditCourse v-if="editModalVisible"></CourseEditModalVue>
    <!-- /////////////-->
  
  </template>
    
  <script>
    import CourseElementVue from './CourseElement.vue';
    import CourseAddModalVue from './CourseAddModal.vue';
    import CourseEditModalVue from './CourseEditModal.vue';

  export default {
    name: 'CourseBox',
    props: {
      msg: String
    },
    components: {
        CourseElementVue, CourseAddModalVue, CourseEditModalVue
    },
    methods: {
        setAddModalVisible(state) {
            this.addModalVisible = state;
        },
        setEditModalVisible(state) {
            this.editModalVisible = state;
        },
        addCourse(course){
            //// fetch api addCourse ///
            ///////////////

            //// if success ////
            this.addModalVisible = false;
            this.elements.push(course);
            ////////////////////
        },
        editCourse(course){
            //// fetch api editCourse ///
            ///////////////
            //// if success ////
            this.editModalVisible = false;
            this.elements[course.idx] = course;
            ////////////////////
        },
        editInit(key){
            this.nowEditCourse = this.elements[key];
            this.nowEditCourse.idx = key;
        }
    },
    data() {
      return {
        elements : [{id:0, code : '001', name:'전자회로2', profName:'김준엽', roomName:'충무관103호',  
        department: '전자정보통신공학',
        dates:[{date:'월', start:'15:00', end:'17:00', visible:false}, {date:'수', start:'17:00', end:'19:00', visible:false}],} ],
        addModalVisible : false,
        editModalVisible : false,
        nowEditCourse : {}
      };
    },
  }
  </script>
    
  
    
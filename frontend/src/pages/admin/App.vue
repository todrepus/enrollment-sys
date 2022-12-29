<template>
  <!-- Nav tabs -->
  <ul class="nav nav-tabs" id="myTab" role="tablist">
    <li class="nav-item" role="presentation">
      <button class="nav-link active" id="member-tab" data-bs-toggle="tab" data-bs-target="#member" type="button" role="tab" aria-controls="member" aria-selected="true">회원</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="course-tab" data-bs-toggle="tab" data-bs-target="#course" type="button" role="tab" aria-controls="profile" aria-selected="false">강의</button>
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
  </div>
</template>

<script>
import MemberBox from './components/Member/MemberBox.vue';
import CourseBox from './components/Course/CourseBox.vue';
export default {
    name: "App",
    components : {
      MemberBox, CourseBox
    }
}
</script>



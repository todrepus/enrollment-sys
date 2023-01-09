<template>
  <!-- Hover added -->
  <div class="d-flex justify-content-end m-4">
    <button type="button" class="btn btn-primary mx-3" @click="setAddModalVisible(true)"> 멤버추가
      <svg xmlns="http://www.w3.org/2000/svg" width="17" height="17" fill="currentColor" class="bi bi-plus"
        viewBox="0 0 16 16">
        <path
          d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
      </svg>
    </button>
    <button type="button" class="btn btn-primary" @click="addMember(1, 1234, 555)"> JSON
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-download"
        viewBox="0 0 16 16">
        <path
          d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z" />
        <path
          d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z" />
      </svg>
    </button>
  </div>
  <hr>

  <table class="table table-hover">
    <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">학번(사번)</th>
        <th scope="col">이름</th>
        <th scope="col"></th>
      </tr>
    </thead>
    <tbody>
      <MemberElementVue :key="i" :member="data" @edit="editModalVisible = true; setNowEditMember(i)"
        v-for="(data, i) in elements"></MemberElementVue>
    </tbody>
  </table>

  <!-- 멤버 추가 모달 -->
  <MemberModal 
  @add="(member)=>{addModalVisible=false; addMember(member);}" 
  @close="addModalVisible=false;"
    v-if="addModalVisible"></MemberModal>
  <!-- ================ -->
  <!-- 멤버 수정 모달 -->
  <MemberEditModal :oldMember="nowEditMember"
  @close="editModalVisible = false;"
  @edit="editMember"
    v-if="editModalVisible"></MemberEditModal>
  <!-- ================ -->

</template>

<script>
import MemberElementVue from './MemberElement.vue';
import MemberModal from './MemberModal.vue';
import MemberEditModal from './MemberEditModal.vue';
import { Member } from './Member';

export default {
  name: 'MemberBox',
  props: {
    msg: String
  },
  components: {
    MemberElementVue, MemberModal, MemberEditModal
  },
  methods: {
    test() { alert('test msg'); },
    addMemberView(member) {
      this.elements.push(member);
    },
    addMember(member) {
      alert(`${member.userId} | ${member.name} | ${member.password} | ${member.role}`);
      //fetch api addMember......
      //////////////////
      fetch('/api/admin/members/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          userId: member.userId,
          name: member.name,
          password: member.password,
          role: member.role
        })
      }).then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          alert('가입실패');
        }
      }).then((data) => {
        /// if success //////
        const memberJoin = data.context.data;
        console.log(memberJoin);
        this.addModalVisible = false;
        this.addMemberView(memberJoin);
      })
    },
    editMember(member) {
      // hide editModal
      this.editModalVisible = false;

      const idx = member.idx;
      alert(`${idx} | ${member.id} | ${member.userId} | ${member.name} | ${member.password}`);
      // 강의 id 리스트로 변환
      let courseIdList = [];
      member.courseList.forEach(course => {
        courseIdList.push(course.content.id);
      });
      //fetch api editMember......
      fetch(`/api/admin/members/${member.id}/update`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: member.name,
          password: member.password,
          courseIdList: courseIdList,
          role: member.role
        })
      }).then((response) => {
        if (response.ok) {
          /// if success //////
          this.editModalVisible = false;
          this.elements[idx].id = member.id;
          this.elements[idx].name = member.name;

          //this.elements[idx].courseIdList = member.courseIdList;
          this.elements[idx].password = member.password;
          this.elements[idx].role = member.role;
        } else {
          alert('업데이트실패');
        }
      })
      /////////

    },
    setNowEditMember(key) {
      this.nowEditMember = this.elements[key];
      this.nowEditMember.idx = key;
    },
    setAddModalVisible(state) { this.addModalVisible = state; },

  },
  data() {
    return {
      elements: [{
        id: 0, name: "길동이", userId: "12224560", password: "12345678", role: "학생", courseList: []
      }],
      addModalVisible: false,
      editModalVisible: false,
      nowEditMember: {
        idx: -1, id: -1, userId: "",
        courseList: [], name: "", password: "", role: "학생"
      },
    };
  },
  beforeCreate() {
    fetch('/api/admin/members', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      if (response.ok)
        return response.json();
    }).then((data) => {
      console.log(data);
      const members = data.context.data;
      //this.maxPage = data.maxPage;

      this.elements = [];
      members.forEach(member => {
        let courseList = member.courseList.map(course => { return { name: course.name, content: course }; });
        this.addMemberView(new Member(member.id, member.userId, member.name, member.password, member.role, courseList));
      });
    })
  }
}

</script>



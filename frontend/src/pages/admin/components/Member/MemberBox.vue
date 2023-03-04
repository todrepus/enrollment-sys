<template>
  <!-- Hover added -->
  <div class="d-flex justify-content-end m-4">
    <button type="button" class="btn btn-primary mx-3" @click="showAddModal"> 멤버추가
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
      <MemberElementVue :key="i" :member="memberList[i]" :editIdx="i"
        v-for="(_, i) in memberList"></MemberElementVue>
    </tbody>
  </table>

  <!-- 멤버 추가 모달 -->
  <MemberModal v-if="addModalVisible"></MemberModal>
  <!-- ================ -->
  <!-- 멤버 수정 모달 -->
  <MemberEditModal 
  v-if="editModalVisible"></MemberEditModal>
  <!-- ================ -->

</template>

<script>
import MemberElementVue from './MemberElement.vue';
import MemberModal from './MemberModal.vue';
import MemberEditModal from './MemberEditModal.vue';
import {useStore} from "vuex";
import {computed } from 'vue';
import {NAMESPACE} from '@/pages/admin/store/modules/member';
import * as actions from '@/pages/admin/store/modules/member/actions';
import * as getters from '@/pages/admin/store/modules/member/getters';


export default {
  name: 'MemberBox',
  setup() {
    const store = useStore();
    let memberList = computed(()=> store.getters[`${NAMESPACE}/${getters.MEMBER_LIST}`]);
    const addModalVisible = computed(()=> store.state.member.view.addModalVisible);
    const editModalVisible = computed(()=> store.state.member.view.editModalVisible);
    const showAddModal = () => store.dispatch(`${NAMESPACE}/${actions.SHOW_ADD_MODAL}`);

    return { memberList, addModalVisible, editModalVisible, showAddModal};
  },
  components: {
    MemberElementVue, MemberModal, MemberEditModal
  },
  beforeCreate() {
    console.debug('beforeCreate');
    this.$store.dispatch(`${NAMESPACE}/${actions.GET_MEMBERS_ON_PAGE}`,0);
  }
}

</script>



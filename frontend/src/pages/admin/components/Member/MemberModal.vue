<template>
    <div tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-scrollable" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">멤버추가</h5>
              <button type="button" class="btn-close" aria-label="Close" @click="visible(false)">
              </button>
            </div>
            <div class="modal-body">
              <div class="mb-3">
                <label for="" class="form-label">학번(사번)</label>
                <input type="text"
                  class="form-control" v-model="userId" aria-describedby="helpId" placeholder="학번을 입력해주세요">
              </div>
              <div class="mb-3">
                <label for="" class="form-label">이름</label>
                <input type="text"
                  class="form-control" v-model="name" aria-describedby="helpId" placeholder="이름을 입력해주세요">
              </div>
              <div class="mb-3">
                <label for="" class="form-label">비밀번호</label>
                <input type="text"
                  class="form-control" v-model="password" aria-describedby="helpId" placeholder="비밀번호를 입력해주세요">
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" @click="addMember(userId, name, password)">추가하기</button>
            </div>
          </div>
        </div>
    </div>
</template>

<script>
export default{
  name : 'MemberModal',
  props: {
    visible: Function,
    addMember: Function,
  },
  data(){
    return {
      userId: "",
      name: "",
      password: "",
    }
  }
}
</script>


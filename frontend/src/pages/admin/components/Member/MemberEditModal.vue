<template>
    <div tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-scrollable" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">수정하기</h5>
                    <button type="button" class="btn-close" aria-label="Close" @click="close">
                    </button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="" class="form-label">id</label>
                        <input disabled type="text" class="form-control" v-model="member.id" aria-describedby="helpId">
                    </div>
                    <div class="mb-3">
                        <label for="" class="form-label">학번(사번)</label>
                        <input readonly type="text" class="form-control" v-model="member.userId"
                            aria-describedby="helpId" placeholder="학번을 입력해주세요">
                    </div>

                    <div class="mb-3" v-if="student">
                        <label for="" class="form-label">학기</label>
                        <input type="text" class="form-control" v-model="member.semester" aria-describedby="helpId"
                            placeholder="학기를 입력해주세요">
                    </div>

                    <div class="mb-3" v-if="professor">
                        <label for="" class="form-label">연차</label>
                        <input type="text" class="form-control" v-model="member.year" aria-describedby="helpId"
                            placeholder="연차를 입력해주세요">
                    </div>

                    <div class="mb-3">
                        <label for="" class="form-label">이름</label>
                        <input type="text" class="form-control" v-model="member.name" aria-describedby="helpId"
                            placeholder="이름을 입력해주세요">
                    </div>
                    <div class="mb-3">
                        <label for="" class="form-label">비밀번호</label>
                        <input type="text" class="form-control" v-model="member.password" aria-describedby="helpId"
                            placeholder="비밀번호를 입력해주세요">
                    </div>

                    <div class="mb-3">
                        <label for="" class="form-label">연락처</label>
                        <input type="text" class="form-control" v-model="member.phoneNumber" aria-describedby="helpId"
                            placeholder="연락처를 입력해주세요">
                    </div>


                    <div class="mb-3">
                        <label for="" class="form-label">학과</label>
                        <SearchBoxVue what="departments"
                            :old-selected="member.department"
                            @update="newValue => member.department = newValue"
                            ></SearchBoxVue>
                    </div>

                    <div class="mb-3" v-if="student || professor">
                        <label for="" class="form-label">강의리스트</label>
                        <SearchBoxVue :key="i" what="courses"
                            :old-selected="course" v-for="(course, i) in member.courseSet"
                            @update="newValue => member.courseSet[i] = newValue"
                            ></SearchBoxVue>
                        <button type="button" class="btn btn-primary mx-3" @click="member.courseSet.push({})">
                            <svg xmlns="http://www.w3.org/2000/svg" width="17" height="17" fill="currentColor"
                                class="bi bi-plus" viewBox="0 0 16 16">
                                <path
                                    d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
                            </svg>
                        </button>
                    </div>


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" @click="edit">수정하기</button>
                    <button type="button" class="btn btn-secondary" @click="close">Close</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import SearchBoxVue from '../SearchBox/SearchBox.vue';
import { useStore } from 'vuex';
import { ref, computed } from 'vue';
import {NAMESPACE} from '@/pages/admin/store/modules/member';
import * as actions from '@/pages/admin/store/modules/member/actions';
import * as getters from '@/pages/admin/store/modules/member/getters';
export default {
    name: 'MemberEditModal',
    components: { SearchBoxVue },
    setup() {
        const store = useStore();
        let member = ref(Object.assign({}, store.getters[`${NAMESPACE}/${getters.EDIT_MEMBER}`]));
        console.debug(member.value);
        const edit = () => store.dispatch(`${NAMESPACE}/${actions.UPDATE_MEMBER}`, member.value);
        const close  = () => store.dispatch(`${NAMESPACE}/${actions.CLOSE_EDIT_MODAL}`);
        const student = computed(()=>member.value.role == "학생");
        const professor = computed(()=>member.value.role == "교수");
        return {member, edit, close, student, professor};
    },
}
</script>


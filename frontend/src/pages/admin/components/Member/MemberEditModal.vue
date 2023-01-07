<template>
    <div tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-scrollable" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">수정하기</h5>
                    <button type="button" class="btn-close" aria-label="Close" @click="visible(false)">
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
                        <label for="" class="form-label">강의리스트</label>
                        <SearchBoxVue :key="i" v-bind:recommendGET="recommendGET" v-model="member.courseList[i]"
                            :old-selected="course" v-for="(course, i) in member.courseList"></SearchBoxVue>
                        <button type="button" class="btn btn-primary mx-3" @click="member.courseList.push({name : '', content : ''})">
                            <svg xmlns="http://www.w3.org/2000/svg" width="17" height="17" fill="currentColor"
                                class="bi bi-plus" viewBox="0 0 16 16">
                                <path
                                    d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" @click="editMember(member.idx, member)">수정하기</button>
                    <button type="button" class="btn btn-secondary" @click="visible(false)">Close</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import SearchBoxVue from '../SearchBox/SearchBox.vue';
export default {
    name: 'MemberEditModal',
    components: { SearchBoxVue },
    data() {
        return {
            member: {
                id: -1,
                userId: "",
                name: "",
                password: "",
                courseList: [],
            }
        }
    },
    methods: {
        recommendGET(words) {
            return fetch(`/api/admin/recommend/courses?content=${words}`, {
                method: 'GET',
            }).then(
                (response) => response.json()
            ).then(
                (data) => {
                    console.log(data)
                    return data.context.courseList.map(course => () => {
                        return { name: course.name, content: course }
                    });
                }
            )

        }
    },
    props: {
        visible: Function,
        editMember: Function,
        oldMember: {
            type: Object,
            default() {
                return {
                    id: -1,
                    userId: "",
                    name: "",
                    password: "",
                }
            }
        }
    },
    mounted() {
        this.member = Object.assign("{}", this.oldMember);
    }
}
</script>


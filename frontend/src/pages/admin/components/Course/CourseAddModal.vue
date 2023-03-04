<template>
    <div tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-scrollable" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addCourseModalLabel">강의 추가</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="close"></button>
                </div>
                <div class="modal-body">

                    <div class="mb-3" style="text-align:start">
                        <label for="userId" class="form-label">강의명</label>
                        <input type="text" class="form-control" v-model="course.name" aria-describedby="helpId"
                            placeholder="강의명을 입력해주세요.">
                    </div>
                    <div class="mb-3" style="text-align:start">
                        <label for="" class="form-label">수강정원</label>
                        <input type="text" class="form-control" v-model="course.maxNum" aria-describedby="helpId"
                            placeholder="최대 수강인원을 입력해주세요.">
                    </div>


                    <div class="mb-3">
                        <label for="" class="form-label">학과</label>
                        <SearchBoxVue what="departments"
                            :old-selected="course.department"
                            @update="newValue => course.department = newValue"
                            ></SearchBoxVue>
                    </div>

                    <div class="mb-3">
                        <label for="" class="form-label">교수</label>
                        <SearchBoxVue what="professors"
                            :old-selected="course.professor"
                            @update="newValue => course.professor = newValue"
                            ></SearchBoxVue>
                    </div>
                    
                    <div class="mb-3">
                        <label for="" class="form-label">강의실</label>
                        <SearchBoxVue what="rooms"
                            :old-selected="course.room"
                            @update="newValue => course.room = newValue"
                            ></SearchBoxVue>
                    </div>

                    <div class="mb-3" style="text-align:start">
                        <label for="name" class="form-label">강의시간</label>
                        <div :key="i" v-for="(schedule, i) in course.scheduleList" class="d-flex justify-content-around mb-3">
                            <div>
                                <input readonly v-model="course.scheduleList[i].day" class="form-control" @click="visibleList[i] = ~visibleList[i]">
                                <ul v-if="visibleList[i]">
                                    <li :key="j" :class="{'dropdown-item' : true, 'active' : data === schedule.day}"
                                     v-for="(data, j) in ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT']" @click="course.scheduleList[i].day = data; visibleList[i]=false;">{{ data }}</li>
                                </ul>
                            </div>
                            <input :class="{'form-control' : true, 'is-invalid' : timeValid(`${schedule.startHour}:${schedule.startMin}`) == false}" v-model="course.scheduleList[i].startHour">
                            <input :class="{'form-control' : true, 'is-invalid' : timeValid(`${schedule.startHour}:${schedule.startMin}`) == false}" v-model="course.scheduleList[i].startMin">
                            <input :class="{'form-control' : true, 'is-invalid' : timeValid(`${schedule.endHour}:${schedule.endMin}`) == false}" v-model="course.scheduleList[i].endHour">
                            <input :class="{'form-control' : true, 'is-invalid' : timeValid(`${schedule.endHour}:${schedule.endMin}`) == false}" v-model="course.scheduleList[i].endMin">
                            <button class="btn btn-primary" @click="course.scheduleList.splice(i, 1); visibleList.splice(i,1)">-</button>
                        </div>
                        <button class="btn btn-primary" @click="addEmptyDate">
                            +
                        </button>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" @click="addCourse">추가</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import SearchBoxVue from '../SearchBox/SearchBox.vue';
import { useStore } from 'vuex';
import {NAMESPACE} from '@/pages/admin/store/modules/course';
import * as actions from '@/pages/admin/store/modules/course/actions';
import { ref } from 'vue';
import {timeValidFunction} from './time';

export default {
    name: 'CourseAddModal',
    setup(){
        const store = useStore();
        let course = ref({scheduleList : []});
        let visibleList = ref([]);
        const addCourse = ()=>{store.dispatch(`${NAMESPACE}/${actions.ADD_COURSE}`, course.value)};
        const close = ()=>store.dispatch(`${NAMESPACE}/${actions.CLOSE_ADD_MODAL}`);
        const timeValid = timeValidFunction;
        return {course, addCourse, visibleList, close, timeValid};
    },
    components: {
        SearchBoxVue
    },
    methods: {
        addEmptyDate(){
            this.course.scheduleList.push({day:'MON', startHour : 0, endHour : 0, startMin : 0, endMin : 0})
            this.visibleList.push(false);
        }
    },
}
</script>


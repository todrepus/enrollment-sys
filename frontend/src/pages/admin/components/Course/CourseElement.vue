<template>
    <!-- Hover added -->
    <tr>
    <td scope="row">{{ course.courseId }}</td>
    <td>{{ course.name }}</td>
    <td>{{ course.department.name }}</td>
    <td>{{ course.professorName }}</td>
    <td>{{ course.room.name }}</td>
    <!-- Hover added -->
    <td><span :key="i" v-for="(date, i) in course.scheduleList">{{ date.day + " " + date.startHour + ':' + date.startMin + '~' + date.endHour + ':' + date.endMin + " "}}</span></td>
    <td class="d-flex justify-content-end mr-5">
        <button type="button" class="btn btn-primary" @click="showEditModal(idx)">수정하기</button>
        <button type="button" class="btn btn-primary mx-3" @click="deleteCourse(course)">삭제하기</button>
    </td>
    </tr>
    

</template>

<script>
import { useStore } from 'vuex';
import {NAMESPACE} from '@/pages/admin/store/modules/course';
import * as actions from '@/pages/admin/store/modules/course/actions';

export default{
    name : 'CourseElement',
    setup(){
        const store = useStore();
        const showEditModal = (idx)=>{store.dispatch(`${NAMESPACE}/${actions.SHOW_EDIT_MODAL}`, idx)};
        const deleteCourse = (c) => {store.dispatch(`${NAMESPACE}/${actions.DELETE_COURSE}`,c)};
        return {showEditModal, deleteCourse}
    },
    props : {
        course: Object,
        idx: Number,
    },
    methods: {}

}
</script>

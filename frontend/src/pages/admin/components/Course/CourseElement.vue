<template>
    <!-- Hover added -->
    <tr>
    <td scope="row">{{ course.id }}</td>
    <td>{{ course.code }}</td>
    <td>{{ course.name }}</td>
    <td>{{ course.department }}</td>
    <td>{{ course.profName }}</td>
    <td>{{ course.roomName }}</td>
    <!-- Hover added -->
    <td><span :key="i" v-for="(date, i) in course.dates">{{ date.date + date.start + '~' + date.end + ' '}}</span></td>
    <td class="d-flex justify-content-end mr-5">
        <button type="button" class="btn btn-primary" @click="editInit(idx);visible(true);">수정하기</button>
        <button type="button" class="btn btn-primary mx-3">삭제하기</button>
    </td>
    </tr>
    

</template>

<script>
export default{
    name : 'CourseElement',
    data (){
        return {

        };
    },
    props : {
        course: Object,
        idx: Number,
        visible: Function,
        editInit: Function,
    }

}
</script>

<template>
    <div tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-scrollable" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addCourseModalLabel">강의 추가</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="visible(false)"></button>
                </div>
                <div class="modal-body">

                    <div class="mb-3" style="text-align:start">
                        <label for="userId" class="form-label">강의코드</label>
                        <input readonly type="text" class="form-control" v-model="course.code"  aria-describedby="helpId"
                            placeholder="">
                    </div>
                    <div class="mb-3" style="text-align:start">
                        <label for="userId" class="form-label">강의명</label>
                        <input type="text" class="form-control" v-model="course.name" aria-describedby="helpId"
                            placeholder="강의명을 입력해주세요.">
                        <!-- Hover added -->
                        <div class="list-group mx-1 mt-1" id='courselist' v-if="codeListView">
                            <button type="button" class="list-group-item list-group-item-action"
                                aria-current="true">Active item</button>
                            <button type="button" class="list-group-item list-group-item-action">Item</button>
                            <button type="button" class="list-group-item list-group-item-action" disabled>Disabled
                                item</button>
                        </div>
                    </div>

                    <div class="mb-3" style="text-align:start">
                        <label for="userId" class="form-label">학과명</label>
                        <input type="text" class="form-control" v-model="course.department"  aria-describedby="helpId"
                            placeholder="학과명을 입력해주세요">
                    </div>

                    <div class="mb-3" style="text-align:start">
                        <label for="name" class="form-label">교수</label>
                        <input hidden type="text" v-model="course.profId">
                        <input type="text" class="form-control" v-model="course.profName" aria-describedby="helpId"
                            placeholder="강의 교수명을 입력해주세요.">
                    </div>

                    <div class="mb-3" style="text-align:start">
                        <label for="name" class="form-label">강의실</label>
                        <input hidden type="text" v-model="course.roomId">
                        <input type="text" class="form-control" v-model="course.roomName" aria-describedby="helpId"
                            placeholder="강의실을 입력해주세요.">
                    </div>

                    <div class="mb-3" style="text-align:start">
                        <label for="name" class="form-label">강의시간</label>
                        <div :key="i" v-for="(dateElement, i) in course.dates" class="d-flex justify-content-around mb-3">
                            <div>
                                <input readonly v-model="course.dates[i].date" class="form-control" @click="course.dates[i].visible = ~course.dates[i].visible">
                                <ul v-if="dateElement.visible">
                                    <li :key="j" :class="{'dropdown-item' : true, 'active' : data === dateElement.date}"
                                     v-for="(data, j) in ['월', '화', '수', '목', '금', '토', '일']" @click="course.dates[i].date = data; course.dates[i].visible=false;">{{ data }}</li>
                                </ul>
                            </div>
                            <input :class="{'form-control' : true, 'is-invalid' : timeValid(course.dates[i].start) == false}" v-model="course.dates[i].start">
                            <input :class="{'form-control' : true, 'is-invalid' : timeValid(course.dates[i].end) == false}" v-model="course.dates[i].end">
                            <button class="btn btn-primary" @click="course.dates.splice(i, 1)">-</button>
                        </div>
                        <button class="btn btn-primary" @click="addEmptyDate">
                            +
                        </button>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" @click="addCourse(course)">추가</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'CourseAddModal',
    data() {
        return {
            course: {
                code : '', name : '', profName : '', profId:'', roomName : '', roomId:'', department: '',
                dates:[{date:'월', start:'15:00', end:'17:00', visible:false}, {date:'수', start:'17:00', end:'19:00', visible:false}],
                codeListView : false, 
            }
        }
    },
    methods: {
        timeValid(timeInput){
            let result = timeInput.match('(\\d{2}):(\\d{2})');
            if (result == null){
                return false;
            }
            if (timeInput !== result[0]){
                return false;
            }
            const hour = result[1] * 1;
            const min = result[2] * 1;

            if (hour < 0 || hour > 24){
                return false;
            }

            if (min < 0 || min > 60){
                return false;
            }

            return true;
        },
        addEmptyDate(){
            this.course.dates.push({date:'월', start:'', end:'', visible:false})
        }
    },
    props: {
        visible: Function,
        addCourse: Function,
    }
}
</script>


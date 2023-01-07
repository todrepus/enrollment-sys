<template>
    <!-- Hover added -->
    <tr>
    <td scope="row">{{ member.id }}</td>
    <td>{{ member.userId }}</td>
    <td>{{ member.name }}</td>
    <td class="d-flex justify-content-end mr-5">
        <button type="button" class="btn btn-primary" @click="editVisible(true); editInit(idx)">수정하기</button>
        <button type="button" class="btn btn-primary mx-3">삭제하기</button>
    </td>
    </tr>
    

</template>

<script>
export default{
    name : 'MemberElement',
    data (){
        return {
        };
    },
    props : {
        member : {
            type: Object,
            default() {
                return {
                    id : -1,
                    userId : "123478",
                    name : "길동이1234",
                    password: "123456",
                };
            }
        },
        editVisible: Function,
        editInit: Function,
        idx: Number,
    }

}
</script>

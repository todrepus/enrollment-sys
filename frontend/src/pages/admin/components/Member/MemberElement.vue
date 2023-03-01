<template>
    <!-- Hover added -->
    <tr>
    <td scope="row">{{ member.id }}</td>
    <td>{{ member.userId }}</td>
    <td>{{ member.name }}</td>
    <td class="d-flex justify-content-end mr-5">
        <button type="button" class="btn btn-primary" @click="showEditModal(editIdx)">수정하기</button>
        <button type="button" class="btn btn-primary mx-3">삭제하기</button>
    </td>
    </tr>
    

</template>

<script>
import * as MuType from '../../store/modules/mutation-types';
import { useStore } from 'vuex';
export default{
    name : 'MemberElement',
    setup(){
        const store = useStore();
        const showEditModal = (idx) => store.commit(MuType.SHOW_EDIT_MODAL, idx);  
        return {showEditModal};
    },
    props : ['member', 'editIdx']

}
</script>

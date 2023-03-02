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
import { useStore } from 'vuex';
import {NAMESPACE} from '@/pages/admin/store/modules/member';
import * as actions from '@/pages/admin/store/modules/member/actions';
import * as getters from '@/pages/admin/store/modules/member/getters';
export default{
    name : 'MemberElement',
    setup(){
        const store = useStore();
        //const member = computed(() => store.getters.member(this.editIdx));
        const showEditModal = (idx) => store.dispatch(`${NAMESPACE}/${actions.SHOW_EDIT_MODAL}`, idx);  
        return {showEditModal};
    },
    props : ['editIdx'],
    computed: {
        member(){
            return this.$store.getters[`${NAMESPACE}/${getters.MEMBER}`](this.editIdx);
        }
    },

}
</script>

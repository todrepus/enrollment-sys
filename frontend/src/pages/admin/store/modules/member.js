/* eslint-disable */
import * as MuType from './mutation-types'

/// 모듈

export const member = {
    state: () => ({
        members: [],
        addModalVisible : false,
        editModalVisible : false,
        editIdx : 0,
        maxPage: 0
    }),
    mutations: {
        [MuType.CLEAR_MEMBERS] (state) {
            state.members = []
        },
        [MuType.ADD_MEMBER] (state, responseData) {
            state.members.push(responseData);
        },

        [MuType.SET_MAX_PAGE] (state, maxPage){
          state.maxPage = maxPage;
        },
        [MuType.SHOW_ADD_MODAL] (state){
          state.addModalVisible = true;
        },
        [MuType.SHOW_EDIT_MODAL] (state, editIdx){
          state.editModalVisible = true;
          state.editIdx = editIdx;
        },
        [MuType.CLOSE_ADD_MODAL] (state){
          state.addModalVisible = false;
        },
        [MuType.CLOSE_EDIT_MODAL] (state){
          state.editModalVisible = false;
        },
        [MuType.SET_MEMBER] (state, idx, member) {
          state.members[idx] = member;
        }
    },
    actions: {
        async [MuType.GET_MEMBERS] ({commit, state}, page) {
            fetch(`/api/admin/members?page=${page}`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json',
                },
              }).then((response) => {
                if (response.ok)
                  return response.json();
              }).then((data) => {
                commit(MuType.CLEAR_MEMBERS);
                const members = data.data;
                commit(MuType.SET_MAX_PAGE, data.context.maxPage);
                members.forEach(member => commit(MuType.ADD_MEMBER, member));
              })
        },
        async [MuType.ADD_MEMBER] ({commit, state}, member) {
          console.debug(member);
          fetch('/api/admin/members/add', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              userId: member.userId,
              name: member.name,
              phoneNumber : member.phoneNumber,
              password: member.password,
              role: member.role
            })
          }).then((response) => {
            if (response.ok) {
              return response.json().data;
            } else {
              alert('가입실패');
            }
          }).then((memberJoin) => {
            /// if success //////
            console.debug(memberJoin);
            commit(MuType.CLOSE_ADD_MODAL);
            commit(MuType.ADD_MEMBER, memberJoin);
          })
        },

        async [MuType.EDIT_STUDENT] ({commit, state} , student) {
            const targetIdx = state.editIdx;
            console.debug(student);
            //fetch api editMember......
            fetch(`/api/admin/members/${student.id}/update`, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify({
                name: student.name,
                password: student.password,
                phoneNumber: student.phoneNumber,
                role: student.role,
                semester: student.semester,
                departmentId: student.departmentId,
                state: student.state,
                enrollList: student.enrollList
              })
            }).then((response) => {
              if (response.ok) {
                return response.json().data;
              } else {
                alert('업데이트실패');
              }
            }).then((updated) =>{
              console.debug(updated);
              commit(MuType.CLOSE_EDIT_MODAL);
              commit(MuType.SET_MEMBER, targetIdx, student);
            });
        }
    },

    getters: {
      memberList (state) {
        return state.members;
      },addModalVisible (state){
        return state.addModalVisible;
      },editModalVisible (state){
        return state.editModalVisible;
      },editMember (state) {
        return state.members[state.editIdx];
      }
    }
}
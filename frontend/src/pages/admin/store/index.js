import Vuex from 'vuex';
import member from './modules/member';
import course from './modules/course';
import room from './modules/room';
import department from './modules/department';
import * as http from '@/pages/http';

export const GET_LOGIN_MEMBER = 'get_login_member'
export const SET_LOGIN_MEMBER = 'set_login_member'

export const store = new Vuex.Store({
    state: {
        loginMember : {}
    },
    mutations: {
        [SET_LOGIN_MEMBER](state, member) {
            state.loginMember = member;
        }
    },
    actions: {
        async [GET_LOGIN_MEMBER]({commit}){
            let member = await http.getLoginMember();
            console.debug(member);
            commit(SET_LOGIN_MEMBER, member);
        }
    },
    modules: {
        member: member,
        course : course,
        room : room,
        department : department
    }
}
)
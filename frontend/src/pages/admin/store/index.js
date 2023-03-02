import Vuex from 'vuex';
import member from './modules/member';


export const store = new Vuex.Store({
    modules: {
        member: member
    }
}
)
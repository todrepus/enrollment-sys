<!-- v-model로 selceted에 저장된 값을 받아보실 수 있습니다. -->
<!-- data의 구조는 {name : '리스트에 보여지는 이름', content : '실제 요소 값 '} 으로 이루어져있습니다. -->
<!-- 추천 method를 props를 통해 제공해주세요. 항상 반환값은 [{name : null, content : null}, ....] 의 구조여야 합니다. -->

<template>
    <div class="mb-3">
        <input class="form-control" v-model="content" placeholder="검색어를 입력해주세요.">
        <!-- 검색어 추천 리스트 -->
        <div class="recommend-box" v-if="recommend">
            <!-- Hover added -->
            <div class="list-group">
                <button :key="i" type="button" class="list-group-item list-group-item-action" 
                v-for="(data, i) in recommend_list"
                @click="handler(data)"> {{ data.name }} </button>
            </div>
        </div>
    </div>
</template>


<script>
export default {
    name : 'SearchBox',
    watch : {
        content : async function (value, oldValue) {
            if (value === oldValue)
                return;
            const ct = value.trim();
            this.recommend = ct !== "";

            if (this.recommend){
                this.recommend_list = await this.recommendGET(ct);
            }else{
                this.recommend_list = [];
            }
        }
    },
    data() {
        return {
            content : "",
            recommend : false,
            recommend_list : [{name : '리스트에 보여질 이름입니다', content : '해당 요소가 가지고 있는 값'},],
            selected : null,
        }
    },
    methods : {
        handler(data){
            this.selected = data;
            this.recommend = false;
            this.$emit('content', data); // v-model 적용
        }
    },
    props: {
        recommendGET : {
            type: Function,
            required: true
        },
        oldSelected: Object,
    },

    mounted() {
        this.selected = Object.assign(this.oldSelected);
        this.content = this.selected.name;
    }
}
</script>



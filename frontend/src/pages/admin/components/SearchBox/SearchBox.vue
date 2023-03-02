<!-- emit event name is "update" -->

<!-- Example In Component.......-->
<!-- <Searchbox @update="newValue => yourParam=newValue"></SearchBox> -->

<template>
    <div class="mb-3" @focusout="handleFocusOut">
        <input class="form-control" v-model="content" placeholder="검색어를 입력해주세요.">
        <!-- 검색어 추천 리스트 -->
        <div class="recommend-box" v-if="recommend">
            <!-- Hover added -->
            <div class="list-group">
                <button :key="i" type="button" class="list-group-item list-group-item-action" 
                v-for="(data, i) in recommend_list"
                @mousedown="handler(data)"> {{ data.name }} </button>
            </div>
        </div>
    </div>
</template>


<script>
export default {
    name : 'SearchBox',
    watch : {
        content : async function (value, oldValue) {
            if (this.watchIgnore){
                this.watchIgnore = false;
                return;
            }
            if (value === oldValue)
                return;
            if (value == null)
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
            watchIgnore : true,
            focusOutIgnore : false,
            lastName : ""
        }
    },
    methods : {
        handler(data){
            this.watchIgnore = true;
            this.focusOutIgnore = true;
            this.selected = data;
            this.recommend = false;
            this.content = data.name;
            this.lastName = this.content;
            console.debug(`data is`);
            console.debug(data);
            this.$emit('update', data.content); // v-model 적용
        },
        async recommendGET(content){
            return fetch(`/api/admin/recommend/${this.what}?content=${content}`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json',
                },
            }).then((response) => {
                if (response.ok)
                    return response.json();
            }).then((data) => {
                let recommends = data.data;
                console.debug("recommends");
                console.debug(recommends);
                return recommends.map((r)=>{return {name : r.name, content : r}});
            })
        },
        handleFocusOut(){
            console.debug('handleFocusOut');
            if (this.focusOutIgnore){
                console.debug('focusout ignored');
                this.focusOutIgnore = false;
                return;
            }
            this.watchIgnore = true;
            this.recommend = false;
            this.content = this.lastName;
        }
    },
    props: {
        what: String,
        oldSelected: Object,
    },

    mounted() {
        if (this.oldSelected != null){
            console.debug(this.oldSelected);
            this.selected = Object.assign(this.oldSelected);
            this.content = this.selected.name;
            this.lastName = this.content;
        }

        this.recommend_list = [];
    }
}
</script>



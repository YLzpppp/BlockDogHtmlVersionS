<template>
    <div class="container" :style="{marginTop: containermt+'px'}">
        <div class="outline"
             :style="{
         height: inputh+'px'}"
        >
            <div style="height: 100%;width: 94%;margin: 0 3%;">
                <div style="width: 100%;height: 49%">
                    <span style="display: inline-block;color:#323234;line-height: 100%;height: 100%;width:10%">手機</span>
                    <input class="iphone" type="text" placeholder="手機號碼" v-model="acc">
                </div>
                <div style="height: 0.9%;width: 100%;background-color: rgba(100,100,100,0.5)"></div>
                <div style="width: 100%;height: 49%">
                    <span style="display: inline-block;color:#323234;line-height: 100%;height: 100%;width:10%">密碼</span>
                    <input class="ipass" type="password" placeholder="請輸入密碼" v-model="pwd">
                </div>

            </div>
            <button @click="dologin" class="loginbtn" :style="{height:singleinph+'px',marginTop: buttonmt+'px'}">登入</button>
            <div class="clearfix" :style="{marginTop:regfindh+'px'}">
                <a href="javascript:;" class="ia reg">掃一掃註冊</a>
                <a href="javascript:;" class="ia find">找回密碼</a>
            </div>
        </div>
    </div>

</template>

<script>
    import VueResource from 'vue-resource';
    import Vue from 'vue';
    Vue.use(VueResource);

    export default {
        name: "login",
        mounted(){
            console.log(this.inputh);
        },
        data: function () {
            return {
                acc:'',
                pwd:'',
            }
        },
        computed: {
            inputh : function () {
                var h = Math.round(window.innerWidth * 0.118)*2;
                return h;
            },
            singleinph : ()=>{
                return Math.round(window.innerWidth * 0.104);
            },
            containermt : ()=>{
                return Math.round(window.innerWidth * 0.10);
            },
            buttonmt : ()=>{
                return Math.round(window.innerWidth * 0.04);
            },
            regfindh : ()=>{
                return Math.round(window.innerWidth * 0.04);
            }
        },
        methods: {
            dologin(){
                var account = this.acc;
                var password = this.pwd;
                var ul = "http://www.lzppp.cn/WebApp/Loginc";
                this.$http.get(ul,{params:{account:account,password:password}}).then(
                    response=>{
                        var responsetext = response.bodyText;
                        switch (responsetext) {
                            case 'accnotexist':
                                window.alert('账号不存在');
                                break;
                            case 'pwdwrong':
                                window.alert('密碼错误');
                                break;
                            case 'success':
                                window.open("./dogmarket.html","_self");
                                break;
                        }
                        // "http://www.lzppp.cn/WebApp/dogmarket.html"
                    },
                    response=>{
                        window.alert('未知错误');
                    }
                )
            }
        }

    }
</script>

<style scoped>
    .clearfix::before{
        content: '';
        display: block;
        clear: both;
        overflow: hidden;
    }
    .ia{
        text-decoration: none;
        color: #ffffff;
    }
    .reg{
        float: left;
    }
    .find{
        float: right;
    }
    input {
        display: inline-block;
        margin: 0;
        padding: 0;
    }
    input::-webkit-input-placeholder{
        font-size: 1rem;
        color: #767676;
    }
    input::-ms-input-placeholder{
        font-size: 1rem;
        color: #767676;
    }
    .container{
        width: 75%;
        margin: 0 12.5%;
    }
    .outline{
        width: 100%;
        background-color: white;
        border-radius: 7px;
    }
    .iphone{
        border: none;
        height: 100%;
        width: 88%;
    }
    .ipass {
        border: none;
        height: 100%;
        width: 88%;
    }
    .loginbtn{
        background-color: #46b39c;
        border-radius: 7px;
        border: none;
        width:100%;
        color: #ffffff;
        font-size: 1.5rem;
    }
</style>
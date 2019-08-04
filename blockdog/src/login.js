import Vue from 'vue';


import './index.css';
import login from './login.vue';

var vm = new Vue({
    el:'#app',
    data: {
        msg:'llo'
    },
    components: {
        login,
    }
})


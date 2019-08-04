import Vue from "vue";
import dogcard from "./dogcard.vue";
import "./dogmarket.css";
import VueResource from "vue-resource";

Vue.use(VueResource);
// Vue.http.options.emulateJSON = true;
Vue.http.options.crossOrigin = true;

var vm2 = new Vue({

    mounted(){

    },
    el:'#app2',
    data: {
        msg:'app2',
        isActive : false,
        isFlipx : false,
        isFlipy : false,
        isDone : true,
        isOne : true,
        dogetdog: false,
        datalists: [
            {
                "kind":"./img/dogone.png",
                "title":"卓登",
                "level":"卓越",
                "value":"901-2500",
                "adopttime":"14:00-15:00",
                "microp":"10/20",
                "profit":"5天/12.5%",
                "wianum":"1.2",
                "dogp":"15"
            },
            {
                "kind":"./img/dogtwo.png",
                "title":"普登",
                "level":"普通",
                "value":"100-300",
                "adopttime":"15:00-16:00",
                "microp":"2/4",
                "profit":"7天/21%",
                "wianum":"0.3",
                "dogp":"30"
            },
            {
                "kind":"./img/dogthree.png",
                "title":"勇登",
                "level":"勇者",
                "value":"301-900",
                "adopttime":"16:30-17:30",
                "microp":"4/8",
                "profit":"3天/9%",
                "wianum":"0.6",
                "dogp":"15"
            },
            {
                "kind":"./img/dogfour.png",
                "title":"承登",
                "level":"傳承金璽",
                "value":"6001-15000",
                "adopttime":"17:00-18:00",
                "microp":"25/50",
                "profit":"20天/40%",
                "wianum":"4",
                "dogp":"5"
            },
            {
                "kind":"./img/dogone.png",
                "title":"卓登",
                "level":"卓越玉璽",
                "value":"901-2500",
                "adopttime":"17:30-18:30",
                "microp":"11/22",
                "profit":"12天/21%",
                "wianum":"1.5",
                "dogp":"18"
            },
            {
                "kind":"./img/dogthree.png",
                "title":"勇登",
                "level":"勇者",
                "value":"301-900",
                "adopttime":"19:30-20:30",
                "microp":"6/12",
                "profit":"5天/15%",
                "wianum":"1",
                "dogp":"20"
            },
            {
                "kind":"./img/dogone.png",
                "title":"卓登",
                "level":"卓越",
                "value":"901-2500",
                "adopttime":"20:00-21:00",
                "microp":"7/14",
                "profit":"1天/5%",
                "wianum":"1.4",
                "dogp":"10"
            },
            {
                "kind":"./img/dogfive.png",
                "title":"稀登",
                "level":"稀有",
                "value":"2500-6000",
                "adopttime":"20:30-21:30",
                "microp":"15/30",
                "profit":"7天/14%",
                "wianum":"2",
                "dogp":"8"
            },
            {
                "kind":"./img/dogfive.png",
                "title":"稀登",
                "level":"稀有",
                "value":"2500-6000",
                "adopttime":"21:00-22:00",
                "microp":"20/40",
                "profit":"15天/33%",
                "wianum":"2.2",
                "dogp":"8"
            }
        ],
    },
    computed: {
        logomt:()=>{
            return window.innerWidth*0.043;
        },
        flipboxh:()=>{
            return window.innerWidth*0.6;
        }
    },
    methods:{
        doflip(){
            console.log('flipping now')
            this.isActive = true;
            this.isFlipx = true;
        },
        sayhi(){
            console.log('hi')
        },
        reget(){
            getdog();
        },
        igetdog(){
            this.isActive = false;
            this.dogetdog = false;
            this.isOne = true;
            this.isDone = true;
            this.isFlipx = true;
            this.isFlipy = false;
        }
        ,
        getdog(){
            this.$http.get('http://www.lzppp.cn/WebApp/Getdogc').then((response)=>{
                var resm = response.bodyText;
                if(resm != 'yes'){
                    console.log('服务端返回处理结果信息 : '+resm);
                    if(resm === 'no'){
                        //表示已经没有狗了，取消抢狗
                        this.dogetdog = false;
                        this.isDone = false;
                    }else{
                        var num = Number.parseInt(resm)*1000;
                        console.log(num);
                        setTimeout(this.getdog, num)
                    }
                }else{
                    this.dogetdog = true;
                    //传回抢到，设置isDone 为false,后续操作在 animationfunc()函数中进行
                    this.isDone = false;

                }
            },(err)=>{
                alert('Err : 未知错误');
            })
        }
        ,
        animationfunc(e){
            console.log("animation")
            e.addEventListener('animationend',()=>{
                if(this.isDone){
                    switch (e.className) {
                        case 'flipcx':
                            this.isFlipx = false;
                            this.isFlipy = true;
                            break;
                        case 'flipcy':
                            this.isFlipy = false;
                            this.isFlipx = true;
                            break;
                    }
                    //使用vue-resource在这里发起请求，请求服务器处理该次get请求
                    if(this.isOne){
                        //通过标识判断是否为首次点领养，是首次就发起请求，并将其设为false
                        this.getdog();
                        this.isOne = false;
                    }

                }else{
                    //通过接收服务端发来的结果对isDone进行了修改为 false，则表示请求处理完成了
                    // 判断 dogetdog 的真假，对结果进行处理
                    if(this.dogetdog){
                        //为true ，表示抢到狗，显示抢到狗的提示信息.由于绑定了v-if
                        //抢到了就会触发v-if ,直接显示，而这里这需要将flipbox隐藏就行了
                        //通过将flipbox也绑定v-if ,且值与抢到狗提示信息的绑定值相对立，因此
                        //不要设置试图。通过抢狗信息绑定点击事件，来取消遮罩

                    }else{
                        //为false，表示没有抢到狗，取消遮罩抢狗界面，设置isActive为false, isFlipx为true,isFlipy为false
                        this.isActive = false;
                        this.isFlipx = true;
                        this.isFlipy = false;
                        this.isDone = true;
                        this.isOne = true;
                    }
                }

            })
        }
    },
    directives:{
      animationend:{
          inserted: function (el,binding) {
              binding.value.animationfunc(el);
          }
      }
    },
    components: {
        dogcard,
    }
})


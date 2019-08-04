<template>
    <div :style="{
    width: '48.75%',
    height: cardh+'px',
    display: 'inline-block',
    marginTop: marg+'px',
    backgroundColor: '#3b3e46',
    borderRadius: '5px',
    paddingBottom: '1rem',
    position: 'relative'
    }">
        <div class="dogpiccontainer">
            <img :src="doginfo.kind" alt="lost">
        </div>
        <div class="infodesc">
            <p><span style="color: #5dd1ba;font-size: 1.35em">{{doginfo.title}}</span>&nbsp;&nbsp;&nbsp;级别：<span style="color: white">{{doginfo.level}}</span></p>
            <p>價值：<span>{{doginfo.value}}</span></p>
            <p>领养時間：<span>{{doginfo.adopttime}}</span></p>
            <p>預約/即抢领养微分：<span>{{doginfo.microp}}</span></p>
            <p>智能合約收益：<span>{{doginfo.profit}}</span></p>
            <p>可挖WIA：<span>{{doginfo.wianum}}</span>枚</p>
            <p>可挖DOGE：收益<span>{{doginfo.dogp}}</span>%</p>
        </div>
        <p>&nbsp;</p>
        <transition>
            <button v-if="btnstatus === 0 " class="btncommon breed">繁殖中</button>
            <button v-else-if="btnstatus === 1" class="btncommon toadopt">待领养</button>
            <button v-else-if="btnstatus === 2" class="btncommon tremain">剩余 {{tcount}}s</button>
            <button v-else-if="btnstatus === 3" class="btncommon adopt" @click="$emit('flipnow')">领养</button>
            <button v-else  class="btncommon appointment" @click="appoinsuc">預約</button>
        </transition>
    </div>
</template>

<script>
    import {hi ,gettime,remainSec} from "./timeutil";

    export default {
        mounted(){
            /*
             * 拿到prop数据中的adopttime值，并分解出领养的时和分
             * 调用remainSec()函数，获取当前时间距离领养时间的 秒数
             * 0：超过adopttime5分钟，btnstatus 为 0
             * 1：早于adopttime 半个小时以内显示为待领养，设置btnstatus 为  1
             * 2：早于adopttime 1分30秒以内，设置btnstatus 为 2
             * 3：和adopttime相等，且5分钟内，设置btnstatus 为 3
             * 4：早于adopttime 半小时以上， 设置btnstatus 为 负数(显示預約)
             */
            var adoptt = this.doginfo.adopttime;
            /*
             * 调用 methods 里的triggerUp 函数进行更新数据
             */
            var intervalid = setInterval(()=>{
                var t = gettime(adoptt);
                var dif = remainSec(t);
                /*
                 *  tar - Date.now();
                 *
                 *  dif 为负数，当前时间 大于 领养时间
                 */
                console.log(dif);
                if( dif <= -300 ){
                    //超过adopttime5分钟（ 300s ），btnstatus 为 0 （繁殖中）
                    this.btnstatus = 0;
                }else if(-300 < dif && dif <= 0) {
                    //超过时间5分钟以内，btnstatus 为 1  （领养）
                    this.btnstatus = 3;
                }else if(dif >0 && dif <= 90){
                        //早于90秒以内，显示剩余，并动态更新数据
                        //btnstatus 要设为 2
                    this.btnstatus = 2;
                    this.tcount = dif;

                }else if(dif > 0 && dif < 1800) {
                    // 早于adopttime 半个小时 （）以内 显示待领养
                    this.btnstatus = 1;
                } else if(dif >= 1800){
                    // 早于adopttime 半个小时（）以上 显示預約
                    this.btnstatus = -1;
                }
            },1000)

            // setTimeout(()=>{
            //     clearInterval(intervalid)
            // },5000)

        },
        name: "dogcard",
        props:['doginfo'],
        data: function () {
            return {
                btnstatus : 0,
                tcount : 90
            }
        },
        methods: {
            appoinsuc(){
                window.alert("預約成功");
            }
        }
        ,
        computed: {
            cardw : ()=>{
                return 0.453*window.innerWidth;
            },
            cardh : ()=>{
                return 0.83*window.innerWidth;
            },
            marg : ()=>{
                return window.innerWidth*0.025;
            },
            dogmt: ()=>{
                return window.innerWidth*0.03;
            },
        }

    }
</script>

<style scoped>

    .dogpiccontainer{
        width:60%;
        margin-left: 20%;
        margin-right: 20%;
        margin-top: 3%;
        margin-bottom: 5%;
    }
    .dogpiccontainer img{
        object-fit: contain;
        object-position: center;
        width: 100%;
        display: block;
    }
    .infodesc{
        width: 88%;
        margin: 0 0 0 6%;
        font-size: 14px;
    }
    .infodesc p{
        display: block;
        color: #c4c4c4;
    }
    .infodesc p span{
        color: #ffffff;
    }
    .btncommon{
        width: 72.5%;
        margin: 0 13.75%;
        border-radius: 20px;
        border: none;
        height: 9%;
        font-size: 1.25rem;
        color:#ffffff;
    }
    .breed{
        background-color: #898989;
    }
    .toadopt{
        background-color: #4d8aa9;
    }
    .appointment{
        background-color: #46b39c;
    }
    .tremain{
        background-color: #9e0000;
    }
    .adopt{
        background-color: #ff7449;
    }
</style>

<!--繁殖中(bg:#898989 ,color:#fff) 待领养(bg:#4d8aa9 color:#fff) 預約 (bg:#46b39c)-->
<!--超过时间的状态为 繁殖中 ，未到时间前一个小时的状态为 待领养 ， 超过一个小时的为預約-->
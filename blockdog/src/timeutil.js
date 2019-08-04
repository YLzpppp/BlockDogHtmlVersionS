
function gettime(timestr){
    var D = new Date();
    var [y,m,d] = [D.getFullYear(),D.getMonth(),D.getDate()];
    var [doghour,dogminute] = timestr.split('-')[0].split(':');
    var thetime = new Date(y,m,d,doghour,dogminute);
    return Date.parse(thetime.toString());
}

function remainSec(tar) {
    var ms =  tar - Date.now();
    if(ms >= 0){
        return Math.floor(ms/1000);
    }else{
        return Math.ceil(ms/1000);
    }

}

function hi() {
    console.log('hi')
}
// export default time;

export { gettime,remainSec,hi };
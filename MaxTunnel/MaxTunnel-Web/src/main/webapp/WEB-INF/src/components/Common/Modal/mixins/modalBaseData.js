export default {
    props: {
        show: { //是否显示弹框
            type: Object,
        },
        width:{ //弹框宽度
            type:Number,
            default:300
        },
        tilteProps:{
            type: Object,
            default: ()=>{
                return {
                    title:'',
                    backgroundColor:'#fff',
                    titleColor:'#17233d',
                }
            }
        }
    },
    methods:{
        getDOMElement(className){
            return document.getElementsByClassName(className)[0];
        },
    }
}
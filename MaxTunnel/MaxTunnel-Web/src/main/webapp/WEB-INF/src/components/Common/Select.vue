<template>
    <div class="vm-select" :style="styles">
        <select
                class="cd-select"
                :id="id"
        ></select>
    </div>
</template>

<style>
    .cd-dropdown, .cd-select {
        width: 100%;
    }
    .vm-select {
        position: absolute;
        top: 15%;
        left: 2%;
        width: 13%;
        display: block;
        z-index: 1003;
    }
</style>

<script>

    export default {
        props:{
            id: {
                required: true
            },
            optionList:{
                type: Array,
            },
            selectStyle:{
                type: Object
            }
        },
        watch:{
            optionList(){
                let _this = this;

                _this.addOption();

                $('#'+ _this.id).dropdown( {
                    gutter : 5,
                    stack : false,
                    slidingIn : 100,
                    onOptionSelect(opt){
                        _this.$emit('getSelectVal', opt.attr('data-value') );
                    }
                } )
            }
        },
        data() {
            return {

            }
        },
        computed:{
            styles(){
                let style = {};
                let { selectStyle } = this;

                return Object.assign( style,selectStyle );
            }
        },
        methods:{
            addOption(){
                let { optionList,id } = this;
                let $dropdown = $('#'+ id);
                let optlist = '';

                for(let i=0 ; i<optionList.length ; i++){
                    optlist += `<Option value="${ optionList[i].value }" >${ optionList[i].label }</Option>`
                }

                $dropdown.empty().append( optlist );
                $dropdown.children('option').eq(0).attr('selected','selected');//设置第一个为默认选中项

            }
        }
    }

</script>
    
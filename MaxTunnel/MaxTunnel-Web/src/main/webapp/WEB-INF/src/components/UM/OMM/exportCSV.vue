<template>
    <a ref="link" class="download" @click="handleExport" href="#">批量导出</a>
</template>
<script>
export default {
    props: {
        header: {
            type: Array,
            default: function(){
                return []
            }
        },
        data: {
            type: Array,
            default: function(){
                return []
            }    
        },
        fileName: {
            type: String,
            default: 'data.scv'
        }
    },
    computed: {
        headerLabel: function(){
            let result;
            result = this.header.map((item) => {
                return item.label
            })
            result = result.join(',')
            return result
        },
        headerProp: function(){
            let result;
            result = this.header.map((item) => {
                return item.prop
            })
            return result
        }
    },
    methods: {
        handleExport(){
            if(!this.data || this.data.length<=0){
                this.$Message.error("没有要导出的数据")
                return
            }
            var csvContent = this.headerLabel + '\n'
            this.data.forEach((item, index) => {
                let dataString = ''
                for(let i = 0; i< this.headerProp.length; i++){
                    if(item[this.headerProp[i]]==null||item[this.headerProp[i]]==undefined){
                        item[this.headerProp[i]] = ''
                    }
                    dataString += item[this.headerProp[i]] + ','
                }
                csvContent += index < this.data.length ? dataString.replace(/,$/,'\n') : dataString.replace(/,$/, '')
            })
            this.$refs.link.setAttribute('href', 'data:text/csv;charset=utf-8,\ufeff'+encodeURIComponent(csvContent));
            this.$refs.link.setAttribute('download', this.fileName+'.csv')
        }
    }    
}
</script>
<style scoped>
.download{
    color: #fff;
    border: 1px solid #5600ff;
    background: linear-gradient(to left, #b327e1, #9b81ce);
    padding: 0.8vmin 1.5vmin;
    border-radius: 0.8vmin;
}
</style>

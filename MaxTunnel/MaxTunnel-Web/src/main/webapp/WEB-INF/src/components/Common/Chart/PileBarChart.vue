<template>
  <div class='pileBar' :id=id></div>
</template>


<script>

  export default {
    name: "pile-bar-chart",
    props: {
        id: {
      		type: String,
      		request: true,
        	default: ''
      	},
      	xAxisData: {
	      	type: Array,
	      	request: true
      	},
      	data: {
      		type: Array,
      		request: true
      	}
    },
    data() {
      return {
        barChart: null
       
      }
    },
    mounted() {
    	this.drawPileBar()
    },
    watch: {
    	'data': function(){
    		this.drawPileBar()
    	}
    },
    methods: {
 		drawPileBar() {
 			this.barChart = this.$echarts.init(document.getElementById(this.id))
 			let seriesData = []
		    this.data.forEach(cat=>{
		    	let temp = {}
		    	temp.name =  cat.tunnelName + cat.category
		    	temp.type = 'bar',
		    	temp.barWidth = 10,
		    	temp.stack = cat.tunnelName
		    	temp.data = cat.value
		    	seriesData.push(temp)
		    })
 			let  barOption = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    legend: {
			        data:['古城大街风机类电表','古城大街灯类电表','古城大街水泵类电表','古城大街其他类电表','实验路灯类电表','实验路风机类电表','实验路水泵类电表','实验路其他类电表']
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            // data : ['周一','周二','周三','周四','周五','周六','周日']
			            data: this.xAxisData
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series: seriesData,
			    // series : [
			    //     {
			    //         name:'古城大街风机类电表',
			    //         type:'bar',
			    //         barWidth : 10,
			    //         stack: '古城大街',
			    //         data:[320, 332, 301, 334, 390, 330, 320]
			    //     },
			    //     {
			    //         name:'古城大街灯类电表',
			    //         type:'bar',
			    //         stack: '古城大街',
			    //         data:[320, 432, 301, 334, 490, 330, 410]
			    //     },
			    //     {
			    //         name:'古城大街水泵类电表',
			    //         type:'bar',
			    //         stack: '古城大街',
			    //         data:[220, 182, 191, 234, 290, 330, 310]
			    //     },
			    //     {
			    //         name:'古城大街其他类电表',
			    //         type:'bar',
			    //         stack: '古城大街',
			    //         data:[150, 232, 201, 154, 190, 330, 410]
			    //     },
			    //     {
			    //         name:'实验路风机类电表',
			    //         type:'bar',
			    //         barWidth : 10,
			    //         stack: '实验路',
			    //         data:[862, 1018, 964, 1026, 1679, 1600, 1570]
			    //         // markLine : {
			    //         //     lineStyle: {
			    //         //         normal: {
			    //         //             type: 'dashed'
			    //         //         }
			    //         //     },
			    //         //     data : [
			    //         //         [{type : 'min'}, {type : 'max'}]
			    //         //     ]
			    //         // }
			    //     },
			    //     {
			    //         name:'实验路灯类电表',
			    //         type:'bar',
			    //         stack: '实验路',
			    //         data:[620, 732, 701, 734, 1090, 1130, 1120]
			    //     },
			    //     {
			    //         name:'实验路水泵类电表',
			    //         type:'bar',
			    //         stack: '实验路',
			    //         data:[120, 132, 101, 134, 290, 230, 220]
			    //     },
			    //     {
			    //         name:'实验路其他类电表',
			    //         type:'bar',
			    //         stack: '实验路',
			    //         data:[60, 72, 71, 74, 190, 130, 110]
			    //     }
			    // ]
			}
			this.barChart.setOption(barOption);
 		}
    },
  }
</script>

<style scoped>
  .pileBar {
    width: 100%;
    height: 100%;
  }
</style>


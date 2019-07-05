<template>
	<div>
		<h2 class="title">巡检明细报表</h2>
		<div>
			<div class="dateBox">
				<div class="navigation">
					<div @click="showWeek()" :class="{'activeWeek': isWeek}">周报</div>
					<div @click="showMonth()" :class="{'activeMonth': isMonth}">月报</div>
					<div @click="showYear()" :class="{'activeYear': isYear}">年报</div>
				</div>
				<div class="nullDate" v-if="cycleReport.length==undefined">暂无数据</div>
				<div class="dataBox">
					<div class="itemBox" v-for="(item,index) in cycleReport" :key="index">
						<span class="leftBox"></span>
						<span class="itemContent">{{item.name}}</span>
						<div class="btnBox">
							<Button type="primary" size="small" @click="previewCycle(item.id)">预览</Button>
							<Button type="primary" size="small" @click="downloadCycle(item.id,item.name)">下载</Button>
						</div>
					</div>
				</div>
			</div>
			<div class="showReport">
				<PDF ref="pdf"></PDF>
			</div>
		</div>
	</div>
</template>
<script>
import PDF from "../../../../../components/UM/MAM/pdfPerviewDownload";
import { DataAnalysisService } from "../../../../../services/DataAnalysis";
import Vue from "vue";
export default {
	components: { PDF },
	data() {
		return {
			cycleReport: [],
			ofType: 1,
			type: 3,
			timeContainer: [],
			isWeek: true,
			isMonth: false,
			isYear: false
		};
	},
	mounted() {
		this.cycleDown();
		DataAnalysisService.timeEnums().then(res => {
			this.timeContainer = res.slice(2);
		});
	},
	methods: {
		showWeek(){
			this.type = 3
			this.isWeek = true
			this.isMonth = false
			this.isYear = false
			this.cycleDown()
		},
		showMonth(){
			this.type = 4
			this.isWeek = false
			this.isMonth = true
			this.isYear = false
			this.cycleDown()
		},
		showYear(){
			this.type = 5
			this.isWeek = false
			this.isMonth = false
			this.isYear = true
			this.cycleDown()
		},
		cycleDown() {
			var params = {
				ofType: this.ofType,
				type: this.type
			};
			DataAnalysisService.chooseDown(params).then(res => {
				this.cycleReport = res;
			});
		},
		//预览
		previewCycle(id) {
			let _this = this;
			this.$refs.pdf.previewPDF(_this.ApiUrl + "/reports/preview/" + id);
		},
		//下载
		downloadCycle(id, fileName) {
			let _this = this;
			this.$refs.pdf.downloadPDF(
				_this.ApiUrl + "/reports/download/" + id,
				fileName
			);
		}
	}
};
</script>
<style scoped>
.title {
	font-size: 2.8vmin;
	color: #fff;
	margin-left: 1vmin;
}
.dateBox,
.showReport {
	padding: 2vmin;
	float: left;
	color: #fff;
}
.dateBox {
	width: 27%;
}
.showReport {
	width: 73%;
}
.itemBox {
	line-height: 4vmin;
	margin: 0.5vmin;
	padding-right: 0.5vmin;
	min-height: 25px;
}
.leftBox,
.itemContent {
	display: inline-block;
	vertical-align: top;
}
.leftBox {
	width: 0.5vmin;
	background: #ed3f14d1;
	height: 4vmin;
}
.itemContent {
	font-size: 1.5vmin;
	color: #fff;
}
.btnBox {
	float: right;
	width: 33%;
}
.dateBox .ivu-tabs>>>.ivu-tabs-nav{
	color: #fff;
}
.navigation{
    line-height: 3.5vmin;
    color: #fff;
	margin-bottom: 1vmin;
	border-bottom: 0.2vmin solid #fff;
}
.navigation div{
    display: inline;
    line-height: 3.5vmin;
    cursor: pointer;
    padding: 0.9vmin 3vmin;
    font-size: 1.5vmin;
}
.navigation div:hover,.activeWeek,.activeMonth,.activeYear{
    border-bottom: 0.4vmin solid #1798e0;
}
.nullDate{
	text-align: center;
    margin-top: 2vmin;
    font-size: 2vmin;
}

.dataBox{
	height: 70vh;    
	overflow-y: auto;
}
.dataBox::-webkit-scrollbar{
    width: 4px;
    height: 4px;
}
.dataBox::-webkit-scrollbar-thumb{
    border-radius: 5px;
    -webkit-box-shadow: inset 0 0 5px rgba(228, 198, 198, 0.2);
    background: rgba(0, 0, 0, 0.2)
}
.dataBox::-webkit-scrollbar-track{
    border-radius: 0;
    -webkit-box-shadow: inset 0 0 5px rgba(221, 208, 208, 0.2);
    background: rgba(0, 0, 0, 0.1)
}
@media (min-width: 2200px) {
	.ivu-btn,
	.ivu-btn-small {
		border-radius: 0.5vmin;
		padding: 0.5vmin 0.7vmin;
		font-size: 1.2vmin !important;
	}
}
</style>

